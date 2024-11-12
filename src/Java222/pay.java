package Java222;


import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebServlet("/pay")
public class pay extends HttpServlet{

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		resp.setCharacterEncoding("utf-8"); 
		resp.setContentType("application/json");
		PrintWriter out= resp.getWriter();
		HttpSession session = req.getSession(); 
		if(session.getAttribute("customer")==null){
			out.print("{\"errcode\":-1,\"errMsg\":\"未登录，请先登录\"}");out.flush();out.close();return;}
		Customer c = (Customer)session.getAttribute("customer");String[] cartids=null;
		try{cartids=req.getParameter("cartids").replace("[", "").replace("]", "").split(",");}catch(Exception e){}
		if(cartids==null){
			out.print("{\"errcode\":-2,\"errMsg\":\"商品不存在\"}");out.flush();out.close();return;}
		int n = DBTools.pay(cartids,c.getId());
		if(n>0){
			out.print("{\"errcode\":0,\"errMsg\":\"买单成功\",\"cnt\":"+n+"}");out.flush();out.close();return;
		}else{
			out.print("{\"errcode\":-3,\"errMsg\":\"买单失败\"}");out.flush();out.close();return;
		}
	}
	
}
