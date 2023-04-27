import java.io.*;
import java.util.*;
import javax.servlet.*;
import javax.servlet.http.*;

import org.w3c.dom.DocumentType;

import java.sql.Connection;

@SuppressWarnings("serial")
public class PHDList extends HttpServlet {
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
        
        Vector<PHDData> PHDList = PHDData.getPHDList(connection); // initialize PHDList vector
        
        if (userRole.equals("manager")) {
            toClient.println("<table border='1'>");
            toClient.println("<tr><td>User ID</td><td>Password</td><td>Role</td><td>Edit info</td><td>Delete</td></tr>");
            for (int i = 0; i < PHDList.size(); i++) {
                PHDData PHD = PHDList.elementAt(i);
                toClient.println("<tr>");
                toClient.println("<td>" + PHD.UserID + " </td>");
                toClient.println("<td>" + PHD.Password + " </td>");
                toClient.println("<td>" + PHD.Role + " </td>");
                toClient.println("<td><a href='PHDEdit?UserID=" + PHD.UserID + "'>Edit PHD</a></td>");
                toClient.println("<td><a href='PHDDelete?UserID=" + PHD.UserID + "' onclick='return confirm(\"Are you sure you want to delete this user?\");'>Delete</a></td>");
                toClient.println("</tr>");

            }

            toClient.println("<form method=get action=InsertPHD>");
            toClient.println("</table>");
            toClient.println("<h1>Agregar Nuevo Usuario</h1>");
            toClient.println("<table border='1'>");
            toClient.println("<tr><td>User ID</td><td>Password</td><td>Role</td></tr>");
            toClient.println("<tr>");
            toClient.println("<td> <input type=text id =UserID name=UserID> </td>");
            toClient.println("<td><input type=text id =Password name=Password></td>");
            toClient.println("<td><input type=text id =Role name=Role></td>");
            toClient.println("</table>");
            toClient.println("<input type=submit value=Agregar></form>");

            toClient.println("</body></html>");

            toClient.println(Utils.footer("PHD List"));
        } else{
            toClient.println("No tienes acceso a esta pagina");
        }
    };
}
