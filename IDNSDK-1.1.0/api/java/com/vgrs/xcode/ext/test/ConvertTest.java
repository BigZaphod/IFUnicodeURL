/*************************************************************************/
/*                                                                       */
/* ConvertTest.java                                                      */
/*                                                                       */
/* (c) VeriSign Inc., 2000-2003, All rights reserved                     */
/*                                                                       */
/* @author: John Colosi                                                  */
/* @date: 02/24/2003                                                     */
/*                                                                       */
/*************************************************************************/

package com.vgrs.xcode.ext.test;

import java.io.*;
import java.util.*;
import com.vgrs.xcode.ext.*;
import com.vgrs.xcode.util.*;
import com.vgrs.xcode.idna.*;
import com.vgrs.xcode.common.*;

/**
 * Test driver for Convert
 */

public class ConvertTest {



	static public void usage() {
		System.out.println("usage: java ConvertTest [-3acsx] <file> <input type> <output types>");
		System.out.println(" -3 => do NOT enforce Std 3 Ascii Rules");
		System.out.println(" -a => allow unassigned codepoints (disallowed by default)");
		System.out.println(" -c => do NOT perform round-trip check during toUnicode");
		System.out.println(" -x => allow eXceptions during toUnicode");
		System.out.println(" --list => Output a list of supported encoding types");
	}



	private Convert convert = null;
	public ConvertTest(Convert convert) {
		this.convert = convert;
	}



	static public void main(String args[]) throws XcodeException {
		if (args.length == 0) {usage();return;}
		if (args[0].equals("--list")) {listEncodings(); return;}
		if (args.length < 3) {usage();return;}

		boolean useStd3AsciiRules = true;
		boolean allowUnassigned = false;
		boolean toUnicodeRoundTripCheckFlag = true;
		boolean toUnicodeExceptionFlag = false;

		int i = 0;
		while (args[i].charAt(0) == '-') {
			for (int j=1; j<args[i].length(); j++) {
				if (args[i].charAt(j) == '3') {useStd3AsciiRules = false;}
				else if (args[i].charAt(j) == 'a') {allowUnassigned = true;}
				else if (args[i].charAt(j) == 'c') {toUnicodeRoundTripCheckFlag = false;}
				else if (args[i].charAt(j) == 'x') {toUnicodeExceptionFlag = true;}
				else {usage(); return;}
			}
			i++;
		}

		if (args.length - i < 3) {usage(); return;}

		Race race = new Race(useStd3AsciiRules);
		Punycode punycode = new Punycode(useStd3AsciiRules);
		Nameprep nameprep = new Nameprep(allowUnassigned);
		Idna iRace =
		new Idna(race,nameprep,toUnicodeExceptionFlag,toUnicodeRoundTripCheckFlag);
		Idna iPunycode =
		new Idna(punycode,nameprep,toUnicodeExceptionFlag,toUnicodeRoundTripCheckFlag);

		File input = new File(args[i++]);
		String itype = args[i++];
		String[] otype = new String[args.length - i];
		System.arraycopy(args,i,otype,0,otype.length);

		if (otype == null) {usage(); return;}

		ConvertTest converttest = new ConvertTest(new Convert(iRace, iPunycode));
		converttest.testExecute(input,itype,otype);
	}



	static private void listEncodings() {
		for (int i=0; i<Native.ENCODINGS.length; i++) {
			Debug.pass(Native.ENCODINGS[i]);
		}
	}



	private void testExecute(File file, String itype, String[] otype) {
		try {
			Iterator data = Datafile.getIterator(file);
			if (otype != null && otype.length==1) {
				while (data.hasNext()) {testExecute((String)data.next(), itype, otype[0]);}
			} else {
				while (data.hasNext()) {testExecute((String)data.next(), itype, otype);}
			}
		} catch (Exception x) {x.printStackTrace();}
	}



	private void testExecute(String input, String itype, String otype)
	throws XcodeException {

		String output = null;
		String check = null;

		if (input == null) {
			Debug.pass("");
			return;
		}
		input = input.trim();

		if (input.length() == 0 || input.charAt(0) == '#') {
			Debug.pass(input);
			return;
		}

		boolean asciiInput = itype.equalsIgnoreCase(Convert.RACE_ENCODING) ||
		itype.equalsIgnoreCase(Convert.PUNYCODE_ENCODING);
		boolean asciiOutput = otype.equalsIgnoreCase(Convert.RACE_ENCODING) ||
		otype.equalsIgnoreCase(Convert.PUNYCODE_ENCODING);

		try {
			if (asciiInput) {
				output = convert.execute(input,itype,otype);
			} else {
				output = convert.execute(new String(Hex.decodeBytes(input)),itype,otype);
			}
		} catch (XcodeException x) {
			Debug.fail(input+" ERROR:"+x.getCode()+" "+x.getMessage());
			return;
		}

		if (asciiOutput) {
			Debug.pass(output);
		} else {
			Debug.pass(Hex.encode(Native.getEncoding(output)));
		}
	}

	private void testExecute(String input, String itype, String[] otype)
	throws XcodeException {

		if (input == null) {
			Debug.pass("");
			return;
		}
		input = input.trim();

		if (input.length() == 0 || input.charAt(0) == '#') {
			Debug.pass(input);
			return;
		}

		boolean asciiInput = itype.equalsIgnoreCase(Convert.RACE_ENCODING) ||
		itype.equalsIgnoreCase(Convert.PUNYCODE_ENCODING);

		Properties output;
		try {
			if (asciiInput) {
				output = convert.execute(input,itype,otype);
			} else {
				output = convert.execute(new String(Hex.decodeBytes(input)),itype,otype);
			}
		} catch (XcodeException x) {
			Debug.fail(input+" ERROR:"+x.getCode()+" "+x.getMessage());
			return;
		}

		Iterator iterator = output.keySet().iterator();
		boolean asciiOutput;
		String key;
		String value;

		System.out.println("\n"+input);
		while (iterator.hasNext()) {
			key = (String)iterator.next();
			value = output.getProperty(key);
			asciiOutput = key.equalsIgnoreCase(Convert.RACE_ENCODING) ||
			key.equalsIgnoreCase(Convert.PUNYCODE_ENCODING);
			System.out.print("	"+key+"	");
			if (asciiOutput) {
				System.out.println(value);
			} else {
				System.out.println(Hex.encode(Native.getEncoding(value)));
			}
		}
	}

}
