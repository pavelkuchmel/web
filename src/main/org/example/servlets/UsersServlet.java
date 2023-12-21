package main.org.example.servlets;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import main.org.example.jdbc.dao.abs.UserDAO;
import main.org.example.jdbc.dao.impl.UserDAOImpl;
import main.org.example.model.User;
import main.org.example.util.EncryptDecryptUtils;
import main.org.example.util.HTMLTableBuilder;
import main.org.example.util.IOUtils;
import main.org.example.util.UserUtils;

import java.io.IOException;
import java.util.Set;

@WebServlet("/users")
public class UsersServlet extends HttpServlet {

    private UserDAO userDAO =  new UserDAOImpl();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        Set<User> users = userDAO.all();
        resp.setContentType("text/html");

        /*if (id != null){
            resp.setContentType("text/html");
            UserDAO userDAO =  new UserDAOImpl();
            User user = userDAO.findById(id);
            HTMLTableBuilder tableBuilder = new HTMLTableBuilder("Users", true, 2, 4, 14, 14, 14);
            tableBuilder.addTableHeader("id", "name", "email", "is active");
            tableBuilder.addRowValues(user.getId().toString(), user.getName(), user.getEmail(), user.getIsActive().toString());
            resp.getWriter().println(tableBuilder.build());
            return;
        }*/

        if (users.size() > 0){
            //HTMLTableBuilder tableBuilder = new HTMLTableBuilder("Users", true, 2, 8, 14, 14, 14);
            //tableBuilder.addTableHeader("id", "name", "email", "details", "is active", "role", "created", "updated");

            if (req.getParameter("id") != null && req.getParameter("action") == null){

                /*for (User user : users) {
                    if (user.getId().equals(Integer.parseInt(req.getParameter("id")))){
                        tableBuilder.addRowValues(user.getId(), user.getName(), user.getEmail(), user.getDetails(), user.getIsActive(), user.getRole().getName(), user.getCreated(), user.getUpdated());
                        String template = IOUtils.readFile("C:\\JavaCode\\web\\src\\main\\webapp\\users.html");
                        template = template.replace("<USERS>", tableBuilder.build());

                        resp.getWriter().println(template);
                        break;
                    }
                }*/

                UserUtils.userTableResponse(req, resp, users);

            }else if (req.getParameter("id") != null && req.getParameter("action") != null){
                if (req.getParameter("action").equals("D")){

                    System.out.println(userDAO.deleteById(Integer.parseInt(req.getParameter("id"))));

                    /*for (User user : users) {
                        tableBuilder.addRowValues(user.getId(), user.getName(), user.getEmail(), user.getDetails(), user.getIsActive(), user.getRole().getName(), user.getCreated(), user.getUpdated());
                    }
                    String template = IOUtils.readFile("C:\\JavaCode\\web\\src\\main\\webapp\\users.html");
                    template = template.replace("<USERS>", tableBuilder.build());

                    resp.getWriter().println(template);*/
                    users = userDAO.all();
                    UserUtils.allUsersTableResponse(resp, users);
                    return;
                }
                if (req.getParameter("action").equals("U")){
                    for (User user : users){
                        if (user.getId().equals(Integer.parseInt(req.getParameter("id")))){
                            UserUtils.showUserToUpdate(resp, user);
                            return;
                        }
                    }
                }

            }else{
                /*for (User user : users) {
                    tableBuilder.addRowValues(user.getId(), user.getName(), user.getEmail(), user.getDetails(), user.getIsActive(), user.getRole().getName(), user.getCreated(), user.getUpdated());
                }
                String template = IOUtils.readFile("C:\\JavaCode\\web\\src\\main\\webapp\\users.html");
                template = template.replace("<USERS>", tableBuilder.build());

                resp.getWriter().println(template);*/
                UserUtils.allUsersTableResponse(resp, users);
            }
        }else {
            UserUtils.showInfo(resp, "Database is empty");
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        User userFromDB = userDAO.findById(Integer.parseInt(req.getParameter("id")));
        User userToUpdate = new User();

        if (req.getParameter("pwdOld").equals(EncryptDecryptUtils.decrypt(userFromDB.getPwd()))) {
            //if ()
            resp.getWriter().println("true");
        }
        else {
            UserUtils.showUserToUpdate(resp, userFromDB, "Incorrect password");
        }
    }
}
