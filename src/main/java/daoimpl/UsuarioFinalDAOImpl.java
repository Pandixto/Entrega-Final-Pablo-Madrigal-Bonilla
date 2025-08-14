package daoimpl;

import dao.UsuarioFinalDAO;
import modelo.UsuarioFinal;
import util.ConexionBD;
import util.SqlLoader;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class UsuarioFinalDAOImpl implements UsuarioFinalDAO {
    private static final Map<String,String> Q = SqlLoader.loadNamedQueries("sql/usuario.sql");

    private UsuarioFinal map(ResultSet rs) throws SQLException {
        UsuarioFinal u = new UsuarioFinal();
        u.setId(rs.getInt("ID"));
        u.setNombreCompleto(rs.getString("NOMBRE_COMPLETO"));
        Date d = rs.getDate("FECHA_NAC");
        if (d != null) u.setFechaNac(d.toLocalDate());
        u.setNacionalidad(rs.getString("NACIONALIDAD"));
        u.setIdentificacion(rs.getString("IDENTIFICACION"));
        u.setAvatar(rs.getString("AVATAR"));
        u.setCorreo(rs.getString("CORREO"));
        u.setNombreUsuario(rs.getString("NOMBRE_USUARIO"));
        u.setContrasena(rs.getString("CONSTRASENA"));
        u.setSaldo(rs.getDouble("SALDO"));
        return u;
    }

    @Override
    public void crear(UsuarioFinal u) throws Exception {
        String sql = Q.get("usuario.insert");
        try (Connection con = ConexionBD.obtenerConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, u.getNombreCompleto());
            if (u.getFechaNac()!=null) ps.setDate(2, Date.valueOf(u.getFechaNac())); else ps.setNull(2, Types.DATE);
            ps.setString(3, u.getNacionalidad());
            ps.setString(4, u.getIdentificacion());
            ps.setString(5, u.getAvatar());
            ps.setString(6, u.getCorreo());
            ps.setString(7, u.getNombreUsuario());
            ps.setString(8, u.getContrasena());
            ps.setDouble(9, u.getSaldo());
            ps.executeUpdate();
        }
    }

    @Override
    public UsuarioFinal buscarPorUsuario(String username) throws Exception {
        String sql = Q.get("usuario.buscarPorUsuario");
        try (Connection con = ConexionBD.obtenerConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, username);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) return map(rs);
                return null;
            }
        }
    }

    @Override
    public void actualizarSaldo(int userId, double nuevoSaldo) throws Exception {
        String sql = Q.get("usuario.actualizarSaldo");
        try (Connection con = ConexionBD.obtenerConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setDouble(1, nuevoSaldo);
            ps.setInt(2, userId);
            ps.executeUpdate();
        }
    }

    @Override
    public List<UsuarioFinal> listar() throws Exception {
        String sql = Q.get("usuario.findAll");
        try (Connection con = ConexionBD.obtenerConexion();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            List<UsuarioFinal> res = new ArrayList<>();
            while (rs.next()) res.add(map(rs));
            return res;
        }
    }
}
