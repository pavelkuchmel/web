package main.org.example.servlets;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import main.org.example.jdbc.dao.abs.EmployeeDAO;
import main.org.example.jdbc.dao.abs.OfficeDAO;
import main.org.example.jdbc.dao.abs.PassportDAO;
import main.org.example.jdbc.dao.impl.EmployeeDAOImpl;
import main.org.example.jdbc.dao.impl.OfficeDAOImpl;
import main.org.example.jdbc.dao.impl.PassportDAOImpl;
import main.org.example.model.Employee;
import main.org.example.model.Passport;
import main.org.example.util.ServletUtils;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

@WebServlet("/employees")
public class EmployeesServlet extends HttpServlet {
    private final EmployeeDAO employeeDAO = new EmployeeDAOImpl();
    private final OfficeDAO officeDAO = new OfficeDAOImpl();
    private final PassportDAOImpl passportDAO = new PassportDAOImpl();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        // if user is not logged in
        if (!ServletUtils.isUserInSession(req)) {
            req.setAttribute("msg", "Please <a href='login'> login</a> to see Empls list");
            ServletUtils.openJSP(req, resp, "generic-message");
            return;
        }

        if (req.getParameter("action") != null){
            switch (req.getParameter("action")){
                case "D":
                    if (!ServletUtils.getUserFromSession(req).getRole().getName().equals("Admin")){
                        ServletUtils.openGenericMessageJSP(req, resp, "No such rights. Your role is not suitable.");
                        return;
                    }

                    employeeDAO.deleteById(Integer.parseInt(req.getParameter("id")));

                    break;
                case "U":
                    String roleName = ServletUtils.getUserFromSession(req).getRole().getName();
                    if (!Arrays.asList("Admin", "Manager").contains(roleName)){
                        ServletUtils.openGenericMessageJSP(req, resp, "No such rights. Your role is not suitable.");
                        return;
                    }
                    //TODO
                    req.setAttribute("empl", employeeDAO.findById(Integer.parseInt(req.getParameter("id"))));
                    ServletUtils.openJSP(req, resp, "employee_update");
                    break;
                case "C":
                    req.setAttribute("offices", officeDAO.all());
                    ServletUtils.openJSP(req, resp, "create-empl");
                    return;
            }
//            if (req.getParameter("action").equals("D")){
//
//            }
//            if (req.getParameter("action").equals("U")){
//                req.setAttribute("empl", employeeDAO.findById(Integer.parseInt(req.getParameter("id"))));
//                ServletUtils.openJSP(req, resp, "employee_update");
//            }
        }

        req.setAttribute("empls", employeeDAO.all());
        ServletUtils.openJSP(req, resp, "employees");
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        if (req.getParameter("action") != null) {
            if (req.getParameter("action").equals("U")) {
                Employee employeeNew = new Employee();
                Employee employeeOld = employeeDAO.findById(Integer.parseInt(req.getParameter("id")));
                Passport passport = new Passport();

                passport.setIndID(req.getParameter("ind_id"));
                passport.setPersonalID(req.getParameter("personal_id"));
                try {
                    passport.setExpTS(new Timestamp((new SimpleDateFormat("yyyy-MM-dd").parse(req.getParameter("exp_ts"))).getTime()));
                } catch (ParseException e) {
                    throw new RuntimeException(e);
                }
                passport.setId(passportDAO.createPassport2(passport));


                employeeNew.setId(Integer.parseInt(req.getParameter("id")));
                employeeNew.setName(req.getParameter("name"));
                employeeNew.setLastName(req.getParameter("last_name"));
                employeeNew.setAge(Integer.parseInt(/*req.getParameter("age")*/"6"));
                employeeNew.setOffice(officeDAO.findById(Integer.parseInt(req.getParameter("office_id"))));
                employeeNew.setPassport(passport);

                try {
                    employeeDAO.updateEmployee(employeeDAO.compareToReplace(new Employee(), employeeOld, employeeNew));
                } catch (InvocationTargetException e) {
                    throw new RuntimeException(e);
                } catch (IllegalAccessException e) {
                    throw new RuntimeException(e);
                }

                resp.sendRedirect("employees");

            }else if (req.getParameter("action").equals("C")){

                Employee employee = new Employee();
                Passport passport = new Passport();

                employee.setName(req.getParameter("name"));
                employee.setLastName(req.getParameter("last_name"));
                employee.setAge(Integer.parseInt(req.getParameter("age")));

                passport.setIndID(req.getParameter("ind_id"));
                passport.setPersonalID(req.getParameter("personal_id"));

                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
                Date date = null;
                try {
                    date = simpleDateFormat.parse(req.getParameter("exp_ts"));
                } catch (ParseException e) {
                    throw new RuntimeException(e);
                }
                System.out.println(new Timestamp(date.getTime()));
                passport.setExpTS(new Timestamp(date.getTime()));

                int passId = passportDAO.createPassport2(passport);
                passport.setId(passId);

                employee.setPassport(passport);
                employee.setOffice(officeDAO.findById(Integer.parseInt(req.getParameter("office_id"))));

                if (employeeDAO.createEmployee(employee)){
                    req.setAttribute("empls", employeeDAO.all());
                    ServletUtils.openJSP(req, resp, "employees");
                }else {
                    ServletUtils.showErrorJSP(req, resp, "Error");
                    System.err.println("ERROR");
                }

                System.out.println(employee);
            }
        }
    }
}
