/*************************************************************************/
/*                                                                       */
/* UnicodeSequence.java                                                  */
/*                                                                       */
/* (c) VeriSign Inc., 2000-2003, All rights reserved                     */
/*                                                                       */
/* @author: John Colosi                                                  */
/* @author: Lihui Zhang                                                  */
/* @date: February, 2003                                                 */
/*                                                                       */
/*************************************************************************/


package com.vgrs.xcode.common;

import java.io.*;
import java.util.*;
import com.vgrs.xcode.util.*;

/**
 * Allows manipulation of arrays of integer primitives.  Supports appending
 * and inserting of integer primitives into the sequence.  The actual
 * sequence is not constructed until the get method is called, so each
 * manipulation can be done with less data reorganization.
 */
public class UnicodeSequence {

	private Vector vector = new Vector();
	private int[] value = null;
	private int len = 0;
	private boolean delta = false;

	/**
	 * Append an integer to this unicode sequence
	 * @param i integer to be added
	 */
	public void append(int i) {
		len ++;
		int[] tmp = new int[1];
		tmp[0] = i;
		vector.add(tmp);
		delta = true;
	}

	/**
	 * Append all integers in the integer array to this unicode sequence
	 * @param i integer array to be added
	 */
	public void append(int[] i) {
		len += i.length;
		vector.add(i);
		delta = true;
	}

	/**
	 * Insert an integer to beginning of this unicode sequence
	 * @param i integer to be added
	 */
	public void prepend(int i) {
		len ++;
		int[] tmp = new int[1];
		tmp[0] = i;
		vector.insertElementAt(tmp,0);
		delta = true;
	}

	/**
	 * Insert all integers to beginning of this unicode sequence
	 * @param i integer array to be added
	 */
	public void prepend(int[] i) {
		len += i.length;
		vector.insertElementAt(i,0);
		delta = true;
	}

	/**
	 * Insert an integer to this unicode sequence at the indicated offset. <br>
	 * The offset argument must be greater or equals to 0 and less then or equal
	 * to the length of this unicode sequence.
	 *
	 * @param i integer array to be added
	 * @param offset the offset
	 */
	public void insert(int i, int offset) {
		if (offset >= len) {append(i);}
		else if (offset <= 0) {prepend(i);}
		else {
			evaluate();
			int[] tmp = new int[len + 1];
			System.arraycopy(value,0,tmp,0,offset);
			tmp[offset] = i;
			System.arraycopy(value,offset,tmp,offset+1,len-offset);
			len++;
			value = tmp;
			vector.setElementAt(tmp,0);
		}
	}

	/**
	 * Insert all integers in an integer array to this unicode sequence
	 * at the indicated offset. <br>
	 * The offset argument must be greater or equals to 0 and less then or equal
	 * to the length of this unicode sequence.
	 *
	 * @param i integer array to be added
	 * @param offset the offset
	 */

	public void insert(int[] i, int offset) {
		if (offset >= len) {append(i);}
		else if (offset <= 0) {prepend(i);}
		else {
			evaluate();
			int[] tmp = new int[len + i.length];
			System.arraycopy(value,0,tmp,0,offset);
			System.arraycopy(i,0,tmp,offset,i.length);
			System.arraycopy(value,offset,tmp,offset+i.length,len-offset);
			len += i.length;
			value = tmp;
			vector.setElementAt(tmp,0);
		}
	}

	/**
	 * Returns the length of this unicode sequence. The length is equals to the
	 * number of integer array in the sequence.
	 * @return the length of this unicode sequence
	 */
	public int length() {
		return len;
	}

	/**
	 * Get the all unicodes in this unicode sequence
	 *
	 * @return integer array
	 */
	public int[] get() {
		evaluate();
		return value;
	}

	/**
	 * Get the unicodes at the indicated position this unicode sequence
	 *
	 * @param offset an offset
	 * @return integer array
	 */
	public int get(int offset) {
		evaluate();
		return value[offset];
	}

	private void evaluate() {
		if (delta) {
			int[] tmp;
			value = new int[len];
			int offset = 0;
			Enumeration e = vector.elements();
			while (e.hasMoreElements()) {
				tmp = (int[])e.nextElement();
				System.arraycopy(tmp,0,value,offset,tmp.length);
				offset+=tmp.length;
			}
			delta = false;
			vector = new Vector();
			vector.add(value);
		}
	}

	/*
	public void dump(String s) {
		evaluate();
		try {System.out.println(s+Hex.encode(value));}
		catch (XcodeException x) {x.printStackTrace();}
	}
	public void vdump(String s) {
		Enumeration e = vector.elements();
		System.out.println(s);
		while (e.hasMoreElements()) {
			int[] tmp = (int[])e.nextElement();
			try {System.out.println(Hex.encode(tmp));}
			catch (XcodeException x) {x.printStackTrace();}
		}
	}
	 */

	/**
	 * Compars this UnicodeSequence object the specified UnicodeSequence.
	 * The result is true only if the specified UnicodeSequence is not null and
	 * contains the same sequence of integer array as this object.
	 *
	 * @param other the UnicodeSequence object to compare with this object
	 * @return true if the specified UnicodeSequence is equal, otherwise false
	 */
	public boolean equals(UnicodeSequence other) {
		return Arrays.equals(this.get(),other.get());
	}

}
