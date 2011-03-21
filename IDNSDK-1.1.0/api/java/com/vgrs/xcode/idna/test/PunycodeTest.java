/*************************************************************************/
/*                                                                       */
/* PunycodeTest.java                                                     */
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


public class PunycodeTest {

	static private Punycode punycode;

	static public void usage() {
		System.out.println("usage: java PunycodeTest [-3] (encode | decode) <file>");
		System.out.println(" -3 => do NOT enforce Std 3 Ascii Rules");
	}

	static public void main(String args[]) {
		if (args.length < 2) {usage();return;}

		int i = 0;
		try {
			if (args[0].equalsIgnoreCase("-3")) {
				punycode = new Punycode(false);i++;
				if (args.length < 3) {usage();return;}
			} else {
				punycode = new Punycode(true);
			}
		} catch (Exception x) {x.printStackTrace();}

		if (args[i].equalsIgnoreCase("encode"))
		{testEncode(new File(args[i+1]));}
		else if (args[i].equalsIgnoreCase("decode"))
		{testDecode(new File(args[i+1]));}
		else {usage(); return;}
	}


	static public void testEncode(File input) {
		try {
			Iterator data = Datafile.getIterator(input);
			while (data.hasNext()) {testEncode((String)data.next());}
		} catch (Exception x) {x.printStackTrace();}
	}

	static public void testDecode(File input) {
		try {
			Iterator data = Datafile.getIterator(input);
			while (data.hasNext()) {testDecode((String)data.next());}
		} catch (Exception x) {x.printStackTrace();}
	}


	static public void testEncode(String input) throws XcodeException {
		int[] inputarray = null;
		char[] output = null;
		int[] check = null;

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
			inputarray = Hex.decodeInts(input);
			output = punycode.domainEncode(inputarray);
		} catch (XcodeException x) {
			Debug.fail(input+"	ERROR:"+x.getCode()+"	"+x.getMessage());
			return;
		}

		try {check = punycode.domainDecode(output);}
		catch (XcodeException x) {
			Debug.fail(input+"	FATAL:"+x.getCode()+"	"+x.getMessage());
			return;
		}

		/*
		 * During ace encode, all delimiters will be converted to the ascii
		 * fullstop.  This same replacement must be done before applying the
		 * round-trip check to avoid false MISMATCH based on a valid delimiter
		 * replacement.
		 */
		for (int i=0; i<inputarray.length; i++) {
			for (int j=1; j<Idna.INT_DELIMITERS.length; j++) {
				if (inputarray[i] == Idna.INT_DELIMITERS[j]) {
					inputarray[i] = Idna.ACE_DELIMITER;
				}
			}
		}

		if (! Arrays.equals(inputarray,check)) {
			Debug.fail(input+"	MISMATCH	"+Hex.encode(check));
			return;
		}

		Debug.pass(new String(output));
	}


	static public void testDecode(String input) throws XcodeException {
		char[] inputarray = null;
		int[] output = null;
		char[] check = null;

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
			inputarray = input.toCharArray();
			output = punycode.domainDecode(inputarray);
		} catch (XcodeException x) {
			Debug.fail(input+"	ERROR:"+x.getCode()+"	"+x.getMessage());
			return;
		}

		try {check = punycode.domainEncode(output);}
		catch (XcodeException x) {
			Debug.fail(input+"	FATAL:"+x.getCode()+"	"+x.getMessage());
			return;
		}

		if (Utf16.isDnsCompatible(inputarray) &&
			! input.equalsIgnoreCase(new String(check))) {
			Debug.fail(input+" MISMATCH "+new String(check));
			return;
		}

		Debug.pass(Hex.encode(output));
	}

}
