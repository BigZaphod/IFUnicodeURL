
/********************************************************************************
 *                              Unicode                                         *
 ********************************************************************************/

/*
Purpose: This tool converts between Unicode data and Utf-16 using the surrogate arithmetic specified in
the UTF-16 RFC.  (see the References section in Appendices)

Usage:   unicode (encode|decode) <file>

Input type:   Utf-16 for encode, Unicode for decode
Output type:  Unicode for encode, Utf-16 for decode
 */

#include "xcode.h"
#include "../utility/utility.h"
#include <stdio.h>

#ifdef WIN32
#ifdef _DEBUG
#pragma comment( lib, "../../../../../lib/win32/xcodelibdbg.lib" )
#else
#pragma comment( lib, "../../../../../lib/win32/xcodelib.lib" )
#endif
#endif

void buildUTF16Input()
{
	int i;
	DWORD dwInput[5];
	UTF16CHAR uResult[256];
	int iuResultLength = 256;

	FILE * fp = fopen( "utf16.input.txt", "w" );
	FILE * fpout = fopen( "utf16.output.txt", "w" );

	for ( i = 0x90000; i <= 0x10FFFF; i = i + 4 )
	{
		dwInput[0] = i;
		dwInput[1] = i+1;
		dwInput[2] = i+2;
		dwInput[3] = i+3;

		Xcode_convert32BitToUTF16( dwInput, 4, uResult, &iuResultLength );

		fprintf( fp, "%x %x %x %x\n", uResult[0], uResult[1], uResult[2], uResult[3] );
		fprintf( fpout, "%x %x %x %x\n", dwInput[0], dwInput[1], dwInput[2], dwInput[3] );
	}
	fcloseall();
}

int main(int argc, char* argv[])
{
	FILE * fpin;
	/*FILE * fpout;*/
	char szIn[1024];
	DWORD dwInput[1024];
	DWORD dwOutput[1024];
	UTF16CHAR uData[1024];
	int iInputSize = 0;
	int iOutputSize = 0;
	int counter = 0;
	int res;
	int i;
	int encode = 0;

	/*buildUTF16Input(); return 0;*/

	/* Arg check */
	if (argc < 2) {
		printf("usage: (encode | decode) <file>\n", argv[0] );
		return 1;
	}

	/* Get file */
	fpin = fopen(argv[2], "r");
	if (fpin == NULL) { printf("Cannot open %s\n",argv[2]); return 1; }

	/*fpout = fopen( "tmp.output.txt", "w" );*/

	if ( strcmp( argv[1], "encode" ) == 0 ) encode = 1;

	while ( !feof( fpin ) ) {
		memset( szIn, 0, sizeof(szIn) );
		memset( dwInput, 0, sizeof(dwInput) );
		memset( uData, 0, sizeof(uData) );
		memset( dwOutput, 0, sizeof(dwOutput) );

		fgets( szIn, sizeof(szIn), fpin );
		if ( szIn[0] == ' ' || szIn[0] == '#' || strlen( szIn ) < 2 ) continue;

		/* Clip off \n */
		szIn[strlen(szIn)-1] = 0;

		if ( encode ) {
			Read16BitLine( szIn, uData, &iInputSize );
		} else {
			Read32BitLine( szIn, dwInput, &iInputSize );
		}

		iOutputSize = 1024;

		if ( encode ) {
			res = Xcode_convertUTF16To32Bit( uData, iInputSize, dwOutput, &iOutputSize );
		} else {
			res = Xcode_convert32BitToUTF16( dwInput, iInputSize, uData, &iOutputSize );
		}

		counter++;

		if ( res != XCODE_SUCCESS ) {
			char szMsg[1024];
			ConvertErrorCode( res, szMsg );
			printf( "Fail: Line=%d '%25s' (%s)\n", counter, szMsg, szIn );
			continue;
		}

		for( i = 0; i < iOutputSize; i++ ) {
			if ( encode ) {
				if ( i > 0 ) printf( " " );
				printf( "%x", dwOutput[i] );
			} else {
				if ( i > 0 ) printf( " " );
				printf( "%x", uData[i] );
			}
		}
		printf( "\n" );

	}

 fclose(fpin);
	#ifdef WIN32
	getchar();
	#endif
	return 0;

}
