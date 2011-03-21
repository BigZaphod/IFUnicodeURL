
/********************************************************************************
 *                              Fullcircle                                      *
 ********************************************************************************/

/*
  Purpose: C specific test harness.

  Usage	fullcircle <file>
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
  char szOut[1024];
  DWORD dwInput[1024];
  DWORD dwOutput[1024];
  UCHAR8 szData[1024];
  UTF16CHAR uInput[1024];
  int iInputSize = 0;
  int iOutputSize = 0;
  int counter = 0;
  int res;

  /* Arg check */
  if (argc < 2) { printf("usage: <fullcircle file>\n", argv[0] ); return 1; }

  /* Get file */
  fpin = fopen(argv[1], "r");
  if (fpin == NULL) { printf("Cannot open %s\n",argv[1]); return 1; }

  while ( !feof( fpin ) )
  {
    memset( szIn, 0, sizeof(szIn) );
    memset( szOut, 0, sizeof(szOut) );
    memset( dwInput, 0, sizeof(dwInput) );
    memset( dwOutput, 0, sizeof(dwOutput) );
    memset( szData, 0, sizeof(szData) );


    fgets( szIn, sizeof(szIn), fpin );
    counter++;
    if ( szIn[0] == ' ' || szIn[0] == '#' || strlen( szIn ) < 2 ) 
    {
      printf( szIn );
      continue;
    }
    fgets( szOut, sizeof(szOut), fpin );
    counter++;

    /* Clip off \n */
    szIn[strlen(szIn)-1] = 0;
    szOut[strlen(szOut)-1] = 0;

    if ( szIn[0] != 'i' ) { printf("Invalid input file format.\n"); return 1; }
    if ( szOut[0] != 'o' ) { printf("Invalid input file format.\n"); return 1; }

    if ( szOut[strlen(szOut)-1] == ' ' ) szOut[strlen(szOut)-1] = '\0';

    /*
    Read32BitLine( &szIn[2], dwInput, &iInputSize );
    iOutputSize = sizeof(dwOutput);
    res = Xcode_nameprepString32( dwInput, iInputSize, dwOutput, &iOutputSize, &dwProhibitChar );
    if ( res != XCODE_SUCCESS ) goto error;
    iInputSize = sizeof(szData); 
    res = Xcode_puny_encodeString( dwOutput, iOutputSize, szData, &iInputSize );
    */

    iInputSize = sizeof( dwInput );
    Read32BitLine( &szIn[2], dwInput, &iInputSize );

    iOutputSize = sizeof(uInput);
    res = Xcode_convert32BitToUTF16( dwInput, iInputSize, uInput, &iOutputSize );

    iInputSize = iOutputSize;

    iOutputSize = sizeof(szData);
    res = Xcode_ToASCII( uInput, iInputSize, szData, &iOutputSize );

    if ( res != XCODE_SUCCESS ) 
    {
      char szMsg[1024];
      ConvertErrorCode( res, szMsg );
      printf( "Fail: Line=%d '%25s' (%s)(%s)\n", counter, szMsg, szIn, szOut );
      continue;
    }

    if ( stricmp( &szOut[2], szData ) != 0 )
    {
      //printf( "%s\no:%s\n", szIn, szData );
      printf( "Error  : Line=%d '%s' != '%s'\n", counter, &szOut[2], szData );
      continue;
    }

    printf( "Success: Line=%d '%s'\n", counter, &szOut[2] );
    //printf( "%s\no:%s\n", szIn, szData );
  }

  fclose(fpin);
  #ifdef WIN32
  getchar();
  #endif
  return 0;
}
