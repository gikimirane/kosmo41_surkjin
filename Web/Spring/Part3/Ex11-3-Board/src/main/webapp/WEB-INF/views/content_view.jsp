<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
 <!-- Required meta tags -->
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">

    <!-- Bootstrap CSS -->
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.1.3/css/bootstrap.min.css" integrity="sha384-MCw98/SFnGE8fJT3GXwEOngsV7Zt27NXFoaoApmYm81iuXoPkFOJwJ8ERdknLPMO" crossorigin="anonymous">
    <script src="https://code.jquery.com/jquery-3.3.1.slim.min.js" integrity="sha384-q8i/X+965DzO0rT7abK41JStQIAqVgRVzpbzo5smXKp4YfRvH+8abtTE1Pi6jizo" crossorigin="anonymous"></script>
<title>content view</title>
</head>
<body>
<div class="container">
<div class="col-sm-10">
<table width="700" cellpadding="0" cellspacing="0" border="1">	
	<tr>
		<td> 번호 </td>
			<td> ${content_view.bId} </td>
	</tr>
	<tr>
		<td> 히트 </td>
		<td> ${content_view.bHit} </td>
	</tr>
	<tr>
		<td> 이름 </td>
		<td> ${content_view.bName} </td>
	</tr>
	<tr>
		<td> 제목 </td>
		<td> ${content_view.bTitle} </td>
	</tr>
	<tr>
		<td> 내용 </td>
		<td> ${content_view.bContent} </td>
	</tr>
	<tr>
		<td colspan="2">
			<a href="modify_view.do?bId=${content_view.bId}&page=${curPage}">수정</a> &nbsp;&nbsp;
			<a href="list.do?page=${curPage}">목록보기</a> &nbsp;&nbsp;
			<a href="delete.do?bId=${content_view.bId}&page=${curPage}">삭제</a> &nbsp;&nbsp;
			<a href="reply_view.do?bId=${content_view.bId}&page=${curPage}">답변</a>
		</td>
	</tr>	
</table>
</div>
</div>
</body>
</html>