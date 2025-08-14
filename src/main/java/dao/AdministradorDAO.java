package dao;

import modelo.Administrador;

public interface AdministradorDAO {
    Administrador buscarPorUsuario(String nombreUsuario) throws Exception;
    void crear(Administrador admin) throws Exception;
    boolean existeAdministrador() throws Exception;
}
