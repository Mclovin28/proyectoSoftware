import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.*;
import javax.servlet.http.*;
import java.sql.Connection;

@WebServlet("/MensajeDelete")
public class MensajeDelete extends HttpServlet {
    Connection connection;
    private static final long serialVersionUID = 1L;

    public MensajeDelete() {
        super();
    }
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        connection = ConnectionUtils.getConnection();
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String ID = request.getParameter("ID");
        
        MensajeData.deleteMensaje(connection, ID);

        response.sendRedirect("MensajeList");
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }
}