<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
</head>
<body>
<a href="index.jsp"> Main page</a><br/>
<h3>Create user</h3>
<form action="<%=request.getContextPath()%>/user" method="post">
    First name: <input type="text" name="firstName"/>
    Last name: <input type="text" name="lastName"/>
    <input type="hidden" name="crud" value="create">
    <input type="submit" value="submit" />
</form>
<h3>Edit user</h3>
<form action="<%=request.getContextPath()%>/user" method="post">
    Id: <input type="text" name="idEdit"/>
    First name: <input type="text" name="firstNameEdit"/>
    Last name: <input type="text" name="lastNameEdit"/>
    <input type="hidden" name="crud" value="edit">
    <input type="submit" value="submit">
</form>
<h3>Delete user</h3>
<form action="<%=request.getContextPath()%>/user" method="post">
    Id: <input type="text" name="idDelete">
    <input type="hidden" name="crud" value="delete">
    <input type="submit" value="submit">
</form>
<h3>All users</h3>
<form action="<%=request.getContextPath()%>/user" method="post">
    <input type="hidden" name="crud" value="all">
    <input type="submit" value="All users">
</form>
<h3>User by id</h3>
<form action="<%=request.getContextPath()%>/user" method="post">
    Id: <input type="text" name="iduser">
    <input type="hidden" name="crud" value="byid">
    <input type="submit" value="submit">
</form>
</body>
</html>
