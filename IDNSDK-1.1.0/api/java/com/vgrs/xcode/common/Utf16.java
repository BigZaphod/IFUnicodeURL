/*************************************************************************/
/*                                                                       */
/* Utf16.java                                                            */
/*                                                                       */
/* (c) VeriSign Inc., 2000-2003, All rights reserved                     */
/*                                                                       */
/* @author: John Colosi                                                  */
/* @date: August, 2002                                                   */
/*                                                                       */
/*************************************************************************/

package com.vgrs.xcode.common;

import java.io.*;
import java.util.*;

/**
 * Statically implements various operations surrounding UTF-16 codepoints.
 */
public class Utf16 {




	/**
	 * Constants
	 */
	static public final char HYPHEN = 0x002d;




	/*
	 * Surrogate arithmetic
	 */

	/**
	 * Check a character is a surrogate character
	 * @param input a character to be checked
	 */
	static public boolean isSurrogate(char input) {
		return (isHighSurrogate(input) || isLowSurrogate(input));
	}

	/**
	 * Check a character is a high surrogate character
	 * @param input a character to be checked
	 */
	static public boolean isHighSurrogate(char input) {
		return (input >= 0xd800 && input < 0xdc00);
	}

	/**
	 * Check a character is a low surrogate character
	 * @param input a character to be checked
	 */
	static public boolean isLowSurrogate(char input) {
		return (input >= 0xdc00 && input < 0xe000);
	}

	/**
	 * Get high 8 bits of a character
	 * @param input a character
	 */
	static public byte getHighByte(char input) {return (byte)(input>>8);}

	/**
	 * Get low 8 bits of a character
	 * @param input a character
	 */
	static public byte getLowByte(char input) {return (byte)(input);}




	/*
	 * Character primitive
	 */

	/**
	 * Check if a character array is an ASCII array
	 * @param c a character array
	 * @return true if all characters in the array are ASCII character, otherwise
	 *   false.
	 */
	static public boolean isAscii(char[] c) {
		for (int i=0; i<c.length; i++) {
			if (! isAscii(c[i])) {return false;}
		}
		return true;
	}

	/**
	 * Check if a character an ASCII character
	 * @param c a character
	 * @return true if the character is an ASCII character, otherwise false
	 */
	static public boolean isAscii(char c) {return (c < 0x80);}

	/**
	 * Check if all characters in an input array are all DNS compatible.
	 * Ace uses this to determine if a label must be encoded
	 * @param c character array
	 * @return true if and only if all characters in the array are DNS compatible
	 */
	static public boolean isDnsCompatible(char[] c) {
		if (c[0] == HYPHEN || c[c.length-1] == HYPHEN) {return false;}
		for (int i=0; i<c.length; i++) {
			if (! isDnsCompatible(c[i])) {return false;}
		}
		return true;
	}

	/**
	 * Check if the input character is DNS compatible.  This method returns true
	 * if the input character is a letter, digit, or hyphen.
	 * @param c a character
	 * @return true if the character is DNS compatible, otherwise false
	 */
	static public boolean isDnsCompatible(char c) {
		return ((c == 0x002D) ||
			(c >= 0x0030 && c <= 0x0039) ||
			(c >= 0x0041 && c <= 0x005A) ||
		(c >= 0x0061 && c <= 0x007A));
	}

	/**
	 * Check if an input character is standard 3 ASCII character, required
	 * by IDNA.toAscii step #3
	 * @param c a character array
	 * @return true if the characters are all standard 3 ASCII character,
	 *    otherwise false
	 */
	static public boolean isStd3Ascii(char[] c) {
		if (c[0] == HYPHEN || c[c.length-1] == HYPHEN) {return false;}
		for (int i=0; i<c.length; i++) {
			if (isAscii(c[i]) && !isDnsCompatible(c[i])) {return false;}
		}
		return true;
	}

	/**
	 * Check if all characters in an input array are printable.
	 * @param c character array
	 * @return true if all characters in the array are printable, false otherwise
	 */
	static public boolean isPrintable(char[] c) {
		for (int i=0; i<c.length; i++) {
			if (! isPrintable(c[i])) {return false;}
		}
		return true;
	}

