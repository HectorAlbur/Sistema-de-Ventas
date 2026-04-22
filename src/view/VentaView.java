package view;

import controller.VentaController;
import controller.ProductoController;
import model.Producto;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class VentaView extends JFrame {

    private VentaController ventaController = new VentaController();
    private ProductoController productoController = new ProductoController();

    private JComboBox<Producto> cmbProductos;
    private JTextField txtCantidad;
    private JLabel lblStock, lblSubtotal, lblIva, lblTotal;

    public VentaView() {
        setTitle("Realizar Venta");
        setSize(400, 420);
        setLocationRelativeTo(null);
        setResizable(false);
        setLayout(new BorderLayout());

        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBorder(BorderFactory.createEmptyBorder(15, 25, 15, 25));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(8, 5, 8, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        JLabel titulo = new JLabel("Nueva Venta", SwingConstants.CENTER);
        titulo.setFont(new Font("Arial", Font.BOLD, 17));
        gbc.gridx = 0; gbc.gridy = 0;
        gbc.gridwidth = 2;
        panel.add(titulo, gbc);

        gbc.gridwidth = 1;
        gbc.gridx = 0; gbc.gridy = 1; panel.add(new JLabel("Producto:"), gbc);
        cmbProductos = new JComboBox<>();
        gbc.gridx = 1; panel.add(cmbProductos, gbc);

        gbc.gridx = 0; gbc.gridy = 2; panel.add(new JLabel("Stock disponible:"), gbc);
        lblStock = new JLabel("—");
        lblStock.setForeground(new Color(0, 100, 180));
        lblStock.setFont(new Font("Arial", Font.BOLD, 13));
        gbc.gridx = 1; panel.add(lblStock, gbc);

        gbc.gridx = 0; gbc.gridy = 3; panel.add(new JLabel("Cantidad:"), gbc);
        txtCantidad = new JTextField();
        gbc.gridx = 1; panel.add(txtCantidad, gbc);

        gbc.gridwidth = 2;
        gbc.gridx = 0; gbc.gridy = 4;
        panel.add(new JSeparator(), gbc);

        gbc.gridwidth = 1;
        gbc.gridx = 0; gbc.gridy = 5; panel.add(new JLabel("Subtotal:"), gbc);
        lblSubtotal = new JLabel("$0.00");
        gbc.gridx = 1; panel.add(lblSubtotal, gbc);

        gbc.gridx = 0; gbc.gridy = 6; panel.add(new JLabel("IVA (16%):"), gbc);
        lblIva = new JLabel("$0.00");
        gbc.gridx = 1; panel.add(lblIva, gbc);

        gbc.gridx = 0; gbc.gridy = 7; panel.add(new JLabel("TOTAL:"), gbc);
        lblTotal = new JLabel("$0.00");
        lblTotal.setFont(new Font("Arial", Font.BOLD, 15));
        lblTotal.setForeground(new Color(0, 130, 0));
        gbc.gridx = 1; panel.add(lblTotal, gbc);

        gbc.gridwidth = 2;
        gbc.gridx = 0; gbc.gridy = 8;
        panel.add(new JSeparator(), gbc);

        JButton btnCalcular = new JButton("Calcular Total");
        JButton btnVender = new JButton("Confirmar Venta");
        btnVender.setBackground(new Color(0, 123, 255));
        btnVender.setForeground(Color.WHITE);
        btnVender.setFont(new Font("Arial", Font.BOLD, 13));

        gbc.gridwidth = 1;
        gbc.gridx = 0; gbc.gridy = 9; panel.add(btnCalcular, gbc);
        gbc.gridx = 1;                panel.add(btnVender, gbc);

        add(panel, BorderLayout.CENTER);

        cargarProductos();

        cmbProductos.addActionListener(e -> actualizarStock());
        btnCalcular.addActionListener(e -> calcularTotal());
        btnVender.addActionListener(e -> confirmarVenta());
    }

    private void cargarProductos() {
        List<Producto> productos = productoController.obtenerTodos();
        for (Producto p : productos) {
            cmbProductos.addItem(p);
        }
        actualizarStock();
    }

    private void actualizarStock() {
        Producto seleccionado = (Producto) cmbProductos.getSelectedItem();
        if (seleccionado != null) {
            lblStock.setText(String.valueOf(seleccionado.getStock()));
        }
    }

    private void calcularTotal() {
        Producto producto = (Producto) cmbProductos.getSelectedItem();
        if (producto == null) return;

        String cantStr = txtCantidad.getText().trim();
        if (cantStr.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Ingresa la cantidad primero.");
            return;
        }

        try {
            int cantidad = Integer.parseInt(cantStr);
            if (cantidad <= 0) throw new NumberFormatException();

            if (cantidad > producto.getStock()) {
                JOptionPane.showMessageDialog(this,
                    "Stock insuficiente. Disponible: " + producto.getStock(),
                    "Error", JOptionPane.WARNING_MESSAGE);
                return;
            }

            double subtotal = producto.getPrecio() * cantidad;
            double iva      = subtotal * 0.16;
            double total    = subtotal + iva;

            lblSubtotal.setText("$" + String.format("%.2f", subtotal));
            lblIva.setText("$"      + String.format("%.2f", iva));
            lblTotal.setText("$"    + String.format("%.2f", total));

        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this,
                "La cantidad debe ser un número entero positivo.",
                "Error de validación", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void confirmarVenta() {
        Producto producto = (Producto) cmbProductos.getSelectedItem();

        String resultado = ventaController.registrarVenta(producto, txtCantidad.getText());

        if (resultado.equals("OK")) {
            JOptionPane.showMessageDialog(this,
                "Venta registrada exitosamente.",
                "Éxito", JOptionPane.INFORMATION_MESSAGE);
            dispose(); // Cerrar ventana
        } else {
            JOptionPane.showMessageDialog(this,
                resultado,
                "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}