import javax.swing.*;
import java.awt.*;
import java.util.List;

public class VLectorHistoricPrestecs extends JFrame {
    private JTextArea areaResultats;
    private GestorFuncions gestorUsuaris;    //Esta línea si al final lo hago por el nombre va fuera.

    public VLectorHistoricPrestecs() {
        gestorUsuaris = new GestorFuncions();
        setTitle("Històric de Préstecs");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());
        areaResultats = new JTextArea();
        areaResultats.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(areaResultats);
        add(scrollPane, BorderLayout.CENTER);
        mostrarPrestecs();
    }

    private void mostrarPrestecs() {
        int idUsuari = Usuari.getId();  //Cojo la id del usuario que pertoca
        List<Prestecs> prestecs = gestorUsuaris.obtenirPrestecs(idUsuari);
        areaResultats.setText("\n\nPréstecs:\n\n");
        for (Prestecs prestec : prestecs) {
            areaResultats.append("ID Préstec: " + prestec.getIdPrestec() + "\n");
            areaResultats.append("ID Llibre: " + prestec.getIdLlibre() + "\n");
            areaResultats.append("Data Préstec: " + prestec.getDataPrestec() + "\n");
            areaResultats.append("Data Retorn Prevista: " + prestec.getDataRetornPrevista() + "\n");
            areaResultats.append("Data Retorn Real: " + prestec.getDataRetornReal() + "\n");
            areaResultats.append("Estat: " + prestec.getEstat() + "\n");
            areaResultats.append("-----------------------------\n");
        }
    }
}

