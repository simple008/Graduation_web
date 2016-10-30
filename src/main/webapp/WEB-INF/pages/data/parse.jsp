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

    </div>

<script>
    setInterval(function(){
        $.get("/data/parse",function (data) {
            var data_=JSON.parse(data);
            if(data.datas.length>0){
                var div = $(".running")[0];
                var table=document.createElement("table");
                table.className="table table-bordered table-striped";
                table.style.width="auto";
                var tbody = document.createElement("tbody");
                table.appendChild(tbody);
                tbody.innerHTML="<tr><th>topic</th><th>data</th></tr>"

            }
        })
    },5000);
</script>
</body>
</html>
