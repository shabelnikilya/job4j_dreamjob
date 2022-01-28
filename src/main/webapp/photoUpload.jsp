<%@ page language="java" pageEncoding="UTF-8" session="true"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <title>Upload</title>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.4.0/css/bootstrap.min.css">
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.4.1/jquery.min.js"></script>
    <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.4.0/js/bootstrap.min.js"></script>
</head>
<body>
<div class="container">
    <h1 align="center">Выберете фото для кандидата</h1>
    <img src="<c:url value='/download?id=${param.id}'/>" width="150px" height="150px"/>
        <form action="<c:url value="/photoUpload?id=${param.id}"/>" method="post" enctype="multipart/form-data">
            <div class="checkbox">
                <input type="file" name="file">
            </div>
            <button type="submit" class="btn btn-default">Загрузить</button>
        </form>
    <a href="<c:url value="/candidates.do"/>">
        <h3 align="center" >
            <font color="black">
                Вернуться к списку кандидатов
            </font>
        </h3>
    </a>
</div>
</body>
</html>