import java.util.Vector;
import java.sql.Connection;
import java.sql.Statement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class PHDData {
	String UserID;
	String Password;
	String Role;
	boolean Status;

	// PHDData (int ID, String UserID, String Password) { // constructor (nombre
	// igual a la clase)
	// this.ID = ID; // referirte a los que estan arriba y lo igualas a lo que le
	// ingresas.
	// this.UserID = UserID;
	// this.Password = Password;
	// }

	PHDData(String UserID, String Password, String Role) { // constructor (nombre igual a la clase)

		this.UserID = UserID;
		this.Password = Password;
		this.Role = Role;

	}

	PHDData(String UserID, String Password, String Role, boolean Status) { // constructor (nombre igual a la clase)

		this.UserID = UserID;
		this.Password = Password;
		this.Role = Role;
		this.Status = Status;

	}

	public static Vector<PHDData> getPHDList(Connection connection) {
		Vector<PHDData> vec = new Vector<PHDData>();
		// definir el sql
		String sql = "Select UserID, Password, Role, Status FROM Users";
		sql += " WHERE Role='phd'";
		System.out.println("getPHDList: " + sql); // verificar lo que hago

		try { // para poder detectar un error. TRY Y CATCH SIEMPRE ES IGUAL
			Statement statement = connection.createStatement();
			ResultSet result = statement.executeQuery(sql);

			while (result.next()) {
				// Utilizamos el constructor de Supplier que necesita (int ID, String UserID,
				// String city)
				PHDData PHD = new PHDData( // siempre que creas un tipo de variable pones new por delante
						result.getString("UserID"),
						result.getString("Password"),
						result.getString("Role"),
						result.getBoolean("Status"));
				vec.addElement(PHD);
			}
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("Error in getPHDList: " + sql + " Exception: " + e);
		}
		return vec;
	}

	// vamos a crear un metodo que nos devuelva UN supplier (Ussers), cuando le
	// ingresemos un connection
	// y un id (Connection connection, int id)

	public static PHDData getPHDEdit(Connection connection, String id) {
		PHDData PHD = null;
		String sql = "Select UserID, Password, Role FROM Users";
		sql += " WHERE Users.UserID=?";
		System.out.println("getPHDEdit: " + sql);
		try {
			// comunicacion con la BBDD
			PreparedStatement pstmt = connection.prepareStatement(sql); // mas seguro para eviatr hackeos y por el ?
			pstmt.setString(1, id);
			ResultSet result = pstmt.executeQuery();

			// Con este while vamos a ir recorriendo linea a linea la matriz Resultset que
			// nos devuelve el sql y vamo a crear UN Suppliers con los valores de las
			// columnas

			while (result.next()) {
				PHD = new PHDData(
						result.getString("UserID"),
						result.getString("Password"),
						result.getString("Role"));
			}

		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("Error in getPHDList: " + sql + " Exception: " + e);
		}
		return PHD;
	}

	public static int metodoUpdate(Connection connection, PHDData PHD) {
		String sql = "UPDATE Users ";
		sql += "SET Password = ?, Role= ?";
		sql += " WHERE UserID = ?";
		System.out.println("metodoUpdate: " + sql);
		int n = 0;
		try {

			PreparedStatement stmtUpdate = connection.prepareStatement(sql);
			stmtUpdate.setString(1, PHD.Password);
			stmtUpdate.setString(2, PHD.Role);
			stmtUpdate.setString(3, PHD.UserID);
			// al se run update se guarda en un int n
			n = stmtUpdate.executeUpdate();
			stmtUpdate.close();

		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("Error in metodoUpdate: " + sql + " Exception: " + e);
		}
		return n;
	}

	public static int InsertPHD(Connection connection, PHDData PHD) {
		String sql = "INSERT INTO Users (UserID, Password, Role) "
				+ "VALUES (?, ?, ?)";
		System.out.println("InsertPHD: " + sql);
		int n = 0;

		try {

			PreparedStatement stmtUpdate = connection.prepareStatement(sql);
			stmtUpdate.setString(1, PHD.UserID);
			stmtUpdate.setString(2, PHD.Password);
			stmtUpdate.setString(3, PHD.Role);
			// al se run update se guarda en un int n
			n = stmtUpdate.executeUpdate();
			stmtUpdate.close();

		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("Error in metodoUpdate: " + sql + " Exception: " + e);
		}
		return n;
	}

	public static void deleteUser(Connection connection, String userID) {
		String sql = "DELETE FROM Users WHERE UserID = ?";

		try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
			pstmt.setString(1, userID);
			pstmt.executeUpdate();
		} catch (SQLException e) {
			System.out.println("Error deleting user: " + e.getMessage());
		}
	}
}
