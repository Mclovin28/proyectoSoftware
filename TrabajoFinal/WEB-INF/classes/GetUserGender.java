import javax.servlet.*;
import javax.servlet.http.*;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.*;
import org.json.JSONObject;
import java.sql.Connection;

public class GetUserGender extends HttpServlet {
    Connection connection;
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        connection = ConnectionUtils.getConnection();
    }
  protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
    resp.setContentType("application/json");
    PrintWriter out = resp.getWriter();
    
    HttpSession session = req.getSession(false);
    String username = (String) session.getAttribute("username");
    
    String gender = PHDInfo.getUserSex(connection, username);
    
    JSONObject jsonResponse = new JSONObject();
    jsonResponse.put("gender", gender);
    out.println(jsonResponse.toString());
  }
}
