/*************************************************************************/
/*                                                                       */
/* BidiTest.java                                                         */
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


public class BidiTest {

	static private Bidi bidi;

	static public void usage() {
		System.out.println("usage: java BidiTest [-p] <file>");
		System.out.println(" -p => apply the Bidi prohibition step");
	}

	static public void main(String args[]) {
		if (args.length < 1) {usage();return;}

		int i = 0;
		try {
			if (args[0].equalsIgnoreCase("-p")) {
				bidi = new Bidi(true);
				i++;
			}
			else if (args[0].equalsIgnoreCase("-t")) {
				bidi = new Bidi(true);
				bidi.show();
				return;
			}
			else {bidi = new Bidi(false);}
		} catch (Exception x) {x.printStackTrace();}

		testExecute(new File(args[i]));
	}


	static public void testExecute(File input) {
		try {
			Iterator data = Datafile.getIterator(input);
			while (data.hasNext()) {testExecute((String)data.next());}
		} catch (Exception x) {x.printStackTrace();}
	}


	static public void testExecute(String input) throws XcodeException {
		if (input == null) {
			Debug.pass("");
			return;
		}
		input = input.trim();

		if (input.length() == 0 || input.charAt(0) == '#') {
			Debug.pass(input);
			return;
		}

		try {bidi.test(Hex.decodeInts(input));}
		catch (XcodeException x) {
			Debug.fail(input+"	ERROR:"+x.getCode()+"	"+x.getMessage());
			return;
		}

		Debug.pass(input);
	}

}
