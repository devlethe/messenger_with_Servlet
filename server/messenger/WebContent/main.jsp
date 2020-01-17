<%@ page language="java" contentType="text/html; charset=EUC-KR"
	pageEncoding="EUC-KR"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="EUC-KR">
<title>Insert title here</title>
<script
	src="https://ajax.googleapis.com/ajax/libs/jquery/3.4.1/jquery.min.js"></script>
</head>
<body>
	<%
		String id = "";
		try {
			id = (String) session.getAttribute("id");
			if (id == null || id.equals("")) {
				response.sendRedirect("login.jsp");
			}
		} catch (Exception e) {
			response.sendRedirect("login.jsp");
		}
	%>
	<div>
		<button id="editprofile">프로필변경</button>
		<button id="findFriend" onclick="location.href='find.jsp'">친구추가</button>
		<button id="refresh">새로고침</button>
		<button id="logout" onclick="location.href='logout.jsp'">로그아웃</button>
	</div>
	<div id="profile"></div>
	<div>
		<h4>친구</h4>
		<div id="flist"></div>
	</div>


</body>
<script>

$("#refresh").click(function(){
	getProfile();
	getFriend();
});

$('body').delegate('#fchat', 'click', function() {
	 var url = "chat.jsp?user=" + $(this).attr("name");
     var name = "popup test";
     var option = "width = 500, height = 500"
     window.open(url, name, option);
});


	$('body').delegate('#fdelete', 'click', function() {
		$.ajax({
		    url: "/messenger/friend", 
		    data: {"user" : "<%=id%>", 
		    	"friend" : $(this).attr("name"),
		    	"type":"delete"},
		    type: "POST",
		    success:function(data){
		    	var result = JSON.parse(data);
		     	getFriend();
		    }    
		});
	})
	
var getProfile = function(){
$.ajax({
    url: "/messenger/profile", 
    data: {"id" : "<%=id%>"},
    type: "GET",
    success:function(data){
    	var result = JSON.parse(data).info;
    	$("#profile").empty();
    	$("#profile").append(
    	`<div id="my" style="border: 1px solid black; width: 70%; height: 50px;">
     	<div id="name" style="display: none;">` + result.name + `</div>
    	<div id="nickname">` +result.nickname+ `</div>
     	<div id="friendid" style="display: none;">` +result.user+ `</div>
     	<div id="status">` +result.status+ `</div>
     </div>`
     )
    }    
});
}

var getFriend = function(){
$.ajax({
    url: "/messenger/friend", 
    data: {"id" : "<%=id%>"},
    type: "GET",
    success:function(data){
    	var result = JSON.parse(data).userdata;
    	$("#flist").empty();
    	for(var i of result){
    	$("#flist").append(
    	`<div id="friend"
     	style="border: 1px solid black; width: 70%; height: 50px;">
     	<div id="name" style="display: none;margin-left:10px;">` + i.f_name + `</div>
    	<div id="nickname" style="float:left; margin-left:10px;">` +i.f_nickname+ `</div>
     	<div id="friendid" style="display: none; margin-left:10px;">` +i.f_user+ `</div>
     	<div id="status" style="float:left; margin-left:10px;">` +i.f_status+ `</div>
     	<button id="fchat" name="` +i.f_user+ `" style="float:left;">채팅</button>
     	<button id="fdelete" name="` +i.f_user+ `" style="float:left;">친구삭제</button>
     	<div id="chat_` + i.f_user + `" style="float:left;">0</button>
     </div>`
     )
    	}
     
    }    
});
}

getProfile();
getFriend();

/*
var webSocket = new WebSocket("ws://localhost:8090/messenger/webchat");
var messageTextArea = document.getElementById("messageTextArea");

webSocket.onopen = function(message){
//messageTextArea.value += "Server connect...\n";
};

webSocket.onclose = function(message){
//messageTextArea.value += "Server Disconnect...\n";
};

webSocket.onerror = function(message){
//messageTextArea.value += "error...\n";
};

webSocket.onmessage = function(message){
	console.log(message.data);
var msg = JSON.parse(message.data);
$("#chat_" + msg.from).text(
		parseInt($("#chat_" + msg.from).text()) + 1
		)
};
*/
function disconnect(){
webSocket.close();
}
</script>

</html>