<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%--
  Created by IntelliJ IDEA.
  User: lukong
  Date: 2016/10/26
  Time: 上午11:45
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>数据解析结果</title>
</head>
<body>

    <div class="container">
        <h3>数据解析结果</h3>
        <hr/>
        <form:form action="/data/topic" method="post" commandName="topic" role="form">
            <div class="form-group">
                <label for="topic" class="col-sm-2 control-label">主题:</label>
                <input type="text" class="form-control addwidth" id="topic" name="topic" placeholder="Enter topic">
                <button type="submit" class="btn btn-sm btn-success">提交</button>
            </div>

        </form:form>
        <div class="show"></div>

    </div>
    <script src="//cdn.bootcss.com/jquery/1.11.3/jquery.min.js"></script>
    <script>
        var datas = [];
        setInterval(function(){
            $.get("/data/parse",function (data) {
                var data_= data != 'NULL' && JSON.parse(data);
                if(data_ && data_.datas.length>0){
                    for(var i in data_.datas){
                        datas.length>10 && datas.shift();
                        datas.push(data_.datas[i]);
                    }
//                    console.log(datas);
                    var content = datas.reduce(function(res,data){
                        return res.concat("<br>"+data);
                    },[]);
                    $(".show")[0].innerHTML = content;
                }
            })
        },5000);
    </script>
    <style>
        .show{
            position: relative;
            width:100%;
            overflow: auto;
            height:80%;
            border:solid 1px #cccccc;
        }
    </style>
</body>
</html>
