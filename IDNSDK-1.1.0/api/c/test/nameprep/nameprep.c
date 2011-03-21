
/********************************************************************************
 *                              Nameprep                                        *
 ********************************************************************************/

/*
  Purpose   Domain Name Preparation.  An algorithm designed to normalize Unicode data forcing like 
            sequences to have equivalent data representation.  This tool runs the Charmap, Normalize, 
            Prohibit, and Bidi algorithms in that order.

  Usage     nameprep [-a] <file>
            -a => Allow unassigned codepoints (disallowed by default)

  Input type    Unicode
  Output type   Unicode
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
  DWORD dwInput[1024];
  DWORD dwOutput[1024];
  DWORD dwProhibitChar;
  int iInputSize = 0;
  int iOutputSize = 0;
  int counter = 0;
  int res;
  int i;

  /* Arg check */
  if (argc < 2) { printf("usage: <unicode codepoints file>\n", argv[0] ); return 1; }

  /* Get file */
  fpin = fopen(argv[1], "r");
  if (fpin == NULL) { printf("Cannot open %s\n",argv[1]); return 1; }

  while ( !feof( fpin ) )
  {
    memset( szIn, 0, sizeof(szIn) );
    memset( dwInput, 0, sizeof(dwInput) );
    memset( dwOutput, 0, sizeof(dwOutput) );

    fgets( szIn, sizeof(szIn), fpin );
    if ( szIn[0] == ' ' || szIn[0] == '#' || strlen( szIn ) < 2 ) 
    {
      printf( szIn );
      continue;
    }

    /* Clip off \n */
    szIn[strlen(szIn)-1] = 0;

    Read32BitLine( szIn, dwInput, &iInputSize );

    iOutputSize = 1024;
    res = Xcode_nameprepString32( dwInput, iInputSize, dwOutput, &iOutputSize, &dwProhibitChar );

    counter++;

    if ( res != XCODE_SUCCESS ) 
    {
      char szMsg[1024];
      ConvertErrorCode( res, szMsg );
      printf( "Fail: Line=%d '%25s' (%s)\n", counter, szMsg, szIn );
      continue;
    }

    for( i = 0; i < iOutputSize; i++ )
    { 
      printf( "%05X ", dwOutput[i] );
    }
    printf( "\n" );

  }

  fclose(fpin);
  #ifdef WIN32
  getchar();
  #endif
  return 0;

}
