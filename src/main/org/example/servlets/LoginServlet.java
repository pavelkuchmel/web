package main.org.example.servlets;

import jakarta.servlet.DispatcherType;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import main.org.example.jdbc.dao.impl.UserDAOImpl;
import main.org.example.model.User;
import main.org.example.util.EncryptDecryptUtils;
import main.org.example.util.ServletUtils;

import static main.org.example.util.ServletUtils.*;

import java.io.IOException;
import java.io.PrintWriter;

@WebServlet("/login")
public class LoginServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) {
//        RequestDispatcher rd = req.getRequestDispatcher("/log.html");
//        rd.forward(req, resp); //move forward with the same params and so on

        forward(req, resp, "/log.html");
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        UserDAOImpl userDAO = new UserDAOImpl();

        // 1st get params
        String email = req.getParameter("email");
        String password = EncryptDecryptUtils.encrypt(req.getParameter("password"));

        // 2nd validation

        //3rd check user
        User user = userDAO.findByEmail(email.trim());
        if(user != null){
            // user exists
            if(!user.getPwd().equals(password))
                include(req, resp, "/login.html", "Sorry, wrong password! Try again or <a href='repair'>repair your account</a>");
            else if(!user.getIsActive())
                include(req, resp, "/login.html", "Sorry, you are not activated yet! Please check you email or <a href='activate'>activate your account</a>");
            else {
                // User exists and active
                //return current Session or new Session with 30 min timeout by default
                ServletUtils.saveUserInSession(req, user);
                //forward(req, resp, "/employees"); // forward to servlet
                resp.sendRedirect("employees");
            }
        } else
            include(req, resp, "/login.html", "Sorry, Email not exists! Please <a href='registry'>registry</a>");
        }

// ----------------

        /*@Override
        protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
            resp.setContentType("text/html");

            //PrintWriter pw = resp.getWriter();
            //pw.println("<h1>Login servlet</h1><p><h2>Name: " + req.getParameter("password") + "</h2></p><p><h2>Email: " + req.getParameter("email") + "</h2></p>");

            String email = req.getParameter("email");
            String password = req.getParameter("password");

            UserDAOImpl userDAO = new UserDAOImpl();
            User user = userDAO.findByEmail(email.trim());

            if (user != null){
                if (user.getPwd().equals(EncryptDecryptUtils.encrypt(password))){
                    if (user.getIsActive()){
                    *//*resp.getWriter().println("Login successfully");
                    req.setAttribute("name", user.getName());
                    forward(req, resp, "/home?name=+" + user.getName());*//*
                        //User exists and active.
                        HttpSession session = req.getSession(); //return current Session or new Session with 30 min timeout by default
                        session.setMaxInactiveInterval(15);
                        System.out.println("Login into session # " + session.getId() + "successful!");
                        //Saving user object into session
                        session.setAttribute("logged_user", user);
                        forward(req, resp, "/employees"); // forward to servlet
                    }
                    else {
                        include(req, resp, "/log.html", "Sorry, user is not activated!");
                    }
                }else {
                    include(req, resp, "/log.html", "Sorry, Password incorrect!");
                }
            }else {
                include(req, resp, "/log.html", "Sorry, Email not exists! Please <a href='registration'> registry</a>");
            } */

// --- homework ---

        /*try{
            User user = userDAO.findByEmail(email.trim());
            if (user.getPwd().equals(EncryptDecryptUtils.encrypt(password))){
                if (user.getIsActive()){
                    resp.getWriter().println("Login successfully");
                }
                else {
                    resp.getWriter().println("<h1>User is not activated.</h1><p><h2>Check your email.</h2><p>");
                }
            }else {
                resp.getWriter().println("Password incorrect");
            }
        }catch (Exception e){
            resp.getWriter().println("Email incorrect");
        }*/
}
