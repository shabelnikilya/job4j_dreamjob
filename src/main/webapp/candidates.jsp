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
<body>
<div class="container pt-3">
    <div class="row">
        <div class="card" style="width: 100%">
            <div class="card-header">
                <div style="float: left;"><font size="6">Кандидаты</font></div>
                    <c:if test="${user != null}">
                        <div style="position:absolute; right:0;"><font size="4"><a class="nav-link" href="<%=request.getContextPath()%>/login.jsp"><c:out value="${user.name}"/></a></font></div>
                        <br><div style="position:absolute; right:0;"><font size="4"><a class="nav-link" href="<%=request.getContextPath()%>/logout.do">Выйти</a></font></div>
                    </c:if>
            </div>
            <div class="card-body">
                <table class="table">
                    <thead>
                    <tr>
                        <th scope="col">Претендует на должность</th>
                        <th scope="col">Имя</th>
                        <th scope="col">Фамилия</th>
                        <th scope="col">Фотография</th>
                    </tr>
                    </thead>
                    <tbody>
                        <c:forEach items="${candidates}" var="can">
                            <tr>
                                <td>
                                    <a href='<c:url value="/candidate/edit.jsp?id=${can.id}"/>'>
                                        <i class="fa fa-edit mr-3"></i>
                                    </a>
                                    <a href='<c:url value="/removeCandidate?id=${can.id}"/>'>
                                            &#10006
                                    </a>
                                    <c:out value="${can.nameVacancy}"/>
                                </td>
                                <td><c:out value="${can.name}"/></td>
                                <td><c:out value="${can.secondName}"/></td>
                                <td>
                                    <img src="<c:url value='/download?id=${can.id}'/>" width="100px" height="70px"/>
                                    <br>
                                    <a href='<c:url value="/photoUpload?id=${can.id}"/>'>
                                            <button type="submit" style="height:25px;width:100px">Добавить</button>
                                    </a>
                                    <br>
                                    <a href='<c:url value="/removePhoto?id=${can.id}"/>'>
                                        <button type="submit" style="height:25px;width:100px">Удалить</button>
                                    </a>
                                </td>
                            </tr>
                        </c:forEach>
                    </tbody>
                </table>
            </div>
        </div>
    </div>
</div>
</body>
</html>