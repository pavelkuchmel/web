<%@ page import="java.util.Set" %>
<%@ page import="main.org.example.model.Employee" %>
<%@ page import="main.org.example.util.ServletUtils" %>
<%@ page import="main.org.example.model.User" %>
<%@ page import="main.org.example.model.Role" %>
<%@ page import="java.util.List" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="java.util.Arrays" %>
<%@ page import="static main.org.example.util.SecUtils.hasRole" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<jsp:useBean id="sutils" class="main.org.example.util.SecUtils"/>

<html>
<head>
    <title>Employees</title>
    <link rel="stylesheet" href="css/table.css">
</head>
<body>
<jsp:include page="header.jsp"></jsp:include>

<c:if test="${empls.isEmpty()}">
    <h1>No Employees found!</h1>
</c:if>
<c:if test="${ not empls.isEmpty()}">
    <table class="table_dark">
        <tr>
            <th>ID</th>
            <th>NAME</th>
            <th>LAST NAME</th>
            <th>AGE</th>
            <th>OFFICE</th>
            <th>PASSPORT</th>
            <th>UPDATED</th>
            <th>CREATED</th>
            <c:if test="${hasRole(request, 'Admin', 'Manager')}">
                <th>UPDATED</th>
            </c:if>
            <c:if test="${hasRole(request, 'Admin')}">
                <th>DELETE</th>
            </c:if>
        </tr>
        <c:forEach items="${empls}" var="empl">
            <tr>
                <td>${empl.id}</td>
                <td>${empl.name}</td>
                <td>${empl.lastName}</td>
                <td>${empl.age}</td>
                <td>${empl.office.title}</td>
                <td>${empl.passport.indID}</td>
                <td>${empl.updatedTs}</td>
                <td>${empl.createdTs}</td>
                <c:if test="${hasRole(request, 'Admin', 'Manager')}">
                    <td><a href="employees?action=U&id=${empl.id}"> UPDATE </a></td>
                </c:if>
                <c:if test="${hasRole(request, 'Admin', 'Manager')}">
                    <td><a href="employees?action=D&id=${empl.id}"> DELETE </a></td>
                </c:if>
                <%--<% if(hasRole(request, "Admin", "Manager")){ %>
                <td><a href="employees?action=U&id=${empl.id}"> UPDATE </a> </td>
                <%}%>
                <% if(hasRole(request, "Admin")){ %>
                <td><a href="employees?action=D&id=${empl.id}"> DELETE </a> </td>
                <%}%>--%>
            </tr>
        </c:forEach>

    </table>
</c:if>
<br><a href="employees?action=C"> CREATE </a>
</body>
</html>
