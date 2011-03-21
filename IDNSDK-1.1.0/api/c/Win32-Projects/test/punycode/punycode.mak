# Microsoft Developer Studio Generated NMAKE File, Based on punycode.dsp
!IF "$(CFG)" == ""
CFG=punycode - Win32 Debug
!MESSAGE No configuration specified. Defaulting to punycode - Win32 Debug.
!ENDIF 

!IF "$(CFG)" != "punycode - Win32 Release" && "$(CFG)" != "punycode - Win32 Debug"
!MESSAGE Invalid configuration "$(CFG)" specified.
!MESSAGE You can specify a configuration when running NMAKE
!MESSAGE by defining the macro CFG on the command line. For example:
!MESSAGE 
!MESSAGE NMAKE /f "punycode.mak" CFG="punycode - Win32 Debug"
!MESSAGE 
!MESSAGE Possible choices for configuration are:
!MESSAGE 
!MESSAGE "punycode - Win32 Release" (based on "Win32 (x86) Console Application")
!MESSAGE "punycode - Win32 Debug" (based on "Win32 (x86) Console Application")
!MESSAGE 
!ERROR An invalid configuration is specified.
!ENDIF 

!IF "$(OS)" == "Windows_NT"
NULL=
!ELSE 
NULL=nul
!ENDIF 

CPP=cl.exe
RSC=rc.exe

!IF  "$(CFG)" == "punycode - Win32 Release"

OUTDIR=.\Release
INTDIR=.\Release

!IF "$(RECURSE)" == "0" 

ALL : "..\..\..\..\..\tools\c\win32\punycode.exe"

!ELSE 

ALL : "xcodelib - Win32 Release" "..\..\..\..\..\tools\c\win32\punycode.exe"

!ENDIF 

!IF "$(RECURSE)" == "1" 
CLEAN :"xcodelib - Win32 ReleaseCLEAN" 
!ELSE 
CLEAN :
!ENDIF 
	-@erase "$(INTDIR)\punycode.obj"
	-@erase "$(INTDIR)\vc60.idb"
	-@erase "..\..\..\..\..\tools\c\win32\punycode.exe"

"$(OUTDIR)" :
    if not exist "$(OUTDIR)/$(NULL)" mkdir "$(OUTDIR)"

CPP_PROJ=/nologo /MD /W3 /GX /O2 /I "../../../xcode/inc/" /D "WIN32" /D "NDEBUG" /D "_CONSOLE" /D "_MBCS" /Fo"$(INTDIR)\\" /Fd"$(INTDIR)\\" /FD /c 
BSC32=bscmake.exe
BSC32_FLAGS=/nologo /o"$(OUTDIR)\punycode.bsc" 
BSC32_SBRS= \
	
LINK32=link.exe
LINK32_FLAGS=kernel32.lib user32.lib gdi32.lib winspool.lib comdlg32.lib advapi32.lib shell32.lib ole32.lib oleaut32.lib uuid.lib odbc32.lib odbccp32.lib kernel32.lib user32.lib gdi32.lib winspool.lib comdlg32.lib advapi32.lib shell32.lib ole32.lib oleaut32.lib uuid.lib odbc32.lib odbccp32.lib /nologo /subsystem:console /incremental:no /pdb:"$(OUTDIR)\punycode.pdb" /machine:I386 /out:"../../../../../tools/c/win32/punycode.exe" 
LINK32_OBJS= \
	"$(INTDIR)\punycode.obj" \
	"..\..\..\..\..\lib\win32\xcodelib.lib"

"..\..\..\..\..\tools\c\win32\punycode.exe" : "$(OUTDIR)" $(DEF_FILE) $(LINK32_OBJS)
    $(LINK32) @<<
  $(LINK32_FLAGS) $(LINK32_OBJS)
<<

!ELSEIF  "$(CFG)" == "punycode - Win32 Debug"

OUTDIR=.\Debug
INTDIR=.\Debug

!IF "$(RECURSE)" == "0" 

ALL : "..\..\..\..\..\tools\c\win32\punycode.exe"

!ELSE 

ALL : "xcodelib - Win32 Debug" "..\..\..\..\..\tools\c\win32\punycode.exe"

!ENDIF 

!IF "$(RECURSE)" == "1" 
CLEAN :"xcodelib - Win32 DebugCLEAN" 
!ELSE 
CLEAN :
!ENDIF 
	-@erase "$(INTDIR)\punycode.obj"
	-@erase "$(INTDIR)\vc60.idb"
	-@erase "$(INTDIR)\vc60.pdb"
	-@erase "$(OUTDIR)\punycode.pdb"
	-@erase "..\..\..\..\..\tools\c\win32\punycode.exe"
	-@erase "..\..\..\..\..\tools\c\win32\punycode.ilk"

