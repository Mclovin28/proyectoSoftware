import java.io.*;
import java.util.*;
import javax.servlet.*;
import javax.servlet.http.*;
import java.sql.Connection;

@SuppressWarnings("serial")
public class EventList extends HttpServlet {
    Connection connection;

    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        connection = ConnectionUtils.getConnection();
    }

    public void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        res.setContentType("text/html");
        PrintWriter toClient = res.getWriter();
        toClient.println("<table border='1'>");
        toClient.println(
                "<tr><td>Event ID</td><td>Event Name</td><td>Lugar</td><td>Edit Info</td><td>Delete</td></tr>");
        Vector<EventData> eventList = EventData.getEventListVec(connection);
        System.out.println(eventList.size());
        for (int i = 0; i < eventList.size(); i++) {
            EventData event = eventList.elementAt(i);
            toClient.println("<tr>");
            toClient.println("<td>" + event.EventID + "</td>");
            toClient.println("<td>" + event.EventName + "</td>");
            toClient.println("<td>" + event.Lugar + "</td>");
            toClient.println("<td>" + event.Description + "</td>");
            toClient.println("<td><a href='EventEdit?EventID=" + event.EventID + "'>Edit Event</a></td>");
            toClient.println("<td><a href='EventDelete?EventID=" + event.EventID
                    + "' onclick='return confirm(\"Are you sure you want to delete this event?\");'>Delete</a></td>");
            toClient.println("</tr>");
        }
        toClient.println("</table>");

        toClient.println("<form method='get' action='InsertEvent'>");
        toClient.println("<h1>Agregar Nuevo Evento</h1>");
        toClient.println("<table border='1'>");
        toClient.println("<tr><td>Event Name</td><td>Lugar</td><td>Description</td></tr>");
        toClient.println("<tr>");

        toClient.println("<td><input type='text' id='EventName' name='EventName'></td>");
        toClient.println("<td><input type='text' id='Lugar' name='Lugar'></td>");
        toClient.println("<td><input type='text' id='Description' name='Description'></td>");
        toClient.println("</tr>");
        toClient.println("</table>");
        toClient.println("<input type='submit' value='Agregar evento'>");
        toClient.println("</form>");

        toClient.println(Utils.footer("Event List"));
    }
}