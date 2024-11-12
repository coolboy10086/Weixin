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
 * Servlet implementation class login
 */
@WebServlet("/login")
public class login extends HttpServlet {
	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub

	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.setContentType("application/json");
		response.setCharacterEncoding("utf-8");
		request.setCharacterEncoding("utf-8");
		String code = request.getParameter("code");
		String url = "https://api.weixin.qq.com/sns/jscode2session?appid=APPID&secret=SECRET&js_code=JSCODE&grant_type=authorization_code";
		url = url.replace("APPID", "wx72a6df47c59befc7");
		url = url.replace("SECRET", "99e9b5158a977428602f8cc577e28fe4");
		url = url.replace("JSCODE", code);
		String ret = MyRequest.get(url);
		JSONObject json = JSONObject.parseObject(ret);
		String openid = json.getString("openid");
		if (openid != null) {
			Customer c = DBTools.login(openid);
			if (c != null) {
				request.getSession().setAttribute("customer", c);
				ArrayList<Cart> list = DBTools.getCart(c.getId());
				int n = 0;
				for (Cart t : list) {
					if (t.getStatus() == 0) {
						n = n + t.getAmount();
					}
					request.getSession().setAttribute("cart", n);

				}
				json.put("user", c);
				json.put("cart", n);
			}
		}
		PrintWriter out = response.getWriter();
		out.print(json);
		out.flush();
		out.close();

	}

}
