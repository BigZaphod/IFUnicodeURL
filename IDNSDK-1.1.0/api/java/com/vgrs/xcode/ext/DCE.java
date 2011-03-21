/*************************************************************************/
/*                                                                       */
/* DCE.java                                                              */
/*                                                                       */
/* (c) VeriSign Inc., 2000-2003, All rights reserved                     */
/*                                                                       */
/* @author: Srikanth Veeramachaneni                                      */
/* @date: 09/10/2002                                                     */
/*                                                                       */
/*************************************************************************/


package com.vgrs.xcode.ext;

import com.vgrs.xcode.util.*;
import com.vgrs.xcode.common.*;



/**
 * Makes a sequence of bytes compatible with the Domain Naming System.  The
 * algorithm uses the Base 32 encoding to create data on the range [a-z, 2-7].
 * Then data sequences longer than 63 characters are delimited using a
 * FULLSTOP character.
 */

public final class DCE {


	public static final int MAX_TOKEN_SIZE = 63;
	public static final char DELIMITER = 0x002E;


	/**
	 * Convert array of bytes into a dns-compatible string
	 * @param input sequence of bytes
	 * @return array of dns-compatible bytes
	 * @throws XcodeException if the input is null or empty
	 */
	static public char[] encode(byte[] input) throws XcodeException {

		if (input == null) throw XcodeError.NULL_ARGUMENT();
		if (input.length == 0) throw XcodeError.EMPTY_ARGUMENT();

		char[] dnsCompatibleBytes = null;
		char[] base32 = Base32.encode(input);

		//
		// if the number of base32 characters is greater than 63
		// insert a '.' after every 63 characters to make it dns compatible
		//
		if (base32.length > MAX_TOKEN_SIZE) {
			int delimsToInsert = base32.length / MAX_TOKEN_SIZE;
			dnsCompatibleBytes = new char[base32.length + delimsToInsert];

			System.arraycopy(base32, 0, dnsCompatibleBytes, 0, MAX_TOKEN_SIZE);
			for(int i = 1; i <= delimsToInsert; i++) {
				int bytesToCopy = MAX_TOKEN_SIZE;
				if (base32.length < (i + 1) * MAX_TOKEN_SIZE) {
					bytesToCopy = base32.length - i * MAX_TOKEN_SIZE;
				}

				dnsCompatibleBytes[i * (MAX_TOKEN_SIZE + 1) - 1] = DELIMITER;
				System.arraycopy(base32, i * MAX_TOKEN_SIZE,
				dnsCompatibleBytes, i * (MAX_TOKEN_SIZE + 1), bytesToCopy);
			}
		}
		else {
			dnsCompatibleBytes = base32;
		}

		return dnsCompatibleBytes;
	}



	/**
	 * Convert a dns-compatible string into array of bytes
	 * @param input array of dns-compatible bytes
	 * @return sequence of bytes
	 * @throws XcodeException if the input is null or empty
	 */
	static public byte[] decode(char[] input) throws XcodeException {

		if (input == null) throw XcodeError.NULL_ARGUMENT();
		if (input.length == 0) throw XcodeError.EMPTY_ARGUMENT();

		char[] base32 = null;

		//
		// remove delimiters if present
		//
		if (input.length > MAX_TOKEN_SIZE) {
			int delimsToRemove = input.length / (MAX_TOKEN_SIZE + 1);
			base32 = new char[input.length - delimsToRemove];

			System.arraycopy(input, 0, base32, 0, MAX_TOKEN_SIZE);
			for (int i = 1; i <= delimsToRemove; i++) {
				if (input[i * (MAX_TOKEN_SIZE + 1) - 1] != DELIMITER) {
					throw XcodeError.DCE_INVALID_DELIMITER();
				}

				int bytesToCopy = MAX_TOKEN_SIZE;
				if (input.length < (i + 1) * (MAX_TOKEN_SIZE + 1)) {
					bytesToCopy = input.length - i * (MAX_TOKEN_SIZE + 1);
				}

				System.arraycopy(input, i * (MAX_TOKEN_SIZE + 1),
				base32, i * MAX_TOKEN_SIZE, bytesToCopy);
			}
		}
		else {
			base32 = input;
		}

		return Base32.decode(base32);
	}

} // END class DCE
