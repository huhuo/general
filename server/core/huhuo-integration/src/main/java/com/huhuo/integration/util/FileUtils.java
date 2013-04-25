package com.huhuo.integration.util;

/**
 * handle all file upload operation
 * 1, upload cache file, and return file's relative path;
 * 2, child module handle form submit data, store file's relative path to DB, 
 * and move file from cache directory to persist directory;
 * 3, supply a file file review interface
 * @author wuyuxuan
 */
public class FileUtils extends org.apache.commons.io.FileUtils {

	/** default post fix for upload file without post fix */
	public static final String DEFFAULT_POSTFIX = "binary";
	/** upload file marker */
	public static final String DEFFAULT_MARKER = ".";
	
	/**
	 * @see #getSuffix(String, String)
	 * @param filename
	 * @return
	 */
	public static String getSuffix(String filename) {
		return getSuffix(filename, DEFFAULT_MARKER);
	}
	/**
	 * get the suffix of file(filename) with "marker" as it's marker
	 * @param filename
	 * @param marker file's marker，for example, “.”
	 * @return return null if filename==null, and in regard to file without marker, return {@value #DEFFAULT_POSTFIX}
	 */
	public static String getSuffix(String filename, String marker) {
		String suffix = null;
		if(filename != null) {
			int indexOfMarker = filename.lastIndexOf(marker);
			if(indexOfMarker == -1) {
				suffix = DEFFAULT_POSTFIX;
			} else {
				suffix = filename.substring(filename.lastIndexOf(marker) + 1);
			}
		}
		return suffix;
	}
	
}
