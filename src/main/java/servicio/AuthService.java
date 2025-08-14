package servicio;

import dao.AdministradorDAO;
import dao.UsuarioFinalDAO;
import daoimpl.AdministradorDAOImpl;
import daoimpl.UsuarioFinalDAOImpl;
import modelo.Administrador;
import modelo.UsuarioFinal;

public class AuthService {
    private final AdministradorDAO adminDAO = new AdministradorDAOImpl();
    private final UsuarioFinalDAO usuarioDAO = new UsuarioFinalDAOImpl();

    public boolean hayAdministrador() throws Exception {
        return adminDAO.existeAdministrador();
    }

    public void registrarAdminSiNoExiste() throws Exception {
        if (!hayAdministrador()) {
            Administrador a = new Administrador(null, "admin@example.com", "admin", "Admin123!");
            adminDAO.crear(a);
        }
    }

    public Administrador loginAdmin(String user, String pass) throws Exception {
        Administrador a = adminDAO.buscarPorUsuario(user);
        return (a != null && a.getContrasena().equals(pass)) ? a : null;
    }

    public UsuarioFinal loginUsuario(String user, String pass) throws Exception {
        UsuarioFinal u = usuarioDAO.buscarPorUsuario(user);
        return (u != null && u.getContrasena().equals(pass)) ? u : null;
    }

    public void registrarUsuario(UsuarioFinal u) throws Exception {
        u.setSaldo(u.getSaldo() + 2.99); // bono
        usuarioDAO.crear(u);
    }
}
