import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.sql.Timestamp;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;

@WebServlet("/FetchEventsServlet")
public class FetchEventsServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            List<Event> events = new ArrayList<Event>();

            try (Connection conn = ConnectionUtils.getConnection()) {
                String query = "SELECT EventID, FechaI, FechaF, Descripcion, ID FROM Fecha"; // Update the query to fetch the event ID
                PreparedStatement stmt = conn.prepareStatement(query);
                ResultSet rs = stmt.executeQuery();
                int rowCount = 0;

                while (rs.next()) {
                    rowCount++;
                    String eventId = rs.getString("EventID"); // Fetch the event ID
                    String title = rs.getString("Descripcion");
                    Timestamp startTimestamp = rs.getTimestamp("FechaI");
                    Timestamp endTimestamp = rs.getTimestamp("FechaF");
                    String start = startTimestamp == null ? null : startTimestamp.toString();
                    String end = endTimestamp == null ? null : endTimestamp.toString();
                    String userId = rs.getString("ID"); // Fetch the user ID
                    events.add(new Event(eventId, title, start, end, userId)); // Add the event ID to the event object


                    System.out.println("Row " + rowCount + ":");
                    System.out.println("Title: " + title);
                    System.out.println("Start: " + start);
                    System.out.println("End: " + end);
                    System.out.println("User ID: " + userId); // Print the user ID
                    System.out.println("EventID: " + eventId);
                }

                System.out.println("Fetched " + rowCount + " rows from the database.");
                stmt.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }

            response.setContentType("application/json");
            PrintWriter out = response.getWriter();

            Gson gson = new Gson();
            out.print(gson.toJson(events));
            out.flush();
        } catch (Exception e) {
            e.printStackTrace();
            throw new ServletException("Error occurred while processing the request", e);
        }
    }
}


