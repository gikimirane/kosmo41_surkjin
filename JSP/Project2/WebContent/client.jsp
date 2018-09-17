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
        <a class="nav-link" href="list.do?page=<%= session.getAttribute("curPage") %>&board=게시판&search=0&word=0">게시판</a>
      </li>
      <li class="nav-item">
        <a class="nav-link" href="list.do?page=<%= session.getAttribute("curPage") %>&board=공지사항&search=0&word=0">공지사항</a>
      </li>
       <li class="nav-item">
        <a class="nav-link" href="list.do?page=<%= session.getAttribute("curPage") %>&&board=QnA&search=0&word=0">Q&A</a>
      </li>
    </ul>
    <a class="nav-link" href="modify_member.jsp">회원관리</a>
     <button class="btn btn-outline-success my-2 my-sm-0" onclick="javascript:logOut();">로그아웃</button>
  </div>
</nav>
	<div class="row">
	<div class="col-4">
	<div style="background:#F9FAF5"><p>
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
		<span> 방만듬 </span>&nbsp; &nbsp;
		<a href="#" onclick="makeRooms(1,1);">공개방</a>&nbsp; &nbsp;
		<a href="#" onclick="makeRooms(2,1);">비공개방</a>
		</div>
		<div>
		<span> 귓속말 </span>&nbsp; &nbsp;
		<a href="#" onclick="onoffCall(1,1);">설정</a>&nbsp; &nbsp;
		<a href="#" onclick="onoffCall(1,0);">해제</a>
		</div>
		<div>
		<span> 금칙어 </span>&nbsp; &nbsp;
		<a href="#" onclick="banList();">보기</a>&nbsp; &nbsp;
		<a href="#" onclick="setBan(1);">설정</a>&nbsp; &nbsp;
		<a href="#" onclick="setBan(2);">해제</a>
		</div>
		<div>
		<span> 차단자 </span>&nbsp; &nbsp;
		<a href="#" onclick="blockList(1);">보기</a>&nbsp; &nbsp;
		<a href="#" onclick="setBlocks(1);">설정</a>&nbsp; &nbsp;
		<a href="#" onclick="setBlocks(2);">해제</a>
		</div>
		<div>
		<span> 게임쓰</span>&nbsp; &nbsp;
		<a href="#" onclick="playGame(1,3);">야구(1인)</a>&nbsp; 
		<a href="#" onclick="gamesIn(1,1,1);">야구(2인)</a>&nbsp; 
		<a href="#" onclick="gamesIn(1,2,1);">TicTacToe</a>
		</div>
		<hr>
	</div><p>
	<div id="selected1"></div>
	<div >
	
	<div id="makeRoom1" style="display:none" >
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
	</div><hr>
	</div>
	
	<div id="banWord" style="display:none">
	<div id="banTitle">금칙어 설정/해제</div>
	<div class="input-group mb-3">
	<div class="input-group-prepend">
	<span class="input-group-text" id="basic-addon1">금칙어</span>
	</div>
	<input id="ban" type="text" class="form-control"  name="ban"  placeholder="단어를 입력하세요">
	</div>
	 <div align="center">
		<input type="button" class="btn btn-primary" value="실행" onclick="banWord();"/> &nbsp; &nbsp;
		<input type="button" class="btn btn-secondary" value="취소" onclick="setBan(0);"/> 
	</div><hr>
	</div>
	
	<div id="setBlock" style="display:none">
	<div id="blkTitle">대화차단자 설정/해제</div>
	<div class="input-group mb-3">
	<div class="input-group-prepend">
	<span class="input-group-text" id="basic-addon1">차단자</span>
	</div>
	<input id="setblk" type="text" class="form-control"  name="setblk"  placeholder="대상자를 입력/선택 하세요">
	</div>
	 <div align="center">
		<input type="button" class="btn btn-primary" value="실행" onclick="blocking();"/> &nbsp; &nbsp;
		<input type="button" class="btn btn-secondary" value="취소" onclick="setBlocks(0);"/> 
	</div><hr>
	</div>
	
	<div id="callto1" style="display:none">
	<div id="onoff1">귀속말 설정</div>
	<div class="input-group mb-3">
	<div class="input-group-prepend">
	</div>
	<input id="call1" type="text" class="form-control"  name="call1"  placeholder="대기실내에서 선택 또는 입력하세요">
	</div>
	 <div align="center">
		<input type="button" class="btn btn-primary" value="실행" onclick="setTalkToOne(1,talkOn)"/> &nbsp; &nbsp;
		<input type="button" class="btn btn-secondary" value="취소" onclick="offcall(1, talkOn);"/> 
	</div><hr>
	</div>
	
	<div id="inputPw" style="display:none">
	<div>비공개방 비밀번호 입력</div>
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
	<div id="games1" style="display:none">
	<div>상대방 선택</div>
	<div class="input-group mb-3">
	<input id="gamMan1" type="text" class="form-control"  name="gamMan1"  placeholder="대상자를 입력/선택 하세요">
	</div>
	 <div align="center">
		<input type="button" class="btn btn-primary" value="초대" onclick="playGame(1,gam_no)"/> &nbsp; &nbsp;
		<input type="button" class="btn btn-secondary" value="취소" onclick="gamesIn(1,0,0);"/> 
	</div><hr>
	</div>
	<div id="messages1"> </div>
	</div>
	
	<div class="col-4" >
	<p>
	<div id="headTitle">
		<%= id %> 님 안녕하세요.
	</div><p>
	<div>
  	<input type="text" id="messageinput" class="form-control" />
	</div><p>
	<div>
		<button type="button" class="btn btn-secondary" onclick="openSocket();">Open</button>
		<button type="button" class="btn btn-primary" onclick="send();">Send</button>
		<button type="button" class="btn btn-info" onclick="sendAll();">notice</button>
		<button type="button" class="btn btn-success" onclick="javacript:clears();">Clear</button>
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
	<div style="background:#F9FAF5"><p>
	<div>
		<span> 리스트 </span>&nbsp; &nbsp;
		<a href="#" onclick="myRoomList();">내방리스트</a>
	</div>
		<div>
		<span> 귓속말 </span>&nbsp; &nbsp;
		<a href="#" onclick="onoffCall(2,1);">설정</a>&nbsp; &nbsp;
		<a href="#" onclick="onoffCall(2,0);">해제</a>
		</div>
		<div>
		<span> 방관련 </span>&nbsp; &nbsp;
		<a href="#" onclick="onoffDiv(1,1);">초대</a>&nbsp; &nbsp;
		<a href="#" onclick="onoffDiv(2,1);">강퇴</a>&nbsp; &nbsp;
		<a href="#" onclick="onoffDiv(3,1);">방장승계</a>&nbsp; &nbsp;
		<a href="#" onclick="onoffDiv(4,1);">방폭파</a>
		</div>
		<div>
		<span> 차단자 </span>&nbsp; &nbsp;
		<a href="#" onclick="blockList(3);">보기</a>&nbsp; &nbsp;
		<a href="#" onclick="setBlocks1(1);">설정</a>&nbsp; &nbsp;
		<a href="#" onclick="setBlocks1(2);">해제</a>
		</div>
		<div>
		<span> 게임쓰 </span>&nbsp; &nbsp;
		<a href="#" onclick="playGame(2,3);">야구(1인)</a>&nbsp;
		<a href="#" onclick="gamesIn(2,1,1);">야구(2인)</a>&nbsp;
		<a href="#" onclick="gamesIn(2,2,1);">TicTacToe</a>
		</div><p> 
		<div>
		<span> 나가기 </span>&nbsp; &nbsp;
		<a href="#" onclick="exitRoom();">방나가기</a>
		</div>
		<hr>
	</div>
	<div id="selected2"></div>
	<hr>
	<div id="callto2" style="display:none">
	<div id="onoff2">귀속말 설정</div>
	<div class="input-group mb-3">
	<div class="input-group-prepend">
	</div>
	<input id="call2" type="text" class="form-control"  name="call2"  placeholder="대화방내에서 선택 또는 입력하세요">
	</div>
	 <div align="center">
		<input type="button" class="btn btn-primary" value="실행" onclick="setTalkToOne(2,talkOn)"/> &nbsp; &nbsp;
		<input type="button" class="btn btn-secondary" value="취소" onclick="offcall(2, talkOn);"/> 
	</div>
	<hr>
	</div>
	<div id="callName" style="display:none">
	<div >대화방 초대</div>
	<div class="input-group mb-3">
	<div class="input-group-prepend">
	</div>
	<input id="callMan" type="text" class="form-control"  name="callMan"  placeholder="대기실내에서 선택 또는 입력하세요">
	</div>
	 <div align="center">
		<input type="button" class="btn btn-primary" value="초대" onclick="callName();"/> &nbsp; &nbsp;
		<input type="button" class="btn btn-secondary" value="취소" onclick="onoffDiv(1,0);"/> 
	</div>
	<hr>
	</div>
	<div id="outRoom" style="display:none">
	<div >대화방 강퇴</div>
	<div class="input-group mb-3">
	<div class="input-group-prepend">
	</div>
	<input id="outMan" type="text" class="form-control"  name="outMan"  placeholder="대화방내에서 선택 또는 입력하세요">
	</div>
	 <div align="center">
		<input type="button" class="btn btn-warning" value="강퇴" onclick="outRoom();"/> &nbsp; &nbsp;
		<input type="button" class="btn btn-secondary" value="취소" onclick="onoffDiv(2,0);"/> 
	</div>
	<hr>
	</div>
	<div id="changeCap" style="display:none">
	<div >방장 승계</div>
	<div class="input-group mb-3">
	<div class="input-group-prepend">
	</div>
	<input id="changeMan" type="text" class="form-control"  name="changeMan"  placeholder="대화방내에서 선택 또는 입력하세요">
	</div>
	 <div align="center">
		<input type="button" class="btn btn-primary" value="승계" onclick="changeCap();"/> &nbsp; &nbsp;
		<input type="button" class="btn btn-secondary" value="취소" onclick="onoffDiv(3,0);"/> 
	</div>
	<hr>
	</div>
	<div id="setBlock1" style="display:none">
	<div id="blkTitle1">대화차단자 설정/해제</div>
	<div class="input-group mb-3">
	<div class="input-group-prepend">
	<span class="input-group-text" id="basic-addon1">차단자</span>
	</div>
	<input id="setblk1" type="text" class="form-control"  name="setblk1"  placeholder="대상자를 입력/선택 하세요">
	</div>
	 <div align="center">
		<input type="button" class="btn btn-primary" value="실행" onclick="blocking1();"/> &nbsp; &nbsp;
		<input type="button" class="btn btn-secondary" value="취소" onclick="setBlocks1(0);"/> 
	</div><hr>
	</div>
	<div id="delRoom" style="display:none">

	<div id="delrm" class="input-group mb-3">
	 폭파하시겠습니까?</div>
	 <div align="center">
		<input type="button" class="btn btn-danger" value="폭파" onclick="deleteRoom();"/> &nbsp; &nbsp;
		<input type="button" class="btn btn-secondary" value="취소" onclick="onoffDiv(4,0);"/> 
	</div><hr>
	</div>
	<div id="games2" style="display:none">
	<div>상대방 선택</div>
	<div class="input-group mb-3">
	<input id="gamMan2" type="text" class="form-control"  name="gamMan2"  placeholder="대상자를 입력/선택 하세요">
	</div>
	 <div align="center">
		<input type="button" class="btn btn-primary" value="초대" onclick="playGame(2,gam_no)"/> &nbsp; &nbsp;
		<input type="button" class="btn btn-secondary" value="취소" onclick="gamesIn(2,0,0);"/> 
	</div><hr>
	</div>
	<div id="messages3"></div>
	</div>
	</div>
	</div>
	
	<!-- Script to utilise the WebSocket -->
	<script type="text/javascript">
		var webSocket;
		var room_no=0, room_cd=0, talkTo, logType=0, talkOn;
		var mbr_id, talk_id, click_no, roomOrder, gam_no=0;
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
				selected1.innerHTML = "";
				selected2.innerHTML = "";
				headTitle.innerHTML =  id + " 님 안녕하세요.";
			};
		}
		
		function send() {
			var text = document.getElementById("messageinput").value;
			if(text==null || text.trim(" ")=="")		return;
			if(talkTo==1) 	webSocket.send(id +" 2 "+"/to " + talk_id + " " + text);
			else 			webSocket.send(id +" 2 "+text);
			document.getElementById("messageinput").value = "";
		}
		
		function gamesIn(in_no, g_no, onoff){
			if(chkWebSocket()==false) return;
			document.getElementById(in_no==1 ? 'selected1':'selected2').innerHTML = "";
			if((in_no==1 && room_no > 0) || (in_no==2 && room_no==0)) return;
			gam_no = g_no;
			document.getElementById(in_no==1 ? 'games1':'games2').style.display = (onoff==0 ? "none":"block");
			if(onoff==1) waitList();
		}
		
		function playGame(g_cd, g_no){ 
			if(chkWebSocket()==false) return;
			if((g_cd==1 && room_no > 0) || (g_cd==2 && room_no==0)) return;
			mbr_id = document.getElementById(g_cd==1 ? 'gamMan1':'gamMan2').value;
			
			if(g_no==3)	webSocket.send(id + " 2 "+"/gam" + g_no);
			else if(mbr_id ==null || mbr_id=="" || mbr_id==id){
				if(g_cd==1){
					document.getElementById(g_cd==1 ? 'selected1':'selected2').innerHTML = "대기실에 있는 사람을 선택하세요.";
					return;
				 }else if(g_cd==2){
					document.getElementById(g_cd==1 ? 'selected1':'selected2').innerHTML = "같은 대화방에 있는 사람을 선택하세요.";
					return;
				}
			}else{	webSocket.send(id + " 2 "+"/gam" + g_no +" " + mbr_id);
					document.getElementById(g_cd==1 ? 'games1':'games2').style.display = "none";
			}
			document.getElementById(g_cd==1 ? 'selected1':'selected2').innerHTML = (g_no==2 ? "TicTacToe":"숫자야구") + " 게임 시작";
		}
		
		function replyYn(yn){
			webSocket.send(id +" 2 "+ (yn==1 ? "y":"n"));
			replyYN.style.display ="none";
			messages4.innerHTML = "";
			if(yn==1 && gam_no==0){
				myRoomList();
				room_no = 999;
			}
		}
		
		function makeRooms(rm, cd){
			if(chkWebSocket()==false) return;
			selected1.innerHTML = "";
			if(cd==1 && room_no > 0){
				selected1.innerHTML = "대기실에서만 대화방을 만들 수 있습니다.";
				return;
			}
			document.getElementById(rm==1 ? 'makeRoom1':'makeRoom2').style.display =(cd==1 ? "block":"none");
			messages4.innerHTML = "";
		}
		
		function banWord(){
			if(chkWebSocket()==false) return;
			var text = document.getElementById("ban").value;
			if(text.trim(" ")==""|| text==null){
				selected1.innerHTML = "단어를 입력/선택 하세요." ;
				return;
			}
			webSocket.send(id +" 1 "+"/ban " + text);
			document.getElementById('banWord').style.display = "none";
			banList();
		}
		
		function setBan(ban_cd){
			if(chkWebSocket()==false) return;
			selected1.innerHTML = "";
			banTitle.innerHTML = (ban_cd==1 ? "금칙어 설정":"금칙어 해제")
			document.getElementById('banWord').style.display = (ban_cd==0 ? "none":"block");	
		}
		
		function blocking(){
			if(chkWebSocket()==false) return;
			var mbr_id = document.getElementById("setblk").value;
			if(mbr_id =="" || mbr_id == null || id == mbr_id){
				selected1.innerHTML = "대상자를 선택/입력 하세요.";
				return;
			}
			webSocket.send(id +" 1 "+"/sblk " + mbr_id);
			document.getElementById('setBlock').style.display = "none";
			selected1.innerHTML = "";
			blockList(1);
		}
		
		function setBlocks(blk_cd){
			if(chkWebSocket()==false) return;
			selected1.innerHTML = "";
			blkTitle.innerHTML = (blk_cd==1 ? "대화차단자 설정":"대화차단자 해제")
			document.getElementById('setBlock').style.display = (blk_cd==0 ? "none":"block");
			blockList(1);
		}
		
		function blocking1(){
			if(chkWebSocket()==false) return;
			var mbr_id = document.getElementById("setblk1").value;
			if(mbr_id =="" || mbr_id == null || id == mbr_id){
				selected2.innerHTML = "대상자를 선택/입력 하세요.";
				return;
			}
			webSocket.send(id +" 3 "+"/sblk " + mbr_id);
			document.getElementById('setBlock1').style.display = "none";
			selected2.innerHTML = "";
			blockList(1);
		}
		
		function setBlocks1(blk_cd){
			if(chkWebSocket()==false) return;
			if(room_no ==0){ selected2.innerHTML = "대화방에서만 가능합니다.";
							 return;}
			selected2.innerHTML = "";
			blkTitle1.innerHTML = (blk_cd==1 ? "대화차단자 설정":"대화차단자 해제")
			document.getElementById('setBlock1').style.display = (blk_cd==0 ? "none":"block");
			blockList(1);
		}
		
		function changeCap() {
			if(chkWebSocket()==false) return;
			mbr_id = changeMan.value;
			if(room_no==0){
				selected2.innerHTML = "현재 대기실입니다.";
				return;
			}else if(mbr_id =="" || id==mbr_id){
				selected2.innerHTML = "방장시킬 사람을 선택하세요.";
				return;
			}else if(click_no != 4){
				selected2.innerHTML = "같은 대화방에 있는 사람을 선택하세요.";
				return;
			}
			webSocket.send(id +" 3 " + "/cap " + mbr_id);
			selected2.innerHTML = "";
			onoffDiv(roomOrder, 0);
			myRoomList();
		}
		
		function outRoom() {
			if(chkWebSocket()==false) return;
			mbr_id = outMan.value;
			if(room_no==0){
				selected2.innerHTML = "현재 대기실입니다.";
				return;
			}else if(mbr_id == "" || id == mbr_id){
				selected2.innerHTML = "강퇴시킬 사람을 선택하세요.";
				return;
			}else if(click_no != 4){
				selected2.innerHTML = "같은 대화방에 있는 사람을 선택하세요.";
				return;
			}
			webSocket.send(id +" 3 " + "/out " + mbr_id);
			onoffDiv(roomOrder, 0);
			selected2.innerHTML = "";
			myRoomList();
		}
		
		function sendAll() {
			if(chkWebSocket()==false) return;
			var text = document.getElementById("messageinput").value;
			if(text==null || text.trim(" ")=="")		return;
			webSocket.send(id +" 2 "+"/noti " + text);
			document.getElementById("messageinput").value = "";
		}
		
		function makeRoom(cd){
			if(chkWebSocket()==false) return;
			if(cd==1){
				var roomNm = document.getElementById("roomNm").value;
				var fixNo = document.getElementById("fixNo").value;
				if(roomNm==null || roomNm.trim(" ")==""){
					selected1.innerHTML = "방이름을 정하세요.";
					return;
				}
				webSocket.send(id +" 1 "+ "/make " + roomNm.trim(" ") + " " + (fixNo=="" ? 20:fixNo));
			}else{
				var roomNm = document.getElementById("roomNm2").value;
				var fixNo = document.getElementById("fixNo2").value;
				var passwd = document.getElementById("pw").value;
				var pw_chk = document.getElementById("pw_chk").value;
				if(roomNm==null || roomNm.trim(" ")==""){
					selected1.innerHTML = "방이름을 정하세요.";
					return;
				}else if(passwd != pw_chk){ 
					selected1.innerHTML = "비밀번호가 일치하지 않습니다.";
					return;
				}
				webSocket.send(id +" 1 "+ "/make " + roomNm.trim(" ") + " " + (fixNo=="" ? 20:fixNo) + " " + passwd);
			}
			roomList(room_cd);
			myRoomList();
			headTitle.innerHTML = id + " (" + roomNm + " 방)";
			document.getElementById(cd==1 ? 'makeRoom1':'makeRoom2').style.display ="none";
			selected1.innerHTML = "";
			room_no=999;
		}
		
		function onoffCall(call, onoff){ 
			if(chkWebSocket()==false) 	return;
			if(talkTo==onoff)			return;
			if(onoff==0 && (talkTo==null || talkTo == undefined)){
				document.getElementById(call==1 ? 'selected1':'selected2').innerHTML = "지금은 귓속말 상태가 아닙니다.";
				return;
			}
			if(call==1 && room_no != 0){
				selected2.innerHTML = "대화방입니다. 대기실에서만 가능합니다.";
				return;
			}
			if(call==2 && room_no == 0){
				selected1.innerHTML = "대기실입니다. 대화방에서만 가능합니다.";
				return;
			}
			if(talkTo==0){
				selected1.innerHTML = "";
			}
			document.getElementById(call==1 ? 'onoff1':'onoff2').innerHTML =(onoff==1 ? '귓속말 설정':'귓속말 해제');
			document.getElementById(call==1 ? 'callto1':'callto2').style.display ="block";
			document.getElementById(call==1 ? 'selected1':'selected2').innerHTML = "";
			roomOrder = 0;
			talkOn = onoff; 
		}
		
		function offcall(call, talks){
			if(talks==0) selected1.innerHTML = "";
			document.getElementById(call==1 ? 'callto1':'callto2').style.display ="none";
			document.getElementById(call==1 ? 'selected1':'selected2').innerHTML = "";
		}
		
		function onoffDiv(div, onoff){
			if(chkWebSocket()==false) return;
			roomOrder = div;
			if(room_no==0){		selected2.innerHTML = "현재 대기실입니다."; return;}
			if(div==1){			document.getElementById('callName').style.display =(onoff==1 ? "block":"none");
								waitList();
			}
			else if(div==2)		document.getElementById('outRoom').style.display =(onoff==1 ? "block":"none");
			else if(div==3)		document.getElementById('changeCap').style.display =(onoff==1 ? "block":"none");
			else if(div==4)		document.getElementById('delRoom').style.display =(onoff==1 ? "block":"none");
			selected2.innerHTML = "";
		}
		function enterRoom(){
			if(chkWebSocket()==false) return;
			var passwd = document.getElementById("pw1").value;
			if(passwd==null || passwd.trim(" ")==""){
				selected1.innerHTML = "비밀번호를 확인 하세요.";
				return;
			}
			
			webSocket.send(id +" 1 " + "/cd " + room_no + " " + passwd);
			headTitle.innerHTML = id + " (" + room_no + " 번방)";
			selected1.innerHTML = "";
			inputPw.style.display ="none";
			myRoomList();
			roomList(2);
		}
		
		function setTalkToOne(t_cd, onoff){
			if(chkWebSocket()==false) return;
			talk_id = (t_cd==1 ? call1.value : call2.value);
			if(talk_id=="" || talk_id==id){
				if(t_cd==1){
					selected1.innerHTML = "대기실에서 귓속말할 사람을 선택하세요";
				}else{
					selected2.innerHTML = "대화방에서 귓속말할 사람을 선택하세요";
				}
				return;
			}
						
			talkTo = onoff;
			document.getElementById(t_cd==1 ? 'selected1':'selected2').innerHTML = 
				talk_id + (onoff==1 ? "에 대한 귓속말을 설정했습니다.":"에 대한 귓속말을 해제했습니다.");	
			document.getElementById(t_cd==1 ? 'callto1':'callto2').style.display ="none";
		}
		
		function clears(){
			messages2.innerHTML = "";
			messages4.innerHTML = "";
			messages6.innerHTML = "";
			selected1.innerHTML = "";
			selected2.innerHTML = "";
			waitList();
			myRoomList();
		}
		
		function closeSocket() {
			webSocket.send(id +" 1 "+ " 님이 퇴장하셨습니다.");
			webSocket.send(id +" 1 "+ "/close");
			webSocket.close();
		}
		
		function writeResponse(text){
			
			var str = text.split(" ");
			var msg = text.substr(str[0].length+1, text.length);
			
			if(str[0] == "0"){ //open
				messages4.innerHTML = msg;
				headTitle.innerHTML = id + " (대기실)";
				webSocket.send(id +" 1 "+ " 님이 입장하셨습니다.");
				waitList();
			}
			else if(str[0] == "1") //대기실
				selected1.innerHTML = msg;
			else if(str[0] == "1L") //대기실
					messages1.innerHTML += msg + "<br>";
			else if(str[0] == "2" || str[0] == "7" || str[0] == "8"){ //채팅 //7:입장퇴장 8:방생성
				if(messages2.innerHTML != '')
					messages6.innerHTML = messages2.innerHTML + "<br>" + messages6.innerHTML;
				messages2.innerHTML = msg;
				if(str[0] == "7") waitList();
				if(str[0] == "8"){
					messages1.innerHTML = "";
					roomList(0);
				}
			}
			else if(str[0] == "3") //대화방
				selected2.innerHTML = msg;
			else if(str[0] == "3L") //대화방
				messages3.innerHTML += "<br>" + msg;
			else if(str[0] == "4")
				messages4.innerHTML = msg;
			else if(str[0] == "6") //내정보
				messages6.innerHTML = msg + "<br>" + messages6.innerHTML;
			else if(str[0].substring(0,2) == "yn"){
				gam_no = str[0].substring(2,3);
				if(gam_no =="1"){ //방초대
						headTitle.innerHTML = id + "( " + str[3] + " 방)";
						myRoomList();}
				messages4.innerHTML = msg;
				replyYN.style.display ="block";		
			}else if(str[0] == "out"){
				headTitle.innerHTML = id + "(대기실)";
				messages2.innerHTML = msg;
				room_no = 0;
				myRoomList();
			}
		}
		
		function callName(){
			if(chkWebSocket()==false) return;
			mbr_id = callMan.value;
			if(room_no==0){
				selected2.innerHTML = "대기실입니다. 대화방에서만 초대할 수 있습니다.";
				return;
			}
			else if(click_no != 2){
				selected2.innerHTML = "대기실에 있는 사람을 선택하세요.";
				return;
			}
			else if(mbr_id =="" || mbr_id == null || id == mbr_id){
				selected2.innerHTML = "초대할 사람을 선택하세요.";
				return;
			}
			onoffDiv(roomOrder, 0);
			webSocket.send(id +" 3 " + "/call " + mbr_id);
			room_no = 999;
			gam_no = 0;
			myRoomList();
		}
		
		function deleteRoom(){
			if(chkWebSocket()==false) return;
			selected2.innerHTML = "";
			webSocket.send(id +" 3 " + "/del");
			document.getElementById('delRoom').style.display ="none";
			roomList(room_cd);
			myRoomList();
			room_no = 0;
			headTitle.innerHTML = id + " (대기실)";
		}
		
		function showList() {
			if(chkWebSocket()==false) return;
			selected1.innerHTML = "";
			messages1.innerHTML = "";
			webSocket.send(id +" 1L " + "/list");
		}
		
		function waitList() {
			if(chkWebSocket()==false) return;
			selected1.innerHTML = "";
			messages1.innerHTML = "";
			webSocket.send(id +" 1L " + "/lsw");
		}
		
		function banList() {
			if(chkWebSocket()==false) return;
			selected1.innerHTML = "";
			messages1.innerHTML = "";
			webSocket.send(id +" 1L " + "/bls");
		}
		
		function blockList(b_cd) {
			if(chkWebSocket()==false) return;
			selected1.innerHTML = "";
			messages1.innerHTML = "";
			webSocket.send(id +" " +b_cd+"L" + " " + "/blk");
		}
		
		function exitRoom() {
			if(chkWebSocket()==false || room_no==0) return;
			selected2.innerHTML = "";
			webSocket.send(id +" 3 " + "/exit");
			myRoomList();
			roomList(room_cd);
			headTitle.innerHTML = id + " (대기실)";
			room_no=0;
		}
		
		function roomList(cd) {
			if(chkWebSocket()==false) return;
			room_cd = cd;
			selected1.innerHTML = "";
			messages1.innerHTML = "";
			webSocket.send(id +" 1L " + "/rls " + room_cd);
		}
		
		function myRoomList() {
			if(chkWebSocket()==false) return;
			selected2.innerHTML = "";
			messages3.innerHTML = "";
			webSocket.send(id +" 3L " + "/my");
		}
		
		function disPasswd(){
			if(chkWebSocket()==false) return;
			selected1.innerHTML = "";
			inputPw.style.display ="none";	
		}
		
		function fclick(bcd, cid){
			click_no = bcd;
			
			if(bcd==2){ 	
				if(roomOrder== 1)	callMan.value = cid;
				else 				call1.value = cid;
				setblk.value = cid;
				gamMan1.value = cid;
			}
			if(bcd==3){
				
				if(isNaN(cid)) {
					inputPw.style.display ="block";	
					selected1.innerHTML="비공개방 입니다. 비밀번호가 필요합니다.";
					room_no = cid.substring(0, cid.length-1);
					return;
				 }
				room_no = cid;
				webSocket.send(id +" 1 " + "/cd " + cid);
				headTitle.innerHTML = id + " (" + cid + "번방)";
				myRoomList();
				roomList(room_cd);
			}
			else if(bcd==4){
				if(roomOrder == 0) 		call2.value = cid;
				else if(roomOrder ==1)  callMan.value = cid;
				else if(roomOrder ==2)  outMan.value = cid;
				else if(roomOrder ==3)  changeMan.value = cid;
				gamMan2.value = cid;
			}else if(bcd==5) 			ban.value = cid;
			 else if(bcd==6) 			setblk.value = cid;
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