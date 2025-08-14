package dao;

import modelo.Cancion;
import java.util.List;

public interface CancionDAO {
    void crear(Cancion c) throws Exception;
    List<Cancion> buscarPorNombre(String nombre) throws Exception;
    List<Cancion> buscarPorArtista(String artista) throws Exception;
    List<Cancion> listar() throws Exception;
    Cancion buscarPorId(int id) throws Exception;
    boolean actualizar(Cancion c) throws Exception;
    boolean eliminar(int id) throws Exception;
}
