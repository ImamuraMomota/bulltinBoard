<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@page isELIgnored="false"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link href="./CSS/bulltinBoard.css" rel="stylesheet" type="text/css">
<title>ホーム画面</title>
</head>
<body>


<h1>わったい菜掲示板</h1>


<table class=basic align="center">
<tr><td>
	<ul class="nav" >
		<li><a href="newMessage">新規投稿する</a></li>
		<c:if test="${loginUser.departmentId == 1}">
		<li><a href="accountmanage">ユーザー管理を行う</a></li>
		</c:if>
		<li><a href="./">ホーム</a></li>

		<c:if test="${loginUser.departmentId != 1}">
		<li>　　　　　　</li>
		</c:if>

		<li><a href="logout">ログアウト</a></li>
	</ul>

	<br/>

<form action="searchMessage" method="get">
	<table class="search">
		<tr>
		<th>

			◎カテゴリー
		</th>
		<td>
			<select name="category">
				<option value="">全て</option>
				<c:forEach items="${categories}" var="category">
					<option value="${category.category}">${category.category}</option>
				</c:forEach>
			</select>
		</td>
		</tr>
		<tr>
		<th>
			◎日付
		</th>
		<td>
			<input type="date" name="startDate" value="${startDate}">から
			<input type="date" name="endDate" value="${endDate}">まで
		</td>
		<td rowspan="2">
			<input type="submit" value="検索" class="submit_btn">
		</td>
		</tr>
	</table>
	</form>





	<div class="message">
		<c:if test="${ not empty loginUser }">
			<br />
			<br />
			<c:forEach items="${favorites}" var="favorite">
			<c:forEach items="${userMessages}" var="message">
				<c:if test="${(loginUser.id == favorite.userId) && (message.id == favorite.messageId)}">
					<table class="newMessage">
					<thead>
					<tr>
						<th colspan="2" scope="cols"><div class="title">${message.title}</div></th>
						<th scope="cols">
							<c:if test="${loginUser.departmentId == 2}">
							<form action="deleteMessage" method="post">
							<input type="hidden" name="messageId" value="${message.id}">
							<input id="delete_button" type="submit" value="" title="投稿を削除する" onclick="return confirm('この投稿を削除してよろしいですか？');"><br />
							</form>
							</c:if>
							<c:if test="${(loginUser.departmentId != 2) && (loginUser.branchId == message.branchId)}">
							<form action="deleteMessage" method="post">
							<input type="hidden" name="messageId" value="${message.id}">
							<input id="delete_button2" type="submit" value="" title="投稿を削除する" onclick="return confirm('この投稿を削除してよろしいですか？');"><br />
							</form>
							</c:if>
						</th>
						<th>
							<!-- <form action="addFavorites" method="post"> -->
							<input id="addedfavorite_button" type="submit" value="★" title="投稿をお気に入りから外す">
							<input type="hidden" name="messageId" value="${message.id}">
							<!-- </form> -->
						</th>
					</tr>
					</thead>
						<tbody>
				<tr>
					<th scope="row">カテゴリー</th>
					<td colspan="3" scope="cols">${message.category}</td>
				</tr>
				<tr>
					<th scope="row">投稿者</th>
					<td colspan="3" scope="cols">${message.name}</td>
				</tr>
				<tr>
					<th scope="row">本文</th>
					<td colspan="3" scope="cols">${message.text}</td>
				</tr>
				<tr>
					<th scope="row">投稿日</th>
					<td colspan="3" scope="cols"><fmt:formatDate value="${message.insertDate}" pattern="yyyy/MM/dd HH:mm:ss" /></td>
				</tr>
					<th scope="row">コメント</th>
					<td>
						<form action="./" method="post">
						<input type="hidden" name="messageId" value="${message.id}">
						<textarea class="comment" name="text" cols="25" rows="4" onkeyup="CountDownLength( '${message.id}' , value , 500 );"></textarea>
						<input type="submit" value="コメントする">
						<p id="${message.id}">あと500文字</p>

						<script type="text/javascript"><!--
						function CountDownLength( idn, str, mnum ) {
				   		document.getElementById(idn).innerHTML = "あと" + (mnum - str.length) + "文字";
						}
						// --></script>
						</form>
					</td>
				</tbody>
					</table>

					<div id="comments">
				<c:forEach items="${comments}" var="comment">
					<c:if test="${message.id == comment.messageId}">
					<table class=comment>
					<thead>
					<tr>
						<th colspan="2" scope="cols">From ${comment.name}</th>
						<th>
							<c:if test="${loginUser.departmentId == 2}">
							<form action="deleteComment" method="post">
							<input type="hidden" name="commentId" value="${comment.id}">
							<input id="delete_button3" value="" type="submit" title="コメントを削除する" onclick="return confirm('このコメントを削除してよろしいですか？');">
							</form>
							</c:if>
						</th>
						<th>
							<c:if test="${loginUser.departmentId != 2 && loginUser.id == comment.userId}">
							<form action="deleteComment" method="post">
							<input type="hidden" name="commentId" value="${comment.id}">
							<input id="delete_button3" type="submit" value="" title="コメントを削除する" onclick="return confirm('このコメントを削除してよろしいですか？');">
							</form>
							</c:if>
						</th>
					</tr>
					</thead>
					<tbody>
					<tr>
						<th scope="row">コメント文</th>
						<td>${comment.text}</td>
					</tr>
					<tr>
						<th scope="row">投稿日</th>
						<td><fmt:formatDate value="${comment.insertDate}" pattern="yyyy/MM/dd HH:mm:ss" /></td>
					</tr>
					</tbody>
					</table>
					<br />
					</c:if>
				</c:forEach>
				</div>
				</c:if>
			</c:forEach>
			</c:forEach>
			</c:if>
	</div>
	<br />
	</td></tr>
	</table>
	<br />
	<div class="copyright" align=center>Copyright(c)Momota Imamura</div>
</body>
</html>