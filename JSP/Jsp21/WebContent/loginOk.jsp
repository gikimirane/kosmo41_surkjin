<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
 <%@ page import="com.study.jsp.MembersDAO" %>
 <%@ page import="com.study.jsp.MembersDTO" %>
 <%
 	request.setCharacterEncoding("UTF-8");
   
   	String id = request.getParameter("id");
   	String pw = request.getParameter("pw");
   	
   	MembersDAO dao = MembersDAO.getInstance();
   	int checkNum = dao.userCheck(id, pw);
   	if(checkNum == -1) {
 %>
 	<script language="javascript">
		alert("아이디가 존재하지 않습니다.");
		history.go(-1);
	</script>
<%
	} else if (checkNum == 0){
%>
	<script language="javascript">
		alert("비밀번호가 틀립니다.");
		history.go(-1);
	</script>
<%
	} else if (checkNum == 1){
		MembersDTO dto = dao.getMember(id);
		
		if (dto == null) {
%>
			<script language="javascript">
				alert("존재하지 않는 회원 입니다.");
				history.go(-1);
			</script>
<% 
		} else {
			String name = dto.getName();
			session.setAttribute("id", id);
			session.setAttribute("name", name);
			session.setAttribute("ValidMem", "yes");
			response.sendRedirect("main.jsp");
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