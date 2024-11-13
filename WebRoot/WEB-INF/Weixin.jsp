<%@page import="Java222.Cart"%>
<%@page import="Java222.DBTools"%>
<%@page import="Java222.Customer"%>
<%@page import="cn.gd.gdgc.Test"%>
<%@page import="com.alibaba.fastjson.JSONObject"%>
<%@page import="cn.gd.gdgc.MyRequest"%>
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
	String code = request.getParameter("code");
	if (code == null) {
		out.print("请在微信公众号内打开本页面");
		return;
	}
	JSONObject json = Test.getUserInfo(code);
	session.setAttribute("userinfo", json);
	if (json == null) {
		out.print("獲取用戶信息失敗！！！！！！！！！！！");
		return;
	}
	String[] gender = { "未知", "男", "女" };
%>
<html>


<head>
<meta name="viewport" content="width=device-width">
</head>
<body>
	<div
		style="width:420px ;height:350px;margin:auto;display:flex;justify-content: center;">

		<img src="<%=json.getString("headimgurl")%>"
			style="border-radius:50%;width:100px;height:100px" />
	</div>
	<div>
		呢稱：<%=json.getString("nickname")%></div>
	<div>
		標示：<%=json.getString("openid")%></div>
	<div>
		性別：<%=gender[json.getIntValue("sex")]%></div>
	<%
		//用这个openid取的据库里检索一下这个人存不存在，加果存在取出信息，
		//并计算这个用户已经挑选了几件商品到购物车，把这些信息

		Customer c = DBTools.login(json.getString("openid"));
		if (c != null) {
			session.setAttribute("customer", c);
			ArrayList<Cart> list = DBTools.getCart(c.getId());
			int n = 0;
			for (Cart t : list) {
				if (t.getStatus() == 0) {
					n = n + t.getAmount();
				}
				session.setAttribute("cart", n);
			}
		}
	%>


	<div>
		<a href="p.jsp">去商城挑好物</a>
	</div>
</body>
</html>

