
/********************************************************************************
 *                              Prohibit                                        *
 ********************************************************************************/

/*
  Purpose:  This algorithm is the third component of Nameprep.  It prohibits certain 
            codepoints from appearing in an IDN input sequence.

  Usage:    prohibit [-a] <file>
            -a => Allow unassigned codepoints (disallowed by default)

  Input type:  Unicode
  Output type: Error condition if prohibited codepoints are found.
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

void buildProhibitInput()
{
  int i, j, index, count;
  FILE * fp = fopen( "../../../testdata/prohibit.input.txt", "w" );
  
  for ( i = 0; i <= 1000; i++ )
  {
    index = (int)(((float)rand() / (float)RAND_MAX) * PROHIBIT_ENTRYCOUNT);
    count = 0;
    for ( j = g_prohibitTable[index].low; j <= g_prohibitTable[index].high; j++ )
    {
      count++;
      fprintf( fp, "%x\n", j );
      if ( count > 5 ) break;
    }
  }
  fcloseall();
}

int main(int argc, char* argv[]) 
{
  FILE * fpin;
  /* FILE * fpout; */
  char szIn[1024];
  DWORD dwInput[1024];
  DWORD dwProhibitChar;
  int iInputSize = 0;
  int counter = 0;
  int res;
  int i;

  /* buildProhibitInput(); */

  /* fpout = fopen( "../../../testdata/prohibit.output.txt", "w" ); */

  /* Arg check */
  if (argc < 2) { printf("usage: <unicode codepoints file>\n", argv[0] ); return 1; }

  /* Get file */
  fpin = fopen(argv[1], "r");
  if (fpin == NULL) { printf("Cannot open %s\n",argv[1]); return 1; }

  while ( !feof( fpin ) )
  {
    memset( szIn, 0, sizeof(szIn) );
    memset( dwInput, 0, sizeof(dwInput) );

    fgets( szIn, sizeof(szIn), fpin );
    if ( szIn[0] == ' ' || szIn[0] == '#' || strlen( szIn ) < 2 ) 
    {
      printf( szIn );
      continue;
    }

    /* Clip off \n */
    szIn[strlen(szIn)-1] = 0;

    Read32BitLine( szIn, dwInput, &iInputSize );

    res = Xcode_prohibitString( dwInput, iInputSize, &dwProhibitChar );

    counter++;

    if ( res != XCODE_SUCCESS ) 
    {
      char szMsg[1024];
      ConvertErrorCode( res, szMsg );
      printf( "Error: Line=%d '%25s' Char=%05X (%s)\n", counter, szMsg, dwProhibitChar, szIn );
      continue;
    }

    for( i = 0; i < iInputSize; i++ )
    { 
      if ( i > 0 ) printf( " " );
      printf( "%x", dwInput[i] );
    }
    printf( "\n" );
  }

  fclose(fpin);
  #ifdef WIN32
  getchar();
  #endif
  return 0;

}
