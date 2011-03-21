/*************************************************************************/
/*                                                                       */
/* UnicodeTokenizer.java                                                 */
/*                                                                       */
/* (c) VeriSign Inc., 2000-2003, All rights reserved                     */
/*                                                                       */
/* @author: John Colosi                                                  */
/* @date: February, 2003                                                 */
/*                                                                       */
/*************************************************************************/

package com.vgrs.xcode.common;

import java.io.*;
import java.util.*;

/**
 * Allows tokenization of an array of integer primitives.  Functionality
 * emulates the StringTokenizer object.
 */
public class UnicodeTokenizer {

	private Enumeration results;

	/**
	 * Construct a unicode tokenizer for the specified unicode.
	 * @param input a unicode to be parsed
	 * @param delimiters delimiters
	 * @param returnDelims flag to indicate if to return delimiter as token
	 */
	public UnicodeTokenizer(int[] input, int[] delimiters, boolean returnDelims) {
		Vector tmpResults = new Vector();

		int len = 0;
		int[] token;
		for (int i=0; i<input.length; i++) {
			if(exists(input[i],delimiters)) {
				if (len > 0) {
					token = new int[len];
					System.arraycopy(input,i-len,token,0,len);
					tmpResults.add(token);
				}
				if (returnDelims) {
					token = new int[1];
					token[0] = input[i];
					tmpResults.add(token);
				}
				len = 0;
			} else {
				len++;
			}
		}
		if (len > 0) {
			token = new int[len];
			System.arraycopy(input,input.length-len,token,0,len);
			tmpResults.add(token);
		}
		this.results = tmpResults.elements();
	}

	static private boolean exists(int item, int[] list) {
		for (int i=0; i<list.length; i++) {
			if (list[i] == item) {return true;}
		}
		return false;
	}

	/**
	 * Emulate StringTokenizer's hasMoreElements method
	 * @see StringTokenizer#hasMoreElements
	 */
	public boolean hasMoreTokens() {return results.hasMoreElements();}
	/**
	 * Emulate StringTokenizer's nextElement method
	 * @see StringTokenizer#nextElement
	 */
	public int[] nextToken() {return (int[])results.nextElement();}

}
