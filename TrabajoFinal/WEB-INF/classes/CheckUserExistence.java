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

@WebServlet("/CheckUserExistence")
public class CheckUserExistence extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        System.out.println("CheckUserExistence servlet doPost method called.");
        String username = request.getParameter("username");
        System.out.println("Username received: " + username);

        try (Connection conn = ConnectionUtils.getConnection()) {
            String query = "SELECT COUNT(*) as user_count FROM Users WHERE UserID = ?";
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setString(1, username);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                int userCount = rs.getInt("user_count");
                response.setContentType("text/plain");
                response.setCharacterEncoding("UTF-8");
                response.getWriter().write(Boolean.toString(userCount > 0));
                System.out.println("User count: " + userCount);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("SQLException occurred in CheckUserExistence servlet.");
            response.setContentType("text/plain");
            response.setCharacterEncoding("UTF-8");
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            response.getWriter().write("Error occurred while processing the request");
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Exception occurred in CheckUserExistence servlet.");
        }
    }
}
