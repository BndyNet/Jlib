/*******************************************************************************
 * Copyright (C) 2017 http://bndy.net
 * Created by Bendy (Bing Zhang)
 ******************************************************************************/
package net.bndy.lib;

import java.io.File;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * Json Utils
 */
public class JsonHelper {
	
	private static ObjectMapper objMapper = new ObjectMapper();
	
	static {
		objMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
		objMapper.configure(DeserializationFeature.FAIL_ON_NUMBERS_FOR_ENUMS, false);
		objMapper.configure(DeserializationFeature.FAIL_ON_NULL_FOR_PRIMITIVES, false);
		objMapper.configure(DeserializationFeature.USE_JAVA_ARRAY_FOR_JSON_ARRAY, true);
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm a z");
		objMapper.setDateFormat(df);
	}

	/**
	 * Converts an object to Json string.
	 *
	 * @param obj	the object
	 * @return		the Json string
	 */
    public static String toString(Object obj) {
        try {
			return objMapper.writeValueAsString(obj);
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}
        return null;
    }

	/**
	 * Parses a Json string to object.
	 *
	 * @param json			the json string
	 * @param valueType		the result type
	 * @param <T>			the type
	 * @return				an instance typed T
	 */
    public static <T> T parse(String json, Class<T> valueType) {
    	try {
			return objMapper.readValue(json, valueType);
		} catch (IOException e) {
			e.printStackTrace();
		}
    	return null;
    }

	/**
	 * Converts an object to Json string and saves to a file.
	 *
	 * @param obj		the object
	 * @param filepath	the file path
	 */
    public static void save2File(Object obj, String filepath) {
    	File file = new File(filepath);
    	file.getParentFile().mkdirs();
    	try {
			objMapper.writeValue(file, obj);
		} catch (IOException e) {
			e.printStackTrace();
		}
    }

	/**
	 * Parse a file which contains Json string.
	 *
	 * @param filepath	the file path
	 * @param valueType	the value type
	 * @param <T>		the type
	 * @return			an instance typed T
	 */
    public static <T> T parseFromFile(String filepath, Class<T> valueType) {
    	try {
			return objMapper.readValue(filepath, valueType);
		} catch (IOException e) {
			e.printStackTrace();
		}
    	return null;
    }
}
