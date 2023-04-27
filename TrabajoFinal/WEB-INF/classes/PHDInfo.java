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
	
	PHDInfo(String UserID, String Area, String Ciudad, String Movil, String Despacho, String Nombre) {

        this.UserID  = UserID;
        this.Area = Area;
		this.Ciudad=Ciudad;
        this.Movil = Movil;
		this.Despacho=Despacho;
        this.Nombre=Nombre;

    }
	public static PHDInfo getPHDInfo(Connection connection, String id) {
		PHDInfo PHD = null;
		String sql = "Select UserID, Area, Ciudad, Movil, Despacho, Nombre  FROM Info";
		sql += " WHERE Info.UserID=?";
		System.out.println("getPHDEdit: " + sql);
		try {
			// comunicacion con la BBDD
			PreparedStatement pstmt=connection.prepareStatement(sql); // mas seguro para eviatr hackeos y por el ?
			pstmt.setString(1, id);
			ResultSet result = pstmt.executeQuery();
			
			// Con este while vamos a ir recorriendo linea a linea la matriz Resultset que nos devuelve el sql y vamo a crear UN Suppliers con los valores de las columnas 
            
			while(result.next()) {
				PHD = new PHDInfo(
					result.getString("UserID"),
					result.getString("Area"),
					result.getString("Ciudad"),
                    result.getString("Movil"),
					result.getString("Despacho"),
                    result.getString("Nombre")
				);
			}
			
		}
		catch(SQLException e) {
			e.printStackTrace();
			System.out.println("Error in getPHDList: " + sql + " Exception: " + e);
		}
		return PHD;
	}
}
