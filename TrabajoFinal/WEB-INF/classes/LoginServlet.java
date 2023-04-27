import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.servlet.http.HttpSession;
import java.time.LocalDateTime;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebServlet("/LoginServlet")
public class LoginServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String username = request.getParameter("username");
        String password = request.getParameter("password");

        try (Connection conn = ConnectionUtils.getConnection()) {
            if (conn == null) {
                System.out.println("Connection is null.");
                return;
            }

            String query = "SELECT * FROM Users WHERE UserID = ?";
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setString(1, username);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                String storedPassword = rs.getString("Password");
                String storedRole = rs.getString("Role");
                boolean status = rs.getBoolean("Status");

                if (password.equals(storedPassword)) {
                    if (status) {
                        HttpSession session = request.getSession();
                        session.setAttribute("username", username);
                        session.setAttribute("userRole", storedRole);
                        session.setAttribute("loginTime", LocalDateTime.now());

                        if ("manager".equals(storedRole)) {
                            response.sendRedirect("opciones_gestor.html");
                        } else {
                            response.sendRedirect("profile.html");
                        }
                    } else {
                        response.setContentType("text/html");
                        response.getWriter().write("<p>Your account has not been activated. Please contact a manager.</p><br>");
                        response.getWriter().write("<a href='login_gestor.html'>Go back to login</a>");
                    }
                } else {
                    response.sendRedirect("index.html");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new ServletException("Error occurred while processing the request", e);
        }
    }
}



