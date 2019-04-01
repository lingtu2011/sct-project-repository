package com.sct.springcloud.util;

import java.io.ByteArrayOutputStream;
import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;

import com.alibaba.fastjson.JSON;
import com.fasterxml.jackson.databind.ObjectMapper;

public class JsonUtil {

	private static final Logger log = Logger.getLogger(JsonUtil.class);
			
	public static String mapToJson(Object obj) {
		ByteArrayOutputStream bos = new ByteArrayOutputStream();
		ObjectMapper objectMapper = new ObjectMapper();
		try {
			objectMapper.writeValue(bos, objectMapper);
		} catch (Exception e) {
			log.info("JsonUtil-mapToJson error!"+e.getMessage());
		}
		return bos.toString();
	}
	
	@SuppressWarnings("unchecked")
	public static Map<String,Object> jsonToMap(String str) {
		Object obj = JSON.parse(str);
		if(obj instanceof Map) {
			return (Map<String, Object>) obj;
		}
		return new HashMap<String,Object>();
	}
}
