import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

@WebServlet("/UpdateEventServlet")
public class UpdateEventServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String eventId = request.getParameter("eventId");
        String newTitle = request.getParameter("title");
        String newStart = request.getParameter("start");
        String newEnd = request.getParameter("end");

        System.out.println("Event ID: " + eventId);
        System.out.println("New Title: " + newTitle);
        System.out.println("New Start: " + newStart);
        System.out.println("New End: " + newEnd);

        // Update the event in the database
        try (Connection conn = ConnectionUtils.getConnection()) {
            String updateSql = "UPDATE Fecha SET Descripcion = ?, FechaI = ?, FechaF = ? WHERE EventID = ?";
            PreparedStatement pstmt = conn.prepareStatement(updateSql);
            pstmt.setString(1, newTitle);
            pstmt.setString(2, newStart);
            pstmt.setString(3, newEnd);
            pstmt.setString(4, eventId);
            int rowsAffected = pstmt.executeUpdate();
            System.out.println("Rows affected: " + rowsAffected);
        } catch (SQLException e) {
            e.printStackTrace();
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            return;
        }

        response.setStatus(HttpServletResponse.SC_OK);
    }

}
