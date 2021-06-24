package ua.kiev.mvovnianko.hospital.controller.impl.common;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import ua.kiev.mvovnianko.hospital.controller.Command;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static ua.kiev.mvovnianko.hospital.utils.UtilConstants.*;
import static ua.kiev.mvovnianko.hospital.utils.UtilConstants.USER_DELETED;

/**
 * The {@code NoCommand} class is an implementation of
 * {@code Command} interface, that is responsible for emptyCommand situation.
 *
 */
public class NoCommand implements Command {

    private static final Logger LOGGER = LogManager.getLogger(MainPageCommand.class);

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {

        LOGGER.debug("Command noCommand starts");

        final String LANG = getLang(request);

        String message = localize(WRONG_COMMAND, LANG);
        request.setAttribute(MESSAGE, message);

        LOGGER.debug("Command noCommand finish");

        return ERROR_PAGE;
    }
}
