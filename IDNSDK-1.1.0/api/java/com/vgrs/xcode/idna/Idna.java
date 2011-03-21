/*************************************************************************/
/*                                                                       */
/* Idna.java                                                             */
/*                                                                       */
/* (c) VeriSign Inc., 2000-2003, All rights reserved                     */
/*                                                                       */
/* @author: John Colosi                                                  */
/* @date: January, 2002                                                  */
/*                                                                       */
/*************************************************************************/


package com.vgrs.xcode.idna;

import java.io.*;
import java.util.*;
import com.vgrs.xcode.util.*;
import com.vgrs.xcode.common.*;

/**
 * Implementation of RFC 3490.
 * <p>
 * Ultimately the following will be true: <br>
 *  The Nameprep objects all use Unicode as input and output<br>
 *  The Race object uses Utf16 as input and output<br>
 *  The Punycode object uses Unicode as input and output<br>
 * <p>
 *           toAscii<br>
 * 1. Application has Unicode data                               U32<br>
 * 2. Pass the Unicode data to Idna                              U32<br>
 * 3. Idna tokenizes the data.                                  *U32<br>
 * 4. Idna runs Unicode through Nameprep.                        U32<br>
 * 5. Idna encodes the data using an ACE algorithm               U32->U16<br>
 * 6. Idna assembles the labels.                                      U16<br>
 * 7. Idna passes the Utf16 back to the application.                  U16<br>
 * <p>
 * - It is not trivial to tokenize the labels as Unicode.  We have written<br>
 *   a special object to do so, which allows us to avoid decoding to and<br>
 *   from Unicode just for tokenization.<br>
 * <p>
 *
 *           toUnicode<br>
 * 1. Application has Utf16 (probably Ascii) string                   U16<br>
 * 2. Pass the Utf16 data to Idna.                                    U16<br>
 * 3. Idna tokenizes the data.                                        U16<br>
 * 4. Idna encodes the data in Unicode.                          U32<-U16<br>
 * 5. Idna runs Unicode through Nameprep.                       *U32<br>
 * 6. Idna decodes the data to Utf-16.                           U32->U16<br>
 * 7. Idna decodes the data using an ACE algorithm               U32<-U16<br>
 * 8. Idna assembles the labels                                 *U32<br>
 * 9. Idna passes the Unicode back to the application.           U32<br>
 * <p>
 * - Unfortunately, nameprep will require that the data undergo a round-trip
 *   Unicode encoding.  This will only happen if the data is non-Ascii.<br>
 * - It is not trivial to assemble the labels as Unicode.  We have written
 *   a special object to do so, which allows us to avoid decoding to and
 *   from Unicode just for assembly.<br>
 * <p>
 * NOTE: Also, an interesting point here.  Race requires utf16 but indirectly
 *       uses the DELIMITERS attribute of this object to determine if the input
 *       or output has an internal delimiter.  This implementation does not
 *       therefore support any delimiters outside of plane 0.  If there was
 *       a new delimiter outside the BMP, then the Race impl would have to
 *       do some surrogate conversions to make the same determination.
 * <p>
 */
public final class Idna {


	/**
	 * Stores the list of IDNA compliant delimiters.
	 * <br>This variable is hard coded to the value
	 * <i>{0x2e,0x3002,0xff0e,0xff61}</i>
	 */
	static public final int[] INT_DELIMITERS = {0x2e,0x3002,0xff0e,0xff61};

	/**
	 * Stores the list of IDNA compliant delimiters.
	 * <br>This variable is hard coded to the value
	 * <i>"\ u 0 0 2 e \ u 3 0 0 2 \ u f f 0 e \ u f f 6 1"</i>
	 */
	static public final String DELIMITERS = "\u002e\u3002\uff0e\uff61";

	/**
	 * Stores the Unicode character used to delimit domain labels in an ASCII
	 * Compatible Encoding sequence.
	 * <br>This variable is hard coded to the value <i>0x002e</i>.
	 */
	static public final char ACE_DELIMITER = 0x002e;

	/**
	 * RFC 1034 imposes a length restriction of 0 to 63 octets per domain label.
	 * <br>This variable is hard coded to the value <i>63</i>.
	 */
	static public final int MAX_DOMAIN_LABEL_LENGTH_IN_OCTETS = 63;

	/**
	 * Indicates whether or not to throw XcodeExceptions from the toUnicode method.
	 * According to RFC 3490 [IDNA] any error condition in the execution of the
	 * toUnicode operation results in the return of the input.  This prevents
	 * applications from knowing whether an error occured during
	 * the process.  The Idna object uses an internal flag to indicate whether
	 * an exception should be thrown if an error condition occurs.  Setting the
	 * internal flag to <i>true</i> will allow an error condition to throw an
	 * exception to the calling application.  The application can then decide
	 * whether or not to continue execution.
	 * <br>This variable takes the value <i>false</i> by default.
	 */
	static public final boolean DEFAULT_TO_UNICODE_EXCEPTION_FLAG = false;

