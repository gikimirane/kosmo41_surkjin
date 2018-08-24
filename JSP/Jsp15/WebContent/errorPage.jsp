<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page isErrorPage="true" %>
<%	response.setStatus(200); %>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Jsp 15-2</title>
</head>
<body>
	
	에러발생 <br />
	<%= exception.getMessage() %>
</body>
</html>