import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class VBibliotecariUsuaris extends JFrame {
    private GestorFuncions gestorUsuaris;
    public VBibliotecariUsuaris() {
        gestorUsuaris = new GestorFuncions();
        setTitle("Gestió d'Usuaris");
        setSize(300, 150);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());
        JPanel panelBotons = new JPanel(new GridLayout(3, 1));
        JButton afegirBoto = new JButton("Afegir Usuari");
        JButton modificarBoto = new JButton("Modificar Usuari");
        JButton eliminarBoto = new JButton("Eliminar Usuari");
        panelBotons.add(afegirBoto);
        panelBotons.add(modificarBoto);
        panelBotons.add(eliminarBoto);
        add(panelBotons, BorderLayout.CENTER);
        afegirBoto.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                afegirUsuari();
            }
        });

        modificarBoto.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                modificarUsuari();
            }
        });

        eliminarBoto.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                eliminarUsuari();
            }
        });
    }

    private void afegirUsuari() {
        JTextField nom = new JTextField(20);
        JTextField cognoms = new JTextField(20);
        JTextField email = new JTextField(20);
        JTextField telefon = new JTextField(20);
        JTextField rol = new JTextField(20);
        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(5, 2));
        panel.add(new JLabel("Nom:"));
        panel.add(nom);
        panel.add(new JLabel("Cognoms:"));
        panel.add(cognoms);
        panel.add(new JLabel("Email:"));
        panel.add(email);
        panel.add(new JLabel("Telèfon:"));
        panel.add(telefon);
        panel.add(new JLabel("Rol:"));
        panel.add(rol);

        int result = JOptionPane.showConfirmDialog(null, panel, "Afegir Usuari", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
        if (result == JOptionPane.OK_OPTION) {
            String emailText = email.getText();
            String telefonText = telefon.getText();
            String rolText = rol.getText();
            if (!gestorUsuaris.validarEmail(emailText)) {
                JOptionPane.showMessageDialog(null, "Email no vàlid. Si us plau, introdueix un email amb '@'.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            if (!gestorUsuaris.validarTelefon(telefonText)) {
                JOptionPane.showMessageDialog(null, "Telèfon no vàlid. Si us plau, introdueix un número de telèfon de 9 dígits.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            if (!gestorUsuaris.validarRol(rolText)) {
                JOptionPane.showMessageDialog(null, "Rol no vàlid. Si us plau, introdueix 'Lector' o 'Bibliotecari'.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            Usuari usuari = new Usuari(0, nom.getText(), cognoms.getText(), email.getText(), telefon.getText(), rol.getText(), null);
            if (gestorUsuaris.afegirUsuari(usuari)) {
                JOptionPane.showMessageDialog(this, "Usuari afegit correctament.");
            } else {
                JOptionPane.showMessageDialog(this, "Error en afegir l'usuari.");
            }
        }
    }

    private void modificarUsuari() {
        JTextField id = new JTextField(20);
        JTextField nom = new JTextField(20);
        JTextField cognoms = new JTextField(20);
        JTextField email = new JTextField(20);
        JTextField telefon = new JTextField(20);
        JTextField rol = new JTextField(20);
        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(6, 2));
        panel.add(new JLabel("ID de l'Usuari:"));
        panel.add(id);
        panel.add(new JLabel("Nom:"));
        panel.add(nom);
        panel.add(new JLabel("Cognoms:"));
        panel.add(cognoms);
        panel.add(new JLabel("Email:"));
        panel.add(email);
        panel.add(new JLabel("Telèfon:"));
        panel.add(telefon);
        panel.add(new JLabel("Rol:"));
        panel.add(rol);

        int result = JOptionPane.showConfirmDialog(null, panel, "Modificar Usuari", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
        if (result == JOptionPane.OK_OPTION) {
            String emailText = email.getText();
            String telefonText = telefon.getText();
            String rolText = rol.getText();

            if (!gestorUsuaris.validarEmail(emailText)) {
                JOptionPane.showMessageDialog(null, "Email no vàlid. Si us plau, introdueix un email amb '@'.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            if (!gestorUsuaris.validarTelefon(telefonText)) {
                JOptionPane.showMessageDialog(null, "Telèfon no vàlid. Si us plau, introdueix un número de telèfon de 9 dígits.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            if (!gestorUsuaris.validarRol(rolText)) {
                JOptionPane.showMessageDialog(null, "Rol no vàlid. Si us plau, introdueix 'Lector' o 'Bibliotecari'.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            Usuari usuari = new Usuari(Integer.parseInt(id.getText()), nom.getText(), cognoms.getText(), email.getText(), telefon.getText(), rol.getText(), null);
            if (gestorUsuaris.modificarUsuari(usuari)) {
                JOptionPane.showMessageDialog(this, "Usuari modificat correctament.");
            } else {
                JOptionPane.showMessageDialog(this, "Error en modificar l'usuari.");
            }
        }
    }

    private void eliminarUsuari() {
        JTextField id = new JTextField(20);
        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(1, 2));
        panel.add(new JLabel("ID de l'Usuari a eliminar:"));
        panel.add(id);
        int result = JOptionPane.showConfirmDialog(null, panel, "Eliminar Usuari", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);    //Enesña lo que va a mostrar si Acepta=0 si cancela=2
        if (result == JOptionPane.OK_OPTION) {
            int idUsuari = Integer.parseInt(id.getText());
            if (gestorUsuaris.eliminarUsuari(idUsuari)) {
                JOptionPane.showMessageDialog(this, "Usuari eliminat correctament.");
            } else {
                JOptionPane.showMessageDialog(this, "Error en eliminar l'usuari. Possiblement tingui un préstec actiu.\nElimina el préstec per poder eliminar l'usuari");
            }
        }
    }

    public static void obrirVBibliotecariUsuaris() {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new VBibliotecariUsuaris().setVisible(true);
            }
        });
    }
}

