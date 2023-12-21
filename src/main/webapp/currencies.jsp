<%@ page import="java.util.Date" %>
<%@ page import="java.util.Map" %><%--
  Created by IntelliJ IDEA.
  User: zarko
  Date: 20.11.2023
  Time: 19:40
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>Currencies</title>
</head>
<body>
  <h2>Currencies Date: <%=getCurrentServerDate()%></h2>

  <%!
    Date getCurrentServerDate(){
      return new Date();
    }
  %>

  <table border="1px">
    <thead>
      <tr>
        <th>
          #
        </th>
        <th>
          Currency name
        </th>
        <th>
          Rate
        </th>
      </tr>
    </thead>

    <tbody>
    <c:forEach items="${map}" var="pair">
      <tr>
        <td>#</td>
        <td>${pair.getKey()}</td>
        <td>${pair.getValue()}</td>
      </tr>
    </c:forEach>
    </tbody>

<%--    <tbody>--%>
<%--      <%--%>
<%--        Map<String, String> map = (Map<String, String>)request.getAttribute("map");--%>
<%--        int number = 0;--%>
<%--        for (Map.Entry<String, String> entry : map.entrySet()){%>--%>
<%--          <tr>--%>
<%--            <td><%= ++number %></td>--%>
<%--            <td><%= entry.getKey() %></td>--%>
<%--            <td><%= entry.getValue() %></td>--%>
<%--          </tr>--%>
<%--      <%}--%>
<%--      %>--%>
<%--    </tbody>--%>
  </table>
</body>
</html>
