# Microsoft Developer Studio Generated NMAKE File, Based on xcodelib.dsp
!IF "$(CFG)" == ""
CFG=xcodelib - Win32 Debug
!MESSAGE No configuration specified. Defaulting to xcodelib - Win32 Debug.
!ENDIF 

!IF "$(CFG)" != "xcodelib - Win32 Release" && "$(CFG)" != "xcodelib - Win32 Debug"
!MESSAGE Invalid configuration "$(CFG)" specified.
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
!ERROR An invalid configuration is specified.
!ENDIF 

!IF "$(OS)" == "Windows_NT"
NULL=
!ELSE 
NULL=nul
!ENDIF 

CPP=cl.exe
RSC=rc.exe

!IF  "$(CFG)" == "xcodelib - Win32 Release"

OUTDIR=.\Release
INTDIR=.\Release

ALL : "..\..\..\..\lib\win32\xcodelib.lib"


CLEAN :
	-@erase "$(INTDIR)\nameprep.obj"
	-@erase "$(INTDIR)\puny.obj"
	-@erase "$(INTDIR)\race.obj"
	-@erase "$(INTDIR)\toxxx.obj"
	-@erase "$(INTDIR)\util.obj"
	-@erase "$(INTDIR)\vc60.idb"
	-@erase "..\..\..\..\lib\win32\xcodelib.lib"

"$(OUTDIR)" :
    if not exist "$(OUTDIR)/$(NULL)" mkdir "$(OUTDIR)"

CPP_PROJ=/nologo /MD /Za /W3 /O1 /I "../../xcode/inc" /D "_WINDOWS" /D "_USRDLL" /D "NDEBUG" /D "_MBCS" /D "WIN32" /Fo"$(INTDIR)\\" /Fd"$(INTDIR)\\" /FD /c 
BSC32=bscmake.exe
BSC32_FLAGS=/nologo /o"$(OUTDIR)\xcodelib.bsc" 
BSC32_SBRS= \
	
LIB32=link.exe -lib
LIB32_FLAGS=/nologo /out:"../../../../lib/win32/xcodelib.lib" /NODEFAULTLIB:library 
LIB32_OBJS= \
	"$(INTDIR)\nameprep.obj" \
	"$(INTDIR)\puny.obj" \
	"$(INTDIR)\race.obj" \
	"$(INTDIR)\toxxx.obj" \
	"$(INTDIR)\util.obj"

"..\..\..\..\lib\win32\xcodelib.lib" : "$(OUTDIR)" $(DEF_FILE) $(LIB32_OBJS)
    $(LIB32) @<<
  $(LIB32_FLAGS) $(DEF_FLAGS) $(LIB32_OBJS)
<<

!ELSEIF  "$(CFG)" == "xcodelib - Win32 Debug"

OUTDIR=.\Debug
INTDIR=.\Debug
# Begin Custom Macros
OutDir=.\Debug
# End Custom Macros

ALL : "..\..\..\..\lib\win32\xcodelibdbg.lib" "$(OUTDIR)\xcodelib.bsc"


CLEAN :
	-@erase "$(INTDIR)\nameprep.obj"
	-@erase "$(INTDIR)\nameprep.sbr"
	-@erase "$(INTDIR)\puny.obj"
	-@erase "$(INTDIR)\puny.sbr"
	-@erase "$(INTDIR)\race.obj"
	-@erase "$(INTDIR)\race.sbr"
	-@erase "$(INTDIR)\toxxx.obj"
	-@erase "$(INTDIR)\toxxx.sbr"
	-@erase "$(INTDIR)\util.obj"
	-@erase "$(INTDIR)\util.sbr"
	-@erase "$(INTDIR)\vc60.idb"
	-@erase "$(INTDIR)\vc60.pdb"
	-@erase "$(OUTDIR)\xcodelib.bsc"
	-@erase "..\..\..\..\lib\win32\xcodelibdbg.lib"

"$(OUTDIR)" :
    if not exist "$(OUTDIR)/$(NULL)" mkdir "$(OUTDIR)"

CPP_PROJ=/nologo /MDd /W3 /Gm /ZI /Od /I "../../xcode/inc" /D "WIN32" /D "_DEBUG" /D "_MBCS" /D "_LIB" /FR"$(INTDIR)\\" /Fo"$(INTDIR)\\" /Fd"$(INTDIR)\\" /FD /GZ /c 
BSC32=bscmake.exe
BSC32_FLAGS=/nologo /o"$(OUTDIR)\xcodelib.bsc" 
BSC32_SBRS= \
	"$(INTDIR)\nameprep.sbr" \
	"$(INTDIR)\puny.sbr" \
	"$(INTDIR)\race.sbr" \
	"$(INTDIR)\toxxx.sbr" \
	"$(INTDIR)\util.sbr"

