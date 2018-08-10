<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <jsp:useBean id="action" type="java.lang.String" scope="request"/>
    <%--<jsp:useBean id="meal" type="ru.javawebinar.topjava.model.Meal" scope="request"/>--%>
    <title>Title</title>
</head>
<body>
    <h3>Добавление приёма пищи</h3>
    <form method="post" action="meals" enctype="application/x-www-form-urlencoded">
        <c:set var="adding" value="${action eq 'add'}"/>
        <c:if test="${!adding}">
            <jsp:useBean id="meal" type="ru.javawebinar.topjava.model.Meal" scope="request"/>
        </c:if>
        <input type="hidden" name="action" value="${action}">
        <input type="hidden" name="id" value="${meal.id}">

        <table>
            <tr>
                <td style="text-align: right">Дата и время</td>
                <td><input type="datetime-local" name="dateTime" required value="${adding ? '' : meal.dateTime.toString()}"><td></td>
            </tr>
            <tr>
                <td style="text-align: right">Описание</td>
                <td><input type="text" name="description" required size="40" value="${adding ? '' : meal.description}"><td></td>
            </tr>
            <tr>
                <td style="text-align: right">Калории</td>
                <td><input type="number" name="calories" required size="40" value="${adding ? '' : meal.calories}"><td></td>
            </tr>
        </table>

        <button type="submit">Сохранить</button>
        <button onclick="window.history.back()">Отменить</button>


    </form>
</body>
</html>
