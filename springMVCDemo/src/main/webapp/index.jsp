<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8" %>
<html>
<body>

<form action="person/insert" method="post">
    psw:<input type="text" name="psw" ><br/>
    money:<input type="text" name="money" ><br/>


    map.productName:<input type="text" name="dict['productName']" ><br/>
    map.productPrice:<input type="text" name="dict['productPrice']" ><br/>

    person1.name: <input type="text" name="ps[0].name"><br/>
    person1.age:<input type="text" name="ps[0].age"><br/>
    person1.address.area:<input type="text" name="ps[0].address.area"/><br/>
    person1.address.street:<input type="text" name="ps[0].address.street"/><br/>


    person2.name: <input type="text" name="ps[1].name"><br/>
    person2.age:<input type="text" name="ps[1].age"><br/>
    person2.address.area:<input type="text" name="ps[1].address.area"/><br/>
    person2.address.street:<input type="text" name="ps[1].address.street"/><br/>

    <input type="submit" value="save"/>
</form>


<a href="person/hello">查看用户</a>
<a href="person/name/1">查看用户1</a>

<form method="post" action="/person/responseBody">
    requset1:<input type="text" name="age"/><br/>
    requset2:<input type="text" name="name"/><br/>

    <input type="submit" value="save"/>
</form>

<h2>Hello World!</h2>


<form method="post" action="/person/modelAttribute">
    age:<input type="text" name="age"/><br/>
    <%--name:<input type="text" name="name"/><br/>--%>
    address.area:<input type="text" name="address.area"/><br/>
    address.street:<input type="text" name="address.street"/><br/>
    <input type="submit" value="保存用户"/>
</form>


<form action="/person/fileupload" method="post" enctype="multipart/form-data">
    名称: <input type="text" name="imageName"/> <br/>
    图片: <input type="file" name="uploadFile"/> <br/>
    <input type="submit" value="上传"/> <br/>
</form>



<form action="/job/object" method="post">
    <input type="text" name="student.id" value="student_id">
    <input type="text" name="student.name" value="student_name">
    <input type="text" name="course.id" value="course_id">
    <input type="text" name="course.name" value="course_name">
    <input type="text" name="date" value="1990-01-01">
    <input type="submit" value="提交">
</form>
</body>
</html>
