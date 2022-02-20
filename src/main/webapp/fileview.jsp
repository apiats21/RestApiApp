<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
</head>
<body>
<a href="index.jsp"> Main page</a><br/>
<h3>Upload file</h3>
<form action="<%=request.getContextPath()%>/file" enctype="multipart/form-data" method="post">
    User Id: <input type="text" name="userIdUpload">
    File: <input type="file" name="fileUpload">
    <input type="hidden" name="crud" value="fileUpload">
    <input type="submit" value="Upload">
</form>
<h3>Edit file name</h3>
<form action="<%=request.getContextPath()%>/file" method="post">
    File id: <input type="text" name="idEdit"/>
    File new name: <input type="text" name="fileNameEdit"/>
    <input type="hidden" name="crud" value="edit">
    <input type="submit" value="Edit">
</form>
<h3>All files</h3>
<form action="<%=request.getContextPath()%>/file" method="post">
    <input type="hidden" name="crud" value="all">
    <input type="submit" value="All files">
</form>
<h3>Download file</h3>
<form action="<%=request.getContextPath()%>/file" method="post">
    File id: <input type="text" name="idDownload">
    <input type="hidden" name="crud" value="download">
    <input type="submit" value="Download">
</form>

<h3>Delete file</h3>
<form action="<%=request.getContextPath()%>/file" method="post">
    File id: <input type="text" name="idDelete">
    <input type="hidden" name="crud" value="delete">
    <input type="submit" value="Delete">

</form>
</body>
</html>

