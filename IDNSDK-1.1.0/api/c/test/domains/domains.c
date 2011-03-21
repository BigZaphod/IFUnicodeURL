/*************************************************************************/
/*                                                                       */
/* sampleuse                                                             */
/*                                                                       */
/* Simple examples of library use used in the HowTo.txt document.        */
/*                                                                       */
/* (c) Verisign Inc., 2000-2002, All rights reserved                     */
/*                                                                       */
/*************************************************************************/

#include "xcode.h"
#include <stdio.h>
#include "../utility/utility.h"

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
  char szOut[1024];
  UCHAR8 szData[MAX_DOMAIN_SIZE_8];
  UTF16CHAR uInput[MAX_DOMAIN_SIZE_16];
  int iInputSize = 0;
  int iOutputSize = 0;
  int counter = 0;
  int res;

  /* Arg check */
 if (argc < 1) { printf("usage: [toascii,tounicode] <domains fullcircle file>\n", argv[0] ); return 1; }

  /* Get file */
  fpin = fopen(argv[1], "r");
  if (fpin == NULL) { printf("Cannot open %s\n",argv[1]); return 1; }

  while ( !feof( fpin ) )
  {
    memset( szIn, 0, sizeof(szIn) );
    memset( szOut, 0, sizeof(szOut) );
    memset( szData, 0, sizeof(szData) );

    fgets( szIn, sizeof(szIn), fpin );
    counter++;
    if ( szIn[0] == ' ' || szIn[0] == '#' || strlen( szIn ) < 2 ) continue;
    fgets( szOut, sizeof(szOut), fpin );
    counter++;

    /* Clip off \n */
    szIn[strlen(szIn)-1] = 0;
    szOut[strlen(szOut)-1] = 0;

    if ( szIn[0] != 'i' ) { printf("Invalid input file format.\n"); return 1; }
    if ( szOut[0] != 'o' ) { printf("Invalid input file format.\n"); return 1; }

    Read16BitLine( &szIn[2], uInput, &iInputSize );
    iOutputSize = sizeof(szData);
    res = Xcode_DomainToASCII( uInput, iInputSize, szData, &iOutputSize );

    counter++;

    if ( res != XCODE_SUCCESS ) 
    {
      char szMsg[1024];
      ConvertErrorCode( res, szMsg );
      printf( "Error: line %d Code %s (%s)\n", counter, szMsg, &szIn[2] );
      continue;
    }

    printf( "%s\n", szData );
  }

  fclose(fpin);
  #ifdef WIN32
  getchar();
  #endif
  return 0;
}
