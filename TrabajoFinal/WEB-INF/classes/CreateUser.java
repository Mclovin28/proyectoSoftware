import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/CreateUser")
public class CreateUser extends HttpServlet {
    private static final long serialVersionUID = 1L;

    public CreateUser() {
        super();
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String email = request.getParameter("email");
        String password = request.getParameter("password");
        String area = request.getParameter("area");
        String city = request.getParameter("city");
        String mobile = request.getParameter("mobile");
        String office = request.getParameter("office");
        String name = request.getParameter("name");
        String sex = request.getParameter("sex");
        String role = request.getParameter("role");

        Connection connection = null;

        try {
            connection = ConnectionUtils.getConnection();
            boolean userCreated = createUser(connection, email, password, area, city, mobile, office, name, sex, role);

            if (userCreated) {
                response.setStatus(HttpServletResponse.SC_OK);
                response.getWriter().write("User created successfully.");
            } else {
                response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
                response.getWriter().write("Error creating user.");
            }
        } catch (Exception e) {
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            response.getWriter().write("Error creating user: " + e.getMessage());
        } finally {
            ConnectionUtils.close(connection);
        }
    }

    private boolean createUser(Connection connection, String email, String password, String area, String city, String mobile, String office, String name, String sex, String role) throws Exception {
        String sql = "INSERT INTO Users (UserId, Password, Area, Ciudad, Movil, Despacho, Nombre, Sexo, Role) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setString(1, email);
        statement.setString(2, password);
        statement.setString(3, area);
        statement.setString(4, city);
        statement.setString(5, mobile);
        statement.setString(6, office);
        statement.setString(7, name);
        statement.setString(8, sex);
        statement.setString(9, role);

        int rowsInserted = statement.executeUpdate();
        return rowsInserted > 0;
    };
}
