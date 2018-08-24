<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Jsp 11-2</title>
</head>
<body>

<%
	String str = request.getParameter("age");
%>

성인입니다. 주류구매가 가능합니다.

<a href="requestEx.html">처음으로 이동</a>
</body>
</html>