# Microsoft Developer Studio Generated NMAKE File, Based on headerexport.dsp
!IF "$(CFG)" == ""
CFG=headerexport - Win32 Debug
!MESSAGE No configuration specified. Defaulting to headerexport - Win32 Debug.
!ENDIF 

!IF "$(CFG)" != "headerexport - Win32 Release" && "$(CFG)" != "headerexport - Win32 Debug"
!MESSAGE Invalid configuration "$(CFG)" specified.
!MESSAGE You can specify a configuration when running NMAKE
!MESSAGE by defining the macro CFG on the command line. For example:
!MESSAGE 
!MESSAGE NMAKE /f "headerexport.mak" CFG="headerexport - Win32 Debug"
!MESSAGE 
!MESSAGE Possible choices for configuration are:
!MESSAGE 
!MESSAGE "headerexport - Win32 Release" (based on "Win32 (x86) Console Application")
!MESSAGE "headerexport - Win32 Debug" (based on "Win32 (x86) Console Application")
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

!IF  "$(CFG)" == "headerexport - Win32 Release"

OUTDIR=.\Release
INTDIR=.\Release

ALL : "..\..\..\..\tools\c\win32\headerexport.exe"


CLEAN :
	-@erase "$(INTDIR)\headerexport.obj"
	-@erase "$(INTDIR)\headergenrfc.obj"
	-@erase "$(INTDIR)\vc60.idb"
	-@erase "..\..\..\..\tools\c\win32\headerexport.exe"

"$(OUTDIR)" :
    if not exist "$(OUTDIR)/$(NULL)" mkdir "$(OUTDIR)"

CPP_PROJ=/nologo /MD /W3 /GX /O2 /I "../../xcode/inc" /D "WIN32" /D "NDEBUG" /D "_CONSOLE" /D "_MBCS" /Fo"$(INTDIR)\\" /Fd"$(INTDIR)\\" /FD /c 
BSC32=bscmake.exe
BSC32_FLAGS=/nologo /o"$(OUTDIR)\headerexport.bsc" 
BSC32_SBRS= \
	
LINK32=link.exe
LINK32_FLAGS=kernel32.lib user32.lib gdi32.lib winspool.lib comdlg32.lib advapi32.lib shell32.lib ole32.lib oleaut32.lib uuid.lib odbc32.lib odbccp32.lib kernel32.lib user32.lib gdi32.lib winspool.lib comdlg32.lib advapi32.lib shell32.lib ole32.lib oleaut32.lib uuid.lib odbc32.lib odbccp32.lib /nologo /subsystem:console /incremental:no /pdb:"$(OUTDIR)\headerexport.pdb" /machine:I386 /out:"../../../../tools/c/win32/headerexport.exe" /libpath:"../bin" 
LINK32_OBJS= \
	"$(INTDIR)\headerexport.obj" \
	"$(INTDIR)\headergenrfc.obj"

"..\..\..\..\tools\c\win32\headerexport.exe" : "$(OUTDIR)" $(DEF_FILE) $(LINK32_OBJS)
    $(LINK32) @<<
  $(LINK32_FLAGS) $(LINK32_OBJS)
<<

!ELSEIF  "$(CFG)" == "headerexport - Win32 Debug"

OUTDIR=.\Debug
INTDIR=.\Debug

ALL : "..\..\..\..\tools\c\win32\headerexport.exe"


CLEAN :
	-@erase "$(INTDIR)\headerexport.obj"
	-@erase "$(INTDIR)\headergenrfc.obj"
	-@erase "$(INTDIR)\vc60.idb"
	-@erase "$(INTDIR)\vc60.pdb"
	-@erase "$(OUTDIR)\headerexport.pdb"
	-@erase "..\..\..\..\tools\c\win32\headerexport.exe"
	-@erase "..\..\..\..\tools\c\win32\headerexport.ilk"

"$(OUTDIR)" :
    if not exist "$(OUTDIR)/$(NULL)" mkdir "$(OUTDIR)"

CPP_PROJ=/nologo /MDd /W3 /Gm /GX /ZI /Od /I "../../xcode/inc" /D "WIN32" /D "_DEBUG" /D "_CONSOLE" /D "_MBCS" /Fo"$(INTDIR)\\" /Fd"$(INTDIR)\\" /FD /GZ /c 
BSC32=bscmake.exe
BSC32_FLAGS=/nologo /o"$(OUTDIR)\headerexport.bsc" 
BSC32_SBRS= \
	
LINK32=link.exe
LINK32_FLAGS=kernel32.lib user32.lib gdi32.lib winspool.lib comdlg32.lib advapi32.lib shell32.lib ole32.lib oleaut32.lib uuid.lib odbc32.lib odbccp32.lib kernel32.lib user32.lib gdi32.lib winspool.lib comdlg32.lib advapi32.lib shell32.lib ole32.lib oleaut32.lib uuid.lib odbc32.lib odbccp32.lib /nologo /subsystem:console /incremental:yes /pdb:"$(OUTDIR)\headerexport.pdb" /debug /machine:I386 /out:"../../../../tools/c/win32/headerexport.exe" /pdbtype:sept /libpath:"../bin" 
LINK32_OBJS= \
	"$(INTDIR)\headerexport.obj" \
	"$(INTDIR)\headergenrfc.obj"

"..\..\..\..\tools\c\win32\headerexport.exe" : "$(OUTDIR)" $(DEF_FILE) $(LINK32_OBJS)
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
!IF EXISTS("headerexport.dep")
!INCLUDE "headerexport.dep"
!ELSE 
!MESSAGE Warning: cannot find "headerexport.dep"
!ENDIF 
!ENDIF 


!IF "$(CFG)" == "headerexport - Win32 Release" || "$(CFG)" == "headerexport - Win32 Debug"
SOURCE=..\..\xcode\headerexport\headerexport.c

"$(INTDIR)\headerexport.obj" : $(SOURCE) "$(INTDIR)"
	$(CPP) $(CPP_PROJ) $(SOURCE)


SOURCE=..\..\xcode\headerexport\headergenrfc.c

"$(INTDIR)\headergenrfc.obj" : $(SOURCE) "$(INTDIR)"
	$(CPP) $(CPP_PROJ) $(SOURCE)



!ENDIF 

