package modelo;

public class Cancion {
    private Integer id;
    private String nombre;
    private String artista;
    private String album;
    private double precio;

    public Cancion() {}

    public Cancion(Integer id, String nombre, String artista, String album, double precio) {
        this.id = id;
        this.nombre = nombre;
        this.artista = artista;
        this.album = album;
        this.precio = precio;
    }

    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }
    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }
    public String getArtista() { return artista; }
    public void setArtista(String artista) { this.artista = artista; }
    public String getAlbum() { return album; }
    public void setAlbum(String album) { this.album = album; }
    public double getPrecio() { return precio; }
    public void setPrecio(double precio) { this.precio = precio; }
}
