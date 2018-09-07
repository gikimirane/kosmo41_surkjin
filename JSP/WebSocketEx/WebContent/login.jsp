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

<title>login</title>
</head>
<script>
function form_check(){
	submit();
}

function submit(){
	document.login_form.submit();
}

</script>
<body>
<div class="container">
<div class="row justify-content-center">
	<form name="login_form" class="form-signin" action="loginOk.do" method = "post">
	 <h1>&nbsp;</h1>
	 <h1>&nbsp;</h1>
   	 <h1 class="h3 mb-4 font-weight-normal">Please login In ...</h1>
      
      <input type="text" name="id" id="inputLogin" class="form-control" placeholder="아이디" required autofocus 
      		 value="<% if(session.getAttribute("id") != null)
					out.println(session.getAttribute("id"));%>">
      
      <input type="password" name="pw" id="inputPassword" class="form-control" placeholder="비밀번호" required>
      <div class="checkbox mb-3 row justify-content-center">
        <label>
          <input type="checkbox" value="remember-me" checked="checked"> remember-me
        </label>
      </div>
      <button class="btn btn-lg btn-primary btn-block" type="submit" >로그인</button>
      <button class="btn btn-success btn-block" type="button" >구글아이디로 로그인</button>
      <button class="btn btn-info btn-block" type="button" onclick="javascript:window.location='join.jsp'">회원가입</button>
    </form>
	</div>
</div>
<script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.14.3/umd/popper.min.js" integrity="sha384-ZMP7rVo3mIykV+2+9J3UJ46jBk0WLaUAdn689aCwoqbBJiSnjAK/l8WvCWPIPm49" crossorigin="anonymous"></script>
<script src="https://stackpath.bootstrapcdn.com/bootstrap/4.1.3/js/bootstrap.min.js" integrity="sha384-ChfqqxuZUCnJSK3+MXmPNIyE6ZbWh2IMqE241rYiqJxyMiZ6OW/JmZQ5stwEULTy" crossorigin="anonymous"></script>
</body>
</html>
