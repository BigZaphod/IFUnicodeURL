############################################################################
#                       NSI PROPRIETARY AND CONFIDENTIAL                   #
#                                                                          #
# This  information  includes  trade  secrets  and confidential commercial #
# and/or financial information belonging to Network Solutions, Inc. (NSI). #
# It is exempt  from  disclosure  under  the  Freedom  of Information Act. #
# Unauthorized  disclosure  and/or  use of  this  information  without the #
# express  written  consent of  Network Solutions, Inc. is  prohibited and #
# may   result  in  criminal   prosecution  and   penalties   pursuant  to #
# 18 U.S.C. section 1905.                                                  #
#                                                                          #
#              COPYRIGHT Network Solutions, Inc. (Unpublished Work)        #
############################################################################

# Targets
all: _java _c
java: _java
c: _c
clean: _clean_java _clean_c
fresh: _fresh



# Java
_java:
	cd ../java/build; make all

_clean_java:
	cd ../java/build; make clean



# C
_c:
	cd ../c/build; make all

_clean_c:
	cd ../c/build; make clean



# Fresh
_fresh: clean
	rm -rf ../java/build/logs
