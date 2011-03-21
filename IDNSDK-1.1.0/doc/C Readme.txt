******************************************************************
******************************************************************
VeriSign IDNA XCode (encode/decode) ANSI c Library

A library for encoding / decoding of IDN domains and labels.

(c) VeriSign Inc., 2002-2003, All rights reserved
******************************************************************
******************************************************************

  Contents:

  I.    License
  II.   Introduction
  III.  General Overview
  IV.   Functional Entry Points
  V.    Standard Types
  VI.   Error and Success Constants
  VII.  Constants
  VIII. Compile Configuration
  IX.   Nameprep data headers and headerexport
  X.    Example Use


I. License


The VeriSign XCode library is licensed under the BSD open source
license, and as such is suitable for use in commercial applications.


II. Introduction


Xcode is an IDNA compliant encoding and decoding library used to 
support the International Domain Names (IDNA) standard in applications. 
XCode is primarily targeted at client application development - the 
library is small, compact, and includes projects for building both 
static and dynmaic libraries on a number of platforms.

For in-depth information on the current IDNA standard, and for
information on how IDN's should be handled at the application
level, see IDNA RFC 3490. Copies of applicable RFC's are included
in the docs directory of this distribution.


III. General Overview


The library has a single set of IDNA compliant routines used
in encoding and decoding domains and domain labels (ToXXX). 
Direct access to each nameprep and encode/decode step is also
made available.
  
In most cases The ToXXX domain label routines ToASCII and ToUnicode, 
or the domain processing routines DomainToASCII and DomainToUnicode 
are the only routines application developers will need to implement 
IDNA in their application. 

The following is a breakdown of the different areas of the library:

  ToXXX (toxxx.c/.h) - IDNA spec domain label & domain processing 
  routines.

  Nameprep (nameprep.c/.h) - Routines which handle applying Nameprep 11 
  to UTF16 domain labels.

  Punycode (puny.c/.h) - Routines which handle Punycode encoding and 
  decoding of domain lables.

  Race (race.c/.h) - Routines which handle Race encoding of domain 
  labels. These routines are not part of IDNA, they are included for 
  migration purposes only. They are not compiled into the library by 
  default.

  Samples & Conformance - Various command line based applications 
  and sample code showing how to use this library, test vectors are 
  also included.

The distribution directory strucuture is as follows:

  xcode            - core library source base.
    src            - .c source files.
    inc            - .h include directory.
      staticdata   - static nameprep data include headers.
    headerexport   - utility used in converting raw data files data
                     to c static headers.
    test           - testing and conformance applications.
    Win32 Projects - Win32 Visual C++ 6.0 project files.
    docs           - Docs, README, and RFC's.


IV. Functional Entry Points


In general application developers should use the primary entry
points in processing domain labels or domains. Using primary 
entry points insures IDNA conformance in the application. Auxillary 
entry points are primarily used in testing. Each entry point
is documented in it's header.

The following entry points are provided by the library. See the 
header files for specific information on each.  


Primary Entry Points:

--------------------------------------------------------------------
int Xcode_ToASCII( const UTF16CHAR *  puzInputString, 
                   int                iInputSize,
                   UCHAR8 *           pzOutputString, 
                   int *              piOutputSize );

Routine for encoding a domain label. Input is UTF16 and output is 
8-bit ASCII.

Example Input:  enténial
Example Output: xn--entnial-dya
--------------------------------------------------------------------


--------------------------------------------------------------------
int Xcode_DomainToASCII( const UTF16CHAR *  puzInputString, 
                         int                iInputSize,
                         UCHAR8 *           pzOutputString, 
                         int *              piOutputSize );

Routine for encoding a domain which may contain unicode codepoints. 
Breaks up a domain by known domain label delimiters and subsequently 
hands each label to ToASCII. Reassembles encoded labels into a domain 
using '.' as the delimiter between each encoded label. Input is UTF16 
and output is 8-bit ASCII. Valid ASCII domains which do not contain 
unicode codepoints are mapped to the output.

Example Input:  www.enténial.com
Example Output: www.xn--entnial-dya.com
--------------------------------------------------------------------


--------------------------------------------------------------------
int Xcode_ToUnicode8( const UCHAR8 *    pzInputString, 
                      int                iInputSize,
                      UTF16CHAR *        puzOutputString, 
                      int *              piOutputSize );

int Xcode_ToUnicode16( const UTF16CHAR * puzInputString, 
                       int               iInputSize,
                       UTF16CHAR *       puzOutputString, 
                       int *             piOutputSize );

