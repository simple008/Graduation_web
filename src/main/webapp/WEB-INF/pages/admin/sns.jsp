<%--
  Created by IntelliJ IDEA.
  User: lukong
  Date: 16/9/17
  Time: 下午9:41
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="zh-CN">
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <!-- 上述3个meta标签*必须*放在最前面，任何其他内容都*必须*跟随其后！ -->
    <title>异构传感器管理界面</title>

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
        <h1>异构传感器管理</h1>
        <hr/>
        <h3>所有传感器<a href="/admin/sns/add" type="button" class="btn btn-primary btn-sm">添加</a></h3>
        <!-- 如果用户列表为空 -->
        <c:if test="${empty snList}">
            <div class="alert alert-warning" role="alert">
                <span class="glyphicon glyphicon-info-sign" aria-hidden="true"></span>Sn表为空，请<a href="/admin/sns/add" type="button" class="btn btn-primary btn-sm">添加</a>
            </div>
        </c:if>
        <!-- 如果用户列表非空 -->
        <c:if test="${!empty snList}">
            <table class="table table-bordered table-striped" style="width:auto;">
                <tr>
                    <th>名称</th>
                    <th>解析方法</th>
                </tr>

                <c:forEach items="${snList}" var="sn">
                    <tr>
                        <td>${sn.sensor}</td>
                        <td>${sn.protocol}</td>
                        <td>
                            <a href="/admin/sns/show/${sn.sensor}" type="button" class="btn btn-sm btn-success">详情</a>
                            <a href="/admin/sns/update/${sn.sensor}" type="button" class="btn btn-sm btn-warning">修改</a>
                            <a href="/admin/sns/delete/${sn.sensor}" type="button" class="btn btn-sm btn-danger">删除</a>
                            <a href="/jar/add/${sn.sensor}" type="button" class="btn btn-sm btn-success">添加解析方法JAR</a>
                            <a href="/jar/submit/${sn.sensor}" type="button" class="btn btn-sm btn-success">提交</a>

                        </td>
                    </tr>
                </c:forEach>
            </table>
        </c:if>
    </div>
    <!-- jQuery文件。务必在bootstrap.min.js 之前引入 -->
    <script src="//cdn.bootcss.com/jquery/1.11.3/jquery.min.js"></script>

    <!-- 最新的 Bootstrap 核心 JavaScript 文件 -->
    <script src="//cdn.bootcss.com/bootstrap/3.3.5/js/bootstrap.min.js"></script>
</body>
