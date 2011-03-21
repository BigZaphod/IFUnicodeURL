/*************************************************************************/
/*                                                                       */
/* Debug.java                                                            */
/*                                                                       */
/* (c) VeriSign Inc., 2000-2003, All rights reserved                     */
/*                                                                       */
/* @author: John Colosi                                                  */
/* @date: June, 2002                                                     */
/*                                                                       */
/*************************************************************************/

package com.vgrs.xcode.util;

import java.io.*;
import java.util.*;

/**
 * Log debugging information to System.out/System.err.
 */
public class Debug {

	/**
	 * Log information to Standard Error.  This method can be used to display
	 * debug information during development.
	 * @param input A message to log
	 */
	static public void log(String input) {
		System.err.println(input);
	}

	/**
	 * Log information to Standard Out.  This method is commonly used in test
	 * routines to display output after a successful execution.  Input is not
	 * modified.
	 * @param input A message to log
	 */
	static public void pass(String input) {
		System.out.println(input);
	}

	/**
	 * Log information to Standard Error.  This method is commonly used in test
	 * routines to display output after a failed execution.  A "Number Sign" (#)
	 * character is prefixed to the input.  This facilitates sequenced executions
	 * on the command-line.  For instance, users can use the "race" command to
	 * decode an input file, and write the contents out to an output file.  This
	 * entire output file can then be fed into the "idna" routine.  Records
	 * which failed the original race decode will be ignored by the idna routine
	 * because they have been prefixed with a "#" character.
	 * @param input A message to log
	 */
	static public void fail(String input) {
		System.err.println("#	"+input);
	}

}
