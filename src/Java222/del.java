package Java222;


import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebServlet("/del")
public class del extends HttpServlet{

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		resp.setCharacterEncoding("utf-8");//�����֧������
		resp.setContentType("application/json");
		PrintWriter out= resp.getWriter();
		HttpSession session = req.getSession();//�õ��Ự
		if(session.getAttribute("customer")==null){
			out.print("{\"errcode\":-1,\"errMsg\":\"未登录请先登录\"}");
			out.flush();
			out.close();
			return;
		}
		Customer c = (Customer)session.getAttribute("customer");
		int id= -1;
		try{
			id = Integer.parseInt(req.getParameter("id"));}catch(Exception e){}
		if(id==-1){
			out.print("{\"errcode\":-2,\"errmsg\":\"商品不存在\"}");
			out.flush();
			out.close();
			return;
		}
		int r = DBTools.del(id);
		if(r>-1){
			req.getSession().setAttribute("cart", r);
			out.print("{\"errcode\":0,\"errmsg\":\"删除失败\",\"cnt\":"+r+"}");
			
		}else{
			out.print("{\"errcode\":-3,\"errmsg\":\"删除成功\"}");
		}
		out.flush();
		out.close();
		
	
	}
	
}
