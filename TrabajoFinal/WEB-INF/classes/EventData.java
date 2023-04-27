import java.io.*;
import java.util.*;
import javax.servlet.*;
import javax.servlet.http.*;
import java.sql.Connection;
import java.sql.Statement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Vector;
import java.sql.PreparedStatement;
public class EventData {
    int EventID;
    String EventName;
    String Lugar;

    EventData(int EventID, String EventName, String Lugar) {
        this.EventID = EventID;
        this.EventName = EventName;
        this.Lugar = Lugar;
    }

    public static Vector<EventData> getEventList(Connection connection) {
        Vector<EventData> eventVector = new Vector<>();

        String sql = "SELECT EventID, EventName, Lugar FROM Event";
        System.out.println("getEventList: " + sql);

        try {
            Statement statement = connection.createStatement();
            ResultSet result = statement.executeQuery(sql);

            while (result.next()) {
                int EventID = result.getInt("EventID");
                String EventName = result.getString("EventName");
                String Lugar = result.getString("Lugar");

                EventData event = new EventData(EventID, EventName, Lugar);
                eventVector.addElement(event);
            }

            statement.close();
            result.close();
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Error in getEventList: " + sql + " Exception: " + e);
        }
        return eventVector;
    }
	
		public static EventData getEventEdit(Connection connection, String id) {
		EventData event = null;
		String sql = "Select EventID, EventName, Lugar FROM Event";
		sql += " WHERE Event.EventID=?";
		System.out.println("getEventEdit: " + sql);
		try {
	
			PreparedStatement pstmt=connection.prepareStatement(sql); 
			pstmt.setString(1, id);
			ResultSet result = pstmt.executeQuery();
            
			while(result.next()) {
				event = new EventData(
					result.getInt("EventID"),
					result.getString("EventName"),
					result.getString("Lugar")
				);
			}
			
		}
		catch(SQLException e) {
			e.printStackTrace();
			System.out.println("Error in getEventList: " + sql + " Exception: " + e);
		}
		return event;
	}
	public static int EventUpdate(Connection connection, EventData Event) {
		String sql ="UPDATE Event ";
			sql+= "SET EventName = ?, Lugar= ?";
			sql+= " WHERE EventID = ?";
			System.out.println("EventUpdate: " + sql);
			int n = 0;
		try {
			
			PreparedStatement stmtUpdate= connection.prepareStatement(sql);
			stmtUpdate.setString(1,Event.EventName);
			stmtUpdate.setString(2,Event.Lugar);
			stmtUpdate.setInt(3,Event.EventID);
			// al se run update se guarda en un int n
			n = stmtUpdate.executeUpdate();
			stmtUpdate.close();
			
		} catch(SQLException e) {
			e.printStackTrace();
			System.out.println("Error in EventUpdate: " + sql + " Exception: " + e);
		}
			return n;
	}


	public static void deleteEvent(Connection connection, int EventID) {
        String sql = "DELETE FROM Event WHERE EventID = ?";

        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setInt(1, EventID);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Error deleting event: " + e.getMessage());
        }
    }

	public static int InsertEvent(Connection connection, EventData Event) {
		String sql ="INSERT INTO Event (EventID, EventName, Lugar) "
            + "VALUES (?, ?, ?)";
			System.out.println("InsertEvent: " + sql);
			int n = 0;
			
		try {
			
			PreparedStatement stmtUpdate= connection.prepareStatement(sql);
			stmtUpdate.setInt(1,Event.EventID);
			stmtUpdate.setString(2,Event.EventName);
			stmtUpdate.setString(3,Event.Lugar);
			n = stmtUpdate.executeUpdate();
			stmtUpdate.close();
			
		} catch(SQLException e) {
			e.printStackTrace();
			System.out.println("Error in InsertEvent: " + sql + " Exception: " + e);
		}
			return n;
	}
	
}