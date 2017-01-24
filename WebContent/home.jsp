<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@page isELIgnored="false"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<jsp:useBean id="util" class="org.apache.commons.lang.StringUtils" />
<%@ page import="java.util.ArrayList" %>
<%@ page import="java.util.List" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>掲示板</title>
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
<a href="post">新規投稿</a>
<c:if test="${ not empty isManager }">
<a href="userManagement">ユーザー管理</a>
</c:if>
<p class="logout"><a href="logout">ログアウト</a></p>
</div>
<div id="search">
<form action="./" method="get" role="search" class="form-inline" id="search">
	<div class="form-group">
		<label for="search_category" class="category_label">カテゴリー</label>
		<input name="category" id="search_category" type="text" class="form-control" value="${editCategory}" placeholder="検索キーワード">
	</div>
	<div class="form-group">
		<label for="from" class="from_label">開始日</label>
		<input name="from" id="from" type="date" class="form-control" placeholder="開始日">
	</div>
	<div class="form-group">
		<label for="to" class="to_label">終了日</label>
		<input name="to" id="to" type="date" class="form-control" placeholder="開始日">
	</div>
		<button type="submit" class="btn btn-default">検索</button>
</form>
</div> <!-- search#end -->
<c:forEach var="obj" items="${customPostList}">
<div class="content">
<h3><a href="post/view?id=<c:out value="${obj.id}" />"><c:out value="${obj.subject}" /></a></h3>
<ul class="meta-list list-inline">
	<li><span class="glyphicon glyphicon-user" aria-hidden="true"></span> <c:out value="${obj.name}" /></li>
	<li><span class="glyphicon glyphicon-comment" aria-hidden="true"></span> Comment(<a href="post/view?id=<c:out value="${obj.id}" />#com"><c:out value="${obj.commentCount}" /></a>)</li>
	<li>Category: <c:out value="${obj.category}" /></li>
	<li class="posted_date"><c:out value="${obj.updatedAt}" /></li>

	<!--<c:if test="${deletePost || fn:contains(userIdList, obj.userId)}">-->
	<c:choose>
	<c:when test="${deletePost}">
		<li>
		<form action="deletePost" method="post">
		<input type="submit" value="delete" class="btn btn-danger btn-xs" onclick="return confirm('削除してもよろしいですか？');" />
		<input name="id" type="hidden" value="<c:out value="${obj.id}" />" />
		</form>
		</li>
	</c:when>
	<c:otherwise>
		<c:forEach var="item" items="${userIdList}">
			<c:if test="${item eq obj.userId}">
				<li>
				<form action="deletePost" method="post">
				<input type="submit" value="delete" class="btn btn-danger btn-xs" onclick="return confirm('削除してもよろしいですか？');" />
				<input name="id" type="hidden" value="<c:out value="${obj.id}" />" />
				</form>
				</li>
			</c:if>
		</c:forEach>
	</c:otherwise>
	</c:choose>
	<!--</c:if>-->
</ul>
<pre><c:out value="${util.abbreviate(obj.body, 200)}" /></pre>
</div>
</c:forEach>
</div><!-- row#end -->
</div><!-- container#end -->
<!-- Bootstrap CDN -->
    <script
        src="//maxcdn.bootstrapcdn.com/bootstrap/3.2.0/js/bootstrap.min.js"></script>
<!-- Bootstrap CDN -->
</body>
</html>