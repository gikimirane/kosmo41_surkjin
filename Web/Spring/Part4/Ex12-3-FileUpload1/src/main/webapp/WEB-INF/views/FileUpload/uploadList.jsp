<%@page import="java.io.File" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core %>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>파일목록보기</title>
</head>
<body>
	<h2>파일목록보기</h2>
	
	<c:forEach items="${fileMap}" var="file">
		<ul>
			
			<li>파일명 : <a href="download?fileName=${file.key }&oriFileName=test.png">${file.key }</a>
			
			    파일크기 : ${files.value}</li>
		</ul>
	</c:forEach>
</body>
</html>