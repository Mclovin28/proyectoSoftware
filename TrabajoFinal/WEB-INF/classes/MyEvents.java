import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.json.JSONArray;
import org.json.JSONObject;

@WebServlet("/MyEvents")
public class MyEvents extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        String userName = (String) session.getAttribute("username");
        if (userName == null) {
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            response.setContentType("application/json");
            response.getWriter().write(new JSONObject().put("error", "No userName found in session").toString());
            return;
        }

        String[] arrOfStr = userName.split("@", 2);
        String id = arrOfStr[0];
        JSONArray events = new JSONArray();
        try (Connection conn = ConnectionUtils.getConnection()) {
            String query = "SELECT Event.EventName, Event.Lugar, Event.Description, Event.Type " +
                           "FROM Inscripciones " +
                           "JOIN Event ON Event.EventID = Inscripciones.EventID " +
                           "WHERE Inscripciones.UserName = ?";
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setString(1, id);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                JSONObject event = new JSONObject();
                event.put("eventName", rs.getString("EventName"));
                event.put("lugar", rs.getString("Lugar"));
                event.put("description", rs.getString("Description"));
                event.put("type", rs.getString("Type"));
                events.put(event);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            response.setContentType("application/json");
            response.getWriter().write(new JSONObject().put("error", "Database error: " + e.getMessage()).toString());
            return;
        } catch (Exception e) {
            e.printStackTrace();
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            response.setContentType("application/json");
            response.getWriter().write(new JSONObject().put("error", "Internal server error: " + e.getMessage()).toString());
            return;
        }

        response.setContentType("application/json");
        response.getWriter().write(events.toString());
    }
}