"$(OUTDIR)" :
    if not exist "$(OUTDIR)/$(NULL)" mkdir "$(OUTDIR)"

CPP_PROJ=/nologo /MDd /W3 /Gm /GX /ZI /Od /I "../../../xcode/inc/" /D "WIN32" /D "_DEBUG" /D "_CONSOLE" /D "_MBCS" /Fo"$(INTDIR)\\" /Fd"$(INTDIR)\\" /FD /GZ /c 
BSC32=bscmake.exe
BSC32_FLAGS=/nologo /o"$(OUTDIR)\punycode.bsc" 
BSC32_SBRS= \
	
LINK32=link.exe
LINK32_FLAGS=kernel32.lib user32.lib gdi32.lib winspool.lib comdlg32.lib advapi32.lib shell32.lib ole32.lib oleaut32.lib uuid.lib odbc32.lib odbccp32.lib kernel32.lib user32.lib gdi32.lib winspool.lib comdlg32.lib advapi32.lib shell32.lib ole32.lib oleaut32.lib uuid.lib odbc32.lib odbccp32.lib /nologo /subsystem:console /incremental:yes /pdb:"$(OUTDIR)\punycode.pdb" /debug /machine:I386 /out:"../../../../../tools/c/win32/punycode.exe" /pdbtype:sept 
LINK32_OBJS= \
	"$(INTDIR)\punycode.obj" \
	"..\..\..\..\..\lib\win32\xcodelibdbg.lib"

"..\..\..\..\..\tools\c\win32\punycode.exe" : "$(OUTDIR)" $(DEF_FILE) $(LINK32_OBJS)
    $(LINK32) @<<
  $(LINK32_FLAGS) $(LINK32_OBJS)
<<

!ENDIF 

.c{$(INTDIR)}.obj::
   $(CPP) @<<
   $(CPP_PROJ) $< 
<<

.cpp{$(INTDIR)}.obj::
   $(CPP) @<<
   $(CPP_PROJ) $< 
<<

.cxx{$(INTDIR)}.obj::
   $(CPP) @<<
   $(CPP_PROJ) $< 
<<

.c{$(INTDIR)}.sbr::
   $(CPP) @<<
   $(CPP_PROJ) $< 
<<

.cpp{$(INTDIR)}.sbr::
   $(CPP) @<<
   $(CPP_PROJ) $< 
<<

.cxx{$(INTDIR)}.sbr::
   $(CPP) @<<
   $(CPP_PROJ) $< 
<<


!IF "$(NO_EXTERNAL_DEPS)" != "1"
!IF EXISTS("punycode.dep")
!INCLUDE "punycode.dep"
!ELSE 
!MESSAGE Warning: cannot find "punycode.dep"
!ENDIF 
!ENDIF 


!IF "$(CFG)" == "punycode - Win32 Release" || "$(CFG)" == "punycode - Win32 Debug"
SOURCE=..\..\..\test\punycode\punycode.c

"$(INTDIR)\punycode.obj" : $(SOURCE) "$(INTDIR)"
	$(CPP) $(CPP_PROJ) $(SOURCE)


!IF  "$(CFG)" == "punycode - Win32 Release"

"xcodelib - Win32 Release" : 
   cd "\Documents and Settings\jim\Desktop\IDNSDK-1.0.2\api\c\Win32-Projects\xcodelib"
   $(MAKE) /$(MAKEFLAGS) /F ".\xcodelib.mak" CFG="xcodelib - Win32 Release" 
   cd "..\test\punycode"

"xcodelib - Win32 ReleaseCLEAN" : 
   cd "\Documents and Settings\jim\Desktop\IDNSDK-1.0.2\api\c\Win32-Projects\xcodelib"
   $(MAKE) /$(MAKEFLAGS) /F ".\xcodelib.mak" CFG="xcodelib - Win32 Release" RECURSE=1 CLEAN 
   cd "..\test\punycode"

!ELSEIF  "$(CFG)" == "punycode - Win32 Debug"

"xcodelib - Win32 Debug" : 
   cd "\Documents and Settings\jim\Desktop\IDNSDK-1.0.2\api\c\Win32-Projects\xcodelib"
   $(MAKE) /$(MAKEFLAGS) /F ".\xcodelib.mak" CFG="xcodelib - Win32 Debug" 
   cd "..\test\punycode"

"xcodelib - Win32 DebugCLEAN" : 
   cd "\Documents and Settings\jim\Desktop\IDNSDK-1.0.2\api\c\Win32-Projects\xcodelib"
   $(MAKE) /$(MAKEFLAGS) /F ".\xcodelib.mak" CFG="xcodelib - Win32 Debug" RECURSE=1 CLEAN 
   cd "..\test\punycode"

!ENDIF 


!ENDIF 

