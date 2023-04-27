import java.io.*;
import java.util.*;
import javax.servlet.*;
import javax.servlet.http.*;
import java.sql.Connection;

@SuppressWarnings("serial")
public class MensajeList extends HttpServlet {
    Connection connection;

    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        connection = ConnectionUtils.getConnection();
    }

    public void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException  {
        res.setContentType("text/html");
        PrintWriter toClient = res.getWriter();
        HttpSession session = req.getSession();
		
        toClient.println("<table border='1'>");
        Vector<MensajeData> MensajeList = MensajeData.getMensajeList(connection); // initialize PHDList vector
        toClient.println("<tr><td>ID</td><td>User ID</td><td>Mensaje</td><td>Realizado</td><td>Edit info</td></tr>");

        for(int i=0; i< MensajeList.size(); i++){
                MensajeData Mensaje = MensajeList.elementAt(i);
                toClient.println("<tr>");
				toClient.println("<td>" + Mensaje.ID + " </td>");
                toClient.println("<td>" + Mensaje.UserID + " </td>");
                toClient.println("<td>" + Mensaje.Mensaje + " </td>");
				toClient.println("<td>" + Mensaje.Realizado + " </td>");
				toClient.println("<td><a href='MensajeEdit?ID=" + Mensaje.ID + "'>Edit Mensaje</a></td>");

                toClient.println("</tr>");
        }

        toClient.println("</table>");
        toClient.println(Utils.footer("Mensaje List"));
        toClient.close();
        connection = ConnectionUtils.close(connection); // close connection when finished
    }
}