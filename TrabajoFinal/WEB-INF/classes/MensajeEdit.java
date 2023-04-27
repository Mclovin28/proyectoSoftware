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
		String idStr = req.getParameter("ID"); 
		
		
		
		//para seleccionar solo 1
		MensajeData Mensaje = MensajeData.getMensajeEdit(connection, idStr);
		
        toClient.println(Utils.header("Mensajes del doctorando"));
		

        toClient.println("<form  action = 'MensajeUpdate' method='GET'>"); 
        toClient.println("<table border='1'>"); 
       
	    toClient.println("<tr><td>Consulta</td>");
        String id = Mensaje.ID;
        toClient.println("<td><input name='ID' value='" + id + "'></td></tr>");
		
		toClient.println("<tr><td>Correo</td>");
        String name = Mensaje.UserID;
        toClient.println("<td><input name='UserID' value='" + name + "'></td></tr>");
        
        toClient.println("<tr><td>Mensaje</td>");
        String Password = Mensaje.Mensaje;
        toClient.println("<td><input name='Password' value='" + Password + "'></td></tr>");
		
		toClient.println("<tr><td>Realizado</td>");
        String Role = Mensaje.Realizado;
        toClient.println("<td><input name='Role' value='" + Role + "'></td></tr>");

        toClient.println("</tr>");
        toClient.println("</table>");
        toClient.println("<input type='submit'>");
        toClient.println("</form>");
        toClient.println(Utils.footer("Mensaje Form"));
        toClient.close();
    }
}