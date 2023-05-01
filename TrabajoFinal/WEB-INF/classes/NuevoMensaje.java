import java.io.*;
import java.util.*;
import javax.servlet.*;
import javax.servlet.http.*;

import org.w3c.dom.DocumentType;

import java.sql.Connection;

@SuppressWarnings("serial")
public class NuevoMensaje extends HttpServlet {
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
        toClient.println("<!DOCTYPE html><html><script>");
        toClient.println("function clearLocalStorage() {localStorage.clear();}");
        toClient.println("function clearSessionStorage() {sessionStorage.clear();}");
        toClient.println(
                "function clearCookies() { document.cookie.split(';').forEach(function(c) {document.cookie = c.replace(/^ +/, '').replace(/=.*/, '=;expires=' + new Date().toUTCString() + ';path=/');});}");
        toClient.println(
                "function logout() { clearCookies(); clearLocalStorage(); clearSessionStorage(); window.location.href = \"/TrabajoFinal/LogoutServlet\";}");
        toClient.println("</script>");
        toClient.print(
                "<body><nav> <button onclick=\"logout()\" class=\"btn btn-outline-danger\">Logout</button></nav>");
  
        Vector<MensajeData> MensajeList = MensajeData.getMensajeList(connection); 
        if (userRole.equals("phd")) {

			toClient.println("<form method=get action=InsertMensaje>");
            toClient.println("</table>");
            toClient.println("<h1>Escribir nuevo Mensaje al manager</h1>");
            toClient.println("<table border='1'>");
            toClient.println("<tr><td>Mensaje</td></tr>");
            toClient.println("<tr>");
            toClient.println("<td><input type=text id =Mensaje name=Mensaje></td>");
            toClient.println("</table>");
            toClient.println("<input type=submit value=Agregar></form>");

			toClient.println("</body></html>");
		} else{
            toClient.println("No tienes acceso a esta pagina");
        }
	};
}