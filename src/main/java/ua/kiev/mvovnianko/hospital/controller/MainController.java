package ua.kiev.mvovnianko.hospital.controller;

import ua.kiev.mvovnianko.hospital.controller.impl.common.NoCommand;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

import static ua.kiev.mvovnianko.hospital.utils.UtilConstants.LANGUAGE;
import static ua.kiev.mvovnianko.hospital.utils.UtilConstants.localize;
import static ua.kiev.mvovnianko.hospital.utils.UtilData.COMMANDS_MAP;

public class MainController extends HttpServlet {

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response)
            throws IOException, ServletException {
        process(request, response);
    }

    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response)
            throws IOException, ServletException {
        process(request, response);
    }

    /**
     * Main method of this controller.
     */
    private void process(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // extract command name from the request
        String commandName = request.getParameter("command");
        // obtain command object by its name or default command "noCommand"
        Command command = COMMANDS_MAP.getOrDefault(commandName, new NoCommand());

        // execute command and get forward address
        String forward = command.execute(request, response);

        // if the forward address is not null go to the address
        if (forward != null) {
            RequestDispatcher dispatcher = request.getRequestDispatcher(forward);

            dispatcher.forward(request, response);
        }
    }
}
