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
    </tr>
    <% DateTimeFormatter df = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"); %>
    <c:forEach items="${meals}" var="meal">
        <jsp:useBean id="meal" type="ru.javawebinar.topjava.model.MealWithExceed"/>
        <tr style='color: <%= meal.isExceed() ? "red" : "green" %>'>
            <td><%=df.format(meal.getDateTime())%></td>
            <td>${meal.description}</td>
            <td style="text-align: center">${meal.calories}</td>
        </tr>
    </c:forEach>
</table>
</body>
</html>
