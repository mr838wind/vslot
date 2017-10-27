package com.wdfall.vslot.utils;

import java.io.File;
import java.io.IOException;

import org.apache.commons.io.FileUtils;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class FileUtil {

	private static final String FILE_ENCODING = "UTF-8";

	public static File getFileOnClasspath(String name) {
		ClassLoader loader = Thread.currentThread().getContextClassLoader();
		File file = new File(loader.getResource("").getPath(), name);
		return file;
	}
	
	public static void writeToFile(File file, String data) {
		try {
			FileUtils.writeStringToFile(file, data, FILE_ENCODING);
		} catch (IOException e) {
			log.error("",e);
			throw new RuntimeException(e);
		}
	}
	
	public static String readFromFile(File file) {
		String data = "";
		try {
			data = FileUtils.readFileToString(file, FILE_ENCODING);
		} catch (IOException e) {
			log.error("",e);
			throw new RuntimeException(e);
		}
		return data;
	}
	
}