Routines which decode encoded domain labels. ToUnicode8 takes
8-bit character string as input, ToUnicode16 takes 16 bit UTF16
character string as input. Output is returned in UTF16. Note that
the IDNA RFC requires certain extended ascii characters to be 
mapped via nameprep in this step. A good example of these are the
full and half width Japanese characters. Use ToUnicode16 to 
correctly map these characters in encoded domain labels to their 
ascii representations prior to decoding.

Example Input:  xn--entnial-dya
Example Output: enténial
--------------------------------------------------------------------


----------------------------------------------------------------------
int Xcode_DomainToUnicode8( const UCHAR8 *     pzInputString, 
                            int                iInputSize,
                            UTF16CHAR *        puzOutputString, 
                            int *              piOutputSize );

int Xcode_DomainToUnicode16( const UTF16CHAR *  puzInputString, 
                             int                iInputSize,
                             UTF16CHAR *        puzOutputString, 
                             int *              piOutputSize );

Routines which decode encoded domains.

Example Input:  www.xn--entnial-dya.com
Example Output: www.enténial.com

Example Input:  www\u3002xn--entnial-dya\uFF0Ecom
Example Output: www.enténial.com

Example 8-bit Input: www.xn--j1aimx.com
Example UTF16 Input: www\u3002xn--\uFF4A1aimx\uFF0Ecom
Example Output:      www.043a\u043e\u0448\u0442.com
----------------------------------------------------------------------


----------------------------------------------------------------------
int Xcode_convertUTF16To32Bit( const UTF16CHAR *  puInput, 
                               int                iInputLength, 
                               DWORD *            pdwResult, 
                               int *              piResultLength );

int Xcode_convert32BitToUTF16( const DWORD *  pdwzInput, 
                               int            iInputLength, 
                               UTF16CHAR *    puzResult, 
                               int *          piResultLength );

int Xcode_convertUTF16ToUTF8( const UTF16CHAR * puzInput,
                              int               iInputLength,
                              UCHAR8 *          pszResult,
                              int *             piResultLength );

int Xcode_convertUTF8ToUTF16( const UCHAR8 *    pszInput,
                              int               iInputLength,
                              UTF16CHAR *       puzResult,
                              int *             piResultLength );


Conversion rotuines used in converting 32-bit/UTF16/UTF8.
----------------------------------------------------------------------

Test Entry Points:

Xcode_nameprepString / Xcode_nameprepString32

Direct access to full Nameprep 11 routines for UTF16 32-bit strings.

Xcode_normalizeString
Xcode_charmapString
Xcode_prohibitString
Xcode_bidifilterString

Direct access to individual Nameprep 11 steps in processing.

Xcode_puny_encodeString / Xcode_puny_decodeString

Direct access to Punycode encode/decode routines.

Xcode_race_decodeString / Xcode_race_encodeString 

Migration of Race domain labels.


V. Standard Types


The library defines a set of standard types which are used in 
entry point declarations. Standard types are typedef'd in the
xcode_config.h header file. The following types are defined:

UCHAR8    - 8-bit unsigned character (unsigned char *)
UTF16CHAR - UTF16 (16-bit) encoded character (unsigned short int)
DWORD     - 32-bit unsigned character or codepoint (unsigned long *)
QWORD     - (Internal use only) 64-bit unsigned codepoint pair (unsigned int64)
XcodeBool - Boolean flag (1/0)


VI. Error and Success Constants


All entry points return an integer value indicating success or 
failure. Success is always indicated by a return value of 0, or
developers can also use the XCode constant XCODE_SUCCESS. Error
constants are defined in the following headers within the 
library:

xcode.h     - [1 - 99] General error constants.

puny.h      - [100-199] Ace specific error constants.

nameprep.h  - [200-299] Nameprep 11 specific error constants. 

toxxx.h     - [300-399] Toxxx routine specific constants.

util.h      - [400-499] Utility specific error constants.

When an entry point returns a value greater than 0, one of these 
headers will describe the error.


VII. Constants


XCode supports various different configurations and constants. All 
configuration options are specified through a common configuration 
include file named "xcode_config.h". The following constants are 
defined:

  ACE_PREFIX

  The IDNA Ace label prefix.

  MAX_LABEL_SIZE_XX, MAX_DOMAIN_SIZE_XX

  The maximum size of 32, 16, & 8 bit strings to be passed into the 
  library's routines. Used internally to define input / output 
  buffer  sizes as well. Input string lengths are checked against 
  these constants prior to processing. All incoming result buffers 
  must meet these minimum widths as well or an XCODE_BUFFER_OVERFLOW_ERROR
  error will be returned.

  static const UTF16CHAR ULABEL_DELIMITER_LIST[4]

  The IDNA approved domain delimiters. See xcode_config.h for more 
  information.


