import java.io.Console;
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import org.json.JSONObject;

@WebServlet("/CreateEvent")
public class CreateEvent extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        String userRole = (String) session.getAttribute("userRole");
        JSONObject jsonResponse = new JSONObject();
        jsonResponse.put("userRole", userRole);
        response.setContentType("application/json");
        response.getWriter().write(jsonResponse.toString());
    }
    protected void doPost(HttpServletRequest request, HttpServletResponse response)throws ServletException, IOException {
        HttpSession session = request.getSession();
        String userRole = (String) session.getAttribute("userRole");
        System.out.println(userRole);
        String eventName = request.getParameter("eventName");
        String lugar = request.getParameter("lugar");
        String description = request.getParameter("description");
        JSONObject jsonResponse = new JSONObject();
        if (userRole.equals("manager")) {
            try (Connection conn = ConnectionUtils.getConnection()) {
                String query = "INSERT INTO Event (EventName, Lugar, Description) VALUES (?, ?, ?)";
                PreparedStatement stmt = conn.prepareStatement(query);
                stmt.setString(1, eventName);
                stmt.setString(2, lugar);
                stmt.setString(3, description);

                int rowsAffected = stmt.executeUpdate();
                if (rowsAffected > 0) {
                    jsonResponse.put("success", true);
                } else {
                    jsonResponse.put("success", false);
                }
            } catch (SQLException e) {
                e.printStackTrace();
                jsonResponse.put("success", false);
            } catch (Exception e) {
                e.printStackTrace();
                jsonResponse.put("success", false);
            }

        } else {
            jsonResponse.put("success", false);
            jsonResponse.put("error", "No tienes acceso a esta función, habla con tu encargado.");
        }
        response.setContentType("application/json");
        response.getWriter().write(jsonResponse.toString());
    }
}

