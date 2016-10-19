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
    <h1>任务管理界面</h1>
    <hr/>
    <h2>正在运行的任务</h2>
    <!-- 如果用户列表为空 -->
    <c:if test="${empty jobs}">
        <div class="alert alert-warning" role="alert">
            <span class="glyphicon glyphicon-info-sign" aria-hidden="true">无任务在运行</span>
        </div>
    </c:if>
    <!-- 如果用户列表非空 -->
    <c:if test="${!empty jobs}">
        <table class="table table-bordered table-striped" style="width:auto;">
            <tr>
                <th>jid</th>
                <th>name</th>
                <th>state</th>
                <th>start-time</th>
                <th>end-time</th>
                <th>duration</th>
                <th>last-modification</th>
                <th>opr</th>
            </tr>

            <c:forEach items="${jobs}" var="job">
                <tr>
                    <td>${job.get("jid")}</td>
                    <td>${job.get("name")}</td>
                    <td>${job.get("state")}</td>
                    <td>${job.get("start-time")}</td>
                    <td>${job.get("end-time")}</td>
                    <td>${job.get("duration")}</td>
                    <td>${job.get("last-modification")}</td>
                    <td>
                        <a href="/job/opr/${job.get("jid")}" type="button" class="btn btn-sm btn-success">cancel</a>
                    </td>
                </tr>
            </c:forEach>
        </table>
    </c:if>


    <h2>已完成的任务</h2>
    <c:if test="${empty jobsComp}">
        <div class="alert alert-warning" role="alert">
            <span class="glyphicon glyphicon-info-sign" aria-hidden="true">无任务完成</span>
        </div>
    </c:if>
    <!-- 如果用户列表非空 -->
    <c:if test="${!empty jobsComp}">
        <table class="table table-bordered table-striped" style="width:auto;">
            <tr>
                <th>jid</th>
                <th>name</th>
                <th>state</th>
                <th>start-time</th>
                <th>end-time</th>
                <th>duration</th>
                <th>last-modification</th>
            </tr>

            <c:forEach items="${jobsComp}" var="job">
                <tr>
                    <td>${job.get("jid")}</td>
                    <td>${job.get("name")}</td>
                    <td>${job.get("state")}</td>
                    <td>${job.get("start-time")}</td>
                    <td>${job.get("end-time")}</td>
                    <td>${job.get("duration")}</td>
                    <td>${job.get("last-modification")}</td>
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
