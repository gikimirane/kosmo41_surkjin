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
<script type="text/javascript" src="http://code.jquery.com/jquery.js"></script>
<script type="text/javascript">
	$(document).ready(function(){
    	$("#messageinput").keydown(function (key) {
        	if(key.keyCode == 13) send();
    	}); 
	});
 
</script>
<title>Clent</title>
</head>
<body>
<% if(session.getAttribute("ValidMem") == null){ %>
		<jsp:forward page="login.jsp" />
<%	}  
		String id = (String)session.getAttribute("id");
		String name = (String)session.getAttribute("name");
%>
	<div class="container">
	<nav class="navbar navbar-expand-lg navbar-light bg-light">
  <button class="navbar-toggler" type="button" data-toggle="collapse" data-target="#navbarTogglerDemo01" aria-controls="navbarTogglerDemo01" aria-expanded="false" aria-label="Toggle navigation">
    <span class="navbar-toggler-icon"></span>
  </button>
  <div class="collapse navbar-collapse" id="navbarTogglerDemo01">
    <a class="navbar-brand" href="#">Project#2</a>
    <ul class="navbar-nav mr-auto mt-2 mt-lg-0">
      <li class="nav-item active">
        <a class="nav-link" href="#">채팅<span class="sr-only">(current)</span></a>
      </li>
      <li class="nav-item">
        <a class="nav-link" href="list.do?page=<%= session.getAttribute("curPage") %>&board=게시판">게시판</a>
      </li>
      <li class="nav-item">
        <a class="nav-link" href="list.do?page=<%= session.getAttribute("curPage") %>&board=공지사항">공지사항</a>
      </li>
       <li class="nav-item">
        <a class="nav-link" href="list.do?page=<%= session.getAttribute("curPage") %>&&board=QnA">Q&A</a>
      </li>
      <li class="nav-item">
        <a class="nav-link" href="modify_member.jsp">회원정보</a>
      
    </ul>
     <button class="btn btn-outline-success my-2 my-sm-0" onclick="javascript:logOut();">로그아웃</button>
  </div>
