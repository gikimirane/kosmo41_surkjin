<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Jsp10-1</title>
</head>
<body>
<%
	int i=0;
	while(true){
		i++;
		out.println("2 * " + i + " = " + (2*i) + "<br />");
%>
	=========<br />
<%
		if(i==9) break;
	}
%>
</body>
</html>