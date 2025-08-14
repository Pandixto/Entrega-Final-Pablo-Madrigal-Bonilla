package daoimpl;

import dao.CancionDAO;
import modelo.Cancion;
import util.ConexionBD;
import util.SqlLoader;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class CancionDAOImpl implements CancionDAO {

    private static final Map<String, String> Q = SqlLoader.loadNamedQueries("sql/cancion.sql");

    private Cancion map(ResultSet rs) throws SQLException {
        Cancion c = new Cancion();
        c.setId(rs.getInt("ID"));
        c.setNombre(rs.getString("NOMBRE"));
        c.setArtista(rs.getString("ARTISTA"));
        c.setAlbum(rs.getString("ALBUM")); // puede venir null
        c.setPrecio(rs.getDouble("PRECIO")); // si PRECIO puede ser null, considera usar getBigDecimal
        return c;
    }

    @Override
    public void crear(Cancion c) throws Exception {
        String sql = Q.get("cancion.insert"); // VALUES (SEQ_CANCION.NEXTVAL, ?, ?, ?, ?)
        try (Connection con = ConexionBD.obtenerConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, c.getNombre());
            ps.setString(2, c.getArtista());

            if (c.getAlbum() == null || c.getAlbum().isBlank()) {
                ps.setNull(3, Types.VARCHAR);
            } else {
                ps.setString(3, c.getAlbum());
            }

            ps.setDouble(4, c.getPrecio()); // <-- índice correcto
            ps.executeUpdate();
        }
    }

    @Override
    public boolean actualizar(Cancion c) throws Exception {
        String sql = Q.get("cancion.update"); // SET NOMBRE=?, ARTISTA=?, ALBUM=?, PRECIO=? WHERE ID=?
        try (Connection con = ConexionBD.obtenerConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, c.getNombre());
            ps.setString(2, c.getArtista());

            if (c.getAlbum() == null || c.getAlbum().isBlank()) {
                ps.setNull(3, Types.VARCHAR);
            } else {
                ps.setString(3, c.getAlbum());
            }

            ps.setDouble(4, c.getPrecio()); // <-- índice correcto
            ps.setInt(5, c.getId());        // <-- índice correcto
            return ps.executeUpdate() == 1;
        }
    }

    @Override
    public boolean eliminar(int id) throws Exception {
        String sql = Q.get("cancion.delete"); // WHERE ID = ?
        try (Connection con = ConexionBD.obtenerConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, id);
            return ps.executeUpdate() == 1;
        }
    }

    @Override
    public List<Cancion> buscarPorNombre(String nombre) throws Exception {
        String sql = Q.get("cancion.buscarPorNombre"); // ... WHERE UPPER(NOMBRE) LIKE UPPER(?)
        try (Connection con = ConexionBD.obtenerConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, "%" + (nombre == null ? "" : nombre.trim()) + "%");
            try (ResultSet rs = ps.executeQuery()) {
                List<Cancion> list = new ArrayList<>();
                while (rs.next()) list.add(map(rs));
                return list;
            }
        }
    }

    @Override
    public List<Cancion> buscarPorArtista(String artista) throws Exception {
        String sql = Q.get("cancion.buscarPorArtista"); // ... WHERE UPPER(ARTISTA) LIKE UPPER(?)
        try (Connection con = ConexionBD.obtenerConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, "%" + (artista == null ? "" : artista.trim()) + "%");
            try (ResultSet rs = ps.executeQuery()) {
                List<Cancion> list = new ArrayList<>();
                while (rs.next()) list.add(map(rs));
                return list;
            }
        }
    }

    @Override
    public List<Cancion> listar() throws Exception {
        String sql = Q.get("cancion.findAll"); // sin parámetros
        try (Connection con = ConexionBD.obtenerConexion();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            List<Cancion> list = new ArrayList<>();
            while (rs.next()) list.add(map(rs));
            return list;
        }
    }

    @Override
    public Cancion buscarPorId(int id) throws Exception {
        String sql = Q.get("cancion.findById"); // WHERE ID = ?
        try (Connection con = ConexionBD.obtenerConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) return map(rs);
                return null;
            }
        }
    }
}
