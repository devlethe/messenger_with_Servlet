<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<script
	src="https://ajax.googleapis.com/ajax/libs/jquery/3.4.1/jquery.min.js"></script>
</head>
<body>
	<%
	String id = "";
	try{
		id = (String)session.getAttribute("id"); 
		if(id!=null&&!id.equals("")){
			response.sendRedirect("main.jsp");	
		}
	} catch(Exception e){
		
	}

%>


	<button id="test">test</button>
	<form method="post" action="login">
		<input type="text" name="id"> <input type="password"
			name="password"> <input type="submit" value="Login">
	</form>
</body>
</html>