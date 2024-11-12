package cn.gd.gdgc;

import Java222.Customer;
import Java222.DBTools;
import com.alibaba.fastjson.JSONObject;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Servlet implementation class register
 */
@WebServlet("/bindU")
public class register extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setCharacterEncoding("utf-8");
        request.setCharacterEncoding("utf-8");
        response.setContentType("application/json");
        PrintWriter out = response.getWriter();
        JSONObject json = new JSONObject();
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        String realname = request.getParameter("realname");
        String address = request.getParameter("place");
        String gender = request.getParameter("gender");
        String birth = request.getParameter("birth");
        String mobile = request.getParameter("mobile");
        String openid = request.getParameter("openid");
        username.replace(" ", "").replace("'", "").replace("/", "").replace("\\", "");
        password.replace(" ", "").replace("'", "").replace("/", "").replace("\\", "");
        realname.replace(" ", "").replace("'", "").replace("/", "").replace("\\", "");
        address.replace(" ", "").replace("'", "").replace("/", "").replace("\\", "");
        mobile.replace(" ", "").replace("'", "").replace("/", "").replace("\\", "");
        gender.replace(" ", "").replace("'", "").replace("/", "").replace("\\", "");
        openid.replace(" ", "").replace("'", "").replace("/", "").replace("\\", "");
        int age = 0;
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            Date d = sdf.parse(birth);
            age = (int) ((new Date().getTime() - d.getTime()) / 1000 / 3600 / 24 / 365);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        Customer c = DBTools.bindU(username, password, realname, address, mobile, gender, age, openid, "");
        if (c != null) {
            json.put("errcode", -1);
            json.put("errcode", "绑定失败!");
        } else {
            json.put("errcode", 0);
            json.put("user", c);
        }
        out.print(json);
        out.flush();
        out.close();
    }
}
