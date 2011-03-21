/*************************************************************************/
/*                                                                       */
/* Native.java                                                           */
/*                                                                       */
/* (c) VeriSign Inc., 2000-2003, All rights reserved                     */
/*                                                                       */
/* @author: Srikanth Veeramachaneni                                      */
/* @author: John Colosi                                                  */
/* @date: January, 2003                                                  */
/*                                                                       */
/* @update: June, 2003, John Colosi                                      */
/*  When using JVM 1.4 or greater, the getBytes() method call of the     */
/*  String object can return a java.nio.BufferOverflowException.         */
/*    (e.g.)   byte[] b = new String("\u678f").getBytes("ISO2022KR");    */
/*  It is not possible to catch the java.nio.BufferOverflowException     */
/*  specifically because this wouldn't compile using older JVM           */
/*  instances.  Instead, the internalDecode() method now catches any     */
/*  Exception and covers it with a XcodeError.NATIVE_INVALID_ENCODING.   */
/*                                                                       */
/*************************************************************************/

package com.vgrs.xcode.common;

import java.io.*;
import java.util.*;
import com.vgrs.xcode.util.*;

/**
 * A class that provides algorithms to encode/decode a UTF 16
 * to/from native characters.
 */
public final class Native {




	/**
	 * Java UTF8 encoding type, used to abstract the Java internals.
	 */
	public static final String UTF8 = "UTF8";

	/**
	 * DOUBLE_UTF8 is not a Java supported encoding, and so requires special
	 * processing.
	 */
	public static final String DOUBLE_UTF8 = "DOUBLE_UTF8";




	/**
	 * Retrieve the encoding stored in a Java String.  In Java, a native
	 * encoding is stored as an array of bytes.  Unfortunately convenience
	 * sometimes dictates that we must use a String to store this data.  When
	 * creating a String from an encoding, Java places each encoding byte in the
	 * low octet of a two byte char, leaving the high byte empty.  In order to
	 * retrieve an encoding from a String, we will reverse this process,
	 * returning only the low bytes from each character in the String.  We must
	 * also throw an Exception if any high byte is not empty to avoid inaccurate
	 * results.
	 * @param input the String holding the encoded data
	 * @return an array of bytes
	 * @throws XcodeException if the input is null/empty or
	 *                        if any high byte is non-zero
	 */
	public static byte[] getEncoding(String input) throws XcodeException {
		if (input == null) throw XcodeError.NULL_ARGUMENT();
		if (input.length() == 0) throw XcodeError.EMPTY_ARGUMENT();
		char[] c = input.toCharArray();
		byte[] b = new byte[c.length];
		for (int i=0; i<c.length; i++) {
			if (c[i] > 0xFF) {throw XcodeError.NATIVE_INVALID_ENCODING();}
			b[i] = (byte)c[i];
		}
		return b;
	}




	/**
	 *   Encode a UTF16 sequence using the specified native encoding.
	 *   This method does not perform round-trip checking and does not support
	 *   DOUBLE_UTF8.  It is provided only for internal use.  The
	 *   encode(String, String[]) method should be used for applications.
	 */
	private static byte[] internalEncode(String input, String enc)
	throws XcodeException {
		if (input == null) throw XcodeError.NULL_ARGUMENT();
		try {return input.getBytes(enc);}
		catch (UnsupportedEncodingException x) {
			throw XcodeError.UNSUPPORTED_ENCODING(": "+enc);
		}
		catch (Exception x) {
			throw XcodeError.NATIVE_INVALID_ENCODING(": "+enc);
		}
	}

	/**
	 * Encode the input using each of the Java supported encoding types
	 * A round-trip check is used to ensure that the encoded data
	 * is valid.
	 * @param input the string to be encoded
	 * @return a HashMap keyed on all encoding types
	 * @throws XcodeException if input is null or empty string.
	 */
	public static HashMap encode(String input) throws XcodeException {
		return encode(input, ENCODINGS);
	}

	/**
	 * Encode the input using the indicated encoding types. A round-trip check
	 * is used to ensure that the encoded data is valid.
	 * @param input string to be encoded
	 * @param encoding string to indicate the encoding type of the output
	 * @throws XcodeException if input is null or empty string or
	 *          if the named charset is not supported
	 * @return the encoded string
	 */
	public static String encode(String input, String encoding)
	throws XcodeException {
		if (input == null) throw XcodeError.NULL_ARGUMENT();
		if (input.length() == 0) throw XcodeError.EMPTY_ARGUMENT();

		byte[] output = null;
		String check = null;

		// Special DOUBLE_UTF8 processing
		if (encoding.equals(DOUBLE_UTF8)) {
			output = internalEncode(new String(internalEncode(input,UTF8)),UTF8);
			check = internalDecode(getEncoding(internalDecode(output,UTF8)),UTF8);
		}
		else {
			output = internalEncode(input,encoding);
			check = internalDecode(output,encoding);
		}
		if (! input.equals(check)) {
			throw XcodeError.NATIVE_INVALID_ENCODING(": "+encoding);
		}

		//return new String(output);
		/*
		 * We used to essentially do this:
		 *    return new String(internalEncode(input,encoding));
		 * But the problem is that new String() is actually trying to decode the
		 * input with respect to the default encoding.  The crux of the
		 * internalDecode routine is actually a call to new String().  Here, we
		 * want to return a String object with exactly the data in the byte[], no
		 * decoding.  We do it the hard way now.
		 */
		char[] c = new char[output.length];
		for (int i=0; i<c.length; i++) {c[i]=(char)(output[i]&0xff);}
		return new String(c);
	}

