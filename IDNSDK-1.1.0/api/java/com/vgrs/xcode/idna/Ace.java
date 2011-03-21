/*************************************************************************/
/*                                                                       */
/* Ace.java                                                              */
/*                                                                       */
/* (c) VeriSign Inc., 2000-2003, All rights reserved                     */
/*                                                                       */
/* @author: John Colosi                                                  */
/* @date: January, 2003                                                  */
/*                                                                       */
/*************************************************************************/


package com.vgrs.xcode.idna;

import java.io.*;
import java.util.*;
import com.vgrs.xcode.util.*;
import com.vgrs.xcode.common.*;

/**
 * An abstract class implementing logic common to all ASCII Compatible
 * Encodings.
 */
public abstract class Ace {

	static public final boolean DEFAULT_USE_STD_3_ASCII_RULES = true;

	private String prefix;
	private boolean useStd3AsciiRules;

	public Ace (String prefix, boolean useStd3AsciiRules) {
		this.prefix = prefix;
		this.useStd3AsciiRules = useStd3AsciiRules;
	}


	/**
	 * Encode an entire domain using the Ace algorithm.
	 * @param input An int array representing a domain name
	 * @return A char array with Ace encoded domain name
	 * @throws XcodeException when the input is null or empty or
	 *        the input contains non-standard 3
	 *        ASCII character or the encoded domain
	 *        name is empty or longer than 63.
	 */
	public char[] domainEncode(int[] input) throws XcodeException {
		if (input == null) {throw XcodeError.NULL_ARGUMENT();}
		if (input.length == 0) {throw XcodeError.EMPTY_ARGUMENT();}

		String output = new String();
		UnicodeTokenizer tokens = new UnicodeTokenizer(input,Idna.INT_DELIMITERS,true);
		int[] token;
		while (tokens.hasMoreTokens()) {
			token = tokens.nextToken();
			if (token.length == 1 && Idna.isDelimiter(token[0])) {
				output += Idna.ACE_DELIMITER;
			}
			else {output += new String(encode(token));}
		}

		return output.toCharArray();
	}


	/**
	 * Decode an entire domain using the Ace algorithm.
	 * @param input A char array representing an encoded domain name
	 * @return An int array with unicode codepooints
	 * @throws XcodeException when the input is null or empty
	 */
	public int[] domainDecode(char[] input) throws XcodeException {
		if (input == null) {throw XcodeError.NULL_ARGUMENT();}
		if (input.length == 0) {throw XcodeError.EMPTY_ARGUMENT();}

		UnicodeSequence output = new UnicodeSequence();
		StringTokenizer tokens = new StringTokenizer(new String(input),Idna.DELIMITERS,true);
		String token;
		while (tokens.hasMoreTokens()) {
			token = tokens.nextToken();
			if (Idna.isDelimiter(token)) {output.append(token.charAt(0));}
			else {output.append(decode(token.toCharArray()));}
		}

		return output.get();
	}


	/**
	 * Encode a single domain label using the Ace algorithm.
	 * @param input An int array representing a domain name
	 * @return A char array with Ace encoded domain name
	 * @throws XcodeException when the input is null or empty or
	 *        the input contains non-standard 3
	 *        ASCII character or the encoded domain
	 *        name is empty or longer than 63.
	 */
	public char[] encode(int[] input) throws XcodeException {
		if (input == null) {throw XcodeError.NULL_ARGUMENT();}
		if (input.length == 0) {throw XcodeError.EMPTY_ARGUMENT();}

		// idna draft:  ToAscii Step #3
		if (useStd3AsciiRules && ! Utf16.isStd3Ascii(input)) {
			throw XcodeError.ACE_ENCODE_NOT_STD3ASCII();
		}

		// idna draft:  ToAscii Step #4
		if (Utf16.isAscii(input)) {
			return Utf16.contract(input);
		}

		// idna draft:  ToAscii Step #5        <update author=JJC date=2003.09.25/>
		if (hasPrefix(input)) {
			throw XcodeError.ACE_ENCODE_PREFIX_FOUND();
		}

		// idna draft:  ToAscii Step #6,7
		return new String(prefix + new String(internalEncode(input))).toCharArray();
	}

	abstract protected char[] internalEncode(int[] input) throws XcodeException;


	/**
	 * Decode a single domain label using the Ace algorithm.
	 * @param input A char array representing an encoded domain name
	 * @return An int array with unicode codepooints
	 * @throws XcodeException when the input is null or empty
	 */
	public int[] decode(char[] input) throws XcodeException {
		if (input == null) {throw XcodeError.NULL_ARGUMENT();}
		if (input.length == 0) {throw XcodeError.EMPTY_ARGUMENT();}

		// Decarations
		int[] output = null;
		String inputString = new String(input);

		// idna draft:  ToUnicode Step #3-5
		if ((input.length > prefix.length()) &&
			(inputString.substring(0,prefix.length()).equalsIgnoreCase(prefix))) {
			input = inputString.substring(prefix.length()).toCharArray();
			output = internalDecode(input);
		} else {
			output = Utf16.expand(input);
		}
		if (useStd3AsciiRules && ! Utf16.isStd3Ascii(output)) {
			throw XcodeError.ACE_DECODE_NOT_STD3ASCII();
		}

		return output;
	}

	abstract protected int[] internalDecode(char[] input) throws XcodeException;

	private boolean hasPrefix(int[] input) {
		int length = prefix.length();
		if (input.length < length) {return false;}
		int[] input32 = new int[length];
		System.arraycopy(input,0,input32,0,length);
		String input16 = new String(Utf16.contract(input32));
		return prefix.equalsIgnoreCase(input16);
	}

}
