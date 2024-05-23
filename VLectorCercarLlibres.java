import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class VLectorCercarLlibres extends JFrame {
    private JTextField campCercaLlibre;
    private JTextArea areaResultats;
    private GestorFuncions gestorUsuaris;

    public VLectorCercarLlibres() {
        gestorUsuaris = new GestorFuncions();
        setTitle("Cercar Llibres");
        setSize(500, 300);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());
        JPanel cercarPanel = new JPanel();
        cercarPanel.setLayout(new FlowLayout());
        JLabel cercarLabel = new JLabel("Cercar Llibre per Titol:");
        campCercaLlibre = new JTextField(10);
        JButton cercarButton = new JButton("Cercar");
        cercarPanel.add(cercarLabel);
        cercarPanel.add(campCercaLlibre);
        cercarPanel.add(cercarButton);
        areaResultats = new JTextArea();
        areaResultats.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(areaResultats);
        add(cercarPanel, BorderLayout.NORTH);
        add(scrollPane, BorderLayout.CENTER);

        cercarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cercarLlibres();
            }
        });
    }

    private void cercarLlibres() {
        String nomLlibre = campCercaLlibre.getText();
        List<Llibres> llibresTrobats = gestorUsuaris.cercarLlibres(nomLlibre);
        mostrarResultats(llibresTrobats);
    }

    private void mostrarResultats(List<Llibres> llibresTrobats) {
        areaResultats.setText("");
        if (llibresTrobats.isEmpty()) {
            areaResultats.append("No s'han trobat llibres amb aquest nom.");
        } else {
            for (Llibres llibre : llibresTrobats) {
                areaResultats.append("ID: " + llibre.getIdLlibre() + "\n");
                areaResultats.append("Titol: " + llibre.getTitol() + "\n");
                areaResultats.append("Autor: " + llibre.getAutor() + "\n");
                areaResultats.append("ISBN: " + llibre.getIsbn() + "\n");
                areaResultats.append("Editorial: " + llibre.getEditorial() + "\n");
                areaResultats.append("Any Publicacio: " + llibre.getAnyPublicacio() + "\n");
                areaResultats.append("Categoria: " + llibre.getCategoria() + "\n");
                areaResultats.append("Estat: " + llibre.getEstat() + "\n");
                areaResultats.append("-----------------------------\n");
            }
        }
    }
}
