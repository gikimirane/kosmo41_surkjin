<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
<meta name="google-signin-client_id" content="99312446803-1c47skmtp769lsuu24sf1pfii6at9ven.apps.googleusercontent.com">
<!-- Bootstrap CSS -->
<link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.1.3/css/bootstrap.min.css" integrity="sha384-MCw98/SFnGE8fJT3GXwEOngsV7Zt27NXFoaoApmYm81iuXoPkFOJwJ8ERdknLPMO" crossorigin="anonymous">
<script src="https://code.jquery.com/jquery-3.3.1.slim.min.js" integrity="sha384-q8i/X+965DzO0rT7abK41JStQIAqVgRVzpbzo5smXKp4YfRvH+8abtTE1Pi6jizo" crossorigin="anonymous"></script>
<script src="http://code.jquery.com/jquery.js"></script>
<title> 로그인 </title>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <script src="http://code.jquery.com/jquery.js"></script>

	<meta name="google-signin-client_id" content="99312446803-1c47skmtp769lsuu24sf1pfii6at9ven.apps.googleusercontent.com">
	<%! 
		String gid="";
	%>
	<script>
    function onSignIn(googleUser) {
    	var profile = googleUser.getBasicProfile();
    	var gid = profile.getName();
    	<% session.setAttribute("id", gid); 
    	session.setAttribute("name", gid);
    	session.setAttribute("ValidMem", "google"); %>
    	//window.location="client.jsp";
    }
    function onFailure(error) {
    }
	
    function renderButton() {
        gapi.signin2.render('my-signin2', {
	        'scope': 'profile email',
	        'width': 240,
	        'height': 50,
	        'longtitle': true,
	        'theme': 'dark',
	        'onsuccess': onSignIn,
	        'onfailure': onFailure
        });
    }
  
</script>
<script>
function form_check(){
	submit();
}

function submit(){
	document.login_form.submit();
}
</script>
</head>
<body>
<div class="container">
<div class="row justify-content-center">
	<form name="login_form" class="form-signin" action="loginOk.do" method = "post">
    	<h1>&nbsp;</h1>
		<h1>&nbsp;</h1>
   		<h1 class="h1 mb-4 font-weight-normal">Project #2</h1>
    	<input type="text" name="id" id="inputLogin" class="form-control" placeholder="아이디" required autofocus 
      			 value="<% if(session.getAttribute("id") != null)
					out.println(session.getAttribute("id"));%>">
      
      	<input type="password" name="pw" id="inputPassword" class="form-control" placeholder="비밀번호" required>
    	<div class="checkbox mb-3 row justify-content-center">
        <label>
          <input type="checkbox" value="remember-me" checked="checked"> remember-me
        </label>
        <div id="msg"></div>
      	</div>
     	<button class="btn btn-lg btn-primary btn-block" type="submit" >로그인</button><p>
    
   		 <div id="my-signin2"></div><p><p>
     	<button class="btn btn-info btn-block" type="button" onclick="javascript:window.location='join.jsp'">회원가입</button>    
	</form>
</div>
  
<script src="https://apis.google.com/js/platform.js?onload=renderButton" async defer></script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.14.3/umd/popper.min.js" integrity="sha384-ZMP7rVo3mIykV+2+9J3UJ46jBk0WLaUAdn689aCwoqbBJiSnjAK/l8WvCWPIPm49" crossorigin="anonymous"></script>
<script src="https://stackpath.bootstrapcdn.com/bootstrap/4.1.3/js/bootstrap.min.js" integrity="sha384-ChfqqxuZUCnJSK3+MXmPNIyE6ZbWh2IMqE241rYiqJxyMiZ6OW/JmZQ5stwEULTy" crossorigin="anonymous"></script>
</body>
</html>

