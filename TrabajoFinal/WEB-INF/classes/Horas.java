import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.Duration;

import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.json.JSONObject;
import java.time.LocalDateTime;
import java.time.Duration;
import java.time.format.DateTimeFormatter;
import javax.servlet.http.HttpSession;
import java.time.LocalDateTime;


public class Horas {
    double completedHours;
    double remainingHours;
    double assignedHours;
    
    Horas (double completedHours, double remainingHours) {
        this.completedHours    = completedHours;
        this.remainingHours  = remainingHours;
    }
    Horas (double completedHours, double remainingHours, double assignedHours) {
        this.completedHours    = completedHours;
        this.remainingHours  = remainingHours;
        this.assignedHours = assignedHours;
    }
    Horas(){
        this.completedHours    = 0;
        this.remainingHours  = 0;
    }
    public static Horas getHours(Connection connection, String id, LocalDateTime loginTime) {
        double completedHours = 0;
        double remainingHours = 0;
        double assignedHours = 0;
        double totalHours = 60;
        Horas hours = new Horas();
        String sql = "SELECT FechaI, FechaF FROM Fecha";
        sql += " WHERE ID = ?";
        System.out.println("Horas: " + sql);
        try {
            PreparedStatement pstmt=connection.prepareStatement(sql);
            pstmt.setString(1,id);
            ResultSet result = pstmt.executeQuery();
            while(result.next()) {
                Timestamp start = result.getTimestamp("FechaI");
                Timestamp end = result.getTimestamp("FechaF");
                LocalDateTime startDateTime = start.toLocalDateTime();
                LocalDateTime endDateTime = end.toLocalDateTime();
                Duration duration = Duration.between(startDateTime, endDateTime);
                double hoursWithFractions = duration.toMinutes() / 60.0;
    
                if (endDateTime.isBefore(loginTime) || endDateTime.isEqual(loginTime)) {
                    completedHours += hoursWithFractions;
                } else {
                    assignedHours += hoursWithFractions;
                }
    
                remainingHours = totalHours - completedHours - assignedHours;
                hours = new Horas(
                    completedHours,
                    remainingHours,
                    assignedHours
                );
            }
        } catch(SQLException e) {
            e.printStackTrace();
            System.out.println("Error in getHours: " + sql + " Exception: " + e);
        }
        System.out.println("Horas realizadas"+hours.completedHours+"Horas restantes:"+ hours.remainingHours+"Horas asignadas:"+ hours.assignedHours+" por:"+ id);
        return hours;
    }
}
    