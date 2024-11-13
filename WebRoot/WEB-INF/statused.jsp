<%@page import="Java222.DBTools"%>
<%@page import="Java222.Cart"%>
<%@page import="Java222.Customer"%>
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>


<!DOCTYPE >
<html>
  <head>
    <title>历史记录</title>
    <meta name="viewport" content="width=device-width">
	<meta charset="utf-8">
    <script src="jquery-3.7.1.min.js"></script>
	
  </head>
  	<%if(session.getAttribute("customer")==null){
		out.print("请先<a href='H5.html'>登录</a>");
		return;
}
	Customer c = (Customer)session.getAttribute("customer");
	ArrayList<Cart> list = DBTools.getCart(c.getId());%>
<style>
	 *{
	  margin:0;
	  padding:0;
	  }
body {
	background: linear-gradient(to right, #2E86C1, #4CAF50);
	overflow: hidden;
	background-size: 400%;
}
a {
	  text-decoration: none;
	  color: inherit;
	}
 header {
    	width:390px;
        background: linear-gradient(to right, #2E86C1, #4CAF50);
        color: black;
        padding: 20px;
        text-align: center;
        position: relative;
        border-bottom:solid white 1px;
    }
#main {
    width: 430px;
    margin: auto;
    height: 100%;
}

.box {
    height: 100px;
    width: 95%;
    display: flex;
    background-color: #fff;
    margin: 10px auto;
    border-radius: 10px;
	box-shadow: 7px 7px 5px rgba(0, 0, 0, 0.5);

}

.b1, .b2, .b4 {
    display: flex;
    align-items: center;
    justify-content: center;
}

.b1 {
    flex: 1;
}

.b2 {
    flex: 3;
}

.b3 {
    flex: 4;
    flex-direction: column;
    padding: 5px;
    display: flex;
}

.b4 {
    flex: 2;
    display: flex;
    align-items: center;
    justify-content: center;
    margin-right:5px;
	
}

.b2 img {

    width: 90px;
    height: 90px;
    border-radius: 10px;
}

.b3 .pn {
    overflow: hidden;
    text-overflow: ellipsis;
    display: -webkit-box;
    -webkit-box-orient: vertical;
    -webkit-line-clamp: 2;
    font-size: 18px;
    line-height: 28px;
    word-break: break-all;
}

.b4 span {
    cursor: pointer;

}
.b4 input{
    color:orange;
    font-size:20px;
}

.userinfo {
    width: 90%;
    margin: 10px 10px ;
    background-color: #f0f0f0;
    box-shadow: 5px 5px 3px rgba(0, 0, 0, 0.2);
    
    font-size: 14px;
    padding: 10px;
    line-height: 25px;
    border-radius: 5px;
}



#bfixed {
    width: 100%;
    position: fixed;
    height: 50px;
    bottom: 0px;
    left: 0px;
    background-color: #fff;
    display: flex;
}
#selectAll {
    flex: 1;
    display: flex;
    align-items: center;
    justify-content: center;
}

#sum {
    flex: 3;
    display: flex;
    align-items: center;
    justify-content: right;
    padding-right: 20px;
    
}
#sumary{
	color:#ff5733;
}

#pay {
    border-radius: 20px;
    padding: 5px 20px;
    background-color: #f50;
    color: #fff;
    cursor: pointer;
    margin-left:10px;
}
.plus,.minus{
	width:20px;
	height:20px;
	line-height:20px;
	text-align:center;
    background-color: #f0f0f0;
}
.price{
	color:#ff5733;
}
.amount {
    width: 30px;
    text-align: center;
    border: 0px;
}
	  </style>
  <body>
	<header>  
  		<h1>历史购买记录</h1>
	</header>
  <div id="main">

  <% for(Cart cart:list){ 
		  if(cart.getStatus()== 1){%>
		  <div class="box">
		  <input type="hidden" value="<%=cart.getId() %>"/>


			  <div class="b2">
			  	<img src="upload/<%=cart.getPicture() %>" />
			  </div>
			  <div class="b3">
			  	<div style="flex:2;" class="pn">
			  	<%=cart.getName() %>
			  	</div>
			  	<div style="flex:1;" class="price">
			  	<%=cart.getPrice() %>
			  	</div>
			  </div>
			  <div class="b4" >
			  	<span style="margin-right:-8px;font-size:10px;">X</span>
			  	<input type="number" class='amount' disabled="disabled" min="1" value="<%=cart.getAmount() %>"/>
			  </div>
		  </div>
		  <%} }%>
		  </div>

</body>
</html>