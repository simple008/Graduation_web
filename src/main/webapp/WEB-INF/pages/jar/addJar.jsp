<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%--
  Created by IntelliJ IDEA.
  User: lukong
  Date: 16/9/19
  Time: 下午5:04
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Submit</title>
    <h3>上传解析逻辑JAR包</h3>
    <hr/>
</head>
<body>
    <div>
        <form:form method="post" action="/jar/upload" commandName="jar" enctype="multipart/form-data">
            <tr>
                <th><label for="file_jar">解析方法JAR包:</label></th>
                <td><input name="jar" type="file" id="file_jar"></td>
            </tr>
            <button type="submit" class="btn btn-sm btn-success">提交</button>
        </form:form>
    </div>
</body>
</html>
