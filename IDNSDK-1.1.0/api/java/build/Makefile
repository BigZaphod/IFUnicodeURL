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
 #                                                                          #
 #                                                                          #
 # Third-Party Acknowledgements:                                            #
 #                                                                          #
 #  - This product includes software developed by the                       #
 #    Apache Software Foundation (http://www.apache.org/)                   #
 ############################################################################


.PHONY: package

# This points to the root of the build
SRC_DIR := ..

# This is used to name the buildlogs
DATE := $(shell date +%Y%m%d%H%M%S)

# This is the location of all buildlogs
LOGDIR := logs

# These are the possible targets
TARGETS := \
 all\
 init\
 prepare\
 pre-compile\
 compile\
 jar\
 doc\
 clean\

# Operating System switching
UNAME = $(shell uname -s)
ifeq ($(UNAME),AIX)
 OS = aix
endif
ifeq ($(UNAME),Linux)
 OS = linux
endif
ifeq ($(UNAME),SunOS)
 OS = solaris
endif

default: all

prep:
	mkdir -p $(LOGDIR)

help:
	@echo "make <target>"
	@echo " target ="
	@for item in $(TARGETS); do \
		echo "  $$item"; \
	done;

$(TARGETS): prep
	./build.sh -Dos.name=$(OS) -Djava.home=${JAVA_HOME} $@ 2>&1 | tee $(LOGDIR)/$(DATE).$@
