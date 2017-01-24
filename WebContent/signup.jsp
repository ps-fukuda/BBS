<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@page isELIgnored="false"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>ユーザー新規登録</title>
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
<a href="userManagement">ユーザー管理</a>
<p class="logout"><a href="logout">ログアウト</a></p>
</div>
<div class="panel panel-default center-block" style="margin-top:20px;">
<div class="panel-heading">ユーザー登録</div>
<div class="panel-body">
	<form action="signup" method="post" class="form-horizontal">
		<div class="form-group">
			<label for="login_id" class="control-label col-sm-2">ログインID</label>
			<div class="col-sm-8">
				<input id="login_id" name="login_id" value="<c:out value="${editUser.loginId}" />" class="form-control" placeholder="半角英数字 6文字以上20文字以下で入力してください" />
			</div>
		</div>
		<div class="form-group">
			<label for="password" class="control-label col-sm-2">パスワード</label>
			<div class="col-sm-8">
				<input id="password" name="password" type="password" class="form-control" placeholder="記号を含む全ての半角文字6文字以上255以下で入力してください" />
			</div>
		</div>
		<div class="form-group">
			<label for="password_confirm" class="control-label col-sm-2">パスワード(確認用)</label>
			<div class="col-sm-8">
				<input id="password_confirm" name="password_confirm" type="password" class="form-control" />
			</div>
		</div>
		<div class="form-group">
			<label for="name" class="control-label col-sm-2">名前</label>
			<div class="col-sm-8">
				<input id="name" name="name" value="<c:out value="${editUser.name}" />" class="form-control" placeholder="10文字以下で入力してください" />
			</div>
		</div>
		<div class="form-group">
			<label for="branch_id" class="control-label col-sm-2">支店名</label>
			<div class="col-sm-8">
				<select name="branch_id" class="form-control">
				<c:forEach var="obj" items="${branch}">
					<option value="<c:out value="${obj.key}" />" <c:if test="${editUser.branchId == obj.key}">selected</c:if>><c:out value="${obj.value}" /></option>
				</c:forEach>
				</select>
			</div>
		</div>
		<div class="form-group">
			<label for="department_id" class="control-label col-sm-2">部署・役職</label>
			<div class="col-sm-8">
				<select name="department_id" class="form-control">
				<c:forEach var="obj" items="${department}">
					<option value="<c:out value="${obj.key}" />" <c:if test="${editUser.departmentId == obj.key}">selected</c:if>><c:out value="${obj.value}" /></option>
				</c:forEach>
				</select>
			</div>
		</div>
		<div class="form-group">
			<div class="col-sm-offset-2 col-sm-4">
				<input type="submit" value="登録" class="btn btn-default" />
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