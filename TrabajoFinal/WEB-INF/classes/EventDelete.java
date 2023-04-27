import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.*;
import javax.servlet.http.*;
import java.sql.Connection;

@WebServlet("/EventDelete")
public class EventDelete extends HttpServlet {
    Connection connection;
    private static final long serialVersionUID = 1L;

    public EventDelete() {
        super();
    }
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        connection = ConnectionUtils.getConnection();
    }

    protected void doGet(HttpServletRequest req, HttpServletResponse response) throws ServletException, IOException {

        int idStr = Integer.parseInt(req.getParameter("EventID")); 
        EventData.deleteEvent(connection, idStr);

        response.sendRedirect("EventList");
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }
}
