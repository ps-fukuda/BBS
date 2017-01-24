<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@page isELIgnored="false"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>${customPost.subject}</title>
<link href="../css/style.css" rel="stylesheet" type="text/css">
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
<a href="../">ホーム</a>
<p class="logout"><a href="../logout">ログアウト</a></p>
</div>
<div class="content">
<h3><c:out value="${customPost.subject}" /></h3>
<ul class="meta-list list-inline">
	<li><span class="glyphicon glyphicon-user" aria-hidden="true"></span> <c:out value="${customPost.name}" /></li>
	<li>Category: <c:out value="${customPost.category}" /></li>
	<li class="posted_date"><c:out value="${customPost.updatedAt}" /></li>
</ul>
<pre><c:out value="${customPost.body}" /></pre>
</div>

<legend id="com">Comments</legend>

<c:forEach var="obj" items="${viewCommentList}">
<div class="comment-wrap well">
<ul class="comment list-inline">
<li class="comment-title">名前 : <c:out value="${obj.name}" /></li>
<li>投稿日 : <c:out value="${obj.createdAt}" /></li>

	<c:choose>
	<c:when test="${deletePost}">
		<li>
		<form action="deleteComment" method="post">
		<input type="submit" value="delete" class="btn btn-danger btn-xs" onclick="return confirm('削除してもよろしいですか？');" />
		<input name="id" type="hidden" value="<c:out value="${obj.id}" />" />
		</form>
		</li>
	</c:when>
	<c:otherwise>
		<c:forEach var="item" items="${userIdList}">
			<c:if test="${item eq obj.userId}">
				<li>
				<form action="deleteComment" method="post">
				<input type="submit" value="delete" class="btn btn-danger btn-xs" onclick="return confirm('削除してもよろしいですか？');" />
				<input name="id" type="hidden" value="<c:out value="${obj.id}" />" />
				</form>
				</li>
			</c:if>
		</c:forEach>
	</c:otherwise>
	</c:choose>
</ul>
<pre><c:out value="${obj.body}" /></pre>
</div>
</c:forEach>

<form action="view" method="post">
	<div class="form-group">
		<label for="subject" class="control-label">名前</label>
		<input id="subject" name="subject" value="<c:out value="${loginUser.name}" />" class="form-control" disabled="disabled" />
	</div>
	<div class="form-group">
		<label for="body" class="control-label">コメント</label>
		<textarea id="body" name="body" rows="7" cols="40" class="form-control" placeholder="500文字以下で入力してください"></textarea>
	</div>
	<input type="hidden" name="post_id" value="<c:out value="${customPost.id}" />" />
	<input type="hidden" name="user_id" value="<c:out value="${loginUser.id}" />" />
	<div class="form-group">
		<input type="submit" value="コメントする" class="btn btn-default" />
	</div>
</form>

</div><!-- row#end -->
</div><!-- container#end -->
<!-- Bootstrap CDN -->
    <script
        src="//maxcdn.bootstrapcdn.com/bootstrap/3.2.0/js/bootstrap.min.js"></script>
<!-- Bootstrap CDN -->
</body>
</html>