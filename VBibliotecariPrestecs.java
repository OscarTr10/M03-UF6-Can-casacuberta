import javax.swing.*;
import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class VBibliotecariPrestecs extends JFrame {
    private GestorFuncions gestorPrestecs;  //Variable para poder importar las funciones del GestorFunciones

    public VBibliotecariPrestecs() {
        gestorPrestecs = new GestorFuncions(); //Declaro variable para llamar al GestorFunciones
        setTitle("Gestió de Préstecs");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); //Cuando cierre ventana que finalize.
        setSize(300, 200);
        setLayout(new BorderLayout());
        JPanel panel = new JPanel(new GridLayout(3, 1));    //Crear el grid para los botones 
        JButton comprovarPrestecsButton = new JButton("Comprovar i Retardar Préstecs"); //Genero los botones
        JButton crearPrestecButton = new JButton("Crear Préstec");
        JButton completarPrestecButton = new JButton("Completar Préstec");
        panel.add(comprovarPrestecsButton);     //Añado los botones
        panel.add(crearPrestecButton);
        panel.add(completarPrestecButton);
        add(panel, BorderLayout.CENTER);    //Coloca el panel en el centro del contenedor y ocupa todo el espacio restante.

        //Acciones de los botones
        comprovarPrestecsButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                gestorPrestecs.comprovarIRetardarPrestecs(); //Aqui llamo a la funcion entera que he decaraco en el Gestor funciones, ya que no tiene botones ni nada.
            }
        });

        crearPrestecButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                afegirPrestec();
            }
        });

        completarPrestecButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                completarPrestec();
            }
        });
        setLocationRelativeTo(null);       //Centra la ventan en el medio
        panel.add(comprovarPrestecsButton); //Añadir los botones
        panel.add(crearPrestecButton);
        panel.add(completarPrestecButton);
        add(panel);     //Añadir el panel.
    }

    public void afegirPrestec() {
        JFrame frame = new JFrame("Crear Préstec"); // Primero creamos el marco
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setSize(400, 200);
        JPanel panel = new JPanel(); // Aquí creamos el panel/ventana
        JLabel label = new JLabel("Introdueix els detalls del nou préstec:");
        JTextField idLlibreField = new JTextField(10);
        JTextField idUsuariField = new JTextField(10);
        JTextField dataRetornPrevistField = new JTextField(10);
        JButton crearButton = new JButton("Crear");
        panel.add(label);
        panel.add(new JLabel("ID del Llibre:"));
        panel.add(idLlibreField);
        panel.add(new JLabel("ID de l'Usuari:"));
        panel.add(idUsuariField);
        panel.add(new JLabel("Data de Retorn Prevista (AAAA-MM-DD):"));
        panel.add(dataRetornPrevistField);
        panel.add(crearButton);
        frame.add(panel);
        frame.setLocationRelativeTo(null); // Centra la finestra en la pantalla
        frame.setVisible(true);

        crearButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    int idLlibre = Integer.parseInt(idLlibreField.getText());   //convierte el texto en un int.
                    int idUsuari = Integer.parseInt(idUsuariField.getText());
                    String dataRetornPrevistText = dataRetornPrevistField.getText();
                    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
                    Date dataRetornPrevist = dateFormat.parse(dataRetornPrevistText);
                    boolean success = gestorPrestecs.afegirPrestec(idLlibre, idUsuari, dataRetornPrevist);
                    if (success) {
                        JOptionPane.showMessageDialog(frame, "Préstec creat correctament.", "Èxit", JOptionPane.INFORMATION_MESSAGE);
                        frame.dispose(); // Tanca la finestra després de crear el préstec
                    } else {
                        // Mostrar mensaje de error si el libro no está disponible o no existe
                        JOptionPane.showMessageDialog(frame, "El llibre no està disponible o no existeix.", "Error", JOptionPane.ERROR_MESSAGE);
                    }
                } catch (NumberFormatException | ParseException ex) {
                    JOptionPane.showMessageDialog(frame, "Error en els camps introduïts. Si us plau, revisa'ls.", "Error", JOptionPane.ERROR_MESSAGE);
                    ex.printStackTrace();
                }
            }
        });
    }

    public void completarPrestec() {
        JFrame frame = new JFrame("Completar Préstec");
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setSize(400, 100);
        JPanel panel = new JPanel();
        JLabel label = new JLabel("Introdueix l'ID del préstec a completar:");
        JTextField idPrestecField = new JTextField(10);
        JButton completarButton = new JButton("Completar");
        panel.add(label);
        panel.add(idPrestecField);
        panel.add(completarButton);
        frame.add(panel);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
        
        completarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {  
                try {      //Genero otra acción del botón completar
                int idPrestec = Integer.parseInt(idPrestecField.getText());     //Cogo los parámetros que me han pasado en la JLabel en este caso el id
                gestorPrestecs.completarPrestec(idPrestec);
                JOptionPane.showMessageDialog(frame, "Préstec completat amb èxit!", "Èxit", JOptionPane.INFORMATION_MESSAGE);     //Le asigno la función completar
                frame.dispose();
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(frame, "ID del préstec no vàlid. Si us plau, introdueix un número enter.", "Error", JOptionPane.ERROR_MESSAGE);
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(frame, "Error en completar el préstec: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
    }

    public static void obrirVBibliotecariPrestecs() {       //Esta función la voy a llamar en VIniciSesio depende del rol que tenga el usuario que inicie
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new VBibliotecariPrestecs().setVisible(true);
            }
        });
    }
}

