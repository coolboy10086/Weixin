package cn.gd.gdgc;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import java.util.Date;
import java.util.HashMap;

/**
 * BaiduTools类提供了与百度AI平台交互的方法
 */
public class BaiduTools {
    // 存储历史对话记录，键为用户标识，值为对话记录的JSONArray
    private static final HashMap<String, JSONArray> history = new HashMap<String, JSONArray>();
    // 百度AI平台的访问令牌
    private static String BaiduAccessToken = null;
    // 访问令牌的过期时间
    private static long BaiduAccessToken_expire_time = 0;
    // 访问令牌的创建时间
    private static long BaiduAccessToken_create_time = 0;
    // 百度AI平台的API密钥
    private static final String API_KEY = " APIKEY";
    // 百度AI平台的密钥
    private static final String SECRET_KEY = "SECRET_KEY";

    /**
     * 获取百度AI平台的访问令牌
     * 如果当前令牌为空或已过期，则会请求新的令牌
     * 
     * @return 当前有效的百度AI访问令牌
     */
    private static String getBaiduToken() {
        // 检查令牌是否为空或已过期，如果过期则重新获取
        if (BaiduAccessToken == null
                || new Date().getTime() - BaiduAccessToken_create_time > BaiduAccessToken_expire_time * 1000) {
            // 构建请求URL，替换占位符为实际的API密钥和密钥
            String url = "https://aip.baidubce.com/oauth/2.0/token?grant_type=GRANT_TYPE&client_id=API_KEY&client_secret=SECRET_KEY";
            url = url.replace("API_KEY", API_KEY).replace("SECRET_KEY", SECRET_KEY).replace("GRANT_TYPE",
                    "client_credentials");
            // 发送请求并解析返回值以获取新的访问令牌
            String ret = MyRequest.post(url, "");
            System.out.println(ret);
            JSONObject json = JSONObject.parseObject(ret);

            // 如果成功获取到令牌，则更新令牌和过期时间
            if (json.getString("access_token") != null) {
                BaiduAccessToken = json.getString("access_token");
                BaiduAccessToken_create_time = new Date().getTime();
                BaiduAccessToken_expire_time = json.getIntValue("expires_in");
            }
        }
        return BaiduAccessToken;

    }

    /**
     * 与百度AI进行对话的函数
     * 
     * @param openid  用户标识，用于区分不同用户的对话历史
     * @param content 用户输入的内容
     * @return AI的回复内容，如果请求失败则返回null
     */
    public static String AiChat(String openid, String content) {
        // 构建请求URL，替换占位符为实际的访问令牌
        String url = "https://aip.baidubce.com/rpc/2.0/ai_custom/v1/wenxinworkshop/chat/yi_34b_chat?access_token=ACCESS_TOKEN";
        url = url.replace("ACCESS_TOKEN", getBaiduToken());
        // 获取用户的历史对话记录，如果没有则创建新的JSONArray
        JSONArray hist = history.get(openid);
        if (hist == null) {
            hist = new JSONArray();
        }
        // 构建用户输入的JSON对象，并添加到历史对话记录中
        JSONObject json = new JSONObject();
        json.put("role", "user");
        json.put("content", content);
        hist.add(json);
        // 构建请求参数，包括历史对话记录和用户标识
        JSONObject param = new JSONObject();
        param.put("messages", hist);
        param.put("user_id", openid);
        // 发送请求并解析返回值以获取AI的回复
        String ret = MyRequest.post(url, param.toJSONString());
        System.out.println(ret);
        JSONObject result = JSONObject.parseObject(ret);
        // 如果成功获取到回复内容，则构建AI回复的JSON对象，更新历史对话记录，并返回回复内容
        if (result.getString("result") != null) {
            JSONObject reply = new JSONObject();
            reply.put("role", "assistant");
            reply.put("content", result.getString("result"));
            hist.add(reply);
            history.put(openid, hist);
            return result.getString("result");
        } else {
            // 如果请求失败，则打印请求参数以便调试
            System.out.println(param);
        }
        return null;
    }

    public static void main(String[] args) {
        // 测试与百度AI的对话功能
        System.out.println(AiChat("aa", "开始"));

    }

}
