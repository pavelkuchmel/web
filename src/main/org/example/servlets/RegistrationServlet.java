package main.org.example.servlets;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import main.org.example.jdbc.dao.abs.UserDAO;
import main.org.example.jdbc.dao.impl.UserDAOImpl;
import main.org.example.util.EmailSender;
import main.org.example.util.EncryptDecryptUtils;
import main.org.example.model.User;

import java.io.IOException;
import java.io.PrintWriter;

@WebServlet("/registration")
public class RegistrationServlet extends HttpServlet {

    private UserDAO dao = new UserDAOImpl();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        RequestDispatcher rd = req.getRequestDispatcher("/reg.html");
        rd.forward(req, resp); //move forward with the same params and so on

    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("text/html");

        String name = req.getParameter("name");
        String password1 = req.getParameter("password1");
        String password2 = req.getParameter("password2");
        String email = req.getParameter("email");

        //EmailSender.send(email, "Application registry", "Login: " + name + " Password1: " + password1 + " Password2: " + password2);
        resp.getWriter().println("Hi. Message send to your email: " + email);

        //1. проверка валидации
        User userFromGUI = new User();
        userFromGUI.setName(name.trim());
        userFromGUI.setEmail(email.trim().toLowerCase());
        userFromGUI.setPwd(EncryptDecryptUtils.encrypt(password1));

        try {
            boolean created = dao.create(userFromGUI);

            //2. отправить сообщение с инструкцией

            if (created) {
                String token = EncryptDecryptUtils.encrypt(email);
                String url = "http://localhost:8080/web_app_war/activate?token=" + token;
                EmailSender.send(email, "APP REG", "<h1>Click <a href='" + url + "'>here</a> to activated</h1>");
            }else {
                PrintWriter pw = resp.getWriter();
                pw.println("Error");
                System.out.println("Error");
            }

            //3. показать больше инструкций в респонс

        }catch (Exception e){
            PrintWriter pw = resp.getWriter();
            pw.println("<details><summary>Tech Error</summary>" +
                    "  <p>"+e.getMessage() +"</p></details>");
            e.printStackTrace();
        }

//        2. отправить сообщение с инструкцией

//        if (created) {
//            String token = EncryptDecryptUtils.encrypt(email);
//            String url = "http://localhost:8080/web_app_war/activate?token=" + token;
//            EmailSender.send(email, "APP REG", "<h1>Click <a href='" + url + "'>here</a> to activated</h1>");
//        }else {
//            System.out.println("Error");
//        }

//        3. показать больше инструкций в респонс
    }
}
