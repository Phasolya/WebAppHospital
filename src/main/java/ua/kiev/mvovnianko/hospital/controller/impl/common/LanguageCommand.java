package ua.kiev.mvovnianko.hospital.controller.impl.common;

import ua.kiev.mvovnianko.hospital.controller.Command;
import ua.kiev.mvovnianko.hospital.controller.impl.common.MainPageCommand;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

import static ua.kiev.mvovnianko.hospital.utils.UtilConstants.LANGUAGE;

public class LanguageCommand implements Command {
    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {

        HttpSession session = request.getSession();

        String lang = request.getParameter(LANGUAGE);

        session.setAttribute(LANGUAGE, lang);

        return new MainPageCommand().execute(request, response);

    }
}
