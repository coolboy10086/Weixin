package cn.gd.gdgc;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Enumeration;
import java.util.UUID;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;

import gd.cn.message.ReceiveMessage;

/**
 * Weixin类用于处理微信服务器的请求，验证消息的来源，并处理接收到的消息
 */
@WebServlet("/Weixin")
public class Weixin extends HttpServlet {
	public static String token = "lichujia";

	public Weixin() {
		super();
	}

	/**
	 * 处理GET请求，用于验证微信服务器的合法性
	 *
	 * @param request  包含请求数据的HttpServletRequest对象
	 * @param response 用于发送响应数据的HttpServletResponse对象
	 * @throws ServletException 如果Servlet遇到异常
	 * @throws IOException      如果发生I/O异常
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// 打印请求参数
		Enumeration<String> names = request.getParameterNames();
		while (names.hasMoreElements()) {
			String key = names.nextElement();
			System.out.println(key + "\t" + request.getParameter(key));
		}

		// 验证微信服务器的合法性
		String signature = request.getParameter("signature");
		String echostr = request.getParameter("echostr");
		String timestamp = request.getParameter("timestamp");
		String nonce = request.getParameter("nonce");

		boolean result = true;
		// 1）将token、timestamp、nonce三个参数进行字典序排序
		if (signature == null || echostr == null || timestamp == null || nonce == null) {
			System.out.println("参数不对");
			return;
		}
		ArrayList<String> list = new ArrayList<String>();
		list.add(timestamp);
		list.add(nonce);
		list.add(token);
		Collections.sort(list);
		// 2）将三个参数字符串拼接成一个字符串进行sha1加密
		String str = list.get(0) + list.get(1) + list.get(2);
		String s2 = SHA1.encode(str);
		// 3）开发者获得加密后的字符串可与signature对比，标识该请求来源于微信
		result = s2.equals(signature);

		PrintWriter out = response.getWriter();
		if (result) {
			out.print(request.getParameter("echostr"));
		} else {
			System.out.println("参数不对");
			out.print("非法来源，已被记录，IP=" + request.getRemoteAddr());
		}
		out.flush();
		out.close();
	}

	/**
	 * 处理POST请求，用于接收微信服务器发送的消息
	 *
	 * @param request  包含请求数据的HttpServletRequest对象
	 * @param response 用于发送响应数据的HttpServletResponse对象
	 * @throws ServletException 如果Servlet遇到异常
	 * @throws IOException      如果发生I/O异常
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		response.setContentType("text/html");
		System.out.println("post");

		// 读取并解析微信服务器发送的XML消息
		InputStream is = request.getInputStream();
		InputStreamReader isr = new InputStreamReader(is, "utf-8");
		BufferedReader br = new BufferedReader(isr);
		String r = null;
		StringBuilder sb = new StringBuilder();
		while ((r = br.readLine()) != null) {
			sb.append(r);
		}
		String xml = sb.toString();
		System.out.println(xml);

		PrintWriter out = response.getWriter();
		out.print("");
		out.flush();
		out.close();

		try {
			// 使用JAXB解析XML消息
			JAXBContext content = JAXBContext.newInstance(ReceiveMessage.class);
			Unmarshaller um = content.createUnmarshaller();
			ReceiveMessage msg = (ReceiveMessage) um.unmarshal(new StringReader(xml));

			String openid = msg.getFromUserName();
			String at = Test.getToken();

			// 根据消息类型处理消息
			if (msg.getMsgType().equals("text")) {
				System.out.println("收到" + openid + "发来的文本消息，内容是" + msg.getContent());
				String h = BaiduTools.AiChat(openid, msg.getContent());
				Test.sendMsg(openid, at, h);
			}
			if (msg.getMsgType().equals("image")) {
				System.out.println("收到" + openid + "发来的图片消息，内容是" + msg.getPicUrl());
				UUID uid = UUID.randomUUID();
				Test.downloadFile(msg.getPicUrl(), "D:\\杂七杂八\\" + uid + ".jpg");
				Test.sendMsg(openid, at, "<a href='" + msg.getPicUrl() + "'>点击发送的图片</a>");
			}
			if (msg.getMsgType().equals("location")) {
				System.out.println("收到" + openid + "发来的文本消息，内容是" + msg.getLabel());
				out.print("收到" + openid + "发来的文本消息，内容是" + msg.getLabel());
			}
			if (msg.getMsgType().equals("video")) {
				// 处理视频消息
			}
			if (msg.getMsgType().equals("event") && msg.getEvent().equals("scancode_waitmsg")) {
				String reslut = msg.getScanCodeInfo().getScanResult();
				System.out.println(reslut);
			}
		} catch (JAXBException e) {
			e.printStackTrace();
		}
	}
}