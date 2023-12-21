package main.org.example.servlets;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import main.org.example.util.CurrencyParser;
import main.org.example.util.EmailSender;
import main.org.example.util.HTMLTableBuilder;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Date;

import static main.org.example.util.ServletUtils.*;

@WebServlet("/currency")
public class CurrencyServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        infoLog(req, "currency -> doGet()");
        PrintWriter pw = resp.getWriter();

        try{
            String email = req.getParameter("email");
            String usdCurrency = CurrencyParser.getCurrency("840");
            EmailSender.send(email, "USD currency", "<h1>"+usdCurrency+"</h1>");
            infoLog(req, "currency -> send");
            pw.println("check email");
        }catch (Exception e){
            e.printStackTrace();
            pw.println("error on server");
            infoLog(req, "ERROR" + e.getMessage());
        }

    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        infoLog(req, "currency -> doPost()");
        PrintWriter pw = resp.getWriter();

        try{
            String email = req.getParameter("email");
            String usdCurrency = CurrencyParser.getCurrency("840");
            String eurCurrency = CurrencyParser.getCurrency("978");
            String rubCurrency = CurrencyParser.getCurrency("643");
            String cnyCurrency = CurrencyParser.getCurrency("156");
            Date date = new Date();

            HTMLTableBuilder builder = new HTMLTableBuilder("Currencies", true, 4, 4, 5, 5, 5);
            builder.addTableHeader("NAME", "CODE", "VALUE", "DATE");
            builder.addRowValues("USD", "840", usdCurrency, date.toString());
            builder.addRowValues("EUR", "978", eurCurrency, date.toString());
            builder.addRowValues("RUB", "643", rubCurrency, date.toString());
            builder.addRowValues("CNY", "156", cnyCurrency, date.toString());
            String table = builder.build();

            EmailSender.send(email, "Latest currencies", table);
            infoLog(req, "currency -> send");
            resp.setContentType("text/html");
            pw.println(table);
            pw.println("<h1>Check your email</h>");
        }catch (Exception e){
            e.printStackTrace();
            pw.println("error on server");
            infoLog(req, "ERROR" + e.getMessage());
        }
    }
}