VIII. Compile Configuration


  UseSTD3ASCIIRules   (default on) 

  Apply STD3 domain format rules in ToASCII and ToUnicode as specified 
  in IDNA.


  AllowUnassigned     (default on) 

  Optionally allow unassigned unicode codepoints per IDNA in Query     
  string processing. Client applications predominately deal with       
  "stored strings", therefore this compile switch is turned off by     
  default.                                                             
                                                                      
  Explanation From STRINGPREP:                     
                                                                  
    7. Unassigned Code Points in Stringprep Profiles                     
                                                                  
    This section describes two different types of strings in typical     
    protocols where internationalized strings are used: "stored strings" 
    and "queries".  Of course, different Internet protocols use strings  
    very differently, so these terms cannot be used exactly in every     
    protocol that needs to use stringprep.  In general, "stored strings" 
    are strings that are used in protocol identifiers and named entities,
    such as names in digital certificates and DNS domain name parts.     
    "Queries" are strings that are used to match against strings that are
    stored identifiers, such as user-entered names for digital           
    certificate authorities and DNS lookups.                             
                                                                  
    All code points not assigned in the character repertoire named in a  
    stringprep profile are called "unassigned code points".  Stored      
    strings using the profile MUST NOT contain any unassigned code       
    points.  Queries for matching strings MAY contain unassigned code    
    points.  Note that this is the only part of this document where the  
    requirements for queries differs from the requirements for stored    
    strings.                                                             
                                                                      
  From IDNA, section 4 - Conversion operations  
                                                          
    Decide whether the domain name is a "stored string" or a "query   
    string" as described in [STRINGPREP].  If this conversion follows 
    the "queries" rule from [STRINGPREP], set the flag called         
    "AllowUnassigned".                                                


  SUPPORT_RACE        (default off) 
  
  Compile switch for enabling race migration routines.


IX. Nameprep data headers and headerexport


Before encoding a domain label, nameprep and normalization must be 
applied, as described in http://www.unicode.org/unicode/reports/tr15/. 
A great deal of static data stored in lookup tables is required to 
accomplish this step. Various raw data files which contain this data 
are specified in the RFC's associated with IDNA. XCode accesses this 
data through a set of static lookup tables located in inc/staticdata. 
All headers neccassary are included with this distribution. A header
export utility which generates thes headers is included for completness.


X. Example use

1) Encoding a domain:

  int res;

  /* unicode or UTF16 input string */

  /* ex: www.enténial.com */

  UTF16CHAR uInput[] = { 0x0077, 0x0077, 0x0077, 0x002E, 0x0066, 
                         0x00FC, 0x006E, 0x0066, 0x0064, 0x3002, 
                         0x006E, 0x0065, 0x0074 };
  int iInputSize = 13;

  /* ASCII output buffer */

  UCHAR8 szOutput[MAX_DOMAIN_SIZE_8];

  int iOutputSize = sizeof(szOutput);

  res = Xcode_DomainToASCII( uInput, iInputSize, szOutput, &iOutputSize );

  if ( res != XCODE_SUCCESS ) 
  {
    /* Error */
  }

  printf( szOutput );

2) Decoding a domain:

  int res;

  /* UTF16 output buffer */

  UTF16CHAR uOutput[MAX_DOMAIN_SIZE_16];

  char * szIn = "www.xn--weingut-schnberger-n3b.net";

  int iInputSize = strlen(szIn);
  int iOutputSize = sizeof(uOutput);

  res = Xcode_DomainToUnicode8( szIn, iInputSize, uOutput, &iOutputSize );

  if ( res != XCODE_SUCCESS ) 
  {
    /* Error */
  }


Application developers may wish to break a domain up into labels
manually outside of this library. According to IDNA, applications
must recognize all domain delimiters defined in xcode_config.h.

1) Encoding a label:

  int res;

  UTF16CHAR uInput[] = { 0x0070, 0x00E4, 0x00E4, 0x006F, 0x006D, 0x0061 };

  int iInputSize = 6;

  UCHAR8 szOutput[1204];

  int iOutputSize = sizeof(szOutput);

  /* ex: enténial */

  res = Xcode_ToASCII( uInput, iInputSize, szOutput, &iOutputSize );

  if ( res != XCODE_SUCCESS ) 
  {
    /* Error */
  }

  printf( szOutput );

2) Decoding a label:

  int res;

  /* UTF16 output buffer */

  UTF16CHAR uOutput[MAX_LABEL_SIZE_16];

  char * szIn = "xn--weingut-schnberger-n3b";

  int iInputSize = strlen(szIn);
  int iOutputSize = sizeof(uOutput);

  res = Xcode_ToUnicode8( szIn, iInputSize, uOutput, &iOutputSize );

  if ( res != XCODE_SUCCESS ) 
  {
    /* Error */
  }
