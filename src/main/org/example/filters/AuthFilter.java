package main.org.example.filters;

import main.org.example.model.User;
import main.org.example.util.PropsUtils;
import main.org.example.util.ServletUtils;

import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

//@WebFilter("/*")
public class AuthFilter implements Filter {

    private PropsUtils props;
    private IOException ioException;

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        System.out.println("Init AuthFilter ");
        try {
            props = new PropsUtils("C:\\JavaCode\\web\\src\\main\\resources\\ROLES_URL_PATTERNS_MAPPING.properties");
        } catch (IOException e) {
            ioException = e;
            e.printStackTrace();
        }
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) servletRequest;
        HttpServletResponse resp = (HttpServletResponse) servletResponse;

        if (ioException != null) {
            ServletUtils.showErrorJSP(req, resp, "Internal Server Error. Properties loading:"
                    + ioException.getMessage());
        }


        //1 st check if client request is valid
        // can be direct image , css, ...

//        ServletUtils.info((HttpServletRequest)servletRequest, "AuthFilter");
//        String path = ((HttpServletRequest)servletRequest).getServletPath();
//
//
//        if(path.contains(".")){
//            ServletUtils.showError((HttpServletRequest)servletRequest,
//                    (HttpServletResponse)servletResponse, "Invalid request");
//            return;
//        } else {
//            // pass
//            filterChain.doFilter(servletRequest, servletResponse);
//        }

        // 2
        String path = req.getServletPath();
        // allow content files
        if (path.endsWith(".css") || path.endsWith(".js") || path.endsWith(".html")) {
            filterChain.doFilter(servletRequest, servletResponse);
            return;
        }
        User user = ServletUtils.getUserFromSession(req);
        if (user == null) { // Unknown
            // /employees
            if (props.getUrlPatterns("Unknown").contains(path)) {
                filterChain.doFilter(servletRequest, servletResponse);
            } else {
                ServletUtils.showErrorJSP(req, resp, "Please login");
            }
        } else if (user.getRole().getName().equals("Admin")) {
            filterChain.doFilter(servletRequest, servletResponse);
        } else if (user.getRole().getName().equals("Manager")) {
            if (props.getUrlPatterns("Manager").contains(path)) {
                filterChain.doFilter(servletRequest, servletResponse);
            } else {
                ServletUtils.showErrorJSP(req, resp, "Please login");
            }
        } else if (user.getRole().getName().equals("General User")) {
            if (props.getUrlPatterns("General User").contains(path)) {
                filterChain.doFilter(servletRequest, servletResponse);
            } else {
                ServletUtils.showErrorJSP(req, resp, "Please login");
            }
        }


    }

    @Override
    public void destroy() {
        System.out.println("Destroy AuthFilter ");
    }
}