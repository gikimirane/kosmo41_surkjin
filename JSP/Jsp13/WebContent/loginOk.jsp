<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Jsp 13-2</title>
</head>
<body>
	<%!
		String id, pw, name;
	%>
	<%
		id = request.getParameter("id");
		name = request.getParameter("name");
		pw = request.getParameter("pw");
		
		try{
			if(id.equals("abcde") && pw.equals("12345")){
				Cookie cookie = new Cookie("id", id);
				cookie.setMaxAge(60);
				response.addCookie(cookie);
				
				Cookie cookie2 = new Cookie("name", name);
				cookie2.setMaxAge(60);
				response.addCookie(cookie2);
				
				response.sendRedirect("welcom.jsp");
			} else {
				response.sendRedirect("login.html");
			}
		} catch (Exception e){}
	%>
</body>
</html>