	/**
	 * @deprecated This variable has been replaced by
	 * DEFAULT_TO_UNICODE_EXCEPTION_FLAG and shares the same runtime value.  All
	 * references should be replaced.
	 */
	static public final boolean DEFAULT_STRICT = DEFAULT_TO_UNICODE_EXCEPTION_FLAG;

	/**
	 * Indicates whether or not to run a round-trip check in the toUnicode method.
	 * According to RFC 3490 [IDNA] after decoding an ASCII sequence, the
	 * sequence should be re-encoded and checked for differences with the
	 * original.  Strictly speaking, this check is necessary to ensure the
	 * viability of the transformation.  However, it is NOT necessary when using
	 * the RACE decoding algorithm provided with this library.  It may also not
	 * be desirable when converting domains prepared with an alternate version
	 * of Nameprep, as some changes to the underlying codepoints might be
	 * expected.  An internal flag is provided to optionally omit the round-trip
	 * check.  If the value of this variable is true, the round-trip check is
	 * performed.  If the value is false, the round-trip check is omitted.
	 * <br>This variable takes the value <i>true</i> by default.
	 */
	static public final boolean DEFAULT_TO_UNICODE_ROUNDTRIP_CHECK_FLAG = true;

	private Ace ace;
	private Nameprep nameprep;
	private boolean toUnicodeExceptionFlag;
	private boolean toUnicodeRoundTripCheckFlag;

	/**
	 * Construct an Idna object for converting data to and from an ASCII
	 * compatible form.
	 * @param ace An Ace object to use during conversion.
	 * @param nameprep A Nameprep object to use during conversion.
	 */
	public Idna (Ace ace, Nameprep nameprep)
	throws XcodeException {
		this(ace,nameprep,
			DEFAULT_TO_UNICODE_EXCEPTION_FLAG,
			DEFAULT_TO_UNICODE_ROUNDTRIP_CHECK_FLAG
		);
	}

	/**
	 * Construct an Idna object for converting data to and from an ASCII
	 * compatible form.
	 * @param ace An Ace object to use during conversion.
	 * @param nameprep A Nameprep object to use during conversion.
	 * @param toUnicodeExceptionFlag See the description for the
	 * <b>DEFAULT_TO_UNICODE_EXCEPTION_FLAG</b> attibute which stores the default
	 * value for this parameter.
	 */
	public Idna (Ace ace, Nameprep nameprep, boolean toUnicodeExceptionFlag)
	throws XcodeException {
		this(ace,nameprep,toUnicodeExceptionFlag,
			DEFAULT_TO_UNICODE_ROUNDTRIP_CHECK_FLAG
		);
	}


	/**
	 * Construct an Idna object for converting data to and from an ASCII
	 * compatible form.
	 * @param ace An Ace object to use during conversion.
	 * @param nameprep A Nameprep object to use during conversion.
	 * @param toUnicodeExceptionFlag See the description for the
	 * <b>DEFAULT_TO_UNICODE_EXCEPTION_FLAG</b> attibute which stores the default
	 * value for this parameter.
	 * @param toUnicodeRoundTripCheckFlag See the description for the
	 * <b>DEFAULT_TO_UNICODE_ROUNDTRIP_CHECK_FLAG</b> attibute which stores the
	 * default value for this parameter.
	 */
	public Idna (Ace ace, Nameprep nameprep,
		boolean toUnicodeExceptionFlag,
	boolean toUnicodeRoundTripCheckFlag)
	throws XcodeException {
		if (ace == null || nameprep == null) {throw XcodeError.NULL_ARGUMENT();}
		this.ace = ace;
		this.nameprep = nameprep;
		this.toUnicodeExceptionFlag = toUnicodeExceptionFlag;
		this.toUnicodeRoundTripCheckFlag = toUnicodeRoundTripCheckFlag;
	}


	/**
	 * Checks if the input string is a delimiter
	 * @param s a string to check
	 * @return true if the s represents one of the DELIMITERS, false if not
	 */
	static public boolean isDelimiter(String s) {
		return (DELIMITERS.indexOf(s) >= 0);
	}


	/**
	 * Checks if the input int is a delimiter
	 * @param c an int to check
	 * @return true if the s represents one of the DELIMITERS, false if not
	 */
	static public boolean isDelimiter(int c) {
		return (DELIMITERS.indexOf(c) >= 0);
	}


	/**
	 * Converts an array of integers to a character array
	 * @param input an int array
	 * @return a character array
	 * @throws XcodeException if the input is null or empty
	 */
	public char[] domainToAscii(int[] input) throws XcodeException {
		if (input == null) {throw XcodeError.NULL_ARGUMENT();}
		if (input.length == 0) {throw XcodeError.EMPTY_ARGUMENT();}

		String output = new String();
		UnicodeTokenizer tokens = new UnicodeTokenizer(input,INT_DELIMITERS,true);
		int[] token;
		while (tokens.hasMoreTokens()) {
			token = tokens.nextToken();
			if (token.length == 1 && isDelimiter(token[0])) {output += ACE_DELIMITER;}
			else {output += new String(toAscii(token));}
		}

		return output.toCharArray();
	}


