<%@ page import="java.util.Enumeration"  %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Jsp 13-3</title>
</head>
<body>
	<%
		request.setCharacterEncoding("UTF-8");
		
		Object obj = session.getAttribute("id");
		String sId = (String) obj;
		
		if(sId == null){
			response.sendRedirect("login.html");
		} else {
			out.println(sId + " 님 안녕하세요." + "<br/><br/>");
		}
	/*
		Enumeration sessions = session.getAttributeNames();
		while(sessions.hasMoreElements()){
			String sId = sessions.nextElement().toString();
			String sNm = session.getAttribute(sId).toString();
			//out.println("-->" + sId + " : " + sNm + "<br>");
			if(sNm.equals("abcde")) out.println(sNm + " 님 안녕하세요." + "<br>");
		}
	*/
	%>
	
	<a href="logout.jsp">로그아웃</a> <br><p>
	<a href="sessiontest.jsp">세션 테스트</a>
</body>
</html>