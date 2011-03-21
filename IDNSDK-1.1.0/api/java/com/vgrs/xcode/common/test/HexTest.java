/*************************************************************************/
/*                                                                       */
/* HexTest.java                                                          */
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

public class HexTest {

	static public void usage() {
		System.out.println("usage: java HexTest (encode | decode) <file>");
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
		String output = null;

		if (input == null) {
			Debug.pass("");
			return;
		}
		//input = input.trim();

		if (input.length() == 0 || input.charAt(0) == '#') {
			Debug.pass(input);
			return;
		}

		try {
			output = Hex.encode(input.toCharArray());
		} catch (XcodeException x) {
			Debug.fail(input+"	ERROR:"+x.getCode()+"	"+x.getMessage());
			return;
		}

		Debug.pass(output);
	}


	static public void testDecode(String input) throws XcodeException {
		String output = null;
		if (input == null) {
			Debug.pass("");
			return;
		}
		input = input.trim();

		if (input.length() == 0 || input.charAt(0) == '#') {
			Debug.pass(input);
			return;
		}

		try {System.out.println(new String(Hex.decodeBytes(input)));}
		catch (XcodeException x1) {
			try {System.out.println(new String(Hex.decodeChars(input)));}
			catch (XcodeException x2) {
				try {System.out.println(new String(Unicode.decode(Hex.decodeInts(input))));}
				catch (XcodeException x3) {
					Debug.fail(input+"	ERROR:"+x3.getCode()+"	"+x3.getMessage());
					return;
				}
			}
		}
	}

}
