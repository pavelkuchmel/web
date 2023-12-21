package main.org.example.servlets;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import main.org.example.util.ServletUtils;


import java.io.IOException;

@WebServlet("/logout")
public class LogoutServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // 1 st Check if user logged in session at all
        HttpSession session = req.getSession();
        if (!ServletUtils.isUserInSession(req)) {
            System.out.println("can't logout");
            ServletUtils.forward(req, resp, "/login.html");
        } else {
            ServletUtils.invalidateSession(req);
            ServletUtils.openGenericMessageJSP(req, resp, "Logout successful");
        }
    }
}