# Microsoft Developer Studio Generated NMAKE File, Based on xcodedll.dsp
!IF "$(CFG)" == ""
CFG=xcodedll - Win32 Debug
!MESSAGE No configuration specified. Defaulting to xcodedll - Win32 Debug.
!ENDIF 

!IF "$(CFG)" != "xcodedll - Win32 Release" && "$(CFG)" != "xcodedll - Win32 Debug"
!MESSAGE Invalid configuration "$(CFG)" specified.
!MESSAGE You can specify a configuration when running NMAKE
!MESSAGE by defining the macro CFG on the command line. For example:
!MESSAGE 
!MESSAGE NMAKE /f "xcodedll.mak" CFG="xcodedll - Win32 Debug"
!MESSAGE 
!MESSAGE Possible choices for configuration are:
!MESSAGE 
!MESSAGE "xcodedll - Win32 Release" (based on "Win32 (x86) Dynamic-Link Library")
!MESSAGE "xcodedll - Win32 Debug" (based on "Win32 (x86) Dynamic-Link Library")
!MESSAGE 
!ERROR An invalid configuration is specified.
!ENDIF 

!IF "$(OS)" == "Windows_NT"
NULL=
!ELSE 
NULL=nul
!ENDIF 

CPP=cl.exe
MTL=midl.exe
RSC=rc.exe

!IF  "$(CFG)" == "xcodedll - Win32 Release"

OUTDIR=.\Release
INTDIR=.\Release

ALL : "..\..\..\..\lib\win32\xcode.dll"


CLEAN :
	-@erase "$(INTDIR)\nameprep.obj"
	-@erase "$(INTDIR)\puny.obj"
	-@erase "$(INTDIR)\race.obj"
	-@erase "$(INTDIR)\toxxx.obj"
	-@erase "$(INTDIR)\util.obj"
	-@erase "$(INTDIR)\vc60.idb"
	-@erase "$(INTDIR)\xcode.res"
	-@erase "$(INTDIR)\xcodedll.obj"
	-@erase "$(OUTDIR)\xcode.exp"
	-@erase "$(OUTDIR)\xcode.lib"
	-@erase "..\..\..\..\lib\win32\xcode.dll"

"$(OUTDIR)" :
    if not exist "$(OUTDIR)/$(NULL)" mkdir "$(OUTDIR)"

CPP_PROJ=/nologo /MD /W3 /O1 /I "../../xcode/inc" /D "WIN32" /D "NDEBUG" /D "_WINDOWS" /D "_MBCS" /D "_USRDLL" /D "XCODEDLL_EXPORTS" /Fo"$(INTDIR)\\" /Fd"$(INTDIR)\\" /FD /c 
MTL_PROJ=/nologo /D "NDEBUG" /mktyplib203 /win32 
RSC_PROJ=/l 0x409 /fo"$(INTDIR)\xcode.res" /d "NDEBUG" 
BSC32=bscmake.exe
BSC32_FLAGS=/nologo /o"$(OUTDIR)\xcodedll.bsc" 
BSC32_SBRS= \
	
LINK32=link.exe
LINK32_FLAGS=kernel32.lib user32.lib gdi32.lib winspool.lib comdlg32.lib advapi32.lib shell32.lib ole32.lib oleaut32.lib uuid.lib odbc32.lib odbccp32.lib /nologo /dll /incremental:no /pdb:"$(OUTDIR)\xcode.pdb" /machine:I386 /out:"../../../../lib/win32/xcode.dll" /implib:"$(OUTDIR)\xcode.lib" 
LINK32_OBJS= \
	"$(INTDIR)\nameprep.obj" \
	"$(INTDIR)\puny.obj" \
	"$(INTDIR)\race.obj" \
	"$(INTDIR)\toxxx.obj" \
	"$(INTDIR)\util.obj" \
	"$(INTDIR)\xcodedll.obj" \
	"$(INTDIR)\xcode.res"

"..\..\..\..\lib\win32\xcode.dll" : "$(OUTDIR)" $(DEF_FILE) $(LINK32_OBJS)
    $(LINK32) @<<
  $(LINK32_FLAGS) $(LINK32_OBJS)
<<

SOURCE="$(InputPath)"
DS_POSTBUILD_DEP=$(INTDIR)\postbld.dep

ALL : $(DS_POSTBUILD_DEP)

$(DS_POSTBUILD_DEP) : "..\..\..\..\lib\win32\xcode.dll"
   copy Release\xcode.lib ..\..\..\..\lib\win32\xcode.lib
	echo Helper for Post-build step > "$(DS_POSTBUILD_DEP)"

!ELSEIF  "$(CFG)" == "xcodedll - Win32 Debug"

OUTDIR=.\Debug
INTDIR=.\Debug

ALL : "..\..\..\..\..\bin\xcodedbg.dll"


