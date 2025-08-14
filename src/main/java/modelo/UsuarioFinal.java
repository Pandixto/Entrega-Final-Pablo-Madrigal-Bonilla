package modelo;

import java.time.LocalDate;

public class UsuarioFinal {
    private Integer id;
    private String nombreCompleto;
    private LocalDate fechaNac;
    private String nacionalidad;
    private String identificacion;
    private String avatar; // ruta opcional
    private String correo;
    private String nombreUsuario;
    private String contrasena;
    private double saldo;

    public UsuarioFinal() {}

    public UsuarioFinal(Integer id, String nombreCompleto, LocalDate fechaNac, String nacionalidad,
                        String identificacion, String avatar, String correo,
                        String nombreUsuario, String contrasena, double saldo) {
        this.id = id;
        this.nombreCompleto = nombreCompleto;
        this.fechaNac = fechaNac;
        this.nacionalidad = nacionalidad;
        this.identificacion = identificacion;
        this.avatar = avatar;
        this.correo = correo;
        this.nombreUsuario = nombreUsuario;
        this.contrasena = contrasena;
        this.saldo = saldo;
    }

    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }
    public String getNombreCompleto() { return nombreCompleto; }
    public void setNombreCompleto(String nombreCompleto) { this.nombreCompleto = nombreCompleto; }
    public LocalDate getFechaNac() { return fechaNac; }
    public void setFechaNac(LocalDate fechaNac) { this.fechaNac = fechaNac; }
    public String getNacionalidad() { return nacionalidad; }
    public void setNacionalidad(String nacionalidad) { this.nacionalidad = nacionalidad; }
    public String getIdentificacion() { return identificacion; }
    public void setIdentificacion(String identificacion) { this.identificacion = identificacion; }
    public String getAvatar() { return avatar; }
    public void setAvatar(String avatar) { this.avatar = avatar; }
    public String getCorreo() { return correo; }
    public void setCorreo(String correo) { this.correo = correo; }
    public String getNombreUsuario() { return nombreUsuario; }
    public void setNombreUsuario(String nombreUsuario) { this.nombreUsuario = nombreUsuario; }
    public String getContrasena() { return contrasena; }
    public void setContrasena(String contrasena) { this.contrasena = contrasena; }
    public double getSaldo() { return saldo; }
    public void setSaldo(double saldo) { this.saldo = saldo; }
}
