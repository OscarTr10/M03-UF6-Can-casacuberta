import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class GestorFuncions {

    private static Connection getConnexio() throws SQLException {
        String url = "jdbc:mysql://localhost:3306/Programacion";
        String usuari = "program";
        String contrasenya = "L@p1neda";
        return DriverManager.getConnection(url, usuari, contrasenya);
    }

    public static Usuari iniciarSessio(String email, String contrasenya) {
        String consulta = "SELECT * FROM Usuaris WHERE Email = ?";
        try (Connection connexio = getConnexio();
            PreparedStatement declaracio = connexio.prepareStatement(consulta)) {
            declaracio.setString(1, email);
            ResultSet resultSet = declaracio.executeQuery();
            if (resultSet.next()) {
                String cognoms = resultSet.getString("Cognoms");
                String nom = resultSet.getString("Nom");
                String telefon = resultSet.getString("Telefon");
                // La contraseña esperada es la primera letra del primer apellido + nombre completo + tres primeros dígitos del teléfono
                String contrasenyaEsperada = cognoms.substring(0, 1).toLowerCase() + nom.toLowerCase() + telefon.substring(0, 3);
                if (contrasenya.equals(contrasenyaEsperada)) {
                    return new Usuari(
                        resultSet.getInt("ID_Usuari"),
                        nom,
                        cognoms,
                        email,
                        telefon,
                        resultSet.getString("Rol"),
                        resultSet.getDate("Data_Registre")
                    );
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
    
    public boolean modificarUsuari(Usuari usuari) {
        String consulta = "UPDATE Usuaris SET Nom = ?, Cognoms = ?, Email = ?, Telefon = ?, Rol = ? WHERE ID_Usuari = ?";
        try (Connection connexio = getConnexio();
            PreparedStatement declaracio = connexio.prepareStatement(consulta)) {
            declaracio.setString(1, usuari.getNom());
            declaracio.setString(2, usuari.getCognoms());
            declaracio.setString(3, usuari.getEmail());
            declaracio.setString(4, usuari.getTelefon());
            declaracio.setString(5, usuari.getRol());
            declaracio.setInt(6, Usuari.getId());
            int filesAfectades = declaracio.executeUpdate();
            return filesAfectades > 0;      //Si no updatea ninguna fila no devuelve nada
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean afegirUsuari(Usuari usuari) {
        String consulta = "INSERT INTO Usuaris (Nom, Cognoms, Email, Telefon, Rol, Data_Registre) VALUES (?, ?, ?, ?, ?, ?)";
        try (Connection connexio = getConnexio();
            PreparedStatement declaracio = connexio.prepareStatement(consulta)) {
            declaracio.setString(1, usuari.getNom());
            declaracio.setString(2, usuari.getCognoms());
            declaracio.setString(3, usuari.getEmail());
            declaracio.setString(4, usuari.getTelefon());
            declaracio.setString(5, usuari.getRol());
            declaracio.setDate(6, Date.valueOf(LocalDate.now())); // Establece la fecha actual
            int filesAfectades = declaracio.executeUpdate();
            return filesAfectades > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    //Validar el teléfono,correo y rol con los parámetros siguientes
    public boolean validarEmail(String email) {
        return email != null && email.contains("@");
    }

    public boolean validarTelefon(String telefon) {
        return telefon != null && Pattern.matches("\\d{9}", telefon); //Que contenga 9 números
    }

    public boolean validarRol(String rol) {
        return "Lector".equalsIgnoreCase(rol) || "Bibliotecari".equalsIgnoreCase(rol);
    }

    public boolean eliminarUsuari(int id) {
        String consultaEliminarPrestecs = "DELETE FROM Prestecs WHERE ID_Usuari = ?";       //Al borrar un usuario tenemos que borrar sus prestecs si no da error por Foreing Key
        String consultaEliminarUsuari = "DELETE FROM Usuaris WHERE ID_Usuari = ?";
        try (Connection connexio = getConnexio();
            PreparedStatement declaracioPrestecs = connexio.prepareStatement(consultaEliminarPrestecs);
            PreparedStatement declaracioUsuari = connexio.prepareStatement(consultaEliminarUsuari)) {
            //Eliminar files de la taula Prestecs
            declaracioPrestecs.setInt(1, id);
            declaracioPrestecs.executeUpdate();
            //Eliminar fila de la taula Usuaris
            declaracioUsuari.setInt(1, id);
            return declaracioUsuari.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public List<Llibres> cercarLlibres(String titol) {
        List<Llibres> llibres = new ArrayList<>();
        String consulta = "SELECT * FROM Llibres WHERE Titol LIKE ?";
        try (Connection connexio = getConnexio();
            PreparedStatement declaracio = connexio.prepareStatement(consulta)) {
            declaracio.setString(1, "%" + titol + "%");
            ResultSet resultSet = declaracio.executeQuery();
            while (resultSet.next()) {
                llibres.add(new Llibres(
                    resultSet.getInt("ID_Llibre"),
                    resultSet.getString("Titol"),
                    resultSet.getString("Autor"),
                    resultSet.getString("ISBN"),
                    resultSet.getString("Editorial"),
                    resultSet.getInt("Any_Publicacio"),
                    resultSet.getString("Categoria"),
                    resultSet.getString("Estat")
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return llibres;
    }

    public List<Prestecs> obtenirPrestecs(int idUsuari) {
        List<Prestecs> prestecs = new ArrayList<>();
        String consulta = "SELECT * FROM Prestecs WHERE ID_Usuari = ?";
        try (Connection connexio = getConnexio();
            PreparedStatement declaracio = connexio.prepareStatement(consulta)) {
            declaracio.setInt(1, idUsuari);
            ResultSet resultSet = declaracio.executeQuery();
            while (resultSet.next()) {
                prestecs.add(new Prestecs(
                    resultSet.getInt("ID_Prestec"),
                    resultSet.getInt("ID_Llibre"),
                    resultSet.getInt("ID_Usuari"),
                    resultSet.getDate("Data_Prestec"),
                    resultSet.getDate("Data_Retorn_Prevista"),
                    resultSet.getDate("Data_Retorn_Real"),
                    resultSet.getString("Estat")
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return prestecs;
    }

    public List<Integer> comprovarIRetardarPrestecs() {
        List<Integer> idsRetrasats = new ArrayList<>();
        try {
            Connection connexio = getConnexio();
            // Actualitza els préstecs a 'retardat' si la data de retorn prevista és menor que la data actual
            String updateQuery = "UPDATE Prestecs SET Estat = 'retardat' WHERE Estat = 'actiu' AND Data_Retorn_Prevista < ?";
            PreparedStatement updatePS = connexio.prepareStatement(updateQuery);
            updatePS.setDate(1, Date.valueOf(LocalDate.now())); // Data actual
            int rowsUpdated = updatePS.executeUpdate();

            // Selecciona tots els préstecs que estan en estat 'retardat'
            String selectQuery = "SELECT * FROM Prestecs WHERE Estat = 'retardat'";
            PreparedStatement selectPS = connexio.prepareStatement(selectQuery);
            ResultSet rs = selectPS.executeQuery();
            // Crear el model de la taula
            DefaultTableModel model = new DefaultTableModel();
            model.addColumn("ID Préstec");
            model.addColumn("ID Llibre");
            model.addColumn("ID Usuari");
            model.addColumn("Data Préstec");
            model.addColumn("Data Retorn Prevista");
            model.addColumn("Data Retorn Real");
            model.addColumn("Estat");
            // Itera sobre cada fila del resultat
            while (rs.next()) {
                // Obtén tota la informació del préstec
                int idPrestec = rs.getInt("ID_Prestec");
                int idLlibre = rs.getInt("ID_Llibre");
                int idUsuari = rs.getInt("ID_Usuari");
                Date dataPrestec = rs.getDate("Data_Prestec");
                Date dataRetornPrevista = rs.getDate("Data_Retorn_Prevista");
                Date dataRetornReal = rs.getDate("Data_Retorn_Real");
                String estat = rs.getString("Estat");
                // Afegeix l'ID a la llista
                idsRetrasats.add(idPrestec);
                // Afegeix la informació a la taula
                model.addRow(new Object[]{idPrestec, idLlibre, idUsuari, dataPrestec, dataRetornPrevista, dataRetornReal, estat});
            }
            // Mostrar els resultats en una finestra interesa que sigui aquí perque només mostrare dades
            JFrame panel = new JFrame("Préstecs Retardats");
            panel.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
            panel.setSize(1000, 400);
            // Afegir el nombre de préstecs actualitzats a la finestra
            JLabel label = new JLabel("Número de préstecs retardats actualitzats: " + rowsUpdated);
            panel.add(label, BorderLayout.NORTH);
            JTable table = new JTable(model);
            JScrollPane scrollPane = new JScrollPane(table);
            panel.add(scrollPane, BorderLayout.CENTER);
            panel.setVisible(true);
            panel.setLocationRelativeTo(null);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return idsRetrasats;
    }

    public boolean afegirPrestec(int idLlibre, int idUsuari, java.util.Date dataRetornPrevist) {
        Connection connexio = null;
        PreparedStatement selectPS = null;
        PreparedStatement insertPS = null;
        PreparedStatement updatePS = null;
        try {
            connexio = getConnexio();
            // Comprova l'estat del llibre
            String selectQuery = "SELECT Estat FROM Llibres WHERE ID_Llibre = ?";
            selectPS = connexio.prepareStatement(selectQuery);
            selectPS.setInt(1, idLlibre);
            ResultSet rs = selectPS.executeQuery();
            if (rs.next()) {
                String estatLlibre = rs.getString("Estat");
                if ("disponible".equalsIgnoreCase(estatLlibre)) {
                    // Insereix un nou préstec a la taula Prestecs
                    String insertQuery = "INSERT INTO Prestecs (ID_Llibre, ID_Usuari, Data_Prestec, Data_Retorn_Prevista, Estat) VALUES (?, ?, ?, ?, ?)";
                    insertPS = connexio.prepareStatement(insertQuery);
                    insertPS.setInt(1, idLlibre);
                    insertPS.setInt(2, idUsuari);
                    insertPS.setDate(3, new java.sql.Date(System.currentTimeMillis())); // Data actual
                    insertPS.setDate(4, new java.sql.Date(dataRetornPrevist.getTime())); // Converteix java.util.Date a java.sql.Date
                    insertPS.setString(5, "actiu"); // Estat inicial: actiu
                    insertPS.executeUpdate();
                    // Actualitza l'estat del llibre a "no disponible"
                    String updateQuery = "UPDATE Llibres SET Estat = 'Prestat' WHERE ID_Llibre = ?";
                    updatePS = connexio.prepareStatement(updateQuery);
                    updatePS.setInt(1, idLlibre);
                    updatePS.executeUpdate();
                    return true; // Prestec creat correctament
                } else {
                    return false; // El llibre no està disponible per al préstec
                }
            } else {
                return false; // El llibre no existeix
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return false; // Error en la base de dades
        }
    }

    public void completarPrestec(int idPrestec) {
        Connection connexio = null;
        PreparedStatement updatePrestecPS = null;
        PreparedStatement selectLlibrePS = null;
        PreparedStatement updateLlibrePS = null;
        try {
            connexio = getConnexio();
            // Actualiza el estado del préstec a 'completat'
            String updatePrestecQuery = "UPDATE Prestecs SET Estat = 'completat', Data_Retorn_Real = ? WHERE ID_Prestec = ?";
            updatePrestecPS = connexio.prepareStatement(updatePrestecQuery);
            updatePrestecPS.setDate(1, new java.sql.Date(System.currentTimeMillis())); // Fecha actual
            updatePrestecPS.setInt(2, idPrestec);
            updatePrestecPS.executeUpdate();
            // Obtiene el ID_Llibre del préstec completado
            String selectLlibreQuery = "SELECT ID_Llibre FROM Prestecs WHERE ID_Prestec = ?";
            selectLlibrePS = connexio.prepareStatement(selectLlibreQuery);
            selectLlibrePS.setInt(1, idPrestec);
            ResultSet rs = selectLlibrePS.executeQuery();
            if (rs.next()) {
                int idLlibre = rs.getInt("ID_Llibre");
                // Actualiza el estado del libro a 'disponible'
                String updateLlibreQuery = "UPDATE Llibres SET Estat = 'disponible' WHERE ID_Llibre = ?";
                updateLlibrePS = connexio.prepareStatement(updateLlibreQuery);
                updateLlibrePS.setInt(1, idLlibre);
                updateLlibrePS.executeUpdate();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
