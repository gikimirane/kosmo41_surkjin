<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Jsp 25-1</title>
</head>
<body>
	<a href="insert.do">insert</a>
	<hr />
	<a href="http://localhost:8081<%=request.getContextPath()%>/update.do">update</a>
	<hr />
	<a href="http://localhost:8081/Jsp25_1/select.do">select</a>
	<hr />
	<a href="<%=request.getContextPath()%>/delete.do">delete</a>
	
</body>
</html>