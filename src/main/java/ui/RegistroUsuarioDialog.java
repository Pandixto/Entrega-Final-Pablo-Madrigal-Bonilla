package ui;

import modelo.UsuarioFinal;
import servicio.AuthService;
import util.Validadores;

import javax.swing.*;
import java.awt.*;
import java.time.LocalDate;

public class RegistroUsuarioDialog extends JDialog {
    private final AuthService auth = new AuthService();

    public RegistroUsuarioDialog(Window owner) {
        super(owner, "Registro de Usuario", ModalityType.APPLICATION_MODAL);
        setSize(480, 360);
        setLocationRelativeTo(owner);
        build();
    }

    private void build() {
        JTextField nombre = new JTextField();
        JTextField fecha = new JTextField(); // formato: YYYY-MM-DD
        JTextField nacionalidad = new JTextField();
        JTextField identificacion = new JTextField();
        JTextField avatar = new JTextField();
        JTextField correo = new JTextField();
        JTextField usuario = new JTextField();
        JPasswordField pwd = new JPasswordField();
        JPasswordField pwd2 = new JPasswordField();

        JPanel form = new JPanel(new GridLayout(9,2,6,6));
        form.add(new JLabel("Nombre completo:")); form.add(nombre);
        form.add(new JLabel("Fecha nacimiento (YYYY-MM-DD):")); form.add(fecha);
        form.add(new JLabel("Nacionalidad:")); form.add(nacionalidad);
        form.add(new JLabel("Identificación:")); form.add(identificacion);
        form.add(new JLabel("Avatar (ruta opc.):")); form.add(avatar);
        form.add(new JLabel("Correo:")); form.add(correo);
        form.add(new JLabel("Usuario:")); form.add(usuario);
        form.add(new JLabel("Contraseña:")); form.add(pwd);
        form.add(new JLabel("Repetir contraseña:")); form.add(pwd2);

        JButton guardar = new JButton("Registrar");
        JButton cerrar = new JButton("Cerrar");
        JPanel btns = new JPanel(); btns.add(guardar); btns.add(cerrar);

        setLayout(new BorderLayout());
        add(form, BorderLayout.CENTER);
        add(btns, BorderLayout.SOUTH);

        guardar.addActionListener(e -> {
            try {
                if (!new String(pwd.getPassword()).equals(new String(pwd2.getPassword()))) {
                    msg("Las contraseñas no coinciden."); return;
                }
                if (!Validadores.contrasenaValida(new String(pwd.getPassword()))) {
                    msg("Contraseña 8-12 con mayúscula, minúscula, número y símbolo."); return;
                }
                LocalDate fn = LocalDate.parse(fecha.getText().trim());
                if (!Validadores.esMayorDeEdad(fn)) { msg("Debes ser mayor de edad."); return; }

                UsuarioFinal u = new UsuarioFinal(null, nombre.getText(), fn,
                        nacionalidad.getText(), identificacion.getText(), avatar.getText(),
                        correo.getText(), usuario.getText(), new String(pwd.getPassword()), 0.0);
                auth.registrarUsuario(u);
                msg("Usuario registrado con bono $2.99.");
                dispose();
            } catch (Exception ex) { ex.printStackTrace(); msg("Error: " + ex.getMessage()); }
        });

        cerrar.addActionListener(e -> dispose());
    }

    private void msg(String s){ JOptionPane.showMessageDialog(this, s); }
}
