/*************************************************************************/
/*                                                                       */
/* Race.java                                                             */
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
 *
 * This class implements the ASCII-Compatible Encoding (ACE) algorithm Race.
 *
 */
public final class Race extends Ace {

	/**
	 * Encode Unicode data in a format compatible with the ASCII standard.
	 *
	 * The most recent version of the Race draft specifies "bq--" as the prefix
	 * which designates a domain label as Race encoded.  Because this draft has
	 * been expired for some time, this implementation offers the ability to set
	 * an alternate prefix during object construction.
	 */
	static public final String DEFAULT_PREFIX = "bq--";


	/*
	 * Some constants used commonly throughout the RACE implementation.
	 */
	static public final char DOUBLE_ESCAPE = 0x0099;
	static public final byte NULL_COMPRESSION_FLAG = (byte)0xd8;
	static public final byte DOUBLE_F = (byte)0xff;
	static public final byte DOUBLE_9 = (byte)0x99;
	static public final byte ZERO = (byte)0x00;
	static public final int MAX_COMPRESSION_SIZE = 36;


	/*
	 * Constructors
	 */
	public Race () {
		super(DEFAULT_PREFIX, DEFAULT_USE_STD_3_ASCII_RULES);
	}
	public Race (String prefix) {
		super(prefix,DEFAULT_USE_STD_3_ASCII_RULES);
	}
	public Race (boolean useStd3AsciiRules) {
		super(DEFAULT_PREFIX,useStd3AsciiRules);
	}
	public Race (String prefix, boolean useStd3AsciiRules) {
		super(prefix,useStd3AsciiRules);
	}


	/*
	 * By contract with Ace
	 */
	protected char[] internalEncode(int[] input) throws XcodeException {
		return Base32.encode(compress(Unicode.decode(input)));
	}

	/*
	 * By contract with Ace
	 */
	protected int[] internalDecode(char[] input) throws XcodeException {
		return Unicode.encode(decompress(Base32.decode(input)));
	}


	/**
	 * compress - The race algorithm contains two main steps.  The Utf16 input
	 *  data is passed into a compression which yields a sequence of bytes.  This
	 *  byte sequence is then encoded using Base32 to ensure that the data
	 *  is dns compatible.
	 */
	private byte[] compress(char[] input) throws XcodeException {
		// Input checking
		if (input == null) {throw XcodeError.NULL_ARGUMENT();}
		if (input.length == 0) {throw XcodeError.EMPTY_ARGUMENT();}

		// Initialize
		//Debug.log(" compress -> " + Hex.encode(new String(input)));

		// Decarations
		byte[] tmp = new byte[(input.length*2) + 1];
		// The tmp array stores the output temporarily before we know the exact
		// length.  The worst case length for compressed output is input*2 + 1.
		byte[] output;
		int output_offset;
		byte hi,u1,u2,n1;
		boolean isCompressible;

		// Should we compress?  What is the common high byte (u1)?
		u1 = ZERO;
		isCompressible = true;
		for (int i=0; i<input.length; i++) {
			hi = Utf16.getHighByte(input[i]);
			if (hi != ZERO) {
				if (u1 == ZERO) {u1 = hi;}
				else if (hi != u1) {isCompressible = false; break;}
			}
		}

		// Compress the input
		output_offset = 0;
		try {
			if (isCompressible) {

				char char_u1 = (char)(u1 & 0x00ff);
				if (char_u1 >= 0x00d8 && char_u1 <= 0x00df)
				{throw XcodeError.RACE_ENCODE_BAD_SURROGATE_USE();}

				tmp[output_offset++] = u1;
				for (int i=0; i<input.length; i++) {
					if (input[i] == DOUBLE_ESCAPE)
					{throw XcodeError.RACE_ENCODE_DOUBLE_ESCAPE_PRESENT();}
					if (Idna.isDelimiter(input[i])) {
						throw XcodeError.RACE_DECODE_INTERNAL_DELIMITER_FOUND(" "+
						Integer.toString(input[i],16));
					}

					u2 = Utf16.getHighByte(input[i]);
					n1 = Utf16.getLowByte(input[i]);
					if (u2 == u1) {
						if (n1 != DOUBLE_F) {
							tmp[output_offset++] = n1;
						} else {
							tmp[output_offset++] = DOUBLE_F;
							tmp[output_offset++] = DOUBLE_9;
						}
					} else {
						tmp[output_offset++] = DOUBLE_F;
						tmp[output_offset++] = n1;
					}
				}
			} else {
				tmp[output_offset++] = NULL_COMPRESSION_FLAG;
				for (int i=0; i<input.length; i++) {
					tmp[output_offset++] = Utf16.getHighByte(input[i]);
					tmp[output_offset++] = Utf16.getLowByte(input[i]);
				}
			}

			if (output_offset != tmp.length) {
				output = new byte[output_offset];
				System.arraycopy(tmp,0,output,0,output_offset);
				//Debug.log("  copied ["+tmp.length+"] to ["+output_offset+"]");
			} else {
				output = tmp;
				//Debug.log("  no copy ["+tmp.length+"] = ["+output_offset+"]");
			}

		} catch (IndexOutOfBoundsException x) {
			throw XcodeError.RACE_ENCODE_COMPRESSION_OVERFLOW();
		}

		// Ensure compression does not exceed allowed length
		if (output.length > MAX_COMPRESSION_SIZE)
		{throw XcodeError.RACE_ENCODE_COMPRESSION_OVERFLOW();}

		// Finalize
		//Debug.log(" compress <- " + Hex.encode(new String(output)));
		return output;
	}