	/**
	 * Encode the input using the indicated encoding types. A round-trip check
	 * is used to ensure that the encoded data is valid.
	 * @param input the string to be encoded
	 * @param encodings indicate the encoding types of the output string
	 * @throws XcodeException if input is null or empty string or
	 *          if the named charset is not supported
	 * @return a HashMap with keyed on the encoding type
	 */
	public static HashMap encode(String input, String[] encodings)
	throws XcodeException {
		if (input == null) throw XcodeError.NULL_ARGUMENT();
		if (input.length() == 0) throw XcodeError.EMPTY_ARGUMENT();

		HashMap results = new HashMap();

		for (int i = 0; i < encodings.length; i++) {
			try {results.put(encodings[i],encode(input,encodings[i]));}
			catch (XcodeException x) {}
		}
		return results;
	}

	/**
	 * Encode the input using each of the encoding types to a HashSet
	 * @param input the string to be encoded
	 * @return a HashSet of the encoded string
	 * @throws XcodeException if input is null or empty string.
	 */
	public static HashSet encodeToSet(String input) throws XcodeException {
		return encodeToSet(input,ENCODINGS);
	}

	/**
	 * Encode the input using indicated encoding types to a HashSet
	 * @param input the string to be encoded
	 * @param encodings encoding types of the decoded string
	 * @return a HashSet of the encoded string
	 * @throws XcodeException if input is null or empty string.
	 */
	public static HashSet encodeToSet(String input, String[] encodings)
	throws XcodeException {
		if (input == null) throw XcodeError.NULL_ARGUMENT();
		if (input.length() == 0) throw XcodeError.EMPTY_ARGUMENT();

		HashSet results = new HashSet();

		for (int i = 0; i < encodings.length; i++) {
			try {results.add(encode(input,encodings[i]));}
			catch (XcodeException x) {}
		}
		return results;
	}




	/**
	 * Use the specified native encoding to return the input sequence
	 * in UTF16 format.  This method does not perform round-trip checking
	 * and does not support DOUBLE_UTF8.  It is provided only for internal
	 * use.  The decode method should be used for applications.
	 *
	 * @param input the bytes to be decoded into characters
	 * @param enc the name of a supported charset native encoding
	 * @throws XcodeException If the named charset is not supported
	 */
	private static String internalDecode(byte[] input, String enc)
	throws XcodeException {
		if (input == null) throw XcodeError.NULL_ARGUMENT();
		try {return new String(input,enc);}
		catch (UnsupportedEncodingException x) {
			throw XcodeError.UNSUPPORTED_ENCODING(": "+enc);
		}
	}

	/**
	 * Evaluate the given input against the list of encodings
	 * to determine how the input may have been encoded.  First
	 * decode and then re-encode the input using each of the
	 * encoding types.  Any type for which the round-trip
	 * is symmetric is considered a viable encoding, and added
	 * to the output list.
	 *
	 * @param input the bytes to be decoded into characters
	 * @return a HashMap with keyed on the encoding type
	 * @throws XcodeException If the named charset is not supported
	 *
	 */
	public static HashMap decode(byte[] input) throws XcodeException {
		return decode(input, ENCODINGS);
	}

	/**
	 * Use the specified native encoding to return the input sequence
	 * in UTF16 format.  This method performs round-trip checking
	 * and supports DOUBLE_UTF8.
	 *
	 * @param input the bytes to be decoded
	 * @param encoding the encoding type to be used in decoding
	 * @return a string of the indicated encoding type
	 * @throws XcodeException if input is null or empty string or
	 *          if the named charset is not supported
	 */
	public static String decode(byte[] input, String encoding)
	throws XcodeException {
		if (input == null) throw XcodeError.NULL_ARGUMENT();
		if (input.length == 0) throw XcodeError.EMPTY_ARGUMENT();

		String output = null;
		byte[] check = null;

		// Special DOUBLE_UTF8 processing
		if (encoding.equals(DOUBLE_UTF8)) {
			output = internalDecode(getEncoding(internalDecode(input,UTF8)),UTF8);
			check = internalEncode(new String(internalEncode(output,UTF8)),UTF8);
		} else {
			output = internalDecode(input,encoding);
			check = internalEncode(output,encoding);
		}
		if (! Arrays.equals(check,input)) {throw XcodeError.NATIVE_INVALID_ENCODING(": "+encoding);}
		return output;
	}

