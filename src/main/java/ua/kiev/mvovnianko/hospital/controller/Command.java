package ua.kiev.mvovnianko.hospital.controller;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

import static ua.kiev.mvovnianko.hospital.utils.UtilConstants.LANGUAGE;
import static ua.kiev.mvovnianko.hospital.utils.UtilConstants.localize;

/**
 * Main interface for the Command pattern implementation.
 *
 * @author M.Vovnianko
 */
public interface Command {
    /**
     * Execution method for command.
     *
     * @return Address to go once the command is executed.
     */
    String execute(HttpServletRequest request, HttpServletResponse response)
            throws IOException, ServletException;

    default String getLang(HttpServletRequest request) {

        HttpSession session = request.getSession();

        String lang = (String) session.getAttribute(LANGUAGE);

        if (lang == null)
            return "en";
        return lang;
    }
}

