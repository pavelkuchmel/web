<%@ page import="static main.org.example.util.ServletUtils.*" %><%--
  Created by IntelliJ IDEA.
  User: sharlan_a
  Date: 27.11.2023
  Time: 19:07
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<link rel="stylesheet" href="css/table.css">
<div class="header">
    <div class="menu">
        <ul>
            <li><h3><a href=''>Home</a></h3></li>
            <li><h3><a href='gallery'>Gallery</a></h3></li>
            <li><h3><a href='contact'>Contact</a></h3></li>
            <li><h3><a href='about'>About</a></h3></li>
            <li><%=isUserInSession(request)? getUserFromSession(request).getName() + " <h3><a href='logout'>Logout</a></h3>"
                    : "<h3><a href='login'>Login</a></h3>"%></li>
        </ul>
    </div>
</div>

