#!/bin/sh 
############################################################################
#                       NSI PROPRIETARY AND CONFIDENTIAL                   #
#                                                                          #
# This document includes trade secrets and proprietary commercial and/or   #
# financial information (collectively "NSI Proprietary Information")       #
# belonging to Network Solutions, Inc. (NSI).  Unauthorized disclosure     #
# and/or use of NSI Proprietary Information without the express written    #
# consent of Network Solutions, Inc. is strictly prohibited and may result #
# in criminal prosecution and/or civil penalties pursuant to "The Economic #
# Espionage Act of 1996", 18 U.S.C. Section 1831 et seq., and/or           #
# the "Virginia Uniform Trade Secrets Act", Va. Code Ann. Section 59.1-336.#
#                                                                          #
# Copyright (c) Network Solutions, Inc. 2000. All Rights Reserved.         #
#                                                                          #
############################################################################


if [ "$JAVA_HOME" = "" ] ; then
  echo "ERROR: JAVA_HOME not found in your environment."
  echo
  echo "Please, set the JAVA_HOME variable in your environment to match the"
  echo "location of the Java Virtual Machine you want to use."
  exit 1
fi

BASE_DIR=..
LIB_DIR=$BASE_DIR/../../lib

LOCALCLASSPATH=\
$JAVA_HOME/lib/tools.jar:\
$JAVA_HOME/lib/dev.jar:\
$LIB_DIR/ant-optional.jar:\
$LIB_DIR/ant.jar:\
$LIB_DIR/jakarta-oro-2.0.7.jar:\
$LIB_DIR/xerces.jar:\

# Compile and Run test
$JAVA_HOME/bin/java -Dant.home=$BASE_DIR -classpath $CLASSPATH:$LOCALCLASSPATH org.apache.tools.ant.Main $*
