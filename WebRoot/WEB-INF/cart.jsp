<%@page import="Java222.Cart"%>
<%@page import="Java222.DBTools"%>
<%@page import="Java222.Customer"%>
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE html>
	<html>
	  <head>
	    <title>购物车</title>
	    <meta name="viewport" content="width=device-width">
	    <meta charset="utf-8">
	    <script src="jquery-3.7.1.min.js"></script>
	  </head>
	<%
if(session.getAttribute("customer")==null){
	out.print("请先<a href='H5.html'>登录</a>");
	return;
}
Customer c = (Customer)session.getAttribute("customer");
ArrayList<Cart> list = DBTools.getCart(c.getId());
 %>

	<script>
	var status = 0;
	$(function(){
		$('#del').on('click',function(e){
			var cartid = -1;
			var button = document.getElementById("del");
			var classValue = button.className;
			cartid = classValue;
				$.ajax({
				  type: "post",
				  url: 'del',
				  dataType:'json',
				  header:{'content-type':"application/json"},
				  data: {'id':cartid},
				  success: res=>{
				  	console.log(res)

				  	if(res.errcode==0){
				  	alert("删除成功");
				  					 	
				  //$('#cnt').html(res.cnt);
				  }
				  	if(res.errcode==-1){
				  		window.location.href="H5.html";
				  	}
				  	window.location.reload();
				  },
				  fail:err=>{alert(err.errmsg);}
				});
				});		
				
	})
	
		
	  $(function(){
	  	$("#pay").on('click',()=>{
	  		var cartids =[];
	  		var items = $(".box").toArray();
	  		
	  		for(var index=0;index<items.length;index++){
	  		
	  		if($(".box input[type=checkbox]")[index].checked==true){
	  			var cartid = $(".box input[type=hidden]")[index].value;
	  			cartids.push(cartid);
	  		}
	  		}
	  		if(cartids.length==0)return;;
	  		$.ajax({
	  			url:'pay',
	  			type:'POST',
	  			dataType:'json',
	  			data:{'cartids':JSON.stringify(cartids)},
	  			success:(res)=>{
	  				if(res.errcode==0){
	  					window.location.href="pay.html";}
	  			alert(res.errMsg);
	  			
	  			},
	  			fail:(err)=>{}
	  		});
	  	});
	    $(".minus").on('click',function(e){
	      var index=$(".minus").index(this);
	      var value=$(".box .amount")[index].value;
	      var cartid=$(".box input[type=hidden]")[index].value;
	      if(value>1){
	      $.ajax({
	      url:'modityCart',
	      method:'POST',
	      dataType:'json',
	      data:{'cartid':cartid,value:-1},
	      success:(res)=>{
	      	if(res.errcode==0){
	      	$(".box .amount")[index].value=value-1;
		  		calc();
		  	//console.log(res);
	      		}
	      	}
	      });
		  
	      }
	    });
	    $(".plus").on('click',function(e){
	      var index=$(".plus").index(this);
	      var value=$(".box .amount")[index].value;
	      var cartid=$(".box input[type=hidden]")[index].value;
	      $.ajax({
	      url:'modityCart',
	      method:'POST',
	      dataType:'json',
	      data:{'cartid':cartid,value:1},
	      success:(res)=>{
	      		if(res.errcode==0){
		      		$(".box .amount")[index].value=parseInt(value)+1;
		      		calc();
	      		}
	      	}
	      });
	      
	      
	    });
	    $(".box input[type=checkbox]").on('click',function(){calc();});
	    $("#selectAll input[type=checkbox]").on('click',function(){
	      var items=$('.box .amount').toArray();
	      for(var index=0;index<items.length;index++){
	        $(".box input[type=checkbox]")[index].checked=this.checked;
	      }
	      calc();
	    });
	  });
	function calc(){
	  var items=$('.box .amount').toArray();
	  var sum=0;
	  var selectAll = true;
	  for(var index=0;index<items.length;index++){
	   if($('input[type=checkbox]')[index].checked){
	      var prices=$('.box .price').toArray();
	      sum+=(parseFloat(items[index].value)*parseFloat(prices[index].innerText));
	   }else{
	   		selectAll=false;
	   }
	  }
	  $('#sumary').html(sum.toFixed(2));
	  $("#selectAll input[type=checkbox]")[0].checked=selectAll;
	  
		$('#statused').on('click',function(){
		status=1;
	})
	}
	
	</script>

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
    }

    nav ul {
        list-style-type: none;
    }

    nav ul li {
        display: inline;
        margin-right: 20px;
    }

    nav ul li a {
        text-decoration: none;
        color: white;
        transition: color 0.3s ease;
    }

    nav ul li a:hover {
        color: #4CAF50;
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
	
	
	input[type='checkbox'] {
	    border-radius: 50%;
	    border: 2px solid #f50;
	    outline: none;
	    appearance: none;
	    width: 20px;
	    height: 20px;}
	
	input[type='checkbox']:checked {
	    background-color: #f50;}
	#bfixed {
	    width: 100%;
	    position: fixed;
	    height: 50px;
	    bottom: 0px;
	    left: 0px;
	    background-color: #fff;
	    display: flex;}
	#selectAll {
	    flex: 1;
	    display: flex;
	    align-items: center;
	    justify-content: center;}
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
        <h1>网站商城</h1>
        <nav><ul><li><a href="p.jsp">首页</a></li><li><a href="statused.jsp" id="statused">历史买单</a> </li></ul></nav>
    </header>
	<div id="main">
        <div class="userinfo">
            <div>收件人：<%=c.getRealname() %></div>
            <div>收件地址：<%=c.getAddress() %></div>
            <div>联系电话：<%=c.getMobile() %></div>
        </div>
       
        <% for (Cart cart : list) {
            if (cart.getStatus() == 0) { %>
            <div class="box">
                <input type="hidden" value="<%=cart.getId() %>"/>
                <div class="b1">
                    <input type="checkbox" />
                </div>
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
                <div class="b4">
                    <span class="minus">-</span>
                    <input type="number" class='amount' disabled="disabled" min="1" max="10" value="<%=cart.getAmount() %>"/>
                    <span class="plus">+</span> 
                </div>
                <button id="del" class=<%=cart.getId() %> >删除</button>
            </div>
        <% } } %>
        <div id='bfixed'>
            <div id="selectAll"><input type="checkbox" />全选</div>
            <div id="sum">合计：<span id="sumary">0.00</span> <span id="pay">结算</span></div>
        </div>
    </div>
	  </body>
	</html>
