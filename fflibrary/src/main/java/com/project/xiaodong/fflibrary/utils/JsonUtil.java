package com.project.xiaodong.fflibrary.utils;

import org.codehaus.jackson.JsonNode;
import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.annotate.JsonAutoDetect.Visibility;
import org.codehaus.jackson.annotate.JsonMethod;
import org.codehaus.jackson.map.DeserializationConfig.Feature;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.map.annotate.JsonSerialize.Inclusion;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;


/**
 * Json解析基盘类
 * @author zhourr_
 *
 */
public class JsonUtil {
	/** ObjectMapper是线程安全的，可用单例模式，据说可提升性能47倍 */
	public static ObjectMapper mapper = null;
	
	public JSONObject jsonData;

	public JsonUtil(){
	}
	
	public JsonUtil(JSONObject jsonData){
		this.jsonData = jsonData;
	}
	
	/**
	 * 解析jsonString
	 * @param jsonString
	 * @param clsBean
	 * @return
	 */
	public static <T> T parse(String jsonString, Class<T> clsBean){
		try {
			mapper = getObjectMapper();
			return mapper.readValue(jsonString, clsBean);
		} catch (JsonParseException e) {
			e.printStackTrace();
		} catch (JsonMappingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * 解析jsonString
	 * @param jsonString
	 * @param clsBean
	 * @return
	 */
	public static <T> ArrayList<T> parseList(String jsonString, Class<T> clsBean){
		try {
			mapper = getObjectMapper();
			org.codehaus.jackson.JsonParser parser = mapper.getJsonFactory().createJsonParser(jsonString);   
			JsonNode nodes = parser.readValueAsTree();
			if(nodes == null || nodes.size() == 0){
				return new ArrayList<T>();
			}
			ArrayList<T> list  = new ArrayList<T>(nodes.size());
			for(JsonNode node : nodes){
				list.add(mapper.readValue(node, clsBean));
			}
			return list;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	/**
	 * 对象转jsonstring
	 * @param o
	 */
	public static String objectToString(Object o){
		String result = "";
		try {
			mapper = getObjectMapper();
			result = mapper.writeValueAsString(o);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}
	
	/**
	 * 获取ObjectMapper对象
	 * @return
	 */
	public static ObjectMapper getObjectMapper(){
		if(mapper == null){
			mapper = new ObjectMapper();
			mapper.setVisibility(JsonMethod.FIELD, Visibility.ANY);
			mapper.configure(Feature.FAIL_ON_UNKNOWN_PROPERTIES, false);
			mapper.setSerializationInclusion(Inclusion.NON_NULL);
		}
		return mapper;
	}
	
}