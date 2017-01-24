<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@page isELIgnored="false"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>ログイン</title>
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

<div class="panel panel-default center-block" style="margin-top:20px;">
<div class="panel-heading">ログイン画面</div>
<div class="panel-body">
<form action="login" method="post" class="form-horizontal">
	<div class="form-group">
		<label for="login_id" class="control-label col-sm-2">ログインID</label>
		<div class="col-sm-8">
			<input id="login_id" name="login_id" value="<c:out value="${editLogin}" />" class="form-control" />
		</div>
	</div>

	<div class="form-group">
		<label for="password" class="control-label col-sm-2">パスワード</label>
		<div class="col-sm-8">
			<input id="password" name="password" type="password" class="form-control" />
		</div>
	</div>
	<div class="form-group">
		<div class="col-sm-offset-2 col-sm-4">
			<input type="submit" value="ログイン" class="btn btn-default" />
		</div>
	</div>
</form>
</div><!-- panel-body -->
</div><!-- panel#end -->
</div><!-- row#end -->
</div><!-- container#end -->
<!-- Bootstrap CDN -->
    <script
        src="//maxcdn.bootstrapcdn.com/bootstrap/3.2.0/js/bootstrap.min.js"></script>
<!-- Bootstrap CDN -->
</body>
</html>