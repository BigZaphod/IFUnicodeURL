/*************************************************************************/
/*                                                                       */
/* Normalize.java                                                         */
/*                                                                       */
/* (c) VeriSign Inc., 2000-2003, All rights reserved                     */
/*                                                                       */
/*************************************************************************/


package com.vgrs.xcode.idna;


import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.StringTokenizer;
import java.util.Iterator;

import com.vgrs.xcode.util.*;
import com.vgrs.xcode.common.*;

/**
 * Provides algorithm to normalize a domain
 */
public class Normalize {

	// Datafiles
	public static final String UNICODE_DATA
	= "data/UnicodeData.txt.gz";
	public static final String COMPOSITION_EXCLUSIONS_DATA
	= "data/CompositionExclusions.txt.gz";

	// Hangul composition constants
	public static final int S_BASE = 0xAC00;
	public static final int L_BASE = 0x1100;
	public static final int V_BASE = 0x1161;
	public static final int T_BASE = 0x11A7;
	public static final int L_COUNT = 19;
	public static final int V_COUNT = 21;
	public static final int T_COUNT = 28;

	// Internal attributes
	private static final ArrayList EXCLUDED_TABLE        = new ArrayList();
	private static final ArrayList COMPATIBILITY_TABLE   = new ArrayList();
	private static final HashMap   CANONICAL_CLASS_TABLE = new HashMap();
	private static final HashMap   COMPOSE_TABLE         = new HashMap();
	private static final HashMap   DECOMPOSE_TABLE       = new HashMap();

	static {
		try {init();}
		catch (XcodeException x) {throw new RuntimeException(x.getMessage());}
	}

	static private void init() throws XcodeException {
		readExclusionChars();
		buildLookupTables();
	}


