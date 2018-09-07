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
	<div class="row">
	<div class="col-3">
	<div>
		<button type="button" onclick="showList();">접속자</button>
		<button type="button" onclick="waitList();">대기자</button>
		<button type="button" onclick="roomList(0);">전체대화방</button>
		<button type="button" onclick="roomList(1);">공개방</button>
		<button type="button" onclick="roomList(2);">비공개방</button>
		<button type="button" onclick="setTalkToOne(2,1);">귓속말 설정</button>
		<button type="button" onclick="setTalkToOne(2,0);">귓속말 해제</button>
		<button type="button" onclick="makeRooms(1,1);">공개방만들기</button>
		<button type="button" onclick="makeRooms(2,1);">비공개방만들기</button>
		<button type="button" onclick="banList();">금칙어 보기</button>
		<button type="button" onclick="disBan(1);">금칙어 설정</button>
		<button type="button" onclick="delBanWord();">금칙어 해제</button>
		<button type="button" onclick="playGame(0,1)">숫자야구게임</button>
		<button type="button" onclick="playGame(0,2)">TicTacToe</button>
	</div>
	<div >
	<div id="makeRoom1" style="display:none" >
	<hr>
	<div>
	방이름 : <input id="roomNm" type="text" name="roomNm"><br>
	정원 : <input id="fixNo" type="text" name="fixNo" value="20"><br><p><p>
		<input type="button" value="확인" onclick="makeRoom(1);"/> &nbsp; &nbsp;
		<input type="button" value="종료"  onclick="makeRooms(1, 0);"/> 
	</div>
	</div>
	<hr>
	 </div>
	<div id="makeRoom2" style="display:none" >
	<div>
	방이름 : <input id="roomNm2" type="text" name="roomNm2"><br>
	정원 : <input id="fixNo2" type="text" name="fixNo2" value="20"><br><p>
	비밀번호 : <input id="pw" type="password" name="pw"><br>
	비밀번호 확인 : <input id="pw_chk" type="password" name="pw_chk"><br><p>
		<input type="button" value="확인" onclick="makeRoom(2);"/> &nbsp; &nbsp;
		<input type="button" value="종료" onclick="makeRooms(2, 0);"/> 
	</div>
	<hr>
	</div>
	<div id="banWord" style="display:none">
	<div>
	금칙어 : <input id="ban" type="text" name="ban"><br><p>
		<input type="button" value="확인" onclick="banWord();"/> &nbsp; &nbsp;
		<input type="button" value="종료" onclick="disBan(0);"/> 
	</div>
	<hr>
	</div>
	<div id="inputPw" style="display:none">
	<div>
	비밀번호 : <input id="pw1" type="password" name="password"><br><p>
		<input type="button" value="확인" onclick="enterRoom();"/> &nbsp; &nbsp;
		<input type="button" value="종료" onclick="disPasswd();"/> 
	</div>
	</div>
	<div id="messages1"> </div>
	</div>
	
	<div class="col-6">
	<div>
		사용자 아이디 : <%= id %>
	</div>
	<div>
		<input type ="text" id="messageinput" size="38"/>
	</div>
	<div>
		<button type="button" onclick="openSocket();">Open</button>
		<button type="button" onclick="send();">Send</button>
		<button type="button" onclick="sendAll();">전체공지</button>
		<button type="button" onclick="closeSocket();">Close</button>
		<button type="button" onclick="javacript:clears();">Clear</button>
	</div>
	
	<div id="messages4"></div>
	<div id="replyYN" style="display:none">
	<hr>
		<input type="button" value="수락(Y)" onclick="replyYn(1);"/> &nbsp; &nbsp;
		<input type="button" value="거부(N)" onclick="replyYn(0);"/> 
	<hr>
	</div>
	<!--  Server responses get written here -->
	<div id="messages2"></div>
	</div>
	<div class="col-3">
	<div>
		<button type="button" onclick="myRoomList();">내방보기</button>
		<button type="button" onclick="callName();">초대</button>
		<button type="button" onclick="setTalkToOne(4,1)">귓속말 설정</button>
		<button type="button" onclick="setTalkToOne(4,0)">귓속말 해제</button>
		<button type="button" onclick="outRoom();">강퇴</button>
		<button type="button" onclick="changeCap();">방장승계</button>
		<button type="button" onclick="deleteRoom();">방폭파</button>
		<button type="button" onclick="playGame(1,1)">숫자야구게임</button>
		<button type="button" onclick="playGame(1,2)">TicTacToe</button>
		<button type="button" onclick="exitRoom();">방나가기</button>
	</div>
	<div id="messages3"></div>
	</div>
	</div>
	</div>
	
	<!-- Script to utilise the WebSocket -->
	<script type="text/javascript">
		var webSocket;
		var room_no=0, room_cd=0, talkTo=0;
		var mbr_id, game_cd, click_no;
		var id = "<%= id %>";
		
		function openSocket(){
			if(webSocket != undefined && webSocket.readyState != WebSocket.CLOSED) {
				writeResponse("2 WebSocket is already opened.");
				return;
			}
			
			webSocket = new WebSocket("ws://localhost:8081/WebSocketEx/websocketendpoint2");
			
			webSocket.onopen = function(event) {
				if (event.data == undefined)
					return ;
				
				writeResponse(event.data);
			};
			
			webSocket.onmessage = function(event) {
				writeResponse(event.data);
			};
			
			webSocket.onclose = function(event) {
				writeResponse("2 connection closed");
			};
		}
		
		function send() {
			var text = document.getElementById("messageinput").value;
			if(text=="" || text.length==0)		return;
			if(talkTo==1) webSocket.send(id +" 2 "+"/to " + mbr_id + " " + text);
			webSocket.send(id +" 2 "+text);
		}
		
		function playGame(g_cd, g_no){
			game_cd = g_cd;
			
			if(mbr_id =="" || mbr_id == null || mbr_id.length==0){
				var msg="게임을 같이 할 사람을 선택하세요.";
				messages4.innerHTML += "<br>" + msg;
				return;
			}else if(room_no==0 && click_no != 2){
				var msg="대기실에 있는 사람을 선택하세요.";
				messages4.innerHTML += "<br>" + msg;
				return;
			}else if(room_no > 0 && click_no != 4){
				var msg="같은 대화방에 있는 사람을 선택하세요.";
				messages4.innerHTML += "<br>" + msg;
				return;
			}
			webSocket.send(id +" 2 "+"/gam" + g_no +" " + mbr_id);
		}
		
		function replyYn(yn){
			webSocket.send(id +" 2 "+ (yn==1 ? "y":"n"));
			replyYN.style.display ="none";		
		}
		
		function makeRooms(rm, cd){
			document.getElementById(rm==1 ? 'makeRoom1':'makeRoom2').style.display =(cd==1 ? "block":"none");		
		}
		
		function disBan(cd){
			document.getElementById('banWord').style.display =(cd==1 ? "block":"none");		
		}
		
		function changeCap() {
			myRoomList();
			if(room_no==0){
				var msg="현재 대기실입니다.";
				messages2.innerHTML += "<br>" + msg;
				return;
			}
			else if(click_no != 4){
				var msg="같은 대화방에 있는 사람을 선택하세요.";
				messages2.innerHTML += "<br>" + msg;
				return;
			}
			else if(mbr_id =="" || mbr_id == null || id==mbr_id){
				var msg="방장시킬 사람을 선택하세요.";
				messages2.innerHTML += "<br>" + msg;
				return;
			}
			webSocket.send(id +" 2 " + "/cap " + mbr_id);
			messages3.innerHTML = "";
			myRoomList();
		}
		
		function outRoom() {
			myRoomList();
			if(room_no==0){
				var msg="현재 대기실입니다.";
				messages2.innerHTML += "<br>" + msg;
				return;
			}
			else if(click_no != 4){
				var msg="같은 대화방에 있는 사람을 선택하세요.";
				messages2.innerHTML += "<br>" + msg;
				return;
			}
			else if(mbr_id =="" || mbr_id == null || id==mbr_id){
				var msg="강퇴시킬 사람을 선택하세요.";
				messages2.innerHTML += "<br>" + msg;
				return;
			}
			webSocket.send(id +" 2 " + "/out " + mbr_id);
			messages3.innerHTML = "";
			myRoomList();
		}
		
		function sendAll() {
			var text = document.getElementById("messageinput").value;
			if(text=="" || text.length==0)		return;
			webSocket.send(id +" 2 "+"/noti " + text);
		}
		
		function banWord(){
			var text = document.getElementById("ban").value;
			if(text=="" || text.length==0)		return;
			webSocket.send(id +" 2 "+"/ban " + text);
		}
		
		function delBanWord(){
			if(mbr_id==null || mbr_id=="" || mbr_id.length==0){
				var msg= "해제할 금칙어를 선택하세요.";
				messages2.innerHTML += "<br>" + msg;
				return;
			}
			webSocket.send(id +" 2 "+"/ban " + mbr_id);
			banList();
		}
		
		function makeRoom(cd){
			if(cd==1){
				var roomNm = document.getElementById("roomNm").value;
				var fixNo = document.getElementById("fixNo").value;
				if(roomNm==null || roomNm=="" || roomNm.length==0){
					var msg= "방이름을 정하세요.";
					messages2.innerHTML += "<br>" + msg;
					return;
				}
				webSocket.send(id +" 2 "+ "/make " + roomNm + " " + fixNo);
			}else{
				var roomNm = document.getElementById("roomNm2").value;
				var fixNo = document.getElementById("fixNo2").value;
				var passwd = document.getElementById("pw").value;
				var pw_chk = document.getElementById("pw_chk").value;
				if(roomNm==null || roomNm=="" || roomNm.length==0){
					var msg= "방이름을 정하세요.";
					messages2.innerHTML += "<br>" + msg;
					return;
				}else if(passwd != pw_chk){
					var msg= "비밀번호가 일치하지 않습니다.";
					messages2.innerHTML += "<br>" + msg;
					return;
				}
				webSocket.send(id +" 2 "+ "/make " + roomNm + " " + fixNo + " " + passwd);
			}
			roomList(room_cd);
			myRoomList();
			room_no=999;
		}
		
		function enterRoom(){
			var passwd = document.getElementById("pw1").value;
			if(passwd==null || passwd=="" || passwd.length==0 ){
				var msg= "비밀번호를 입력하세요.";
				messages2.innerHTML += "<br>" + msg;
				return;
			}
			room_no = room_no.substring(0, room_no.length-1);
			
			webSocket.send(id +" 2 " + "/cd " + room_no + " " + passwd);
			myRoomList();
		}
		
		function setTalkToOne(clickNo, cd){
			waitList();
			if(cd==0 && (mbr_id==null || mbr_id=="")) return;
			if(clickNo != click_no){
				if(clickNo==2){
					var msg= "대기실에서 귓속말할 사람을 선택하세요";
					messages2.innerHTML += "<br>" + msg;
				}else{
					var msg= "대화방에서 귓속말할 사람을 선택하세요";
					messages2.innerHTML += "<br>" + msg;
				}
				return;
			}
			if(cd==0){
				talkTo = cd;
				var msg= mbr_id + "에 대한 귓속말을 해제합니다.";
				messages2.innerHTML += "<br>" + msg;
				return;
			}else if(cd==1 && (mbr_id==null || mbr_id=="")){
				var msg="귓속말할 사람을 선택하세요.";
				messages2.innerHTML += "<br>" + msg;
				return;
			}
			var msg="귓속말 대상: " + mbr_id;
			messages2.innerHTML += "<br>" + msg;
			talkTo = cd;
		}
		
		function clears(){
			messages2.innerHTML = "";
			messages4.innerHTML = "";
		}
		
		function closeSocket() {
			webSocket.close();
		}
		
		function writeResponse(text){
			var str = text.split(" ");
			var msg = text.substr(str[0].length+1, text.length);
			
			if(str[0] == "1")
				messages1.innerHTML += "<br>" + msg;
			else if(str[0] == "2")
				messages2.innerHTML += "<br>" + msg;
			else if(str[0] == "3")
				messages3.innerHTML += "<br>" + msg;
			else if(str[0] == "4" || str[0] == "9")
					messages4.innerHTML = "<br>" + msg;
			else if(str[0] == "yn"){
				messages4.innerHTML = "<br>" + msg;
				replyYN.style.display ="block";		
			}
		}
		
		function callName(){
			if(room_no==0){
				var msg="대기실입니다. 대화방에서만 초대할 수 있습니다.";
				messages2.innerHTML += "<br>" + msg;
				return;
			}
			else if(click_no != 2){
				var msg="대기실에 있는 사람을 선택하세요.";
				messages2.innerHTML += "<br>" + msg;
				return;
			}
			else if(mbr_id =="" || mbr_id == null || id==mbr_id){
				var msg="초대할 사람을 선택하세요.";
				messages2.innerHTML += "<br>" + msg;
				return;
			}
			webSocket.send(id +" 2 " + "/call " + mbr_id);
			myRoomList();
		}
		
		function deleteRoom(){
			webSocket.send(id +" 2 " + "/del");
			roomList(room_cd);
		    room_no=0;
		}
		
		function showList() {
			mbr_id = null;
			webSocket.send(id +" 1 " + "/list");
			messages1.innerHTML = "";
		}
		
		function waitList() {
			mbr_id = null;
			webSocket.send(id +" 1 " + "/lsw");
			messages1.innerHTML = "";
		}
		
		function banList() {
			mbr_id = null;
			webSocket.send(id +" 1 " + "/bls");
			messages1.innerHTML = "";
		}
		
		function exitRoom() {
			webSocket.send(id +" 2 " + "/exit");
			myRoomList();
			roomList(room_cd);
			room_no=0;
		}
		
		function roomList(cd) {
			mbr_id = null;
			room_cd = cd;
			webSocket.send(id +" 1 " + "/rls " + room_cd);
			messages1.innerHTML = "";
		}
		
		function myRoomList() {
			mbr_id = null;
			webSocket.send(id +" 3 " + "/my");
			messages3.innerHTML = "";
		}
		
		function disPasswd(){
			inputPw.style.display ="none";	
		}
		
		function fclick(bcd, hid){
			click_no = bcd;
			
			if(bcd==3){
				room_no = hid;
				
				if(isNaN(hid)) {
					inputPw.style.display ="block";	
					messages2.innerHTML="<br>"+"비공개방 입니다. 비밀번호가 필요합니다.";
					return;
				 }
				
				webSocket.send(id +" 2 " + "/cd " + hid);
				myRoomList();
				roomList(room_cd);
			}
			else  mbr_id = hid;
			
		}
	</script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.14.3/umd/popper.min.js" integrity="sha384-ZMP7rVo3mIykV+2+9J3UJ46jBk0WLaUAdn689aCwoqbBJiSnjAK/l8WvCWPIPm49" crossorigin="anonymous"></script>
<script src="https://stackpath.bootstrapcdn.com/bootstrap/4.1.3/js/bootstrap.min.js" integrity="sha384-ChfqqxuZUCnJSK3+MXmPNIyE6ZbWh2IMqE241rYiqJxyMiZ6OW/JmZQ5stwEULTy" crossorigin="anonymous"></script>
</body>
</html>