CLEAN :
	-@erase "$(INTDIR)\nameprep.obj"
	-@erase "$(INTDIR)\puny.obj"
	-@erase "$(INTDIR)\race.obj"
	-@erase "$(INTDIR)\toxxx.obj"
	-@erase "$(INTDIR)\util.obj"
	-@erase "$(INTDIR)\vc60.idb"
	-@erase "$(INTDIR)\vc60.pdb"
	-@erase "$(INTDIR)\xcode.res"
	-@erase "$(INTDIR)\xcodedll.obj"
	-@erase "$(OUTDIR)\xcodedbg.exp"
	-@erase "$(OUTDIR)\xcodedbg.lib"
	-@erase "$(OUTDIR)\xcodedbg.pdb"
	-@erase "..\..\..\..\..\bin\xcodedbg.dll"
	-@erase "..\..\..\..\..\bin\xcodedbg.ilk"

"$(OUTDIR)" :
    if not exist "$(OUTDIR)/$(NULL)" mkdir "$(OUTDIR)"

CPP_PROJ=/nologo /MDd /W3 /Gm /ZI /Od /I "../../xcode/inc" /D "WIN32" /D "_DEBUG" /D "_WINDOWS" /D "_MBCS" /D "_USRDLL" /D "XCODEDLL_EXPORTS" /Fo"$(INTDIR)\\" /Fd"$(INTDIR)\\" /FD /GZ /c 
MTL_PROJ=/nologo /D "_DEBUG" /mktyplib203 /win32 
RSC_PROJ=/l 0x409 /fo"$(INTDIR)\xcode.res" /d "_DEBUG" 
BSC32=bscmake.exe
BSC32_FLAGS=/nologo /o"$(OUTDIR)\xcodedll.bsc" 
BSC32_SBRS= \
	
LINK32=link.exe
LINK32_FLAGS=kernel32.lib user32.lib gdi32.lib winspool.lib comdlg32.lib advapi32.lib shell32.lib ole32.lib oleaut32.lib uuid.lib odbc32.lib odbccp32.lib /nologo /dll /incremental:yes /pdb:"$(OUTDIR)\xcodedbg.pdb" /debug /machine:I386 /out:"../../../../../bin/xcodedbg.dll" /implib:"$(OUTDIR)\xcodedbg.lib" /pdbtype:sept 
LINK32_OBJS= \
	"$(INTDIR)\nameprep.obj" \
	"$(INTDIR)\puny.obj" \
	"$(INTDIR)\race.obj" \
	"$(INTDIR)\toxxx.obj" \
	"$(INTDIR)\util.obj" \
	"$(INTDIR)\xcodedll.obj" \
	"$(INTDIR)\xcode.res"

"..\..\..\..\..\bin\xcodedbg.dll" : "$(OUTDIR)" $(DEF_FILE) $(LINK32_OBJS)
    $(LINK32) @<<
  $(LINK32_FLAGS) $(LINK32_OBJS)
<<

SOURCE="$(InputPath)"
DS_POSTBUILD_DEP=$(INTDIR)\postbld.dep

ALL : $(DS_POSTBUILD_DEP)

$(DS_POSTBUILD_DEP) : "..\..\..\..\..\bin\xcodedbg.dll"
   copy Debug\xcodedbg.lib ..\..\..\..\lib\win32\xcodedbg.lib
	echo Helper for Post-build step > "$(DS_POSTBUILD_DEP)"

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
!IF EXISTS("xcodedll.dep")
!INCLUDE "xcodedll.dep"
!ELSE 
!MESSAGE Warning: cannot find "xcodedll.dep"
!ENDIF 
!ENDIF 


!IF "$(CFG)" == "xcodedll - Win32 Release" || "$(CFG)" == "xcodedll - Win32 Debug"
SOURCE=..\..\xcode\src\nameprep.c

"$(INTDIR)\nameprep.obj" : $(SOURCE) "$(INTDIR)"
	$(CPP) $(CPP_PROJ) $(SOURCE)


SOURCE=..\..\xcode\src\puny.c

"$(INTDIR)\puny.obj" : $(SOURCE) "$(INTDIR)"
	$(CPP) $(CPP_PROJ) $(SOURCE)


SOURCE=..\..\xcode\src\race.c

"$(INTDIR)\race.obj" : $(SOURCE) "$(INTDIR)"
	$(CPP) $(CPP_PROJ) $(SOURCE)


SOURCE=..\..\xcode\src\toxxx.c

"$(INTDIR)\toxxx.obj" : $(SOURCE) "$(INTDIR)"
	$(CPP) $(CPP_PROJ) $(SOURCE)


SOURCE=..\..\xcode\src\util.c

"$(INTDIR)\util.obj" : $(SOURCE) "$(INTDIR)"
	$(CPP) $(CPP_PROJ) $(SOURCE)


SOURCE=.\xcode.rc

"$(INTDIR)\xcode.res" : $(SOURCE) "$(INTDIR)"
	$(RSC) $(RSC_PROJ) $(SOURCE)


SOURCE=.\xcodedll.cpp

"$(INTDIR)\xcodedll.obj" : $(SOURCE) "$(INTDIR)"



!ENDIF 

