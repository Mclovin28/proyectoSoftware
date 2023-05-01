import java.io.*;
import java.util.*;
import javax.servlet.*;
import javax.servlet.http.*;
import java.sql.Connection;

@SuppressWarnings("serial")
public class EventEdit extends HttpServlet {
    Connection connection;

    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        connection = ConnectionUtils.getConnection();
    }

    public void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        res.setContentType("text/html");
        PrintWriter toClient = res.getWriter();
        HttpSession session = req.getSession();
        String idStr = req.getParameter("EventID");
        int idInt;
        try {
            idInt = Integer.parseInt(idStr);
        } catch (NumberFormatException e) {
            // Handle the case where the parameter is not an integer
            toClient.println("Error: Invalid Event ID");
            toClient.close();
            return;
        }
        EventData Event = EventData.getEventEdit(connection, Integer.toString(idInt));

        // toClient.println(Utils.header("Event Form"));
        toClient.println("<form  action = 'EventUpdate' method='get'>");
        toClient.println("<table border='1'>");

        toClient.println("<tr><td>Event ID</td>");
        int EventID = Event.EventID;
        toClient.println("<td><input name='EventID' value='" + EventID + "'></td></tr>");

        toClient.println("<tr><td>Event Name</td>");
        String EventName = Event.EventName;
        toClient.println("<td><input name='EventName' value='" + EventName + "'></td></tr>");

        toClient.println("<tr><td>Lugar</td>");
        String Lugar = Event.Lugar;
        toClient.println("<td><input name='Lugar' value='" + Lugar + "'></td></tr>");

        toClient.println("</tr>");
        toClient.println("</table>");
        toClient.println("<input type='submit'>");
        toClient.println("</form>");
        toClient.println(Utils.footer("Event Form"));
        toClient.close();
    }
}