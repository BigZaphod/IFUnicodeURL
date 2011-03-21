# Microsoft Developer Studio Generated NMAKE File, Based on sampleuse.dsp
!IF "$(CFG)" == ""
CFG=sampleuse - Win32 Debug
!MESSAGE No configuration specified. Defaulting to sampleuse - Win32 Debug.
!ENDIF 

!IF "$(CFG)" != "sampleuse - Win32 Release" && "$(CFG)" != "sampleuse - Win32 Debug"
!MESSAGE Invalid configuration "$(CFG)" specified.
!MESSAGE You can specify a configuration when running NMAKE
!MESSAGE by defining the macro CFG on the command line. For example:
!MESSAGE 
!MESSAGE NMAKE /f "sampleuse.mak" CFG="sampleuse - Win32 Debug"
!MESSAGE 
!MESSAGE Possible choices for configuration are:
!MESSAGE 
!MESSAGE "sampleuse - Win32 Release" (based on "Win32 (x86) Console Application")
!MESSAGE "sampleuse - Win32 Debug" (based on "Win32 (x86) Console Application")
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

!IF  "$(CFG)" == "sampleuse - Win32 Release"

OUTDIR=.\Release
INTDIR=.\Release
# Begin Custom Macros
OutDir=.\Release
# End Custom Macros

!IF "$(RECURSE)" == "0" 

ALL : "$(OUTDIR)\sampleuse.exe"

!ELSE 

ALL : "xcodelib - Win32 Release" "$(OUTDIR)\sampleuse.exe"

!ENDIF 

!IF "$(RECURSE)" == "1" 
CLEAN :"xcodelib - Win32 ReleaseCLEAN" 
!ELSE 
CLEAN :
!ENDIF 
	-@erase "$(INTDIR)\examples.obj"
	-@erase "$(INTDIR)\vc60.idb"
	-@erase "$(OUTDIR)\sampleuse.exe"

"$(OUTDIR)" :
    if not exist "$(OUTDIR)/$(NULL)" mkdir "$(OUTDIR)"

CPP_PROJ=/nologo /MD /W3 /GX /O2 /I "../../../xcode/inc" /I "../../../test/utility" /D "WIN32" /D "NDEBUG" /D "_CONSOLE" /D "_MBCS" /Fp"$(INTDIR)\sampleuse.pch" /YX /Fo"$(INTDIR)\\" /Fd"$(INTDIR)\\" /FD /c 
BSC32=bscmake.exe
BSC32_FLAGS=/nologo /o"$(OUTDIR)\sampleuse.bsc" 
BSC32_SBRS= \
	
LINK32=link.exe
LINK32_FLAGS=kernel32.lib user32.lib gdi32.lib winspool.lib comdlg32.lib advapi32.lib shell32.lib ole32.lib oleaut32.lib uuid.lib odbc32.lib odbccp32.lib kernel32.lib user32.lib gdi32.lib winspool.lib comdlg32.lib advapi32.lib shell32.lib ole32.lib oleaut32.lib uuid.lib odbc32.lib odbccp32.lib /nologo /subsystem:console /incremental:no /pdb:"$(OUTDIR)\sampleuse.pdb" /machine:I386 /out:"$(OUTDIR)\sampleuse.exe" 
LINK32_OBJS= \
	"$(INTDIR)\examples.obj" \
	"..\..\..\..\..\lib\win32\xcodelib.lib"

"$(OUTDIR)\sampleuse.exe" : "$(OUTDIR)" $(DEF_FILE) $(LINK32_OBJS)
    $(LINK32) @<<
  $(LINK32_FLAGS) $(LINK32_OBJS)
<<

!ELSEIF  "$(CFG)" == "sampleuse - Win32 Debug"

OUTDIR=.\Debug
INTDIR=.\Debug
# Begin Custom Macros
OutDir=.\Debug
# End Custom Macros

!IF "$(RECURSE)" == "0" 

ALL : "$(OUTDIR)\sampleuse.exe"

!ELSE 

ALL : "xcodelib - Win32 Debug" "$(OUTDIR)\sampleuse.exe"

!ENDIF 

