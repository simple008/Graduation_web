<%@ page contentType="text/html;charset=UTF-8" language="java" %>
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
        <h3>添加传感器</h3>
        <hr/>
        <form:form action="/admin/sns/addP" method="post" commandName="sn" role="form">
            <div class="form-group">
                <label for="sensor" class="col-sm-2 control-label">传感器:</label>
                <input type="text" class="form-control addwidth" id="sensor" name="sensor" placeholder="Enter Sensor">
            </div>
            <div class="form-group">
                <label for="protocol" class="col-sm-2 control-label">解析方法:</label>
                <input type="text" class="form-control addwidth" id="protocol" name="protocol" placeholder="Enter Protocol">
            </div>
            <div class="form-group">
                <label for="communication" class="col-sm-2 control-label">传输方式:</label>
                <!--<input type="text" class="form-control addwidth" id="communication" name="communication" placeholder="Enter communication">-->
                <select id="communication" name="communication">
                    <option value="tcp">tcp</option>
                    <option value="udp">udp</option>
                    <option value="http">http</option>
                    <option value="web socket">web socket</option>
                </select>
            </div>

            <div class="form-group">
                <label for="ip" class="col-sm-2 control-label">IP:</label>
                <input type="text" class="form-control addwidth" id="ip" name="ip" placeholder="Enter ip">
            </div>

            <div class="form-group">
                <label for="port" class="col-sm-2 control-label">端口号:</label>
                <input type="text" class="form-control addwidth" id="port" name="port" placeholder="Enter port">
            </div>

            <div class="form-group">
                <label for="topic" class="col-sm-2 control-label">发布主题：</label>
                <input type="text" class="form-control addwidth" id="topic" name="topicUp" placeholder="Enter topicUp">
            </div>

            <div class="form-group">
                <label for="topic_down" class="col-sm-2 control-label">下发主题：</label>
                <input type="text" class="form-control addwidth" id="topic_down" name="topicDown" placeholder="Enter topicDown">
            </div>


            <div class="form-group col-sm-offset-2">
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
