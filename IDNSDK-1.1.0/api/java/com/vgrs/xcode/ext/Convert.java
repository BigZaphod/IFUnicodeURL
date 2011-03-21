/*************************************************************************/
/*                                                                       */
/* Convert.java                                                          */
/*                                                                       */
/* (c) VeriSign Inc., 2000-2003, All rights reserved                     */
/*                                                                       */
/* @author: John Colosi                                                  */
/* @date: 02/24/2003                                                     */
/*                                                                       */
/*************************************************************************/


package com.vgrs.xcode.ext;

import java.io.*;
import java.util.*;
import com.vgrs.xcode.util.*;
import com.vgrs.xcode.idna.*;
import com.vgrs.xcode.common.*;


/**
 * This routine converts directly between Race, Punycode and Native forms,
 * abstracting any intermediate steps. For instance, it is possible for
 * calling applications to convert a domain name from Race to Punycode and UTF8
 * using a single call.
 * <br><br><b>Note:</b><br>
 *  Data conversions involving "Race" and "Punycode" rely on the underlying
 *  Idna object.  Encoding to "Race" or "Punycode" always applies the
 *  Nameprep algorithm.  Decoding from "Race" or "Punycode" may optionally
 *  apply Nameprep dependant on the toUnicodeRoundTripCheckFlag of the Idna
 *  object.  Conversions to and from all other data types do not
 *  include the Nameprep algorithm, and are allowed to contain data not valid
 *  for IDN registration.
 */
public class Convert {



	/**
	 * A string refering to the Row-based ASCII Compatible Encoding.
	 * <br>This variable is hard coded to the value "RACE".
	 */
	public static final String RACE_ENCODING = "RACE";

	/**
	 * A string refering to the Punycode algorithm referenced in RFC 3492.
	 * <br>This variable is hard coded to the value "PUNYCODE".
	 */
	public static final String PUNYCODE_ENCODING = "PUNYCODE";



	/**
	 * Private attributes
	 */
	private Idna iRace = null;
	private Idna iPunycode = null;
	private UnicodeFilter unicodeFilter = null;



	/**
	 * Construct a new Convert object to transform a single input sequence
	 * into one or more encoding forms.
	 */
	public Convert () throws XcodeException {
		this(new Idna(new Race(),new Nameprep()),
		new Idna(new Punycode(),new Nameprep()));
	}

	/**
	 * Construct a new Convert object to transform a single input sequence
	 * into one or more encoding forms.
	 * @param nameprep A Nameprep object to use in conversions.
	 * @param punycode An Ace object for converting to and from Punycode.
	 * @param race An Ace object for converting to and from Race.
	 * @throws XcodeException If the parameters are null or invalid.
	 */
	public Convert (Nameprep nameprep, Punycode punycode, Race race)
	throws XcodeException {
		this(new Idna(race,nameprep), new Idna(punycode,nameprep));
	}

	/**
	 * Construct a new Convert object to transform a single input sequence
	 * into one or more encoding forms.
	 * @param nameprep A Nameprep object to use in conversions.
	 * @param punycode An Ace object for converting to and from Punycode.
	 * @param race An Ace object for converting to and from Race.
	 * @param toUnicodeExceptionFlag (see the Idna object for details)
	 * @throws XcodeException If the parameters are null or invalid.
	 */
	public Convert (Nameprep nameprep, Punycode punycode,
	Race race, boolean toUnicodeExceptionFlag)
	throws XcodeException {
		this(new Idna(race,nameprep,toUnicodeExceptionFlag),
		new Idna(punycode,nameprep,toUnicodeExceptionFlag));
	}

	/**
	 * Construct a new Convert object to transform a single input sequence
	 * into one or more encoding forms.
	 * @param race An Idna object for converting to and from Race.
	 * @param punycode An Idna object for converting to and from Punycode.
	 * @throws XcodeException If the parameters are null or invalid.
	 */
	public Convert (Idna iRace, Idna iPunycode) throws XcodeException {
		this(iRace,iPunycode,null);
	}

	/**
	 * Construct a new Convert object to transform a single input sequence
	 * into one or more encoding forms.
	 * @param race An Idna object for converting to and from Race.
	 * @param punycode An Idna object for converting to and from Punycode.
	 * @param unicodeFilter A UnicodeFilter object to applied to the input
	 * sequence before encoding.
	 * @throws XcodeException If the parameters are null or invalid.
	 */
	public Convert (Idna iRace, Idna iPunycode, UnicodeFilter unicodeFilter)
	throws XcodeException {
		this.iRace = iRace;
		this.iPunycode = iPunycode;
		this.unicodeFilter = unicodeFilter;
	}



