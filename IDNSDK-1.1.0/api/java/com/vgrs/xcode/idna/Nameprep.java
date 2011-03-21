/*************************************************************************/
/*                                                                       */
/* Nameprep.java                                                         */
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
 * The Nameprep implementation runs four constituent components (charmap,
 * normalize, prohibit, bidi) in order as prescribed by the rfc.
 * The Bidi object constructor is passed a false boolean value to indicate
 * that it should NOT run the Bidi prohibition step.  The flag is offered in
 * the Bidi constructor precisely because the Nameprep prohibition step
 * has already prohibited characters which "Change display properties or are
 * deprecated".  There is no reason to run it again.
 */
public class Nameprep {

	static public final boolean DEFAULT_ALLOWUNASSIGNED = false;

	private Prohibit prohibit = null;
	private Bidi bidi = null;

	public Nameprep () throws XcodeException {this(DEFAULT_ALLOWUNASSIGNED);}
	public Nameprep (boolean allowunassigned) throws XcodeException {
		prohibit = new Prohibit(allowunassigned);
		bidi = new Bidi(false);
	}

	/**
	 * Name prep sections of a domain in form of an int array
	 * @param input a domain
	 * @return an int array
	 * @throws XcodeException if the input array contains invalid or prohibitted
	 *                        character.
	 */
	public int[] domainExecute(int[] input) throws XcodeException {
		if (input == null) {throw XcodeError.NULL_ARGUMENT();}
		if (input.length == 0) {throw XcodeError.EMPTY_ARGUMENT();}

		UnicodeSequence output = new UnicodeSequence();
		UnicodeTokenizer tokens = new UnicodeTokenizer(input,Idna.INT_DELIMITERS,true);
		int[] token;
		while (tokens.hasMoreTokens()) {
			token = tokens.nextToken();
			if (token.length == 1 && Idna.isDelimiter(token[0])) {
				output.append(token);
			}
			else {output.append(execute(token));}
		}

		return output.get();
	}

	/**
	 * Name prep an int array
	 * @param input an int array
	 * @return an int array
	 * @throws XcodeException if the input array contains invalid or prohibitted
	 *								 character.
	 */
	public int[] execute(int[] input) throws XcodeException {
		input = Charmap.execute(input);
		input = Normalize.execute(input);
		prohibit.test(input);
		bidi.test(input);
		return input;
	}

}
