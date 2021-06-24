package ua.kiev.mvovnianko.hospital.controller.impl.common;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import ua.kiev.mvovnianko.hospital.controller.Command;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

import static ua.kiev.mvovnianko.hospital.utils.UtilConstants.GUEST_PAGE;

/**
 * The {@code LogoutCommand} class is an implementation of
 * {@code Command} interface, that is responsible for logout.
 *
 */
public class LogoutCommand implements Command {

    private static final Logger LOGGER = LogManager.getLogger(LogoutCommand.class);

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {

        LOGGER.debug("Command logout starts");

        HttpSession session = request.getSession(false);
        if (session != null)
            session.invalidate();

        LOGGER.debug("Command logout finished");
        return GUEST_PAGE;

    }
}