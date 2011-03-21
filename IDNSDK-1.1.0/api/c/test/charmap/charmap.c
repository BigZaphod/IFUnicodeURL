
/********************************************************************************
 *                              Charmap                                         *
 ********************************************************************************/

/*
  Purpose: This tool is the first component of Nameprep.  It converts a single codepoint in the 
           input sequence, into zero or more codepoints in the output sequence.

  Usage	charmap <file>

  Input type:   Unicode
  Output type:  Unicode
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

#include <stdlib.h>
#include "../../xcode/inc/staticdata/nameprep_datastructures.h"
#include "../../xcode/inc/staticdata/nameprep_charmap.h"
#include "../../xcode/inc/staticdata/nameprep_prohibit.h"

void buildCharmapInput()
{
  int i, j, index, ccount;
  FILE * fp = fopen( "../../../testdata/charmap.input.txt", "w" );

  for ( i = 0; i <= 5000; i++ )
  {
    ccount = (int)rand();
    ccount = (int)(((float)rand() / (float)RAND_MAX) * 15.0f);
    if ( ccount == 0 ) ccount = 1;
    
    for ( j = 0; j < ccount; j++ )
    {
      index = (int)(((float)rand() / (float)RAND_MAX) * CHARMAP_ENTRYCOUNT);
      if ( j > 0 ) fprintf( fp, " " );
      fprintf( fp, "%x", g_charmapTable[index].dwCodepoint );
    }
    fprintf( fp, "\n" );
  }
  fcloseall();
}

int main(int argc, char* argv[]) 
{
  FILE * fpin;
  /* FILE * fpout; */
  char szIn[1024];
  DWORD dwInput[1024];
  DWORD dwOutput[1024];
  int iInputSize = 0;
  int iOutputSize = 0;
  int counter = 0;
  int res;
  int i;

  /* buildCharmapInput(); */

  /* fpout = fopen( "../../../testdata/charmap.output.txt", "w" ); */

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

    Read32BitLine( szIn, dwInput, &iInputSize );

    iOutputSize = 1024;
    res = Xcode_charmapString( dwInput, iInputSize, dwOutput, &iOutputSize );

    counter++;

    if ( res != XCODE_SUCCESS ) 
    {
      char szMsg[1024];
      ConvertErrorCode( res, szMsg );
      printf( "Error: Line=%d '%25s' (%s)\n", counter, szMsg, szIn );
      continue;
    }

    for( i = 0; i < iOutputSize; i++ )
    { 
      if ( i > 0 ) printf( " " );
      printf( "%x", dwOutput[i] );
    }
    printf( "\n" );

  }

  fclose(fpin);
  #ifdef WIN32
  getchar();
  #endif
  return 0;

}
