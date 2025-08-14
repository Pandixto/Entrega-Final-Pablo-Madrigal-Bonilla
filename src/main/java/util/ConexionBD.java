package util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConexionBD {
    private static final String URL = "jdbc:oracle:thin:@localhost:1521:xe";
    private static final String USUARIO = "SYSTEM";
    private static final String CONTRASENA = "0502";

    public static Connection obtenerConexion() throws SQLException {
        try {
            Class.forName("oracle.jdbc.OracleDriver");
        } catch (ClassNotFoundException e) {
            throw new SQLException("No se encontró el driver de Oracle JDBC en el classpath.", e);
        }
        return DriverManager.getConnection(URL, USUARIO, CONTRASENA);
    }
}
