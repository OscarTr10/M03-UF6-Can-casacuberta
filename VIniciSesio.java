import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class VIniciSesio extends JFrame {
    private JTextField emailField;
    private JPasswordField passwordField;
    private JLabel messageLabel;

    public VIniciSesio() {
        setTitle("Inicia Sessió");
        setSize(500, 200);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        
        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(4, 2));
        
        JLabel etiquetaEmail = new JLabel("Correu electrònic:");
        emailField = new JTextField();
        
        JLabel etiquetaContrasenya = new JLabel("Contrasenya:");
        passwordField = new JPasswordField();
        
        JButton botoIniciaSessio = new JButton("Inicia Sessió");
        botoIniciaSessio.addActionListener(new GestorBotoIniciaSessio());
        
        messageLabel = new JLabel("", SwingConstants.CENTER);
        messageLabel.setForeground(Color.RED);
        
        panel.add(etiquetaEmail);
        panel.add(emailField);
        panel.add(etiquetaContrasenya);
        panel.add(passwordField);
        panel.add(new JLabel());
        panel.add(botoIniciaSessio);
        panel.add(messageLabel);
        
        add(panel);
        
    }

    private class GestorBotoIniciaSessio implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            String email = emailField.getText();
            String contrasenya = new String(passwordField.getPassword());
            // Trucar a la funció iniciarSessio de l'altre fitxer
            Usuari usuari = GestorFuncions.iniciarSessio(email, contrasenya);

            if (usuari != null) {
                messageLabel.setText("Sessió iniciada correctament!");
                if ("Bibliotecari".equals(usuari.getRol())) {
                    SwingUtilities.invokeLater(() -> new VBibliotecari().setVisible(true));
                } else if ("Lector".equals(usuari.getRol())) {
                    SwingUtilities.invokeLater(() -> new VLector().setVisible(true));
                }
            } else {
                messageLabel.setText("Credencials incorrectes.");
            }
        }
    }
}

