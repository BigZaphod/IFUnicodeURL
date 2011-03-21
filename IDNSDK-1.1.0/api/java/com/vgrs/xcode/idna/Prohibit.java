/*************************************************************************/
/*                                                                       */
/* Prohibit.java                                                         */
/*                                                                       */
/* (c) VeriSign Inc., 2000-2003, All rights reserved                     */
/*                                                                       */
/* @author: John Colosi                                                  */
/* @date: January, 2003                                                  */
/*                                                                       */
/*************************************************************************/

package com.vgrs.xcode.idna;

import java.io.*;
import java.util.*;
import com.vgrs.xcode.util.*;
import com.vgrs.xcode.common.*;

/**
 * Prevents certain unicode characters from IDN registration.
 */
public class Prohibit extends UnicodeFilter {

	static public final int FILTER_THRESHOLD = 17;
	static public final String PROHIBIT_DATA = "data/Prohibit.txt.gz";
	static public final String ILLEGAL_DATA = "data/Illegal.txt.gz";
	static public final boolean DEFAULT_ALLOWUNASSIGNED = false;
	static public final String EXCEPTION_PREFIX = "Prohibited ";

	public Prohibit() throws XcodeException {
		this(DEFAULT_ALLOWUNASSIGNED);
	}
	public Prohibit(boolean allowunassigned) throws XcodeException {
		super(FILTER_THRESHOLD,EXCEPTION_PREFIX);
		if (! allowunassigned) {apply(ILLEGAL_DATA);}
		else {apply(PROHIBIT_DATA);}
	}

}
