<%--
  Created by IntelliJ IDEA.
  User: marina
  Date: 10.02.2020
  Time: 23:28
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<html>
<head>
    <title>Meals</title>
    <style>
        .withoutExcess {
            color: green;
        }
        .withExcess {
            color: red;
        }
    </style>
</head>
<body>
<section>
    <h2><a href="index.html">Home</a></h2>
    <h3>Meals</h3>
    <hr>
    <table border="1">
        <thead>
          <tr>
              <th>Дата/Время</th>
              <th>Описание</th>
              <th>Калории</th>
          </tr>
        </thead>
        <c:forEach items="${meals}" var="meal">
            <jsp:useBean id="meal" scope="page" type="ru.javawebinar.topjava.model.MealTo"></jsp:useBean>
            <tr class="${meal.excess? 'withExcess' : 'withoutExcess'}">
                <td>
                    <fmt:parseDate value="${meal.dateTime}" pattern="y-M-dd'T'H:m" var="parsedDate"></fmt:parseDate>
                    <fmt:formatDate value="${parsedDate}" pattern="yyyy-MM-dd HH:mm"></fmt:formatDate>
                </td>
                <td>${meal.description}</td>
                <td>${meal.calories}</td>
            </tr>
        </c:forEach>
    </table>
</section>
</body>
</html>
