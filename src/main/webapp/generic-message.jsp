<%--
  Created by IntelliJ IDEA.
  User: zarko
  Date: 27.11.2023
  Time: 20:04
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>INFO</title>
</head>
<body>
    <jsp:include page="header.jsp"></jsp:include>
    <h1><%=request.getAttribute("msg")%></h1>
</body>
</html>
