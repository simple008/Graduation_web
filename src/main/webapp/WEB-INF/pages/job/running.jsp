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
    <h3>任务管理界面</h3>
    <hr/>
    <h4>正在运行的任务</h4>
    <!-- 如果用户列表为空 -->
    <div class="running">
        <c:if test="${empty jobs}">
            <div class="alert alert-warning" role="alert">
                <span class="glyphicon glyphicon-info-sign" aria-hidden="true">无任务在运行</span>
            </div>
        </c:if>
        <!-- 如果用户列表非空 -->
        <c:if test="${!empty jobs}">
            <table class="table table-bordered table-striped" style="width:auto;">
                <tr>
                    <%--<th>jid</th>--%>
                    <th>name</th>
                    <th>state</th>
                    <th>start-time</th>
                    <th>end-time</th>
                    <th>duration</th>
                    <%--<th>last-modification</th>--%>
                    <th>opr</th>
                </tr>

                <c:forEach items="${jobs}" var="job">
                    <tr>
                        <%--<td>${job.get("jid")}</td>--%>
                        <td>${job.get("name")}</td>
                        <td>${job.get("state")}</td>
                        <td>${job.get("start-time")}</td>
                        <td>${job.get("end-time")}</td>
                        <td>${job.get("duration")}</td>
                        <%--<td>${job.get("last-modification")}</td>--%>
                        <td>
                            <a href="/job/opr/${job.get("jid")}" type="button" class="btn btn-sm btn-success">cancel</a>
                        </td>
                    </tr>
                </c:forEach>
            </table>
        </c:if>
    </div>


    <h4>已完成的任务</h4>
    <div class="end">
        <c:if test="${empty jobsComp}">
            <div class="alert alert-warning" role="alert">
                <span class="glyphicon glyphicon-info-sign" aria-hidden="true">无任务完成</span>
            </div>
        </c:if>
        <!-- 如果用户列表非空 -->
        <c:if test="${!empty jobsComp}">
            <table class="table table-bordered table-striped" style="width:auto;">
                <tr>
                    <%--<th>jid</th>--%>
                    <th>name</th>
                    <th>state</th>
                    <th>start-time</th>
                    <th>end-time</th>
                    <th>duration</th>
                    <%--<th>last-modification</th>--%>
                </tr>

                <c:forEach items="${jobsComp}" var="job">
                    <tr>
                        <%--<td>${job.get("jid")}</td>--%>
                        <td>${job.get("name")}</td>
                        <td>${job.get("state")}</td>
                        <td>${job.get("start-time")}</td>
                        <td>${job.get("end-time")}</td>
                        <td>${job.get("duration")}</td>
                        <%--<td>${job.get("last-modification")}</td>--%>
                    </tr>
                </c:forEach>
            </table>
        </c:if>
    </div>
</div>


<!-- jQuery文件。务必在bootstrap.min.js 之前引入 -->
<script src="//cdn.bootcss.com/jquery/1.11.3/jquery.min.js"></script>

<!-- 最新的 Bootstrap 核心 JavaScript 文件 -->
<script src="//cdn.bootcss.com/bootstrap/3.3.5/js/bootstrap.min.js"></script>