	/**
	 * Decode the input of the indicated encoding types. A round-trip check
	 * is used to ensure that the encoded data is valid.
	 * @param input the native string to be decode
	 * @param encodings indicate the encoding types of the input string
	 * @throws XcodeException if input is null or empty string or
	 *          if the named charset is not supported
	 * @return the encoded string
	 */
	public static HashMap decode(byte[] input, String[] encodings)
	throws XcodeException {
		if (input == null) throw XcodeError.NULL_ARGUMENT();
		if (input.length == 0) throw XcodeError.EMPTY_ARGUMENT();

		HashMap results = new HashMap();

		for (int i = 0; i < encodings.length; i++) {
			try {results.put(encodings[i],decode(input,encodings[i]));}
			catch (XcodeException x) {}
		}
		return results;
	}

	/**
	 * Decode the native string of all encoding types to a HashSet
	 * @param input the string to be decoded
	 * @return a HashSet of the decoded string
	 * @throws XcodeException if input is null or empty string.
	 */
	public static HashSet decodeToSet(byte[] input) throws XcodeException {
		return decodeToSet(input,ENCODINGS);
	}

	/**
	 * Decode the native string of indicated encoding types to a HashSet
	 * @param input the string to be decoded
	 * @param encodings encoding types of the decoded string
	 * @return a HashSet of the decoded string
	 * @throws XcodeException if input is null or empty string.
	 */
	public static HashSet decodeToSet(byte[] input, String[] encodings)
	throws XcodeException {
		if (input == null) throw XcodeError.NULL_ARGUMENT();
		if (input.length == 0) throw XcodeError.EMPTY_ARGUMENT();

		HashSet results = new HashSet();

		for (int i = 0; i < encodings.length; i++) {
			try {results.add(decode(input,encodings[i]));}
			catch (XcodeException x) {}
		}
		return results;
	}




	/**
	 * Array to stort all Java-Supported native encodings
	 */
	public static final String[] ENCODINGS = {
		"ASCII",
		"Cp1252",
		"ISO8859_1",
		"UnicodeBig",
		"UnicodeBigUnmarked",
		"UnicodeLittle",
		"UnicodeLittleUnmarked",
		"UTF8",
		"UTF-16",
		"Big5",
		"Big5_HKSCS",
		"Cp037",
		"Cp273",
		"Cp277",
		"Cp278",
		"Cp280",
		"Cp284",
		"Cp285",
		"Cp297",
		"Cp420",
		"Cp424",
		"Cp437",
		"Cp500",
		"Cp737",
		"Cp775",
		"Cp838",
		"Cp850",
		"Cp852",
		"Cp855",
		"Cp856",
		"Cp857",
		"Cp858",
		"Cp860",
		"Cp861",
		"Cp862",
		"Cp863",
		"Cp864",
		"Cp865",
		"Cp866",
		"Cp868",
		"Cp869",
		"Cp870",
		"Cp871",
		"Cp874",
		"Cp875",
		"Cp918",
		"Cp921",
		"Cp922",
		"Cp930",
		"Cp933",
		"Cp935",
		"Cp937",
		"Cp939",
		"Cp942",
		"Cp942C",
		"Cp943",
		"Cp943C",
		"Cp948",
		"Cp949",
		"Cp949C",
		"Cp950",
		"Cp964",
		"Cp970",
		"Cp1006",
		"Cp1025",
		"Cp1026",
		"Cp1046",
		"Cp1097",
		"Cp1098",
		"Cp1112",
		"Cp1122",
		"Cp1123",
		"Cp1124",
		"Cp1140",
		"Cp1141",
		"Cp1142",
		"Cp1143",
		"Cp1144",
		"Cp1145",
		"Cp1146",
		"Cp1147",
		"Cp1148",
		"Cp1149",
		"Cp1250",
		"Cp1251",
		"Cp1253",
		"Cp1254",
		"Cp1255",
		"Cp1256",
		"Cp1257",
		"Cp1258",
		"Cp1381",
		"Cp1383",
		"Cp33722",
		"EUC_CN",
		"EUC_JP",
		"EUC_JP_LINUX",
		"EUC_KR",
		"EUC_TW",
		"GBK",
		//"ISO2022CN",
		//"ISO2022CN_CNS",
		//"ISO2022CN_GB",
		"ISO2022JP",
		"ISO2022KR",
		"ISO8859_2",
		"ISO8859_3",
		"ISO8859_4",
		"ISO8859_5",
		"ISO8859_6",
		"ISO8859_7",
		"ISO8859_8",
		"ISO8859_9",
		"ISO8859_13",
		"ISO8859_15_FDIS",
		"JIS0201",
		"JIS0208",
		"JIS0212",
		//"JISAutoDetect",
		"Johab",
		"KOI8_R",
		"MS874",
		"MS932",
		"MS936",
		"MS949",
		"MS950",
		"MacArabic",
		"MacCentralEurope",
		"MacCroatian",
		"MacCyrillic",
		"MacDingbat",
		"MacGreek",
		"MacHebrew",
		"MacIceland",
		"MacRoman",
		"MacRomania",
		"MacSymbol",
		"MacThai",
		"MacTurkish",
		"MacUkraine",
		"SJIS",
		"TIS620",
	};

} // END class Native
