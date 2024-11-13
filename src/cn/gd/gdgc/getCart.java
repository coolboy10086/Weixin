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
import Java222.DBTools;

/**
 * Servlet 实现类 getCart
 * 该类用于处理与购物车相关的请求，特别是获取用户的购物车信息。
 */
@WebServlet("/getCart")
public class getCart extends HttpServlet {
	/**
	 * 处理 HTTP GET 请求。
	 * 该方法根据请求中提供的客户ID获取用户的购物车信息，计算购物车中未支付的商品总数，并返回购物车信息。
	 *
	 * @param request  HttpServletRequest 对象，用于获取客户ID参数。
	 * @param response HttpServletResponse 对象，用于设置响应的内容类型和编码，并返回购物车信息。
	 * @throws ServletException 如果发生特定于Servlet的异常。
	 * @throws IOException      如果发生I/O异常。
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.setCharacterEncoding("utf-8");
		response.setCharacterEncoding("utf-8");
		response.setContentType("application/json");
		PrintWriter out = response.getWriter();
		JSONObject json = new JSONObject();

		// 从请求参数中获取客户ID
		int customerid = Integer.parseInt(request.getParameter("customerid"));
		// 使用客户ID从数据库中获取购物车信息
		ArrayList<Cart> list = DBTools.getCart(customerid);

		// 初始化购物车中商品的总数
		int n = 0;
		// 遍历购物车列表，计算未支付的商品总数
		for (Cart t : list) {
			if (t.getStatus() == 0)
				n = n + t.getAmount();
		}
		// 将购物车中商品的总数存储在会话中
		request.getSession().setAttribute("cart", n);
		// 将购物车中商品的总数和购物车列表添加到JSON对象中
		json.put("cart", n);
		json.put("cartlist", list);

		// 输出JSON对象
		out.print(json);
		out.flush();
	}

	/**
	 * 处理 HTTP POST 请求。
	 * 该方法简单地调用doGet方法来处理请求。
	 *
	 * @param request  HttpServletRequest 对象。
	 * @param response HttpServletResponse 对象。
	 * @throws ServletException 如果发生特定于Servlet的异常。
	 * @throws IOException      如果发生I/O异常。
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// 调用doGet方法处理POST请求
		doGet(request, response);
	}
}