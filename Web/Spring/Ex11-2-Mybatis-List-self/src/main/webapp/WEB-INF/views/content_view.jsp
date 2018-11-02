<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
   
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
</head>
<body>

<table width="500" cellpadding="0" cellspacing="0" border="1">
		<input type="hidden" name="bId" value="${content_view.bId }">
		<tr>
			<td>번호</td>
			<td>${content_view.bId }</td>
		</tr>
		<tr>
			<td>히트</td>
			<td>${content_view.bHit}</td>
		</tr>
		<tr>
			<td>이름</td>
			<td>${content_view.bName}</td>
		</tr>
		<tr>
			<td>제목</td>
			<td>${content_view.bTitle}</td>
		</tr>
		<tr>
			<td>내용</td>
			<td>${content_view.bContent}</td>
		</tr>
		<tr>
			<td colspan=2>
				<a href="modify_view?bId=${content_view.bId }">수정</a>&nbsp;&nbsp;
				<a href="list?page=<%=session.getAttribute("cpage")%>">목록보기</a>&nbsp;&nbsp;
				<a href="delete?bId=${content_view.bId}">삭제</a>&nbsp;&nbsp;
				<a href="reply_view?bId=${content_view.bId}">답변</a>&nbsp;&nbsp;
			</td>
		</tr>	
</table>
</body>
</html>