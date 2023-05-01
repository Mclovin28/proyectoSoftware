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
	String Description;
	String Type;

	EventData(int EventID, String EventName, String Lugar, String Description) {
		this.EventID = EventID;
		this.EventName = EventName;
		this.Lugar = Lugar;
		this.Description = Description;
	}
	EventData(String EventName,String Lugar, String Description, String Type, int EventID ) {
		this.EventName = EventName;
		this.Lugar = Lugar;
		this.Description = Description;
		this.Type=Type;
	}

	public static String getEventList(Connection connection) {
		Vector<EventData> eventVector = new Vector<>();
		String resul = "";

		String sql = "SELECT EventID, EventName, Lugar, Description FROM Event";
		System.out.println("getEventList: " + sql);

		try {
			Statement statement = connection.createStatement();
			ResultSet result = statement.executeQuery(sql);

			while (result.next()) {
				int EventID = result.getInt("EventID");
				String EventName = result.getString("EventName");
				String Lugar = result.getString("Lugar");
				String Description = result.getString("Description");
				EventData event = new EventData(EventID, EventName, Lugar, Description);
				eventVector.addElement(event);
			}
			for (int i = 0; i < eventVector.size(); i++) {
				EventData event = eventVector.elementAt(i);
				resul += "<center><br><br><h2 style=\"color:#953A27\">";
				resul += "EVENTO# " + event.EventID;
				resul += "</h2></center><div class=\"container\"><div class=\"card-deck mt-3\" style=\"color: #953A27\"><div class=\"card text-center border-info\"><div class=\"card-body\"><h4 class=\"card-title\" style=\"color: #953A27; text-align: left;\">";
				resul += event.EventName;
				resul += " </h4><p class=\"card-text\" style=\"text-align: left;\">&iexcl;";
				resul += event.Description;
				resul += " </p><button id = \"apuntate\" type=\"submit\" class=\"btn btn-primary\" style=\"background-color: #953A27; margin-left: auto; float: right;\" onclick=\"signUpEvent(" + event.EventID + ", 'Regular')\">";
				resul += " Ap&uacutentate</button>";
				resul += " </div></div></div></div>";
			}
			statement.close();
			result.close();
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("Error in getEventList: " + sql + " Exception: " + e);
		}
		return resul;
	}

	public static String getEventAttendingList(Connection connection,String id) {
		Vector<EventData> eventVector = new Vector<>();
		String eventsHTML = "";

		String sql = "SELECT Event.EventName, Event.Lugar, Event.Description, Event.Type " +
		"FROM Inscripciones " +
		"JOIN Event ON Event.EventID = Inscripciones.EventID " +
		"WHERE Inscripciones.UserName = ?";
		System.out.println("getEventList: " + sql);

		try {
			PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setString(1, id);
            ResultSet result = stmt.executeQuery();

			while (result.next()) {
				String EventName = result.getString("EventName");
				String Lugar = result.getString("Lugar");
				String Description = result.getString("Description");
				String Type = result.getString("Type");
				EventData event = new EventData(EventName, Lugar, Description, Type, 0);
				eventVector.addElement(event);
			}
			for (int i = 0; i < eventVector.size(); i++) {
				EventData event = eventVector.elementAt(i);
				eventsHTML += "<div class='event'>";
				eventsHTML += "<h3>" + event.EventName + "</h3>";
				eventsHTML += "<p>Lugar: " + event.Lugar + "</p>";
				eventsHTML += "<p>Description: " + event.Description + "</p>";
				eventsHTML += "<p>Type: " + event.Type + "</p>";
				eventsHTML += "</div>";
			}
			stmt.close();
			result.close();
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("Error in getEventList: " + sql + " Exception: " + e);
		}
		return eventsHTML;
	}
	public static Vector<EventData> getEventListVec(Connection connection) {
		Vector<EventData> eventVector = new Vector<>();
		String resul = "";

		String sql = "SELECT EventID, EventName, Lugar, Description FROM Event";
		System.out.println("getEventList: " + sql);

		try {
			Statement statement = connection.createStatement();
			ResultSet result = statement.executeQuery(sql);

			while (result.next()) {
				int EventID = result.getInt("EventID");
				String EventName = result.getString("EventName");
				String Lugar = result.getString("Lugar");
				String Description = result.getString("Description");
				EventData event = new EventData(EventID, EventName, Lugar, Description);
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
		String sql = "Select EventID, EventName, Lugar, Description FROM Event";
		sql += " WHERE Event.EventID=?";
		System.out.println("getEventEdit: " + sql);
		try {

			PreparedStatement pstmt = connection.prepareStatement(sql);
			pstmt.setString(1, id);
			ResultSet result = pstmt.executeQuery();

			while (result.next()) {
				event = new EventData(
						result.getInt("EventID"),
						result.getString("EventName"),
						result.getString("Lugar"),
						result.getString("Description"));
			}

		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("Error in getEventList: " + sql + " Exception: " + e);
		}
		return event;
	}

	public static int EventUpdate(Connection connection, EventData Event) {
		String sql = "UPDATE Event ";
		sql += "SET EventName = ?, Lugar= ?, Description= ?";
		sql += " WHERE EventID = ?";
		System.out.println("EventUpdate: " + sql);
		int n = 0;
		try {

			PreparedStatement stmtUpdate = connection.prepareStatement(sql);
			stmtUpdate.setString(1, Event.EventName);
			stmtUpdate.setString(2, Event.Lugar);
			stmtUpdate.setInt(4, Event.EventID);
			stmtUpdate.setString(3, Event.Description);
			// al se run update se guarda en un int n
			n = stmtUpdate.executeUpdate();
			stmtUpdate.close();

		} catch (SQLException e) {
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
		String sql = "INSERT INTO Event (EventID, EventName, Lugar, Description ) "
				+ "VALUES (?, ?, ?, ?)";
		System.out.println("InsertEvent: " + sql);
		int n = 0;

		try {

			PreparedStatement stmtUpdate = connection.prepareStatement(sql);
			stmtUpdate.setInt(1, Event.EventID);
			stmtUpdate.setString(2, Event.EventName);
			stmtUpdate.setString(3, Event.Lugar);
			stmtUpdate.setString(4, Event.Description);
			n = stmtUpdate.executeUpdate();
			stmtUpdate.close();

		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("Error in InsertEvent: " + sql + " Exception: " + e);
		}
		return n;
	}

}