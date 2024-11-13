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
 * Servlet 实现类 register
 * 该类处理用户注册请求，处理用户提交的信息，并将用户信息绑定到数据库。
 */
@WebServlet("/bindU")
public class register extends HttpServlet {
    /**
     * 处理 HTTP <code>POST</code> 方法。
     * 该方法读取从请求中提交的用户注册信息，包括用户名、密码、真实姓名、地址、性别、生日、手机号码和 openid。
     * 然后根据生日计算用户的年龄，并调用 DBTools.bindU 方法将用户信息绑定到数据库。
     * 最后，以 JSON 格式返回处理结果。
     *
     * @param request  HttpServletRequest 对象，包含请求信息
     * @param response HttpServletResponse 对象，包含响应信息
     * @throws ServletException 如果发生特定于 servlet 的错误
     * @throws IOException      如果发生 I/O 错误
     */
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setCharacterEncoding("utf-8");
        request.setCharacterEncoding("utf-8");
        response.setContentType("application/json");
        PrintWriter out = response.getWriter();
        JSONObject json = new JSONObject();

        // 从请求中获取参数
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        String realname = request.getParameter("realname");
        String address = request.getParameter("place");
        String gender = request.getParameter("gender");
        String birth = request.getParameter("birth");
        String mobile = request.getParameter("mobile");
        String openid = request.getParameter("openid");

        // 清除参数中的空格和特殊字符
        username = username.replace(" ", "").replace("'", "").replace("/", "").replace("\\", "");
        password = password.replace(" ", "").replace("'", "").replace("/", "").replace("\\", "");
        realname = realname.replace(" ", "").replace("'", "").replace("/", "").replace("\\", "");
        address = address.replace(" ", "").replace("'", "").replace("/", "").replace("\\", "");
        mobile = mobile.replace(" ", "").replace("'", "").replace("/", "").replace("\\", "");
        gender = gender.replace(" ", "").replace("'", "").replace("/", "").replace("\\", "");
        openid = openid.replace(" ", "").replace("'", "").replace("/", "").replace("\\", "");

        int age = 0;
        try {
            // 将生日字符串转换为日期对象并计算年龄
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            Date d = sdf.parse(birth);
            age = (int) ((new Date().getTime() - d.getTime()) / 1000 / 3600 / 24 / 365);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        // 调用 DBTools.bindU 方法将用户信息绑定到数据库
        Customer c = DBTools.bindU(username, password, realname, address, mobile, gender, age, openid, "");

        // 根据绑定结果构建 JSON 响应
        if (c != null) {
            json.put("errcode", -1);
            json.put("errmsg", "绑定失败!");
        } else {
            json.put("errcode", 0);
            json.put("user", c);
        }

        // 输出 JSON 响应
        out.print(json);
        out.flush();
        out.close();
    }
}