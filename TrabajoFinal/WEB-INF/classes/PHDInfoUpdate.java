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

    public void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        System.out.println("Received POST request");
        res.setContentType("text/html");
        PrintWriter toClient = res.getWriter();
        HttpSession session = req.getSession();
        String userRole = (String) session.getAttribute("userRole");
        if (userRole == null) {
            // User not logged in
            res.sendRedirect("login.html");
            return;
        }
        System.out.println("Received data:");
        System.out.println("email: " + req.getParameter("email"));
        System.out.println("mobile: " + req.getParameter("mobile"));
        System.out.println("office: " + req.getParameter("office"));
        System.out.println("name: " + req.getParameter("fullName"));
        String nombre = req.getParameter("fullName");
        String idStr = req.getParameter("email");
        String movil = req.getParameter("mobile");
        String despacho = req.getParameter("office");
        PHDInfo PHD = new PHDInfo(
                idStr,
                movil,
                despacho,
                nombre);
        int n = PHDInfo.updateInfo(connection, PHD);
        res.sendRedirect("profile.html");
    }
}
