<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Jsp 23-3</title>
</head>
<body>
<form action="objelOk.jsp" method="post">
		아이디 : <input type="text" name="id"/><br>
		비밀번호 : <input type="password"  name="pw"/>
		<input type="submit" value="login" >
	</form>

	<%
		application.setAttribute("application_name", "application_value");
		session.setAttribute("session_name", "session_value");
		request.setAttribute("request_name", "request_value");
		pageContext.setAttribute("page_name", "page_value");
	%>
</body>
</html>