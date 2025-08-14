package dao;

import java.util.List;
import modelo.Compra;
import modelo.Cancion;

public interface CompraDAO {
    void crear(Compra c) throws Exception;
    boolean yaComprada(int usuarioId, int cancionId) throws Exception;
    List<Cancion> cancionesCompradas(int usuarioId) throws Exception;
}
