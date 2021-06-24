package ua.kiev.mvovnianko.hospital.controller.impl.common;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import ua.kiev.mvovnianko.hospital.controller.Command;
import ua.kiev.mvovnianko.hospital.controller.impl.admin.GetDoctorsCommand;
import ua.kiev.mvovnianko.hospital.controller.impl.common.MainPageCommand;
import ua.kiev.mvovnianko.hospital.service.UserService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

import static ua.kiev.mvovnianko.hospital.utils.UtilConstants.LANGUAGE;

/**
 * The {@code LanguageCommand} class is an implementation of
 * {@code Command} interface, that is responsible for localization.
 *
 */
public class LanguageCommand implements Command {

    private static final Logger LOGGER = LogManager.getLogger(LanguageCommand.class);

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {

        LOGGER.debug("Command change language starts");

        HttpSession session = request.getSession();

        String lang = request.getParameter(LANGUAGE);

        session.setAttribute(LANGUAGE, lang);

        LOGGER.debug("Command change language finished");

        return new MainPageCommand().execute(request, response);

    }
}
