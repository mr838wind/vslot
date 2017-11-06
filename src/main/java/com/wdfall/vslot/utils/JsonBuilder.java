package com.wdfall.vslot.utils;

import java.io.IOException;

import org.codehaus.jackson.map.ObjectMapper;

import lombok.extern.slf4j.Slf4j;

/**
 * @author Administrator
 * Json 관련 유틸
 */
@Slf4j
public class JsonBuilder {

	private static ObjectMapper mapper = new ObjectMapper();

	private JsonBuilder(){
	}

	/**
	 * Object 를 Json String으로 변환
	 * @param obj
	 * @return
	 */
	public static String objectToJson(Object obj)  {
		String jsonInString = null;
		try {
			jsonInString = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(obj);	
		} catch (IOException e) {
			log.error("",e);
			jsonInString = "";
			throw new RuntimeException("objectToJson exception", e);
		}
		return jsonInString;
	}

	/**
	 * Json String을 Object&lt;T&gt;로 변환
	 * @param json
	 * @param clazz
	 * @return
	 */
	public static <T> T jsonToObject(String json, Class<T> clazz) {
		T result = null;
		try {
			result = mapper.readValue(json, clazz);
		} catch (Exception e) {
			log.error("",e);
			throw new RuntimeException("jsonToObject exception", e);
		}
		return result;
	}
	
}
