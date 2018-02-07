package com.project.xiaodong.fflibrary.utils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by xiaodong.jin on 2018/2/6.
 * description：根据参数生成加密签名
 */

public class SignUtil {
    /**
     * 对传人的参数进行MD5加密
     */
    public static HashMap<String, Object> createSignMap(HashMap<String, Object> params, String key){
        if(params == null){
            return params;
        }
        StringBuilder sb = new StringBuilder();
        List<Map.Entry<String, Object>> entries =
                new ArrayList<Map.Entry<String, Object>>(params.entrySet());
        for (int index = entries.size() - 1; index >= 0; index--) {
            Map.Entry<String, Object> entry = entries.get(index);
            Object obj = entry.getValue();
            sb.append(entry.getKey() + "=" + String.valueOf(obj) + "&");
        }
        sb.append(key);


        String sign = Md5Util.MD5(sb.toString());
        params.put("sign", sign);
        return params;
    }

    /**
     * 对传人的参数进行MD5加密
     */
    public static HashMap<String, String> createStringSignMap(HashMap<String, String> params, String key){
        if(params == null){
            return params;
        }
        StringBuilder sb = new StringBuilder();
        List<Map.Entry<String, String>> entries =
                new ArrayList<Map.Entry<String, String>>(params.entrySet());
        for (int index = entries.size() - 1; index >= 0; index--) {
            Map.Entry<String, String> entry = entries.get(index);
            Object obj = entry.getValue();
            sb.append(entry.getKey() + "=" + String.valueOf(obj) + "&");
        }
        sb.append(key);

        String sign = Md5Util.MD5(sb.toString());
        params.put("sign", sign);
        return params;
    }

    /**
     * 对传人的参数进行MD5加密
     */
    public static String createSignString(HashMap<String, Object> params){
        StringBuilder sb = new StringBuilder();
        for(String key:  params.keySet()){
            Object obj = params.get(key);
            sb.append(key + "=" + String.valueOf(obj) +  "&");
        }
        if(sb.length() > 0){
            sb.deleteCharAt(sb.length()-1);
        }
        return md5Encode(sb.toString());
    }

//	public static String NoParameter(){
//		return md5Encode(SECURITY_KEY);
//	}

    /**
     * md5加密
     * */
    public static String md5Encode(String paramString ){
        SecurityEncoderUtil securityEncoderUtils = SecurityEncoderUtil.getInstance("MD5");
        String encodeStr = securityEncoderUtils.encode(paramString);
        return encodeStr;
    }

}
