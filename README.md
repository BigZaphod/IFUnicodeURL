# About

IFUnicodeURL is a category for NSURL which will allow it to support [Internationalized domain names]() in URLs.

# Usage

Usage is quite simple. Where you'd have normally used NSURL's `URLWithString:` (or `initWithString:`) methods, simply use the unicode versions added by the category. There is also a method to retrieve the URL as a string with the hostname converted back into Unicode:

	NSURL *url = [NSURL URLWithUnicodeString:@"http://➡.ws/鞰齒"];
	NSLog( @"The URL: %@", [url absoluteString] );
	NSLog( @"The Unicode URL: %@", [url unicodeAbsoluteString] );

# Notes

This uses code from IDN SDK from Verisign, Inc. The entire IDN SDK source package is included in IDNSDK-1.1.0.zip. I have pulled out and slightly modified (to avoid compiler and analyzer warnings) the files and headers needed so that building this in Xcode is as easy as adding the IFUnicodeURL folder to your project.

Take note of the IDNSDK license which can be found in the IDNSDK-1.1.0.zip file. (The license is basically a BSD-like license.) The IFUnicodeURL category is licensed under the Simplified BSD License (see IFUnicodeURL-LICENSE.txt)

# Author

Sean Heber ([@BigZaphod](http://twitter.com/BigZaphod/) on Twitter)
<http://www.iconfactory.com>
