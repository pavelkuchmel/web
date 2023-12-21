<%@ page import="main.org.example.model.Passport" %>
<%@ page import="java.util.Set" %>
<%@ page import="main.org.example.model.Office" %>
<%@ page import="main.org.example.jdbc.dao.abs.PassportDAO" %>
<%@ page import="main.org.example.jdbc.dao.abs.OfficeDAO" %>
<%@ page import="main.org.example.model.Employee" %>
<%@ page import="main.org.example.jdbc.dao.impl.OfficeDAOImpl" %>
<%@ page import="main.org.example.jdbc.dao.impl.PassportDAOImpl" %><%--
  Created by IntelliJ IDEA.
  User: zarko
  Date: 22.11.2023
  Time: 19:19
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Create Employee</title>
    <link rel="stylesheet" href="css/index.css">
</head>
<body>
<hgroup>
    <h1>Registration</h1>
</hgroup>
<%
    Set<Office> offices = (Set<Office>) request.getAttribute("offices");
%>
<form action="employees" method="post">
    <input type="hidden" name="action" value="C">
    <div class="group">
        <input type="text" name="name"><span class="highlight"></span><span class="bar"></span>
        <label>Name</label>
    </div>
    <div class="group">
        <input type="text" name="last_name"><span class="highlight"></span><span class="bar"></span>
        <label>Last Name</label>
    </div>
    <div class="group">
        <input type="number" name="age"><span class="highlight"></span><span class="bar"></span>
        <label>Age</label>
    </div>
    <div class="group">
        <select name="office_id">
            <%
                for (Office office : offices){
            %>
                <option value="<%=office.getId()%>"><%=office.getTitle()%></option>
            <%
                }
            %>
        </select>
    </div>
    <%--Passport form--%>
    <div class="group">
        <input type="text" name="personal_id"><span class="highlight"></span><span class="bar"></span>
        <label>Personal ID</label>
    </div>
    <div class="group">
        <input type="text" name="ind_id"><span class="highlight"></span><span class="bar"></span>
        <label>Ind ID</label>
    </div>
    <div class="group">
        <input type="date" name="exp_ts"><span class="highlight"></span><span class="bar"></span>
        <label>Exp Date</label>
    </div>
        <button type="submit" class="button buttonBlue">Registration
        <div class="ripples buttonRipples"><span class="ripplesCircle"></span></div>
    </button>
</form>
<script src="js/index.js"></script>
</body>
</html>
