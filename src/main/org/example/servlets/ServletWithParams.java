package main.org.example.servlets;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.PrintWriter;

@WebServlet("/params")
public class ServletWithParams extends HttpServlet{

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String aParam = req.getParameter("a");
        String bParam = req.getParameter("b");

        double a = Double.parseDouble(aParam);
        double b = Double.parseDouble(bParam);
        double s = a + b;

        PrintWriter pw = resp.getWriter();
        pw.println("Result: ");
        pw.println(s);


    }
}
