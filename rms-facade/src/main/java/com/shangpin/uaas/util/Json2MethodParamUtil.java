/**
 * 
 */
package com.shangpin.uaas.util;

import java.io.IOException;
import java.lang.reflect.Type;
import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.databind.ser.std.BooleanSerializer;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import com.google.gson.JsonParser;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;


/**
 * @description  
 * @author  陈小峰 <br/>2015年5月7日
 */
public class Json2MethodParamUtil {
	private static final Logger logger = LoggerFactory.getLogger(Json2MethodParamUtil.class);
	private static GsonBuilder gb=new GsonBuilder();
	private static Gson gson = gb.create();
	static {
		gb.registerTypeAdapter(java.util.Date.class, new DateSerializer()).setDateFormat(DateFormat.LONG);
		gb.registerTypeAdapter(java.util.Date.class, new DateDeserializer()).setDateFormat(DateFormat.LONG);
		gb.registerTypeAdapter(Boolean.class,new BooleanDeserializer());
	}
	/**
	 * google Gson方式将json数组转换成类型数组对应的对象集合<br/>
	 * json对象里面的属性可以跟类型里面属性不一样
	 * @param jsonArray json数组
	 * @param paramTypes 方法参数数组
	 * @return
	 */
	public static List<?> gsonDeserialize(String jsonArray, Type[] paramTypes){

        JsonParser parser = new JsonParser();
		JsonArray methodParams = parser.parse(jsonArray).getAsJsonArray();
        List<Object> params = new ArrayList<Object>();
        for (int i = 0; i < methodParams.size(); i++) {
            logger.debug("The param {}'s type name is {}", i, paramTypes[i].toString());
            params.add(gson.fromJson(methodParams.get(i), paramTypes[i]));
            logger.debug("The converted param {}'s type name is {}", i, params.get(i).getClass().getName());
        }
        return params;
    }
	/**
	 * 将json数组转换成类型数组对应的对象集合<br/>
	 * <b style="color:red">注意：1.json对象里面的属性不能比类型里面的属性多！<br/>
	 * </b>
	 * 
	 * @param jsonArray json对象数组
	 * @param paramTypes 方法参数数组
	 * @return
	 */
	public static List<?> jacksonDeserialize(String jsonArray, Type[] paramTypes) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.configure(DeserializationFeature.ACCEPT_SINGLE_VALUE_AS_ARRAY, true);
        List<?> methodParams = objectMapper.readValue(jsonArray, List.class);
        List<Object> params = new ArrayList<Object>();
        for (int i = 0; i < methodParams.size(); i++) {
            logger.debug("The param {}'s type name is {}", i, paramTypes[i].toString());
            JavaType javaType = objectMapper.getTypeFactory().constructType(paramTypes[i]);
            params.add(objectMapper.convertValue(methodParams.get(i), javaType));
            logger.debug("The converted param {}'s type name is {}", i, params.get(i).getClass().getName());
        }
        return params;
    }
	static class DateSerializer implements JsonSerializer<Date>{
		@Override
		public JsonElement serialize(Date src, Type typeOfSrc,
				JsonSerializationContext context) {
			return new JsonPrimitive(src.getTime());
		}
		
	}
	static class DateDeserializer implements JsonDeserializer<Date> {
		@Override
		public Date deserialize(JsonElement json, Type typeOfT,
								JsonDeserializationContext context) throws JsonParseException {
			return new Date(json.getAsJsonPrimitive().getAsLong());
		}
	}
	static class BooleanDeserializer implements JsonDeserializer<Boolean> {
		@Override
		public Boolean deserialize(JsonElement json, Type typeOfT,JsonDeserializationContext context) throws JsonParseException {
			if(json==null){
				return null;
			}
			String val = json.getAsString();
			if(StringUtils.isBlank(val)){
				return null;
			}else if ("true".equals(val)){
				return true;
			}else{
				return false;
			}
		}
	}
	/*public static void main(String[] args) throws Exception {
		String json="[{\"returnUrl\":\"http://192.168.4.143:8080/shangpin/order/spdb/pay/callback\","
				+ "\"body\":\"尚品网轻奢的选择\",\"mobilePay\":\"1\",\"totalFee\":\"7777.00\","
				+ "\"orderNo\":\"20150507037745\",\"gateway\":\"SPDB\",\"subject\":\"尚品网轻奢的选择\","
				+ "\"notifyUrl\":\"http://192.168.4.143:8080/shangpin/order/spdb/pay/notify\","
				+ "\"customerIp\":\"127.0.0.1\",\"ext\":\"transType:1\",\"timeout\":\"1\","
				+ "\"currency\":\"CNY\"}]";
		String notify="[\"SPDB\",{\"Plain\":\"TranAbbr\u003dIPER|AcqSsn\u003d001354476481|MercDtTm\u003d20150508120230|TermSsn\u003d050800001079|RespCode\u003d00|TermCode\u003d00000000|MercCode\u003d912045110000101|TranAmt\u003d0.01|SettDate\u003d20150508\","
				+ "\"Signature\":\"992127a3ac89ccc39d8a6cb7ca53c838092563383d31ef5ea3a7bb53135cfc411fe47adf1981edd1f07e76b17ff86dfe8c904b9321ca87e26b9611b999de89475d4cb2b080b4e7e9ddc799204fcd7f4c04701447b080a21909094dbba55a38401cd0d8e8fff994a2e1b4d78a301b81bdf37e393406ddc5603fa9cb9b23779a89\"}]";
		Type[] paramTypes=PayFacade.class.getMethod("handlePayNotify", Gateway.class,Map.class).getGenericParameterTypes();
		List<?> params=gsonDeserialize(notify, paramTypes);
		//List<?> params=jacksonDeserialize(json, paramTypes); //会报错，如果json参数多余
		Gson gson = new Gson();
		List<Object> l = new ArrayList<>();
		l.add("SPDB");
		Map<String,String> m = new HashMap<>();
		m.put("plain", "ok");
		m.put("Signature", "sssSignature");
		l.add(m);
		System.out.println(gson.toJson(l));
		//System.out.println(params);
	}*/

}