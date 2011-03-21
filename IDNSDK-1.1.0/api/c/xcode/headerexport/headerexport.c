/*********************************************************************************/
/*                                                                               */
/* headerexport                                                                  */
/*                                                                               */
/* Command line entry point to headergen.                                        */
/*                                                                               */
/* (c) Verisign Inc., 2000-2003, All rights reserved                             */
/*                                                                               */
/*********************************************************************************/

#include "xcode.h"
#include "headergen.h"

#include <stdio.h>

int main(int argc, char *argv[]) 
{
  Xcode_buildDataTables();
  getchar();
  return 0;
}

