/*************************************************************************/
/*                                                                       */
/* UnicodeMatrix.java                                                    */
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
 * structures are used to store codepoints internally.  The THRESHOLD
 * attribute determines whether codepoints are stored in a MATRIX or a VECTOR
 * of ranges.  Data below the threshold is stored in a matrix.  Data above
 * the threshold is stored in a Vector of ranges.  The matrix is faster but
 * requires more memory, so it is best to put densly packed but non
 * contiguous points into the matrix, and leave large ranges in the vector.
 * The threshold is interpreted as a number of bits.  A threshold of 16 means
 * all points less than 2^16 or 0x10000 are stored in a matrix, all
 * points >= 0x10000 are stored as a list of ranges.  Because the matrix is
 * stored internally as an array of char primatives, a threshold < 4 does not
 * make sense.  Any threshold < 4 will be treated as a threshold of 0.  No
 * matrix will be made, all data will be stored in ranges.
 */

public class UnicodeMatrix {

	static private final int LENGTH = 4096;

	private int plane;
	private int floor;
	private int ceiling;
	private char[] data = null;



	/**
	 * Construct a UnicodeMatrix to store 65,535 flags
	 * @param plane A number on the range [0-16] indicating the active plane.
	 */
	public UnicodeMatrix(int plane) {
		this.plane = plane;
		this.floor = plane * 0x10000;
		this.ceiling = (plane * 0x10000) + 0xffff;
		this.data = new char[4096];
	}



	/**
	 * Store a range in the UnicodeMatrix
	 * @param range An integer array of length two.  The first element is the
	 *              lower bound and the second element is the upper bound.
	 */
	public void insert(int[] range) {
		int j = range[0];
		int k = range[1];
		if (k < floor || j > ceiling) {return;}

		if (j < floor) { j = 0x0000; }
		else { j &= 0x0000ffff;}

		if (k > ceiling) { k = 0xffff; }
		else {k &= 0x0000ffff;}

		for (int i=j; i<=k; i++) {insert(i);}
	}



	/**
	 * Store an integer in the UnicodeMatrix
	 * @param i An integer to be inserted in matrix
	 */
	public void insert(int i) {
		data[(i>>4)] |= (1<<(i&0xf));
	}



	/**
	 * Return true if the given range fits completely inside the matrix.
	 * @param range An integer array of length two.  The first element is the
	 *              lower bound and the second element is the upper bound.
	 */
	public boolean spans(int[] range) {
		return (range[0] >= floor && range[1] <= ceiling);
	}



	/**
	 * Test for intersection between a single integer and the UnicodeMatrix
	 * @param input An integer
	 */
	public boolean test(int input) {
		return (((1<<(input&0x0000000f))&data[(input & 0x0000fff0)>>4]) > 0);
	}



	/**
	 * Display the matrix data to standard output.
	 */
	public void show() {
		for (int i=0; i<data.length; i++) {
			System.out.print(Integer.toString(data[i],16)+", ");
			if (i%8==7) {System.out.println();}
		}
	}



}
