package ua.kiev.mvovnianko.hospital.controller.impl.common;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import ua.kiev.mvovnianko.hospital.controller.Command;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static ua.kiev.mvovnianko.hospital.utils.UtilConstants.*;
import static ua.kiev.mvovnianko.hospital.utils.UtilConstants.WRONG_COMMAND;

/**
 * The {@code UnauthorizedAccessCommand} class is an implementation of
 * {@code Command} interface, that is responsible for hacking attempt.
 *
 */
public class UnauthorizedAccessCommand implements Command {

    private static final Logger LOGGER = LogManager.getLogger(MainPageCommand.class);

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {

        LOGGER.debug("Command UnauthorizedAccess starts");

        final String LANG = getLang(request);

        String message = localize(HACKING_ATTEMPT, LANG);
        request.setAttribute(MESSAGE, message);

        LOGGER.debug("Command UnauthorizedAccess finish");

        return ERROR_PAGE;
    }
}
