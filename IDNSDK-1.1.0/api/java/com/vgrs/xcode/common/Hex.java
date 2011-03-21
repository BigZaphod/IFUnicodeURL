/*************************************************************************/
/*                                                                       */
/* Hex.java                                                              */
/*                                                                       */
/* (c) VeriSign Inc., 2000-2003, All rights reserved                     */
/*                                                                       */
/* @author: John Colosi                                                  */
/* @date: June, 2002                                                     */
/*                                                                       */
/*************************************************************************/

package com.vgrs.xcode.common;

import java.io.*;
import java.util.*;
import com.vgrs.xcode.util.*;

/**
 * Base 16 or Hexadecimal is often represented using the digits 0-9 as well
 * as the letters a-f.  The decode methods in this class interpret this
 * Hexadecimal notation, converting into usable data structures.  The encode
 * methods perform the opposite function, representing internal data using
 * Hexadecimal notation.
 */
public final class Hex {

	/**
	 * Return the Hexadecimal representation of the input sequence.
	 * @param input Sequence of 8 bit bytes
	 * @return Sets of two characters separated by a space.
	 *		     Each group represents a single byte from the
	 * 		  input stream using hex notation.
	 * @throws XcodeException if the input is null or with length == 0
	 */
	static public String encode(byte[] input) throws XcodeException {
		if (input == null) {throw XcodeError.NULL_ARGUMENT();}
		if (input.length == 0) {throw XcodeError.EMPTY_ARGUMENT();}

		String output = Integer.toString(((char)input[0])&0xff,16);
		for (int i=1; i<input.length; i++) {
			output += " "+Integer.toString(((char)input[i])&0xff,16);
		}
		return output;
	}

	/**
	 * Return the Hexadecimal representation of the input sequence.
	 * @param input Sequence of 16 bit characters in UTF16 format
	 * @return Sets of four characters separated by a space.
	 *		  Each group represents a single character from
	 *		  the input stream using hex notation.
	 * @throws XcodeException if the input is null or with length == 0
	 */
	static public String encode(char[] input) throws XcodeException {
		if (input == null) {throw XcodeError.NULL_ARGUMENT();}
		if (input.length == 0) {throw XcodeError.EMPTY_ARGUMENT();}

		String output = Integer.toString(input[0],16);
		for (int i=1; i<input.length; i++) {
			output += " "+Integer.toString(input[i],16);
		}
		return output;
	}

	/**
	 * Return the Hexadecimal representation of the input sequence.
	 * @param input Sequence of integers representing Unicode characters.
	 * @return Grouped characters separated by a space.  Each group
	 *	     represents a single Unicode character from the input
	 *		  stream using hex notation.
	 * @throws XcodeException if the input is null or with length == 0
	 */
	static public String encode(int[] input) throws XcodeException {
		if (input == null) {throw XcodeError.NULL_ARGUMENT();}
		if (input.length == 0) {throw XcodeError.EMPTY_ARGUMENT();}

		String output = Integer.toString(input[0],16);
		for (int i=1; i<input.length; i++) {
			output += " "+Integer.toString(input[i],16);
		}
		return output;
	}


	/**
	 * Return the data structure represented by the Hexadecimal input sequence.
	 * @param input Grouped characters separated by a space.  Each group
	 *        represents a single byte using hex notation.
	 * @return Each group of bytes in hex notation
	 *        is interpreted to create a new 8 bit Java
	 *        byte primitive.
	 * @throws XcodeException if the input is null or with length == 0 or
	 *                        if length of the input indicates  the hex
	 *								value is greater than 0xff
	 */
	static public byte[] decodeBytes(String input) throws XcodeException {
		if (input == null) {throw XcodeError.NULL_ARGUMENT();}
		if (input.length() == 0) {throw XcodeError.EMPTY_ARGUMENT();}

		StringTokenizer st = new StringTokenizer(input);
		byte[] output = new byte[st.countTokens()];
		int output_offset = 0;
		String token = null;

		while(st.hasMoreTokens()) {
			token = st.nextToken().toUpperCase();
			if (token.startsWith("\\U") || token.startsWith("0X")) {
				token = token.substring(2);
			}
			try {
				if (token.length() > 2) {throw XcodeError.HEX_DECODE_ONE_BYTE_EXCEEDED();}
				output[output_offset++] = (byte)Integer.parseInt(token,16);
			} catch (NumberFormatException x) {throw XcodeError.HEX_DECODE_INVALID_FORMAT();}
		}
		return output;
	}


	/**
	 * Return the data structure represented by the Hexadecimal input sequence.
	 * @param input Grouped characters separated by a space.  Each group
	 *        represents a single Utf16 character using hex notation.
	 * @return Each group of characters in hex notation
	 *         is interpreted to create a new 16 bit Java
	 *         character primitive.
	 * @throws XcodeException if the input is null or with length == 0 or
	 *                        if length of the input indicates  the hex
	 *								value is greater than 0xff
	 */
	static public char[] decodeChars(String input) throws XcodeException {
		if (input == null) {throw XcodeError.NULL_ARGUMENT();}
		if (input.length() == 0) {throw XcodeError.EMPTY_ARGUMENT();}

		StringTokenizer st = new StringTokenizer(input);
		char[] output = new char[st.countTokens()];
		int output_offset = 0;
		String token = null;

		while(st.hasMoreTokens()) {
			token = st.nextToken().toUpperCase();
			if (token.startsWith("\\U") || token.startsWith("0X")) {
				token = token.substring(2);
			}
			try {
				if (token.length() > 4) {throw XcodeError.HEX_DECODE_TWO_BYTES_EXCEEDED();}
				output[output_offset++] = (char)Integer.parseInt(token,16);
			} catch (NumberFormatException x) {throw XcodeError.HEX_DECODE_INVALID_FORMAT();}
		}
		return output;
	}


	/**
	 * Return the data structure represented by the Hexadecimal input sequence.
	 * @param input Grouped characters separated by a space.  Each group
	 *        represents a single Unicode character using hex notation.
	 * @return Each group of characters in hex notation
	 *        is interpreted as a Unicode value resulting in a 21 bit
	 *        Java int primitive.
	 * @throws XcodeException if the input is null or with length == 0 or
	 *                        if length of the input indicates  the hex
	 *								value is greater than 0xffffffff
	 */
	static public int[] decodeInts(String input) throws XcodeException {
		if (input == null) {throw XcodeError.NULL_ARGUMENT();}
		if (input.length() == 0) {throw XcodeError.EMPTY_ARGUMENT();}

		StringTokenizer st = new StringTokenizer(input);
		int[] output = new int[st.countTokens()];
		int output_offset = 0;
		String token = null;

		while(st.hasMoreTokens()) {
			token = st.nextToken().toUpperCase();
			if (token.startsWith("\\U") || token.startsWith("0X")) {
				token = token.substring(2);
			}
			try {
				if (token.length() > 8) {throw XcodeError.HEX_DECODE_FOUR_BYTES_EXCEEDED();}
				output[output_offset++] = Integer.parseInt(token,16);
			} catch (NumberFormatException x){throw XcodeError.HEX_DECODE_INVALID_FORMAT();}
		}

		return Unicode.encode(Unicode.decode(output));
	}

}