	/**
	 * decompress - Reverse the Race compression and return a sequence of Utf16
	 *  characters.
	 */
	private char[] decompress(byte[] input) throws XcodeException {
		// Input checking
		if (input == null) {throw XcodeError.NULL_ARGUMENT();}
		if (input.length == 0) {throw XcodeError.EMPTY_ARGUMENT();}

		// Initialize
		//Debug.log(" decompress -> " + Hex.encode(input));

		// Ensure compression does not exceed allowed length
		if (input.length > MAX_COMPRESSION_SIZE)
		{throw XcodeError.RACE_ENCODE_COMPRESSION_OVERFLOW();}

		// Decarations
		char[] tmp = new char[input.length];
		char[] output;
		int output_offset;
		boolean foundInvalidDnsCharacter;
		boolean foundUnescapedOctet;
		boolean foundDoubleF;
		boolean isCompressible;
		byte u1,n1,hi,lo;
		char delta;

		// Process
		foundInvalidDnsCharacter = false;
		foundUnescapedOctet = false;
		foundDoubleF = false;
		isCompressible = true;
		output_offset = 0;

		try {
			if (input.length == 1) {throw XcodeError.RACE_DECODE_ODD_OCTET_COUNT();}
			u1 = input[0];

			// No compression was done, so copy the remaining octets into the output.
			// Ensure that no Delimiters are found in the output.
			// Ensure that the output could not have been compressed.
			if (u1 == NULL_COMPRESSION_FLAG) {
				if (input.length%2==0) {throw XcodeError.RACE_DECODE_ODD_OCTET_COUNT();}
				u1 = ZERO;
				for (int i=1; i<input.length; i+=2) {
					hi = input[i];
					lo = input[i+1];
					delta = (char)((hi<<8) | (lo&0x00ff));

					if (Idna.isDelimiter(delta)) {
						throw XcodeError.RACE_DECODE_INTERNAL_DELIMITER_FOUND(" "+
						Integer.toString(delta,16));
					}

					if (hi != ZERO) {
						if (u1 == ZERO) {u1 = hi;}
						else if (hi != u1) {isCompressible = false;}
						foundInvalidDnsCharacter = true;
					} else {
						if (! Utf16.isDnsCompatible(delta)) {foundInvalidDnsCharacter = true;}
					}
					tmp[output_offset++] = delta;
				}
				if (isCompressible){
					throw XcodeError.RACE_DECODE_IMPROPER_NULL_COMPRESSION();
				}
			} else {

				char char_u1 = (char)(u1 & 0x00ff);
				if (char_u1 >= 0x00d8 && char_u1 <= 0x00df)
				{throw XcodeError.RACE_DECODE_BAD_SURROGATE_DECOMPRESS();}

				for (int i=1; i<input.length; i++) {
					n1 = input[i];
					if (! foundDoubleF) {
						if (n1 == DOUBLE_F) {foundDoubleF = true;}
						else {
							delta = (char)((u1<<8) | (n1&0x00ff));

							if (Idna.isDelimiter(delta)) {
								throw XcodeError.RACE_DECODE_INTERNAL_DELIMITER_FOUND(" "+
								Integer.toString(delta,16));
							}

							if (delta == DOUBLE_ESCAPE)
							{throw XcodeError.RACE_DECODE_DOUBLE_ESCAPE_FOUND();}
							if (! Utf16.isDnsCompatible(delta))
							{foundInvalidDnsCharacter = true;}
							foundUnescapedOctet = true;
							tmp[output_offset++] = delta;
						}
					} else {
						foundDoubleF = false;
						if (n1 == DOUBLE_9) {
							delta = (char)((u1<<8) | (0x00ff));
							foundUnescapedOctet = true;
							foundInvalidDnsCharacter = true;
							tmp[output_offset++] = delta;
						} else {
							delta = (char)(n1 & 0x00ff);
							if (u1 == ZERO) {throw XcodeError.RACE_DECODE_UNNEEDED_ESCAPE_PRESENT();}

							if (Idna.isDelimiter(delta)) {
								throw XcodeError.RACE_DECODE_INTERNAL_DELIMITER_FOUND(" "+
								Integer.toString(delta,16));
							}

							if (! Utf16.isDnsCompatible(delta))
							{foundInvalidDnsCharacter = true;}
							tmp[output_offset++] = delta;
						}
					}
				}
				if (foundDoubleF)
				{throw XcodeError.RACE_DECODE_TRAILING_ESCAPE_PRESENT();}
				if (u1 != ZERO && !foundUnescapedOctet)
				{throw XcodeError.RACE_DECODE_NO_UNESCAPED_OCTETS();}
			}
			if (! foundInvalidDnsCharacter)
			{throw XcodeError.RACE_DECODE_NO_INVALID_DNS_CHARACTERS();}

			if (output_offset != tmp.length) {
				output = new char[output_offset];
				System.arraycopy(tmp,0,output,0,output_offset);
				//Debug.log("  copied ["+tmp.length+"] to ["+output_offset+"]");
			} else {
				output = tmp;
				//Debug.log("  no copy ["+tmp.length+"] = ["+output_offset+"]");
			}


		} catch (IndexOutOfBoundsException x) {
			throw XcodeError.RACE_DECODE_DECOMPRESSION_OVERFLOW();
		}

		// Finalize
		//Debug.log(" decompress <- " + Hex.encode(output));
		return output;
	}

}
