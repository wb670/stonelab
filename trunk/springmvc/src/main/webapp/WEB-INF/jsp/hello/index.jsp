<%@ page contentType="text/html" pageEncoding="utf-8"
	isELIgnored="false"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<html>
<head>
<title>hello.index</title>
</head>
<body>
	Hello,welcome ${ name }<br/>
	<form action="update.htm" method="post">
		<spring:bind path="user.name">
			name:<input name="name" value="${ status.value }" />${ status.errorMessage }
		</spring:bind>
		<br/>
		<spring:bind path="user.info">
			info:<input name="info" value="${ status.value }" />${ status.errorMessage }
		</spring:bind>
		<br/>
		<input name="submit" type="submit" value="submit"/>
	</form>
</body>
</html>