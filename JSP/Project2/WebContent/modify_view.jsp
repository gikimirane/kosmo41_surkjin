<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
    <!-- Bootstrap CSS -->
<link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.1.3/css/bootstrap.min.css" integrity="sha384-MCw98/SFnGE8fJT3GXwEOngsV7Zt27NXFoaoApmYm81iuXoPkFOJwJ8ERdknLPMO" crossorigin="anonymous">
<script src="https://code.jquery.com/jquery-3.3.1.slim.min.js" integrity="sha384-q8i/X+965DzO0rT7abK41JStQIAqVgRVzpbzo5smXKp4YfRvH+8abtTE1Pi6jizo" crossorigin="anonymous"></script>
<script type="text/javascript" src="./naver_editor/js/service/HuskyEZCreator.js" charset="utf-8"></script>
<title>content view</title>
</head>
<script>
	function form_check(){
		oEditors.getById["ir1"].exec("UPDATE_CONTENTS_FIELD", []);
		document.modify_form.submit();
	}
	
	function f_list(){
		document.location.href="list.do?page=<%= session.getAttribute("curPage")%>&board=${board}&search=0&word=0";
	}
	
	function f_view(){
		document.location.href="content_view.do?bId=${content_view.bId}&board=${board}&search=0&word=0";
	}
</script>
<body>
<div class="container">
<p>
 <div class="col-sm-10">
<table class="table table-bordered" >
	
		<form name="modify_form" action="modify.do" method="post">
			<thead class="thead-light" >
			<tr>
				<th> 번호 </th>
				<input type="hidden" name="bId" value="${content_view.bId}">
				<input type="hidden" name="board"  value="${board}">
				<input type="hidden" name="search"  value="0">
				<input type="hidden" name="word"  value="0">
				<td> ${content_view.bId} </td>
			</tr>
			<tr>
				<th> 조회수 </th>
				<td> ${content_view.bHit} </td>
			</tr>
			<tr>
				<th> 작성자 </th>
				<td>${content_view.bName} </td>
			</tr>
			<tr>
				<th> 제목 </th>
				<td> <input type="text" name="bTitle" size="50" value="${content_view.bTitle}"></td>
			</tr>
			<tr>
				<th> 내용 </th>
				<td> <textarea id="ir1" name="bContent" rows="10">${content_view.bContent}</textarea></td>
				<script type="text/javascript">
					var oEditors = [];
					nhn.husky.EZCreator.createInIFrame({
   					 	oAppRef: oEditors,
    					elPlaceHolder: "ir1",
    					sSkinURI: "./naver_editor/SmartEditor2Skin.html",
    					fCreator: "createSEditor2"
					});
				</script>
			</tr>
			</thead>
			<thead  class="thead-light" align="right" >
			<tr>
				<th colspan="2">
					<button type="button" class="btn btn-outline-primary" onclick="JavaScript:form_check();">수정완료</button> &nbsp;&nbsp;
					<button type="button" class="btn btn-outline-secondary" onclick="JavaScript:f_view();">취소</button> &nbsp;&nbsp;
					<button type="button" class="btn btn-outline-info" onclick="JavaScript:f_list();">목록보기</button>
				</th>
			</tr>	
			</thead>
		</form>
	</table>
	</div>
	</div>
<script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.14.3/umd/popper.min.js" integrity="sha384-ZMP7rVo3mIykV+2+9J3UJ46jBk0WLaUAdn689aCwoqbBJiSnjAK/l8WvCWPIPm49" crossorigin="anonymous"></script>
<script src="https://stackpath.bootstrapcdn.com/bootstrap/4.1.3/js/bootstrap.min.js" integrity="sha384-ChfqqxuZUCnJSK3+MXmPNIyE6ZbWh2IMqE241rYiqJxyMiZ6OW/JmZQ5stwEULTy" crossorigin="anonymous"></script>
</body>
</html>