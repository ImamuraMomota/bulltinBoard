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
<title>新規投稿</title>
</head>
<body>
<h2>新規投稿画面</h2>
<c:if test="${ not empty loginUser }">
	<table class="back">
	<tr><td><a href="./">ホーム</a></td></tr>
	</table>
<br />
	<c:if test="${ not empty errorMessages }">
			<div class="errorMessages">
			<ul type="circle">
				<c:forEach items="${errorMessages}" var="message">
					<li><c:out value="${message}" /><br /></li>
				</c:forEach>
			</ul>
			</div>
		<c:remove var="errorMessages" scope="session"/>
	</c:if>
	<br />
	<table class=basic align="center">
	<tr><td>
	<form action="newMessage" method="post">
		<br />
		<img src="./images/pencil.jpg" align="left" width="20px" height="20px" border="1" hspace="10" vspace="1">投稿タイトル
		　　<textarea name="title" cols="31"rows="1"><c:out value="${message.title}"></c:out></textarea><br />
		<br />
		<img src="./images/pencil.jpg" align="left" width="20px" height="20px" border="1"hspace="10" vspace="1" style="vertical-align:middle;">既存のカテゴリー
		<select name="category" style="width: 93px">
			<option value="">全て</option>
			<c:forEach items="${categories}" var="category">
				<option value="${category.category}">${category.category}</option>
			</c:forEach>
		</select>
		<br />
		<img src="./images/pencil.jpg" align="left" width="20px" height="20px" border="1"hspace="10" vspace="5" style="vertical-align:middle;">新規のカテゴリー
			<textarea name="newcategory" cols="10" rows="1" ><c:out value="${category.category}"></c:out></textarea><br />
		<br />
		<img src="./images/pencil.jpg" align="left" width="20px" height="20px" border="1"hspace="10" vspace="1" style="vertical-align:top;">本文<br />
		　　　<textarea name="text" cols="50" rows="10" style="width: 150px height: 10pm" onkeyup="CountDownLength( 'cdlength1' , value , 1000 );"><c:out value="${message.text}"></c:out></textarea>
		<p id="cdlength1"><br/>あと1000文字</p>

		<script type="text/javascript"><!--
		function CountDownLength( idn, str, mnum ) {
   		document.getElementById(idn).innerHTML = "あと" + (mnum - str.length) + "文字";
		}
		// --></script>

		<br />
		<table class=menu align="center">
		<tr><td>
		<br />
		<input id="submit_button" type="submit" value="投稿する"class="submit_btn">
		</td></tr>
		</table>
		<br/>
	</form>
	</td></tr>
	</table>
</c:if>
<br />
<br />
<div class="copyright" align=center>Copyright(c)Momota Imamura</div>
</body>
</html>