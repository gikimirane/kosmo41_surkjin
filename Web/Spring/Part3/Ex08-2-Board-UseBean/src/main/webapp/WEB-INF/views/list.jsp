<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>list</title>
<style>
</style>
</head>
<body>
 
	<table width="700" cellpadding="0" cellspacing="0" border="1">
		<tr>
			<td align="center">번호</td>
			<td align="center">이름</td>
			<td align="center">제목</td>
			<td align="center">날짜</td>
			<td align="center">히트</td>
		</tr>
		<c:forEach items="${list}" var="dto">
		<tr>
			<td>${dto.bId}</td>
			<td>${dto.bName}</td>
			<td>
				<c:forEach begin="1" end="${dto.bIndent}">-</c:forEach>
				<a href="content_view.do?bId=${dto.bId}">${dto.bTitle}</a>
			</td>
			<td>${dto.bDate}</td>
			<td>${dto.bHit}</td>
		</tr>
		</c:forEach>
		<tr>
			<td colspan="5" align="right"> 
			<a href="write_view.do">글작성</a> &nbsp;&nbsp;
			</td>
		</tr>
		<tr>
			<td colspan="5" align="center"> 
			<c:choose>
			<c:when test="${(page.curPage-1)<1}">
				[처음]
			</c:when>
			<c:otherwise>
				<a href="list.do?page=1">[처음]</a>
			</c:otherwise>
			</c:choose>	
			
			<c:choose>
			<c:when test="${(page.curPage-1)<1}">
				◀이전 &nbsp;&nbsp;
			</c:when>
			<c:otherwise>
				<a href="list.do?page=${page.curPage-1}">◀이전</a>&nbsp;&nbsp;
			</c:otherwise>
			</c:choose>	
			
			<c:forEach var="num"  begin="${page.startPage}" end="${page.endPage}" step="1">
			<c:choose>
			<c:when test="${page.curPage==num}">
				${num}&nbsp;
			</c:when>
			<c:otherwise>
				<a href="list.do?page=${num}" >${num}</a>&nbsp;
			</c:otherwise>
			</c:choose>	
			</c:forEach>
			&nbsp;
			
			<c:choose>
			<c:when test="${(page.curPage+1) > page.totalPage}">
				다음▶ 
			</c:when>
			<c:otherwise>
				<a href="list.do?page=${page.curPage+1}">다음▶</a>
			</c:otherwise>
			</c:choose>	
			
			<c:choose>
			<c:when test="${page.curPage== page.totalPage}">
				[끝]
			</c:when>
			<c:otherwise>
				<a href="list.do?page=${page.totalPage}">[끝]</a>
			</c:otherwise>
			</c:choose>	
			</td>
		</tr>
	</table>
	<!-- endpage: ${page.endPage} <br> -->
</body>
</html>