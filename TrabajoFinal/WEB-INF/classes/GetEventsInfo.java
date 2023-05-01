import java.io.IOException;
import java.sql.Connection;
import java.sql.Statement;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.json.JSONObject;
import java.util.Vector;

@WebServlet("/GetEventsInfo")
public class GetEventsInfo extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String info = "";
        try {
            Connection conn = ConnectionUtils.getConnection();
            Statement stmt = conn.createStatement();
            info = EventData.getEventList(conn);
        } catch (Exception e) {
            e.printStackTrace();
        }

        JSONObject EventData = new JSONObject();
        EventData.put("events", info); // Use camelCase for consistency and change the key to "events"

        response.setContentType("application/json");
        response.getWriter().write(EventData.toString());
    }
}