	/**
	 * Reads the list of excluded characters from the data file into memory
	 */
	static private void readExclusionChars() throws XcodeException {

		Iterator reader = null;
		String line = null;
		StringTokenizer st = null;
		String token = null;
		int excludedChar = -1;

		try {
			reader = Datafile.getIterator(COMPOSITION_EXCLUSIONS_DATA);

			while (reader.hasNext()) {
				line = (String)reader.next();

				if (line == null) break;
				if (line.length() == 0) continue;
				if (line.charAt(0) == '#') continue;

				st = new StringTokenizer(line, " #\t");
				if (st.hasMoreTokens()) {
					token = st.nextToken();
					excludedChar = Integer.parseInt(token,16);
					EXCLUDED_TABLE.add(new Integer(excludedChar));
				}
				else {
					continue; // skip blank line
				}
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

	} // END readExclusionChars()



	/**
	 * Builds canonical class, compatibility, composed and decomposed
	 * lookup tables from unicode data file. The exlcuded characters must
	 * have been loaded already.
	 */
	static private void buildLookupTables() throws XcodeException {

		Iterator reader = null;
		String line = null;
		String token = null;
		int[] mapseq = null;
		int unicodeChar = -1;
		int canonicalClass = -1;
		int semicolonIndx = -1;
		int nextSemicolonIndx = -1;
		int gtIndex = -1;
		int first = 0;
		int second = 0;
		long pair = -1;
		int sIndex = -1;
		int tIndex = -1;
		int nCount = -1;
		int sCount = -1;
		//int valueHangul = -1;

		try {
			reader = Datafile.getIterator(UNICODE_DATA);

			while (reader.hasNext()) {

				boolean fCompat = false;

				line = (String)reader.next();

				if (line == null) break;

				//
				// each line consists of strings(tokens) which are seperated by
				// semi-colons
				//

				semicolonIndx = line.indexOf(';');
				if (semicolonIndx == -1) continue; // skip blank line

				//
				// token 1: codepoint
				//
				if (semicolonIndx != 0) {
					token = line.substring(0, semicolonIndx);
					unicodeChar = Integer.parseInt(token,16);
				}


				//
				// token 2: ?
				//
				nextSemicolonIndx = line.indexOf(';', semicolonIndx + 1);
				if (nextSemicolonIndx == -1) continue;

				//
				// token 3: ?
				//
				semicolonIndx = nextSemicolonIndx;
				nextSemicolonIndx = line.indexOf(';', semicolonIndx + 1);
				if (nextSemicolonIndx == -1) continue;

				//
				// token 4: canonical combining class
				//
				semicolonIndx = nextSemicolonIndx;
				nextSemicolonIndx = line.indexOf(';', semicolonIndx + 1);
				if (nextSemicolonIndx == -1) continue;

				if (nextSemicolonIndx - semicolonIndx > 1) {
					token = line.substring(semicolonIndx + 1, nextSemicolonIndx);
					canonicalClass = Integer.parseInt(token);

					//
					// check if canocical class is within 0 - 255
					//
					if (canonicalClass != (canonicalClass & 0xFF)) {
						throw XcodeError.NORMALIZE_BAD_CANONICALCLASS_ERROR();
					}

					CANONICAL_CLASS_TABLE.put(
					new Integer(unicodeChar), new Integer(canonicalClass));
				}

				//
				// token 5: bidirectional
				//
				semicolonIndx = nextSemicolonIndx;
				nextSemicolonIndx = line.indexOf(';', semicolonIndx + 1);
				if (nextSemicolonIndx == -1) continue;

				//
				// token 6: decomposition
				//
				semicolonIndx = nextSemicolonIndx;
				nextSemicolonIndx = line.indexOf(';', semicolonIndx + 1);
				if (nextSemicolonIndx == -1) continue;

				if (nextSemicolonIndx - semicolonIndx > 1) {
					token = line.substring(semicolonIndx + 1, nextSemicolonIndx);

					if (token.charAt(0) == '<') {
						COMPATIBILITY_TABLE.add(new Integer(unicodeChar));

						gtIndex = token.indexOf('>');
						if (gtIndex == -1) {
							throw XcodeError.NORMALIZE_BAD_COMPATTAG_ERROR();
						}

						token = token.substring(gtIndex + 2);
						fCompat = true;
					}

					//
					// process mapping sequence
					//
					mapseq = Hex.decodeInts(token);

					//
					// sanity check: all decomposition must be singles or pairs
					//
					if (mapseq.length < 1 || (mapseq.length > 2 && !fCompat)) {
						throw XcodeError.NORMALIZE_BAD_DECOMPSEQUENCE_ERROR();
					}

					DECOMPOSE_TABLE.put(new Integer(unicodeChar), mapseq);

					//
					// store composition pairs
					//
					if (!fCompat && !EXCLUDED_TABLE.contains(new Integer(unicodeChar))) {
						if (mapseq.length > 1) {
							first  = mapseq[0];
							second = mapseq[1];
						}
						else {
							first  = 0;
							second = mapseq[0];
						}
						pair = ((long)first << 32) | second;

						// The key is a construction of the first two values in mapseq
						COMPOSE_TABLE.put(new Long(pair), new Integer(unicodeChar));

					}
				}

			} // END while()
		}
		catch (NumberFormatException x) {
			line = ": \""+line+"\"";
			throw XcodeError.INVALID_FILE_FORMAT(line);
		}
		catch (Exception x) {
			line = ": \""+line+"\"";
			throw XcodeError.INVALID_FILE_FORMAT(line);
		}


		//
		// Hangul decompositions
		//
		nCount = V_COUNT * T_COUNT;   // 588
		sCount = L_COUNT * nCount;   // 11172
		for (sIndex = 0; sIndex < sCount; ++sIndex) {
			tIndex = sIndex % T_COUNT;

			if (tIndex != 0) { // triple
				first  = (S_BASE + sIndex - tIndex);
				second = (T_BASE + tIndex);
			}
			else {
				first  = (L_BASE + sIndex / nCount);
				second = (V_BASE + (sIndex % nCount) / T_COUNT);
			}

			mapseq = new int[2];
			mapseq[0] = first;
			mapseq[1] = second;

			pair = ((long)first << 32) | second;

			unicodeChar = sIndex + S_BASE;

			DECOMPOSE_TABLE.put(new Integer(unicodeChar), mapseq);
			COMPOSE_TABLE.put(new Long(pair), new Integer(unicodeChar));

		} // END for()


	} // END buildLookupTables()


	/**
	 * Execute the normalization algorithm
	 * @param input Unicode sequence to normalize
	 * @return Normalized Unicode sequence
	 * @throws XcodeException if the input string is null or with length == 0
	 *          or the input sequence cannot be normalized.
	 */
	static public int[] execute(int[] input) throws XcodeException {
		if (input == null) throw XcodeError.NULL_ARGUMENT();
		if (input.length == 0) throw XcodeError.EMPTY_ARGUMENT();
		return kcCompose(kcDecompose(input));
	}


	/**
	 * Form KC decomposition
	 */
	static private int[] kcDecompose(int[] input) throws XcodeException {

		UnicodeSequence output = new UnicodeSequence();
		int[] buf = null;
		int charI = 0;
		int charJ = 0;
		int cClass = -1;
		int cursor = -1;
		Integer pCanonicalItem = null;

		for (int i = 0; i < input.length; i++) {
			charI = input[i];

			if (charI == 0) {
				throw XcodeError.NORMALIZE_NULL_CHARACTER_PRESENT();
			}

			buf = doDecomposition(charI, false);
			//System.out.println(Integer.toString(charI,16)+" -> "+Hex.encode(buf));

			for (int j = 0; j < buf.length; j++) {
				charJ = buf[j];
				pCanonicalItem = (Integer)CANONICAL_CLASS_TABLE.get(new Integer(charJ));

				if (pCanonicalItem == null) {cClass = 0;}
				else {cClass = pCanonicalItem.intValue();}
				//System.out.println("   class: "+Integer.toString(charJ,16)+" is "+cClass);

				cursor = output.length();

				if (cClass != 0) {
					for (; cursor > 0; --cursor) {
						pCanonicalItem = (Integer) CANONICAL_CLASS_TABLE.get(
						new Integer(output.get(cursor - 1)));
						if (pCanonicalItem == null) {
							throw XcodeError.NORMALIZE_CANONICAL_LOOKUP_ERROR();
						}
						if (pCanonicalItem.intValue() <= cClass) {break;}
					}
				}
				//System.out.println("insert: "+Integer.toString(charJ,16)+" at "+cursor);
				output.insert(charJ, cursor);
				//output.dump(" updated: ");
				//output.vdump(" vector: ");
				//System.out.println("\n");
			}
		}

		//output.dump("decomposed: ");
		return output.get();

	} // END kcDecompose()



	/**
	 * doDecomposition - recursive decomposition for one unicode character
	 *
	 */
	static private int[] doDecomposition(int u, boolean fCanonical) {
		UnicodeSequence output = new UnicodeSequence();
		int[] decomposeItem = null;
		boolean compatExists = false;

		decomposeItem = (int[]) DECOMPOSE_TABLE.get(new Integer(u));
		compatExists = COMPATIBILITY_TABLE.contains(new Integer(u));

		if (decomposeItem != null && !(fCanonical && compatExists)) {
			for(int i = 0; i < decomposeItem.length; i++) {
				output.append(doDecomposition(decomposeItem[i], fCanonical));
			}
		}
		else {
			output.append(u);
		}

		return output.get();

	} // END doDecomposition()




	/**
	 * Form KC recomposition
	 *
	 */
	static private int[] kcCompose(int[] output) throws XcodeException {
		int startCh = 0;
		Integer pCanonical = null;
		Integer pCompose = null;
		int lastClass = -1;
		int decompPos = -1;
		int startPos = 0;
		int compPos = 1;

		startCh = output[0];

		pCanonical = (Integer) CANONICAL_CLASS_TABLE.get(new Integer(startCh));
		if (pCanonical != null && pCanonical.intValue() != 0) {
			lastClass = 256;
		}
		else {
			lastClass = 0;
		}

		for (decompPos=1; decompPos < output.length; decompPos++) {
			int chClass = -1;
			int composite = -1;
			int ch = output[decompPos];
			long pair = -1;

			pCanonical = (Integer) CANONICAL_CLASS_TABLE.get(new Integer(ch));
			if (pCanonical == null) {chClass = 0;}
			else {chClass = pCanonical.intValue();}

			pair = ((long)startCh << 32) | ch;
			pCompose = (Integer) COMPOSE_TABLE.get(new Long(pair));
			if (pCompose == null) {composite = 0xffffffff;}
			else {composite = pCompose.intValue();}

			if (composite != 0xffffffff && (lastClass < chClass || lastClass == 0)) {
				output[startPos] = composite;
				startCh = composite;
			} else {
				if (chClass == 0) {
					startPos = compPos;
					startCh = ch;
				}
				lastClass = chClass;
				output[compPos++] = ch;
			}
		}

		if (compPos != output.length) {
			int[] buf = new int[compPos];
			System.arraycopy(output,0,buf,0,compPos);
			output = buf;
		}

		return output;

	} // END kcCompose()

} // END Normalize.java
