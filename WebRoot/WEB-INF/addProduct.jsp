<%@page import="Java222.Cart"%>
<%@page import="Java222.Customer"%>
<%@page import="Java222.DBTools"%>
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>

<!DOCTYPE html>
<html>

<head>
    <title>addProduct.html</title>
    <meta name="viewport" content="width=device-width">
    <meta charset="utf-8">
</head>
   	<%
if(session.getAttribute("customer")==null){
	out.print("请先<a href='H5.html'>登录</a>");
	return;
}
Customer c = (Customer)session.getAttribute("customer");
ArrayList<Cart> list = DBTools.getCart(c.getId());
 %>
    <style>
        * {
            font-family: Arial, sans-serif;
            margin: 0;
            padding: 0;
        }
        a{
        text-decoration: none;
        }
        body {
            height: 100vh;
            width: 100%;
            overflow: hidden;
          
            background-size: 400%;
            font-family: "montserrat";
          background: linear-gradient(to right, #2E86C1, #4CAF50);
        }

        @keyframes bganimation {
            0% {
                background-position: 0% 50%;
            }

            50% {
                background-position: 100% 50%;
            }

            100% {
                background-position: 0% 50%;
            }
        }
        .form {
            width: 85%;
            max-width: 600px;
            /* max-height: 700px; */
            background-color: rgba(255, 255, 255, .05);
            position: absolute;
            top: 50%;
            left: 50%;
            transform: translate(-50%, -50%);
            padding: 40px;
            border-radius: 10px;
            box-shadow: 0 0 5px #000;
            text-align: center;
            font-family: "微软雅黑";
            color: #fff;
            display: flex;
            justify-content: center;
            align-items: center;
        }

        .txtb {

            border: 1px solid #aaa;
            margin: 8px 0;
            padding: 12px 18px;
            border-radius: 10px;
        }

        label {
            display: block;
            text-align: left;
            color: #fff;
            font-size: 14px;
        }
        .txtb input {
            width: 100%;
            background: none;
            border: none;
            outline: none;
            margin-top: 6px;
            font-size: 18px;
            color: #fff;
        }
        .form-button_one+.form-button_two {line-height: 10px;}
        #back{
        	position:absolute;
       		width: 50px;
            background: none;
            border: solid white 1px;
            border-radius:5px;
            outline: none;
            margin-top: 6px;
            font-size: 18px;
            color: #fff;
            top: 10px;
    		left: 30px
            }
    </style>
<body>
    <div class="form">
        <form action="addProduct" enctype="multipart/form-data" method="post">
            <a href="p.jsp"><span id="back">返回</span></a>
            <h1>AddProduct</h1>
            <div class="txtb"><label class="form-label">请输入商品名称</label>
                <input type="text" name="name" required placeholder="请输入商品名称">
            </div>
            <div class="txtb"><label class="form-label">请输入商品描述</label><input type="text" name="description" required
                    placeholder="请输入商品描述"></div>
            <div class="txtb"><label class="form-label">请输入商品价格</label><input type="text" name="price" value="0.00"
                    required placeholder="请输入商品价格"></div>
            <div class="txtb"><label class="form-label">请输入商品库存</label>
                <input type="number" name="amount" min=1 value="1" required placeholder="请输入商品库存">
            </div>
            <div class="txtb"><label class="form-label">商品名称</label><input type="file" name="picture" accept="image/*"></div>
            <div class="txtb" style="text-align:center;">
                <input type="submit" value="添加" class="form-button_one" />
            </div>
            <div class="txtb" style="text-align:center;">
                <input type="reset" value="情空" class="form-button_two" />
            </div>
        </form>
</div>
</body>

</html>