package main.org.example.servlets;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import main.org.example.dao.UserDAO;
import main.org.example.model.User;
import main.org.example.util.EncryptDecryptUtils;

import java.io.IOException;

@WebServlet("/activate")
public class ActivateServlet extends HttpServlet {

    private UserDAO dao = new UserDAO();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("text/html");

        String token = req.getParameter("token");
        String email = EncryptDecryptUtils.decrypt(token);
        User userFromDB = dao.findByEmail(email);

        if (userFromDB != null){
            if (userFromDB.getIsActive()){
                resp.getWriter().println("Already activated");
            }else {
                dao.activated(userFromDB);
                System.out.println("Activated");
                //TODO login servlet
                resp.getWriter().println("<a href=''> login </a>");
            }
        }else {
            resp.getWriter().println("Incorrect request");
        }
    }
}
