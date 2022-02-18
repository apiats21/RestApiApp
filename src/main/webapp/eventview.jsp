<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
</head>
<body>
<a href="index.jsp"> Main page</a><br/>
<h3>List events</h3>
<form action="<%=request.getContextPath()%>/events" method="post">
    <label>Id: <input type="text" id="idEvents"></label>
    <input type="submit" value="submit">
</form>
</body>
</html>
