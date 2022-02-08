<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core" %>
<!doctype html>
<html lang="en">
<head>
    <!-- Required meta tags -->
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">

    <!-- Bootstrap CSS -->
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css">
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.4.1/css/bootstrap.min.css"
          integrity="sha384-Vkoo8x4CGsO3+Hhxv8T/Q5PaXtkKtu6ug5TOeNV6gBiFeWPGFN9MuhOf23Q9Ifjh" crossorigin="anonymous">
    <script src="https://code.jquery.com/jquery-3.4.1.slim.min.js"
            integrity="sha384-J6qa4849blE2+poT4WnyKhv5vZF5SrPo0iEjwBvKU7imGFAV0wwj1yYfoRSJoZ+n" crossorigin="anonymous"></script>
    <script src="https://cdn.jsdelivr.net/npm/popper.js@1.16.0/dist/umd/popper.min.js"
            integrity="sha384-Q6E9RHvbIyZFJoft+2mJbHaEWldlvI9IOYy5n3zV9zzTtmI3UksdQRVvoxMfooAo" crossorigin="anonymous"></script>
    <script src="https://stackpath.bootstrapcdn.com/bootstrap/4.4.1/js/bootstrap.min.js"
            integrity="sha384-wfSDF2E50Y2D1uUdj0O3uMBJnjuUD4Ih7YwaYd1iqfktj0Uod8GCExl3Og8ifwB6" crossorigin="anonymous"></script>
    <title>Работа мечты</title>
</head>
<script src="https://code.jquery.com/jquery-3.4.1.min.js" ></script>
<script>
    $(document).ready(function () {
        $.ajax({
            type: 'GET',
            url: 'http://localhost:8080/dreamjob/lastDayPosts',
            dataType: 'json'
        }).done(function (data) {
            for (var post of data) {
                $('#postId').append(`<tr>
                                    <td>${post.name}</td>
                                    <td>${post.description}</td>
                                    <td>${post.created}</td>
                                    </tr>`
                )
            };
        }).fail(function (err) {
            console.log(err);
        });
    });
</script>
<script>
    $(document).ready(function () {
        $.ajax({
            type: 'GET',
            url: 'http://localhost:8080/dreamjob/lastDayCandidates',
            dataType: 'json'
        }).done(function (data) {
            for (var candidate of data) {
                $('#candidateId').append(`<tr>
                                        <td>${candidate.nameVacancy}</td>
                                        <td>${candidate.name}</td>
                                        <td>${candidate.secondName}</td>
                                        <td>${candidate.cityId.name}</td>
                                        <td>${candidate.createdCandidate}</td>
                                        </tr>`
                )
            };
        }).fail(function (err) {
            console.log(err);
        });
    });
</script>
<body>
<div class="container">
    <div class="row">
        <ul class="nav">
            <li class="nav-item">
                <a class="nav-link" href="<%=request.getContextPath()%>/posts.do">Вакансии</a>
            </li>
            <li class="nav-item">
                <a class="nav-link" href="<%=request.getContextPath()%>/candidates.do">Кандидаты</a>
            </li>
            <li class="nav-item">
                <a class="nav-link" href="<%=request.getContextPath()%>/post.do">Добавить вакансию</a>
            </li>
            <li class="nav-item">
                <a class="nav-link" href="<%=request.getContextPath()%>/candidate.do">Добавить кондидата</a>
            </li>
            <div style="position:absolute; right:360px;">
                <c:if test="${user != null}">
                    <ul class="nav">
                        <li class="nav-item">
                            <a class="nav-link" href="<%=request.getContextPath()%>/login.jsp">
                                <c:out value="${user.name}"/>
                            </a>
                        </li>
                        <li class="nav-item">
                            <a class="nav-link" href="<%=request.getContextPath()%>/logout.do">
                                Выйти
                            </a>
                        </li>
                    </ul>
                </c:if>
                <c:if test="${user == null}">
                    <a class="nav-link" href="<%=request.getContextPath()%>/login.jsp">Войти</a>
                </c:if>
            </div>
        </ul>
    </div>
    <div class="row">
        <div class="card" style="width: 100%">
            <div class="card-header">
                Сегодняшние вакансии.
            </div>
            <div class="card-body">
                <table class="table">
                    <thead>
                    <tr>
                        <th scope="col">Названия</th>
                        <th scope="col">Описание</th>
                        <th scope="col">Дата создания</th>
                    </tr>
                    </thead>
                    <tbody id="postId">
                    </tbody>
                </table>
            </div>
        </div>
    </div>
    <div class="row pt-3">
        <div class="card" style="width: 100%">
            <div class="card-header">
                Сегодняшние кандидаты.
            </div>
            <div class="card-body">
                <table class="table">
                    <thead>
                    <tr>
                        <th scope="col">Претендует на должность</th>
                        <th scope="col">Имя</th>
                        <th scope="col">Фамилия</th>
                        <th scope="col">Город</th>
                        <th scope="col">Дата создания</th>
                    </tr>
                    </thead>
                    <tbody id="candidateId">
                    </tbody>
                </table>
            </div>
        </div>
    </div>
</div>
</body>
</html>