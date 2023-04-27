import java.io.*;
import java.util.*;
import javax.servlet.*;
import javax.servlet.http.*;
import java.sql.Connection;

//leer la info del otro servlet de las tres caracteristicas mediante el getparameter
//despue sun update

@SuppressWarnings("serial")
public class InsertPHD extends HttpServlet {
    Connection connection;

    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        connection = ConnectionUtils.getConnection();
    }

    public void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException  {
        res.setContentType("text/html");
		PrintWriter toClient = res.getWriter();
		
		//sacas estos nombres de categoryedit
		String ID = req.getParameter("UserID");
		String pass = req.getParameter("Password");
		String role = req.getParameter("Role");
		
        PHDData PHD = new PHDData(
            ID,
            pass,
			role
        );
        int n = PHDData.InsertPHD(connection, PHD);
		res.sendRedirect("PHDList");


    }
}
	
