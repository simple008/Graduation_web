<%--
  Created by IntelliJ IDEA.
  User: lukong
  Date: 2016/10/22
  Time: 下午5:24
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<link href="http://cdn.bootcss.com/font-awesome/4.6.3/css/font-awesome.css" rel="stylesheet">
<style>
    .menu{
        height:100%;
        width:25%;
        background-color: #000;
        display: inline;
    }
    iframe{
        display: inline-block;
        border-width:0;
        height:100%;
        flex: 1;
    }
    body{
        margin:0;
        display: flex;
    }
    button{
        font-size:20px;
    }
    ul li a{
        color: #aaa;
        text-decoration: none;
        font-size:15px;
        line-height: 20px;
    }
    li{
        padding:10px 20px;
    }
    ul {
        padding: 0;
    }
    li:hover{
        background-color: #777;
    }
    i{
        height:20px !important;
        width:20px !important;
    }

</style>
<script>
    console.log(123);
    var change = function change(url){
        document.getElementsByTagName("iframe")[0].src=url;
    }
</script>
<head>
    <title>导航栏</title>
</head>
<body>
    <div class="menu">
       <%--<button onclick="change('/job/jobinfo')">lalala</button>--%>
        <div style="height: 16px;padding: 17px 25px;">
            <img src="/images/flink-logo.png" style="width:25px;height:25px"/>
            <span style="color:#eee"> &nbsp; Dashboard</span>
        </div>

        <div class="navbar navbar-sidebar">
            <ul>
                <li>
                    <a onclick="change('/admin/sns')" href="javascript:void(0);">
                        <i class="fa fa-snapchat" aria-hidden="true"></i> &nbsp; 传感器建模
                    </a>
                </li>
                <li>
                    <a onclick="change('/job/jobinfo')" href="javascript:void(0);">

                        <i class="fa fa-tasks fa-fw" aria-hidden="true"></i> &nbsp; 任务管理
                    </a>
                </li>

                <li >
                    <a onclick="change('http://www.baidu.com')" href="javascript:void(0)">

                        <i class="fa fa-wpforms" aria-hidden="true"></i> &nbsp;数据处理
                    </a>

                </li>

            </ul>
        </div>
    </div>
    <iframe src="/admin/sns"></iframe>
</body>
</html>
