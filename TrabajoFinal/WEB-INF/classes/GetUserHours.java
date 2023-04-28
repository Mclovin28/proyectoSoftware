import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.Duration;
import java.sql.Connection;
import java.sql.DriverManager;
import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.json.JSONObject;
import java.time.LocalDateTime;
import java.time.Duration;
import java.time.format.DateTimeFormatter;
@WebServlet("/GetUserHours")
public class GetUserHours extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        HttpSession session = request.getSession(); // Get the session object
        String userId = (String) session.getAttribute("username"); // Retrieve the user ID from the session
        LocalDateTime loginTime = (LocalDateTime) session.getAttribute("loginTime");
        String[] arrOfStr = userId.split("@", 2);
        String id = arrOfStr[0];
        // Declare and initialize the Horas object
        Horas hours = new Horas();
        hours.completedHours = 0;
        hours.remainingHours = 60;

        if (userId != null) { // Check if the user ID exists in the session
            try {
                Connection conn = ConnectionUtils.getConnection(); // Use ConnectionUtils to get the connection
                Statement stmt = conn.createStatement();
                hours = Horas.getHours(conn, id,loginTime);
                System.out.println(hours.remainingHours);
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            // Handle the case when the user ID is not found in the session
        }

        JSONObject hoursData = new JSONObject();
        hoursData.put("completedHours", hours.completedHours);
        hoursData.put("assignedHours", hours.assignedHours);
        hoursData.put("remainingHours", hours.remainingHours);

        response.setContentType("application/json");
        response.getWriter().write(hoursData.toString());
    }
}
