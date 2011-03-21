
/*******************************************************************************
 *                                  Race                                       *
 ******************************************************************************/

/*
 * Purpose:
 *  This tool compresses and converts Unicode data into an ASCII compatible
 *  sequence.  This particular algorithm is no longer supported by the IETF.
 *  The IDNA draft gives applications permission to choose whether or not to
 *  exclude ASCII characters which are not a letter, digit, or hyphen.  If
 *  the -3 switch is given then these no standard codepoints are allowed.
 *
 * Usage:      race [-3] (encode|decode) <file>
 *             -3 => do NOT enforce Std 3 ASCII rules
 *
 * Input type:   Utf-16
 * Output type:  ASCII
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

int main(int argc, char* argv[])
{
	FILE * fpin;
	char szIn[1024];
	UTF16CHAR uInput[1024];
	DWORD  dwOutput[1024];
	UCHAR8 szData[1024];
	UTF16CHAR uData[1024];
	int iInputSize = 0;
	int iOutputSize = 0;
	int counter = 0;
	int res;
	int i;
	int encode = 0;

	/* Arg check */
	if (argc < 2) { printf("usage: [encode (to race)|decode (to unicode)] <file>\n", argv[0] ); return 1; }

	/* Get file */
	fpin = fopen(argv[2], "r");
	if (fpin == NULL) { printf("Cannot open %s\n",argv[2]); return 1; }

	if ( strcmp( argv[1], "encode" ) == 0 ) encode = 1;

	while ( !feof( fpin ) )
	{
		memset( szIn, 0, sizeof(szIn) );
		memset( uInput, 0, sizeof(uInput) );
		memset( dwOutput, 0, sizeof(dwOutput) );
		memset( uData, 0, sizeof(uData) );
		memset( szData, 0, sizeof(szData) );

		fgets( szIn, sizeof(szIn), fpin );
		if ( szIn[0] == ' ' || szIn[0] == '#' || strlen( szIn ) < 2 )
		{
			printf( szIn );
			continue;
		}

		/* Clip off \n */
		szIn[strlen(szIn)-1] = 0;

		if ( encode )
		Read16BitLine( szIn, uInput, &iInputSize );

		iOutputSize = 1024;

		if ( encode )
		{
			res = Xcode_race_encodeString( uInput, iInputSize, szData, &iOutputSize, "bq--", 4 );
		} else {
			iInputSize = strlen( szIn );
			res = Xcode_race_decodeString( szIn, iInputSize, uData, &iOutputSize, "bq--", 4 );
			if ( res != XCODE_SUCCESS ) goto error;
			res = Xcode_convertUTF16To32Bit( uData, iOutputSize, dwOutput, &iOutputSize );
		}

		counter++;

		error:
		if ( res != XCODE_SUCCESS )
		{
			char szMsg[1024];
			ConvertErrorCode( res, szMsg );
			printf( "Fail: Line=%d '%25s' (%s)\n", counter, szMsg, szIn );
			continue;
		}

		for( i = 0; i < iOutputSize; i++ )
		{
			if ( encode )
			printf( "%c", szData[i] );
			else
			printf( "%x ", dwOutput[i] );
		}
		printf( "\n" );

	}

	fclose(fpin);
	#ifdef WIN32
	getchar();
	#endif
	return 0;

}
