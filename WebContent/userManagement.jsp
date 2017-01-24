<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@page isELIgnored="false"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>ユーザー管理</title>
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
<a href="signup">ユーザー新規登録</a>
<p class="logout"><a href="logout">ログアウト</a></p>
</div>
<div class="panel panel-default center-block" style="margin-top:20px;">
<div class="panel-heading">ユーザー管理</div>
<div class="panel-body">
<table class="table table-striped">
<thead>
<tr>
<th>名前</th>
<th>支店</th>
<th>部署</th>
<th>編集</th>
<th>状態</th>
<th>削除</th>
</tr>
</thead>
<tbody>
<c:forEach var="user" items="${userList}">
<tr>
<td><c:out value="${user.name}" /></td>
<td><c:out value="${user.branchName}" /></td>
<td><c:out value="${user.departmentName}" /></td>
<td>
<form action="editUser" method="get">
<input name="id" type="hidden" value="${user.id}" />
<input type="submit" value="編集" class="btn btn-default btn-xs" />
</form>
</td>
<td>
<c:choose>
	<c:when test="${!user.isStopped()}"><c:set var="value" value="停止" /></c:when>
	<c:when test="${user.isStopped()}"><c:set var="value" value="復活" /></c:when>
</c:choose>
<form action="status" method="post">
<c:if test="${myId != user.id}">
	<input type="submit" value="<c:out value="${value}" />する" class="btn btn-warning btn-xs" onclick="return confirm('<c:out value="${value}" />してもよろしいですか？');" />
</c:if>
<input name="id" type="hidden" value="${user.id}" />
<input name="is_stopped" type="hidden" value="${user.isStopped()}" />
</form>
</td>
<td>
<form action="deleteUser" method="post">
<c:if test="${myId != user.id}">
	<input type="submit" value="削除" class="btn btn-danger btn-xs" onclick="return confirm('削除してもよろしいですか？');" />
</c:if>
<input name="id" type="hidden" value="${user.id}" />
</form>
</td>
</tr>
</c:forEach>
</tbody>
</table>
</div> <!-- panel-body#end -->
</div> <!-- panel#end -->
</div><!-- row#end -->
</div><!-- container#end -->
<!-- Bootstrap CDN -->
    <script
        src="//maxcdn.bootstrapcdn.com/bootstrap/3.2.0/js/bootstrap.min.js"></script>
<!-- Bootstrap CDN -->
</body>
</html>