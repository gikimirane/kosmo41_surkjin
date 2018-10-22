<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>입력</title>
<script type="text/javascript" src="resources/naver_editor/js/service/HuskyEZCreator.js" charset="utf-8"></script>
<script>
	function form_check(){
		oEditors.getById["ir1"].exec("UPDATE_CONTENTS_FIELD", []);
		document.write_form.submit();
	}
</script>
</head>
<body>
	<table width="700" cellpadding="0" cellspacing="0" border="1">
		<form name="write_form" action="write.do" method="post">
			<tr>
				<td> 이름 </td>
				<td> <input type="text" name="bName" size="50"></td>
			</tr>
			<tr>
				<td> 제목 </td>
				<td> <input type="text" name="bTitle" size="50"></td>
			</tr>
			<tr>
				<td> 내용 </td>
				<td> <textarea id="ir1" name="bContent" rows="10" column="100"></textarea></td>
				
				<script type="text/javascript">
					var oEditors = [];
					nhn.husky.EZCreator.createInIFrame({
   					 	oAppRef: oEditors,
    					elPlaceHolder: "ir1",
    					sSkinURI: "resources/naver_editor/SmartEditor2Skin.html",
    					fCreator: "createSEditor2"
					});
				</script>
			</tr>
			<tr>
				<td colspan="2">
					<input type="submit" value="입력" onclick="JavaScript:form_check();"> &nbsp;&nbsp;
					<a href="list.do">목록보기</a>
				</td>
			</tr>	
		</form>
	</table>
</body>
</html>