<%@ page import="java.time.format.DateTimeFormatter" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>Meals</title>
</head>
<body>
<table border="1" cellpadding="4">
    <tr>
        <th>Время</th>
        <th>Описание</th>
        <th>Калории</th>
        <th colspan="2">Действия</th>
    </tr>
    <c:forEach items="${meals}" var="meal">
        <jsp:useBean id="meal" type="ru.javawebinar.topjava.model.MealWithExceed"/>
        <tr style='color: ${meal.exceed ? "red" : "green"}'>
            <jsp:useBean id="df" type="java.time.format.DateTimeFormatter" scope="request"/>
            <td><%=df.format(meal.getDateTime())%></td>
            <td>${meal.description}</td>
            <td style="text-align: center">${meal.calories}</td>
            <td style="text-align: center"><a href="meals?action=edit&id=${meal.id}">Редактировать</a></td>
            <td style="text-align: center"><a href="meals?action=delete&id=${meal.id}">Удалить</a></td>
        </tr>
    </c:forEach>
</table>
<a href="meals?action=add">Добавить</a>
</body>
</html>