	/**
	 * Convert the input sequence into a new encoding form.
	 * @param input the string to be converted
	 * @param itype the encoding type of the input string
	 * @param otype the desired output encoding
	 * @return A Properties object containing the results of all transformations.
	 * Each key matches a String from otype and each value is the associated
	 * encoded form.
	 * @throws XcodeException if either the input string is invalid or
	 *									the itype/otype is not supported
	 */
	public String execute (String input, String itype, String otype)
	throws XcodeException {

		if (input == null ||
			itype == null ||
		otype == null) throw XcodeError.NULL_ARGUMENT();

		if (input.length()==0 ||
			itype.length()==0 ||
		otype.length()==0) {throw XcodeError.EMPTY_ARGUMENT();}

		int[] unicode = decode(input,itype);
		if (unicodeFilter != null) {unicodeFilter.test(unicode);}
		return encode(unicode,otype);
	}

	/**
	 * Convert the input sequence into one or more encoding forms.
	 * <br><br><b>Note:</b><br>
	 *  The Convert logic ignores XcodeExceptions when attempting to convert to
	 *  multiple output encodings.  Output encodings for which the conversion
	 *  fails are not included in the result set.  This implies that
	 *  XcodeExceptions such as UNSUPPORTED_ENCODING are hidden from the user
	 *  unless the call asks specifically for a conversion to exactly one encoding.
	 *  This logic improves the efficiency of the algorithm, and is not generally
	 *  problematic.
	 * @param input the string to be converted
	 * @param itype the encoding type of the input string
	 * @param otype the String array of desired output encodings
	 * @return A Properties object containing the results of all transformations.
	 * Each key matches a String from otype and each value is the associated
	 * encoded form.
	 * @throws XcodeException On invalid input parameters
	 */
	public Properties execute (String input, String itype, String[] otype)
	throws XcodeException {

		if (input == null ||
			itype == null ||
		otype == null) throw XcodeError.NULL_ARGUMENT();

		if (input.length()==0 ||
			itype.length()==0 ||
		otype.length==0) {throw XcodeError.EMPTY_ARGUMENT();}

		int[] unicode = decode(input,itype);
		if (unicodeFilter != null) {unicodeFilter.test(unicode);}
		return encode(unicode,otype);
	}



	/**
	 * Decodes the input string to Unicode
	 * @param input the string to be converted
	 * @param itype the encoding type of the input string
	 * @throw XcodeException if the itype is not supported or the
	 *								 input string is invalid
	 */
	private int[] decode(String input, String itype)
	throws XcodeException {

		if (itype.equalsIgnoreCase(RACE_ENCODING)) {
			return iRace.domainToUnicode(input.toCharArray());
		}
		else if (itype.equalsIgnoreCase(PUNYCODE_ENCODING)) {
			return iPunycode.domainToUnicode(input.toCharArray());
		}
		else {
			String utf16 = Native.decode(Native.getEncoding(input), itype);
			return Unicode.encode(utf16.toCharArray());
		}
	}



	/**
	 * Encode the input Unicode using the indicated encoding type
	 * @param unicode the Unicode sequence to be converted
	 * @param otype the encoding type
	 * @throw XcodeException if the otype is not supported or the utf16
	 *								 string is invalid
	 */
	private String encode(int[] unicode, String otype)
	throws XcodeException {

		if (otype.equalsIgnoreCase(RACE_ENCODING)) {
			return new String(iRace.domainToAscii(unicode));
		}
		else if (otype.equalsIgnoreCase(PUNYCODE_ENCODING)) {
			return new String(iPunycode.domainToAscii(unicode));
		}
		else {
			return Native.encode(new String(Unicode.decode(unicode)),otype);
		}
	}

	/**
	 * Encode the input Unicode using all indicated encoding types
	 * @param unicode the Unicode sequence to be converted
	 * @param otype the encoding types
	 * @throw XcodeException Currently no XcodeException can be thrown
	 */
	private Properties encode(int[] unicode, String[] otype)
	throws XcodeException {
		Properties results = new Properties();
		for (int i=0; i<otype.length; i++) {
			try {
				results.put(otype[i],encode(unicode,otype[i]));
			} catch (XcodeException x) {}
		}
		return results;
	}

}
