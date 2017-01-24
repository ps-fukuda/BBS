<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@page isELIgnored="false"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>新規投稿</title>
<link href="./css/style.css" rel="stylesheet" type="text/css">
<!-- Bootstrap CDN -->
<link rel="stylesheet"
    href="//maxcdn.bootstrapcdn.com/bootstrap/3.2.0/css/bootstrap.min.css">
<link rel="stylesheet"
    href="//maxcdn.bootstrapcdn.com/bootstrap/3.2.0/css/bootstrap-theme.min.css">
<!-- Bootstrap CDN -->
</head>
<body>
<div class="container">
<div class="row">
<c:if test="${ not empty errorMessages }">
	<div class="errorMessages">
		<ul class="error">
			<c:forEach items="${errorMessages}" var="message">
				<li class="validate"><span class="glyphicon glyphicon-ok" aria-hidden="true"></span> <c:out value="${message}" /></li>
			</c:forEach>
		</ul>
	</div>
	<c:remove var="errorMessages" scope="session"/>
</c:if>
<div id="navi">
<a href="./">ホーム</a>
<p class="logout"><a href="logout">ログアウト</a></p>
</div>
<div class="panel panel-default center-block" style="margin-top:20px;">
<div class="panel-heading">新規投稿</div>
<div class="panel-body">
	<form action="post" method="post" class="form-horizontal">
		<div class="form-group">
			<label for="subject" class="control-label col-sm-2">件名</label>
			<div class="col-sm-8">
				<input id="subject" name="subject" value="<c:out value="${editPost.subject}" />" class="form-control" placeholder="50文字以下で入力してください" />
			</div>
		</div>
		<div class="form-group">
			<label for="body" class="control-label col-sm-2">本文</label>
			<div class="col-sm-8">
				<textarea id="body" name="body" rows="7" cols="40" class="form-control" placeholder="1000文字以下で入力してください"><c:out value="${editPost.body}" /></textarea>
			</div>
		</div>
		<div class="form-group">
			<label for="category" class="control-label col-sm-2">カテゴリー</label>
			<div class="col-sm-8">
				<input id="category" name="category" type="text" value="<c:out value="${editPost.category}" />" class="form-control" placeholder="10文字以下で入力してください" />
			</div>
		</div>
		<input type="hidden" name="user_id" value="<c:out value="${userId}" />" />
		<div class="form-group">
			<div class="col-sm-offset-2 col-sm-4">
				<input type="submit" value="投稿" class="btn btn-default" />
			</div>
		</div>
	</form>
</div> <!-- panel-body#end -->
</div> <!-- panel#end -->
</div> <!-- row#end -->
</div> <!-- container#end -->
<!-- Bootstrap CDN -->
    <script
        src="//maxcdn.bootstrapcdn.com/bootstrap/3.2.0/js/bootstrap.min.js"></script>
<!-- Bootstrap CDN -->
</body>
</html>