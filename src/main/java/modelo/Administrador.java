package modelo;

public class Administrador {
    private Integer id;
    private String correo;
    private String nombreUsuario;
    private String contrasena;

    public Administrador() {}

    public Administrador(Integer id, String correo, String nombreUsuario, String contrasena) {
        this.id = id;
        this.correo = correo;
        this.nombreUsuario = nombreUsuario;
        this.contrasena = contrasena;
    }

    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }
    public String getCorreo() { return correo; }
    public void setCorreo(String correo) { this.correo = correo; }
    public String getNombreUsuario() { return nombreUsuario; }
    public void setNombreUsuario(String nombreUsuario) { this.nombreUsuario = nombreUsuario; }
    public String getContrasena() { return contrasena; }
    public void setContrasena(String contrasena) { this.contrasena = contrasena; }
}
