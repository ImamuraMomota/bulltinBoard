<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@page isELIgnored="false"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link href="CSS/bulltinBoard.css" rel="stylesheet" type="text/css">
<title>ユーザー管理画面</title>
</head>
<body>
<br />
<h2>ユーザ管理画面</h2><br />
<table class=menu align="center">
<tr><td>
	<a href="./">ホーム</a>
</td></tr>
</table>
<br />
<table class=basic align="center">
<tr><td>
<ul class="signup" align="center">
	<li><a href="signup">新規ユーザーを登録する</a></li>
</ul>
<br />



<c:if test="${ not empty loginUser }">
	<table class=manage border="1">
	<tr>
	<th>ログインID</th> <th>名前</th> <th>支店</th> <th>部署</th> <th>編集</th> <th>停止</th>
	<th>削除</th>
	</tr>

		<c:forEach items="${users}" var="user">
		<tr>
			<td>
				<div class="account">
					<c:out value="${user.account}"></c:out>
				</div>
			</td>
			<td>
				<div class="name">
					<c:out value="${user.name}"></c:out>
				</div>
			</td>
			<td>
				<div class="branchId">
					<c:forEach items="${branches}" var="branch">
						<c:if test="${user.branchId == branch.id}">
							<c:out value="${branch.name}"></c:out>
						</c:if>
					</c:forEach>
				</div>
			</td>
			<td>
				<div class="departmentId">
					<c:forEach items="${departments}" var="department">
						<c:if test="${user.departmentId == department.id}">
							<c:out value="${department.name}"></c:out>
						</c:if>
					</c:forEach>
				</div>
			</td>
			<td>

				<form action="edituser" method="get">
					<input id="editor_button" type="submit" value="編集する">
					<input type="hidden" name="id" value="${user.id}">
					<br />
				</form>
			</td>
			<td>
			<form action="accountmanage" method="post">
				<input type="hidden" name="userId" value="${user.id}">
				<c:if test="${user.isStopped == 0}">
						<input type="hidden" name="isStopped" value="1">
						<input id="stop_button" type="submit" value="停止する" onclick="return confirm('このユーザーを停止してよろしいですか？');">
				</c:if>
				<c:if test="${user.isStopped == 1 }">
						<input type="hidden" name="isStopped" value="0">
						<input id="stop_button" type="submit" value="復活する"onclick="return confirm('このユーザーを復活させてよろしいですか？');">
				</c:if>
			</form>

			</td>
			<td>
				<form action="deleteUser" method="post">
					<input type="hidden" name="userId" value="${user.id}">
					<input id="userDelete_button" type="submit" value="削除する" onclick="return confirm('このユーザーを削除してよろしいですか？');"/><br />
				</form>
			</td>
		</tr>
		</c:forEach>
	</table>
</c:if>
</td></tr>
</table>
<br />
</body>
</html>