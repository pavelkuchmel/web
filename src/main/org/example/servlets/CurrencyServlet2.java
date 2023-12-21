package main.org.example.servlets;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import main.org.example.util.CurrencyParser;
import main.org.example.util.ServletUtils;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@WebServlet("/currencies")
public class CurrencyServlet2 extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String usdCurrency = CurrencyParser.getCurrency("840");
        String eurCurrency = CurrencyParser.getCurrency("978");
        String rubCurrency = CurrencyParser.getCurrency("643");
        String cnyCurrency = CurrencyParser.getCurrency("156");

        Map<String, String> currenciesMap = new HashMap<>();
        currenciesMap.put("840", usdCurrency);
        currenciesMap.put("978", eurCurrency);
        currenciesMap.put("643", rubCurrency);
        currenciesMap.put("156", cnyCurrency);

        req.setAttribute("map", currenciesMap);

        ServletUtils.forward(req, resp, "/currencies.jsp");

    }
}
