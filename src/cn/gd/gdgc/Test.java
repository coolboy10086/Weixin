/**
 * 从JSON对象中获取openid列表
 * 
 * @param json 包含用户数据的JSON对象
 * @return 如果data和openid字段存在，则返回openid的JSONArray，否则返回空的JSONArray
 */
public JSONArray getUserOpenIds(JSONObject json) {
	if (json.containsKey("data") && json.getJSONObject("data").containsKey("openid")) {
		JSONArray list = json.getJSONObject("data").getJSONArray("openid");
		return list;
	} else {
		return new JSONArray();
	}
}

/**
 * 发送消息给指定用户
 * 
 * @param openid  用户的openid
 * @param at      授权令牌
 * @param message 要发送的消息内容
 * @return 如果消息发送成功，则返回true，否则返回false
 */
public static boolean sendMsg(Object openid, String at, String message) {
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

/**
 * 群发消息给所有用户
 * 
 * @param at      授权令牌
 * @param message 要发送的消息内容
 * @return 成功发送消息的用户数量
 */
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
	String url = "https://api.weixin.qq.com/cgi-bin/menu/create?access_token=ACCESS_TOKEN";
	url = url.replace("ACCESS_TOKEN", at);
	String param = "{\"button\":[{\"type\":\"view\",\"name\":\"主页\",\"key\":\"M1\",\"url\":\"https://www.baidu.com\"},"
			+ "{\"name\":\"班级\",\"sub_button\":[{\"type\":\"pic_photo_or_album\",\"key\":\"M21\",\"name\":\"照片\"},"
			+ "{\"type\":\"scancode_waitmsg\",\"key\":\"M22\",\"name\":\"签到\"},"
			+ "{\"type\":\"location_select\",\"key\":\"M23\",\"name\":\"我在这！！\"}]},"
			+ "{\"type\":\"view\",\"name\":\"商城\",\"key\":\"M3\",\"url\":\"URL\"}]}";
	String uri = "https://open.weixin.qq.com/connect/oauth2/authorize?appid=APPID&redirect_uri=REDIRECT_URL&response_type=code"
			+ "&scope=SCOPE&state=STATE#wechat_redirect";
	uri = uri.replace("APPID", "wxa9e0fce7bc48eabd")
			.replace("REDIRECT_URL", "http://dahle.natapp1.cc/Weixin/Weixin.jsp")
			.replace("SCOPE", "snsapi_userinfo");

	param = param.replace("URL", uri);

	String ret = MyRequest.post(url, param);
	JSONObject json = JSONObject.parseObject(ret);
	System.out.println(json);
	// JSONObject jsa = getUserInfo(code);
	// System.out.println(jsa);
}
// System.out.println(sendMsg("o9RAu6_v3T_b3-fq_EA09imUtTiQ",getToken(),"早点睡觉"));

// JSONArray list = getUsers(at);
// list.forEach((e) -> {
// String openid = (String) e;
// sendMBXX(at, openid);
// });
// }
//
// String message = "早点睡觉";
// int n = sendAllMsg(at, message);
// System.out.println("成功发送" + n + "条记录。");
//

/**
 * 发送模板消息
 * 
 * @param at     授权令牌
 * @param openid 用户的openid
 */
public static void sendMBXX(String at, String openid) {
	String url = "https://api.weixin.qq.com/cgi-bin/message/template/send?access_token=ACCESS_TOKEN";
	url = url.replace("ACCESS_TOKEN", at);
	String param = "{\"touser\":\"" + openid + "\","
			+ "\"template_id\":\"4lgEQgISjZ9AE_Un0POQRPfkZwhVYE400zrQuDFZZhI\","
			+ "\"url\":\"http://www.bing.com\","
			+ "\"data\":{\"time\":{\"value\":\"2014年9月22日\"}}}";
	String ret = MyRequest.post(url, param);
	System.out.println(ret);
}

/**
 * 获取用户信息
 * 
 * @param code 用户授权后的code
 * @return 如果获取成功，则返回包含用户信息的JSONObject，否则返回null
 */
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

/**
 * 下载文件
 * 
 * @param downloadUrl 文件的下载URL
 * @param path        文件保存的路径
 */
public static void downloadFile(String downloadUrl, String path) {
	InputStream inputStream = null;
	OutputStream outputStream = null;
	try {
		URL url = new URL(downloadUrl);
		HttpURLConnection connection = (HttpURLConnection) url.openConnection();
		inputStream = new BufferedInputStream(connection.getInputStream());
		File file = new File(path);
		if (file.exists()) {
			file.delete();
		}
		outputStream = new FileOutputStream(file);
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