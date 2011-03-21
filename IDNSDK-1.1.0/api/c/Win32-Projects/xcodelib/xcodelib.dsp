# Microsoft Developer Studio Project File - Name="xcodelib" - Package Owner=<4>
# Microsoft Developer Studio Generated Build File, Format Version 6.00
# ** DO NOT EDIT **

# TARGTYPE "Win32 (x86) Static Library" 0x0104

CFG=xcodelib - Win32 Debug
!MESSAGE This is not a valid makefile. To build this project using NMAKE,
!MESSAGE use the Export Makefile command and run
!MESSAGE 
!MESSAGE NMAKE /f "xcodelib.mak".
!MESSAGE 
!MESSAGE You can specify a configuration when running NMAKE
!MESSAGE by defining the macro CFG on the command line. For example:
!MESSAGE 
!MESSAGE NMAKE /f "xcodelib.mak" CFG="xcodelib - Win32 Debug"
!MESSAGE 
!MESSAGE Possible choices for configuration are:
!MESSAGE 
!MESSAGE "xcodelib - Win32 Release" (based on "Win32 (x86) Static Library")
!MESSAGE "xcodelib - Win32 Debug" (based on "Win32 (x86) Static Library")
!MESSAGE 

# Begin Project
# PROP AllowPerConfigDependencies 0
# PROP Scc_ProjName ""
# PROP Scc_LocalPath ""
CPP=cl.exe
RSC=rc.exe

!IF  "$(CFG)" == "xcodelib - Win32 Release"

# PROP BASE Use_MFC 0
# PROP BASE Use_Debug_Libraries 0
# PROP BASE Output_Dir "Release"
# PROP BASE Intermediate_Dir "Release"
# PROP BASE Target_Dir ""
# PROP Use_MFC 0
# PROP Use_Debug_Libraries 0
# PROP Output_Dir "Release"
# PROP Intermediate_Dir "Release"
# PROP Target_Dir ""
# ADD BASE CPP /nologo /W3 /GX /O2 /D "WIN32" /D "NDEBUG" /D "_MBCS" /D "_LIB" /YX /FD /c
# ADD CPP /nologo /MD /Za /W3 /O1 /I "../../xcode/inc" /D "_WINDOWS" /D "_USRDLL" /D "NDEBUG" /D "_MBCS" /D "WIN32" /FD /c
# SUBTRACT CPP /X /YX
# ADD BASE RSC /l 0x409 /d "NDEBUG"
# ADD RSC /l 0x409 /d "NDEBUG"
BSC32=bscmake.exe
# ADD BASE BSC32 /nologo
# ADD BSC32 /nologo
LIB32=link.exe -lib
# ADD BASE LIB32 /nologo
# ADD LIB32 /nologo /out:"../../../../lib/win32/xcodelib.lib" /NODEFAULTLIB:library

!ELSEIF  "$(CFG)" == "xcodelib - Win32 Debug"

# PROP BASE Use_MFC 0
# PROP BASE Use_Debug_Libraries 1
# PROP BASE Output_Dir "Debug"
# PROP BASE Intermediate_Dir "Debug"
# PROP BASE Target_Dir ""
# PROP Use_MFC 0
# PROP Use_Debug_Libraries 1
# PROP Output_Dir "Debug"
# PROP Intermediate_Dir "Debug"
# PROP Target_Dir ""
# ADD BASE CPP /nologo /W3 /Gm /GX /ZI /Od /D "WIN32" /D "_DEBUG" /D "_MBCS" /D "_LIB" /YX /FD /GZ /c
# ADD CPP /nologo /MDd /W3 /Gm /ZI /Od /I "../../xcode/inc" /D "WIN32" /D "_DEBUG" /D "_MBCS" /D "_LIB" /FR /FD /GZ /c
# SUBTRACT CPP /X /YX
# ADD BASE RSC /l 0x409 /d "_DEBUG"
# ADD RSC /l 0x409 /d "_DEBUG"
BSC32=bscmake.exe
# ADD BASE BSC32 /nologo
# ADD BSC32 /nologo
LIB32=link.exe -lib
# ADD BASE LIB32 /nologo
# ADD LIB32 /nologo /out:"../../../../lib/win32/xcodelibdbg.lib"

!ENDIF 

# Begin Target

# Name "xcodelib - Win32 Release"
# Name "xcodelib - Win32 Debug"
# Begin Group "Source Files"

# PROP Default_Filter "cpp;c;cxx;rc;def;r;odl;idl;hpj;bat"
# Begin Source File

SOURCE=..\..\xcode\src\nameprep.c
# End Source File
# Begin Source File

SOURCE=..\..\xcode\src\puny.c
# End Source File
# Begin Source File

SOURCE=..\..\xcode\src\race.c
# End Source File
# Begin Source File

SOURCE=..\..\xcode\src\toxxx.c
# End Source File
# Begin Source File

SOURCE=..\..\xcode\src\util.c
# End Source File
# End Group
# Begin Group "Header Files"

# PROP Default_Filter "h;hpp;hxx;hm;inl"
# Begin Source File

SOURCE=..\..\xcode\inc\adapter.h
# End Source File
# Begin Source File

SOURCE=..\..\xcode\inc\nameprep.h
# End Source File
# Begin Source File

SOURCE=..\..\xcode\inc\staticdata\nameprep_data.h
# End Source File
# Begin Source File

SOURCE=..\..\xcode\inc\staticdata\nameprep_datastructures.h
# End Source File
# Begin Source File

SOURCE=..\..\xcode\inc\staticdata\nameprep_lookups.h
# End Source File
# Begin Source File

SOURCE=..\..\xcode\inc\puny.h
# End Source File
# Begin Source File

SOURCE=..\..\xcode\inc\race.h
# End Source File
# Begin Source File

SOURCE=..\xcodedll\StdAfx.h
# End Source File
# Begin Source File

SOURCE=..\..\xcode\inc\toxxx.h
# End Source File
# Begin Source File

SOURCE=..\..\xcode\inc\util.h
# End Source File
# Begin Source File

SOURCE=..\..\xcode\inc\xcode.h
# End Source File
# Begin Source File

SOURCE=..\..\xcode\inc\xcode_config.h
# End Source File
# End Group
# Begin Source File

SOURCE=..\..\docs\License.txt
# End Source File
# Begin Source File

SOURCE=..\..\docs\README.txt
# End Source File
# End Target
# End Project