!IF "$(RECURSE)" == "1" 
CLEAN :"xcodelib - Win32 DebugCLEAN" 
!ELSE 
CLEAN :
!ENDIF 
	-@erase "$(INTDIR)\examples.obj"
	-@erase "$(INTDIR)\vc60.idb"
	-@erase "$(INTDIR)\vc60.pdb"
	-@erase "$(OUTDIR)\sampleuse.exe"
	-@erase "$(OUTDIR)\sampleuse.ilk"
	-@erase "$(OUTDIR)\sampleuse.pdb"

"$(OUTDIR)" :
    if not exist "$(OUTDIR)/$(NULL)" mkdir "$(OUTDIR)"

CPP_PROJ=/nologo /MDd /W3 /Gm /GX /ZI /Od /I "../../../xcode/inc" /I "../../../test/utility" /D "WIN32" /D "_DEBUG" /D "_CONSOLE" /D "_MBCS" /Fp"$(INTDIR)\sampleuse.pch" /YX /Fo"$(INTDIR)\\" /Fd"$(INTDIR)\\" /FD /GZ /c 
BSC32=bscmake.exe
BSC32_FLAGS=/nologo /o"$(OUTDIR)\sampleuse.bsc" 
BSC32_SBRS= \
	
LINK32=link.exe
LINK32_FLAGS=kernel32.lib user32.lib gdi32.lib winspool.lib comdlg32.lib advapi32.lib shell32.lib ole32.lib oleaut32.lib uuid.lib odbc32.lib odbccp32.lib kernel32.lib user32.lib gdi32.lib winspool.lib comdlg32.lib advapi32.lib shell32.lib ole32.lib oleaut32.lib uuid.lib odbc32.lib odbccp32.lib /nologo /subsystem:console /incremental:yes /pdb:"$(OUTDIR)\sampleuse.pdb" /debug /machine:I386 /out:"$(OUTDIR)\sampleuse.exe" /pdbtype:sept 
LINK32_OBJS= \
	"$(INTDIR)\examples.obj" \
	"..\..\..\..\..\lib\win32\xcodelibdbg.lib"

"$(OUTDIR)\sampleuse.exe" : "$(OUTDIR)" $(DEF_FILE) $(LINK32_OBJS)
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
!IF EXISTS("sampleuse.dep")
!INCLUDE "sampleuse.dep"
!ELSE 
!MESSAGE Warning: cannot find "sampleuse.dep"
!ENDIF 
!ENDIF 


!IF "$(CFG)" == "sampleuse - Win32 Release" || "$(CFG)" == "sampleuse - Win32 Debug"
SOURCE=..\..\..\test\sampleuse\examples.c

"$(INTDIR)\examples.obj" : $(SOURCE) "$(INTDIR)"
	$(CPP) $(CPP_PROJ) $(SOURCE)


!IF  "$(CFG)" == "sampleuse - Win32 Release"

"xcodelib - Win32 Release" : 
   cd "\Documents and Settings\jim\Desktop\IDNSDK-1.0.2\api\c\Win32-Projects\xcodelib"
   $(MAKE) /$(MAKEFLAGS) /F ".\xcodelib.mak" CFG="xcodelib - Win32 Release" 
   cd "..\TEST\sampleuse"

"xcodelib - Win32 ReleaseCLEAN" : 
   cd "\Documents and Settings\jim\Desktop\IDNSDK-1.0.2\api\c\Win32-Projects\xcodelib"
   $(MAKE) /$(MAKEFLAGS) /F ".\xcodelib.mak" CFG="xcodelib - Win32 Release" RECURSE=1 CLEAN 
   cd "..\TEST\sampleuse"

!ELSEIF  "$(CFG)" == "sampleuse - Win32 Debug"

"xcodelib - Win32 Debug" : 
   cd "\Documents and Settings\jim\Desktop\IDNSDK-1.0.2\api\c\Win32-Projects\xcodelib"
   $(MAKE) /$(MAKEFLAGS) /F ".\xcodelib.mak" CFG="xcodelib - Win32 Debug" 
   cd "..\TEST\sampleuse"

"xcodelib - Win32 DebugCLEAN" : 
   cd "\Documents and Settings\jim\Desktop\IDNSDK-1.0.2\api\c\Win32-Projects\xcodelib"
   $(MAKE) /$(MAKEFLAGS) /F ".\xcodelib.mak" CFG="xcodelib - Win32 Debug" RECURSE=1 CLEAN 
   cd "..\TEST\sampleuse"

!ENDIF 


!ENDIF 

