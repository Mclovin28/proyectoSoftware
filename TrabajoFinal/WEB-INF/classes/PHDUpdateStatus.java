import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/PHDUpdateStatus")
public class PHDUpdateStatus extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String userID = request.getParameter("UserID");

        try (Connection conn = ConnectionUtils.getConnection()) {
            if (conn == null) {
                System.out.println("Connection is null.");
                return;
            }

            String query = "UPDATE Users SET Status = NOT Status WHERE UserID = ?";
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setString(1, userID);
            int updatedRows = stmt.executeUpdate();

            if (updatedRows > 0) {
                response.sendRedirect("PHDList");
            } else {
                response.sendRedirect("PHDList?error=1");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new ServletException("Error occurred while processing the request", e);
        }
    }
}