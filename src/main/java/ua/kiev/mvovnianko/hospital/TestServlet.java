package ua.kiev.mvovnianko.hospital;

import ua.kiev.mvovnianko.hospital.dao.impl.JDBCUserDAO;
import ua.kiev.mvovnianko.hospital.entity.User;
import ua.kiev.mvovnianko.hospital.service.UserService;
import ua.kiev.mvovnianko.hospital.service.mysqlimpl.MySQLUserService;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;

@WebServlet("/test")
public class TestServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String msg = "JDBC Pool connection successfully created";

        resp.setCharacterEncoding("UTF-8");
        resp.setContentType("text/html");
        PrintWriter out = resp.getWriter();
        out.write("<h1>Hello World Message</h1>");
        out.write("<hr/>");
        out.write("<p>" + msg + "</p>");

        out.write("<hr/>");
        out.write(localize());
        out.write("<hr/>");

        // DB TEST START
        UserService userService = new MySQLUserService(new JDBCUserDAO());
        List<User> patients = null;
        try {
            patients = userService.getUsersByRoleId(3, "birth_date");
        } catch (SQLException throwable) {
            throwable.printStackTrace();
        }
        for(User u : patients){
            out.write("<p>" + u + "</p>");
        }
        // DB TEST END

        out.close();

    }

    private static String localize() {

        Locale russian = new Locale("ru");
        Locale english = new Locale("en");
        ResourceBundle bundle = ResourceBundle.getBundle("messages", russian);
        String message = bundle.getString("test");

        return message;
    }
}
