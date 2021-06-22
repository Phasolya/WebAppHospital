package ua.kiev.mvovnianko.hospital.controller.filter;

import ua.kiev.mvovnianko.hospital.controller.Command;
import ua.kiev.mvovnianko.hospital.controller.impl.common.NoCommand;
import ua.kiev.mvovnianko.hospital.entity.UserRole;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.IOException;

import static ua.kiev.mvovnianko.hospital.utils.UtilData.COMMANDS_MAP;

public class CommandAccessFilter implements Filter {
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {

        HttpServletRequest httpRequest = (HttpServletRequest) request;
        HttpSession session = httpRequest.getSession();

        UserRole userRole = (UserRole) session.getAttribute("userRole");

        if (userRole == null) {

            userRole = UserRole.GUEST;

            session.setAttribute("userRole", userRole);

        }

        String commandName = request.getParameter("command");
        Command command = COMMANDS_MAP.getOrDefault(commandName, new NoCommand());

        String commandClassName = command.getClass().getName();
        String userRoleName = userRole.getName().toLowerCase();

        if (!(commandClassName.contains("common") || commandClassName.contains(userRoleName))) {

            request.setAttribute("command", "unauthorizedAccess");

        }

        chain.doFilter(request, response);
    }

    @Override
    public void destroy() {

    }

}
