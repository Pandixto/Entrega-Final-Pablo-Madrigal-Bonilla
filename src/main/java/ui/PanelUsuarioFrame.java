package ui;

import modelo.Cancion;
import modelo.UsuarioFinal;
import servicio.TiendaService;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class PanelUsuarioFrame extends JFrame {
    private final TiendaService tienda = new TiendaService();
    private final UsuarioFinal usuario;

    private DefaultTableModel modeloCatalogo = new DefaultTableModel(new Object[]{"ID","Nombre","Artista","Álbum","Precio"}, 0) {
        public boolean isCellEditable(int r,int c){return false;}
    };
    private DefaultTableModel modeloCompras = new DefaultTableModel(new Object[]{"Nombre","Artista","Álbum"}, 0) {
        public boolean isCellEditable(int r,int c){return false;}
    };
    private JTable tablaCatalogo = new JTable(modeloCatalogo);
    private JTable tablaCompras = new JTable(modeloCompras);
    private JLabel lblSaldo;

    public PanelUsuarioFrame(UsuarioFinal u) {
        this.usuario = u;
        setTitle("MusicStore - Usuario");
        setSize(900, 600);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        build();
        cargarCatalogo();
        cargarCompras();
    }

    private void build() {
        lblSaldo = new JLabel("Hola, " + usuario.getNombreUsuario() + " | Saldo: $" + String.format("%.2f", usuario.getSaldo()));
        JButton recargar = new JButton("Recargar $10");
        JButton salir = new JButton("Cerrar sesión");
        JPanel top = new JPanel(new FlowLayout(FlowLayout.LEFT));
        top.add(lblSaldo); top.add(recargar); top.add(salir);

        JTextField q = new JTextField(22);
        JButton buscar = new JButton("Buscar");
        JButton listar = new JButton("Listar todo");
        JPanel search = new JPanel(new FlowLayout(FlowLayout.LEFT));
        search.add(new JLabel("Buscar:")); search.add(q); search.add(buscar); search.add(listar);

        JButton preview = new JButton("Vista previa 30s");
        JButton comprar = new JButton("Comprar");
        JPanel acciones = new JPanel(new FlowLayout(FlowLayout.LEFT));
        acciones.add(preview); acciones.add(comprar);

        JSplitPane split = new JSplitPane(JSplitPane.VERTICAL_SPLIT,
                new JScrollPane(tablaCatalogo),
                new JScrollPane(tablaCompras));
        split.setDividerLocation(330);

        setLayout(new BorderLayout());
        add(top, BorderLayout.NORTH);
        add(search, BorderLayout.WEST);
        add(split, BorderLayout.CENTER);
        add(acciones, BorderLayout.SOUTH);

        buscar.addActionListener(e -> {
            try {
                String s = q.getText().trim();
                List<Cancion> data = tienda.buscarPorNombre(s);
                if (data.isEmpty()) data = tienda.buscarPorArtista(s);
                fillCatalogo(data);
            } catch (Exception ex){ msg("Error: " + ex.getMessage()); }
        });
        listar.addActionListener(e -> cargarCatalogo());
        recargar.addActionListener(e -> {
            try {
                tienda.recargarSaldo(usuario, 10.0);
                lblSaldo.setText("Hola, " + usuario.getNombreUsuario() + " | Saldo: $" + String.format("%.2f", usuario.getSaldo()));
                msg("Saldo recargado +$10");
            } catch (Exception ex){ msg("Error: " + ex.getMessage()); }
        });
        preview.addActionListener(e -> {
            int row = tablaCatalogo.getSelectedRow();
            if (row < 0){ msg("Selecciona una canción."); return; }
            String nombre = (String) modeloCatalogo.getValueAt(row, 1);
            msg("Reproduciendo vista previa de 30s: " + nombre);
        });
        comprar.addActionListener(e -> {
            try {
                int row = tablaCatalogo.getSelectedRow();
                if (row < 0){ msg("Selecciona una canción."); return; }
                int id = (Integer) modeloCatalogo.getValueAt(row, 0);
                Cancion c = tienda.buscarPorId(id);
                tienda.comprarCancion(usuario, c);
                lblSaldo.setText("Hola, " + usuario.getNombreUsuario() + " | Saldo: $" + String.format("%.2f", usuario.getSaldo()));
                cargarCompras();
                msg("Compra realizada: " + c.getNombre());
            } catch (Exception ex){ msg("Error: " + ex.getMessage()); }
        });
        salir.addActionListener(e -> {
            dispose();
            new LoginDialog().setVisible(true);
        });
    }

    private void cargarCatalogo() {
        try {
            fillCatalogo(tienda.listarCatalogo());
        } catch (Exception ex){ msg("Error cargando catálogo: " + ex.getMessage()); }
    }

    private void cargarCompras() {
        try {
            List<Cancion> comps = tienda.cancionesCompradas(usuario.getId());
            modeloCompras.setRowCount(0);
            for (Cancion c : comps) {
                modeloCompras.addRow(new Object[]{c.getNombre(), c.getArtista(), c.getAlbum()});
            }
        } catch (Exception ex){ msg("Error cargando compras: " + ex.getMessage()); }
    }

    private void fillCatalogo(List<Cancion> data) {
        modeloCatalogo.setRowCount(0);
        for (Cancion c : data) {
            modeloCatalogo.addRow(new Object[]{c.getId(), c.getNombre(), c.getArtista(), c.getAlbum(), c.getPrecio()});
        }
    }

    private void msg(String s){ JOptionPane.showMessageDialog(this, s); }
}