	/**
	 * Converts a character array to an integer array
	 * @param input a character array
	 * @return an int array
	 * @throws XcodeException if the input is null or empty
	 */
	public int[] domainToUnicode(char[] input) throws XcodeException {
		if (input == null) {throw XcodeError.NULL_ARGUMENT();}
		if (input.length == 0) {throw XcodeError.EMPTY_ARGUMENT();}

		UnicodeSequence output = new UnicodeSequence();
		StringTokenizer tokens =
		new StringTokenizer(new String(input),DELIMITERS,true);
		String token;
		while (tokens.hasMoreTokens()) {
			token = tokens.nextToken();
			if (isDelimiter(token)) {output.append(token.charAt(0));}
			else {output.append(toUnicode(token.toCharArray()));}
		}

		return output.get();
	}





	/**
	 * toAscii - IETF Idna draft documents a method of converting unicode
	 *           data into a DNS compliant domain.
	 *
	 * 1. If all code points in the sequence are in the ASCII range (0..7F)
	 *    then skip to step 3.
	 * 2. Perform the steps specified in [NAMEPREP] and fail if there is
	 *    an error. The AllowUnassigned flag is used in [NAMEPREP].
	 * 3. If the UseSTD3AsciiRules flag is set, then perform these checks:
	 *  (a) Verify the absence of non-LDH ASCII code points; that is,
	 *  the absence of 0..2C, 2E..2F, 3A..40, 5B..60, and 7B..7F.
	 *    (b) Verify the absence of leading and trailing hyphen-minus;
	 *    that is, the absence of U+002D at the beginning and end of
	 *  the sequence.
	 * 4. If all code points in the sequence are in the ASCII range
	 *  (0..7F), then skip to step 8.
	 * 5. Verify that the sequence does NOT begin with the ACE prefix.
	 * 6. Encode the sequence using the encoding algorithm in [PUNYCODE]
	 *  and fail if there is an error.
	 * 7. Prepend the ACE prefix.
	 * 8. Verify that the number of code points is in the range 1 to 63
	 *  inclusive.
	 *
	 * @param input an int array to be converted to characters
	 * @return converted character array
	 */
	private char[] toAscii(int[] input) throws XcodeException {
		char[] output;

		// Step #1-2
		if (! Utf16.isAscii(input)) {
			input = nameprep.execute(input);
		}

		// Step #3-7
		output = ace.encode(input);

		// Step #8
		if (output.length > MAX_DOMAIN_LABEL_LENGTH_IN_OCTETS) {
			throw XcodeError.IDNA_LABEL_LENGTH_RESTRICTION();
		}

		return output;
	}


	/**
	 * toUnicode -  IETF Idna draft documents a method of converting a
	 *              DNS compliant domain into unicode data.
	 *
	 * 1. If all code points in the sequence are in the ASCII range (0..7F)
	 *    then skip to step 3.
	 *
	 * 2. Perform the steps specified in [NAMEPREP] and fail if there is an
	 *    error. (If step 3 of ToASCII is also performed here, it will not
		 *    affect the overall behavior of ToUnicode, but it is not
	 *    necessary.) The AllowUnassigned flag is used in [NAMEPREP].
	 *
	 * 3. Verify that the sequence begins with the ACE prefix, and save a
	 *    copy of the sequence.
	 *
	 * 4. Remove the ACE prefix.
	 *
	 * 5. Decode the sequence using the decoding algorithm in [PUNYCODE]
	 *    and fail if there is an error. Save a copy of the result of
	 *    this step.
	 *
	 * 6. Apply ToASCII.
	 *
	 * 7. Verify that the result of step 6 matches the saved copy from
	 *    step 3, using a case-insensitive ASCII comparison.
	 *
	 * 8. Return the saved copy from step 5.
	 *
	 * NOTE: Idna says that toUnicode should not fail.  If a failure condition
	 *       appears, then the input should be returned.  Currently however, the
	 *       input and output types differ on toUnicode.  We take Utf16 (which
	 *       could be anything from ACE to surrogate pairs) and we return an
	 *       int[] storing the appropriate Unicode codepoints.  To return the
	 *       input on error, we have to convert the char[] to a int[].  It
	 *       may be irresponsible of the toUnicode method to catch an
	 *       exception, and then convert to an int[], masking the fact that
	 *       an error occurred.  Perhaps the better solution is to have the
	 *       application make an informed decision about what to do if it catches
	 *       an exception.  This implementation reluctantly adheres to the draft.
	 */
	private int[] toUnicode(char[] input) throws XcodeException {
		int[] output = null;
		char[] check = null;

		try {
			// Step #1-2
			if (! Utf16.isAscii(input)) {
				input = Unicode.decode(nameprep.execute(Unicode.encode(input)));
			}

			// Step #3-5
			output = ace.decode(input);

			// Step #6-7
			if (toUnicodeRoundTripCheckFlag) {
				check = toAscii(output);
				if (! new String(input).equalsIgnoreCase(new String(check))) {
					throw XcodeError.IDNA_DECODE_MISMATCH();
				}
			}

		} catch (XcodeException x) {
			if (toUnicodeExceptionFlag) {throw x;}
			output = Utf16.expand(input);
		}

		// Step #8
		return output;
	}

}
