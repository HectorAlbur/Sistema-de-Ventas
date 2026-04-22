package view;

import controller.VentaController;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class HistorialView extends JFrame {

    private VentaController controller = new VentaController();
    private DefaultTableModel modeloTabla;

    public HistorialView() {
        setTitle("Historial de Ventas");
        setSize(850, 420);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout(10, 10));

        JLabel titulo = new JLabel("Historial de Ventas", SwingConstants.CENTER);
        titulo.setFont(new Font("Arial", Font.BOLD, 17));
        titulo.setBorder(BorderFactory.createEmptyBorder(15, 0, 5, 0));
        add(titulo, BorderLayout.NORTH);

        String[] columnas = {"# Venta", "Fecha", "Producto", "Cantidad", "Precio Unit.", "Subtotal", "IVA", "Total"};
        modeloTabla = new DefaultTableModel(columnas, 0) {
            @Override
            public boolean isCellEditable(int row, int col) { return false; }
        };

        JTable tabla = new JTable(modeloTabla);
        tabla.setRowHeight(25);
        tabla.getColumnModel().getColumn(0).setMaxWidth(60);
        tabla.getColumnModel().getColumn(3).setMaxWidth(70);
        tabla.setFont(new Font("Arial", Font.PLAIN, 13));
        tabla.getTableHeader().setFont(new Font("Arial", Font.BOLD, 13));

        add(new JScrollPane(tabla), BorderLayout.CENTER);

        JPanel panelSur = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        JLabel lblContador = new JLabel();
        lblContador.setFont(new Font("Arial", Font.ITALIC, 12));
        lblContador.setForeground(Color.GRAY);
        panelSur.add(lblContador);
        add(panelSur, BorderLayout.SOUTH);

        List<String[]> historial = controller.obtenerHistorial();
        for (String[] fila : historial) {
            modeloTabla.addRow(fila);
        }

        lblContador.setText("Total de ventas registradas: " + historial.size() + "   ");
    }
}