<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Jsp 26-1</title>
</head>
<body>
	dispatcherJsp.jsp
	<hr>
	
	id : <%= request.getAttribute("id") %><br>
	pw : <%= request.getAttribute("pw") %>
</body>
</html>