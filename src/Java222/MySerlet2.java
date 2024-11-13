package Java222;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.swing.plaf.synth.Region;
@WebServlet("/made")
public class MySerlet2 extends HttpServlet {
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {}
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setCharacterEncoding("utf-8");
		request.setCharacterEncoding("utf-8");
		response.setContentType("text/html;charset=utf-8");
		PrintWriter out = response.getWriter();
		String um = request.getParameter("username");
		String pwd = request.getParameter("password");
		if(um==null||pwd==null)return;
		um=um.replace("'","").replace(" ", "").replace("//", "").replace("\\", "");
		pwd = pwd.replace("'","").replace(" ", "").replace("//", "").replace("\\", "");
		Customer c = DBTools.login(um, pwd);
		if(c==null){
			out.print("未登录，请先登录");
		}else{
			request.getSession().setAttribute("customer", c);

			ArrayList<Cart> list = DBTools.getCart(c.getId());
			int n = 0;
			for(Cart t:list){
				if(t.getStatus()==0)n=n+t.getAmount();
			}
			request.getSession().setAttribute("cart", n);
			response.sendRedirect("p.jsp");
		}
		out.flush();
		out.close();
	}
}
