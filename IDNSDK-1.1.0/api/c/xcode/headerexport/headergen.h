/*********************************************************************************/
/*                                                                               */
/* headergen                                                                     */
/*                                                                               */
/* Utility routines for generating inline nameprep and normalization             */
/* data header files.                                                            */
/*                                                                               */
/* (c) Verisign Inc., 2000-2003, All rights reserved                             */
/*                                                                               */
/*********************************************************************************/

#ifndef _DATAFILECONVERSION_H_
#define _DATAFILECONVERSION_H_

#include "xcode_config.h"

#ifdef __cplusplus
extern "C" 
{
#endif /* __cplusplus */

/*************************************************************************/
/*                                                                       */
/* <Function>                                                            */
/*                                                                       */
/*  Xcode_buildAllDataTables                                             */
/*                                                                       */
/* <Description>                                                         */
/*                                                                       */
/*  Utility routine that builds inline nameprep and normalization data   */
/*  tables used by INLINE_NAMEPREP_DATA compiles. Generates static       */
/*  tables headers from data files specified in ace_config.h. Generates  */
/*  the following files:                                                 */
/*                                                                       */
/*   normalize_static_compatible.h                                       */
/*   normalize_static_composite.h                                        */
/*   normalize_static_canonical.h                                        */
/*   normalize_static_decompose.h                                        */
/*   nameprep_static_charmap.h                                           */
/*   nameprep_static_prohibit.h                                          */
/*                                                                       */
/*  File location are based on a number of constants defined in          */
/*  xcode_config.h. See ace_config.h for more information.               */
/*                                                                       */
/*  In the default distribution, all generated headers are present.      */
/*  Headers need only be generated when nameprep or normalization data   */
/*  files are updated to newer versions.                                 */
/*                                                                       */
/*  Returns XCODE_SUCCESS if call was successful.                        */
/*                                                                       */
int Xcode_buildDataTables( void );
/*                                                                       */
/*************************************************************************/


#ifdef __cplusplus
}
#endif /* __cplusplus */

#endif
