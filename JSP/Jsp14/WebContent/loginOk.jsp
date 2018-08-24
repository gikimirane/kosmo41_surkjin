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
		request.setCharacterEncoding("UTF-8");
		
		id = request.getParameter("id");
		name = request.getParameter("name");
		pw = request.getParameter("pw");
		
		try{
			
			if(id.equals("abcde") && pw.equals("12345")){
				
				session.setAttribute("id", id);
				session.setAttribute("pw", pw);
				session.setAttribute("name", name);
				session.setMaxInactiveInterval(60*60); //60분
				
				response.sendRedirect("welcom.jsp");
			} else {
				response.sendRedirect("login.html");
			}
		} catch (Exception e){
			out.println(e);
		}
	%>
</body>
</html>