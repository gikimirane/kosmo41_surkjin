<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>JSP 11-2</title>
</head>
<body>
<%!
	int age;
%>
<%
	String str = request.getParameter("age");

	try{
		age = Integer.parseInt(str);
	
		if(age >= 19){
			response.sendRedirect("pass.jsp?age=" + age);
		}else{
			response.sendRedirect("ng.jsp?age=" + age);
		}
	}catch(Exception e){}
%>

<%= age %>
</body>
</html>