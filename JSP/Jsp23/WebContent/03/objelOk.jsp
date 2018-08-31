<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Jsp 23-3</title>
</head>
<body>
	<%
		String id = request.getParameter("id");
		String pw = request.getParameter("pw");
	%>
	
	아이디 : <%= id %><br>
	비밀번호 : <%= pw %>
	
	<hr>
	아이디 : ${ param.id }<br>
	비밀번호 : ${ param.pw }<br>
	아이디 : ${ param["id"]}<br>
	비밀번호 : ${ param["pw"]}<br>
	<hr>
	applicationScope : ${applicationScope.application_name}<br>
	sessionScope : ${sessionScope.session_name}<br>
	requestScope : ${requestScope.request_name}<br>
	pageScope : ${pageScope.page_name}<br>
	<hr>
	context 초기화 파라미터<br>
	${initParam.con_name }<br>
	${initParam.con_id }<br>
	${initParam.con_pw }<br>
</body>
</html>