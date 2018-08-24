<%@ page import="java.util.Arrays" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Jsp11-1-1</title>
</head>
<body>
	<%!
		String nm, id, mjr, pw, prc;
		String[] hobbys;
	%>
	
	<%
		String sType = request.getMethod();
		if(sType.equals("GET")){
			response.sendRedirect("error40A.html");
		}
			request.setCharacterEncoding("UTF-8");
			nm = request.getParameter("name");
			id = request.getParameter("id");
			pw = request.getParameter("pw");
	
			hobbys = request.getParameterValues("hobby");
			mjr = request.getParameter("major");
			prc = request.getParameter("protocol");
	%>
	
		이름 : <%= nm %>  <br/>
		아이디 : <%= id %>  <br/>
		비밀번호 : <%= pw %>  <br/>
		취미 : <%= Arrays.toString(hobbys) %>  <br/>
		전공 : <%= mjr %>  <br/>
		프로토콜 : <%= prc %>  <br/>	
	
</body>
</html>