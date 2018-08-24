<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Jsp 13-5</title>
</head>
<body>
	<%
		Cookie[] cookie = request.getCookies();
	
		if(cookie != null){
			for(int i=0; i<cookie.length; i++){
				out.println(cookie[i].getName() + " : " +
							cookie[i].getValue() + "<br>");
			}
		}
	%>
</body>
</html>