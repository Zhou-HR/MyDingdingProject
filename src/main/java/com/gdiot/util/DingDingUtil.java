package com.gdiot.util;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

import javax.mail.MessagingException;
import java.util.HashMap;
import java.util.Map;

/**
 * @author ZhouHR
 */
@Slf4j
public class DingDingUtil {
	
	private static String dingding_url = com.gdiot.ssm.util.PropertiesUtil.getValue("dingding_url");
	
	public static String send(String userCode,String userID,String msg){
		msg= com.gdiot.ssm.util.DateUtil2.getTodayTime()+" "+msg;
		Map<String,String> param = new HashMap<String,String>();
		//toDo 正式要被注释
		userID="350002";
		log.info("===============20190709yxl  钉钉发消息 =================="+userCode+" "+userID+" "+msg);
		if(StringUtils.isEmpty(userID)){
			return "钉钉userId为空";
		}
			
		param.put("userID", userID);
		param.put("msg", msg);
		
		String result="超时";
		try{
			result = HttpClientUtil.postJson(dingding_url, param, "UTF-8");
			
			return result;
//			Map result_map = JSONObject.parseObject(result);
		}catch(Exception e){
			e.printStackTrace();
			
		}finally{
		}
		
		return result;
		
	}

	public static void main(String[] args) throws MessagingException, Exception {
		// TODO Auto-generated method stub
//		send("","汉");
	}

}
