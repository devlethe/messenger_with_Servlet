<%@ page language="java" contentType="text/html; charset=EUC-KR"
    pageEncoding="EUC-KR"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="EUC-KR">
<title>Logout</title>
</head>
<body>
<%
	session.invalidate(); 
%>
</body>
<script>

alert("로그아웃 되었습니다.");

document.location.href = "login.jsp"; 

</script>
</html>