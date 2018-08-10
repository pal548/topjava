<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <c:set var="adding" value="${param.action eq 'add'}"/>
    <c:set var="title" value="${adding ? 'Добавление приёма пищи' : 'Редактирование приёма пищи'}"/>
    <title>${title}</title>
</head>
<body>
    <h3>${title}</h3>
    <form method="post" action="meals" enctype="application/x-www-form-urlencoded">
        <c:if test="${!adding}">
            <jsp:useBean id="meal" type="ru.javawebinar.topjava.model.Meal" scope="request"/>
        </c:if>
        <input type="hidden" name="action" value="${param.action}">
        <input type="hidden" name="id" value="${meal.id}">

        <table>
            <tr>
                <td style="text-align: right">Дата</td>
                <td><input type="date" name="date" required value="${adding ? '' : meal.dateTime.toLocalDate().toString()}"><td></td>
            </tr>
            <tr>
                <td style="text-align: right">Время</td>
                <td><input type="time" name="time" required value="${adding ? '' : meal.dateTime.toLocalTime().toString()}"><td></td>
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
