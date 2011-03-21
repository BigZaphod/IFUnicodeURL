/*************************************************************************/
/*                                                                       */
/* Base32.java                                                           */
/*                                                                       */
/* (c) VeriSign Inc., 2000-2003, All rights reserved                     */
/*                                                                       */
/* @author: Srikanth Veeramachaneni                                      */
/* @date: 09/10/2002                                                     */
/*                                                                       */
/*************************************************************************/

package com.vgrs.xcode.common;

import com.vgrs.xcode.util.*;

/**
 * Implements a Base 32 algorithm with encode and decode operations.  The
 * encode operation converts data on the range [0x00 - 0xff] to data on
 * the range [a-z, 2-7].  The following table describes the conversion:
 *
 * <p>
 * <table border=1>
 * <tr><td colspan=2>input</td><td colspan=2> base-32</td></tr>
 *
 * <tr><td>bits</td><td>hex</td>   <td>hex</td><td> char</td></tr>
 *
 * <tr><td>00000</td><td>0x00</td> <td>0x61</td><td>a</td></tr>
 * <tr><td>00001</td><td>0x01</td> <td>0x62</td><td>b</td></tr>
 * <tr><td>00010</td><td>0x02</td> <td>0x63</td><td>c</td></tr>
 * <tr><td>00011</td><td>0x03</td> <td>0x64</td><td>d</td></tr>
 * <tr><td>00100</td><td>0x04</td> <td>0x65</td><td>e</td></tr>
 * <tr><td>00101</td><td>0x05</td> <td>0x66</td><td>f</td></tr>
 * <tr><td>00110</td><td>0x06</td> <td>0x67</td><td>g</td></tr>
 * <tr><td>00111</td><td>0x07</td> <td>0x68</td><td>h</td></tr>
 * <tr><td>01000</td><td>0x08</td> <td>0x69</td><td>i</td></tr>
 * <tr><td>01001</td><td>0x09</td> <td>0x6A</td><td>j</td></tr>
 * <tr><td>01010</td><td>0x0A</td> <td>0x6B</td><td>k</td></tr>
 * <tr><td>01011</td><td>0x0B</td> <td>0x6C</td><td>l</td></tr>
 * <tr><td>01100</td><td>0x0C</td> <td>0x6D</td><td>m</td></tr>
 * <tr><td>01101</td><td>0x0D</td> <td>0x6E</td><td>n</td></tr>
 * <tr><td>01110</td><td>0x0E</td> <td>0x6F</td><td>o</td></tr>
 * <tr><td>01111</td><td>0x0F</td> <td>0x70</td><td>p</td></tr>
 * <tr><td>10000</td><td>0x10</td> <td>0x71</td><td>q</td></tr>
 * <tr><td>10001</td><td>0x11</td> <td>0x72</td><td>r</td></tr>
 * <tr><td>10010</td><td>0x12</td> <td>0x73</td><td>s</td></tr>
 * <tr><td>10011</td><td>0x13</td> <td>0x74</td><td>t</td></tr>
 * <tr><td>10100</td><td>0x14</td> <td>0x75</td><td>u</td></tr>
 * <tr><td>10101</td><td>0x15</td> <td>0x76</td><td>v</td></tr>
 * <tr><td>10110</td><td>0x16</td> <td>0x77</td><td>w</td></tr>
 * <tr><td>10111</td><td>0x17</td> <td>0x78</td><td>x</td></tr>
 * <tr><td>11000</td><td>0x18</td> <td>0x79</td><td>y</td></tr>
 * <tr><td>11001</td><td>0x19</td> <td>0x7A</td><td>z</td></tr>
 *
 * <tr><td>11010</td><td>0x1A</td> <td>0x32</td><td>2</td></tr>
 * <tr><td>11011</td><td>0x1B</td> <td>0x33</td><td>3</td></tr>
 * <tr><td>11100</td><td>0x1C</td> <td>0x34</td><td>4</td></tr>
 * <tr><td>11101</td><td>0x1D</td> <td>0x35</td><td>5</td></tr>
 * <tr><td>11110</td><td>0x1E</td> <td>0x36</td><td>6</td></tr>
 * <tr><td>11111</td><td>0x1F</td> <td>0x37</td><td>7</td></tr>
 * </table>
 * <p>
 *
 * data of size 1 bytes will be converted to 2 base-32 characters.<br>
 * data of size 2 bytes will be converted to 4 base-32 characters.<br>
 * data of size 3 bytes will be converted to 5 base-32 characters.<br>
 * data of size 4 bytes will be converted to 7 base-32 characters.<br>
 * data of size 5 bytes will be converted to 8 base-32 characters.<br>
 */
