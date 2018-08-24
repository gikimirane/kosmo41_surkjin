<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Join</title>
</head>
<body>
	<form action = "joinProcess" method="post">
		아이디 : <input type="text" name="id" size="12"><br/>
		비밀번호 : <input type="text" name="pw" size="10"><br/>
		이름 : <input type="text" name="name" size="15"><br/>
		전화번호 : <select name="phone1">
			<option value="010">010</option>
			<option value="011">011</option>
			<option value="016">016</option>
			<option value="017">017</option>
			<option value="018">018</option>
			<option value="019">019</option>
		</select> -
		<input type="text" name="phone2" size="5"> -
		<input type="text" name="phone3" size="5"> <br/>
		성별구분 : <input type="radio" name="gender" value="1">남 &nbsp;
				   <input type="radio" name="gender" value="2">여 <br><br>
		<input type="submit" value="회원가입"> <input type="reset" value="취소">
	</form>
</body>
</html>