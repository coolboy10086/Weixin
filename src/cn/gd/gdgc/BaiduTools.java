package cn.gd.gdgc;

import java.util.Date;
import java.util.HashMap;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

public class BaiduTools {
	private static HashMap<String,JSONArray> history=new HashMap<String,JSONArray>(); 
	private static String BaiduAccessToken=null;
	private static long BaiduAccessToken_expire_time=0;
	private static long BaiduAccessToken_create_time=0; 
	private static String API_KEY="DO0cU2fWUBc0YnAGnYvmZTgn"; 
	private static String SECRET_KEY="zj3Z8TVbymnoF629FI2UmCI1Izl1LQP5"; 

	private static String getBaiduToken() {
		if (BaiduAccessToken == null
				|| new Date().getTime() - BaiduAccessToken_create_time > BaiduAccessToken_expire_time * 1000) {
			String url = "https://aip.baidubce.com/oauth/2.0/token?grant_type=GRANT_TYPE&client_id=API_KEY&client_secret=SECRET_KEY";
			url = url.replace("API_KEY", API_KEY).replace("SECRET_KEY", SECRET_KEY).replace("GRANT_TYPE","client_credentials");
			String ret = MyRequest.post(url, "");
			System.out.println(ret);
			JSONObject json = JSONObject.parseObject(ret);
			 
			if (json.getString("access_token")!=null) {
				BaiduAccessToken = json.getString("access_token");
				BaiduAccessToken_create_time = new Date().getTime();
				BaiduAccessToken_expire_time = json.getIntValue("expires_in");
			}
		}
		return BaiduAccessToken;

	}
	public static String AiChat(String openid,String content){
		String url="https://aip.baidubce.com/rpc/2.0/ai_custom/v1/wenxinworkshop/chat/yi_34b_chat?access_token=ACCESS_TOKEN";
		url=url.replace("ACCESS_TOKEN",getBaiduToken());
		JSONArray hist=history.get(openid);
		if(hist==null){
			hist=new JSONArray();
		}
		JSONObject json=new JSONObject();
		json.put("role", "user");
		json.put("content", content);
		hist.add(json);
		JSONObject param=new JSONObject();
		param.put("messages", hist);
		param.put("user_id", openid); 
		String ret=MyRequest.post(url, param.toJSONString());
		System.out.println(ret);
		JSONObject result = JSONObject.parseObject(ret);
		if(result.getString("result")!=null){
			JSONObject reply=new JSONObject();
			reply.put("role", "assistant");
			reply.put("content", result.getString("result"));
			hist.add(reply);
			history.put(openid, hist); 
			return result.getString("result");
		}else{
			System.out.println(param);
		}
		return null;
	}
	public static void main(String[] args) {
		System.out.println(AiChat("aa","开始"));

	}

}