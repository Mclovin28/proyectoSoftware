import java.io.*;
import java.util.*;
import javax.servlet.*;
import javax.servlet.http.*;
import java.sql.Connection;

//leer la info del otro servlet de las tres caracteristicas mediante el getparameter
//despue sun update

@SuppressWarnings("serial")
public class InsertEvent extends HttpServlet {
    Connection connection;

    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        connection = ConnectionUtils.getConnection();
    }

    public void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        res.setContentType("text/html");
        PrintWriter toClient = res.getWriter();

        // sacas estos nombres de categoryedit
        int ID = Integer.parseInt(req.getParameter("EventID"));
        String name = req.getParameter("EventName");
        String place = req.getParameter("Lugar");
        String description = req.getParameter("Description");

        EventData Event = new EventData(
                ID,
                name,
                place,
                description);
        int n = EventData.InsertEvent(connection, Event);
        res.sendRedirect("EventList");

    }
}
