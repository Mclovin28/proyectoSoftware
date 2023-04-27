import java.io.*;
import java.util.*;
import javax.servlet.*;
import javax.servlet.http.*;
import java.sql.Connection;

//leer la info del otro servlet de las tres caracteristicas mediante el getparameter
//despue sun update

@SuppressWarnings("serial")
public class PHDInfoUpdate extends HttpServlet {
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
        if (userRole == null) {
            // User not logged in
            res.sendRedirect("login.html");
            return;
        }

        String idStr = req.getParameter("UserID");
        String movil = req.getParameter("Movil");
        String despacho = req.getParameter("Despacho");
        String nombre = req.getParameter("Nombre");
        PHDInfo PHD = new PHDInfo(
                idStr,
                null, // Set other fields to null as they are not used in the update
                null,
                movil,
                despacho,
                nombre);
        int n = PHDInfo.updateInfo(connection, PHD);
        res.sendRedirect("profile.html");
    }
}
