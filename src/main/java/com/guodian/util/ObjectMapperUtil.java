package com.guodian.util;

import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * 	该工具类对象转化中的try catch问题
 * 	1.对象转JSON  toJSON
 * 	2.JSON转对象	 toObject
 * @author lenovo
 *
 */
public class ObjectMapperUtil {
	private static final ObjectMapper mapper = new ObjectMapper();
	//对象转json
	public static String toJSON(Object obj) {
		String json=null;
		try {
			json = mapper.writeValueAsString(obj);
		} catch (Exception e) {
			e.printStackTrace();
			//报错需要转化为运行时异常
			throw new RuntimeException();
		}
		return json;
	}
	//json转对象  传入的是什么类型转的就是什么类型
	public static <T> T toObject(String json,Class<T> target) {
		T t=null;
		try {
			t = mapper.readValue(json, target);
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException();
		}
		return t;
	}
}
