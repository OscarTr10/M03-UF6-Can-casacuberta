import java.sql.*;

public class Usuaris {

    static final String DB_URL = "jdbc:mysql://localhost:3306/Programacion";
    static final String USER = "program";
    static final String PASS = "L@p1neda";
    static final String QUERY = "SELECT * FROM Usuaris";

    public static void main(String[] args) {
        try (Connection conn = DriverManager.getConnection(DB_URL, USER, PASS)) {
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(QUERY);
            while (rs.next()) {
                System.out.println("ID_Usuari: " + rs.getInt("ID_Usuari") + 
                                   ", Nom: " + rs.getString("Nom") + 
                                   ", Cognoms: " + rs.getString("Cognoms") + 
                                   ", Email: " + rs.getString("Email") + 
                                   ", Telefon: " + rs.getString("Telefon") + 
                                   ", Rol: " + rs.getString("Rol") + 
                                   ", Data_Registre: " + rs.getDate("Data_Registre"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
