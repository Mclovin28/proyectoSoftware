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

@WebServlet("/GetUserInfo")
public class GetUserInfo extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession(); // Get the session object
        String userId = (String) session.getAttribute("username");
        System.out.println(userId);
        PHDInfo info = null;
        if (userId != null) { // Check if the user ID exists in the session
            try {
                Connection conn = ConnectionUtils.getConnection(); // Use ConnectionUtils to get the connection
                Statement stmt = conn.createStatement();
                info = PHDInfo.getPHDInfo(conn, userId);
                System.out.println(info.UserID);
                System.out.println(info.Area);
                System.out.println(info.Ciudad);
                System.out.println(info.Movil);
                System.out.println(info.Despacho);
                System.out.println(info.Nombre);
                ConnectionUtils.close(conn); // Use ConnectionUtils to close the connection
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            System.out.println("No hay username");
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            response.getWriter().write("{\"error\": \"User not found in session\"}");
            return;
        }

        JSONObject PHDData = new JSONObject();
        PHDData.put("UserID", info.UserID); // Use camelCase for consistency
        PHDData.put("Area", info.Area);
        PHDData.put("Ciudad", info.Ciudad);
        PHDData.put("Movil", info.Movil);
        PHDData.put("Despacho", info.Despacho);
        PHDData.put("Nombre", info.Nombre);

        response.setContentType("application/json");
        response.getWriter().write(PHDData.toString());
    }
}
