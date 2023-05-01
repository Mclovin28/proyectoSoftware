import java.io.*;
import java.util.*;
import javax.servlet.*;
import javax.servlet.http.*;
import java.sql.Connection;

@SuppressWarnings("serial")
public class MensajeEdit extends HttpServlet {
    Connection connection;

    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        connection = ConnectionUtils.getConnection();
    }

    public void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException  {
        res.setContentType("text/html");
        PrintWriter toClient = res.getWriter();
		HttpSession session = req.getSession();
		String userRole = (String)session.getAttribute("userRole");
		if(userRole == null){
			// User not logged in
			res.sendRedirect("login.html");
			return;
		}
		String idStr = req.getParameter("ID"); 

		MensajeData Mensaje = MensajeData.getMensajeEdit(connection, idStr);
		
        //toClient.println(Utils.header("Mensajes del doctorando"));
		

        toClient.println("<form  action = 'MensajeUpdate' method='get'>"); 
        toClient.println("<table border='1'>"); 
        
        toClient.println("<tr><td>ID</td>");
        String ID = Mensaje.ID;
        toClient.println("<td><input name='ID' value='" + ID + "'></td></tr>");
        
        toClient.println("<tr><td>UserID</td>");
        String UserID = Mensaje.UserID;
        toClient.println("<td><input name='UserID' value='" + UserID + "'></td></tr>");
        
        toClient.println("<tr><td>Mensaje</td>");
        String Mensajee = Mensaje.Mensaje;
        toClient.println("<td><input name='Mensaje' value='" + Mensajee + "'></td></tr>");
		
		toClient.println("<tr><td>Cambiar estado</td>");
        String Realizado = Mensaje.Realizado;
        toClient.println("<td><input name='Realizado' value='" + Realizado + "'></td></tr>");

		
        toClient.println("</tr>");
        toClient.println("</table>");
        toClient.println("<input type='submit'>");
        toClient.println("</form>");
        toClient.println(Utils.footer("Mensaje Form"));
        toClient.close();
    }
}