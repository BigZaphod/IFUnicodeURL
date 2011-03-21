/*************************************************************************/
/*                                                                       */
/* UnicodeFilter.java                                                    */
/*                                                                       */
/* (c) VeriSign Inc., 2000-2003, All rights reserved                     */
/*                                                                       */
/* @author: John Colosi                                                  */
/* @date: June, 2002                                                     */
/*                                                                       */
/*************************************************************************/



package com.vgrs.xcode.common;
import java.io.*;
import java.util.*;
import com.vgrs.xcode.util.*;



/**
 * Implements a set of Unicode codepoints and operations to determine whether
 * certain codepoints fall inside or outside the set.  Two different data
 * structures are used to store codepoints internally.
 */
public class UnicodeFilter {

	/**
	 * This is the maximum number of ranges to allow on a plane before
	 * converting the data to a matrix.  If there are only a handful of ranges,
	 * then the expense of a matrix is not justified.  If the number of ranges
	 * exceeds RANGE_THRESHOLD, then a matrix of size 8K is generated and the
	 * range data is stored.  Ranges contained entirely on the matrix are
	 * removed from the list.
	 */
	public static final int RANGE_THRESHOLD = 30;

	/**
	 * The set of Unicode points is defined on the values 0 - 0x10ffff.  This is
	 * exactly 17 planes of size 0x10000.  The UNICODE_PLANES variable is
	 * hard-coded with the value 17.
	 */
	public static final int UNICODE_PLANES = 17;

	/**
	 * This variable determines the default behavior of the test(int[]) method.
	 * If this variable is true, then an XcodeException is thrown if any part of
	 * the input falls outside the filter.  If this variable is false, then an
	 * XcodeException is thrown if any part of the input falls inside the filter.
	 * <br>This variable is hard coded to the value <i>false</i>.
	 */
	public static final boolean DEFAULT_FILTER_CONTEXT = false;



	private String prefix = null;
	private Vector list = null;
	private boolean filterContext;
	private boolean[] matrixFlag = new boolean[UNICODE_PLANES];
	private int[] rangeCount = new int[UNICODE_PLANES];
	private UnicodeMatrix[] matrix = new UnicodeMatrix[UNICODE_PLANES];



	/**
	 * Construct a UnicodeFilter
	 * @deprecated - The threshold parameter is no longer supplied by the user
	 *               and it's value is ignored.
	 */
	public UnicodeFilter(int threshold) {
		this("");
	}



	/**
	 * Construct a UnicodeFilter
	 * @deprecated - The threshold parameter is no longer supplied by the user
	 *               and it's value is ignored.
	 */
	public UnicodeFilter(int threshold, String prefix) {
		this(prefix);
	}



	/**
	 * Construct a UnicodeFilter
	 * @param prefix A String describing the characters to appear before
	 *               filtered codepoints in the returned XcodeException.
	 */
	public UnicodeFilter(String prefix) {
		this(prefix,DEFAULT_FILTER_CONTEXT);
	}



	/**
	 * Construct a UnicodeFilter
	 * @param prefix A String describing the characters to appear before
	 *               filtered codepoints in the returned XcodeException.
	 * @param filterContext A boolean describing whether codepoints within the
	 *                      filter represent an error condition, or if instead
	 *                      codepoints outside the filter represent an error
	 *                      condition.
	 */
	public UnicodeFilter(String prefix, boolean filterContext) {
		this.prefix = prefix;
		this.list = new Vector();
		this.filterContext = filterContext;
		for (int i=0; i<UNICODE_PLANES; i++) {
			matrixFlag[i] = false;
			rangeCount[i] = 0;
		}
	}



