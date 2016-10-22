<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<!DOCTYPE html>
<html lang="zh-CN">
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <!-- 上述3个meta标签*必须*放在最前面，任何其他内容都*必须*跟随其后！ -->
    <title>异构传感器管理界面</title>

    <style>
        .form-control-add {
            width: 200px;
            height: 20px;
            padding: 6px 12px;
            font-size: 14px;
            line-height: 1.42857143;
            color: #555;
            background-color: #fff;
            background-image: none;
            border: 1px solid #ccc;
            border-radius: 4px;
            -webkit-box-shadow: inset 0 1px 1px rgba(0,0,0,.075);
            box-shadow: inset 0 1px 1px rgba(0,0,0,.075);
            -webkit-transition: border-color ease-in-out .15s,-webkit-box-shadow ease-in-out .15s;
            -o-transition: border-color ease-in-out .15s,box-shadow ease-in-out .15s;
            transition: border-color ease-in-out .15s,box-shadow ease-in-out .15s;
        }
        div.container{
            position: absolute;
            left: 50%;
            top: 50%;
            width: 500px;
            height:250px;
            transform: translate(-50%,-150px);
            background-color: rgba(255,255,255,0.8);
            border-radius: 10px;
        }
        .container form{
            width: 250px;
            position: absolute;
            left:50%;
            transform: translateX(-50%);
        }
        body{
            background-image: url("/images/nanhai.jpg");
            background-size: cover;
        }
    </style>


    <!-- 新 Bootstrap 核心 CSS 文件 -->
    <link rel="stylesheet" href="//cdn.bootcss.com/bootstrap/3.3.5/css/bootstrap.min.css">

    <!-- HTML5 shim and Respond.js for IE8 support of HTML5 elements and media queries -->
    <!-- WARNING: Respond.js doesn't work if you view the page via file:// -->
    <!--[if lt IE 9]>
    <script src="//cdn.bootcss.com/html5shiv/3.7.2/html5shiv.min.js"></script>
    <script src="//cdn.bootcss.com/respond.js/1.4.2/respond.min.js"></script>
    <![endif]-->
</head>
<body>
    <div class="container">
        <h3 style="text-align: center;">欢迎来到异构传感器接入界面</h3>
        <hr/>
        <form:form action="/admin/users" method="post" commandName="us" role="form">
            <div class="form-group">
                <label for="name">用户名</label>
                <input type="text" class="form-control-add" id="name" name="name">
            </div>
            <div class="form-group">
                <label for="password">密码  &nbsp&nbsp</label>
                <input type="password" class="form-control-add" id="password" name="password">
            </div>
            <div class="form-group">
                <button type="submit" class="btn btn-sm btn-success">登入</button>
                <a href="/admin/reg" class="btn btn-sm btn-success">注册</a>
            </div>
        </form:form>


    </div>
    <!-- jQuery文件。务必在bootstrap.min.js 之前引入 -->
    <script src="//cdn.bootcss.com/jquery/1.11.3/jquery.min.js"></script>

    <!-- 最新的 Bootstrap 核心 JavaScript 文件 -->
    <script src="//cdn.bootcss.com/bootstrap/3.3.5/js/bootstrap.min.js"></script>
</body>
</html>
