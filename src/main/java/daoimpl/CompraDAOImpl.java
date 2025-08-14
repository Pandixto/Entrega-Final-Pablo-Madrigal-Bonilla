package daoimpl;

import dao.CompraDAO;
import modelo.Cancion;
import modelo.Compra;
import util.ConexionBD;
import util.SqlLoader;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class CompraDAOImpl implements CompraDAO {
    private static final Map<String,String> Q = SqlLoader.loadNamedQueries("sql/compra.sql");

    @Override
    public void crear(Compra c) throws Exception {
        String sql = Q.get("compra.insert");
        try (Connection con = ConexionBD.obtenerConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, c.getUsuarioId());
            ps.setInt(2, c.getCancionId());
            ps.executeUpdate();
        }
    }

    @Override
    public boolean yaComprada(int usuarioId, int cancionId) throws Exception {
        String sql = Q.get("compra.yaComprada");
        try (Connection con = ConexionBD.obtenerConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, usuarioId);
            ps.setInt(2, cancionId);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) return rs.getInt(1) == 1;
                return false;
            }
        }
    }

    @Override
    public List<Cancion> cancionesCompradas(int usuarioId) throws Exception {
        String sql = Q.get("compra.cancionesCompradas");
        try (Connection con = ConexionBD.obtenerConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, usuarioId);
            try (ResultSet rs = ps.executeQuery()) {
                List<Cancion> res = new ArrayList<>();
                while (rs.next()) {
                    Cancion c = new Cancion();
                    c.setId(rs.getInt("ID"));
                    c.setNombre(rs.getString("NOMBRE"));
                    c.setArtista(rs.getString("ARTISTA"));
                    c.setAlbum(rs.getString("ALBUM"));
                    c.setPrecio(rs.getDouble("PRECIO"));
                    res.add(c);
                }
                return res;
            }
        }
    }
}
