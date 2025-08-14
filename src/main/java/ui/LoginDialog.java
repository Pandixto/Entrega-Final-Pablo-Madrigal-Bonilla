package ui;

import modelo.Administrador;
import modelo.UsuarioFinal;
import servicio.AuthService;

import javax.swing.*;
import java.awt.*;

public class LoginDialog extends JDialog {
    private final AuthService auth = new AuthService();

    public LoginDialog() {
        setTitle("MusicStore - Login");
        setSize(400, 220);
        setLocationRelativeTo(null);
        setModal(true);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        build();
    }

    private void build() {
        JPanel panel = new JPanel(new GridLayout(5,1,6,6));
        JRadioButton rbUser = new JRadioButton("Usuario final", true);
        JRadioButton rbAdmin = new JRadioButton("Administrador");
        ButtonGroup bg = new ButtonGroup();
        bg.add(rbUser); bg.add(rbAdmin);

        JTextField user = new JTextField();
        JPasswordField pass = new JPasswordField();

        JPanel role = new JPanel(new FlowLayout(FlowLayout.LEFT));
        role.add(rbUser); role.add(rbAdmin);

        panel.add(role);
        panel.add(labeled("Usuario:", user));
        panel.add(labeled("Contraseña:", pass));

        JButton ingresar = new JButton("Ingresar");
        JButton registrarse = new JButton("Registrarse");
        JPanel btns = new JPanel();
        btns.add(ingresar); btns.add(registrarse);

        panel.add(btns);
        add(panel);

        ingresar.addActionListener(e -> {
            try {
                if (rbAdmin.isSelected()) {
                    Administrador a = auth.loginAdmin(user.getText(), new String(pass.getPassword()));
                    if (a != null) {
                        dispose();
                        new PanelAdminFrame().setVisible(true);
                    } else {
                        msg("Credenciales admin inválidas");
                    }
                } else {
                    UsuarioFinal u = auth.loginUsuario(user.getText(), new String(pass.getPassword()));
                    if (u != null) {
                        dispose();
                        new PanelUsuarioFrame(u).setVisible(true);
                    } else {
                        msg("Credenciales de usuario inválidas");
                    }
                }
            } catch (Exception ex) { ex.printStackTrace(); msg("Error: " + ex.getMessage()); }
        });

        registrarse.addActionListener(e -> new RegistroUsuarioDialog(this).setVisible(true));
    }

    private JPanel labeled(String lbl, JComponent c) {
        JPanel p = new JPanel(new BorderLayout());
        p.add(new JLabel(lbl), BorderLayout.WEST);
        p.add(c, BorderLayout.CENTER);
        return p;
    }

    private void msg(String s){ JOptionPane.showMessageDialog(this, s); }
}
