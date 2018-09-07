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
<div class="row">
  <div class="col-sm-9">
    Level 1: .col-sm-9
    <div class="row">
      <div class="col-8 col-sm-6">
        Level 2: .col-8 .col-sm-6
      </div>
      <div class="col-4 col-sm-6">
        Level 2: .col-4 .col-sm-6
      </div>
    </div>
  </div>
</div>
	<div>
		사용자 아이디 : <%= id %>
	</div>
	<div>
		<input type ="text" id="messageinput"/>
	</div>
	<div>
		<button type="button" onclick="openSocket();">Open</button>
		<button type="button" onclick="send();">Send</button>
		<button type="button" onclick="closeSocket();">Close</button>
	</div>
	<!--  Server responses get written here -->
	<div id="messages"></div>
	
	<!-- Script to utilise the WebSocket -->
	<script type="text/javascript">
		var webSocket;
		var messages = document.getElementById("messages");
		
		function openSocket(){
			//Ensures only one connection is open at a time
			if(webSocket != undefined && webSocket.readyState != WebSocket.CLOSED) {
				writeResponse("WebSocket is already opened.");
				return;
			}
			
			// Create a new instance of the websocket
			// webScoket = new WebSocket("ws://localhost/  *projectName*/echo");
			webSocket = new WebSocket("ws://localhost:8081/WebSocketEx/websocketendpoint2");
			
			/*
			Binds functions to the listeners for the websocket.
			*/
			webSocket.onopen = function(event) {
				// for reasons I can't determine, onopen gets called twice
				// and the first time event.data is undefined
				// Leave a comment if you know the answer.
				if (event.data == undefined)
					return ;
				
				writeResponse(event.data);
			};
			
			webSocket.onmessage = function(event) {
				writeResponse(event.data);
			};
			
			webSocket.onclose = function(event) {
				writeResponse("connection closed");
			};
		}
		
		/*
		* Sends the value of the text input to the server
		*/
		
		function send() {
			var id = "<%= id %>";
			var text = document.getElementById("messageinput").value;
			webSocket.send(id +": "+text);
		}
		
		function closeSocket() {
			webSocket.close();
		}
		
		function writeResponse(text){
			messages.innerHTML += "<br>" + text;
		}
	</script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.14.3/umd/popper.min.js" integrity="sha384-ZMP7rVo3mIykV+2+9J3UJ46jBk0WLaUAdn689aCwoqbBJiSnjAK/l8WvCWPIPm49" crossorigin="anonymous"></script>
<script src="https://stackpath.bootstrapcdn.com/bootstrap/4.1.3/js/bootstrap.min.js" integrity="sha384-ChfqqxuZUCnJSK3+MXmPNIyE6ZbWh2IMqE241rYiqJxyMiZ6OW/JmZQ5stwEULTy" crossorigin="anonymous"></script>
</body>
</html>