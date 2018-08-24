<%@ page import="java.util.Enumeration"  %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Jsp 13-4</title>
</head>
<body>
	<%
		Enumeration enumeration = session.getAttributeNames();
	/*
		if(enumeration.hasMoreElements()){
			session.removeAttribute("id");
			session.removeAttribute("name");
			session.removeAttribute("pw");
		}
		
		while(enumeration.hasMoreElements()){
			String sId = enumeration.nextElement().toString();
			if(sId.endsWith("id")) session.removeAttribute(sId);
			if(sId.endsWith("pw")) session.removeAttribute(sId);
			if(sId.endsWith("name")) session.removeAttribute(sId);
		}
		*/
		session.invalidate();
		
		response.sendRedirect("login.html");
	%>
</body>
</html>