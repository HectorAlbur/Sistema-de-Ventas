package view;

import controller.ProductoController;
import model.Producto;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class ProductoView extends JFrame {

    private ProductoController controller = new ProductoController();

    private JTextField txtNombre, txtDescripcion, txtPrecio, txtStock;
    private DefaultTableModel modeloTabla;

    public ProductoView() {
        setTitle("Gestión de Productos");
        setSize(700, 500);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout(10, 10));

        JPanel panelForm = new JPanel(new GridBagLayout());
        panelForm.setBorder(BorderFactory.createTitledBorder("Registrar Nuevo Producto"));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        txtNombre      = new JTextField(15);
        txtDescripcion = new JTextField(15);
        txtPrecio      = new JTextField(10);
        txtStock       = new JTextField(10);

        gbc.gridx = 0; gbc.gridy = 0; panelForm.add(new JLabel("Nombre: *"), gbc);
        gbc.gridx = 1;                panelForm.add(txtNombre, gbc);
        gbc.gridx = 2;                panelForm.add(new JLabel("Descripción:"), gbc);
        gbc.gridx = 3;                panelForm.add(txtDescripcion, gbc);

        gbc.gridx = 0; gbc.gridy = 1; panelForm.add(new JLabel("Precio: *"), gbc);
        gbc.gridx = 1;                panelForm.add(txtPrecio, gbc);
        gbc.gridx = 2;                panelForm.add(new JLabel("Stock: *"), gbc);
        gbc.gridx = 3;                panelForm.add(txtStock, gbc);

        JButton btnGuardar = new JButton("Guardar Producto");
        btnGuardar.setBackground(new Color(40, 167, 69));
        btnGuardar.setForeground(Color.WHITE);
        btnGuardar.setFont(new Font("Arial", Font.BOLD, 13));
        gbc.gridx = 1; gbc.gridy = 2; gbc.gridwidth = 2;
        panelForm.add(btnGuardar, gbc);

        add(panelForm, BorderLayout.NORTH);

        String[] columnas = {"ID", "Nombre", "Descripción", "Precio", "Stock"};
        modeloTabla = new DefaultTableModel(columnas, 0) {
            @Override
            public boolean isCellEditable(int row, int col) { return false; }
        };

        JTable tabla = new JTable(modeloTabla);
        tabla.setRowHeight(25);
        tabla.getColumnModel().getColumn(0).setMaxWidth(40);
        tabla.getColumnModel().getColumn(3).setMaxWidth(80);
        tabla.getColumnModel().getColumn(4).setMaxWidth(60);

        add(new JScrollPane(tabla), BorderLayout.CENTER);

        btnGuardar.addActionListener(e -> guardarProducto());

        cargarTabla();
    }

    private void guardarProducto() {
    String resultado = controller.registrar(
        txtNombre.getText(),
        txtDescripcion.getText(),
        txtPrecio.getText(),
        txtStock.getText()
    );

    System.out.println("Resultado del Controller: '" + resultado + "'"); // línea temporal
    System.out.println("Longitud: " + resultado.length()); // línea temporal

    if (resultado.trim().equals("OK")) {
            JOptionPane.showMessageDialog(this,
                "Producto registrado correctamente.",
                "Éxito", JOptionPane.INFORMATION_MESSAGE);
            limpiarFormulario();
            cargarTabla();
        } else {
            JOptionPane.showMessageDialog(this,
                resultado,
                "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void cargarTabla() {
        modeloTabla.setRowCount(0); // Limpiar filas anteriores
        List<Producto> productos = controller.obtenerTodos();
        for (Producto p : productos) {
            modeloTabla.addRow(new Object[]{
                p.getId(),
                p.getNombre(),
                p.getDescripcion(),
                "$" + p.getPrecio(),
                p.getStock()
            });
        }
    }

    private void limpiarFormulario() {
        txtNombre.setText("");
        txtDescripcion.setText("");
        txtPrecio.setText("");
        txtStock.setText("");
        txtNombre.requestFocus();
    }
}