import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class VLector extends JFrame {
    public VLector() {
        setTitle("Menú Principal - Lector");
        setSize(400, 150);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        JPanel panel = new JPanel(new GridLayout(2,1));
        JButton buscarLlibresBoto = new JButton("Cercar Llibres");
        JButton historicPrestecsBoto = new JButton("Històric de Préstecs");
        buscarLlibresBoto.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                VLectorCercarLlibres vCercarLlibres = new VLectorCercarLlibres(); //No me esta buscando los libros
                vCercarLlibres.setVisible(true);
            }
        });

        historicPrestecsBoto.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                VLectorHistoricPrestecs vHistoricPrestecs = new VLectorHistoricPrestecs();      //Prestecs OK ojo cambiar 
                vHistoricPrestecs.setVisible(true);
            }
        });
        panel.add(buscarLlibresBoto);
        panel.add(historicPrestecsBoto);
        add(panel);
    }
  
    static void obrirVLector() {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new VLector().setVisible(true);
            }
        });
    }
}
