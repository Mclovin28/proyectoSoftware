import java.io.*;
import java.util.*;
import javax.servlet.*;
import javax.servlet.http.*;

import org.w3c.dom.DocumentType;

import java.sql.Connection;

//leer la info del otro servlet de las dos caracteristicas mediante el getparameter
//despues un update

@SuppressWarnings("serial")
public class InsertMensaje extends HttpServlet {
    Connection connection;

    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        connection = ConnectionUtils.getConnection();
    }

    public void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException  {
        res.setContentType("text/html");
		PrintWriter toClient = res.getWriter();
        HttpSession session = req.getSession();
		String ID = req.getParameter("ID");
		String UserID = (String) session.getAttribute("username");
		String Mensaje = req.getParameter("Mensaje");
		String Realizado = "Pendiente";
		
        MensajeData Mensajes = new MensajeData(ID, UserID, Mensaje, Realizado);
        int n = MensajeData.InsertMensaje(connection, Mensajes);
		res.sendRedirect("MensajeList");


    }
}