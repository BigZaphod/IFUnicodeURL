/*
 * This source file is constructed from the ErrorCodes.txt
 * file in the data directory.  Do not modify.
 */

package com.vgrs.xcode.util;
public class XcodeError {


/*
 * SUCCESS: 0
 */
 static public XcodeException SUCCESS() {
  return new XcodeException(
   0,
   "Successful execution"
  );
 }
 static public XcodeException SUCCESS(String msg) {
  return new XcodeException(
   0,
   "Successful execution"+msg
  );
 }
 static public boolean is_SUCCESS(XcodeException x) {
  return (x.getCode() == 0);
 }


/*
 * INVALID_ARGUMENT: 1
 */
 static public XcodeException INVALID_ARGUMENT() {
  return new XcodeException(
   1,
   "Invalid Argument"
  );
 }
 static public XcodeException INVALID_ARGUMENT(String msg) {
  return new XcodeException(
   1,
   "Invalid Argument"+msg
  );
 }
 static public boolean is_INVALID_ARGUMENT(XcodeException x) {
  return (x.getCode() == 1);
 }


/*
 * EMPTY_ARGUMENT: 2
 */
 static public XcodeException EMPTY_ARGUMENT() {
  return new XcodeException(
   2,
   "Empty Argument"
  );
 }
 static public XcodeException EMPTY_ARGUMENT(String msg) {
  return new XcodeException(
   2,
   "Empty Argument"+msg
  );
 }
 static public boolean is_EMPTY_ARGUMENT(XcodeException x) {
  return (x.getCode() == 2);
 }


/*
 * NULL_ARGUMENT: 3
 */
 static public XcodeException NULL_ARGUMENT() {
  return new XcodeException(
   3,
   "Null Argument"
  );
 }
 static public XcodeException NULL_ARGUMENT(String msg) {
  return new XcodeException(
   3,
   "Null Argument"+msg
  );
 }
 static public boolean is_NULL_ARGUMENT(XcodeException x) {
  return (x.getCode() == 3);
 }


/*
 * FILE_IO: 4
 */
 static public XcodeException FILE_IO() {
  return new XcodeException(
   4,
   "File i/o failure"
  );
 }
 static public XcodeException FILE_IO(String msg) {
  return new XcodeException(
   4,
   "File i/o failure"+msg
  );
 }
 static public boolean is_FILE_IO(XcodeException x) {
  return (x.getCode() == 4);
 }


/*
 * INVALID_FILE_FORMAT: 5
 */
 static public XcodeException INVALID_FILE_FORMAT() {
  return new XcodeException(
   5,
   "Invalid file format"
  );
 }
 static public XcodeException INVALID_FILE_FORMAT(String msg) {
  return new XcodeException(
   5,
   "Invalid file format"+msg
  );
 }
 static public boolean is_INVALID_FILE_FORMAT(XcodeException x) {
  return (x.getCode() == 5);
 }


/*
 * UNSUPPORTED_ENCODING: 6
 */
 static public XcodeException UNSUPPORTED_ENCODING() {
  return new XcodeException(
   6,
   "Unsupported encoding"
  );
 }
 static public XcodeException UNSUPPORTED_ENCODING(String msg) {
  return new XcodeException(
   6,
   "Unsupported encoding"+msg
  );
 }
 static public boolean is_UNSUPPORTED_ENCODING(XcodeException x) {
  return (x.getCode() == 6);
 }


/*
 * HEX_DECODE_INVALID_FORMAT: 100
 */
 static public XcodeException HEX_DECODE_INVALID_FORMAT() {
  return new XcodeException(
   100,
   "Found characters which do not represent a hex value"
  );
 }
 static public XcodeException HEX_DECODE_INVALID_FORMAT(String msg) {
  return new XcodeException(
   100,
   "Found characters which do not represent a hex value"+msg
  );
 }
 static public boolean is_HEX_DECODE_INVALID_FORMAT(XcodeException x) {
  return (x.getCode() == 100);
 }


/*
 * HEX_DECODE_ONE_BYTE_EXCEEDED: 101
 */
 static public XcodeException HEX_DECODE_ONE_BYTE_EXCEEDED() {
  return new XcodeException(
   101,
   "Value of input characters exceeds 0xff"
  );
 }
 static public XcodeException HEX_DECODE_ONE_BYTE_EXCEEDED(String msg) {
  return new XcodeException(
   101,
   "Value of input characters exceeds 0xff"+msg
  );
 }
 static public boolean is_HEX_DECODE_ONE_BYTE_EXCEEDED(XcodeException x) {
  return (x.getCode() == 101);
 }


/*
 * HEX_DECODE_TWO_BYTES_EXCEEDED: 102
 */
 static public XcodeException HEX_DECODE_TWO_BYTES_EXCEEDED() {
  return new XcodeException(
   102,
   "Value of input characters exceeds 0xffff"
  );
 }
 static public XcodeException HEX_DECODE_TWO_BYTES_EXCEEDED(String msg) {
  return new XcodeException(
   102,
   "Value of input characters exceeds 0xffff"+msg
  );
 }
 static public boolean is_HEX_DECODE_TWO_BYTES_EXCEEDED(XcodeException x) {
  return (x.getCode() == 102);
 }


/*
 * HEX_DECODE_FOUR_BYTES_EXCEEDED: 103
 */
 static public XcodeException HEX_DECODE_FOUR_BYTES_EXCEEDED() {
  return new XcodeException(
   103,
   "Value of input characters exceeds 0xffffffff"
  );
 }
 static public XcodeException HEX_DECODE_FOUR_BYTES_EXCEEDED(String msg) {
  return new XcodeException(
   103,
   "Value of input characters exceeds 0xffffffff"+msg
  );
 }
 static public boolean is_HEX_DECODE_FOUR_BYTES_EXCEEDED(XcodeException x) {
  return (x.getCode() == 103);
 }


/*
 * ACE_ENCODE_NOT_STD3ASCII: 200
 */
 static public XcodeException ACE_ENCODE_NOT_STD3ASCII() {
  return new XcodeException(
   200,
   "Input does not meet STD3 rules for domain name format."
  );
 }
 static public XcodeException ACE_ENCODE_NOT_STD3ASCII(String msg) {
  return new XcodeException(
   200,
   "Input does not meet STD3 rules for domain name format."+msg
  );
 }
 static public boolean is_ACE_ENCODE_NOT_STD3ASCII(XcodeException x) {
  return (x.getCode() == 200);
 }


/*
 * ACE_ENCODE_INVALID_OUTPUT_LENGTH: 201
 */
 static public XcodeException ACE_ENCODE_INVALID_OUTPUT_LENGTH() {
  return new XcodeException(
   201,
   "Resulting Ace sequence is too long or too short."
  );
 }
 static public XcodeException ACE_ENCODE_INVALID_OUTPUT_LENGTH(String msg) {
  return new XcodeException(
   201,
   "Resulting Ace sequence is too long or too short."+msg
  );
 }
 static public boolean is_ACE_ENCODE_INVALID_OUTPUT_LENGTH(XcodeException x) {
  return (x.getCode() == 201);
 }


/*
 * ACE_ENCODE_VALID_PREFIX: 202
 */
 static public XcodeException ACE_ENCODE_VALID_PREFIX() {
  return new XcodeException(
   202,
   "The input sequence already has an ACE prefix."
  );
 }
 static public XcodeException ACE_ENCODE_VALID_PREFIX(String msg) {
  return new XcodeException(
   202,
   "The input sequence already has an ACE prefix."+msg
  );
 }
 static public boolean is_ACE_ENCODE_VALID_PREFIX(XcodeException x) {
  return (x.getCode() == 202);
 }


/*
 * ACE_DECODE_NOT_STD3ASCII: 203
 */
 static public XcodeException ACE_DECODE_NOT_STD3ASCII() {
  return new XcodeException(
   203,
   "Output does not meet STD3 rules for domain name format."
  );
 }
 static public XcodeException ACE_DECODE_NOT_STD3ASCII(String msg) {
  return new XcodeException(
   203,
   "Output does not meet STD3 rules for domain name format."+msg
  );
 }
 static public boolean is_ACE_DECODE_NOT_STD3ASCII(XcodeException x) {
  return (x.getCode() == 203);
 }


/*
 * ACE_ENCODE_PREFIX_FOUND: 204
 */
 static public XcodeException ACE_ENCODE_PREFIX_FOUND() {
  return new XcodeException(
   204,
   "Input begins with a valid prefix."
  );
 }
 static public XcodeException ACE_ENCODE_PREFIX_FOUND(String msg) {
  return new XcodeException(
   204,
   "Input begins with a valid prefix."+msg
  );
 }
 static public boolean is_ACE_ENCODE_PREFIX_FOUND(XcodeException x) {
  return (x.getCode() == 204);
 }


/*
 * RACE_ENCODE_BAD_SURROGATE_USE: 300
 */
 static public XcodeException RACE_ENCODE_BAD_SURROGATE_USE() {
  return new XcodeException(
   300,
   "Surrogates should be ordered pairs of high,low during race encoding."
  );
 }
 static public XcodeException RACE_ENCODE_BAD_SURROGATE_USE(String msg) {
  return new XcodeException(
   300,
   "Surrogates should be ordered pairs of high,low during race encoding."+msg
  );
 }
 static public boolean is_RACE_ENCODE_BAD_SURROGATE_USE(XcodeException x) {
  return (x.getCode() == 300);
 }


/*
 * RACE_ENCODE_DOUBLE_ESCAPE_PRESENT: 301
 */
 static public XcodeException RACE_ENCODE_DOUBLE_ESCAPE_PRESENT() {
  return new XcodeException(
   301,
   "The codepoint 0x0099 is not allowed during race encoding."
  );
 }
 static public XcodeException RACE_ENCODE_DOUBLE_ESCAPE_PRESENT(String msg) {
  return new XcodeException(
   301,
   "The codepoint 0x0099 is not allowed during race encoding."+msg
  );
 }
 static public boolean is_RACE_ENCODE_DOUBLE_ESCAPE_PRESENT(XcodeException x) {
  return (x.getCode() == 301);
 }


/*
 * RACE_ENCODE_COMPRESSION_OVERFLOW: 302
 */
 static public XcodeException RACE_ENCODE_COMPRESSION_OVERFLOW() {
  return new XcodeException(
   302,
   "The compressed input length exceeds expected octets during race encode."
  );
 }
 static public XcodeException RACE_ENCODE_COMPRESSION_OVERFLOW(String msg) {
  return new XcodeException(
   302,
   "The compressed input length exceeds expected octets during race encode."+msg
  );
 }
 static public boolean is_RACE_ENCODE_COMPRESSION_OVERFLOW(XcodeException x) {
  return (x.getCode() == 302);
 }


/*
 * RACE_ENCODE_INTERNAL_DELIMITER_PRESENT: 303
 */
 static public XcodeException RACE_ENCODE_INTERNAL_DELIMITER_PRESENT() {
  return new XcodeException(
   303,
   "Input contains a delimiter"
  );
 }
 static public XcodeException RACE_ENCODE_INTERNAL_DELIMITER_PRESENT(String msg) {
  return new XcodeException(
   303,
   "Input contains a delimiter"+msg
  );
 }
 static public boolean is_RACE_ENCODE_INTERNAL_DELIMITER_PRESENT(XcodeException x) {
  return (x.getCode() == 303);
 }


/*
 * RACE_DECODE_ODD_OCTET_COUNT: 304
 */
 static public XcodeException RACE_DECODE_ODD_OCTET_COUNT() {
  return new XcodeException(
   304,
   "Compression indicates an odd number of compressed octets."
  );
 }
 static public XcodeException RACE_DECODE_ODD_OCTET_COUNT(String msg) {
  return new XcodeException(
   304,
   "Compression indicates an odd number of compressed octets."+msg
  );
 }
 static public boolean is_RACE_DECODE_ODD_OCTET_COUNT(XcodeException x) {
  return (x.getCode() == 304);
 }


/*
 * RACE_DECODE_BAD_SURROGATE_DECOMPRESS: 305
 */
 static public XcodeException RACE_DECODE_BAD_SURROGATE_DECOMPRESS() {
  return new XcodeException(
   305,
   "Compression indicates a stream of identical surrogates."
  );
 }
 static public XcodeException RACE_DECODE_BAD_SURROGATE_DECOMPRESS(String msg) {
  return new XcodeException(
   305,
   "Compression indicates a stream of identical surrogates."+msg
  );
 }
 static public boolean is_RACE_DECODE_BAD_SURROGATE_DECOMPRESS(XcodeException x) {
  return (x.getCode() == 305);
 }


/*
 * RACE_DECODE_IMPROPER_NULL_COMPRESSION: 306
 */
 static public XcodeException RACE_DECODE_IMPROPER_NULL_COMPRESSION() {
  return new XcodeException(
   306,
   "Sequence could have been compressed but was not."
  );
 }
 static public XcodeException RACE_DECODE_IMPROPER_NULL_COMPRESSION(String msg) {
  return new XcodeException(
   306,
   "Sequence could have been compressed but was not."+msg
  );
 }
 static public boolean is_RACE_DECODE_IMPROPER_NULL_COMPRESSION(XcodeException x) {
  return (x.getCode() == 306);
 }


/*
 * RACE_DECODE_INTERNAL_DELIMITER_FOUND: 307
 */
 static public XcodeException RACE_DECODE_INTERNAL_DELIMITER_FOUND() {
  return new XcodeException(
   307,
   "Found a delimiter while decoding a label"
  );
 }
 static public XcodeException RACE_DECODE_INTERNAL_DELIMITER_FOUND(String msg) {
  return new XcodeException(
   307,
   "Found a delimiter while decoding a label"+msg
  );
 }
 static public boolean is_RACE_DECODE_INTERNAL_DELIMITER_FOUND(XcodeException x) {
  return (x.getCode() == 307);
 }


/*
 * RACE_DECODE_DOUBLE_ESCAPE_FOUND: 308
 */
 static public XcodeException RACE_DECODE_DOUBLE_ESCAPE_FOUND() {
  return new XcodeException(
   308,
   "The codepoint 0x0099 was found during race decoding."
  );
 }
 static public XcodeException RACE_DECODE_DOUBLE_ESCAPE_FOUND(String msg) {
  return new XcodeException(
   308,
   "The codepoint 0x0099 was found during race decoding."+msg
  );
 }
 static public boolean is_RACE_DECODE_DOUBLE_ESCAPE_FOUND(XcodeException x) {
  return (x.getCode() == 308);
 }


/*
 * RACE_DECODE_UNNEEDED_ESCAPE_PRESENT: 309
 */
 static public XcodeException RACE_DECODE_UNNEEDED_ESCAPE_PRESENT() {
  return new XcodeException(
   309,
   "Found a double f escape character when u1 is zero."
  );
 }
 static public XcodeException RACE_DECODE_UNNEEDED_ESCAPE_PRESENT(String msg) {
  return new XcodeException(
   309,
   "Found a double f escape character when u1 is zero."+msg
  );
 }
 static public boolean is_RACE_DECODE_UNNEEDED_ESCAPE_PRESENT(XcodeException x) {
  return (x.getCode() == 309);
 }


/*
 * RACE_DECODE_TRAILING_ESCAPE_PRESENT: 310
 */
 static public XcodeException RACE_DECODE_TRAILING_ESCAPE_PRESENT() {
  return new XcodeException(
   310,
   "Found a double f escape character at the end of a sequence."
  );
 }
 static public XcodeException RACE_DECODE_TRAILING_ESCAPE_PRESENT(String msg) {
  return new XcodeException(
   310,
   "Found a double f escape character at the end of a sequence."+msg
  );
 }
 static public boolean is_RACE_DECODE_TRAILING_ESCAPE_PRESENT(XcodeException x) {
  return (x.getCode() == 310);
 }


/*
 * RACE_DECODE_NO_UNESCAPED_OCTETS: 311
 */
 static public XcodeException RACE_DECODE_NO_UNESCAPED_OCTETS() {
  return new XcodeException(
   311,
   "The u1 character is non-zero, but all octets are escaped."
  );
 }
 static public XcodeException RACE_DECODE_NO_UNESCAPED_OCTETS(String msg) {
  return new XcodeException(
   311,
   "The u1 character is non-zero, but all octets are escaped."+msg
  );
 }
 static public boolean is_RACE_DECODE_NO_UNESCAPED_OCTETS(XcodeException x) {
  return (x.getCode() == 311);
 }


/*
 * RACE_DECODE_NO_INVALID_DNS_CHARACTERS: 312
 */
 static public XcodeException RACE_DECODE_NO_INVALID_DNS_CHARACTERS() {
  return new XcodeException(
   312,
   "Sequence should not have been encoded."
  );
 }
 static public XcodeException RACE_DECODE_NO_INVALID_DNS_CHARACTERS(String msg) {
  return new XcodeException(
   312,
   "Sequence should not have been encoded."+msg
  );
 }
 static public boolean is_RACE_DECODE_NO_INVALID_DNS_CHARACTERS(XcodeException x) {
  return (x.getCode() == 312);
 }


/*
 * RACE_DECODE_DECOMPRESSION_OVERFLOW: 313
 */
 static public XcodeException RACE_DECODE_DECOMPRESSION_OVERFLOW() {
  return new XcodeException(
   313,
   "Decompressed sequence exceeds size limitations."
  );
 }
 static public XcodeException RACE_DECODE_DECOMPRESSION_OVERFLOW(String msg) {
  return new XcodeException(
   313,
   "Decompressed sequence exceeds size limitations."+msg
  );
 }
 static public boolean is_RACE_DECODE_DECOMPRESSION_OVERFLOW(XcodeException x) {
  return (x.getCode() == 313);
 }


/*
 * RACE_DECODE_5BIT_UNDERFLOW: 314
 */
 static public XcodeException RACE_DECODE_5BIT_UNDERFLOW() {
  return new XcodeException(
   314,
   "Too few pentets to create a whole number of octets."
  );
 }
 static public XcodeException RACE_DECODE_5BIT_UNDERFLOW(String msg) {
  return new XcodeException(
   314,
   "Too few pentets to create a whole number of octets."+msg
  );
 }
 static public boolean is_RACE_DECODE_5BIT_UNDERFLOW(XcodeException x) {
  return (x.getCode() == 314);
 }


/*
 * RACE_DECODE_5BIT_OVERFLOW: 315
 */
 static public XcodeException RACE_DECODE_5BIT_OVERFLOW() {
  return new XcodeException(
   315,
   "Too many pentets to create a whole number of octets."
  );
 }
 static public XcodeException RACE_DECODE_5BIT_OVERFLOW(String msg) {
  return new XcodeException(
   315,
   "Too many pentets to create a whole number of octets."+msg
  );
 }
 static public boolean is_RACE_DECODE_5BIT_OVERFLOW(XcodeException x) {
  return (x.getCode() == 315);
 }


/*
 * PUNYCODE_OVERFLOW: 400
 */
 static public XcodeException PUNYCODE_OVERFLOW() {
  return new XcodeException(
   400,
   "The code point exceeded maximum value allowed."
  );
 }
 static public XcodeException PUNYCODE_OVERFLOW(String msg) {
  return new XcodeException(
   400,
   "The code point exceeded maximum value allowed."+msg
  );
 }
 static public boolean is_PUNYCODE_OVERFLOW(XcodeException x) {
  return (x.getCode() == 400);
 }


/*
 * PUNYCODE_BAD_OUTPUT: 401
 */
 static public XcodeException PUNYCODE_BAD_OUTPUT() {
  return new XcodeException(
   401,
   "Bad output encountered while trying to decode the string."
  );
 }
 static public XcodeException PUNYCODE_BAD_OUTPUT(String msg) {
  return new XcodeException(
   401,
   "Bad output encountered while trying to decode the string."+msg
  );
 }
 static public boolean is_PUNYCODE_BAD_OUTPUT(XcodeException x) {
  return (x.getCode() == 401);
 }


/*
 * PUNYCODE_BIG_OUTPUT: 402
 */
 static public XcodeException PUNYCODE_BIG_OUTPUT() {
  return new XcodeException(
   402,
   "The output length exceeds expected characters."
  );
 }
 static public XcodeException PUNYCODE_BIG_OUTPUT(String msg) {
  return new XcodeException(
   402,
   "The output length exceeds expected characters."+msg
  );
 }
 static public boolean is_PUNYCODE_BIG_OUTPUT(XcodeException x) {
  return (x.getCode() == 402);
 }


/*
 * PUNYCODE_DECODE_DNS_COMPATIBLE: 403
 */
 static public XcodeException PUNYCODE_DECODE_DNS_COMPATIBLE() {
  return new XcodeException(
   403,
   "Invalid encoding contains no international data."
  );
 }
 static public XcodeException PUNYCODE_DECODE_DNS_COMPATIBLE(String msg) {
  return new XcodeException(
   403,
   "Invalid encoding contains no international data."+msg
  );
 }
 static public boolean is_PUNYCODE_DECODE_DNS_COMPATIBLE(XcodeException x) {
  return (x.getCode() == 403);
 }


/*
 * PUNYCODE_DECODE_INTERNAL_DELIMITER_FOUND: 404
 */
 static public XcodeException PUNYCODE_DECODE_INTERNAL_DELIMITER_FOUND() {
  return new XcodeException(
   404,
   "Found a delimiter while decoding a label"
  );
 }
 static public XcodeException PUNYCODE_DECODE_INTERNAL_DELIMITER_FOUND(String msg) {
  return new XcodeException(
   404,
   "Found a delimiter while decoding a label"+msg
  );
 }
 static public boolean is_PUNYCODE_DECODE_INTERNAL_DELIMITER_FOUND(XcodeException x) {
  return (x.getCode() == 404);
 }


/*
 * CHARMAP_OVERFLOW: 500
 */
 static public XcodeException CHARMAP_OVERFLOW() {
  return new XcodeException(
   500,
   "The output length exceeds expected characters during character mapping."
  );
 }
 static public XcodeException CHARMAP_OVERFLOW(String msg) {
  return new XcodeException(
   500,
   "The output length exceeds expected characters during character mapping."+msg
  );
 }
 static public boolean is_CHARMAP_OVERFLOW(XcodeException x) {
  return (x.getCode() == 500);
 }


/*
 * CHARMAP_LABEL_ELIMINATION: 501
 */
 static public XcodeException CHARMAP_LABEL_ELIMINATION() {
  return new XcodeException(
   501,
   "All input characters were mapped out during character mapping."
  );
 }
 static public XcodeException CHARMAP_LABEL_ELIMINATION(String msg) {
  return new XcodeException(
   501,
   "All input characters were mapped out during character mapping."+msg
  );
 }
 static public boolean is_CHARMAP_LABEL_ELIMINATION(XcodeException x) {
  return (x.getCode() == 501);
 }


/*
 * NORMALIZE_BAD_CANONICALCLASS_ERROR: 600
 */
 static public XcodeException NORMALIZE_BAD_CANONICALCLASS_ERROR() {
  return new XcodeException(
   600,
   "Bad canonical class"
  );
 }
 static public XcodeException NORMALIZE_BAD_CANONICALCLASS_ERROR(String msg) {
  return new XcodeException(
   600,
   "Bad canonical class"+msg
  );
 }
 static public boolean is_NORMALIZE_BAD_CANONICALCLASS_ERROR(XcodeException x) {
  return (x.getCode() == 600);
 }


/*
 * NORMALIZE_BAD_COMPATTAG_ERROR: 601
 */
 static public XcodeException NORMALIZE_BAD_COMPATTAG_ERROR() {
  return new XcodeException(
   601,
   "Bad compatibility tag"
  );
 }
 static public XcodeException NORMALIZE_BAD_COMPATTAG_ERROR(String msg) {
  return new XcodeException(
   601,
   "Bad compatibility tag"+msg
  );
 }
 static public boolean is_NORMALIZE_BAD_COMPATTAG_ERROR(XcodeException x) {
  return (x.getCode() == 601);
 }


/*
 * NORMALIZE_BAD_DECOMPSEQUENCE_ERROR: 602
 */
 static public XcodeException NORMALIZE_BAD_DECOMPSEQUENCE_ERROR() {
  return new XcodeException(
   602,
   "Bad decomposition sequence"
  );
 }
 static public XcodeException NORMALIZE_BAD_DECOMPSEQUENCE_ERROR(String msg) {
  return new XcodeException(
   602,
   "Bad decomposition sequence"+msg
  );
 }
 static public boolean is_NORMALIZE_BAD_DECOMPSEQUENCE_ERROR(XcodeException x) {
  return (x.getCode() == 602);
 }


/*
 * NORMALIZE_NULL_CHARACTER_PRESENT: 603
 */
 static public XcodeException NORMALIZE_NULL_CHARACTER_PRESENT() {
  return new XcodeException(
   603,
   "Null character"
  );
 }
 static public XcodeException NORMALIZE_NULL_CHARACTER_PRESENT(String msg) {
  return new XcodeException(
   603,
   "Null character"+msg
  );
 }
 static public boolean is_NORMALIZE_NULL_CHARACTER_PRESENT(XcodeException x) {
  return (x.getCode() == 603);
 }


/*
 * NORMALIZE_CANONICAL_LOOKUP_ERROR: 604
 */
 static public XcodeException NORMALIZE_CANONICAL_LOOKUP_ERROR() {
  return new XcodeException(
   604,
   "Error looking up canonical class"
  );
 }
 static public XcodeException NORMALIZE_CANONICAL_LOOKUP_ERROR(String msg) {
  return new XcodeException(
   604,
   "Error looking up canonical class"+msg
  );
 }
 static public boolean is_NORMALIZE_CANONICAL_LOOKUP_ERROR(XcodeException x) {
  return (x.getCode() == 604);
 }


/*
 * PROHIBIT_INVALID_CHARACTER: 700
 */
 static public XcodeException PROHIBIT_INVALID_CHARACTER() {
  return new XcodeException(
   700,
   "Prohibited"
  );
 }
 static public XcodeException PROHIBIT_INVALID_CHARACTER(String msg) {
  return new XcodeException(
   700,
   "Prohibited"+msg
  );
 }
 static public boolean is_PROHIBIT_INVALID_CHARACTER(XcodeException x) {
  return (x.getCode() == 700);
 }


/*
 * BASE32_ENCODE_BIT_OVERFLOW: 800
 */
 static public XcodeException BASE32_ENCODE_BIT_OVERFLOW() {
  return new XcodeException(
   800,
   "The output length exceeds expected characters during encode."
  );
 }
 static public XcodeException BASE32_ENCODE_BIT_OVERFLOW(String msg) {
  return new XcodeException(
   800,
   "The output length exceeds expected characters during encode."+msg
  );
 }
 static public boolean is_BASE32_ENCODE_BIT_OVERFLOW(XcodeException x) {
  return (x.getCode() == 800);
 }


/*
 * BASE32_DECODE_INVALID_SIZE: 801
 */
 static public XcodeException BASE32_DECODE_INVALID_SIZE() {
  return new XcodeException(
   801,
   "Invalid input size (1, 3, or 6) for base32 decode."
  );
 }
 static public XcodeException BASE32_DECODE_INVALID_SIZE(String msg) {
  return new XcodeException(
   801,
   "Invalid input size (1, 3, or 6) for base32 decode."+msg
  );
 }
 static public boolean is_BASE32_DECODE_INVALID_SIZE(XcodeException x) {
  return (x.getCode() == 801);
 }


/*
 * BASE32_DECODE_INVALID_BIT_SEQUENCE: 802
 */
 static public XcodeException BASE32_DECODE_INVALID_BIT_SEQUENCE() {
  return new XcodeException(
   802,
   "The base32 string ends with invalid bit sequence."
  );
 }
 static public XcodeException BASE32_DECODE_INVALID_BIT_SEQUENCE(String msg) {
  return new XcodeException(
   802,
   "The base32 string ends with invalid bit sequence."+msg
  );
 }
 static public boolean is_BASE32_DECODE_INVALID_BIT_SEQUENCE(XcodeException x) {
  return (x.getCode() == 802);
 }


/*
 * BASE32_DECODE_BIT_OVERFLOW: 803
 */
 static public XcodeException BASE32_DECODE_BIT_OVERFLOW() {
  return new XcodeException(
   803,
   "The output length exceeds expected characters during decode"
  );
 }
 static public XcodeException BASE32_DECODE_BIT_OVERFLOW(String msg) {
  return new XcodeException(
   803,
   "The output length exceeds expected characters during decode"+msg
  );
 }
 static public boolean is_BASE32_DECODE_BIT_OVERFLOW(XcodeException x) {
  return (x.getCode() == 803);
 }


/*
 * BASE32_MAP_BIT_OVERFLOW: 804
 */
 static public XcodeException BASE32_MAP_BIT_OVERFLOW() {
  return new XcodeException(
   804,
   "Mapping not found for input"
  );
 }
 static public XcodeException BASE32_MAP_BIT_OVERFLOW(String msg) {
  return new XcodeException(
   804,
   "Mapping not found for input"+msg
  );
 }
 static public boolean is_BASE32_MAP_BIT_OVERFLOW(XcodeException x) {
  return (x.getCode() == 804);
 }


/*
 * BASE32_DEMAP_INVALID_BASE32_CHAR: 805
 */
 static public XcodeException BASE32_DEMAP_INVALID_BASE32_CHAR() {
  return new XcodeException(
   805,
   "Base32 input is limited to the values [a-z,2-7]."
  );
 }
 static public XcodeException BASE32_DEMAP_INVALID_BASE32_CHAR(String msg) {
  return new XcodeException(
   805,
   "Base32 input is limited to the values [a-z,2-7]."+msg
  );
 }
 static public boolean is_BASE32_DEMAP_INVALID_BASE32_CHAR(XcodeException x) {
  return (x.getCode() == 805);
 }


/*
 * DCE_INVALID_DELIMITER: 900
 */
 static public XcodeException DCE_INVALID_DELIMITER() {
  return new XcodeException(
   900,
   "Invalid delimiter in dns string"
  );
 }
 static public XcodeException DCE_INVALID_DELIMITER(String msg) {
  return new XcodeException(
   900,
   "Invalid delimiter in dns string"+msg
  );
 }
 static public boolean is_DCE_INVALID_DELIMITER(XcodeException x) {
  return (x.getCode() == 900);
 }


/*
 * DCE_DECODE_BIT_OVERFLOW: 901
 */
 static public XcodeException DCE_DECODE_BIT_OVERFLOW() {
  return new XcodeException(
   901,
   "The output length exceeds expected characters during decode."
  );
 }
 static public XcodeException DCE_DECODE_BIT_OVERFLOW(String msg) {
  return new XcodeException(
   901,
   "The output length exceeds expected characters during decode."+msg
  );
 }
 static public boolean is_DCE_DECODE_BIT_OVERFLOW(XcodeException x) {
  return (x.getCode() == 901);
 }


/*
 * DCE_DECODE_INVALID_SIZE: 902
 */
 static public XcodeException DCE_DECODE_INVALID_SIZE() {
  return new XcodeException(
   902,
   "Bad size of output dns bytes."
  );
 }
 static public XcodeException DCE_DECODE_INVALID_SIZE(String msg) {
  return new XcodeException(
   902,
   "Bad size of output dns bytes."+msg
  );
 }
 static public boolean is_DCE_DECODE_INVALID_SIZE(XcodeException x) {
  return (x.getCode() == 902);
 }


/*
 * TCSC_DOES_NOT_APPLY: 1000
 */
 static public XcodeException TCSC_DOES_NOT_APPLY() {
  return new XcodeException(
   1000,
   "The input sequence is not a candidate for TC/SC variation."
  );
 }
 static public XcodeException TCSC_DOES_NOT_APPLY(String msg) {
  return new XcodeException(
   1000,
   "The input sequence is not a candidate for TC/SC variation."+msg
  );
 }
 static public boolean is_TCSC_DOES_NOT_APPLY(XcodeException x) {
  return (x.getCode() == 1000);
 }


/*
 * TCSC_CHARACTER_MAPPED_OUT: 1001
 */
 static public XcodeException TCSC_CHARACTER_MAPPED_OUT() {
  return new XcodeException(
   1001,
   "The input character has no TC/SC variant"
  );
 }
 static public XcodeException TCSC_CHARACTER_MAPPED_OUT(String msg) {
  return new XcodeException(
   1001,
   "The input character has no TC/SC variant"+msg
  );
 }
 static public boolean is_TCSC_CHARACTER_MAPPED_OUT(XcodeException x) {
  return (x.getCode() == 1001);
 }


/*
 * INVALID_FILE_FORMAT_NOT_TCSC: 1002
 */
 static public XcodeException INVALID_FILE_FORMAT_NOT_TCSC() {
  return new XcodeException(
   1002,
   "Invalid TCSC codepoint found"
  );
 }
 static public XcodeException INVALID_FILE_FORMAT_NOT_TCSC(String msg) {
  return new XcodeException(
   1002,
   "Invalid TCSC codepoint found"+msg
  );
 }
 static public boolean is_INVALID_FILE_FORMAT_NOT_TCSC(XcodeException x) {
  return (x.getCode() == 1002);
 }


/*
 * NOT_CLASS_A_TCSC: 1003
 */
 static public XcodeException NOT_CLASS_A_TCSC() {
  return new XcodeException(
   1003,
   "The input domain name is not a Class A domain name"
  );
 }
 static public XcodeException NOT_CLASS_A_TCSC(String msg) {
  return new XcodeException(
   1003,
   "The input domain name is not a Class A domain name"+msg
  );
 }
 static public boolean is_NOT_CLASS_A_TCSC(XcodeException x) {
  return (x.getCode() == 1003);
 }


/*
 * NATIVE_UNSUPPORTED_ENCODING: 1100
 */
 static public XcodeException NATIVE_UNSUPPORTED_ENCODING() {
  return new XcodeException(
   1100,
   "Native encoding algorithm is not supported"
  );
 }
 static public XcodeException NATIVE_UNSUPPORTED_ENCODING(String msg) {
  return new XcodeException(
   1100,
   "Native encoding algorithm is not supported"+msg
  );
 }
 static public boolean is_NATIVE_UNSUPPORTED_ENCODING(XcodeException x) {
  return (x.getCode() == 1100);
 }


/*
 * NATIVE_INVALID_ENCODING: 1101
 */
 static public XcodeException NATIVE_INVALID_ENCODING() {
  return new XcodeException(
   1101,
   "Encoding can not be applied to input"
  );
 }
 static public XcodeException NATIVE_INVALID_ENCODING(String msg) {
  return new XcodeException(
   1101,
   "Encoding can not be applied to input"+msg
  );
 }
 static public boolean is_NATIVE_INVALID_ENCODING(XcodeException x) {
  return (x.getCode() == 1101);
 }


/*
 * UNICODE_SURROGATE_DECODE_ATTEMPTED: 1200
 */
 static public XcodeException UNICODE_SURROGATE_DECODE_ATTEMPTED() {
  return new XcodeException(
   1200,
   "A valid surrogate pair is invalid input to Unicode decode"
  );
 }
 static public XcodeException UNICODE_SURROGATE_DECODE_ATTEMPTED(String msg) {
  return new XcodeException(
   1200,
   "A valid surrogate pair is invalid input to Unicode decode"+msg
  );
 }
 static public boolean is_UNICODE_SURROGATE_DECODE_ATTEMPTED(XcodeException x) {
  return (x.getCode() == 1200);
 }


/*
 * UNICODE_DECODE_INVALID_VALUE: 1201
 */
 static public XcodeException UNICODE_DECODE_INVALID_VALUE() {
  return new XcodeException(
   1201,
   "Unicode can only decode values on the range [0x10000 - 0x10FFFF]"
  );
 }
 static public XcodeException UNICODE_DECODE_INVALID_VALUE(String msg) {
  return new XcodeException(
   1201,
   "Unicode can only decode values on the range [0x10000 - 0x10FFFF]"+msg
  );
 }
 static public boolean is_UNICODE_DECODE_INVALID_VALUE(XcodeException x) {
  return (x.getCode() == 1201);
 }


/*
 * UNICODE_INVALID_VALUE: 1202
 */
 static public XcodeException UNICODE_INVALID_VALUE() {
  return new XcodeException(
   1202,
   "Unicode values must be on the range [0 - 0x10FFFF]"
  );
 }
 static public XcodeException UNICODE_INVALID_VALUE(String msg) {
  return new XcodeException(
   1202,
   "Unicode values must be on the range [0 - 0x10FFFF]"+msg
  );
 }
 static public boolean is_UNICODE_INVALID_VALUE(XcodeException x) {
  return (x.getCode() == 1202);
 }


/*
 * UNICODEFILTER_DOES_NOT_PASS: 1300
 */
 static public XcodeException UNICODEFILTER_DOES_NOT_PASS() {
  return new XcodeException(
   1300,
   ""
  );
 }
 static public XcodeException UNICODEFILTER_DOES_NOT_PASS(String msg) {
  return new XcodeException(
   1300,
   ""+msg
  );
 }
 static public boolean is_UNICODEFILTER_DOES_NOT_PASS(XcodeException x) {
  return (x.getCode() == 1300);
 }


/*
 * UNICODEFILTER_INVALID_RANGE: 1301
 */
 static public XcodeException UNICODEFILTER_INVALID_RANGE() {
  return new XcodeException(
   1301,
   "Low value precedes high value in a Unicode range"
  );
 }
 static public XcodeException UNICODEFILTER_INVALID_RANGE(String msg) {
  return new XcodeException(
   1301,
   "Low value precedes high value in a Unicode range"+msg
  );
 }
 static public boolean is_UNICODEFILTER_INVALID_RANGE(XcodeException x) {
  return (x.getCode() == 1301);
 }


/*
 * BIDI_RULE_2_VIOLATION: 1400
 */
 static public XcodeException BIDI_RULE_2_VIOLATION() {
  return new XcodeException(
   1400,
   "Intermixed R and RAL characters."
  );
 }
 static public XcodeException BIDI_RULE_2_VIOLATION(String msg) {
  return new XcodeException(
   1400,
   "Intermixed R and RAL characters."+msg
  );
 }
 static public boolean is_BIDI_RULE_2_VIOLATION(XcodeException x) {
  return (x.getCode() == 1400);
 }


/*
 * BIDI_RULE_3_VIOLATION: 1401
 */
 static public XcodeException BIDI_RULE_3_VIOLATION() {
  return new XcodeException(
   1401,
   "RAL characters without RAL characters in first and last positions."
  );
 }
 static public XcodeException BIDI_RULE_3_VIOLATION(String msg) {
  return new XcodeException(
   1401,
   "RAL characters without RAL characters in first and last positions."+msg
  );
 }
 static public boolean is_BIDI_RULE_3_VIOLATION(XcodeException x) {
  return (x.getCode() == 1401);
 }


/*
 * IDNA_DECODE_MISMATCH: 1500
 */
 static public XcodeException IDNA_DECODE_MISMATCH() {
  return new XcodeException(
   1500,
   "Result of toUnicode() and then toAscii() does not match input."
  );
 }
 static public XcodeException IDNA_DECODE_MISMATCH(String msg) {
  return new XcodeException(
   1500,
   "Result of toUnicode() and then toAscii() does not match input."+msg
  );
 }
 static public boolean is_IDNA_DECODE_MISMATCH(XcodeException x) {
  return (x.getCode() == 1500);
 }


/*
 * IDNA_LABEL_LENGTH_RESTRICTION: 1501
 */
 static public XcodeException IDNA_LABEL_LENGTH_RESTRICTION() {
  return new XcodeException(
   1501,
   "The length of the ASCII sequence exceeds the 63 octet limit imposed by RFC 1034."
  );
 }
 static public XcodeException IDNA_LABEL_LENGTH_RESTRICTION(String msg) {
  return new XcodeException(
   1501,
   "The length of the ASCII sequence exceeds the 63 octet limit imposed by RFC 1034."+msg
  );
 }
 static public boolean is_IDNA_LABEL_LENGTH_RESTRICTION(XcodeException x) {
  return (x.getCode() == 1501);
 }
}
