package main.org.example.util;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import main.org.example.jdbc.dao.abs.UserDAO;
import main.org.example.jdbc.dao.impl.UserDAOImpl;
import main.org.example.model.User;

import java.io.IOException;
import java.util.Set;

public class UserUtils {
    private static UserDAO userDAO =  new UserDAOImpl();

    public static void allUsersTableResponse(HttpServletResponse response, Set<User> users){
        HTMLTableBuilder tableBuilder = new HTMLTableBuilder("Users", true, 2, 3, 14, 14, 14);
        tableBuilder.addTableHeader("id", "name", "email");

        for (User user : users) {
            tableBuilder.addRowValues(user.getId(), user.getName(), user.getEmail());
        }

        String template = IOUtils.readFile("C:\\JavaCode\\web\\src\\main\\webapp\\users.html");
        template = template.replace("<USERS>", tableBuilder.build());

        try {
            response.getWriter().println(template);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static void userTableResponse(HttpServletRequest request, HttpServletResponse response, Set<User> users) throws IOException {
        HTMLTableBuilder tableBuilder = new HTMLTableBuilder("Users", true, 2, 8, 14, 14, 14);
        tableBuilder.addTableHeader("id", "name", "email", "details", "is active", "role", "created", "updated");

        for (User user : users) {
            if (user.getId().equals(Integer.parseInt(request.getParameter("id")))) {
                tableBuilder.addRowValues(user.getId(), user.getName(), user.getEmail(), user.getDetails(), user.getIsActive(), user.getRole().getName(), user.getCreated(), user.getUpdated());
                String template = IOUtils.readFile("C:\\JavaCode\\web\\src\\main\\webapp\\users.html");
                template = template.replace("<USERS>", tableBuilder.build());
                response.getWriter().println(template);
                return;
            }
        }
        showInfo(response, "Incorrect id");
    }
    public static void showInfo(HttpServletResponse response, String message) throws IOException {
        String template = IOUtils.readFile("C:\\JavaCode\\web\\src\\main\\webapp\\users.html");
        template = template.replace("<USERS>", message);
        response.getWriter().println(template);
    }
    public static void showUserToUpdate(HttpServletResponse response, User user) throws IOException {
        String template = IOUtils.readFile("C:\\JavaCode\\web\\src\\main\\webapp\\update.html");
        template = template.replace("<USER>", "<form action=\"users\" method=\"post\">\n" +
                "      <h3>User id: " + user.getId() + "</h3>\n" +
                "      <input type=\"hidden\" name=\"id\" value=\"" + user.getId() + "\" readonly>\n" +
                "      <input type=\"text\" name=\"name\" value=\"" + user.getName() + "\">\n" +
                "      <input type=\"email\" name=\"email\" value=\"" + user.getEmail() + "\">\n" +
                "      <input type=\"password\" name=\"pwdOld\" placeholder=\"Password\">\n" +
                "      <input type=\"password\" name=\"pwd1\" placeholder=\"New password\">\n" +
                "      <input type=\"password\" name=\"pwd2\" placeholder=\"Repeat new password\">\n" +
                "      <input type=\"text\" " + (user.getDetails() == null ? "placeholder=\"Details\"" : "value=\"" + user.getDetails() + "\"") + ">\n" +
                "      <input type=\"text\" name=\"role\" value=\"" + user.getRole().getName() + "\">\n" +
                "      <button type=\"submit\" class=\"button buttonBlue\">Update\n" +
                "      <div class=\"ripples buttonRipples\"><span class=\"ripplesCircle\"></span></div>\n" +
                "      </button>\n" +
                "  </form>");
        System.out.println(template);
        response.getWriter().println(template);
    }
    public static void showUserToUpdate(HttpServletResponse response, User user, String message) throws IOException {
        String template = IOUtils.readFile("C:\\JavaCode\\web\\src\\main\\webapp\\update.html");
        template = template.replace("<USER>", "<form action=\"users\" method=\"post\">\n" +
                "      <h3>User id: " + user.getId() + "</h3>\n" +
                "      <input type=\"hidden\" name=\"id\" value=\"" + user.getId() + "\" readonly>\n" +
                "      <input type=\"text\" name=\"name\" value=\"" + user.getName() + "\">\n" +
                "      <input type=\"email\" name=\"email\" value=\"" + user.getEmail() + "\">\n" +
                "      <input type=\"password\" name=\"pwdOld\" placeholder=\"Password\">\n" +
                "      <input type=\"password\" name=\"pwd1\" placeholder=\"New password\">\n" +
                "      <input type=\"password\" name=\"pwd2\" placeholder=\"Repeat new password\">\n" +
                "      <input type=\"text\" " + (user.getDetails() == null ? "placeholder=\"Details\"" : "value=\"" + user.getDetails() + "\"") + ">\n" +
                "      <input type=\"text\" name=\"role\" value=\"" + user.getRole().getName() + "\">\n" +
                "      <button type=\"submit\" class=\"button buttonBlue\">Update\n" +
                "      <div class=\"ripples buttonRipples\"><span class=\"ripplesCircle\"></span></div>\n" +
                "      </button>\n" +
                "  </form>");
        System.out.println(template);
        response.getWriter().println(template);
    }
}
