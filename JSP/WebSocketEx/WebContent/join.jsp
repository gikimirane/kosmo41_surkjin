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

<title>회원가입</title>
<script language="JavaScript" src="members.js" ></script>
</head>
<body>
	<div class="row justify-content-center">
   	 <h1 class="h3 mb-4 font-weight-normal">회원 가입</h1>
    </div>

<div class="container">
	<form class="form-signin" action="joinOk.do" Method="post" name="reg_frm">
		
	<div class="input-group mb-3">
  		<div class="input-group-prepend">
  		
    	<span class="input-group-text" id="basic-addon1">아이디*</span>
  		</div>
  		<input type="text" name="id" class="form-control" placeholder="4글자이상 최대 20자이내" >
	</div>
	<div class="input-group mb-3">
  		<div class="input-group-prepend">
  		
    	<span class="input-group-text" id="basic-addon1">이름*</span>
  		</div>
  		<input type="text" name="name" class="form-control" placeholder="" >
	</div>
	<div class="input-group mb-3">
  		<div class="input-group-prepend">
  		
    	<span class="input-group-text" id="basic-addon2">비밀번호*</span>
  		</div>
  		<input type="password" name="pw" class="form-control" placeholder="" >
	</div>
	<div class="input-group mb-3">
  		<div class="input-group-prepend">
  		
    	<span class="input-group-text" id="basic-addon3">비밀번호 확인*</span>
  		</div>
  		<input type="password" name="pw_check" class="form-control" placeholder="" >
	</div>
	<div class="input-group mb-3">
  		<div class="input-group-prepend">
  		
    	<span class="input-group-text" id="basic-addon4">메일*</span>
  		</div>
  		<input name="eMail" type="email" id="inputEmail" class="form-control" placeholder="@가 포함된 이메일 주소" >
	</div>
	<div class="input-group mb-3">
  		<div class="input-group-prepend">
  		
    	<span class="input-group-text" id="basic-addon5">주소</span>
  		</div>
  		<input type="text" name="address" class="form-control" placeholder="" aria-label="Username" aria-describedby="basic-addon1">
	</div>
	
		<div align="center">
		<input type="button" class="btn btn-primary" value="회원가입" onclick="infoConfirm()">&nbsp;&nbsp;&nbsp;
				
		<input type="button" class="btn btn-info" value="로그인" onclick="javascript:window.location='login.jsp'">
		</div>
		
	</form>
</div>
<script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.14.3/umd/popper.min.js" integrity="sha384-ZMP7rVo3mIykV+2+9J3UJ46jBk0WLaUAdn689aCwoqbBJiSnjAK/l8WvCWPIPm49" crossorigin="anonymous"></script>
<script src="https://stackpath.bootstrapcdn.com/bootstrap/4.1.3/js/bootstrap.min.js" integrity="sha384-ChfqqxuZUCnJSK3+MXmPNIyE6ZbWh2IMqE241rYiqJxyMiZ6OW/JmZQ5stwEULTy" crossorigin="anonymous"></script>
</body>
</html>