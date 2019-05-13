<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8" %>
<html>
<body>

<form action="person/insert" method="post">
    name: <input type="text" name="name"><br/>
<%--    age:<input type="text" name="age"><br/>--%>
    <input type="submit" value="save"/>
</form>


<a href="person/name">查看用户</a>
<h2>Hello World!</h2>
</body>
</html>
