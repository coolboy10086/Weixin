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
 * Servlet implementation class getCart
 */
@WebServlet("/getCart")
public class getCart extends HttpServlet {
	 
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("utf-8");
	response.setCharacterEncoding("utf-8");
	response.setContentType("application/json");
	PrintWriter out=response.getWriter();
	JSONObject json=new JSONObject();
	
	int customerid  = Integer.parseInt(request.getParameter("customerid"));
	ArrayList<Cart> list = DBTools.getCart(customerid);
	
	int n = 0;
	for(Cart t :list){
		if(t.getStatus()==0)n=n+t.getAmount();
	}
	request.getSession().setAttribute("cart", n);
	json.put("cart", n);
	json.put("cartlist", list);
	
	
	
	
	
	out.print(json);
	out.flush();
	}		
	
	
	
	



	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
