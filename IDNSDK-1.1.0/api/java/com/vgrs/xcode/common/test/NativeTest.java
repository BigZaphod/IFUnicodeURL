/*************************************************************************/
/*                                                                       */
/* NativeTest.java                                                       */
/*                                                                       */
/* (c) VeriSign Inc., 2000-2003, All rights reserved                     */
/*                                                                       */
/* @author: John Colosi                                                  */
/* @date: January, 2003                                                  */
/*                                                                       */
/*************************************************************************/

package com.vgrs.xcode.common.test;

import java.io.*;
import java.util.*;
import com.vgrs.xcode.util.*;
import com.vgrs.xcode.common.*;


/**
 * Because of the nature of Java's support for Native Encodings, the round
 * trip checking must be done deep inside the code.  It does not, therefore
 * need to take place here in the test driver.  The encode and decode methods
 * here just make one call, because the answer is guaranteed to be reliable.
 */
public class NativeTest {

	static public void usage() {
		System.out.println("usage: java NativeTest (encode | decode) <file> <encoding list>");
	}

	static public void main(String args[]) {
		if (args.length < 2) {usage();return;}

		String[] encodings = null;
		if (args.length > 2) {
			encodings = new String[args.length-2];
			System.arraycopy(args,2,encodings,0,encodings.length);
		}

		if (args[0].equalsIgnoreCase("encode"))
		{testEncode(new File(args[1]),encodings);}
		else if (args[0].equalsIgnoreCase("decode"))
		{testDecode(new File(args[1]),encodings);}
		else {usage(); return;}
	}


	static public void testEncode(File input, String[] encodings) {
		try {
			Iterator data = Datafile.getIterator(input);
			if (encodings != null && encodings.length==1) {
				while (data.hasNext()) {testEncode((String)data.next(), encodings[0]);}
			} else {
				while (data.hasNext()) {testEncode((String)data.next(), encodings);}
			}
		} catch (Exception x) {x.printStackTrace();}
	}

	static public void testDecode(File input, String[] encodings) {
		try {
			Iterator data = Datafile.getIterator(input);
			if (encodings != null && encodings.length==1) {
				while (data.hasNext()) {testDecode((String)data.next(), encodings[0]);}
			} else {
				while (data.hasNext()) {testDecode((String)data.next(), encodings);}
			}
		} catch (Exception x) {x.printStackTrace();}
	}


	static public void testEncode(String input, String encoding)
	throws XcodeException {
		String inputdecoded = null;
		String output = null;
		String variant = null;

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
			inputdecoded = new String(Hex.decodeChars(input));
			output = Native.encode(inputdecoded,encoding);
		} catch (XcodeException x) {
			Debug.fail(input+"	ERROR:"+x.getCode()+"	"+x.getMessage());
			return;
		}

		System.out.println(Hex.encode(output.toCharArray()));
	}


	static public void testEncode(String input, String[] encodings)
	throws XcodeException {
		String inputdecoded = null;
		HashMap output = null;
		String variant = null;

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
			inputdecoded = new String(Hex.decodeChars(input));
			if (encodings == null || encodings.length == 0) {
				output = Native.encode(inputdecoded);
			} else {
				output = Native.encode(inputdecoded,encodings);
			}
		} catch (XcodeException x) {
			Debug.fail(input+"	ERROR:"+x.getCode()+"	"+x.getMessage());
			return;
		}

		System.out.println(input);
		Iterator i = output.keySet().iterator();
		String encoding = null;
		while (i.hasNext()) {
			encoding = (String)i.next();
			variant = (String)output.get(encoding);
			System.out.println("	"+encoding+"	"+Hex.encode(variant.toCharArray()));
		}
		System.out.println(output.size());
	}


	static public void testDecode(String input, String encoding)
	throws XcodeException {
		byte[] inputarray = null;
		String output = null;
		String variant = null;

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
			inputarray = Hex.decodeBytes(input);
			output = Native.decode(inputarray,encoding);
		} catch (XcodeException x) {
			Debug.fail(input+"	ERROR:"+x.getCode()+"	"+x.getMessage());
			return;
		}
		System.out.println(Hex.encode(output.toCharArray()));
	}


	static public void testDecode(String input, String[] encodings)
	throws XcodeException {
		byte[] inputarray = null;
		HashMap output = null;
		String variant = null;

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
			inputarray = Hex.decodeBytes(input);
			if (encodings == null || encodings.length == 0) {
				output = Native.decode(inputarray);
			} else {
				output = Native.decode(inputarray,encodings);
			}
		} catch (XcodeException x) {
			Debug.fail(input+"	ERROR:"+x.getCode()+"	"+x.getMessage());
			return;
		}

		System.out.println(input);
		Iterator i = output.keySet().iterator();
		String encoding = null;
		while (i.hasNext()) {
			encoding = (String)i.next();
			variant = (String)output.get(encoding);
			System.out.println("	"+encoding+"	"+Hex.encode(variant.toCharArray()));
		}
		System.out.println(output.size());
	}

}