	/**
	 * Read a set of Unicode codepoints from a file and store them in an
	 * internal data structure.
	 * @param filename The file containing Unicode codepoints.
	 * @throws XcodeException If the file does not exist of has invalid format.
	 */
	public void apply(String filename) throws XcodeException {
		Iterator reader = null;
		String line = null;
		StringTokenizer st = null;
		int[] range = null;
		int range_count = 0;

		reader = Datafile.getIterator(filename);
		try {

			while (reader.hasNext()) {
				line = (String)reader.next();

				if (line == null) break;
				line = line.trim();
				if (line.length() == 0) continue;
				if (line.charAt(0) == '#') continue;

				st = new StringTokenizer(line, " \t-");
				range = new int[2];
				range_count = 0;

				if (st.hasMoreTokens()) {
					range[0] = Integer.parseInt(st.nextToken(),16);
				} else {
					continue;
				}

				if (st.hasMoreTokens()) {
					range[1] = Integer.parseInt(st.nextToken(),16);
				} else {
					range[1] = range[0];
				}

				if (st.hasMoreTokens()) {
					line = ": \""+line+"\"";
					throw XcodeError.INVALID_FILE_FORMAT(line);
				}

				apply(range);
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
		integrate();
		collapse();
	}



	/**
	 * Store a range of integers in the UnicodeFilter.
	 * @param range An integer array of length two.  The first element is the
	 *              lower bound and the second element is the upper bound.
	 * @throws XcodeException If range is not valid Unicode
	 */
	public void apply(int[] range) throws XcodeException {
		Unicode.isValid(range);
		if (range[0] > range[1]) {throw XcodeError.UNICODEFILTER_INVALID_RANGE();}

		int first = getPlane(range[0]);
		int last = getPlane(range[1]);
		for (int i=first; i<=last; i++) { rangeCount[i]++; }

		list.add(range);
	}



	/**
	 * Create a UnicodeMatrix for planes with too many ranges.
	 */
	private void integrate() {
		for(int plane=0; plane<UNICODE_PLANES; plane++) {
			if ((rangeCount[plane] > RANGE_THRESHOLD) ||
				(matrixFlag[plane] && rangeCount[plane] > 0)) {
				populateMatrix(plane);
			}
		}
	}



	/**
	 * Populate a UnicodeMatrix for the given plane.
	 * This method also removes ranges which are contained completely within
	 * the given matrix.  Ranges that fall even partially outside the matrix
	 * are ignored.
	 * @param plane The number of a Unicode Plane for the matrix
	 */
	private void populateMatrix(int plane) {
		Vector newlist = new Vector();
		if (! matrixFlag[plane]) {matrix[plane] = new UnicodeMatrix(plane);}
		int[] range = null;
		Enumeration e = list.elements();
		while (e.hasMoreElements()) {
			range = (int[])e.nextElement();
			matrix[plane].insert(range);
			if (! matrix[plane].spans(range)) { newlist.add(range); }
		}
		list = newlist;
		rangeCount[plane] = 0;
		matrixFlag[plane] = true;
	}



	/**
	 * Remove ranges that are contained by more than one matrix.
	 * Most ranges are probably already removed by the populateMatrix method.
	 * This method only serves to clean up records that span two matrices, of
	 * which there can only be few.
	 */
	private void collapse() {
		Vector newlist = new Vector();
		int[] range = null;
		int[] plane = new int[2];
		Enumeration e = list.elements();
		while (e.hasMoreElements()) {
			range = (int[])e.nextElement();
			plane[0] = getPlane(range[0]);
			plane[1] = getPlane(range[1]);
			for (int i=plane[0]; i<=plane[1]; i++) {
				if (! matrixFlag[i]) { newlist.add(range); break; }
			}
		}
		this.list = newlist;
	}



	/**
	 * Return the number of the Unicode Plane to which this codepoint belongs.
	 * @param input An integer
	 */
	private int getPlane(int i) {
		return ((i & 0xffff0000) >> 16);
	}



	/**
	 * Test for intersection between an integer array and the UnicodeFilter
	 * @param input An integer array
	 * @throws XcodeException If intersecting codepoints are found
	 */
	public void test(int[] input) throws XcodeException {
		String msg = null;
		for (int i=0; i<input.length; i++) {
			if (test(input[i])^filterContext) {
				msg = prefix + Integer.toString(input[i],16);
				throw XcodeError.UNICODEFILTER_DOES_NOT_PASS(msg);
			}
		}
	}



	/**
	 * Test for intersection between a single integer and the UnicodeFilter
	 * @param input An integer
	 * @throws XcodeException If an intersection is found
	 */
	public boolean test(int input) throws XcodeException {
		int plane = getPlane(input);
		if (matrixFlag[plane]) {return matrix[plane].test(input);}
		else {
			int[] range = null;
			Enumeration e = list.elements();
			while (e.hasMoreElements()) {
				range = (int[])e.nextElement();
				if (input >= range[0] && input <= range[1]) {return true;}
			}
			return false;
		}
	}


	/**
	 * Display the restricted ranges stored in the list
	 */
	public void showList() {
		System.out.println("\n\n List");
		try {
			Enumeration e = list.elements();
			int[] range;
			while (e.hasMoreElements()) {
				range = (int[])e.nextElement();
				System.out.println(Hex.encode(range));
			}
		} catch (Exception x) {x.printStackTrace();}
	}



	/**
	 * Display the restricted ranges stored in the matrix
	 */
	public void showMatrix() {
		for (int i=0; i<UNICODE_PLANES; i++) {
			if (matrixFlag[i]) {
				System.out.println("\n\n Matrix "+i);
				matrix[i].show();
			}
		}
	}



}
