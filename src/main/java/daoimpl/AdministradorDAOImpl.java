package daoimpl;

import dao.AdministradorDAO;
import modelo.Administrador;
import util.ConexionBD;
import util.SqlLoader;

import java.sql.*;
import java.util.Map;

public class AdministradorDAOImpl implements AdministradorDAO {
    private static final Map<String,String> Q = SqlLoader.loadNamedQueries("sql/administrador.sql");

    @Override
    public Administrador buscarPorUsuario(String nombreUsuario) throws Exception {
        String sql = Q.get("admin.buscarPorUsuario");
        try (Connection con = ConexionBD.obtenerConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, nombreUsuario);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    Administrador a = new Administrador();
                    a.setId(rs.getInt("ID"));
                    a.setCorreo(rs.getString("CORREO"));
                    a.setNombreUsuario(rs.getString("NOMBRE_USUARIO"));
                    a.setContrasena(rs.getString("CONSTRASENA"));
                    return a;
                }
                return null;
            }
        }
    }

    @Override
    public void crear(Administrador admin) throws Exception {
        String sql = Q.get("admin.insert");
        try (Connection con = ConexionBD.obtenerConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, admin.getCorreo());
            ps.setString(2, admin.getNombreUsuario());
            ps.setString(3, admin.getContrasena());
            ps.executeUpdate();
        }
    }

    @Override
    public boolean existeAdministrador() throws Exception {
        String sql = Q.get("admin.exists");
        try (Connection con = ConexionBD.obtenerConexion();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            if (rs.next()) return rs.getInt(1) == 1;
            return false;
        }
    }
}
