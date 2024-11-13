package cn.gd.gdgc;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import org.apache.commons.io.IOUtils;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

public class Test {
	public static String getToken() {
		String url = "https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid=APPID&secret=APPSECRET";
		String APPID = "wxa9e0fce7bc48eabd";
		String APPSECRET = "3cdaf197f4f6be76136a606a4a93a294";
		url = url.replace("APPID", APPID).replace("APPSECRET", APPSECRET);

		String r = MyRequest.get(url);
		JSONObject json = JSONObject.parseObject(r);// 把json格式的字符串转换为json对象
		String at = json.getString("access_token");
		// System.out.println(at);
		return at;
	}

	public static JSONArray getUsers(String at) {
		String url = "https://api.weixin.qq.com/cgi-bin/user/get?access_token=ACCESS_TOKEN&next_openid=NEXT_OPENID";
		url = url.replace("ACCESS_TOKEN", at).replace("NEXT_OPENID", "");
		String r = MyRequest.get(url);
		JSONObject json = JSONObject.parseObject(r);
		if ((json.getInteger("errcode") == null || json.getInteger("errcode") == 0) && json.getInteger("count") > 0) {
			JSONArray list = json.getJSONObject("data").getJSONArray("openid");
			return list;
		} else {
			return new JSONArray();
		}
	}

	// 指定发送信息
	public static boolean sendMsg(Object openid, String at, String message) {
		// http请求方式: POST
		// https://api.weixin.qq.com/cgi-bin/message/custom/send?access_token=ACCESS_TOKEN
		// String openid = "o9RAu6_v3T_b3-fq_EA09imUtTiQ";
		// String at = getToken();
		// String message = "早点睡觉";
		String url = "https://api.weixin.qq.com/cgi-bin/message/custom/send?access_token=ACCESS_TOKEN";
		url = url.replace("ACCESS_TOKEN", at);
		JSONObject text = new JSONObject();
		text.put("content", message);
		JSONObject p = new JSONObject();
		p.put("touser", openid);
		p.put("msgtype", "text");
		p.put("text", text);
		String param = p.toJSONString();
		String r = MyRequest.post(url, param);
		JSONObject json = JSONObject.parseObject(r);
		return json.getInteger("errcode") == 0;
	}

	// 群发
	public static int sendAllMsg(String at, String message) {
		int n = 0;
		JSONArray list = getUsers(at);
		for (Object openid : list) {
			if (sendMsg(openid, at, message)) {
				System.out.println("成功发送给" + openid + "的消息");
				n++;
			} else {
				System.out.println("发送给" + openid + "的消息失败");
			}
		}
		return n;
	}

	public static void main(String[] args) {
		String at = getToken();
		String url =
				"https://api.weixin.qq.com/cgi-bin/menu/create?access_token=ACCESS_TOKEN";
		url = url.replace("ACCESS_TOKEN", at);
		String param =
				"{\"button\":[{\"type\":\"view\",\"name\":\"主页\",\"key\":\"M1\",\"url\":\"https://www.baidu.com\"},"
						+ "{\"name\":\"班级\",\"sub_button\":[{\"type\":\"pic_photo_or_album\",\"key\":\"M21\",\"name\":\"照片\"},"
						+ "{\"type\":\"scancode_waitmsg\",\"key\":\"M22\",\"name\":\"签到\"},"
						+ "{\"type\":\"location_select\",\"key\":\"M23\",\"name\":\"我在这！！\"}]},"
						+ "{\"type\":\"view\",\"name\":\"商城\",\"key\":\"M3\",\"url\":\"URL\"}]}";
		String uri =
				"https://open.weixin.qq.com/connect/oauth2/authorize?appid=APPID&redirect_uri=REDIRECT_URL&response_type=code"
						+ "&scope=SCOPE&state=STATE#wechat_redirect";
		uri = uri.replace("APPID", "wxa9e0fce7bc48eabd")
				.replace("REDIRECT_URL", "http://dahle.natapp1.cc/Weixin/Weixin.jsp")
				.replace("SCOPE", "snsapi_userinfo");

		param = param.replace("URL", uri);

		String ret = MyRequest.post(url, param);
		JSONObject json = JSONObject.parseObject(ret);
		System.out.println(json);
//		JSONObject jsa = getUserInfo(code);
//		System.out.println(jsa);
	}
	// System.out.println(sendMsg("o9RAu6_v3T_b3-fq_EA09imUtTiQ",getToken(),"早点睡觉"));

//		JSONArray list = getUsers(at);
//		list.forEach((e) -> {
//			String openid = (String) e;
//			sendMBXX(at, openid);
//		});
//	}
//
//		String message = "早点睡觉";
//		int n = sendAllMsg(at, message);
//		System.out.println("成功发送" + n + "条记录。");


	public static void sendMBXX(String at, String openid) {
		String url = "https://api.weixin.qq.com/cgi-bin/message/template/send?access_token=ACCESS_TOKEN";
		url = url.replace("ACCESS_TOKEN", at);
		String param = "{\"touser\":\"" + openid +"\","
				+ "\"template_id\":\"4lgEQgISjZ9AE_Un0POQRPfkZwhVYE400zrQuDFZZhI\","
				+ "\"url\":\"http://www.bing.com\","
				+ "\"data\":{\"time\":{\"value\":\"2014年9月22日\"}}}";
		String ret = MyRequest.post(url, param);
		System.out.println(ret);
	}

	public static JSONObject getUserInfo(String code) {
		String url = "https://api.weixin.qq.com/sns/oauth2/access_token?appid=APPID&secret=SECRET&code=CODE"
				+ "&grant_type=authorization_code";
		url = url.replace("APPID", "wxa9e0fce7bc48eabd").replace("SECRET", "3cdaf197f4f6be76136a606a4a93a294")
				.replace("CODE", code);
		String ret = MyRequest.get(url);
		JSONObject json = JSONObject.parseObject(ret);
		if (json.getString("access_token") == null) {
			System.out.println(json);
			return null;
		}
		String at = json.getString("access_token");
		String openid = json.getString("openid");
		url = "https://api.weixin.qq.com/sns/userinfo?access_token=ACCESS_TOKEN&openid=OPENID&lang=zh_CN";
		url = url.replace("ACCESS_TOKEN", at).replace("OPENID", openid);
		ret = MyRequest.get(url);
		json = JSONObject.parseObject(ret);
		if (json.getString("nickname") == null) {
			System.out.println(json);
			return null;
		}
		return json;
	}

	public static void downloadFile(String downloadUrl, String path) {
		InputStream inputStream = null;
		OutputStream outputStream = null;
		try {
			URL url = new URL(downloadUrl);
			// 这里没有使用 封装后的ResponseEntity 就是也是因为这里不适合一次性的拿到结果，放不下content,会造成内存溢出
			HttpURLConnection connection = (HttpURLConnection) url.openConnection();

			// 使用bufferedInputStream 缓存流的方式来获取下载文件，不然大文件会出现内存溢出的情况
			inputStream = new BufferedInputStream(connection.getInputStream());
			File file = new File(path);
			if (file.exists()) {
				file.delete();
			}
			outputStream = new FileOutputStream(file);
			// 这里也很关键每次读取的大小为5M 不一次性读取完
			byte[] buffer = new byte[1024 * 1024 * 5];// 5MB
			int len = 0;
			while ((len = inputStream.read(buffer)) != -1) {
				outputStream.write(buffer, 0, len);
			}
			connection.disconnect();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			IOUtils.closeQuietly(outputStream);
			IOUtils.closeQuietly(inputStream);
		}
	}
}
