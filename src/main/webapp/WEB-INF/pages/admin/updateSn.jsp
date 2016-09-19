<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="zh-CN">
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <!-- 上述3个meta标签*必须*放在最前面，任何其他内容都*必须*跟随其后！ -->
    <title>异构传感器管理界面</title>
    <style>
        .addwidth {
            width: 200px !important;
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
        <h1>更新传感器信息</h1>
        <hr/>
        <form:form action="/admin/sns/updateP" method="post" commandName="snP" role="form">
            <div class="form-group">
                <label for="sensor">传感器:</label>
                <input type="text" class="form-control addwidth" id="sensor" name="sensor" placeholder="Enter sensor" value="${sn.sensor}">
            </div>
            <div class="form-group">
                <label for="protocol">解析方法:</label>
                <input type="text" class="form-control addwidth" id="protocol" name="protocol" placeholder="Enter protocol" value="${sn.protocol}">
            </div>
            <div class="form-group">
                <label for="communication">传输方式:</label>
                <input type="text" class="form-control addwidth" id="communication" name="communication" placeholder="Enter communication" value="${sn.communication}">
            </div>

            <div class="form-group">
                <label for="ip">传输方式:</label>
                <input type="text" class="form-control addwidth" id="ip" name="ip" placeholder="Enter ip" value="${sn.ip}">
            </div>

            <div class="form-group">
                <label for="port">传输方式:</label>
                <input type="text" class="form-control addwidth" id="port" name="port" placeholder="Enter port" value="${sn.port}">
            </div>
            <div class="form-group">
                <button type="submit" class="btn btn-sm btn-success">提交</button>
            </div>
        </form:form>
    </div>
    <!-- jQuery文件。务必在bootstrap.min.js 之前引入 -->
    <script src="//cdn.bootcss.com/jquery/1.11.3/jquery.min.js"></script>

    <!-- 最新的 Bootstrap 核心 JavaScript 文件 -->
    <script src="//cdn.bootcss.com/bootstrap/3.3.5/js/bootstrap.min.js"></script>
</body>
</html>
