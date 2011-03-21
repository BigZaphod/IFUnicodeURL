
#include <stdlib.h>
#include <stdio.h>

static void ConvertErrorCode( int code, char * pszMsg )
{
  switch( code )
  {
    /* xcode */
    case XCODE_SUCCESS:
    sprintf( pszMsg, "XCODE_SUCCESS" );
    break;
    case XCODE_BAD_ARGUMENT_ERROR:
    sprintf( pszMsg, "XCODE_BAD_ARGUMENT_ERROR" );
    break;
    case XCODE_MEMORY_ALLOCATION_ERROR:
    sprintf( pszMsg, "XCODE_MEMORY_ALLOCATION_ERROR" );
    break;
    case XCODE_BUFFER_OVERFLOW_ERROR:
    sprintf( pszMsg, "XCODE_BUFFER_OVERFLOW_ERROR" );
    break;

    /* nameprep */
    case XCODE_NAMEPREP_EXPANSIONERROR:
    sprintf( pszMsg, "XCODE_NAMEPREP_EXPANSIONERROR" );
    break;
    case XCODE_NAMEPREP_PROHIBITEDCHAR:
    sprintf( pszMsg, "XCODE_NAMEPREP_PROHIBITEDCHAR" );
    break;
    case XCODE_NAMEPREP_NULL_CHARACTER_PRESENT:
    sprintf( pszMsg, "XCODE_NAMEPREP_NULL_CHARACTER_PRESENT" );
    break;
    case XCODE_NAMEPREP_FIRSTLAST_BIDIERROR:
    sprintf( pszMsg, "XCODE_NAMEPREP_FIRSTLAST_BIDIERROR" );
    break;
    case XCODE_NAMEPREP_MIXED_BIDIERROR:
    sprintf( pszMsg, "XCODE_NAMEPREP_MIXED_BIDIERROR" );
    break;
    case XCODE_NAMEPREP_BAD_ARGUMENT_ERROR:
    sprintf( pszMsg, "XCODE_NAMEPREP_BAD_ARGUMENT_ERROR" );
    break;
    case XCODE_NAMEPREP_MEMORY_ALLOCATION_ERROR:
    sprintf( pszMsg, "XCODE_NAMEPREP_MEMORY_ALLOCATION_ERROR" );
    break;
    case XCODE_NAMEPREP_BUFFER_OVERFLOW_ERROR:
    sprintf( pszMsg, "XCODE_NAMEPREP_BUFFER_OVERFLOW_ERROR" );
    break;
    case XCODE_NAMEPREP_MAPPEDOUT:
    sprintf( pszMsg, "XCODE_NAMEPREP_MAPPEDOUT" );
    break;

    /* toxxx */
    case XCODE_TOXXX_STD3_NONLDH:
    sprintf( pszMsg, "XCODE_TOXXX_STD3_NONLDH" );
    break;
    case XCODE_TOXXX_STD3_HYPHENERROR:
    sprintf( pszMsg, "XCODE_TOXXX_STD3_HYPHENERROR" );
    break;
    case XCODE_TOXXX_ALREADYENCODED:
    sprintf( pszMsg, "XCODE_TOXXX_ALREADYENCODED" );
    break;
    case XCODE_TOXXX_INVALIDDNSLEN:
    sprintf( pszMsg, "XCODE_TOXXX_INVALIDDNSLEN" );
    break;
    case XCODE_TOXXX_CIRCLECHECKFAILED:
    sprintf( pszMsg, "XCODE_TOXXX_CIRCLECHECKFAILED" );
    break;

    /* util */
    case XCODE_UTIL_UTF16DECODEERROR:
    sprintf( pszMsg, "XCODE_UTIL_UTF16DECODEERROR" );
    break;
    case XCODE_UTIL_UTF16ENCODEERROR:
    sprintf( pszMsg, "XCODE_UTIL_UTF16ENCODEERROR" );
    break;

    default:
    sprintf( pszMsg, "%d", code );
    break;
  }
}

static int Read32BitLine( char * entry, DWORD * input, size_t * size ) 
{
  DWORD dwChar;
  char szTemp[8];
  int offset = -1;
  int tindex = 0;
  int i;

  int len = strlen( entry );  

  memset( szTemp, 0, sizeof( szTemp ) );

  *size = 0;

  for ( i = 0; i <= len; i++ )
  {
    if ( *(entry+i) == ' ' || *(entry+i) == '\n' || *(entry+i) == 0 )
    {
      dwChar = 0;
      if ( strlen( szTemp ) == 0 ) break;
      sscanf( szTemp, "%x", &dwChar );
      offset++;
      *(input+offset) = dwChar;
      memset( szTemp, 0, sizeof( szTemp ) );
      tindex = 0;
      continue;
    }
    szTemp[tindex] = *(entry+i);
    tindex++;
  }

  *size = offset+1;
  return 0;
}

static int Read16BitLine( char * entry, UTF16CHAR * input, size_t * size ) 
{
  DWORD dwChar;
  char szTemp[8];
  int offset = -1;
  int tindex = 0;
  int i;

  int len = strlen( entry );  

  memset( szTemp, 0, sizeof( szTemp ) );

  *size = 0;

  for ( i = 0; i <= len; i++ )
  {
    if ( *(entry+i) == ' ' || *(entry+i) == '\n' || *(entry+i) == 0 )
    {
      dwChar = 0;
      if ( strlen( szTemp ) == 0 ) break;
      sscanf( szTemp, "%x", &dwChar );
      offset++;
      *(input+offset) = (UTF16CHAR) dwChar;
      memset( szTemp, 0, sizeof( szTemp ) );
      tindex = 0;
      continue;
    }
    szTemp[tindex] = *(entry+i);
    tindex++;
  }

  *size = offset+1;
  return 0;
}

static int Read8BitLine( char * entry, UCHAR8 * input, size_t * size ) 
{
  DWORD dwChar;
  char szTemp[8];
  int offset = -1;
  int tindex = 0;
  int i;

  int len = strlen( entry );  

  memset( szTemp, 0, sizeof( szTemp ) );

  *size = 0;

  for ( i = 0; i <= len; i++ )
  {
    if ( *(entry+i) == ' ' || *(entry+i) == '\n' || *(entry+i) == 0 )
    {
      dwChar = 0;
      if ( strlen( szTemp ) == 0 ) break;
      sscanf( szTemp, "%x", &dwChar );
      offset++;
      *(input+offset) = (UCHAR8) dwChar;
      memset( szTemp, 0, sizeof( szTemp ) );
      tindex = 0;
      continue;
    }
    szTemp[tindex] = *(entry+i);
    tindex++;
  }

  *size = offset+1;
  return 0;
}