public final class Base32 {

	/**
	 * Value of Ox0000
	 */
	public static final byte ZERO = 0x0000;


	/**
	 * Encode a byte array into a base 32 sequence
	 * @param input the array of bytes on the range [0x00 - 0xff]
	 * @return Base 32 encoded string
	 * @throws XcodeException if the input string is null or with length == 0
	 *         or the input cannot be convert to a base-32 string.
	 */
	public static char[] encode(byte[] input) throws XcodeException {

		if (input == null) throw XcodeError.NULL_ARGUMENT();
		if (input.length == 0) throw XcodeError.EMPTY_ARGUMENT();

		int outputLength = (input.length * 8 + 4) / 5;

		char[] output = new char[outputLength];
		int offset = 0;
		byte last = ZERO;
		byte next = ZERO;

		try {
			for (int i=0; i<input.length; i++) {
				last = next;
				next = input[i];

				//
				// process the input as blocks of size 5 bytes
				// each 5 byte block is mapped to 8 base-32 characters
				//
				switch (i % 5) {
					case 0:
					//
					// base32[1] = first 5 bits of byte 1
					//
					output[offset++] = map((byte) ((next & 0xF8) >> 3));
					break;
					case 1:
					//
					// base32[2] = last 3 bits of byte 1 and first 2 bits of byte 2
					// base32[3] = bits 3-7 of byte 2
					//
					output[offset++] = map((byte) (((last & 0x07) << 2) | ((next & 0xC0) >> 6)));
					output[offset++] = map((byte) ((next & 0x3E) >> 1));
					break;
					case 2:
					//
					// base32[4] = last bit of byte 2 and first 4 bits of byte 3
					//
					output[offset++] = map((byte) (((last & 0x01) << 4) | ((next & 0xF0) >> 4)));
					break;
					case 3:
					//
					// base32[5] = last 4 bits of byte 3 and first bit of byte 4
					// base32[6] = bits 2-6 of byte 4
					//
					output[offset++] = map((byte) (((last & 0x0F) << 1) | ((next & 0x80) >> 7)));
					output[offset++] = map((byte) ((next & 0x7C) >> 2));
					break;
					case 4:
					//
					// base32[7] = last 2 bits of byte 4 and first 3 bits of byte 5
					// base32[8] = last 5 bits of byte 5
					//
					output[offset++] = map((byte) (((last & 0x03) << 3) | ((next & 0xE0) >> 5)));
					output[offset++] = map((byte) (next & 0x1F));
					break;
				} // END switch()
			} // END for()


			//
			// if the input size is not a multiple of 5 set the last base-32 character
			//
			switch (input.length % 5) {
				case 1:
				//
				// last 3 bits of byte 1
				//
				output[offset++] = map((byte) ((next & 0x0007) << 2));
				break;
				case 2:
				//
				// last bit of byte 2
				//
				output[offset++] = map((byte) ((next & 0x0001) << 4));
				break;
				case 3:
				//
				// last 4 bits of byte 3
				//
				output[offset++] = map((byte) ((next & 0x000F) << 1));
				break;
				case 4:
				//
				// last 2 bits of byte 4
				//
				output[offset++] = map((byte) ((next & 0x0003) << 3));
				break;
			} // END switch()

		}
		catch (IndexOutOfBoundsException x) {
			throw XcodeError.BASE32_ENCODE_BIT_OVERFLOW();
		}

		return output;

	} // END encode()




