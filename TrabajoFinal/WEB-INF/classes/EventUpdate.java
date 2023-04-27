import java.io.*;
import java.util.*;
import javax.servlet.*;
import javax.servlet.http.*;
import java.sql.Connection;

//leer la info del otro servlet de las tres caracteristicas mediante el getparameter
//despue sun update

@SuppressWarnings("serial")
public class EventUpdate extends HttpServlet {
    Connection connection;

    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        connection = ConnectionUtils.getConnection();
    }
	
	public void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException  {
		res.setContentType("text/html");
		PrintWriter toClient = res.getWriter();
		HttpSession session = req.getSession();
		//String userRole = (String)session.getAttribute("userRole");

		
		int idStr = Integer.parseInt(req.getParameter("EventID")); 
		String pass = req.getParameter("EventName");
		String role = req.getParameter("Lugar");
		EventData Event = new EventData(
			idStr,
			pass,
			role
		);
		int n = EventData.EventUpdate(connection, Event);
		res.sendRedirect("EventList");
	}
}
	
