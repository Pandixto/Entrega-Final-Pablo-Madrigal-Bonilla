package dao;

import modelo.UsuarioFinal;
import java.util.List;

public interface UsuarioFinalDAO {
    void crear(UsuarioFinal u) throws Exception;
    UsuarioFinal buscarPorUsuario(String username) throws Exception;
    void actualizarSaldo(int userId, double nuevoSaldo) throws Exception;
    List<UsuarioFinal> listar() throws Exception;
}
