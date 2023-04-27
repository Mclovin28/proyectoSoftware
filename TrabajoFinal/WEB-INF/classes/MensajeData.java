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


	//PHDData (int ID, String UserID, String Password) { // constructor (nombre igual a la clase)
        //this.ID    = ID; // referirte a los que estan arriba y lo igualas a lo que le ingresas.
        //this.UserID  = UserID;
        //this.Password = Password;
    //}
	
	MensajeData (String ID, String UserID, String Mensaje, String Realizado) { // constructor (nombre igual a la clase)
		this.ID  = ID;
        this.UserID  = UserID;
        this.Mensaje = Mensaje;
		this.Realizado=Realizado;

    }
	
	public static Vector<MensajeData> getMensajeList(Connection connection){
        Vector<MensajeData> vec = new Vector<MensajeData>();
//definir el sql
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
	
	//vamos a crear un metodo que nos devuelva UN supplier (Ussers), cuando le ingresemos un connection
     //y un id (Connection connection, int id)
	 
	 
	public static MensajeData getMensajeEdit(Connection connection, String id) {
		MensajeData supplier = null;
		String sql = "Select ID, UserID, Mensaje, Realizado FROM Mensajes";
		sql += " WHERE Mensajes.ID=?";
		System.out.println("getMensajeEdit: " + sql);
		try {
			// comunicacion con la BBDD
			PreparedStatement pstmt=connection.prepareStatement(sql); // mas seguro para eviatr hackeos y por el ?
			pstmt.setString(1, id);
			ResultSet result = pstmt.executeQuery();
			
			// Con este while vamos a ir recorriendo linea a linea la matriz Resultset que nos devuelve el sql y vamo a crear UN Suppliers con los valores de las columnas 
            
			while(result.next()) {
				supplier = new MensajeData(
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
		return supplier;
	}
	
	/*
	public static int metodoUpdate(Connection connection, MensajeData supplier) {
		String sql ="UPDATE Ussers ";
			sql+= "SET UserID = ?, Password = ?, Role= ?";
			sql+= " WHERE ID = ?";
			System.out.println("metodoUpdate: " + sql);
			int n = 0;
		try {
			
			PreparedStatement stmtUpdate= connection.prepareStatement(sql);
			stmtUpdate.setString(1,supplier.UserID);
			stmtUpdate.setString(2,supplier.Password);
			stmtUpdate.setString(3,supplier.Role);
			stmtUpdate.setInt(4,supplier.ID);
			// al se run update se guarda en un int n
			n = stmtUpdate.executeUpdate();
			stmtUpdate.close();
			
		} catch(SQLException e) {
			e.printStackTrace();
			System.out.println("Error in metodoUpdate: " + sql + " Exception: " + e);
		}
			return n;
	}

	public static int insertSupplier(Connection connection, MensajeData supplier) {
		String sql ="INSERT INTO Ussers (UserID, Password, Role, CompanyName, ContactTitle) "
            + "VALUES (?, ?, ?, ?, ?)";
			System.out.println("insertSupplier: " + sql);
			int n = 0;
			
		try {
			
			PreparedStatement stmtUpdate= connection.prepareStatement(sql);
			stmtUpdate.setString(1,supplier.UserID);
			stmtUpdate.setString(2,supplier.Password);
			stmtUpdate.setString(3,supplier.Role);
			stmtUpdate.setString(4,"Mercadona");
			stmtUpdate.setString(5,"Ventas manager");
			// al se run update se guarda en un int n
			n = stmtUpdate.executeUpdate();
			stmtUpdate.close();
			
		} catch(SQLException e) {
			e.printStackTrace();
			System.out.println("Error in metodoUpdate: " + sql + " Exception: " + e);
		}
			return n;
	}*/

	
	
	
}
