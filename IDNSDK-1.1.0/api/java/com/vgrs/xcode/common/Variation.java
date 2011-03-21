/*************************************************************************/
/*                                                                       */
/* Variation.java                                                        */
/*                                                                       */
/* (c) VeriSign Inc., 2000-2003, All rights reserved                     */
/*                                                                       */
/* @author: John Colosi                                                  */
/* @date: June, 2002                                                     */
/*                                                                       */
/*************************************************************************/

package com.vgrs.xcode.common;

import java.util.*;
import com.vgrs.xcode.util.*;

/**
 * Recursively constructs variations of a String object by varying each
 * character and then appending the result of further variations.
 */
abstract public class Variation {

	/**
	 * Generate variants for an input charater
	 * The first character in the output array is the input character.
	 * @param input a character
	 * @throws XcodeException if the input charater is not a TC/SC
	 */
	abstract public char[] execute(char input) throws XcodeException;

	/**
	 * Generate variants for an input String
	 * The first sequence in the output array is the input String.
	 * @param input a String
	 * @throws XcodeException if the input String is null or empty String or
	 *								if the input String does stands for a TC/SC
	 */
	public String[] execute(String input) throws XcodeException {

		// Input checking
		if (input == null) {throw XcodeError.NULL_ARGUMENT();}
		if (input.length() == 0) {throw XcodeError.EMPTY_ARGUMENT();}

		char[] variant = execute(input.charAt(0));
		String[] result;

		if (input.length() > 1) {
			String[] tmp = execute(input.substring(1));
			result = new String[tmp.length * variant.length];
			for (int i=0; i<tmp.length; i++) {
				for (int j=0; j<variant.length; j++) {
					result[(i*variant.length)+j] = variant[j] + tmp[i];
				}
			}
		} else {
			result = new String[variant.length];
			for (int i=0; i<variant.length; i++) {
				result[i] = new String(""+variant[i]);
			}
		}

		return result;
	}

	/**
	 * Generate variants array for an input String array
	 * @param input a String array
	 * @return a String array for the input array
	 * @throws XcodeException if the input String is null or empty String or
	 *								if the input String does stands for a TC/SC
	 */
	public String[] execute(String[] input) throws XcodeException {
		if (input == null) {throw XcodeError.NULL_ARGUMENT();}
		if (input.length == 0) {throw XcodeError.EMPTY_ARGUMENT();}

		Vector variants = new Vector();
		String[] output;

		for (int i=0; i<input.length; i++) {
			try {
				output = execute(input[i]);
				for (int j=0; j<output.length; j++) {
					variants.add(output[j]);
				}
			} catch (XcodeException x) {} // No variants exist for input[i]
		}
		output = (String[])variants.toArray(new String[variants.size()]);
		return output;
	}

}
