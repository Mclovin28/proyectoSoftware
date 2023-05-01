import java.io.*;
import java.util.*;
import javax.servlet.*;
import javax.servlet.http.*;
import java.sql.Connection;

//leer la info del otro servlet de las tres caracteristicas mediante el getparameter
//despue sun update

@SuppressWarnings("serial")
public class MensajeUpdate extends HttpServlet {
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
		String idStr1 = req.getParameter("ID");
		String idStr = req.getParameter("UserID"); 
		String pass = req.getParameter("Mensaje");
		String role = req.getParameter("Realizado");
		MensajeData Mensaje = new MensajeData(
			idStr1,
			idStr,
			pass,
			role
		);
		int n = MensajeData.metodoUpdate(connection, Mensaje);
		res.sendRedirect("MensajeList");
	}
}
	