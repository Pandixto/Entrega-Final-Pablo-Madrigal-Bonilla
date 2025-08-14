package modelo;

import java.time.LocalDateTime;

public class Recarga {
    private Integer id;
    private Integer usuarioId;
    private double monto;
    private LocalDateTime fechaRec;

    public Recarga() {}

    public Recarga(Integer id, Integer usuarioId, double monto, LocalDateTime fechaRec) {
        this.id = id;
        this.usuarioId = usuarioId;
        this.monto = monto;
        this.fechaRec = fechaRec;
    }

    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }
    public Integer getUsuarioId() { return usuarioId; }
    public void setUsuarioId(Integer usuarioId) { this.usuarioId = usuarioId; }
    public double getMonto() { return monto; }
    public void setMonto(double monto) { this.monto = monto; }
    public LocalDateTime getFechaRec() { return fechaRec; }
    public void setFechaRec(LocalDateTime fechaRec) { this.fechaRec = fechaRec; }
}