	/**
	 * Check if an input character is printable
	 * (c > 0x1f && c < 0x7f) ||
	 * (c > 0xA0 && c < 0x100)
	 * @param c a character
	 * @return true if the character is printable, false otherwise
	 */
	static public boolean isPrintable(char c) {
		return ( (c > 0x1f && c < 0x7f) ||
		(c > 0xA0 && c < 0x100) );
	}




	/*
	 * Integer primitive
	 */

	/**
	 * Check if an int array is an ASCII array
	 * @param c an int array
	 * @return true if all ints in the array are ASCII character, otherwise
	 *         false.
	 */
	static public boolean isAscii(int[] c) {
		for (int i=0; i<c.length; i++) {
			if (! isAscii(c[i])) {return false;}
		}
		return true;
	}

	/**
	 * Check if an int is an ASCII character
	 * @param c an int
	 * @return true if the int is a ASCII character, otherwise
	 *         false.
	 */
	static public boolean isAscii(int c) {return (c < 0x80);}

	/**
	 * Check if all ints in an input array are all DNS compatible.
	 * Ace uses this to determine if a label must be encoded
	 * @param c a int array
	 * @return true if all ints in the array are DNS compatible, otherwise
	 *         false
	 */
	static public boolean isDnsCompatible(int[] c) {
		if (c[0] == HYPHEN || c[c.length-1] == HYPHEN) {return false;}
		for (int i=0; i<c.length; i++) {
			if (! isDnsCompatible(c[i])) {return false;}
		}
		return true;
	}

	/**
	 * Check if the input codepoint is DNS compatible.  This method returns true
	 * if the input character is a letter, digit, or hyphen.
	 * @param c an integer
	 * @return true if the codepoint is DNS compatible, otherwise false
	 */
	static public boolean isDnsCompatible(int c) {
		return ((c == 0x002D) ||
			(c >= 0x0030 && c <= 0x0039) ||
			(c >= 0x0041 && c <= 0x005A) ||
		(c >= 0x0061 && c <= 0x007A));
	}

	/**
	 * Check if an input int is standard 3 ASCII int, required
	 * by IDNA.toAscii step #3
	 * @param c an int
	 * @return true if the int is standard 3 ASCII character,
	 *        otherwise false
	 */
	static public boolean isStd3Ascii(int[] c) {
		if (c[0] == HYPHEN || c[c.length-1] == HYPHEN) {return false;}
		for (int i=0; i<c.length; i++) {
			if (isAscii(c[i]) && !isDnsCompatible(c[i])) {return false;}
		}
		return true;
	}

	/**
	 * Check if all integers in an input array are printable.
	 * @param c integer array
	 * @return true if all integers in the array are printable, false otherwise
	 */
	static public boolean isPrintable(int[] c) {
		for (int i=0; i<c.length; i++) {
			if (! isPrintable(c[i])) {return false;}
		}
		return true;
	}

	/**
	 * Check if an input integer is printable
	 * (c > 0x1f && c < 0x7f) ||
	 * (c > 0xA0 && c < 0x100)
	 * @param c an integer
	 * @return true if the integer is printable, false otherwise
	 */
	static public boolean isPrintable(int c) {
		return ( (c > 0x1f && c < 0x7f) ||
		(c > 0xA0 && c < 0x100) );
	}




	/*
	 * Utility resizing functions
	 */

	/**
	 * Expand a 16 bit sequence into 32 bits.  Idna toUnicode has a corner
	 * case which returns the input if there is an error.  The input is
	 * 16 bits wide and the output is 32 bits wide, so we need a convenient
	 * way to copy data from one type to the other.
	 * @param input is a character array
	 * @return output is an integer array whose low bytes match the input.
	 */
	static public int[] expand(char[] input) {
		int[] output = new int[input.length];
		for (int i=0; i<input.length; i++) {output[i] = input[i];}
		return output;
	}

	/**
	 * Contract a 32 bit sequence into 16 bits.  Useful when the input to
	 * Ace.encode does not require encoding.
	 * @param input is an integer array
	 * @return output is a char array matching low bytes of the input.
	 */
	static public char[] contract(int[] input) {
		char[] output = new char[input.length];
		for (int i=0; i<input.length; i++) {output[i] = (char)(input[i]&0xffff);}
		return output;
	}

}
