<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<% if(session.getAttribute("ValidMem") == null){ %>
		<jsp:forward page="login.jsp" />
<%	}  
		String id = (String)session.getAttribute("id");
		String name = (String)session.getAttribute("name");
		
%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Jsp 21-1</title>
</head>
<body>
	<h1><%= name %>님 안녕하세요.</h1>
	<form action="logout.do" method="post">
		<input type="submit" value="로그아웃">&nbsp;&nbsp;&nbsp;
		<input type="button" value="정보수정"
			onclick="javascript:window.location='modify.jsp'">
	</form>
</body>
</html>