/*************************************************************************/
/*                                                                       */
/* Datafile.java                                                         */
/*                                                                       */
/* (c) VeriSign Inc., 2000-2003, All rights reserved                     */
/*                                                                       */
/* @author: John Colosi, Lihui Zhang, Srikanth Veeramachaneni            */
/* @date: Feburary, 2003                                                 */
/*                                                                       */
/*************************************************************************/


package com.vgrs.xcode.util;

import java.io.*;
import java.util.*;
import java.util.zip.*;

/**
 * Provides capability to retrieve data from compressed file from system
 * resource or a plain text from local file system.
 */
public class Datafile {

	static final String ZIP_SUFFIX = ".zip";
	static final String GZIP_SUFFIX = ".gz";

	/**
	 * Open the plain text file from the search path used to load classes.
	 * BufferedReader of the file.
	 * @param file a file to read
	 * @return BufferedReader object
	 * @throws XcodeException if an input stream for reading the file could not
	 *                        be found
	 * @deprecated  This method exists solely for use with reading data from
	 *				  a plain text file. The method has been deprecated because
	 *				  we no longer ship plain text data file in SDK library.
	 */
	static public BufferedReader getReader(String file) throws XcodeException {
		ClassLoader loader = Datafile.class.getClassLoader();
		InputStream inputStream = loader.getResourceAsStream(file);
		if (inputStream == null) {
			throw XcodeError.FILE_IO(": '" + file + "'");
		}
		return new BufferedReader(new InputStreamReader(inputStream));
	}



	/**
	 * Retrieve data from a file from the local file system
	 * @param file a File object
	 * @return an Iterator object of the file
	 */
	static public Iterator getIterator(File file) throws XcodeException {
		return getTextFileIterator(file);
	}

	/**
	 * Retrieve data from a gz/zip file from the search path used to load classes.
	 * If it is a plain text file, try also to read from local file system.
	 * @param file a file name
	 * @return an Iterator object of the file
	 */
	static public Iterator getIterator(String file) throws XcodeException {

		if (file==null) throw XcodeError.NULL_ARGUMENT();
		if (file.length()==0 ) {throw XcodeError.EMPTY_ARGUMENT();}

		if (file.endsWith(ZIP_SUFFIX)) {
			return getZipFileIterator(file);
		}
		else if ( file.endsWith(GZIP_SUFFIX) ) {
			return getGZipFileIterator(file);
		}
		else {
			return getTextFileIterator(file);
		}
	}



	/**
	 * Get an Interator of a text file from local file system.
	 * @param file a File object, represents a plain text file
	 * @return an Iterator object of the file
	 * @throws XcodeException if an input stream for reading the file could not
	 *                        be found
	 */
	static public Iterator getTextFileIterator(String file) throws XcodeException {
		return getTextFileIterator(new File(file));
	}

	static public Iterator getTextFileIterator(File file) throws XcodeException {
		try {
			BufferedReader reader = new BufferedReader(new FileReader(file));
			return (readData(reader)).iterator();
		}
		catch (IOException x) {
			throw XcodeError.FILE_IO(": '" + file + "'");
		}
	}



	/**
	 * Get an Interator of a .gz file from the search path of to load classes
	 * @param file a File object, represents a GZip file
	 * @return an Iterator object of the GZip File
	 * @throws XcodeException if an input stream for reading the file could not
	 *                        be found
	 */
	static public Iterator getGZipFileIterator(String file) throws XcodeException {

		try {
			ClassLoader loader = Datafile.class.getClassLoader();
			InputStream inputStream = loader.getResourceAsStream(file);
			if (inputStream == null) {
				throw XcodeError.FILE_IO(": '" + file + "'");
			}
			GZIPInputStream gis =
			new GZIPInputStream(inputStream);
			BufferedReader reader =
			new BufferedReader(new InputStreamReader(gis));
			return (readData(reader)).iterator();

		} catch (IOException x) {
			throw XcodeError.FILE_IO(": '" + file + "'");
		}

	}



	/**
	 * Get an Interator of a .zip file
	 * @param file a File object, represents a Zip file
	 * @return an Iterator object of the Zip File
	 * @throws XcodeException if an input stream for reading the file could not
	 *                        be found
	 */
	static public Iterator getZipFileIterator(String file) throws XcodeException {
		try {
			ClassLoader loader = Datafile.class.getClassLoader();
			InputStream inputStream = loader.getResourceAsStream(file);
			if (inputStream == null) {
				throw XcodeError.FILE_IO(": '" + file + "'");
			}
			Vector lines = new Vector();
			ZipInputStream zin =
			new ZipInputStream (new BufferedInputStream(inputStream));

			ZipEntry entry;
			while ( (entry = zin.getNextEntry()) != null ) {
				BufferedReader reader =
				new BufferedReader(new InputStreamReader(zin));
				lines.addAll(readData(reader));
			}
			return lines.iterator();
		}
		catch (IOException x) {
			throw XcodeError.FILE_IO(": '" + file + "'");
		}
	}



	/**
	 * Get an Interator from a BufferReader object
	 * @param buff a BufferReader object
	 * @return an Iterator object of the BufferReader
	 * @throws XcodeException if an BufferReader for reading the file could not
	 *                        be found
	 */

	static private Vector readData(BufferedReader buff) throws IOException {
		Vector lines = new Vector();
		String line = null;
		while ((line = buff.readLine()) != null) { lines.add(line); }
		return lines;
	}

}
