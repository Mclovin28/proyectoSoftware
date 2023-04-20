import java.sql.Connection;
import java.sql.DriverManager;

public class ConnectionUtils {
    public static Connection getConnection() {
        Connection connection = null;
        try {
            Class.forName("net.ucanaccess.jdbc.UcanaccessDriver");
            String url = "jdbc:ucanaccess://D:/Java8/apache-tomcat-8.5.24/webapps/TrabajoFinal/northbrick.mdb";
            connection = DriverManager.getConnection(url);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return connection;
    }

    public static Connection close(Connection connection) {
        try {
            connection.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return connection;
    }
}

