<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Jsp09-4</title>
</head>
<body>
<%
	String max = request.getParameter("max");
	if(max != null){
		int max_value = Integer.parseInt(max);
		
		for (int i=0; i<max_value; i++){
			out.println(i + "<br>");
		}
	} else {
		out.println("<h1>You must set 'max' parameter!!</h1>");
	}
%>
</body>
</html>