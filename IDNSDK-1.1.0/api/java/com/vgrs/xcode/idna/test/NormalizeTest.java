/*************************************************************************/
/*                                                                       */
/* NormalizeTest.java                                                    */
/*                                                                       */
/* (c) VeriSign Inc., 2000-2003, All rights reserved                     */
/*                                                                       */
/* @author: John Colosi                                                  */
/* @date: February, 2003                                                 */
/*                                                                       */
/*************************************************************************/

package com.vgrs.xcode.idna.test;

import java.io.*;
import java.util.*;
import com.vgrs.xcode.util.*;
import com.vgrs.xcode.idna.*;
import com.vgrs.xcode.common.*;


public class NormalizeTest {

	static public void usage() {
		System.out.println("usage: java NormalizeTest [-c] <file>");
	}


	static public void main(String args[]) {
		if (args.length < 1) {usage();return;}

		if (args.length == 1) {
			testExecute(new File(args[0]));
		}
		else if (args.length == 2 && args[0].equals("-c")) {
			testConformance(new File(args[1]));
		}
		else {
			usage();
			return;
		}
	}


	static public void testExecute(File input) {
		try {
			Iterator data = Datafile.getIterator(input);
			while (data.hasNext()) {testExecute((String)data.next());}
		} catch (Exception x) {x.printStackTrace();}
	}


	static public void testExecute(String input) throws XcodeException {
		int[] output = null;

		if (input == null) {
			Debug.pass("");
			return;
		}
		input = input.trim();

		if (input.length() == 0 || input.charAt(0) == '#') {
			Debug.pass(input);
			return;
		}

		try {
			output = Normalize.execute(Hex.decodeInts(input));
		} catch (XcodeException x) {
			Debug.fail(input+"	ERROR:"+x.getCode()+"	"+x.getMessage());
			return;
		}

		Debug.pass(Hex.encode(output));
	}



	static public void testConformance(File input) {
		try {
			Iterator data = Datafile.getIterator(input);
			while (data.hasNext()) {testConformance((String)data.next());}
		} catch (Exception x) {x.printStackTrace();}
	}


	static public void testConformance(String input) throws XcodeException {

		if (input == null) {
			Debug.pass("");
			return;
		}
		input = input.trim();
		int hashIndex = input.indexOf('#');
		if (hashIndex > 0) {
			input = input.substring(0, hashIndex);
			input = input.trim();
		}

		if ( input.length() == 0
			|| input.charAt(0) == '#'
		|| input.charAt(0) == '@')
		{
			// Debug.pass(input);
			return;
		}

		//
		// parse the data into columns
		//
		StringTokenizer st = new StringTokenizer(input, ";");
		int tokenCount = st.countTokens();
		if (tokenCount < 5) {
			Debug.fail(input + "	ERROR: Invalid number of columns");
			return;
		}
		int index = 0;
		String[] column = new String[tokenCount];
		while (st.hasMoreTokens()) {
			column[index++] = st.nextToken();
		}

		//
		// parse first 5 columns from hex format
		//
		int[][] c = new int[5][];
		try {
			for (index = 0; index < 5; index++) {
				c[index] = Hex.decodeInts(column[index]);
			}
		}
		catch (XcodeException x) {
			Debug.fail(input+"	ERROR:["+column[index]+"]"+x.getCode()+"	"+x.getMessage());
			return;
		}

		try {
			//
			// ensure conformance for NFKC
			// c4 == NFKC(c1) == NFKC(c2) == NFKC(c3) == NFKC(c4) == NFKC(c5)
			//
			for (int i = 0; i < 5; i++) {
				int[] nfkc = Normalize.execute(getCopy(c[i]));
				if ( ! Arrays.equals(c[3], nfkc)) {
					Debug.fail(input
						+ "	ERROR: conformance test failed: col[4] = '"
						+ Hex.encode(c[3]) + "': col[" + (i+1) + "] = '" + Hex.encode(c[i])
					+ "': NFKC[" + (i+1) + "] = '" + Hex.encode(nfkc) + "'");
				}
			}

		}
		catch (XcodeException x) {
			Debug.fail(input+"	ERROR:"+x.getCode()+"	"+x.getMessage());
			return;
		}

	}

	static private int[] getCopy(int[] arr) {
		int[] copy = new int[arr.length];
		System.arraycopy(arr, 0, copy, 0, arr.length);
		return copy;
	}

}
