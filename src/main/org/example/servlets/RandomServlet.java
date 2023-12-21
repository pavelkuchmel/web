package main.org.example.servlets;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import main.org.example.util.ServletUtils;

import java.io.IOException;
import java.util.Random;

@WebServlet("/random")
public class RandomServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        ServletUtils.forward(req, resp, "/randomaizer.jsp");
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        if (!req.getParameter("a").isEmpty() && !req.getParameter("b").isEmpty()){
            if (Integer.parseInt(req.getParameter("a")) < Integer.parseInt(req.getParameter("b"))){
                req.setAttribute("rnum", new Random().nextInt(Integer.parseInt(req.getParameter("a")), Integer.parseInt(req.getParameter("b"))));
            }
        }
        ServletUtils.openJSP(req, resp, "randomaizer");
    }
}
