<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
 <%@ page import="com.study.jsp.MembersDAO" %>
 <%@ page import="com.study.jsp.MembersDTO" %>
 <%
 	request.setCharacterEncoding("UTF-8");
 %>
 <%
 	String id = (String)session.getAttribute("id");
   	MembersDAO dao = MembersDAO.getInstance();
   	MembersDTO dto = dao.getMember(id);
 %>
<!DOCTYPE html">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">

<!-- Bootstrap CSS -->
<link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.1.3/css/bootstrap.min.css" integrity="sha384-MCw98/SFnGE8fJT3GXwEOngsV7Zt27NXFoaoApmYm81iuXoPkFOJwJ8ERdknLPMO" crossorigin="anonymous">
<script src="https://code.jquery.com/jquery-3.3.1.slim.min.js" integrity="sha384-q8i/X+965DzO0rT7abK41JStQIAqVgRVzpbzo5smXKp4YfRvH+8abtTE1Pi6jizo" crossorigin="anonymous"></script>

<title>회원정보관리</title>
<script language="JavaScript" src="members.js" ></script>
</head>
<body>
	<div><h1>&nbsp;</h1></div>
	<div class="col-sm-9 row justify-content-center">
   	 	<h1 class="h2 mb-1 font-weight-normal">회원정보 관리</h1>
    </div>

	<div class="container">
 		<div class="col-sm-8">
		<table class="table table-bordered" >
		
		<form name="reg_frm" action="modifyMember.do" method="post">
			<input id="quit" type="hidden" name="quit"  value="1">
			<thead class="thead-light" >
				<tr>
					<th> 아이디 </th>
					<td> <%=dto.getId()%> </td>
				</tr>
				<tr>
					<th> 이름 </th>
					<td> <%=dto.getName()%></td>
				</tr>
				<tr>
					<th> 비밀번호 </th>
					<div class="input-group mb-3">
					<td> <input id="pw" class="form-control" type="password" name="pw" placeholder="비밀번호를 입력하세요."></td>
					</div>
				</tr>
				<tr>
				<div class="input-group mb-3">
					<th> 비밀번호 확인 </th>
					<td> <input id="pw_check" class="form-control" type="password" name="pw_check" placeholder="비밀번호를 다시 입력하세요."></td>
					</div>
				</tr>
				<tr>
					<div class="input-group mb-3">
					<th> 메일 </th>
					<td> <input class="form-control"  type="text" name="eMail" size="70" value="<%= dto.geteMail() %>"></td>
					</div>
				</tr>
				<tr>
					<th> 주소 </th>
					<div class="input-group mb-3">
					<td> <input class="form-control" type="text" name="address" size="70" value="<%= dto.getAddress() %>"></td>
					</div>
				</tr>
			</thead>
			<thead  class="thead-light" align="right" >
				<tr>
					<th colspan="2">
						<button type="button" class="btn btn-outline-primary" onclick="JavaScript:updateInfoConfirm();">수정</button> &nbsp;&nbsp;&nbsp;
						<button type="button" class="btn btn-outline-danger" onclick="javascript:quitMember();">탈퇴</button>&nbsp;&nbsp;&nbsp;
						<button type="button" class="btn btn-outline-secondary" onclick="javascript:window.location='client.jsp'">취소</button>
					</th>	
				</tr>	
			</thead>
		</form>
	</table>
	</div>
	</div>
<script>
	
	function quitMember(){
		if(pw.value == ""){
			alert("패스워드를 입력하세요.");
			pw.focus();
			return
		}
		
		if(pw.value != pw_check.value){
			alert("패스워드가 일치하지 않습니다.");
			pw.focus();
			return
		}
		var ret = confirm("회원탈퇴를 하시려면 확인을 누르세요!");
		if(ret == true) {
			document.getElementById("quit").value = "0";
			document.reg_frm.submit();
		}
	}
</script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.14.3/umd/popper.min.js" integrity="sha384-ZMP7rVo3mIykV+2+9J3UJ46jBk0WLaUAdn689aCwoqbBJiSnjAK/l8WvCWPIPm49" crossorigin="anonymous"></script>
<script src="https://stackpath.bootstrapcdn.com/bootstrap/4.1.3/js/bootstrap.min.js" integrity="sha384-ChfqqxuZUCnJSK3+MXmPNIyE6ZbWh2IMqE241rYiqJxyMiZ6OW/JmZQ5stwEULTy" crossorigin="anonymous"></script>
</body>
</html>