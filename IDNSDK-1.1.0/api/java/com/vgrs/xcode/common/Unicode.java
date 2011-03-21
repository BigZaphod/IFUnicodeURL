/*************************************************************************/
/*                                                                       */
/* Unicode.java                                                          */
/*                                                                       */
/* (c) VeriSign Inc., 2000-2003, All rights reserved                     */
/*                                                                       */
/* @author: John Colosi                                                  */
/* @date: January, 2003                                                  */
/*                                                                       */
/*************************************************************************/

package com.vgrs.xcode.common;

import java.io.*;
import java.util.*;
import com.vgrs.xcode.util.*;

/**
 * A class that provides algorithms to encode/decode a UTF 16
 * to/from unicode.
 */

public class Unicode {

	/**
	 * This is the maximum value a Unicode codepoint can assume.
	 * <br>This variable is hard-coded with the value 0x10ffff.
	 */
	static public final int MAX = 0x10ffff;

	/**
	 * This is the minumum value a Unicode codepoint can assume.
	 * <br>This variable is hard-coded with the value 0.
	 */
	static public final int MIN = 0;

	/**
	 * Encode a character sequence to an array of unicode
	 *
	 * @param input a character sequence
	 * @return array of unicode
	 * @throws XcodeException if the input array is null or empty
	 */
	static public int[] encode(char[] input) throws XcodeException {
		if (input == null) {throw XcodeError.NULL_ARGUMENT();}
		if (input.length == 0) {throw XcodeError.EMPTY_ARGUMENT();}

		int[] tmp = new int[input.length];
		int[] output;
		int output_offset = 0;

		for (int i=0; i<input.length; i++) {
			if (i+1 < input.length &&
				Utf16.isHighSurrogate(input[i]) &&
				Utf16.isLowSurrogate(input[i+1])) {
				tmp[output_offset++] = encode(input[i],input[i+1]);
				i++;
			} else {
				tmp[output_offset++] = (int)input[i];
			}
		}

		if (output_offset != tmp.length) {
			output = new int[output_offset];
			System.arraycopy(tmp,0,output,0,output_offset);
			//Debug.log("  copied ["+tmp.length+"] to ["+output_offset+"]");
		} else {
			output = tmp;
			//Debug.log("  no copy ["+tmp.length+"] = ["+output_offset+"]");
		}

		return output;
	}


	/**
	 * Decode a sequence of unicode to a character sequence
	 *
	 * @param input array of unicode
	 * @return a character sequence
	 * @throws XcodeException if the array of unicode is null or empty
	 */

	static public char[] decode(int[] input) throws XcodeException {
		if (input == null) {throw XcodeError.NULL_ARGUMENT();}
		if (input.length == 0) {throw XcodeError.EMPTY_ARGUMENT();}

		char[] tmp = new char[input.length * 2];
		char[] output;
		char[] surrogate;
		int output_offset = 0;

		for (int i=0; i<input.length; i++) {
			if (input[i] > 0xFFFF) {
				surrogate = decode(input[i]);
				tmp[output_offset++] = surrogate[0];
				tmp[output_offset++] = surrogate[1];
			} else {

				/*
				 * I am removing this check for a valid surrogate pair.  In theory, passing
				 * a decomposed surrogate pair into a method that is decomposing surrogate
				 * pairs is a bit cheeky.  In practice however, it may be common to have
				 * some data in an int[] that you're not sure about.  I'll allow this to
				 * improve user experience, even though it does break the round trip check
				 * of which I do not approve.
				if (i+1 < input.length &&
					Utf16.isHighSurrogate((char)input[i]) &&
					Utf16.isLowSurrogate((char)input[i+1])) {
					throw XcodeError.UNICODE_SURROGATE_DECODE_ATTEMPTED();
				}
				 */

				tmp[output_offset++] = (char)input[i];
			}
		}

		if (output_offset != tmp.length) {
			output = new char[output_offset];
			System.arraycopy(tmp,0,output,0,output_offset);
			//Debug.log("  copied ["+tmp.length+"] to ["+output_offset+"]");
		} else {
			output = tmp;
			//Debug.log("  no copy ["+tmp.length+"] = ["+output_offset+"]");
		}

		return output;
	}


	/**
	 * Convert two characters to one unicode.
	 *
	 * @param high The character serves as high 8 bits of a unicode
	 * @param low The character serves as low 8 bits of a unicode
	 * @return a unicode
	 */
	static public int encode(char high, char low) {
		return (int)(((high - 0xd800) << 10) + (low - 0xdc00) + 0x10000);
	}

	/**
	 * Convert one unicode to two characters
	 *
	 * @param input a unicode value
	 * @return A surrogate pair comprised of two 16-bit surrogate values.
	 */
	static public char[] decode(int input) throws XcodeException {
		if (input < 0x10000 || input > 0x10ffff) {
			throw XcodeError.UNICODE_DECODE_INVALID_VALUE(" "+Integer.toString(input,16));
		}
		char[] surrogate = new char[2];
		surrogate[0] = (char)(((input - 0x10000) >> 10) + 0xd800);
		surrogate[1] = (char)(((input - 0x10000) & 0x3ff) + 0xdc00);
		return surrogate;
	}



	/**
	 * Assert that the specified values are Unicode codepoints
	 * @param input An array of potential unicode values
	 * @throws XcodeException if any input value is not a Unicode codepoint.
	 */
	static public void isValid(int[] input) throws XcodeException {
		for (int i=0; i<input.length; i++) {isValid(input[i]);}
	}

	/**
	 * Assert that the specified value is a Unicode codepoint
	 * @param input A potential unicode value
	 * @throws XcodeException if the input is not a Unicode codepoint.
	 */
	static public void isValid(int input) throws XcodeException {
		if (input > MAX || input < MIN) {
			throw XcodeError.UNICODE_INVALID_VALUE(" "+Integer.toString(input,16));
		}
	}

}
