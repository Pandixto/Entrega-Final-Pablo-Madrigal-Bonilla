package daoimpl;

import dao.RecargaDAO;
import modelo.Recarga;
import util.ConexionBD;
import util.SqlLoader;

import java.sql.*;
import java.util.Map;

public class RecargaDAOImpl implements RecargaDAO {
    private static final Map<String,String> Q = SqlLoader.loadNamedQueries("sql/recarga.sql");

    @Override
    public void crear(Recarga r) throws Exception {
        String sql = Q.get("recarga.insert");
        try (Connection con = ConexionBD.obtenerConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, r.getUsuarioId());
            ps.setDouble(2, r.getMonto());
            ps.executeUpdate();
        }
    }
}
