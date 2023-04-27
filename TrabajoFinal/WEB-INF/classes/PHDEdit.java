import java.io.*;
import java.util.*;
import javax.servlet.*;
import javax.servlet.http.*;
import java.sql.Connection;

@SuppressWarnings("serial")
public class PHDEdit extends HttpServlet {
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
		String idStr = req.getParameter("UserID"); 
		//para seleccionar solo 1
		PHDData PHD = PHDData.getPHDEdit(connection, idStr);

		
		
        //toClient.println(Utils.header("PHD Form"));
		

        toClient.println("<form  action = 'PHDUpdate' method='get'>"); 
        toClient.println("<table border='1'>"); 
       
		toClient.println("<tr><td>Name</td>");
        String name = PHD.UserID;
        toClient.println("<td><input name='UserID' value='" + name + "'></td></tr>");
        
        toClient.println("<tr><td>Password</td>");
        String Password = PHD.Password;
        toClient.println("<td><input name='Password' value='" + Password + "'></td></tr>");
		
		toClient.println("<tr><td>Role</td>");
        String Role = PHD.Role;
        toClient.println("<td><input name='Role' value='" + Role + "'></td></tr>");

        toClient.println("</tr>");
        toClient.println("</table>");
        toClient.println("<input type='submit'>");
        toClient.println("</form>");
        toClient.println(Utils.footer("PHD Form"));
        toClient.close();
    }
}