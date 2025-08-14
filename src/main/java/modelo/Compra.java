package modelo;

import java.time.LocalDateTime;

public class Compra {
    private Integer id;
    private Integer usuarioId;
    private Integer cancionId;
    private LocalDateTime fechaCompra;

    public Compra() {}

    public Compra(Integer id, Integer usuarioId, Integer cancionId, LocalDateTime fechaCompra) {
        this.id = id;
        this.usuarioId = usuarioId;
        this.cancionId = cancionId;
        this.fechaCompra = fechaCompra;
    }

    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }
    public Integer getUsuarioId() { return usuarioId; }
    public void setUsuarioId(Integer usuarioId) { this.usuarioId = usuarioId; }
    public Integer getCancionId() { return cancionId; }
    public void setCancionId(Integer cancionId) { this.cancionId = cancionId; }
    public LocalDateTime getFechaCompra() { return fechaCompra; }
    public void setFechaCompra(LocalDateTime fechaCompra) { this.fechaCompra = fechaCompra; }
}
