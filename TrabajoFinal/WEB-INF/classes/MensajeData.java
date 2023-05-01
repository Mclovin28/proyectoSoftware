import java.util.Vector;
import java.sql.Connection;
import java.sql.Statement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class MensajeData {
    String ID;
	String UserID;
	String Mensaje;
	String Realizado;

	
	MensajeData (String ID, String UserID, String Mensaje, String Realizado) { // constructor (nombre igual a la clase)
		this.ID  = ID;
        this.UserID  = UserID;
        this.Mensaje = Mensaje;
		this.Realizado=Realizado;

    }
	MensajeData (String ID, String Realizado) { // constructor (nombre igual a la clase)
		this.ID  = ID;
		this.Realizado=Realizado;

    }
	
	public static Vector<MensajeData> getMensajeList(Connection connection){
        Vector<MensajeData> vec = new Vector<MensajeData>();

        String sql = "Select ID, UserID, Mensaje, Realizado FROM Mensajes";
        System.out.println("getMensajeList: " + sql); // verificar lo que hago
		
        try { // para poder detectar un error. TRY Y CATCH SIEMPRE ES IGUAL
			Statement statement=connection.createStatement();
            ResultSet result = statement.executeQuery(sql);
            
			while(result.next()) {
                MensajeData Mensaje = new MensajeData( // siempre que creas un tipo de variable pones new por delante
					result.getString("ID"),
                    result.getString("UserID"),
                    result.getString("Mensaje"),
					result.getString("Realizado")
                );
                vec.addElement(Mensaje);
            }
        } catch(SQLException e) {
            e.printStackTrace();
            System.out.println("Error in getMensajeList: " + sql + " Exception: " + e);
        }
        return vec;
    }
	public static int metodoUpdate(Connection connection, MensajeData Mensaje) {
		String sql ="UPDATE Mensajes ";
		sql+= "SET UserID= ?, Mensaje= ?, Realizado= ?";
		sql+= " WHERE ID = ?";
		System.out.println("metodoUpdate: " + sql);
        int n = 0;
		try {
			
			PreparedStatement stmtUpdate= connection.prepareStatement(sql);
			stmtUpdate.setString(1,Mensaje.UserID);
            stmtUpdate.setString(2,Mensaje.Mensaje);
            stmtUpdate.setString(3,Mensaje.Realizado);
            stmtUpdate.setString(4,Mensaje.ID);
			// al se run update se guarda en un int n
			n = stmtUpdate.executeUpdate();
			stmtUpdate.close();
			
		} catch(SQLException e) {
			e.printStackTrace();
			System.out.println("Error in metodoUpdate: " + sql + " Exception: " + e);
		}
			return n;
	}
	
	
	 
	public static MensajeData getMensajeEdit(Connection connection, String id) {
		MensajeData Mensaje = null;
		String sql = "Select ID, UserID, Mensaje, Realizado FROM Mensajes";
		sql += " WHERE Mensajes.ID=?";
		System.out.println("getMensajeEdit: " + sql);
		try {
			
			PreparedStatement pstmt=connection.prepareStatement(sql); // mas seguro para eviatr hackeos y por el ?
			pstmt.setString(1, id);
			ResultSet result = pstmt.executeQuery();

            
			while(result.next()) {
				Mensaje = new MensajeData(
					result.getString("ID"),
					result.getString("UserID"),
					result.getString("Mensaje"),
					result.getString("Realizado")
				);
			}
			
		}
		catch(SQLException e) {
			e.printStackTrace();
			System.out.println("Error in getMensajeList: " + sql + " Exception: " + e);
		}
		return Mensaje;
	}
	

	public static int InsertMensaje(Connection connection, MensajeData Mensaje) {
		String sql ="INSERT INTO Mensajes (ID, UserID, Mensaje, Realizado) "
            + "VALUES (?, ?, ?, ?)";
			System.out.println("InsertMensaje: " + sql);
			int n = 0;
			
		try {
			
			PreparedStatement stmtUpdate= connection.prepareStatement(sql);
			stmtUpdate.setString(1,Mensaje.ID);
			stmtUpdate.setString(2,Mensaje.UserID);
			stmtUpdate.setString(3,Mensaje.Mensaje);
			stmtUpdate.setString(4,Mensaje.Realizado);
			// al se run update se guarda en un int n
			n = stmtUpdate.executeUpdate();
			stmtUpdate.close();
			
		} catch(SQLException e) {
			e.printStackTrace();
			System.out.println("Error in metodoUpdate: " + sql + " Exception: " + e);
		}
			return n;
	}
    public static void deleteMensaje(Connection connection, String ID) {
        String sql = "DELETE FROM Mensajes WHERE ID = ?";

        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setString(1, ID);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Error deleting Message with ID: " + e.getMessage());
        }
    }


	
	
	
}
