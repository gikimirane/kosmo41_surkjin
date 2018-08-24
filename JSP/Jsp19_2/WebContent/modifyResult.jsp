<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Modify</title>
</head>
<body>
<%=session.getAttribute("name") %>님의 회원정보가 수정되었습니다.<br><p>

<a href="logout.jsp">로그아웃</a> &nbsp;&nbsp; <a href="modify.jsp">정보수정</a>
</body>
</html>