import java.io.IOException;
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

@WebServlet("/SignUpEvent")
public class SignUpEvent extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        String userName = (String) session.getAttribute("username");
        String eventID = request.getParameter("eventID");
        String[] arrOfStr = userName.split("@", 2);
        String id = arrOfStr[0];
        JSONObject jsonResponse = new JSONObject();
        try (Connection conn = ConnectionUtils.getConnection()) {
            String query = "INSERT INTO Inscripciones (UserName, EventID) VALUES (?, ?)";
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setString(1, id);
            stmt.setString(2, eventID);

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

        response.setContentType("application/json");
        response.getWriter().write(jsonResponse.toString());
    }
}

