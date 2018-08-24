<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Jsp 11-3</title>
</head>
<body>
<%!
	String id, pw;
%>
<%
	id = request.getParameter("id");
	pw = request.getParameter("pw");
%>

<h1>forward_param.jsp 입니다.</h1>
아이디 : <%= id %> <br>
비밀번호 : <%= pw %>
</body>
</html>