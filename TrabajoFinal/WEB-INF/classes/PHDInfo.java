import java.util.Vector;
import java.sql.Connection;
import java.sql.Statement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class PHDInfo {
	String UserID;
	String Area;
	String Ciudad;
	String Movil;
	String Despacho;
	String Nombre;
	String Password;
	String Role;
	String Sexo;

	PHDInfo(String UserID, String Area, String Ciudad, String Movil, String Despacho, String Nombre) {

		this.UserID = UserID;
		this.Area = Area;
		this.Ciudad = Ciudad;
		this.Movil = Movil;
		this.Despacho = Despacho;
		this.Nombre = Nombre;

	}

	PHDInfo(String UserID, String Area, String Ciudad, String Movil, String Despacho, String Nombre, String Password,
			String Role, String Sexo) {

		this.UserID = UserID;
		this.Area = Area;
		this.Ciudad = Ciudad;
		this.Movil = Movil;
		this.Despacho = Despacho;
		this.Nombre = Nombre;
		this.Password = Password;
		this.Role = Role;
		this.Sexo = Sexo;

	}

	PHDInfo(String UserID, String Movil, String Despacho, String Nombre) {
		this.UserID = UserID;
		this.Movil = Movil;
		this.Despacho = Despacho;
		this.Nombre = Nombre;
	}

	PHDInfo(String Sexo) {
		this.Sexo = Sexo;
	}

	public static PHDInfo getPHDInfo(Connection connection, String id) {
		PHDInfo PHD = null;
		String sql = "Select UserID, Area, Ciudad, Movil, Despacho, Nombre  FROM Users";
		sql += " WHERE UserID=?";
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
				PHD = new PHDInfo(
						result.getString("UserID"),
						result.getString("Area"),
						result.getString("Ciudad"),
						result.getString("Movil"),
						result.getString("Despacho"),
						result.getString("Nombre"));
			}

		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("Error in getPHDList: " + sql + " Exception: " + e);
		}
		return PHD;
	}

	public static int updateInfo(Connection connection, PHDInfo PHD) {
		String sql = "UPDATE Users ";
		sql += "SET UserID = ?, Movil = ?, Despacho = ?";
		sql += " WHERE Nombre = ?";
		System.out.println("metodoUpdate: " + sql);
		int n = 0;
		try {

			PreparedStatement stmtUpdate = connection.prepareStatement(sql);
			stmtUpdate.setString(1, PHD.UserID);
			stmtUpdate.setString(2, PHD.Movil);
			stmtUpdate.setString(3, PHD.Despacho);
			stmtUpdate.setString(4, PHD.Nombre);
			// al se run update se guarda en un int n
			n = stmtUpdate.executeUpdate();
			stmtUpdate.close();

		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("Error in metodoUpdate: " + sql + " Exception: " + e);
		}
		return n;
	}

	public static String getUserSex(Connection connection, String id) {
		PHDInfo PHD = null;
		String sql = "Select Sexo FROM Users";
		sql += " WHERE UserID=?";
		System.out.println("getSEXO: " + sql);
		try {
			// comunicacion con la BBDD
			PreparedStatement pstmt = connection.prepareStatement(sql); // mas seguro para eviatr hackeos y por el ?
			pstmt.setString(1, id);
			ResultSet result = pstmt.executeQuery();

			// Con este while vamos a ir recorriendo linea a linea la matriz Resultset que
			// nos devuelve el sql y vamo a crear UN Suppliers con los valores de las
			// columnas

			while (result.next()) {
				PHD = new PHDInfo(
						result.getString("Sexo"));
			}

		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("Error in getPHDList: " + sql + " Exception: " + e);
		}
		return PHD.Sexo;
	}
}
