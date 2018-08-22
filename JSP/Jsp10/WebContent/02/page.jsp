<%@ page import = "java.util.Arrays" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Jsp10-2-1</title>
</head>
<body>
	<%
		int[] iArr = {10, 20, 30};
		out.println(Arrays.toString(iArr));
	%>
</body>
</html>