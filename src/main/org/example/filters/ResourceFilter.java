package main.org.example.filters;

import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import main.org.example.util.ServletUtils;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@WebFilter(urlPatterns = {"/img/*", "/css/*", "/js/*"})
public class ResourceFilter implements Filter {
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        Filter.super.init(filterConfig);
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;

        if (new File("C:/JavaCode/web/src/main/webapp"+request.getServletPath()).exists()){
            filterChain.doFilter(request, response);
        }
        else {
            ServletUtils.showErrorJSP(request, response, "Resource not found");
        }
    }

    @Override
    public void destroy() {
        Filter.super.destroy();
    }
}
