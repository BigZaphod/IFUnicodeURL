@echo off

rem ---- check if JAVA_HOME is set ----
if "%JAVA_HOME%" == "" goto nojavahome

set LIB_DIR=..\..\..\lib

set MYCLASSPATH=%JAVA_HOME%\lib\tools.jar
set MYCLASSPATH=%MYCLASSPATH%;%LIB_DIR%\ant-optional.jar
set MYCLASSPATH=%MYCLASSPATH%;%LIB_DIR%\ant.jar
set MYCLASSPATH=%MYCLASSPATH%;%LIB_DIR%\jakarta-oro-2.0.7.jar
set MYCLASSPATH=%MYCLASSPATH%;%LIB_DIR%\xerces.jar
set MYCLASSPATH=%MYCLASSPATH%;%LIB_DIR%\idnsdk.jar

rem ---- invoke the java class ----
%JAVA_HOME%\bin\java -classpath %MYCLASSPATH% com.vgrs.xcode.idna.test.PunycodeTest %*
goto done

:nojavahome
echo ERROR: JAVA_HOME environment variable not set

:done
