/*************************************************************************/
/*                                                                       */
/* XcodeErrorGenerator.java                                              */
/*                                                                       */
/* (c) VeriSign Inc., 2000-2003, All rights reserved                     */
/*                                                                       */
/* @author: John Colosi                                                  */
/* @date: January, 2003                                                  */
/*                                                                       */
/*************************************************************************/


package com.vgrs.xcode.util;

import java.io.*;
import java.util.*;
import com.vgrs.xcode.common.*;

/**
 * Generates XcodeError object on the fly. The XcodeError object reads in
 * error codes from the ErrorCodes.txt source file.
 */

public class XcodeErrorGenerator {

	static public final String EMPTY = "";
	static public final char DELIMITER = '\t';

	static public void usage() {
		System.out.println("usage: java XcodeErrorGenerator <inputfile> <outputfile>");
	}

	static public void main(String args[]) {
		if (args.length != 2) {usage();return;}
		generate(new File(args[0]),new File(args[1]));
	}

	static public String getHeader() {
		String header = new String();
		header += "/*\n * This source file is constructed from the ErrorCodes.txt\n";
		header += " * file in the data directory.  Do not modify.\n */\n\n";
		header += "package com.vgrs.xcode.util;\npublic class XcodeError {\n";
			return header;
		}

		static public String getFooter() {
		return "}\n";
	}

	static public void generate(File input, File output) {
		try {
			// Read from ErrorCodes.txt
			BufferedReader in = new BufferedReader(new FileReader(input));
			Vector lines = new Vector();
			String line = null;
			while ((line = in.readLine()) != null) { lines.add(line); }
			Iterator data = lines.iterator();


			// Write to XcodeError.java
			FileOutputStream fos = new FileOutputStream(output);
			BufferedOutputStream bos = new BufferedOutputStream(fos);
			byte[] buffer;

			buffer = getHeader().getBytes();
			bos.write(buffer,0,buffer.length);

			while (data.hasNext()) {
				buffer = generate((String)data.next()).getBytes();
				bos.write(buffer,0,buffer.length);
			}

			buffer = getFooter().getBytes();
			bos.write(buffer,0,buffer.length);

			bos.close();
		} catch (Exception x) { x.printStackTrace(); }
	}

	static public String generate(String input) throws Exception {
		if (input == null) { return EMPTY; }
		input = input.trim();
		if (input.length() == 0 || input.charAt(0) == '#') { return EMPTY; }

		int token;
		int code;
		String name;
		String msg;

		token = input.indexOf(DELIMITER);
		if (token < 0) {throw new Exception("Invalid file format: "+input);}
		code = Integer.parseInt(input.substring(0,token));
		input = input.substring(token+1);

		token = input.indexOf(DELIMITER);
		if (token < 0) {
			name = input;
			msg = EMPTY;
		} else {
			name = input.substring(0,token);
			msg = input.substring(token+1);
		}

		String result = "\n\n/*\n * "+name+": "+code+"\n */\n";

		// Create method with no parameters
		result += " static public XcodeException "+name+"() {\n";
			result += "  return new XcodeException(\n";
				result += "   "+code+",\n";
				result += "   \""+msg+"\"\n";
			result += "  );\n";
		result += " }\n";

		// Create method with String parameter
		result += " static public XcodeException "+name+"(String msg) {\n";
			result += "  return new XcodeException(\n";
				result += "   "+code+",\n";
				result += "   \""+msg+"\"+msg\n";
			result += "  );\n";
		result += " }\n";

		// Create is method with XcodeException parameter
		result += " static public boolean is_"+name+"(XcodeException x) {\n";
			result += "  return (x.getCode() == "+code+");\n";
		result += " }\n";

		return result;
	}

}
