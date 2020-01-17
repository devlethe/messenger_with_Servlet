<%@ page language="java" contentType="text/html; charset=EUC-KR"
	pageEncoding="EUC-KR"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="EUC-KR">
<title>친구찾기</title>
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
	<input id="userid">
	<button id="find">찾기</button>
	<div id="result"></div>
</body>
<script>

$('body').delegate('#friendadd', 'click', function() {
	$.ajax({
	    url: "/messenger/friend", 
	    data: {"user" : "<%=id%>", 
	    	"friend" : $(this).attr("name"),
	    	"type":"add"},
	    type: "POST",
	    success:function(data){
	    	var result = JSON.parse(data);
	    	location.href = "main.jsp";
	    }    
	});
});

$("#find").click(function(){
	$.ajax({
	    url: "/messenger/find", 
	    data: {"nickname" : $("#userid").val()},
	    type: "GET",
	    success:function(data){
	    	var result = JSON.parse(data).userdata;
    	$("#result").empty();
    	for(var i of result){
    		$("#result").append(
    	`<div id="my" style="border: 1px solid black; width: 70%; height: 50px;">
     	<div id="name" style="display: none;">` + i.name + `</div>
    	<div id="nickname">` +i.nickname+ `</div>
     	<div id="friendid" style="display: none;">` +i.user+ `</div>
     	<div id="status">` +i.status+ `</div>
     	<button id="friendadd" name="`  +i.user + `">친구추가</button>
     </div>`)
	    }
	}
})
});

</script>
</html>