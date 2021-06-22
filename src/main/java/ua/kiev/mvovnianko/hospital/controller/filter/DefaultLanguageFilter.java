package ua.kiev.mvovnianko.hospital.controller.filter;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.IOException;

public class DefaultLanguageFilter implements Filter {
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {

        HttpServletRequest httpRequest = (HttpServletRequest) request;
        HttpSession session = httpRequest.getSession();

        String language = (String) session.getAttribute("language");

        if (language == null || language.isEmpty()) {

            session.setAttribute("language", "ru");

        }

        chain.doFilter(request, response);
    }

    @Override
    public void destroy() {

    }

}
