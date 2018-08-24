<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Jsp 14-2</title>
</head>
<body>
	<%
		session.setAttribute("mySessionName", "mySessionData");
		session.setAttribute("myNum", 12345);
	%>
	
	<a href="sessionget.jsp">session get</a>
</body>
</html>