/*************************************************************************/
/*                                                                       */
/* Charmap.java                                                          */
/*                                                                       */
/* (c) VeriSign Inc., 2000-2003, All rights reserved                     */
/*                                                                       */
/* @author: John Colosi                                                  */
/* @date: June, 2002                                                     */
/*                                                                       */
/*************************************************************************/


package com.vgrs.xcode.idna;

import java.io.*;
import java.util.*;
import com.vgrs.xcode.util.*;
import com.vgrs.xcode.common.*;


/**
 * Implements the character mapping rules defined in the Stringprep and
 * Nameprep rfc documents.
 */
public class Charmap {

	static public final String CHARMAP_DATA = "data/Charmap.txt.gz";

	static public HashMap CHARMAP_HASH = new HashMap();

	static {
		try {init();}
		catch (Exception x) {throw new RuntimeException(x.getMessage());}
	}

	static private void init() throws XcodeException {
		processFile(CHARMAP_DATA);
	}

	/**
	 * Load character mapping into memory
	 * @param filename the charater mapping data file
	 */
	static private void processFile(String filename) throws XcodeException {
		Iterator reader = null;
		String line = null;
		StringTokenizer fields = null;
		StringTokenizer values = null;
		String token = null;
		int key;
		int[] value;

		try {
			reader = Datafile.getIterator(filename);

			while (reader.hasNext()) {
				line = (String)reader.next();

				if (line == null) break;
				line = line.trim();
				if (line.length() == 0) continue;
				if (line.charAt(0) == '#') continue;

				fields = new StringTokenizer(line, ";");

				if (fields.hasMoreTokens()) {
					key = Integer.parseInt(fields.nextToken(),16);
				} else {
					continue;   // skipping empty line
				}

				if (fields.hasMoreTokens()) {
					values = new StringTokenizer(fields.nextToken());
					value = new int[values.countTokens()];
					int i = 0;
					while (values.hasMoreTokens()) {
						value[i++] = Integer.parseInt(values.nextToken(),16);
					}
				} else {
					line = ": \""+line+"\"";
					throw XcodeError.INVALID_FILE_FORMAT(line);
				}

				addCharactersToHashMap(key,value);
			}
		}
		catch (NumberFormatException x) {
			line = ": \""+line+"\"";
			throw XcodeError.INVALID_FILE_FORMAT(line);
		}
		catch (Exception x) {
			line = ": \""+line+"\"";
			throw XcodeError.INVALID_FILE_FORMAT(line);
		}
	}

	static private void addCharactersToHashMap(int key, int[] value) {
		CHARMAP_HASH.put(new Integer(key),value);
	}

	/**
	 * Get all character from the mapping tables in memory
	 * @param input an int array
	 * @return int array mapping to the input array
	 * @throws XcodeException if the input array in null or empty or
	 *								  if input characters were mapped out during
	 *								  character mapping
	 */
	static public int[] execute(int[] input) throws XcodeException {
		if (input == null) {throw XcodeError.NULL_ARGUMENT();}
		if (input.length == 0) {throw XcodeError.EMPTY_ARGUMENT();}

		// Initialize
		//Debug.log(" charmap -> " + Hex.encode(new String(input)));

		// Decarations
		int[] tmp = new int[(input.length*6)];
		// The tmp array stores the output temporarily before we know the exact
		// length.  The worst case length is input * 6.
		int[] delta;
		int[] output;
		int output_offset = 0;

		try {

			for (int i=0; i<input.length; i++) {
				if (CHARMAP_HASH.containsKey(new Integer(input[i]))) {
					delta = (int[])CHARMAP_HASH.get(new Integer(input[i]));
					if (delta != null) {
						System.arraycopy(delta,0,tmp,output_offset,delta.length);
						output_offset+=delta.length;
					}
				} else {
					tmp[output_offset++] = input[i];
				}
			}

			if (output_offset <= 0) {throw XcodeError.CHARMAP_LABEL_ELIMINATION();}

			if (output_offset != tmp.length) {
				output = new int[output_offset];
				System.arraycopy(tmp,0,output,0,output_offset);
				//Debug.log("  copied ["+tmp.length+"] to ["+output_offset+"]");
			} else {
				output = tmp;
				//Debug.log("  no copy ["+tmp.length+"] = ["+output_offset+"]");
			}

		} catch (IndexOutOfBoundsException x) {
			throw XcodeError.CHARMAP_OVERFLOW();
		}

		// Finalize
		//Debug.log(" charmap <- " + Hex.encode(new String(output)));
		return output;
	}

}
