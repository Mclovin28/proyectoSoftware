import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/CreateEventServlet")
public class CreateEventServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            String title = request.getParameter("title");
            String start = request.getParameter("start");
            String end = request.getParameter("end");
            String userId = request.getParameter("userId"); // Add this line to read the user ID from the request

            System.out.println("Title: " + title);
            System.out.println("Start: " + start);
            System.out.println("End: " + end);
            System.out.println("User ID: " + userId); // Add this line to print the user ID

            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
            Timestamp startTimestamp = new Timestamp(dateFormat.parse(start).getTime());
            Timestamp endTimestamp = new Timestamp(dateFormat.parse(end).getTime());

            boolean updateSuccessful = false;

            try (Connection conn = ConnectionUtils.getConnection()) {
                String query = "INSERT INTO Fecha (FechaI, FechaF, Descripcion, ID) VALUES (?, ?, ?, ?)"; // Modify the query to include the assigned_to column
                PreparedStatement stmt = conn.prepareStatement(query);
                stmt.setTimestamp(1, startTimestamp);
                stmt.setTimestamp(2, endTimestamp);
                stmt.setString(3, title);
                stmt.setString(4, userId); // Add this line to set the value of the assigned_to column

                int rowsAffected = stmt.executeUpdate();
                conn.commit(); // Add this line
                updateSuccessful = rowsAffected > 0;
                stmt.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }

            if (updateSuccessful) {
                response.setStatus(HttpServletResponse.SC_OK);
                System.out.println("Event creation successful");
            } else {
                response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
                System.out.println("Event creation failed");
            }
        } catch (ParseException e) {
            e.printStackTrace();
            throw new ServletException("Error occurred while parsing the timestamp", e);
        } catch (Exception e) {
            e.printStackTrace();
            throw new ServletException("Error occurred while processing the request", e);
        }
    }
}

