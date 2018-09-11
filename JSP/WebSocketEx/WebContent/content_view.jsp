<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">

    <!-- Bootstrap CSS -->
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.1.3/css/bootstrap.min.css" integrity="sha384-MCw98/SFnGE8fJT3GXwEOngsV7Zt27NXFoaoApmYm81iuXoPkFOJwJ8ERdknLPMO" crossorigin="anonymous">
    <script src="https://code.jquery.com/jquery-3.3.1.slim.min.js" integrity="sha384-q8i/X+965DzO0rT7abK41JStQIAqVgRVzpbzo5smXKp4YfRvH+8abtTE1Pi6jizo" crossorigin="anonymous"></script>
<title>Content view</title>
</head>
<body>
<% 
		String name = (String)session.getAttribute("name");
%>
<div class="container">
 <div class="col-sm-10">
<table class="table table-bordered" >
<thead class="thead-light" >
	<tr>
		<th> 번호 </th>
		<td> ${content_view.bId} </td>
	</tr>
	<tr>
		<th> 조회수 </th>
		<td> ${content_view.bHit} </td>
	</tr>
	<tr>
		<th> 이름 </th>
		<td> ${content_view.bName} </td>
	</tr>
	<tr>
		<th> 제목 </th>
		<td> ${content_view.bTitle} </td>
	</tr>
	<tr>
		<th> 내용 </th>
		<td> ${content_view.bContent} </td>
	</tr>
	</thead>
	<thead  class="thead-light" align="right" >
	<tr>
		<th colspan="2">
			
			<c:if test="${board=='게시판'}">
				<a href="#" onclick="modify();">수정</a> &nbsp;&nbsp;&nbsp;
				<a href="#" onclick="delData();">삭제</a> &nbsp;&nbsp;&nbsp;
				<a href="reply_view.do?bId=${content_view.bId}&board=${board}">답변</a>&nbsp;&nbsp;&nbsp;
			</c:if>
			<c:if test="${board=='QnA'}">	
				<a href="#" onclick="delData();">삭제</a> &nbsp;&nbsp;&nbsp;
				<a href="reply_view.do?bId=${content_view.bId}&board=${board}">답변</a>&nbsp;&nbsp;&nbsp;
			</c:if>
			<a href="list.do?page=<%= session.getAttribute("curPage")%>&board=${board}">목록보기</a>
		</th>
	</tr>	
	</thead>
</table>
<script>
	function modify(){
		var name = "<%= name %>";
		if(name != "${content_view.bName}"){
			alert("작성자가 아닙니다. 수정할 수가 없습니다.");
			return;
		}
		window.location="modify_view.do?bId=${content_view.bId}&board=${board}";
	}
	
	function delData(){
		var name = "<%= name %>";
		if(name != "${content_view.bName}"){
			alert("작성자가 아닙니다. 삭제할 수가 없습니다.");
			return;
		}
		var ret = confirm("선택하신 데이타 삭제할까요?");
		if(ret == true) {
			window.location="delete.do?bId=${content_view.bId}&board=${board}";
		}
	}
</script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.14.3/umd/popper.min.js" integrity="sha384-ZMP7rVo3mIykV+2+9J3UJ46jBk0WLaUAdn689aCwoqbBJiSnjAK/l8WvCWPIPm49" crossorigin="anonymous"></script>
<script src="https://stackpath.bootstrapcdn.com/bootstrap/4.1.3/js/bootstrap.min.js" integrity="sha384-ChfqqxuZUCnJSK3+MXmPNIyE6ZbWh2IMqE241rYiqJxyMiZ6OW/JmZQ5stwEULTy" crossorigin="anonymous"></script>
</body>
</html>