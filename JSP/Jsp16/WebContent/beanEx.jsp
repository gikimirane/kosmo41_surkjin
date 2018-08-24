<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<jsp:useBean id="student" class="com.study.jsp.Student" scope="page" />
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Jsp Bean</title>
</head>
<body>
	<jsp:setProperty name="student" property="name" value="홍길동" />
	<jsp:setProperty name="student" property="age" value="13" />
	<jsp:setProperty name="student" property="grade" value="6" />
	<jsp:setProperty name="student" property="studentNum" value="7" />
	
	이름 : <jsp:getProperty name="student" property="name" /><br />
	나이 : <jsp:getProperty name="student" property="age" /><br />
	학년 : <jsp:getProperty name="student" property="grade" /><br />
	번호 : <jsp:getProperty name="student" property="studentNum" /><br />
</body>
</html>