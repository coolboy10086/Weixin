<%@page import="Java222.Customer"%>
<%@page import="Java222.Product"%>
<%@page import="Java222.DBTools"%>
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%=request.getParameter("code") %>

<!DOCTYPE HTML >
<html>
	<head>
	
		<meta charset="utf-8" />
		<meta name="viewport" content="width=device-width">
		<title></title>
	
	</head>
	<script src="jquery-3.7.1.min.js">
	</script>
	<script >
		$(function(){
		$('.add').on('click',function(e){	
//监听按钮的点击事件，通过e.target.id来区别不同的商品
		var productid = e.target.id;
		//console.log(e.target);
		$.ajax({
		  type: "post",
		  url: 'addCart',
		  dataType:'json',
		  header:{'content-type':"application/json"},
		  data: {'productid':productid},
		  success: res=>{
		  	console.log(res)
//		  	alert(res.errcode)
		 	alert(res.errmsg);
		  	if(res.errcode==0){
				// alert(1);
		  		$('#cnt').html(res.cnt);
		  }
		  	if(res.errcode==-1){
		  		window.location.href="H5.html";
		  	}
		  },
		  fail:err=>{alert(err.errmsg);}
		});
		});		
		});
		</script>
	<style>
	body {
        font-family: Arial, sans-serif;
        background-color: #f0f0f0;
        font-size:10px;
    }
	a {
	  text-decoration: none;
	  color: inherit;
	}
    header {
        background: linear-gradient(to right, #2E86C1, #4CAF50);
        color: black;
        padding: 20px;
        text-align: center;
        position: relative;
    }
    #addProduct {
        background-color: #4CAF50;
        position: absolute;
        left: 10px;
        top: 10px;
        color: white;
        padding: 10px 10px;
        text-align: center;
        text-decoration: none;
        display: inline-block;
        font-size: 10px;
        cursor: pointer;
        border: none;
        border-radius: 10px;
    }

    #addProduct:hover {
        background-color: #FF7B52;
    }

    #addProduct:active {
        background-color: #FF5733;
        box-shadow: 0 5px #666;
        transform: translateY(4px);
    }

    /* 商品样式 */
    #products {
        display: flex;
        flex-wrap: wrap;
        justify-content: space-between;
        align-items: center;
    }

    .product {
        flex: 1 1 20%;
        margin: 5px;
        border: 1px solid #ccc;
        padding: 10px;
        box-shadow: 0 4px 8px 0 rgba(0, 0, 0, 0.2);
    }
    .product img {
        width: 100%;
        height: auto;
    }

    .product p {
        margin: 0;
        font-size: 10px;
    }
    .product button {
        background-color: #4CAF50;
        /* Green */
        border: none;
        color: white;
        text-align: center;
        text-decoration: none;
        display: inline-block;
        font-size: 10px;
        cursor: pointer;
    }
    button:hover {
        background-color: #45a049;
    }
    #cart-icon {
        position: fixed;
        top: 20px;
        right: 20px;
        z-index: 1000;
        cursor: pointer;
    }
    #cart-icon i {
        font-size: 24px;
    }
    #cart-icon .count {
        position: absolute;
        right: -6px;
        background-color: red;
        color: white;
        font-size: 8px;
        border-radius: 50%;
        width: 10px;
        height: 10px;
        line-height: 10px;
        text-align: center;
    }
	</style>
	<body>
	    <header>
        <h1>网站商城</h1>
        <button id="addProduct" >
            	<a href="addProduct.jsp">添加商品</a>
        </button>
    </header>
     <main>
     <section id="products">
	<%
	ArrayList<Product> list = DBTools.getProducts();
	for(Product p:list){
	%>
            <div class="product">
				<img src="upload/<%=p.getPicture() %>" />
                <p>商品名称：<%=p.getName() %></p>
                <p>商品介绍： <%=p.getDescription() %>
                </p><div class="rb"><div class="price" >价格： <%=p.getPrice() %></div>
                    <div class="amount"><span class="amount_label">库存：</span><span class="amount_value"><%=p.getAmount() %></span></div>
                    <button id=<%=p.getId() %> class="add">加入购物车</button></div>
 			</div>
			<%} %>
	</section>
	</main>
    <div id="cart-icon">
    <a href="cart.jsp">
        <i class="fas fa-shopping-cart"></i>
        <img src="./img/shopingcar.jpg" alt="" style="width: 20px;height: auto;">
        <span id="cnt" class="count">
            <%=session.getAttribute("cart")!=null?session.getAttribute("cart"):0 %>
        </span>
        </a>
    </div>
	</body>
</html>