</nav>
	<div class="row">
	<div class="col-4">
	<div><p>
	<div>
		<span> 리스트 </span>&nbsp; &nbsp;
		<a href="#" onclick="showList();">접속자</a> &nbsp; &nbsp;
		<a href="#" onclick="waitList();">대기실</a>
		</div>
		<div>
		<span> 대화방 </span>&nbsp; &nbsp;
		<a href="#" onclick="roomList(0);">전체</a>&nbsp; &nbsp;
		<a href="#" onclick="roomList(1);">공개방</a>&nbsp; &nbsp;
		<a href="#" onclick="roomList(2);">비공개방</a>
		</div>
		<div>
		<span> 귓속말 </span>&nbsp; &nbsp;
		<a href="#" onclick="setTalkToOne(2,1);">설정</a>&nbsp; &nbsp;
		<a href="#" onclick="setTalkToOne(2,0);">해제</a>
		</div>
		<div>
		<span> 방만듬 </span>&nbsp; &nbsp;
		<a href="#" onclick="makeRooms(1,1);">공개방</a>&nbsp; &nbsp;
		<a href="#" onclick="makeRooms(2,1);">비공개방</a>
		</div>
		<div>
		<span> 금칙어 </span>&nbsp; &nbsp;
		<a href="#" onclick="banList();">보기</a>&nbsp; &nbsp;
		<a href="#" onclick="disBan(1);">설정</a>&nbsp; &nbsp;
		<a href="#" onclick="delBanWord();">해제</a>
		</div>
		<div>
		<span> 게임쓰</span>&nbsp; &nbsp;
		<a href="#" onclick="playGame(0,3)">야구(1인)</a>&nbsp; 
		<a href="#" onclick="playGame(0,1)">야구(2인)</a>&nbsp; 
		<a href="#" onclick="playGame(0,2)">TicTacToe</a>
		</div>
	</div>
	<div >
	<div id="makeRoom1" style="display:none" >
	<hr>
	<div>공개방 만들기</div><p>
	<div class="input-group mb-1">
	<div class="input-group-prepend">
	<span class="input-group-text" id="basic-addon1">방이름</span>
	</div>
	<input id="roomNm" type="text" class="form-control" name="roomNm" placeholder="최대 20자 이내"><br>
	</div>
	<div class="input-group mb-3">
	<div class="input-group-prepend">
	<span class="input-group-text"  id="basic-addon1" >정원</span>
	</div>
	<input id="fixNo" type="text" class="form-control"  name="fixNo"  placeholder="디폴트 20명이고 최대 999명까지">
	</div>
	<div align="center">
		<input type="button" class="btn btn-primary"  value="생성" onclick="makeRoom(1);"/> &nbsp; &nbsp;
		<input type="button" class="btn btn-secondary" value="취소"  onclick="makeRooms(1, 0);"/> 
	</div>
	</div>
	<hr>
	 </div>
	 <div>
	<div id="makeRoom2" style="display:none" >
	<div>비공개방 만들기</div><p>
	<div class="input-group mb-1">
	<div class="input-group-prepend">
	<span class="input-group-text" id="basic-addon1">방이름</span>
	</div>
	<input id="roomNm2" type="text" class="form-control" name="roomNm2" placeholder="최대 20자 이내">
	</div>
	<div class="input-group mb-1">
	<div class="input-group-prepend">
	<span class="input-group-text"  id="basic-addon1" >정원</span>
	</div>
	<input id="fixNo2" type="text" class="form-control"  name="fixNo2"  placeholder="디폴트 20명이고 최대 999명까지">
	</div>
	<div class="input-group mb-1">
	<div class="input-group-prepend">
	<span class="input-group-text"  id="basic-addon1" >비밀번호 입력</span>
	</div>
	<input id="pw" type="password" class="form-control"  name="pw"  placeholder="최소 4글자 이상">
	</div>
	<div class="input-group mb-3">
	<div class="input-group-prepend">
	<span class="input-group-text"  id="basic-addon1" >비밀번호 확인</span>
	</div>
	<input id="pw_chk" type="password" class="form-control"  name="pw_chk"  placeholder="비밀번호 다시 입력 하세요">
	</div>
	<div align="center">
		<input type="button" class="btn btn-primary" value="생성" onclick="makeRoom(2);"/> &nbsp; &nbsp;
		<input type="button" class="btn btn-secondary" value="취소" onclick="makeRooms(2, 0);"/> 
	</div>
	<hr>
	</div>
	</div>
	<div>
	<div id="banWord" style="display:none">
	<div>금칙어 설정</div><p>
	<div class="input-group mb-3">
	<div class="input-group-prepend">
	<span class="input-group-text" id="basic-addon1">금칙어</span>
	</div>
	<input id="ban" type="text" class="form-control"  name="ban"  placeholder="단어를 입력하세요">
	</div>
	 <div align="center">
		<input type="button" class="btn btn-primary" value="확인" onclick="banWord();"/> &nbsp; &nbsp;
		<input type="button" class="btn btn-secondary" value="취소" onclick="disBan(0);"/> 
	</div>
	<hr>
	</div>
	</div>
	<div id="inputPw" style="display:none">
	<div>비공개방 비밀번호 입력</div><p>
	<div class="input-group mb-3">
	<div class="input-group-prepend">
	<span class="input-group-text" id="basic-addon1">비밀번호</span>
	</div>
	<input id="pw1" type="password" class="form-control"  name="pw1" >
	</div>
	<div align="center">
		<input type="button" class="btn btn-primary" value="확인" onclick="enterRoom();"/> &nbsp; &nbsp;
		<input type="button" class="btn btn-secondary" value="취소" onclick="disPasswd();"/> 
	</div><hr>
	</div>
	<div id="messages1"> </div>
	</div>
	
	<div class="col-4" >
	<p>
	<div id="messages5">
		<%= id %> 님 반갑습니다.
	</div><p>
	<div>
  	<input type="text" id="messageinput" class="form-control" ></input>
	</div><p>
	<div>
		<button type="button" class="btn btn-secondary" onclick="openSocket();">Open</button>
		<button type="button" class="btn btn-primary" onclick="send();">Send</button>
		<button type="button" class="btn btn-info" onclick="sendAll();">notice</button>
		<button type="button" class="btn btn-info" onclick="javacript:clears();">Clear</button>
		<button type="button" class="btn btn-secondary" onclick="closeSocket();">Close</button>
		
	</div>
	<hr>
	<div id="messages4" style="color:steelblue"></div>
	<hr>
	<div id="replyYN" style="display:none" align="center">
		<input type="button" class="btn btn-primary" value="수락(Y)" onclick="replyYn(1);"/> &nbsp; &nbsp;
		<input type="button" class="btn btn-secondary" value="거부(N)" onclick="replyYn(0);"/> 
	<hr>
	</div>
	<!--  Server responses get written here -->
	<div id="messages2" style="color:blue"></div><p>
	<div id="messages6"></div>
	</div>
	<div class="col-4">
	<div><p>
	<div>
		<span> 리스트 </span>&nbsp; &nbsp;
		<a href="#" onclick="myRoomList();">내방보기</a>
	</div>
		<div>
		<span> 귓속말 </span>&nbsp; &nbsp;
		<a href="#" onclick="setTalkToOne(4,1)">설정</a>&nbsp; &nbsp;
		<a href="#" onclick="setTalkToOne(4,0)">해제</a>
		</div>
		<div>
		<span> 방관련 </span>&nbsp; &nbsp;
		<a href="#" onclick="callName();">초대</a>&nbsp; &nbsp;
		<a href="#" onclick="outRoom();">강퇴</a>&nbsp; &nbsp;
		<a href="#" onclick="changeCap();">방장승계</a>&nbsp; &nbsp;
		<a href="#" onclick="deleteRoom();">방폭파</a>
		</div>
		<div>
		<span> 게임쓰 </span>&nbsp; &nbsp;
		<a href="#" onclick="playGame(1,3)">야구(1인)</a>&nbsp;
		<a href="#" onclick="playGame(1,1)">야구(2인)</a>&nbsp;
		<a href="#" onclick="playGame(1,2)">TicTacToe</a>
		</div><p> 
		<div>
		<span> 나가기 </span>&nbsp; &nbsp;
		<a href="#" onclick="exitRoom();">방나가기</a>
		</div>
		<hr>
	</div>
	<div id="messages3"></div>
	</div>
	</div>
	</div>
	
	<!-- Script to utilise the WebSocket -->
	<script type="text/javascript">
		var webSocket;
		var room_no=0, room_cd=0, talkTo=0, logType=0;
		var mbr_id, game_cd, click_no;
		var id = "<%= id %>";
		
		function openSocket(){
			if(webSocket != undefined && webSocket.readyState != WebSocket.CLOSED) {
				writeResponse("4 WebSocket is already opened.");
				return;
			}
			
			webSocket = new WebSocket("ws://localhost:8081/WebSocketEx/websocketendpoint2");
			logType=1;
			webSocket.onopen = function(event) {
				if (event.data == undefined)
					return ;
				
				writeResponse(event.data);
			};
			
			webSocket.onmessage = function(event) {
				writeResponse(event.data);
			};
			
			webSocket.onclose = function(event) {
				writeResponse("4 connection closed");
			
				messages1.innerHTML = "";
				messages2.innerHTML = "";
				messages3.innerHTML = "";
				messages6.innerHTML = "";
				messages5.innerHTML =  id + " 님 반갑습니다.";
			};
		}
		
		function send() {
			var text = document.getElementById("messageinput").value;
			if(text==null || text.trim(" ")=="")		return;
			if(talkTo==1) webSocket.send(id +" 2 "+"/to " + mbr_id + " " + text);
			webSocket.send(id +" 2 "+text);
			messages4.innerHTML = "";
			document.getElementById("messageinput").value = "";
		}
		
		function playGame(g_cd, g_no){
			if(chkWebSocket()==false) return;
			game_cd = g_cd;
			
			if(g_no < 3){
				if(mbr_id ==null || mbr_id==""){
					messages4.innerHTML = "게임을 같이 할 사람을 선택하세요.";
					return;
				}else if(room_no==0 && click_no != 2){
					messages4.innerHTML = "대기실에 있는 사람을 선택하세요.";
					return;
				}else if(room_no > 0 && click_no != 4){
					messages1.innerHTML = "같은 대화방에 있는 사람을 선택하세요.";
					return;
				}
					webSocket.send(id +" 2 "+"/gam" + g_no +" " + mbr_id);
			} else 	webSocket.send(id +" 2 "+"/gam" + g_no);
			
			messages4.innerHTML = "";
		}
		
		function replyYn(yn){
			webSocket.send(id +" 2 "+ (yn==1 ? "y":"n"));
			replyYN.style.display ="none";
			//myRoomList();
		}
		
		function makeRooms(rm, cd){
			if(chkWebSocket()==false) return;
			if(cd==1 && room_no > 0){
				messages4.innerHTML = "대기실에서만 대화방을 만들 수 있습니다.";
				return;
			}
			document.getElementById(rm==1 ? 'makeRoom1':'makeRoom2').style.display =(cd==1 ? "block":"none");
			messages4.innerHTML = "";
		}
		
		function disBan(cd){
			if(chkWebSocket()==false) return;
			document.getElementById('banWord').style.display =(cd==1 ? "block":"none");	
			//messages4.innerHTML = "";
		}
		
		function changeCap() {
			if(chkWebSocket()==false) return;
			if(room_no==0){
				messages4.innerHTML = "현재 대기실입니다.";
				return;
			}
			else if(click_no != 4){
				messages4.innerHTML = "같은 대화방에 있는 사람을 선택하세요.";
				return;
			}
			else if(mbr_id ==null || mbr_id.lenght==0 || id==mbr_id){
				messages4.innerHTML = "방장시킬 사람을 선택하세요.";
				return;
			}
			webSocket.send(id +" 2 " + "/cap " + mbr_id);
			messages4.innerHTML = "";
			myRoomList();
		}
		
		function outRoom() {
			if(chkWebSocket()==false) return;
			if(room_no==0){
				messages4.innerHTML = "현재 대기실입니다.";
				return;
			}
			else if(click_no != 4){
				messages4.innerHTML = "같은 대화방에 있는 사람을 선택하세요.";
				return;
			}
			else if(mbr_id == null || id==mbr_id){
				messages4.innerHTML = "강퇴시킬 사람을 선택하세요.";
				return;
			}
			webSocket.send(id +" 2 " + "/out " + mbr_id);
			messages4.innerHTML = "";
			myRoomList();
		}
		
		function sendAll() {
			if(chkWebSocket()==false) return;
			var text = document.getElementById("messageinput").value;
			if(text==null || text.trim(" ")=="")		return;
			webSocket.send(id +" 2 "+"/noti " + text);
			document.getElementById("messageinput").value = "";
		}
		
		function banWord(){
			if(chkWebSocket()==false) return;
			var text = document.getElementById("ban").value;
			if(text.trim(" ")==""|| text==null){
				messages4.innerHTML = "단어를 입력하세요." ;
				return;
			}
			webSocket.send(id +" 2 "+"/ban " + text);
			document.getElementById('banWord').style.display = "none";
			banList();
		}
		
		function delBanWord(){
			if(chkWebSocket()==false) return;
			if(mbr_id==null || mbr_id==""){
				messages4.innerHTML = "해제할 금칙어를 선택하세요.";
				return;
			}
			webSocket.send(id +" 2 "+"/ban " + mbr_id);
			banList();
		}
		
		function makeRoom(cd){
			if(chkWebSocket()==false) return;
			if(cd==1){
				var roomNm = document.getElementById("roomNm").value;
				var fixNo = document.getElementById("fixNo").value;
				if(roomNm==null || roomNm.trim(" ")==""){
					messages4.innerHTML = "방이름을 정하세요.";
					return;
				}
				webSocket.send(id +" 2 "+ "/make " + roomNm.trim(" ") + " " + (fixNo=="" ? 20:fixNo));
			}else{
				var roomNm = document.getElementById("roomNm2").value;
				var fixNo = document.getElementById("fixNo2").value;
				var passwd = document.getElementById("pw").value;
				var pw_chk = document.getElementById("pw_chk").value;
				if(roomNm==null || roomNm.trim(" ")==""){
					messages4.innerHTML = "방이름을 정하세요.";
					return;
				}else if(passwd != pw_chk){ 
					messages4.innerHTML = "비밀번호가 일치하지 않습니다.";
					return;
				}
				webSocket.send(id +" 2 "+ "/make " + roomNm.trim(" ") + " " + (fixNo=="" ? 20:fixNo) + " " + passwd);
			}
			roomList(room_cd);
			myRoomList();
			messages5.innerHTML = id + " (" + roomNm + " 방)";
			document.getElementById(cd==1 ? 'makeRoom1':'makeRoom2').style.display ="none";
			room_no=999;
		}
		
		function enterRoom(){
			if(chkWebSocket()==false) return;
			var passwd = document.getElementById("pw1").value;
			if(passwd==null || passwd.trim(" ")==""){
				messages4.innerHTML = "비밀번호를 확인 하세요.";
				return;
			}
			room_no = room_no.substring(0, room_no.length-1);
			messages4.innerHTML = "";
			webSocket.send(id +" 2 " + "/cd " + room_no + " " + passwd);
			messages5.innerHTML = id + " (" + roomNm + " 방)";
			inputPw.style.display ="none";
			myRoomList();
			waitList();
		}
		
		function setTalkToOne(clickNo, cd){
			if(chkWebSocket()==false) return;
			waitList();
			if(cd==0 && (mbr_id==null || mbr_id=="")) return;
			if(clickNo != click_no){
				if(clickNo==2){
					messages4.innerHTML = "대기실에서 귓속말할 사람을 선택하세요";
				}else{
					messages4.innerHTML = "대화방에서 귓속말할 사람을 선택하세요";
				}
				return;
			}
			if(cd==0){
				talkTo = cd;
				messages4.innerHTML = (mbr_id + "에 대한 귓속말을 해제합니다.");
				return;
			}else if(cd==1 && (mbr_id==null || mbr_id=="")){
				messages4.innerHTML = "귓속말할 사람을 선택하세요.";
				return;
			}
			messages4.innerHTML = ("귓속말 대상: " + mbr_id);
			talkTo = cd;
		}
		
		function clears(){
			messages2.innerHTML = "";
			messages4.innerHTML = "";
			messages6.innerHTML = "";
			wailList();
			myRoomList();
		}
		
		function closeSocket() {
			webSocket.close();
		}
		
		function writeResponse(text){
			var str = text.split(" ");
			var msg = text.substr(str[0].length+1, text.length);
			//alert(text);
			
			if(str[0] == "0"){
				messages4.innerHTML = msg;
				messages5.innerHTML = id + " (대기실)";
				webSocket.send(id +" 2 "+ " 님이 입장하셨습니다.");
			}
			else if(str[0] == "1")
				messages1.innerHTML += "<br>" + msg;
			else if(str[0] == "2"){
				
				if(messages2.innerHTML != '')
					messages6.innerHTML = "> " + messages2.innerHTML + "<br>" + messages6.innerHTML;
				messages2.innerHTML = msg;
			}
			else if(str[0] == "3")
				messages3.innerHTML += "<br>" + msg;
			else if(str[0] == "4" || str[0] == "9")
				messages4.innerHTML = msg;
			else if(str[0] == "6")
				messages6.innerHTML = "> " + msg + "<br>" + messages6.innerHTML;
			else if(str[0].substring(0,2) == "yn"){
				if(str[0].substring(2,3)=="1"){
						messages5.innerHTML = id + "( " + str[3] + " 방)";
						myRoomList();}
				messages4.innerHTML = msg;
				replyYN.style.display ="block";		
			}
		}
		
		function callName(){
			if(chkWebSocket()==false) return;
			if(room_no==0){
				messages4.innerHTML = "대기실입니다. 대화방에서만 초대할 수 있습니다.";
				return;
			}
			else if(click_no != 2){
				messages4.innerHTML = "대기실에 있는 사람을 선택하세요.";
				return;
			}
			else if(mbr_id =="" || mbr_id == null || id==mbr_id){
				messages4.innerHTML = "초대할 사람을 선택하세요.";
				return;
			}
			webSocket.send(id +" 2 " + "/call " + mbr_id);
			messages4.innerHTML = "";
			myRoomList();
		}
		
		function deleteRoom(){
			if(chkWebSocket()==false) return;
			webSocket.send(id +" 2 " + "/del");
			roomList(room_cd);
			myRoomList();
		    room_no=0;
		}
		
		function showList() {
			if(chkWebSocket()==false) return;
			mbr_id = null;
			messages1.innerHTML = "";
			webSocket.send(id +" 1 " + "/list");
		}
		
		function waitList() {
			if(chkWebSocket()==false) return;
			mbr_id = null;
			messages1.innerHTML = "";
			webSocket.send(id +" 1 " + "/lsw");
		}
		
		function banList() {
			if(chkWebSocket()==false) return;
			mbr_id = null;
			messages1.innerHTML = "";
			webSocket.send(id +" 1 " + "/bls");
		}
		
		function exitRoom() {
			if(chkWebSocket()==false) return;
			webSocket.send(id +" 2 " + "/exit");
			myRoomList();
			roomList(room_cd);
			messages5.innerHTML = id + " (대기실)";
			room_no=0;
		}
		
		function roomList(cd) {
			if(chkWebSocket()==false) return;
			mbr_id = null;
			room_cd = cd;
			webSocket.send(id +" 1 " + "/rls " + room_cd);
			messages1.innerHTML = "";
		}
		
		function myRoomList() {
			if(chkWebSocket()==false) return;
			mbr_id = null;
			webSocket.send(id +" 3 " + "/my");
			messages3.innerHTML = "";
		}
		
		function disPasswd(){
			if(chkWebSocket()==false) return;
			inputPw.style.display ="none";	
		}
		
		function fclick(bcd, hid){
			click_no = bcd;
			
			if(bcd==3){
				room_no = hid;
				
				if(isNaN(hid)) {
					inputPw.style.display ="block";	
					messages4.innerHTML="비공개방 입니다. 비밀번호가 필요합니다.";
					return;
				 }
				
				webSocket.send(id +" 2 " + "/cd " + hid);
				messages5.innerHTML = id + " (" + hid + "번방)";
				myRoomList();
				roomList(room_cd);
			}
			else{  
				mbr_id = hid;
				messages4.innerHTML = "선택: " + hid;
			}	
		}
		
		function chkWebSocket(){
			if(webSocket==undefined || webSocket.readyState == WebSocket.CLOSED) {
				alert("WebSocket is not opened or closed.");
				return false;}
			else true;
		}
		
		function logOut(){
			if(logType == 1)	closeSocket();
			window.location="login.jsp";
		}
	</script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.14.3/umd/popper.min.js" integrity="sha384-ZMP7rVo3mIykV+2+9J3UJ46jBk0WLaUAdn689aCwoqbBJiSnjAK/l8WvCWPIPm49" crossorigin="anonymous"></script>
<script src="https://stackpath.bootstrapcdn.com/bootstrap/4.1.3/js/bootstrap.min.js" integrity="sha384-ChfqqxuZUCnJSK3+MXmPNIyE6ZbWh2IMqE241rYiqJxyMiZ6OW/JmZQ5stwEULTy" crossorigin="anonymous"></script>
</body>
</html>