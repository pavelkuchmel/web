<%--
  Created by IntelliJ IDEA.
  User: zarko
  Date: 22.11.2023
  Time: 21:25
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Error</title>
</head>
<body>
    <jsp:include page="header.jsp"></jsp:include>
    <h1><%=request.getAttribute("error-msg")%></h1>
</body>
</html>