"$(OUTDIR)\xcodelib.bsc" : "$(OUTDIR)" $(BSC32_SBRS)
    $(BSC32) @<<
  $(BSC32_FLAGS) $(BSC32_SBRS)
<<

LIB32=link.exe -lib
LIB32_FLAGS=/nologo /out:"../../../../lib/win32/xcodelibdbg.lib" 
LIB32_OBJS= \
	"$(INTDIR)\nameprep.obj" \
	"$(INTDIR)\puny.obj" \
	"$(INTDIR)\race.obj" \
	"$(INTDIR)\toxxx.obj" \
	"$(INTDIR)\util.obj"

"..\..\..\..\lib\win32\xcodelibdbg.lib" : "$(OUTDIR)" $(DEF_FILE) $(LIB32_OBJS)
    $(LIB32) @<<
  $(LIB32_FLAGS) $(DEF_FLAGS) $(LIB32_OBJS)
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
!IF EXISTS("xcodelib.dep")
!INCLUDE "xcodelib.dep"
!ELSE 
!MESSAGE Warning: cannot find "xcodelib.dep"
!ENDIF 
!ENDIF 


!IF "$(CFG)" == "xcodelib - Win32 Release" || "$(CFG)" == "xcodelib - Win32 Debug"
SOURCE=..\..\xcode\src\nameprep.c

!IF  "$(CFG)" == "xcodelib - Win32 Release"


"$(INTDIR)\nameprep.obj" : $(SOURCE) "$(INTDIR)"
	$(CPP) $(CPP_PROJ) $(SOURCE)


!ELSEIF  "$(CFG)" == "xcodelib - Win32 Debug"


"$(INTDIR)\nameprep.obj"	"$(INTDIR)\nameprep.sbr" : $(SOURCE) "$(INTDIR)"
	$(CPP) $(CPP_PROJ) $(SOURCE)


!ENDIF 

SOURCE=..\..\xcode\src\puny.c

!IF  "$(CFG)" == "xcodelib - Win32 Release"


"$(INTDIR)\puny.obj" : $(SOURCE) "$(INTDIR)"
	$(CPP) $(CPP_PROJ) $(SOURCE)


!ELSEIF  "$(CFG)" == "xcodelib - Win32 Debug"


"$(INTDIR)\puny.obj"	"$(INTDIR)\puny.sbr" : $(SOURCE) "$(INTDIR)"
	$(CPP) $(CPP_PROJ) $(SOURCE)


!ENDIF 

SOURCE=..\..\xcode\src\race.c

!IF  "$(CFG)" == "xcodelib - Win32 Release"


"$(INTDIR)\race.obj" : $(SOURCE) "$(INTDIR)"
	$(CPP) $(CPP_PROJ) $(SOURCE)


!ELSEIF  "$(CFG)" == "xcodelib - Win32 Debug"


"$(INTDIR)\race.obj"	"$(INTDIR)\race.sbr" : $(SOURCE) "$(INTDIR)"
	$(CPP) $(CPP_PROJ) $(SOURCE)


!ENDIF 

SOURCE=..\..\xcode\src\toxxx.c

!IF  "$(CFG)" == "xcodelib - Win32 Release"


"$(INTDIR)\toxxx.obj" : $(SOURCE) "$(INTDIR)"
	$(CPP) $(CPP_PROJ) $(SOURCE)


!ELSEIF  "$(CFG)" == "xcodelib - Win32 Debug"


"$(INTDIR)\toxxx.obj"	"$(INTDIR)\toxxx.sbr" : $(SOURCE) "$(INTDIR)"
	$(CPP) $(CPP_PROJ) $(SOURCE)


!ENDIF 

SOURCE=..\..\xcode\src\util.c

!IF  "$(CFG)" == "xcodelib - Win32 Release"


"$(INTDIR)\util.obj" : $(SOURCE) "$(INTDIR)"
	$(CPP) $(CPP_PROJ) $(SOURCE)


!ELSEIF  "$(CFG)" == "xcodelib - Win32 Debug"


"$(INTDIR)\util.obj"	"$(INTDIR)\util.sbr" : $(SOURCE) "$(INTDIR)"
	$(CPP) $(CPP_PROJ) $(SOURCE)


!ENDIF 


!ENDIF 

