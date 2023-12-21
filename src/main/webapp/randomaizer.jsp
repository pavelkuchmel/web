<%--
  Created by IntelliJ IDEA.
  User: zarko
  Date: 09.12.2023
  Time: 10:10
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>Randomaizer</title>
</head>
<body>
  <jsp:include page="header.jsp"/>
  <form action="random" method="post">
    <h3>Start number</h3>
    <input type="number" name="a">
    <h3>End number</h3>
    <input type="number" name="b">
    <input type="submit" value="Start">
  </form>
  <c:if test="${rnum != null}">
    <h1>${rnum}</h1>
  </c:if>
</body>
</html>
