<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%!
    	String name, id, pw;
    %>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>login Result</title>
</head>
<body>
	<%
		name = (String)session.getAttribute("name");
		id = (String)session.getAttribute("id");
		pw = (String)session.getAttribute("pw");
	%>
	
	<%= name %> 님 인녕하세요. <br><p>
	
	<a href="modify.jsp">회원정보 수정</a>
</body>
</html>