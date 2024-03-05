package com.lixin.utils;


import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.*;

import java.util.List;



public final class JsonUtils {
	private JsonUtils(){}
	public final static ObjectMapper MAPPER = newMapper();

	public static ObjectMapper newMapper() {
		ObjectMapper mapper = new ObjectMapper();
		//mapper.getSerializationConfig().setSerializationInclusion(inclusion);
		//设置输入时忽略JSON字符串中存在而Java对象实际没有的属性
		//mapper.getDeserializationConfig().set(org.codehaus.jackson.map.DeserializationConfig.Feature.FAIL_ON_UNKNOWN_PROPERTIES, false);
		mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
		//mapper.getSerializationConfig().setDateFormat(df);
		//mapper.getDeserializationConfig().setDateFormat(df);
		// 美化输出
		//mapper.enable(SerializationFeature.INDENT_OUTPUT);
		// 允许序列化空的POJO类
		// （否则会抛出异常）
		mapper.disable(SerializationFeature.FAIL_ON_EMPTY_BEANS);
		// 把java.util.Date, Calendar输出为数字（时间戳）
		mapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);

		// 在遇到未知属性的时候不抛出异常
		mapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
		// 强制JSON 空字符串("")转换为null对象值:
		//mapper.enable(DeserializationFeature.ACCEPT_EMPTY_STRING_AS_NULL_OBJECT);

		// 在JSON中允许C/C++ 样式的注释(非标准，默认禁用)
		mapper.configure(JsonParser.Feature.ALLOW_COMMENTS, true);
		// 允许没有引号的字段名（非标准）
		mapper.configure(JsonParser.Feature.ALLOW_UNQUOTED_FIELD_NAMES, true);
		// 允许单引号（非标准）
		mapper.configure(JsonParser.Feature.ALLOW_SINGLE_QUOTES, true);
		// 强制转义非ASCII字符
		//mapper.configure(JsonGenerator.Feature.ESCAPE_NON_ASCII, true);
		// 将内容包裹为一个JSON属性，属性名由@JsonRootName注解指定
		//mapper.configure(SerializationFeature.WRAP_ROOT_VALUE, true);
		return mapper;
	}

	/**
	 * 将json字符串转化为指定的类型
	 * @param json                json字符串
	 * @param clazz                实例class
	 * @return
	 */
	public static <T> T toObject(String json,Class<T> clazz) {
		try {
			return MAPPER.readValue(json, clazz);
		} catch (JsonProcessingException e) {
			return null;
		}
	}

	/**
	 * 将json字符串转化为指定的类型
	 * @param json		json字符串
	 * @param type		反序列化的类型映射
	 * @param <T>		要转成的对象
	 * @return
	 */
	public static <T> T toObject(String json,TypeReference<T> type) {
		try {
			return MAPPER.readValue(json,type);
		} catch (JsonProcessingException e) {
			return null;
		}
	}

	/**
	 * 将json字符串转化为指定类型的对象
	 * @param json		json字符串
	 * @param javaType	反序列化的类型映射
	 * @param <T>
	 * @return
	 */
	public static <T> T toObject(String json, JavaType javaType) {
		try {
			return MAPPER.readValue(json,javaType);
		} catch (JsonProcessingException e) {
			return null;
		}
	}

	/**
	 * 将json字符串转化为对象，json数据是一个数组或者list数据
	 * @param json			json字符串
	 * @param clazz			List和数组包含的实例class
	 * @return
	 */
	public static <T> List<T> toObjects(String json,Class<T> clazz) {
		try {
			TypeReference<List<T>> reference = new TypeReference<List<T>>(){};
			return MAPPER.readValue(json,reference);
		} catch (JsonProcessingException e) {
			return null;
		}
	}

	/**
	 * 将json字符串转化为对象，json数据是一个数组或者list数据
	 *
	 * @param json  json字符串
	 * @param clazz List和数组包含的实例class
	 * @return
	 */
	public static <T> List<T> toObjectList(String json, Class<T> clazz) {
		try {
			return MAPPER.readValue(json, MAPPER.getTypeFactory().constructCollectionType(List.class, clazz));
		} catch (JsonProcessingException e) {
			return null;
		}
	}

	/**
	 * 将对象转为json字符串
	 * @param object	要转换的对象
	 * @return	json格式的字符串
	 */
	public static String toJsonString(Object object) {
		try {
			return MAPPER.writeValueAsString(object);
		} catch (JsonProcessingException e) {
            e.printStackTrace();
			return null;
		}
	}
	
}
