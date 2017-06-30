<%@page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@page isELIgnored="false"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<link href="CSS/bulltinBoard.css" rel="stylesheet" type="text/css">
	<title>ユーザー編集画面</title>
</head>
<body>
<h2>ユーザー情報編集</h2>
<table class="back">
<tr><td><a href="./">ホーム</a>　＞　<a href="./accountmanage">ユーザー管理画面</a></td></tr>
</table>
<br />
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
<table class=basic>
<tr>
<td>
<form action="edituser" method="post"><br />
	<table class="newUser">
	<tr>
		<th scope="row">名前</th>
		<td><input name="editId" type="hidden" value="${editUser.id}">
			<input name="name" id="name" value="${editUser.name}"/></td>
	</tr>
	<tr>
		<th scope="row">アカウント名</th>
		<td><input name="account" id="account"value="${editUser.account}"/></td>
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
			<c:forEach items="${branches}" var="branch">
				<c:if test="${editUser.branchId == branch.id}">
					<option value="${branch.id}">${branch.name}</option>
				</c:if>
			</c:forEach>

			<c:forEach items="${branches}" var="branch">
				<c:if test="${editUser.branchId != branch.id}">
					<option value="${branch.id}">${branch.name}</option>
				</c:if>
			</c:forEach>

			</select>
		</td>
	</tr>
	<tr>
		<th scope="row">役職</th>
		<td><select name="department_id">
			<c:forEach items="${departments}" var="department">
				<c:if test="${editUser.departmentId == department.id}">
					<option value="${department.id}">${department.name}</option>
				</c:if>
			</c:forEach>

			<c:forEach items="${departments}" var="department">
				<c:if test="${editUser.departmentId != department.id}">
					<option value="${department.id}">${department.name}</option>
				</c:if>
			</c:forEach>
		</select>
		</td>
	</tr>
	</table>
	<br />
	<br />
	<table class=menu align="center">
		<tr><td>
		<input id="submit_button" type="submit" value="更新" class="submit_btn" onclick="return confirm('ユーザー情報を更新してよろしいですか？');"/>
		</td></tr>
		</table>
		<br />
	</form>
	</td></tr>
</table>
<br />
	<div class="copyright" align=center>Copyright(c)Momota Imamura</div>

</body>