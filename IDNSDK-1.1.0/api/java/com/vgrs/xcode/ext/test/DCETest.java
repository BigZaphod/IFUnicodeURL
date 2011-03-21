/*************************************************************************/
/*                                                                       */
/* DCETest.java                                                          */
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
import com.vgrs.xcode.common.*;

public class DCETest {

	static public void usage() {
		System.out.println("usage: java DCETest (encode | decode) <file>");
	}

	static public void main(String args[]) {
		if (args.length != 2) {usage();return;}

		if (args[0].equalsIgnoreCase("encode"))
		{testEncode(new File(args[1]));}
		else if (args[0].equalsIgnoreCase("decode"))
		{testDecode(new File(args[1]));}
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
		byte[] inputarray = null;
		char[] output = null;
		byte[] check = null;

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
			output = DCE.encode(inputarray);
		} catch (XcodeException x) {
			Debug.fail(input+"	ERROR:"+x.getCode()+"	"+x.getMessage());
			return;
		}

		try {check = DCE.decode(output);}
		catch (XcodeException x) {
			Debug.fail(input+" FATAL:"+x.getCode()+" "+x.getMessage());
			return;
		}

		if (! Arrays.equals(inputarray,check)) {
			Debug.fail(input+" MISMATCH "+Hex.encode(check));
			return;
		}

		Debug.pass(new String(output));
	}


	static public void testDecode(String input) throws XcodeException {
		char[] inputarray = null;
		byte[] output = null;
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
			output = DCE.decode(inputarray);
		} catch (XcodeException x) {
			Debug.fail(input+"	ERROR:"+x.getCode()+"	"+x.getMessage());
			return;
		}

		try {check = DCE.encode(output);}
		catch (XcodeException x) {
			Debug.fail(input+" FATAL:"+x.getCode()+" "+x.getMessage());
			return;
		}

		if (! input.equalsIgnoreCase(new String(check))) {
			Debug.fail(input+"	MISMATCH	"+new String(check));
			return;
		}

		Debug.pass(Hex.encode(output));
	}

}
