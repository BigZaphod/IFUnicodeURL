/*************************************************************************/
/*                                                                       */
/* XcodeException.java                                                   */
/*                                                                       */
/* (c) VeriSign Inc., 2000-2003, All rights reserved                     */
/*                                                                       */
/* @author: John Colosi                                                  */
/* @date: June, 2002                                                     */
/*                                                                       */
/*************************************************************************/


package com.vgrs.xcode.util;

import java.util.*;

/**
 * This object embodies all error conditions from the IDN SDK.
 */

public class XcodeException extends Exception {

	private int code;
	private String message;
	private Throwable parent;

	/**
	 * Construct a xcode exception with an error code and detailed message
	 * @param code an error code
	 * @param message detailed message
	 */
	public XcodeException(int code, String message) {
		this(code,message,null);
	}

	/**
	 * Construct a xcode exception chaining the supplied XcodeExcpetion and
	 * another Throwable object.
	 * @param x a XcodeException object
	 * @param parent a Throwable object
	 */
	public XcodeException(XcodeException x, Throwable parent) {
		this(x.code,x.message,parent);
	}

	/**
	 * Construct a xcode exception chaining the supplied XcodeExcpetion and
	 * detail message.
	 * @param x a XcodeException object
	 * @param message detailed message
	 */
	public XcodeException(XcodeException x, String message) {
		this(x.code,x.message+message,x.parent);
	}

	/**
	 * Construct a xcode exception with an error code, and chaining the
	 * supplied XcodeExcpetion and a Throwable object
	 * detail message.
	 * @param x a XcodeException object
	 * @param detailed message
	 * @param parent a Throwable object
	 */
	public XcodeException(int code, String message, Throwable parent) {
		super(message);
		this.code = code;
		this.message = message;
		this.parent = parent;
	}

	/**
	 * Obtain error code of this XcodeExcpetion
	 * @return int error code
	 */
	public int getCode() {
		return this.code;
	}

	/**
	 * Obtain detailed message of this XcodeExcpetion
	 * @return String error message
	 */
	public String getMessage() {
		String output = new String();
		if (this.message != null) {output += this.message;}
		if (this.parent != null) {
			output += "\n -> "+parent.getClass().getName()+": ";
			String msg = parent.getMessage();
			if (msg != null) {output += msg;}
		}
		return output;
	}

}
