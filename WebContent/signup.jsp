<%@page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@page isELIgnored="false"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<link href="CSS/bulltinBoard.css" rel="stylesheet" type="text/css">
	<title>ユーザー新規登録</title>
</head>
<body>

<h2>ユーザー新規登録</h2>
<table class="back">
<tr><td><a href="./">ホーム</a>　＞　<a href="./accountmanage">ユーザー管理画面</a><br /></td></tr>
</table>
<br />
	<c:if test="${ not empty errorMessages }">
	<div class="errorMessages">
		<ul type="circle">
			<c:forEach items="${errorMessages}" var="message">
				<li><c:out value="${message}" />
			</c:forEach>
		</ul>
	</div>
	<c:remove var="errorMessages" scope="session"/>
	</c:if>
<br />
<table class=basic align="center">
<tr><td>
<form action="signup" method="post"><br />
	<table class="newUser">
		<tr>
			<th scope="row">名前</th>
			<td><input name="name" id="name" value="${user.name}"/></td>
		</tr>
		<tr>
			<th scope="row">ログインID</th>
			<td><input name="account" id="account"value="${user.account}"/></td>
		</tr>
		<tr>
			<th scope="row">パスワード</th>
			<td><input name="password" type="password" id="password"/></td>
		</tr>
		<tr>
			<th scope="row">確認用パスワード</th>
			<td><input name="confirmingPassword" type="password" id="password"/></td>
		</tr>
		<tr>
			<th scope="row">支店</th>
			<td><select name="branch_id">
				<option value="0">選択してください</option>
				<c:forEach items="${branches}" var="branch">
				<option value="${branch.id}">
					${branch.name}
				</option>
				</c:forEach>
				</select>
			</td>
		</tr>
		<tr>
			<th scope="row">役職</th>
			<td><select name="department_id">
				<option value="0">選択してください</option>
				<c:forEach items="${departments}" var="department">
				<option value="${department.id}">
					${department.name}
				</option>
				</c:forEach>
				</select>
			</td>
		</tr>
	</table>
<br />
<table class=menu align="center">
		<tr><td>
		<input id="submit_button" type="submit" value="登録"class="submit_btn">
		</td></tr>
		</table>
		<br />
</form>
</td></tr>
</table>
<br />
<br />
<div class="copyright" align=center>Copyright(c)Momota Imamura</div>

</body>
</html>