	/**
	 * Decode a base 32 sequence into an array of bytes
	 * @param input Base 32 encoded string
	 * @return a byte array
	 * @throws XcodeException if the input string is null or with length == 0
	 *          or the input cannot be convert to a native string.
	 */
	public static byte[] decode(char[] input) throws XcodeException {

		if (input == null) throw XcodeError.NULL_ARGUMENT();
		if (input.length == 0) throw XcodeError.EMPTY_ARGUMENT();

		//
		// check if the input is of the right size
		//
		// the only possible of sizes are (x * 8) + ( 2 or 4 or 5 or 7 or 8 )
		//
		int mod8 = input.length % 8;
		if (mod8 == 1 || mod8 == 3 || mod8 == 6) {
			throw XcodeError.BASE32_DECODE_INVALID_SIZE();
		}

		int outputLength = (input.length * 5) / 8;

		byte[] output = new byte[outputLength];
		int outputOffset = 0;
		int inputOffset = 0;
		int target = 0;
		byte cursor = ZERO;
		byte delta = ZERO;


		try {
			while (inputOffset < input.length) {
				delta = ZERO;
				switch (target % 5) {
					case 0:
					//
					// byte 1 = 5 bits of base32 1
					///       + first 3 bits of base32 2
					//
					cursor = demap(input[inputOffset++]);
					delta |= (cursor << 3);
					cursor = demap(input[inputOffset++]);
					delta |= (cursor >> 2);
					output[outputOffset++] = delta;
					break;
					case 1:
					//
					// byte 2 = last 2 bits of base32 2
					//             + 5 bits of base32 3
					//             + first bit of base32 4
					//
					delta |= ((cursor & 0x0003) << 6);
					cursor = demap(input[inputOffset++]);
					delta |= (cursor << 1);
					cursor = demap(input[inputOffset++]);
					delta |= (cursor >> 4);
					output[outputOffset++] = delta;
					break;
					case 2:
					//
					// byte 3 = last 4 bits of base32 4
					//             + first 4 bits of base32 5
					//
					delta |= ((cursor & 0x000f) << 4);
					cursor = demap(input[inputOffset++]);
					delta |= (cursor >> 1);
					output[outputOffset++] = delta;
					break;
					case 3:
					//
					// byte 4 = last bit of base32 5
					//          + 5 bits of base32 6
					//          + first 2 bits of base32 7
					//
					delta |= ((cursor & 0x0001) << 7);
					cursor = demap(input[inputOffset++]);
					delta |= (cursor << 2);
					cursor = demap(input[inputOffset++]);
					delta |= (cursor >> 3);
					output[outputOffset++] = delta;
					break;
					case 4:
					//
					// byte 5 = last 3 bits of base32 7
					//             + 5 bits of base32 8
					//
					delta |= ((cursor & 0x0007) << 5);
					cursor = demap(input[inputOffset++]);
					delta |= cursor;
					output[outputOffset++] = delta;
					break;
				} // END switch()
				target++;
			}

			//
			// make sure that the bits in the last base32 character are right
			//
			int mod5 = target % 5;
			if ((mod5 == 1 && (cursor & 0x03) > 0)
				|| (mod5 == 2 && (cursor & 0x0F) > 0)
				|| (mod5 == 3 && (cursor & 0x01) > 0)
			|| (mod5 == 4 && (cursor & 0x07) > 0))
			{
				throw XcodeError.BASE32_DECODE_INVALID_BIT_SEQUENCE();
			}

		}
		catch (IndexOutOfBoundsException x) {
			throw XcodeError.BASE32_DECODE_BIT_OVERFLOW();
		}

		return output;

	} // END decode()




	/**
	 * @param input a byte on the range [0x00 - 0xff]
	 * @return a Base 32 byte on the range [a-z, 0-9]
	 * @throws XcodeException if the input byte > 0x1F
	 */
	private static char map(byte input) throws XcodeException {
		if (input <= 0x19) return (char) ((int) input + (int) 0x61);
		else if (input <= 0x1F) return (char) ((int) input + (int) 0x18);
		else throw XcodeError.BASE32_MAP_BIT_OVERFLOW();
	}

	/**
	 * @param input a Base 32 byte on the range [a-z, 0-9]
	 * @return a byte on the range [0x00 - 0xff]
	 * @throws XcodeException if the input byte is an valid Base 32 character
	 *         i.e. the input < 0x32 or > ox7A
	 */

	private static byte demap(char input) throws XcodeException {
		if (input >= 0x32 && input <= 0x37) {
			return (byte) (input - 0x18);
		} else if (input >= 0x41 && input <= 0x5A) {
			return (byte) (input - 0x41);
		} else if (input >= 0x61 && input <= 0x7A) {
			return (byte) (input - 0x61);
		} else {
			throw XcodeError.BASE32_DEMAP_INVALID_BASE32_CHAR();
		}
	}

} // END class Base32
