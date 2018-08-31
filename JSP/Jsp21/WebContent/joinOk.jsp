<%@ page import="java.sql.Timestamp" %>
<%@ page import="com.study.jsp.*" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" %>
<% request.setCharacterEncoding("UTF-8"); %>
<jsp:useBean id="dto" class="com.study.jsp.MembersDTO" />
<jsp:setProperty name="dto" property="*" />
<%
	dto.setrDate(new Timestamp(System.currentTimeMillis()));
	MembersDAO dao = MembersDAO.getInstance();
	if(dao.confirmId(dto.getId()) == MembersDAO.MEMBER_EXIT){
%>
		<script language="javascript">
			alert("아이디가 이미 존재합니다.");
			history.back();
		</script>
<%
	} else {
		int ri = dao.insertMembers(dto);
		if(ri == MembersDAO.MEMBER_JOIN_SUCESS) {
	session.setAttribute("id", dto.getId());
%>
			<script language="javascript">
				alert("회원가입을 축하합니다.");
				document.location.href="login.jsp";
			</script>
<%
		} else {
%>
			<script language="javascript">
				alert("회원가입에 실패했습니다.");
				document.location.href="login.jsp";
			</script>
<%	
		}
	}
%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Jsp 21-1</title>
</head>
<body>

</body>
</html>