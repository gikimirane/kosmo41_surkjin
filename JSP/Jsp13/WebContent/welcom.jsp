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
		Cookie[] cookie = request.getCookies();
	
		for (int i=0; i<cookie.length; i++){
			if(cookie[i].getName().equals("id")){
				String id = cookie[i].getValue();
				if(id.equals("abcde")) out.println(id + " 님 안녕하세요." + "<br>");
		
			}
		}
	%>
	
	<a href="logout.jsp">로그아웃</a> <br><p>
	<a href="cookieTest.jsp">쿠키 테스트</a>
</body>
</html>