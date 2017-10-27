package com.wdfall.vslot.utils;

import com.rits.cloning.Cloner;

/**
 * @author chhan
 */
public class CloneUtils {

	/**
	 * deepCopy by json
	 * @param obj
	 * @param clazz
	 * @return
	 */
	public static <T> T deepCopyByJson(T obj, Class<T> clazz) {
		String json = JsonBuilder.objectToJson(obj);
		T copied = JsonBuilder.jsonToObject(json, clazz);
		return copied;
	}
	
	// deepCopy by library 
	//uk.com.robust-it : cloning : 1.9.6
	private static Cloner cloner = new Cloner();
	/**
	 * deepCopy by library 
	 * @param obj
	 * @return
	 */
	public static <T> T deepCopyByLib(T obj) {
		return cloner.deepClone(obj);
	}
	
	
}
