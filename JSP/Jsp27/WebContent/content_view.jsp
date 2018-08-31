<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
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
<div class="container">
 <div class="col-sm-10">
<table class="table table-bordered" >
<thead class="thead-light" >
	<tr>
		<th> 번호 </th>
		<td> ${content_view.bId} </td>
	</tr>
	<tr>
		<th> 히트 </th>
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
			<a href="modify_view.do?bId=${content_view.bId}">수정</a> &nbsp;&nbsp;&nbsp;
			<a href="list.do?page=<%= session.getAttribute("curPage") %>">목록보기</a> &nbsp;&nbsp;&nbsp;
			<a href="delete.do?bId=${content_view.bId}">삭제</a> &nbsp;&nbsp;&nbsp;
			<a href="reply_view.do?bId=${content_view.bId}">답변</a>
		</th>
	</tr>	
	</thead>
</table>
<script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.14.3/umd/popper.min.js" integrity="sha384-ZMP7rVo3mIykV+2+9J3UJ46jBk0WLaUAdn689aCwoqbBJiSnjAK/l8WvCWPIPm49" crossorigin="anonymous"></script>
<script src="https://stackpath.bootstrapcdn.com/bootstrap/4.1.3/js/bootstrap.min.js" integrity="sha384-ChfqqxuZUCnJSK3+MXmPNIyE6ZbWh2IMqE241rYiqJxyMiZ6OW/JmZQ5stwEULTy" crossorigin="anonymous"></script>
</body>
</html>