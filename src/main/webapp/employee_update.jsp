<%@ page import="main.org.example.model.Employee" %>
<%@ page import="java.util.Map" %>
<%@ page import="java.util.Set" %>
<%@ page import="main.org.example.jdbc.dao.abs.OfficeDAO" %>
<%@ page import="main.org.example.model.Office" %>
<%@ page import="main.org.example.jdbc.dao.impl.OfficeDAOImpl" %>
<%@ page import="main.org.example.model.Passport" %>
<%@ page import="main.org.example.jdbc.dao.abs.PassportDAO" %>
<%@ page import="main.org.example.jdbc.dao.impl.PassportDAOImpl" %><%--
  Created by IntelliJ IDEA.
  User: zarko
  Date: 21.11.2023
  Time: 9:53
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Employee Update</title>
    <link rel="stylesheet" href="css/table.css">
</head>
<body>
    <%
        Employee employee = (Employee) request.getAttribute("empl");
        OfficeDAO officeDAO = new OfficeDAOImpl();
        PassportDAO passportDAO = new PassportDAOImpl();
        Set<Office> offices = officeDAO.all();
        Set<Passport> passports = passportDAO.all();
    %>
    <form action="employees" method="post" class="container-employees_update">
        <input type="hidden" name="action" value="U">
        <input type="hidden" name="id" value="<%=employee.getId()%>">
        <table class="table_dark">
            <tr>
                <th>Field name</th>
                <th>Old employee</th>
                <th>New employee</th>
            </tr>
            <tr>
                <td>Name</td>
                <td><%=employee.getName()%></td>
                <td><input type="text" name="name"></td>
            </tr>
            <tr>
                <td>Last Name</td>
                <td><%=employee.getLastName()%></td>
                <td><input type="text" name="last_name"></td>
            </tr>
            <tr>
                <td>Age</td>
                <td><%=employee.getAge()%></td>
                <td><input type="text" name="age"></td>
            </tr>
            <tr>
                <td>Office</td>
                <td><%=employee.getOffice().getTitle()%></td>
                <td>
                    <select name="office_id">
                        <%
                            for (Office office : offices){
                                %>
                                    <option value="<%=office.getId()%>"><%=office.getTitle()%></option>
                                <%
                            }
                        %>
                    </select>
                </td>
            </tr>
            <tr>
                <th style="text-align: center" colspan="3">Passport</th>
            </tr>
            <tr>
                <td>Identity number</td>
                <td><%=employee.getPassport().getIndID()%></td>
                <td><input type="text" name="ind_id"></td>
            </tr>
            <tr>
                <td>Personal number</td>
                <td><%=employee.getPassport().getPersonalID()%></td>
                <td><input type="text" name="personal_id"></td>
            </tr>
            <tr>
                <td>Expected date</td>
                <td><%=employee.getPassport().getExpTS()%></td>
                <td><input type="date" name="exp_ts"></td>
            </tr>
        </table>
        <button type="submit">Update</button>
    </form>
</body>
</html>
