<%@ page contentType="text/html; charset=UTF-8" %>
<%@ page import="ru.job4j.dream.model.Candidate" %>
<%@ page import="ru.job4j.dream.store.DbStore" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core" %>
<!doctype html>
<html lang="en">
<head>
    <!-- Required meta tags -->
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">

    <!-- Bootstrap CSS -->
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.4.1/css/bootstrap.min.css"
          integrity="sha384-Vkoo8x4CGsO3+Hhxv8T/Q5PaXtkKtu6ug5TOeNV6gBiFeWPGFN9MuhOf23Q9Ifjh" crossorigin="anonymous">
    <script src="https://code.jquery.com/jquery-3.4.1.slim.min.js"
            integrity="sha384-J6qa4849blE2+poT4WnyKhv5vZF5SrPo0iEjwBvKU7imGFAV0wwj1yYfoRSJoZ+n" crossorigin="anonymous"></script>
    <script src="https://cdn.jsdelivr.net/npm/popper.js@1.16.0/dist/umd/popper.min.js"
            integrity="sha384-Q6E9RHvbIyZFJoft+2mJbHaEWldlvI9IOYy5n3zV9zzTtmI3UksdQRVvoxMfooAo" crossorigin="anonymous"></script>
    <script src="https://stackpath.bootstrapcdn.com/bootstrap/4.4.1/js/bootstrap.min.js"
            integrity="sha384-wfSDF2E50Y2D1uUdj0O3uMBJnjuUD4Ih7YwaYd1iqfktj0Uod8GCExl3Og8ifwB6" crossorigin="anonymous"></script>
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css">
    <title>Работа мечты</title>
</head>
    <script src="https://code.jquery.com/jquery-3.4.1.min.js" ></script>
    <script>
        function validate() {
            var rsl = true;
            $('#form').find('input, select').each(function() {
                if ($(this).val() === '') {
                    alert($(this).attr('title'));
                    rsl = false;
                }
            })
            if (document.querySelector('select').value === '0') {
                alert($('#select').attr('title'));
                rsl = false;
            }
            return rsl;
        }
    </script>
    <script>
        $(document).ready(function () {
            $.ajax({
                type: 'GET',
                url: 'http://localhost:8080/dreamjob/allCity',
                dataType: 'json'
            }).done(function (data) {
                for (var city of data) {
                    $('#select').append(`<option value="${city.id}">${city.name}</option>`)
                };
            }).fail(function (err) {
                console.log(err);
            });
        });
    </script>
<body>
<%
    String id = request.getParameter("id");
    Candidate candidate = new Candidate(0, "", "", "");
    if (id != null) {
        candidate = DbStore.instOf().findCandidateById(Integer.parseInt(id));
    }
%>
<div class="container pt-3">
    <div class="row">
        <div class="card" style="width: 100%">
            <div class="card-header">
                <% if (id == null) { %>
                <div style="float: left;"><font size="6">Новый кондидат.</font></div>
                <% } else { %>
                <div style="float: left;"><font size="6">Редактировать кандидата.</font></div>
                <% } %>
                <c:if test="${user != null}">
                    <div style="position:absolute; right:0;">
                        <font size="4">
                            <a class="nav-link" href="<%=request.getContextPath()%>/login.jsp">
                                <c:out value="${user.name}"/>
                            </a>
                        </font>
                    </div>
                    <br><div style="position:absolute; right:0;">
                    <font size="4">
                        <a class="nav-link" href="<%=request.getContextPath()%>/logout.do">
                            Выйти
                        </a>
                    </font>
                    </div>
                </c:if>
            </div>
            <div class="card-body">
                <form id="form" action="<%=request.getContextPath()%>/candidate.do?id=<%=candidate.getId()%>" method="post">
                    <div class="form-group">
                        <label>Название вакансии</label>
                        <input type="text" title="Заполните поле: Название вакансии!" class="form-control"
                               name="nameVacancy" value="<%=candidate.getNameVacancy()%>">
                        <label>Имя</label>
                        <input type="text" title="Заполните поле: Имя!" class="form-control"
                               name="name" value="<%=candidate.getName()%>">
                        <label>Фамилия</label>
                        <input type="text" title="Заполните поле: Фамилия!" class="form-control"
                               name="secondName" value="<%=candidate.getSecondName()%>">
                        <label>Город</label>
                        <select id="select" name="city" title="Выберете город!">
                            <option value="0">Выберете город</option>
                        </select>
                    </div>
                    <button type="submit" class="btn btn-primary" onclick="return validate();">Сохранить</button>
                </form>
            </div>
        </div>
    </div>
</div>
</body>
</html>