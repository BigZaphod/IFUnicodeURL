/*************************************************************************/
/*                                                                       */
/* EncodingVariantsTest.java                                             */
/*                                                                       */
/* (c) VeriSign Inc., 2000-2003, All rights reserved                     */
/*                                                                       */
/* @author: John Colosi                                                  */
/* @date: January, 2003                                                  */
/*                                                                       */
/*************************************************************************/


package com.vgrs.xcode.ext.test;

import java.io.*;
import java.util.*;
import com.vgrs.xcode.ext.*;
import com.vgrs.xcode.util.*;
import com.vgrs.xcode.idna.*;
import com.vgrs.xcode.common.*;

public class EncodingVariantsTest {

	static public EncodingVariants ev;

	static public void usage() {
		System.out.println("usage: java EncodingVariantsTest [-r] <file> <encoding list>");
		System.out.println(" -r => use Race for ACE encoding (Punycode by default)");
	}

	static public void main(String args[]) {
		if (args.length < 1) {usage();return;}

		int i = 0;

		if (args[0].equals("-r")) {ev = new EncodingVariants(new Race()); i++;}
		else { ev = new EncodingVariants(new Punycode()); }

		File infile = new File(args[i++]);

		if (args.length == i) {
			testExecute(infile,null);
		} else {
			String[] encodings = new String[args.length - i];
			System.arraycopy(args,i,encodings,0,encodings.length);
			testExecute(infile,encodings);
		}
	}

	static public void testExecute(File input, String[] encodings) {
		try {
			Iterator data = Datafile.getIterator(input);
			while (data.hasNext()) {testExecute((String)data.next(),encodings);}
		} catch (Exception x) {x.printStackTrace();}
	}

	static public void testExecute(String input, String[] encodings)
	throws XcodeException {
		String[] output = null;

		if (input == null) {
			Debug.pass("");
			return;
		}
		input = input.trim();

		if (input.length() == 0 || input.charAt(0) == '#') {
			Debug.pass(input);
			return;
		}

		try {output = ev.execute(input,encodings);}
		catch (XcodeException x) {
			Debug.fail(input+"	ERROR:"+x.getCode()+"	"+x.getMessage());
			return;
		}

		if (output == null || output.length == 0) {
			Debug.pass(input+" -> ");
		}
		else if (output.length == 1) {
			Debug.pass(input+" -> "+output[0]);
		}
		else {
			System.out.println("\n"+input);
			for (int i=0; i<output.length; i++) {
				Debug.pass("-> "+output[i]);
			}
			System.out.println();
		}
	}

}