<script>

    setInterval(function(){
        $.get("/job/running",function(data){
            var result = JSON.parse(data);
            if(result.jobs.length>0){
                var runningDiv = $(".running")[0];
                var table = document.createElement("table");
                table.className="table table-bordered table-striped";
                table.style.width="auto";
                var tbody = document.createElement("tbody");
                table.appendChild(tbody);
                tbody.innerHTML="<tr><th>name</th><th>state</th><th>start-time</th><th>end-time</th>"
                        +"<th>duration</th><th>opr</th></tr>";
                for(var i in result.jobs){
                    var tr = document.createElement("tr");
                    var start = new Date(result.jobs[i]['start-time']);
                    var startTime = start.getFullYear()+"-"
                            +(start.getMonth()+1 < 10 ? '0'+(start.getMonth()+1) : start.getMonth()+1) + '-'
                            +start.getDay()+" "+start.getHours()+":"+start.getMinutes()+":"+start.getSeconds();
                    var last = new Date(result.jobs[i]['last-modification']);
                    var lastTime = last.getFullYear()+"-"
                            +(last.getMonth()+1 < 10 ? '0'+(last.getMonth()+1) : last.getMonth()+1) + '-'
                            +last.getDay()+" "+last.getHours()+":"+last.getMinutes()+":"+last.getSeconds();
                    var end = new Date();
                    var endTime = end.getFullYear()+"-"
                            +(end.getMonth()+1 < 10 ? '0'+(end.getMonth()+1) : end.getMonth()+1) + '-'
                            +end.getDay()+" "+end.getHours()+":"+end.getMinutes()+":"+end.getSeconds();
                    tr.innerHTML="<td>"+result.jobs[i].name+"</td>"
                            +"<td>"+result.jobs[i].state+"</td>"
                            +"<td>"+startTime+"</td>"
                            +"<td>"+endTime+"</td>"
                            +"<td>"+getDuration(result.jobs[i].duration)+"</td>"
                            +"<td><a href=\"/job/opr/"+result.jobs[i].jid+"\" type=\"button\" class=\"btn btn-sm btn-success\">cancel</a> </td>";
                    tbody.appendChild(tr);
                }
                runningDiv.innerHTML="";
                runningDiv.appendChild(table);


            }else{
                $(".running")[0].innerHTML="<div class=\"alert alert-warning\" role=\"alert\"> <span class=\"glyphicon glyphicon-info-sign\" aria-hidden=\"true\">无任务在运行</span> </div>"
            }
            if(result.jobsComp.length>0){
                var endDiv = $(".end")[0];
                var table = document.createElement("table");
                table.className="table table-bordered table-striped";
                table.style.width="auto";
                var tbody = document.createElement("tbody");
                table.appendChild(tbody);
                tbody.innerHTML="<tr><th>name</th><th>state</th><th>start-time</th><th>end-time</th>"
                        +"<th>duration</th></tr>";
                for(var i in result.jobsComp){
                    var tr = document.createElement("tr");
                    var start = new Date(result.jobsComp[i]['start-time']);
                    var startTime = start.getFullYear()+"-"
                            +(start.getMonth()+1 < 10 ? '0'+(start.getMonth()+1) : start.getMonth()+1) + '-'
                            +start.getDay()+" "+start.getHours()+":"+start.getMinutes()+":"+start.getSeconds();
                    var end = new Date(result.jobsComp[i]['end-time']);
                    var endTime = end.getFullYear()+"-"
                            +(end.getMonth()+1 < 10 ? '0'+(end.getMonth()+1) : end.getMonth()+1) + '-'
                            +end.getDay()+" "+end.getHours()+":"+end.getMinutes()+":"+end.getSeconds();
                    var last = new Date(result.jobsComp[i]['last-modification']);
                    var lastTime = last.getFullYear()+"-"
                            +(last.getMonth()+1 < 10 ? '0'+(last.getMonth()+1) : last.getMonth()+1) + '-'
                            +last.getDay()+" "+last.getHours()+":"+last.getMinutes()+":"+last.getSeconds();
                    tr.innerHTML="<td>"+result.jobsComp[i].name+"</td>"
                            +"<td>"+result.jobsComp[i].state+"</td>"
                            +"<td>"+startTime+"</td>"
                            +"<td>"+endTime+"</td>"
                            +"<td>"+getDuration(result.jobsComp[i].duration)+"</td>"
                            ;
                    tbody.appendChild(tr);
                }
                endDiv.innerHTML="";
                endDiv.appendChild(table);
            }else{
                $(".end")[0].innerHTML="<div class=\"alert alert-warning\" role=\"alert\"> <span class=\"glyphicon glyphicon-info-sign\" aria-hidden=\"true\">无任务完成</span> </div>"
            }
        })
    },1000);

    function getDuration(d) {
        var dur = '';
        var sec = Math.floor(d/1000);
        var min, hour, day;
        //一分钟内
        if(sec >=0 && sec <60) {
            dur = sec + 's';
        }
        //一小时内
        else if(sec >=60 && sec <3600) {
            min = Math.floor(sec/60);
            sec = sec%60;
            dur = min + 'm '+ sec + 's';
        }
        //一天内
        else if(sec >=3600 && sec <86400) {
            hour = Math.floor(sec/3600);
            min = Math.floor(sec%3600/60);
            sec = sec%3600%60;
            dur = hour + 'h ' + min + 'm '+ sec + 's';
        }
        //一天以上
        else {
            day = Math.floor(sec/86400);
            hour = Math.floor(sec%86400/3600);
            min = Math.floor(sec%86400%3600/60);
            sec = sec%86400%3600%60;
            dur = day + 'd ' + hour + 'h ' + min + 'm '+ sec + 's';
        }
        return dur;
    }
</script>
</body>
