package ui;

import modelo.Cancion;
import servicio.TiendaService;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class PanelAdminFrame extends JFrame {
    private final TiendaService tienda = new TiendaService();
    private final JTextField txtBuscar = new JTextField();
    private final JComboBox<String> cmbFiltro = new JComboBox<>(new String[]{"Nombre", "Artista"});
    private final JTable tabla = new JTable();
    private final DefaultTableModel modeloTabla = new DefaultTableModel(
            new Object[]{"ID","Nombre","Artista","Álbum","Precio"}, 0
    ) {
        @Override public boolean isCellEditable(int r, int c){ return false; }
    };

    public PanelAdminFrame() {
        setTitle("MusicStore - Admin");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(900, 520);
        setLocationRelativeTo(null);
        buildUI();
        cargarLista();
    }

    private void buildUI() {
        // Top bar (search)
        JPanel top = new JPanel(new BorderLayout(8,8));
        top.setBorder(BorderFactory.createEmptyBorder(8,8,8,8));
        JButton btnBuscar = new JButton("Buscar");
        JButton btnLimpiar = new JButton("Limpiar");
        JPanel right = new JPanel(new FlowLayout(FlowLayout.LEFT, 8, 0));
        right.add(new JLabel("Filtrar por:"));
        right.add(cmbFiltro);
        right.add(btnBuscar);
        right.add(btnLimpiar);
        top.add(new JLabel("Canciones"), BorderLayout.WEST);
        JPanel mid = new JPanel(new BorderLayout(8,8));
        mid.add(txtBuscar, BorderLayout.CENTER);
        top.add(mid, BorderLayout.CENTER);
        top.add(right, BorderLayout.EAST);

        // Center (table)
        tabla.setModel(modeloTabla);
        JScrollPane sp = new JScrollPane(tabla);

        // Bottom bar (CRUD)
        JPanel bottom = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 10));
        JButton btnAgregar = new JButton("Agregar");
        JButton btnEditar  = new JButton("Editar");
        JButton btnEliminar= new JButton("Eliminar");
        JButton btnRefrescar = new JButton("Refrescar");
        JButton btnSalir = new JButton("Salir");
        bottom.add(btnAgregar);
        bottom.add(btnEditar);
        bottom.add(btnEliminar);
        bottom.add(btnRefrescar);
        bottom.add(btnSalir);

        // Layout
        setLayout(new BorderLayout());
        add(top, BorderLayout.NORTH);
        add(sp, BorderLayout.CENTER);
        add(bottom, BorderLayout.SOUTH);

        // Actions
        btnBuscar.addActionListener(e -> buscar());
        btnLimpiar.addActionListener(e -> { txtBuscar.setText(""); cargarLista(); });
        btnRefrescar.addActionListener(e -> cargarLista());
        btnSalir.addActionListener(e -> { dispose(); new LoginDialog().setVisible(true); });
        btnAgregar.addActionListener(e -> accionAgregar());
        btnEditar.addActionListener(e -> accionEditar());
        btnEliminar.addActionListener(e -> accionEliminar());
    }

    // ---- Data helpers ----
    private void cargarLista() {
        try {
            List<Cancion> data = tienda.listarCanciones();
            llenarTabla(data);
        } catch (Exception ex) {
            msg("Error listando: " + ex.getMessage());
        }
    }

    private void buscar() {
        String q = txtBuscar.getText().trim();
        if (q.isEmpty()) { cargarLista(); return; }
        String filtro = (String) cmbFiltro.getSelectedItem();
        try {
            List<Cancion> data;
            switch (filtro) {
                case "Artista" -> data = tienda.buscarPorArtista(q);
                default        -> data = tienda.buscarPorNombre(q);
            }
            llenarTabla(data);
        } catch (Exception ex) {
            msg("Error buscando: " + ex.getMessage());
        }
    }

    private void llenarTabla(List<Cancion> canciones) {
        modeloTabla.setRowCount(0);
        for (Cancion c : canciones) {
            modeloTabla.addRow(new Object[]{
                    c.getId(), c.getNombre(), c.getArtista(), c.getAlbum(), c.getPrecio()
            });
        }
        if (modeloTabla.getRowCount() > 0) tabla.setRowSelectionInterval(0,0);
    }

    private Integer idSeleccionado() {
        int row = tabla.getSelectedRow();
        if (row < 0) return null;
        Object v = modeloTabla.getValueAt(row, 0);
        return (v == null) ? null : Integer.parseInt(String.valueOf(v));
    }

    // ---- CRUD actions ----
    private void accionAgregar() {
        Cancion c = dialogCancion(null);
        if (c == null) return;
        try {
            tienda.crearCancion(c);
            msg("Canción creada.");
            cargarLista();
        } catch (Exception ex) {
            msg("Error al crear: " + ex.getMessage());
        }
    }

    private void accionEditar() {
        Integer id = idSeleccionado();
        if (id == null) { msg("Selecciona una canción."); return; }
        try {
            Cancion actual = tienda.buscarCancionPorId(id);
            if (actual == null) { msg("No encontrada."); return; }
            Cancion editada = dialogCancion(actual);
            if (editada == null) return;
            editada.setId(id);
            boolean ok = tienda.actualizarCancion(editada);
            if (ok) { msg("Canción actualizada."); cargarLista(); }
            else msg("No se actualizó (verifica datos).");
        } catch (Exception ex) {
            msg("Error al actualizar: " + ex.getMessage());
        }
    }

    private void accionEliminar() {
        Integer id = idSeleccionado();
        if (id == null) { msg("Selecciona una canción."); return; }
        if (JOptionPane.showConfirmDialog(this, "¿Eliminar la canción seleccionada?", "Confirmar",
                JOptionPane.YES_NO_OPTION) != JOptionPane.YES_OPTION) return;
        try {
            boolean ok = tienda.eliminarCancion(id);
            if (ok) { msg("Canción eliminada."); cargarLista(); }
            else msg("No se eliminó (puede que no exista).");
        } catch (Exception ex) {
            msg("Error al eliminar: " + ex.getMessage());
        }
    }

    // ---- Dialogo de alta/edición ----
    private Cancion dialogCancion(Cancion base) {
        JTextField nombre = new JTextField(base != null ? safe(base.getNombre()) : "");
        JTextField artista = new JTextField(base != null ? safe(base.getArtista()) : "");
        JTextField album = new JTextField(base != null ? safe(base.getAlbum()) : "");
        JTextField precio = new JTextField(base != null ? String.valueOf(base.getPrecio()) : "");

        JPanel form = new JPanel(new GridLayout(0,2,6,6));
        form.add(new JLabel("Nombre:")); form.add(nombre);
        form.add(new JLabel("Artista:")); form.add(artista);
        form.add(new JLabel("Álbum:")); form.add(album);form.add(new JLabel("Precio:")); form.add(precio);

        int r = JOptionPane.showConfirmDialog(this, form, (base==null?"Agregar":"Editar") + " canción",
                JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
        if (r != JOptionPane.OK_OPTION) return null;

        // Validaciones mínimas
        if (nombre.getText().trim().isEmpty() || artista.getText().trim().isEmpty()) {
            msg("Nombre y Artista son obligatorios."); return null;
        }

        Cancion c = new Cancion();
        c.setNombre(nombre.getText().trim());
        c.setArtista(artista.getText().trim());
        c.setAlbum(album.getText().trim());try {
            double p = Double.parseDouble(precio.getText().trim());
            if (p < 0) { msg("El precio no puede ser negativo."); return null; }
            c.setPrecio(p);
        } catch (Exception ex) {
            msg("Precio inválido."); return null;
        }
        return c;
    }

    private static String safe(String s){ return s==null? "": s; }
    private void msg(String s){ JOptionPane.showMessageDialog(this, s); }
}
