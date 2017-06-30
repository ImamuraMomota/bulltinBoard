<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@page isELIgnored="false"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link href="CSS/bulltinBoard.css" rel="stylesheet" type="text/css">
<title>ログイン画面</title>
</head>
<body>
<h1>わったい菜掲示板</h1><br />
<br />
	<c:if test="${ not empty errorMessages }">
	<div class="errorMessagesSignin">
		<ul type="circle">
			<c:forEach items="${errorMessages}" var="message">
				<li><c:out value="${message}" />
			</c:forEach>
		</ul>
	</div>
	<c:remove var="errorMessages" scope="session"/>
	</c:if>
<br />

<form action="login" method="post">
<table class=basicLogin align="center">
	<tr>
		<th scope="row">ログインID</th>
		<td><input name="account" id="account"value="${user.account}"/></td>
	</tr>
	<tr>
		<th scope="row">パスワード</th>
		<td><input name="password" type="password" id="password"/></td>
	</tr>
	<tr><td colspan="2" align="center">
			<input id="submit_button" type="submit" value="ログイン"class="submit_btn">
	</td></tr>
</table>
</form>
<br />
<br />
<div class="copyright" align=center>Copyright(c)Momota Imamura</div>

</body>
</html>