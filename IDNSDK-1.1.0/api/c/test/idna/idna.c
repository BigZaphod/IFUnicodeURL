
/********************************************************************************
 *                              IDNA                                            *
 ********************************************************************************/

/*
Purpose:     Internationalized Domain Names in Applications.  A set of algorithms,
which define a way to encode and decode Unicode data making it compatible
with the Domain Naming System.

Usage:    idna (toAscii|toUnicode) <file>

Input type:   Unicode for toAscii, ASCII for toUnicode
Output type:  ASCII for toAscii, Unicode for toUnicode
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

void setupTestcase()
{
	FILE * fp;
	int i;
	fp = fopen( "test.txt", "w" );
	//fprintf( fp, "xn--rvjze3zks5ga." );
	for ( i = 1; i <= 0xff; i++ ) fprintf( fp, "%c", i );
}

int main(int argc, char* argv[])
{
	FILE * fpin;
	char szIn[1024];
	DWORD dwInput[1024];
	UTF16CHAR uInput[MAX_DOMAIN_SIZE_16];
	UTF16CHAR uOutput[MAX_DOMAIN_SIZE_16];
	UCHAR8 szOutput[MAX_DOMAIN_SIZE_8];
	int iInputSize = 0;
	int iOutputSize = 0;
	int iOutputSize2 = 0;
	int counter = 0;
	int res;
	int i;
	int toascii = 0;
	int inputis8bit = 0;

	//setupTestcase();
	//return 0;

	/* Arg check */
	if (argc < 2) {
		printf("usage: (toAscii | toUnicode) <file>\n", argv[0] );
		return 1;
	}

	if ( strcmp( "toAscii", argv[1] ) == 0 ) {toascii = 1;}
	fpin = fopen(argv[2], "r");
	if (fpin == NULL) { printf("Cannot open %s\n",argv[2]); return 1; }

	while ( !feof( fpin ) ) {
		memset( szIn, 0, sizeof(szIn) );
		memset( uInput, 0, sizeof(uInput) );
		memset( szOutput, 0, sizeof(szOutput) );

		fgets( szIn, sizeof(szIn), fpin );
		if ( szIn[0] == ' ' || szIn[0] == '#' || strlen( szIn ) < 2 ) {
			printf( szIn );
			continue;
		}

		if ( toascii ) {
			Read32BitLine( szIn, dwInput, &iInputSize );

			iOutputSize = MAX_DOMAIN_SIZE_16;
			res = Xcode_convert32BitToUTF16( dwInput, iInputSize, uInput, &iOutputSize );

			if ( res != XCODE_SUCCESS ) {
				char szMsg[1024];
				ConvertErrorCode( res, szMsg );
				printf( "Error: Line=%d '%25s' (%s)\n", counter, szMsg, szIn );
				continue;
			}

			iInputSize = iOutputSize;
			iOutputSize = sizeof(szOutput);
			res = Xcode_DomainToASCII( uInput, iInputSize, szOutput, &iOutputSize );

		} else {

			iOutputSize = MAX_DOMAIN_SIZE_16;
			szIn[strlen(szIn)-1] = 0;
			res = Xcode_DomainToUnicode8( szIn, strlen(szIn), uInput, &iOutputSize );

			/*
			// This was the code for handling Unicode input in the toUnicode() method
			Read32BitLine( szIn, dwInput, &iInputSize );

			iOutputSize2 = MAX_DOMAIN_SIZE_16;
			res = Xcode_convert32BitToUTF16(dwInput, iInputSize, uOutput, &iOutputSize2 );
			if ( res != XCODE_SUCCESS ) {
				char szMsg[1024];
				ConvertErrorCode( res, szMsg );
				printf( "Error: Line=%d '%25s' (%s)\n", counter, szMsg, szIn );
				continue;
			}

			iOutputSize = MAX_DOMAIN_SIZE_16;
			res = Xcode_DomainToUnicode16( uOutput, iOutputSize2, uInput, &iOutputSize );
			 */
		}

		counter++;

		if ( res != XCODE_SUCCESS ) {
			char szMsg[1024];
			ConvertErrorCode( res, szMsg );
			printf( "Error: Line=%d %s\n", counter, szMsg );
			continue;
		}
		if ( toascii ) {
			printf( "%s\n", szOutput );
		} else {
			for( i = 0; i < iOutputSize; i++ ) {
				if ( i > 0 ) printf( " " );
				printf( "%x", uInput[i] );
			}
			printf( "\n" );
		}
	}

	fclose(fpin);
	#ifdef WIN32
	getchar();
	#endif
	return 0;

}
