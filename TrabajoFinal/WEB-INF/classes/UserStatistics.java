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
import org.json.JSONArray;
import org.json.JSONObject;

@WebServlet("/UserStatistics")
public class UserStatistics extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        JSONArray userStats = new JSONArray();
        try (Connection conn = ConnectionUtils.getConnection()) {
            String query = "SELECT Users.UserName, Users.Nombre, COUNT(Inscripciones.EventID) as EventCount " +
                           "FROM Users " +
                           "LEFT JOIN Inscripciones ON Users.UserName = Inscripciones.UserName " +
                           "GROUP BY Users.UserName, Users.Nombre " +
                           "ORDER BY EventCount DESC";
            PreparedStatement stmt = conn.prepareStatement(query);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                JSONObject userStat = new JSONObject();
                userStat.put("userID", rs.getString("UserName"));
                userStat.put("nombre", rs.getString("Nombre"));
                userStat.put("eventCount", rs.getInt("EventCount"));
                userStats.put(userStat);
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
        response.getWriter().write(userStats.toString());
    }
}
