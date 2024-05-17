import java.sql.*;

public class Llibres {

    static final String DB_URL = "jdbc:mysql://localhost:3306/Programacion";
    static final String USER = "program";
    static final String PASS = "L@p1neda";
    static final String QUERY = "SELECT * FROM Llibres";

    public static void main(String[] args) {
        try (Connection conn = DriverManager.getConnection(DB_URL, USER, PASS)) {
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(QUERY);
            while (rs.next()) {
                System.out.println("ID_Llibre: " + rs.getInt("ID_Llibre") + 
                                   ", Títol: " + rs.getString("Titol") + 
                                   ", Autor: " + rs.getString("Autor") + 
                                   ", ISBN: " + rs.getString("ISBN") + 
                                   ", Editorial: " + rs.getString("Editorial") + 
                                   ", Any_Publicacio: " + rs.getInt("Any_Publicacio") + 
                                   ", Categoria: " + rs.getString("Categoria") + 
                                   ", Estat: " + rs.getString("Estat"));
            
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}

