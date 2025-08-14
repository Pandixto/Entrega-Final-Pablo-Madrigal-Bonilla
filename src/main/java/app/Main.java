package app;

import servicio.AuthService;
import ui.LoginDialog;

public class Main {
    public static void main(String[] args) {
        javax.swing.SwingUtilities.invokeLater(() -> {
            try {
                new AuthService().registrarAdminSiNoExiste();
            } catch (Exception e) {
                e.printStackTrace();
                javax.swing.JOptionPane.showMessageDialog(null, "Error inicial: " + e.getMessage());
            }
            new LoginDialog().setVisible(true);
        });
    }
}
