package Java222;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/addCart")
public class addCart extends HttpServlet {
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setCharacterEncoding("utf-8");
		response.setContentType("application/json");
		PrintWriter out = response.getWriter();
		if(request.getSession().getAttribute("customer")==null){
			out.print("{\"errcode\":-1,\"errmsg\":\"未登录请登录\"}");
			out.flush();
			out.close();
			return;}
		else{
			Customer c = (Customer)request.getSession().getAttribute("customer");
			int customerid = c.getId();
			int productid = -1;
			try{
				productid = Integer.parseInt(request.getParameter("productid"));}catch(Exception e){}
			if(productid==-1){
				out.print("{\"errcode\":-2,\"errmsg\":\"未找到商品\"}");
				out.flush();
				out.close();
				return;}
			int r = DBTools.addCart(customerid, productid, 1);
			if(r>-1){
				request.getSession().setAttribute("cart", r);
				out.print("{\"errcode\":0,\"errmsg\":\"成功添加购物车\",\"cnt\":"+r+"}");
				}else{out.print("{\"errcode\":-3,\"errmsg\":\"添加购物车失败\"}");}
			out.flush();
			out.close();}}}
