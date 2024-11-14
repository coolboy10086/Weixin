package cn.gd.gdgc;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.alibaba.fastjson.JSONObject;

import Java222.Cart;
import Java222.Customer;
import Java222.DBTools;

/**
 * Servlet 实现类 login
 */
@WebServlet("/login")
public class login extends HttpServlet {
	/**
	 * 处理 HTTP GET 请求。
	 *
	 * @param request  servlet 请求
	 * @param response servlet 响应
	 * @throws ServletException 如果发生特定于 servlet 的错误
	 * @throws IOException      如果发生 I/O 错误
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub

	}

	/**
	 * 处理 HTTP POST 请求，用于用户登录。
	 * 该方法负责处理登录请求，通过微信验证用户身份，并返回用户和购物车信息。
	 *
	 * @param request  servlet 请求
	 * @param response servlet 响应
	 * @throws ServletException 如果发生特定于 servlet 的错误
	 * @throws IOException      如果发生 I/O 错误
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// 设置响应内容类型和字符编码
		response.setContentType("application/json");
		response.setCharacterEncoding("utf-8");
		request.setCharacterEncoding("utf-8");

		// 从请求中获取 code 参数，用于交换 session_key 和 openid
		String code = request.getParameter("code");
		String url = "https://api.weixin.qq.com/sns/jscode2session?appid=APPID&secret=SECRET&js_code=JSCODE&grant_type=authorization_code";
		// 替换 URL 中的 APPID、SECRET 和 JSCODE 为实际值
		url = url.replace("APPID", "APPID");
		url = url.replace("SECRET", "SECRET");
		url = url.replace("JSCODE", code);

		// 向微信服务器发送请求并获取响应数据
		String ret = MyRequest.get(url);
		JSONObject json = JSONObject.parseObject(ret);

		// 从微信服务器响应中获取 openid
		String openid = json.getString("openid");
		if (openid != null) {
			// 验证用户身份并获取用户信息
			Customer c = DBTools.login(openid);
			if (c != null) {
				// 将用户信息存储在 session 中
				request.getSession().setAttribute("customer", c);
				// 获取用户的购物车信息
				ArrayList<Cart> list = DBTools.getCart(c.getId());
				int n = 0;
				// 计算购物车中未结算的商品总数
				for (Cart t : list) {
					if (t.getStatus() == 0) {
						n = n + t.getAmount();
					}
					// 将购物车商品总数存储在 session 中
					request.getSession().setAttribute("cart", n);
				}
				// 将用户和购物车信息添加到响应中
				json.put("user", c);
				json.put("cart", n);
			}
		}
		// 写入响应数据
		PrintWriter out = response.getWriter();
		out.print(json);
		out.flush();
		out.close();
	}
}
