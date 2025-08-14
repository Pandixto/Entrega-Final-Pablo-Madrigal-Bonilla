package servicio;

import dao.CancionDAO;
import dao.CompraDAO;
import dao.UsuarioFinalDAO;
import dao.RecargaDAO;
import daoimpl.CancionDAOImpl;
import daoimpl.CompraDAOImpl;
import daoimpl.UsuarioFinalDAOImpl;
import daoimpl.RecargaDAOImpl;
import modelo.Cancion;
import modelo.Compra;
import modelo.Recarga;
import modelo.UsuarioFinal;

import java.util.List;

public class TiendaService {
    private final CancionDAO cancionDAO = new CancionDAOImpl();
    private final CompraDAO compraDAO = new CompraDAOImpl();
    private final UsuarioFinalDAO usuarioDAO = new UsuarioFinalDAOImpl();
    private final RecargaDAO recargaDAO = new RecargaDAOImpl();

    public List<Cancion> listarCatalogo() throws Exception {
        return cancionDAO.listar();
    }

    public List<Cancion> buscarPorNombre(String q) throws Exception {
        return cancionDAO.buscarPorNombre(q);
    }

    public List<Cancion> buscarPorArtista(String q) throws Exception {
        return cancionDAO.buscarPorArtista(q);
    }

    public boolean puedeReproducirCompleta(int userId, int cancionId) throws Exception {
        return compraDAO.yaComprada(userId, cancionId);
    }

    public void comprarCancion(UsuarioFinal u, Cancion c) throws Exception {
        if (compraDAO.yaComprada(u.getId(), c.getId())) return;
        if (u.getSaldo() < c.getPrecio()) throw new IllegalStateException("Saldo insuficiente.");
        double nuevoSaldo = u.getSaldo() - c.getPrecio();
        usuarioDAO.actualizarSaldo(u.getId(), nuevoSaldo);
        u.setSaldo(nuevoSaldo);
        compraDAO.crear(new Compra(null, u.getId(), c.getId(), null));
    }

    public void recargarSaldo(UsuarioFinal u, double monto) throws Exception {
        if (monto <= 0) throw new IllegalArgumentException("Monto inválido.");
        double nuevo = u.getSaldo() + monto;
        usuarioDAO.actualizarSaldo(u.getId(), nuevo);
        u.setSaldo(nuevo);
        recargaDAO.crear(new Recarga(null, u.getId(), monto, null));
    }

    public List<Cancion> cancionesCompradas(int userId) throws Exception {
        return compraDAO.cancionesCompradas(userId);
    }

    public void altaCancion(Cancion c) throws Exception {
        cancionDAO.crear(c);
    }

    public Cancion buscarPorId(int id) throws Exception {
        return cancionDAO.buscarPorId(id);
    }

    // ---- CRUD Cancion (expuesto para UI) ----
    public void crearCancion(modelo.Cancion c) throws Exception {
        cancionDAO.crear(c);
    }
    public boolean actualizarCancion(modelo.Cancion c) throws Exception {
        return cancionDAO.actualizar(c);
    }
    public boolean eliminarCancion(int id) throws Exception {
        return cancionDAO.eliminar(id);
    }


    public java.util.List<Cancion> listarCanciones() throws Exception {
        return cancionDAO.listar();
    }
    public Cancion buscarCancionPorId(int id) throws Exception {
        return cancionDAO.buscarPorId(id);
    }
}