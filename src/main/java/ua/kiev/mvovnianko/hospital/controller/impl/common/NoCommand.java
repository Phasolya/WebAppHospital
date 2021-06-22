package ua.kiev.mvovnianko.hospital.controller.impl.common;

import ua.kiev.mvovnianko.hospital.controller.Command;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static ua.kiev.mvovnianko.hospital.utils.UtilConstants.*;
import static ua.kiev.mvovnianko.hospital.utils.UtilConstants.USER_DELETED;

public class NoCommand implements Command {
    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {

        final String LANG = getLang(request);

        String message = localize(WRONG_COMMAND, LANG);
        request.setAttribute("message", message);

        return ERROR_PAGE;
    }
}
