/*************************************************************************/
/*                                                                       */
/* EncodingVariants.java                                                 */
/*                                                                       */
/* (c) VeriSign Inc., 2000-2003, All rights reserved                     */
/*                                                                       */
/* @author: John Colosi                                                  */
/* @date: January, 2003                                                  */
/*                                                                       */
/*************************************************************************/


package com.vgrs.xcode.ext;

import java.io.*;
import java.util.*;
import com.vgrs.xcode.util.*;
import com.vgrs.xcode.idna.*;
import com.vgrs.xcode.common.*;


/**
 * Class to generate encoding variants for an ACE encoded domain name and
 * encode them using DCE.
 */

public class EncodingVariants {

	static private Ace ace = null;

	public EncodingVariants (Ace ace) {
		this.ace = ace;
	}

	/**
	 * Generate encoding variants for the given ACE domain name.  Encode the
	 * results using DCE.
	 * @param input an ACE encoded domain name
	 * @return encoding variants, the first element is the input
	 * @throws XcodeException on invalid input
	 */
	public String[] execute(String input, String[] encodings)
	throws XcodeException {
		if (input == null) {throw XcodeError.NULL_ARGUMENT();}
		if (input.length() == 0) {throw XcodeError.EMPTY_ARGUMENT();}

		String tld = "";
		int lastDotIndex = input.lastIndexOf('.');
		if (lastDotIndex != -1) {
			tld = input.substring(lastDotIndex).toLowerCase();
			input = input.substring(0, lastDotIndex);
		}

		String utf16 = new String(Unicode.decode(ace.decode(input.toCharArray())));
		HashSet variants = null;
		if (encodings == null) {variants = Native.encodeToSet(utf16);}
		else {variants = Native.encodeToSet(utf16,encodings);}
		Iterator iterator = variants.iterator();

		String[] results = new String[variants.size()];
		int i = 0;
		while (iterator.hasNext()) {
			byte[] variant = Native.getEncoding((String)iterator.next());
			results[i++] = new String(DCE.encode(variant))+tld;
		}

		return results;
	}

}
