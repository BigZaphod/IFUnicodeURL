/*************************************************************************/
/*                                                                       */
/* IdnaTest.java                                                         */
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

/*
 * Round-trip checking has been disabled because Nameprep renders it
 * unreliable.  Perhaps this checking should be enabled when Nameprep is
 * not executed.
 */
public class IdnaTest {

	static public void usage() {
		System.out.println("usage: java IdnaTest [-3acrsx] (toAscii | toUnicode) <file>");
		System.out.println(" -3 => do NOT enforce Std 3 Ascii Rules");
		System.out.println(" -a => allow unassigned codepoints (disallowed by default)");
		System.out.println(" -c => do NOT perform round-trip check during toUnicode");
		System.out.println(" -r => use Race for ACE encoding (Punycode by default)");
		System.out.println(" -x(s) => allow eXceptions during toUnicode");
	}

	private Idna idna = null;

	public IdnaTest(Idna idna) {
		this.idna = idna;
	}

	static public void main(String args[]) {
		if (args.length < 2) {usage();return;}

		Ace ace = null;
		Nameprep nameprep = null;

		boolean aceispunycode = true;
		boolean allowunassigned = false;
		boolean usestd3asciirules = true;
		boolean toUnicodeExceptionFlag = false;
		boolean toUnicodeRoundTripCheckFlag = true;

		try {
			int i = 0;

			while (args[i].charAt(0) == '-') {
				args[i] = args[i].toLowerCase();
				for (int j=1; j<args[i].length(); j++) {
					if (args[i].charAt(j) == 'r') {aceispunycode = false;}
					else if (args[i].charAt(j) == 'a') {allowunassigned = true;}
					else if (args[i].charAt(j) == '3') {usestd3asciirules = false;}
					else if (args[i].charAt(j) == 'x') {toUnicodeExceptionFlag = true;}
					else if (args[i].charAt(j) == 's') {toUnicodeExceptionFlag = true;}
					else if (args[i].charAt(j) == 'c') {toUnicodeRoundTripCheckFlag = false;}
					else {usage(); return;}
				}
				i++;
			}

			if (aceispunycode) {ace = new Punycode(usestd3asciirules);}
			else {ace = new Race(usestd3asciirules);}
			nameprep = new Nameprep(allowunassigned);

			if (args.length - i != 2) {usage();return;}

			IdnaTest idnatest = new IdnaTest(
			new Idna(ace,nameprep,toUnicodeExceptionFlag,toUnicodeRoundTripCheckFlag));

			if (args[i].equalsIgnoreCase("toascii")) {
				idnatest.testToAscii(new File(args[i+1]));
			} else if (args[i].equalsIgnoreCase("tounicode")) {
				idnatest.testToUnicode(new File(args[i+1]));
			} else { usage(); return; }
		} catch (Exception x) {x.printStackTrace();}
	}


	public void testToAscii(File input) {
		try {
			Iterator data = Datafile.getIterator(input);
			while (data.hasNext()) {testToAscii((String)data.next());}
		} catch (Exception x) {x.printStackTrace();}
	}

	public void testToUnicode(File input) {
		try {
			Iterator data = Datafile.getIterator(input);
			while (data.hasNext()) {testToUnicode((String)data.next());}
		} catch (Exception x) {x.printStackTrace();}
	}


	public void testToAscii(String input) throws XcodeException {
		int[] input_decoded = null;
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
			try {input_decoded = Hex.decodeInts(input);}
			catch (XcodeException x) {input_decoded = Unicode.encode(input.toCharArray());}
			output = idna.domainToAscii(input_decoded);
		} catch (XcodeException x) {
			Debug.fail(input+"	ERROR:"+x.getCode()+"	"+x.getMessage());
			return;
		}

		/*
		try {check = idna.toUnicode(output);}
		catch (XcodeException x) {
			Debug.fail(input+"	FATAL:"+x.getCode()+"	"+x.getMessage());
			return;
		}

		if (! Arrays.equals(input_decoded,check)) {
			Debug.fail(input+"	MISMATCH	"+Hex.encode(check));
			return;
		}
		 */

		Debug.pass(new String(output));
	}


	public void testToUnicode(String input) throws XcodeException {
		char[] input_decoded = null;
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
			try {input_decoded = Hex.decodeChars(input);}
			catch (XcodeException x) {
				try {input_decoded = Unicode.decode(Hex.decodeInts(input));}
				catch (XcodeException x2) {input_decoded = input.toCharArray();}
			}
			output = idna.domainToUnicode(input_decoded);
		} catch (XcodeException x) {
			Debug.fail(input+"	ERROR:"+x.getCode()+"	"+x.getMessage());
			return;
		}

		/*
		try {check = idna.toAscii(output);}
		catch (XcodeException x) {
			Debug.fail(input+"	FATAL:"+x.getCode()+"	"+x.getMessage());
			return;
		}

		if (! Arrays.equals(input_decoded,check)) {
			Debug.fail(input+"	MISMATCH	"+Hex.encode(check));
			return;
		}
		 */

		if (Utf16.isPrintable(output)) {Debug.pass(new String(Utf16.contract(output)));}
		else {Debug.pass(Hex.encode(output));}
	}

}
