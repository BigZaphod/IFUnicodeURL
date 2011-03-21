/*************************************************************************/
/*                                                                       */
/* Bidi.java                                                             */
/*                                                                       */
/* (c) VeriSign Inc., 2000-2003, All rights reserved                     */
/*                                                                       */
/* @author: John Colosi                                                  */
/* @date: February, 2003                                                 */
/*                                                                       */
/*************************************************************************/


package com.vgrs.xcode.idna;

import java.io.*;
import java.util.*;
import com.vgrs.xcode.util.*;
import com.vgrs.xcode.common.*;

/**
 * Prevents certain groupings of unicode characters from IDN registration.
 * The Stringprep draft prescribes the prohibition of characters which
 * "Change display properties or are deprecated" (Section 5.8)  These
 * characters are prohibited during the Nameprep Prohibition step as well.
 * To avoid redundant prohibition of these characters, a boolean parameter
 * is offered in the constructor allowing applications to opt out of the
 * Bidi prohibition step.
 */
public class Bidi {

	static public final boolean DEFAULT_PROHIBIT = false;

	static public final int BIDI_PROHIBIT_THRESHOLD = 0;
	static public final int BIDI_L_THRESHOLD = 17;
	static public final int BIDI_RAL_THRESHOLD = 16;

	static public final String BIDI_PROHIBIT_DATA = "data/BidiProhibit.txt.gz";
	static public final String BIDI_L_DATA = "data/BidiL.txt.gz";
	static public final String BIDI_RAL_DATA = "data/BidiRAL.txt.gz";

	static public final String PROHIBIT_PREFIX = "Bidi prohibited ";

	private UnicodeFilter bidi_prohibit = null;
	private UnicodeFilter bidi_l = null;
	private UnicodeFilter bidi_ral = null;
	private boolean prohibit;

	public Bidi() throws XcodeException {
		this(DEFAULT_PROHIBIT);
	}
	public Bidi(boolean prohibit) throws XcodeException {
		this.prohibit = prohibit;
		if (prohibit) {
			bidi_prohibit = new UnicodeFilter(BIDI_PROHIBIT_THRESHOLD,PROHIBIT_PREFIX);
			bidi_prohibit.apply(BIDI_PROHIBIT_DATA);
		}
		bidi_l = new UnicodeFilter(BIDI_L_THRESHOLD);
		bidi_l.apply(BIDI_L_DATA);
		bidi_ral = new UnicodeFilter(BIDI_RAL_THRESHOLD);
		bidi_ral.apply(BIDI_RAL_DATA);
	}


	/**
	 * Use UnicodeFilter object to check if there is a prohibited
	 * unicode in an integer array
	 * @param input an integer array
	 * @throws XcodeException Bidi rule is violated
	 */
	public void test(int[] input) throws XcodeException {
		boolean anyL = false;
		boolean anyRAL = false;
		if (prohibit) {bidi_prohibit.test(input);}
		if (input.length > 1) {
			boolean firstAndLast = bidi_ral.test(input[0]) &&
			bidi_ral.test(input[input.length-1]);
			for (int i=0; i<input.length; i++) {
				if (bidi_ral.test(input[i])) {anyRAL = true;}
				if (bidi_l.test(input[i])) {anyL = true;}
				if (anyL && anyRAL) {throw XcodeError.BIDI_RULE_2_VIOLATION();}
				if (anyRAL && !firstAndLast) {throw XcodeError.BIDI_RULE_3_VIOLATION();}
			}
		}
	}

	/**
	 * Display restricted range
	 *
	 * @throws XcodeException if ranges are not available.
	 */
	public void show() throws XcodeException {
		System.out.println("\n\nBidi Prohibit Matrix:");
		bidi_prohibit.showMatrix();
		System.out.println("\n\nBidi Prohibit List:");
		bidi_prohibit.showList();

		System.out.println("\n\nBidi RAL Matrix:");
		bidi_ral.showMatrix();
		System.out.println("\n\nBidi RAL List:");
		bidi_ral.showList();

		System.out.println("\n\nBidi L Matrix:");
		bidi_l.showMatrix();
		System.out.println("\n\nBidi L List:");
		bidi_l.showList();
	}

}
