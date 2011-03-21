@echo off

rem ---- check if JAVA_HOME is set ----
if "%JAVA_HOME%" == "" goto nojavahome

set BASE_DIR=..
set LIB_DIR=%BASE_DIR%\..\..\lib

set MYCLASSPATH=%JAVA_HOME%\lib\tools.jar
set MYCLASSPATH=%MYCLASSPATH%;%LIB_DIR%\ant-optional.jar
set MYCLASSPATH=%MYCLASSPATH%;%LIB_DIR%\ant.jar
set MYCLASSPATH=%MYCLASSPATH%;%LIB_DIR%\jakarta-oro-2.0.7.jar
set MYCLASSPATH=%MYCLASSPATH%;%LIB_DIR%\xerces.jar

rem ---- invoke ant to start the build ----
%JAVA_HOME%\bin\java -Dant.home=%BASE_DIR% -classpath %MYCLASSPATH% org.apache.tools.ant.Main %*
goto done

:nojavahome
echo ERROR: JAVA_HOME environment variable not set

:done
