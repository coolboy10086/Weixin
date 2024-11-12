package Java222;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

@WebServlet("/addProduct")
public class addProduct extends HttpServlet {
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("utf-8");
		response.setCharacterEncoding("utf-8");
		response.setContentType("text/html;charset=utf-8");
		PrintWriter out = response.getWriter();		
		boolean ok =true;
		HashMap<String,String> map = new HashMap<String,String>();
		boolean isMultipart = ServletFileUpload.isMultipartContent(request);
		if(isMultipart){
			// Create a factory for disk-based file items
			DiskFileItemFactory factory = new DiskFileItemFactory();//ʹ�ù���ģʽ
			// Configure a repository (to ensure a secure temp location is used)
			ServletContext servletContext = this.getServletConfig().getServletContext();
			File repository = (File) servletContext.getAttribute("javax.servlet.context.tempdir"); // Or "javax.servlet.context.tempdir" for javax
			factory.setRepository(repository);
			// Create a new file upload handler
			ServletFileUpload upload = new ServletFileUpload(factory);
			// Parse the request
			try {
				List<FileItem> items = upload.parseRequest(request);
				// Process the uploaded items
				for (FileItem item : items) {
				    if (item.isFormField()) {
				       map.put(item.getFieldName(),new String(item.getString().getBytes("iso8859-1"),"utf-8"));
				    } else {
				    	if(item.getSize()>0){
				    	String picture = new Date().getTime()+".jpg";
				    	File uploadedFile = new File(request.getRealPath("/upload")+"/"+picture);
				        item.write(uploadedFile);
				        map.put("picture", picture);}}}
			} catch (FileUploadException e) {
				ok=false;
				e.printStackTrace();
			} catch (Exception e) {
				ok=false;
				e.printStackTrace();
			}
		}
		
		if(!ok){
			out.print("�����ˣ�");
			out.flush();
			out.close();
			return;
		}
		//�����ύ������
		String name = map.get("name");
		
		String description =  map.get("description");
		double price =Double.parseDouble( map.get("price"));
		int amount =-1; Integer.parseInt( map.get("amount")) ;
		//�ж�---------------------------------------------------------------
		if(name==null&&name.length()>50)name=name.substring(0,50);
		if(description==null&&description.length()>50)description=description.substring(0,50);
		try{
			price=Double.parseDouble(map.get("price"));
			amount = Integer.parseInt(map.get("amount"));
		}catch(Exception e){
		}
		if(price<0||amount<0){
			out.print("参数错误");
			out.flush();
			out.close();
			return;}
		
		
		//----------------------------------------------------
		Product p = new Product(name,description,price,amount,5, map.get("picture"),0);
 
		if(p.save()){
			out.println("<script>alert('添加商品成功');window.location.href='addProduct.jsp';</script>");
		}else{
			out.println("<script>alert('添加商品失败');window.history.go(-1);</script>");
		}
		out.flush();
		out.close();
	}
	

}
