import java.io.*;
import java.util.*;
import javax.servlet.*;
import javax.servlet.http.*;

import org.w3c.dom.DocumentType;

import java.sql.Connection;

@SuppressWarnings("serial")
public class MensajeList extends HttpServlet {
    Connection connection;

    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        connection = ConnectionUtils.getConnection();
    }

    public void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        res.setContentType("text/html");
        PrintWriter toClient = res.getWriter();
        HttpSession session = req.getSession();
        String userRole = (String) session.getAttribute("userRole");
        System.out.print(userRole);
        toClient.println("<!DOCTYPE html><head><link rel=\"stylesheet\" href=\"style.css\"></head><html><script>");
        toClient.println("function clearLocalStorage() {localStorage.clear();}");
        toClient.println("function clearSessionStorage() {sessionStorage.clear();}");
        toClient.println(
                "function clearCookies() { document.cookie.split(';').forEach(function(c) {document.cookie = c.replace(/^ +/, '').replace(/=.*/, '=;expires=' + new Date().toUTCString() + ';path=/');});}");
        toClient.println(
                "function logout() { clearCookies(); clearLocalStorage(); clearSessionStorage(); window.location.href = \"/TrabajoFinal/LogoutServlet\";}");
        toClient.println("</script>");
        toClient.print("<body color: black;>");

  
        Vector<MensajeData> MensajeList = MensajeData.getMensajeList(connection); // initialize PHDList vector
        if (userRole.equals("manager")) {
            toClient.println("<table border='1'>");
			toClient.println("<tr><td>ID</td><td>User ID</td><td>Mensaje</td><td>Realizado</td><td>Mandar correo al usuario</td><td>Responder consulta</td></tr>");

			for(int i=0; i< MensajeList.size(); i++){
                MensajeData Mensaje = MensajeList.elementAt(i);
                toClient.println("<tr>");
				toClient.println("<td>" + Mensaje.ID + " </td>");
                toClient.println("<td>" + Mensaje.UserID + " </td>");
                toClient.println("<td>" + Mensaje.Mensaje + " </td>");
				toClient.println("<td>" + Mensaje.Realizado + " </td>");
				toClient.println("<td><a href='https://mail.google.com/mail/u/0/?view=cm&fs=1&to=" + Mensaje.UserID + "&su=Contestacion de consulta&body=A continuación te respondo a tu consulta recibida:     " + Mensaje.Mensaje +"'>Responder por Gmail</a></td>");
				toClient.println("<td><a href='MensajeEdit?ID=" + Mensaje.ID + "'>Marcar como realizado</a></td>");
                toClient.println("<td><a href='MensajeDelete?ID=" + Mensaje.ID + "' onclick='return confirm(\"Estas seguro de que quieres borrar el mensaje?\");'>Delete</a></td>");
                toClient.println("</tr>");
			}
		} else{
            toClient.println("No tienes acceso a esta pagina");
        }
	};
}
      
