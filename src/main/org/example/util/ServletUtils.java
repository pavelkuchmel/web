package main.org.example.util;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import main.org.example.model.Employee;
import main.org.example.model.User;

import javax.mail.Session;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.http.HttpRequest;
import java.util.Date;

public class ServletUtils {

    public static User getUserFromSession(HttpServletRequest request) {
        HttpSession session = request.getSession();
        Object val = session.getAttribute("logged_user");
        if (val == null) {
            return null;
        } else {
            User user = (User) val;
            infoLog(request, "User with ID = " + user.getId() + " was successfully loaded from session");
            return (User) val;
        }
    }

    public static boolean isUserInSession(HttpServletRequest request){
        return getUserFromSession(request) != null;
    }

    public static void saveUserInSession(HttpServletRequest request, User user){

        if (isUserInSession(request)){
            infoLog(request, "User refreshed in session");
        }

        HttpSession session = request.getSession();
        infoLog(request, "User with ID = " + user.getId() + " was successfully successfully saved in session # " + session.getId());
        request.getSession().setAttribute("logged_user", user);
    }

    public static void invalidateSession(HttpServletRequest request){
        request.getSession().invalidate();
        System.out.println("Session # " + request.getSession().getId() + " was invalidated successfully!");

    }

     public static void infoLog(HttpServletRequest request, String msg){

         request.getMethod();
         request.getContextPath();
         request.getPathInfo();
         request.getServletPath();
         request.getRequestURI();
         request.getRequestURL();

         System.out.println(String.format("%s INFO [%s] Method: %s | Path: %s | %s",
                 new Date().toString(), Thread.currentThread().getName(), request.getMethod(), request.getServletPath(), msg));
     }

     public static void forward(HttpServletRequest request, HttpServletResponse response, String path) {
         infoLog(request, "FORWARD to -> " + path);
         try {
             request.getRequestDispatcher(path).forward(request, response);
         } catch (ServletException e) {
             throw new RuntimeException(e);
         } catch (IOException e) {
             throw new RuntimeException(e);
         }
     }

     public static void include(HttpServletRequest request, HttpServletResponse response, String path, String message){
         infoLog(request, "INCLUDE to -> " + path);
         try {
             response.getWriter().print("<h1>" + message + "</h1>");
             RequestDispatcher rd = request.getRequestDispatcher(path);
             rd.include(request, response);
         } catch (IOException | ServletException e) {
             throw new RuntimeException(e);
         }
     }

    public static void openJSP(HttpServletRequest request, HttpServletResponse response, String jspName) {
        forward(request, response, "/" + jspName + ".jsp");
    }

    public static void openGenericMessageJSP(HttpServletRequest request, HttpServletResponse response, String msg) {
        request.setAttribute("msg", msg);
        openJSP(request, response, "generic-message");
    }
    public static void showErrorJSP(HttpServletRequest request, HttpServletResponse response, String msg) {
        request.setAttribute("error-msg", msg);
        openJSP(request, response, "generic-error");
    }

    public static Object compareToReplace(Object object, Object objOld, Object objNew) throws InvocationTargetException, IllegalAccessException {

         Method[] methods = object.getClass().getDeclaredMethods();

        for (Method method : methods){
            if (method.getName().toLowerCase().startsWith("get")){
                if (method.invoke(objOld) != null && method.invoke(objNew) != null){
                    System.out.println(method.getName() + ": " + method.invoke(objOld).equals(method.invoke(objNew)));
                }
                else {
                    System.out.println(method.getName() + ": null");
                }
            }
        }
         return null;
    }
}
