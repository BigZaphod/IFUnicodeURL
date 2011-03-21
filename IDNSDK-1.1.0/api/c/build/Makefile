
UNAME = $(shell uname -s)
PTHREAD = -lpthread

ifeq ($(UNAME),AIX)
 OS = aix
 Q32 = -q32
 ARCH = -G
endif
ifeq ($(UNAME),SunOS)
 OS = solaris
 ARCH = -G
endif
ifeq ($(UNAME),Linux)
 OS = linux
 ARCH = -shared
endif
ifeq ($(UNAME),Darwin)
 OS = osx
 ARCH = -shared
endif
ifeq ($(UNAME),FreeBSD)
 OS = freebsd
 ARCH = -shared
 PTHREAD = -pthread
endif

XCODE_INC_PATH = -I../xcode/inc

CC = gcc
CFLAGS = $(Q32) $(XCODE_INC_PATH)
AFLAGS = $(Q32) $(ARCH)

CSOURCE = nameprep puny race toxxx util

LIBRARIES = libxcode

# Build everything

all: clean directories $(CSOURCE) $(LIBRARIES) tools

directories:
	@printf "Creating obj and lib directories..."
	mkdir -p ../obj/db ../../../lib/$(OS) ../../../tools/c/$(OS)
	@printf "done\n"

# Build the xcodelib library

$(CSOURCE):
	$(CC) -c $(CFLAGS) ../xcode/src/$@.c -o ../obj/$@.o
	$(CC) -c $(DEBUG) $(CFLAGS) ../xcode/src/$@.c -o ../obj/db/$@.o

$(LIBRARIES):
	$(CC) $(AFLAGS)  -o ../../../lib/$(OS)/$@.so ../obj/*.o $(PTHREAD)
	$(CC) $(DEBUG) $(AFLAGS) -o ../../../lib/$(OS)/$@_db.so ../obj/db/*.o $(PTHREAD)
	cd ../../../lib/$(OS); ln -fs $@.so $@.a; ln -fs $@_db.so $@_db.a;

# Build the header export utility based on the current settings in
# xcode_config.h.

headerexport:
	$(CC) $(CFLAGS) $(LDFLAGS) \
	../xcode/headerexport/headerexport.c ../xcode/headerexport/headergenrfc.c \
	-o ../../../tools/c/$(OS)/headerexport
	$(CC) $(DEBUG) $(CFLAGS) $(LDFLAGS) \
	../xcode/headerexport/headerexport.c ../xcode/headerexport/headergenrfc.c \
	-o ../../../tools/c/$(OS)/headerexportdb

clean:
	rm -rf ../obj ../../../lib/$(OS); cd ../test; make clean

tools:
	cd ../test; make all







