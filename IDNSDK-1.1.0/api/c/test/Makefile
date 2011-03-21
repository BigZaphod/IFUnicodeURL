UNAME = $(shell uname -s)

ifeq ($(UNAME),AIX)
 OS = aix
 Q32 = -q32
endif
ifeq ($(UNAME),SunOS)
 OS = solaris
 XCODE_RUN_PATH = -R../../../../lib/$(OS)
endif
ifeq ($(UNAME),Linux)
 OS = linux
 XCODE_RUN_PATH = -Wl,-R../../../../lib/$(OS)
endif
ifeq ($(UNAME),Darwin)
 OS = osx
endif
ifeq ($(UNAME),FreeBSD)
 OS = freebsd
endif

XCODE_INC_PATH = -I../xcode/inc
XCODE_LIB_PATH = -L../../../lib/$(OS) $(XCODE_RUN_PATH)
XCODE_LIB = -lxcode

CC = gcc
CFLAGS = $(Q32) $(XCODE_INC_PATH)
LDFLAGS = $(XCODE_LIB_PATH)

all:	_punycode _race _charmap _prohibit _idna _unicode _domains _utf8

clean:
	rm -rf ../../../tools/c/$(OS)

_punycode:
	$(CC) $(CFLAGS) $(LDFLAGS) $(XCODE_LIB) punycode/punycode.c -o ../../../tools/c/$(OS)/punycode

_race:
	$(CC) $(CFLAGS) $(LDFLAGS) $(XCODE_LIB) race/race.c -o ../../../tools/c/$(OS)/race

_charmap:
	$(CC) $(CFLAGS) $(LDFLAGS) $(XCODE_LIB) charmap/charmap.c -o ../../../tools/c/$(OS)/charmap

_prohibit:
	$(CC) $(CFLAGS) $(LDFLAGS) $(XCODE_LIB) prohibit/prohibit.c -o ../../../tools/c/$(OS)/prohibit

_idna:
	$(CC) $(CFLAGS) $(LDFLAGS) $(XCODE_LIB) idna/idna.c -o ../../../tools/c/$(OS)/idna

_unicode:
	$(CC) $(CFLAGS) $(LDFLAGS) $(XCODE_LIB) unicode/unicode.c -o ../../../tools/c/$(OS)/unicode

_domains:
	$(CC) $(CFLAGS) $(LDFLAGS) $(XCODE_LIB) domains/domains.c -o ../../../tools/c/$(OS)/domains

_utf8:
	$(CC) $(CFLAGS) $(LDFLAGS) $(XCODE_LIB) utf8/utf8.c -o ../../../tools/c/$(OS)/utf8
