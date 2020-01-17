<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
 <!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.4.1/jquery.min.js"></script>
</head>
<body>
		
<form method="post" action="register">
  <!-- <input type="image" name="fileupload"> -->  
<h1>id</h1>
    <input type="text" name="id" id="userid">
<h1>password</h1>
    <input type="password" name="password">
<h1>nickname</h1>
    <input type="text" name="nickname">
<h1>name</h1>
    <input type="text" name="name">
<h1>status message</h1>
    <input type="text" name="status">
    
    <input type="submit" value="전송">
</form> <button id="checkid">ID 중복확인</button>
</body>
<script>
$("#checkid").click(function(){
	$.ajax({
	    url: "/messenger/checkID", 
	    data: {"id" : $("#userid").val()},
	    type: "GET",
	}).done(function(json) {alert(json)});
});
</script>
</html>