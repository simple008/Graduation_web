<%@ taglib prefix="sf" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
</head>
<body>
    <form:form method="post" action="/flink/form" commandName="user" enctype="multipart/form-data">
        <tr>
            <th><label for="username">name</label></th>
            <td><sf:input path="name" id="username"/></td>
        </tr>
        <tr>
            <th><label for="ps" >password </label></th>
            <td><sf:input path="password" id="ps"/></td>
        </tr>

        <tr>
            <th><label for="file">file:</label></th>
            <td><input name="jar" type="file" id="file"></td>
        </tr>

        <button type="submit">提交</button>

    </form:form>
</body>
</html>
