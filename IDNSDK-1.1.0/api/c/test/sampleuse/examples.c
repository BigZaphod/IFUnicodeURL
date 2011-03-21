
#include "xcode.h"

void testCharactermap( void )
{
  int res;
  DWORD dwOutput[1024];
  DWORD dwInput[] = { 0x1d56f, 0x1e22, 0x3a5 };

  int iInputSize = 3;
  int iOutputSize = sizeof(dwOutput);

  res = Xcode_charmapString( dwInput, iInputSize, dwOutput, &iOutputSize );

  if ( res != XCODE_SUCCESS ) 
  {
    /* Error */
  }
}

void testProhibit( void )
{
  int res;
  DWORD dwInput[] = { 0x1d56f, 0x1e22, 0x3a5 };
  DWORD dwProhibitChar;

  int iInputSize = 3;

  res = Xcode_prohibitString( dwInput, iInputSize, &dwProhibitChar );

  if ( res != XCODE_SUCCESS ) 
  {
    /* Error */
  }
}

void testNormalize( void )
{
  int res;
  DWORD dwOutput[1024];
  DWORD dwInput[] = { 0x1d56f, 0x1e22, 0x3a5 };

  int iInputSize = 3;
  int iOutputSize = sizeof(dwOutput);

  res = Xcode_normalizeString( dwInput, iInputSize, dwOutput, &iOutputSize );

  if ( res != XCODE_SUCCESS ) 
  {
    /* Error */
  }
}

void testNameprep( void )
{
  int res;
  DWORD dwOutput[1024];
  UTF16CHAR uInput[] = { 0xda00, 0xdc1c, 0xda00, 0xdc1d };
  DWORD dwProhibitChar;

  int iInputSize = 4;
  int iOutputSize = sizeof(dwOutput);

  res = Xcode_nameprepString( uInput, iInputSize, dwOutput, &iOutputSize, &dwProhibitChar );

  if ( res != XCODE_SUCCESS ) 
  {
    /* Error */
  }
}

void testBidiFilter( void )
{
  int res;
  DWORD dwInput[] = { 0x1d56f, 0x1e22, 0x3a5 };

  int iInputSize = 3;

  res = Xcode_bidifilterString( dwInput, iInputSize );

  if ( res != XCODE_SUCCESS ) 
  {
    /* Error */
  }
}

void testUTFConvert()
{
  int i;
  DWORD dwInput[5];
  UTF16CHAR uResult[256];
  DWORD dwResult[10];
  int iuResultLength = sizeof(uResult);
  int idwResultLength = sizeof(dwResult);

  for ( i = 0x90000; i <= 0x10FFFF; i = i + 4 )
  {
    dwInput[0] = i;
    dwInput[1] = i+1;
    dwInput[2] = i+2;
    dwInput[3] = i+3;

    Xcode_convert32BitToUTF16( dwInput, 4, uResult, &iuResultLength );

    Xcode_convertUTF16To32Bit( uResult, iuResultLength, dwResult, &idwResultLength );

    if ( memcmp( dwInput, dwResult, 4 ) != 0 )
    {
      /* Error */
    }
  }
}

void testPunycode( void )
{
  int res;
  UCHAR8 szOutput[1024];
  DWORD dwInput[] = { 0x1d56f, 0x1e22, 0x3a5 };
  UTF16CHAR uOutput[1024];

  int iInputSize = 3;
  int iOutputSize = sizeof(szOutput);

  res = Xcode_puny_encodeString( dwInput, iInputSize, szOutput, &iOutputSize );

  if ( res != XCODE_SUCCESS ) 
  {
    /* Error */
  }

  iInputSize = iOutputSize;
  iOutputSize = sizeof(uOutput);

  res = Xcode_puny_decodeString( szOutput, iInputSize, uOutput, &iOutputSize );

  if ( res != XCODE_SUCCESS ) 
  {
    /* Error */
  }
}

void testRaceDecode( void )
{
  int res;
  UTF16CHAR uOutput[1024];
  char * szIn = "bq--3b4mhtlrhbjsrzwy23orivhn";

  int iInputSize = strlen(szIn);
  int iOutputSize = sizeof(uOutput);

  res = Xcode_race_decodeString( szIn, iInputSize, uOutput, &iOutputSize, "bq--", 4 );

  if ( res != XCODE_SUCCESS ) 
  {
    /* Error */
  }
}

void testToASCII( void )
{
  int res;
  UTF16CHAR uInput[] = { 0x0070, 0x00E4, 0x00E4, 0x006F, 0x006D, 0x0061 };
  UCHAR8 szOutput[1204];

  int iInputSize = 6;
  int iOutputSize = sizeof(szOutput);

  res = Xcode_ToASCII( uInput, iInputSize, szOutput, &iOutputSize );

  if ( res != XCODE_SUCCESS ) 
  {
    /* Error */
  }
}

void testDomainToASCII( void )
{
  int res;
  UTF16CHAR uInput[] = { 0x0077, 0x0077, 0x0077, 0x002E, 0x0066, 
                         0x00FC, 0x006E, 0x0066, 0x0064, 0x3002, 0x006E, 0x0065, 0x0074 };
  UCHAR8 szOutput[1204];

  int iInputSize = 13;
  int iOutputSize = sizeof(szOutput);

  res = Xcode_DomainToASCII( uInput, iInputSize, szOutput, &iOutputSize );

  if ( res != XCODE_SUCCESS ) 
  {
    /* Error */
  }
}


