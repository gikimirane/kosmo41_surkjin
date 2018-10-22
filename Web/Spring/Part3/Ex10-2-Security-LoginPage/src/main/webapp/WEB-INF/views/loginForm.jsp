<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
</head>
<body>
<h1>로그인페이지</h1>
<form action="<c:url value="j_spring_security_check"/>" method="post">

ID : <input type="text" name="j_username" id="j_username"><br/>
PW : <input type="text" name="j_password" id="j_password"><br/>
<input type="submit" value="전송"><br/>

</form>

</body>
</html>