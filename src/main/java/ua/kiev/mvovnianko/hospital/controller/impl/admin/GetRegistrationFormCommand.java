package ua.kiev.mvovnianko.hospital.controller.impl.admin;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import ua.kiev.mvovnianko.hospital.controller.Command;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static ua.kiev.mvovnianko.hospital.utils.UtilConstants.REGISTRAION_FORM_PAGE;

/**
 * The {@code GetRegistrationFormCommand} class is an implementation of
 * {@code Command} interface, that is responsible for getting registration page.
 */
public class GetRegistrationFormCommand implements Command {

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {

        return REGISTRAION_FORM_PAGE;
    }
}
