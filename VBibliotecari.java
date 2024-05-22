import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class VBibliotecari extends JFrame {
    private GestorFuncions gestorUsuaris;

    public VBibliotecari() {
        gestorUsuaris = new GestorFuncions();
        setTitle("Menú Principal - Bibliotecari");
        setSize(400, 150);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new GridLayout(2, 1));
        JButton gestionUsuarisButton = new JButton("Gestió d'Usuaris");
        JButton gestionPrestecsButton = new JButton("Gestió de Préstecs");
        add(gestionUsuarisButton);
        add(gestionPrestecsButton);

        gestionUsuarisButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                VBibliotecariUsuaris.obrirVBibliotecariUsuaris();
            }
        });

        gestionPrestecsButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                VBibliotecariPrestecs.obrirVBibliotecariPrestecs();
            }
        });
    }
}


