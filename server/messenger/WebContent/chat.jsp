<%@ page language="java" contentType="text/html; charset=EUC-KR"
	pageEncoding="EUC-KR"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="EUC-KR">
<title>chat</title>
<script
	src="https://ajax.googleapis.com/ajax/libs/jquery/3.4.1/jquery.min.js"></script>
</head>
<%
	String id = "";
	Boolean isCloseWindow = false;
	String friend = request.getParameter("user");
	try {
		id = (String) session.getAttribute("id");
		if (id == null || id.equals("")) {
			isCloseWindow = true;
		}
		if (friend == null || friend.equals("")) {
			isCloseWindow = true;
		}
		session.setAttribute("person", friend);
	} catch (Exception e) {
		isCloseWindow = true;
	}
%>
<body onLoad="closeWindow(<%=isCloseWindow%>)">
	<input id="chat">

	<button id="send">보내기</button>
	<div id="messagelist"></div>
	<textarea id="messageTextArea" rows="20" cols="30"></textarea>
</body>
<script>
var webSocket = new WebSocket("ws://localhost:8090/messenger/webchat");
var messageTextArea = document.getElementById("messageTextArea");


webSocket.onopen = function(message){
};

webSocket.onclose = function(message){
//messageTextArea.value += "Server Disconnect...\n";
};

webSocket.onerror = function(message){
//messageTextArea.value += "error...\n";
};

webSocket.onmessage = function(message){
var msg = JSON.parse(message.data);
messageTextArea.value += (msg.from == "<%=id%>" ? "나": msg.from) + " : " + msg.msg +"\n";
$('#messageTextArea').scrollTop($('#messageTextArea').prop('scrollHeight'));
};

$("#send").click(function(){
var message = document.getElementById("chat");
messageTextArea.value += "나 :" + message.value+"\n";
webSocket.send(message.value);
message.value = "";
var top = $('#messageTextArea').prop('scrollHeight');
$('#messageTextArea').scrollTop(top);
}
)

function closeWindow(ifClose) {
	if (ifClose) {
	window.close();
	}
	}

</script>

</html>