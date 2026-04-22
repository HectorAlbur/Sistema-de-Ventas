package view;

import javax.swing.*;
import java.awt.*;

public class MainView extends JFrame {

    public MainView() {
        setTitle("Sistema de Ventas - Supermercado");
        setSize(400, 320);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());
        setResizable(false);

        JLabel titulo = new JLabel("Supermercado", SwingConstants.CENTER);
        titulo.setFont(new Font("Arial", Font.BOLD, 20));
        titulo.setBorder(BorderFactory.createEmptyBorder(25, 0, 10, 0));
        add(titulo, BorderLayout.NORTH);

        JLabel subtitulo = new JLabel("Sistema de Ventas", SwingConstants.CENTER);
        subtitulo.setFont(new Font("Arial", Font.PLAIN, 13));
        subtitulo.setForeground(Color.GRAY);

        JPanel panel = new JPanel(new GridLayout(4, 1, 0, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(10, 60, 30, 60));

        panel.add(subtitulo);

        JButton btnProductos = new JButton("Gestionar Productos");
        JButton btnVenta = new JButton("Realizar Venta");
        JButton btnHistorial = new JButton("Historial de Ventas");

        for (JButton btn : new JButton[]{btnProductos, btnVenta, btnHistorial}) {
            btn.setFont(new Font("Arial", Font.PLAIN, 14));
            btn.setPreferredSize(new Dimension(0, 40));
        }

        btnProductos.addActionListener(e -> new ProductoView().setVisible(true));
        btnVenta.addActionListener(e -> new VentaView().setVisible(true));
        btnHistorial.addActionListener(e -> new HistorialView().setVisible(true));

        panel.add(btnProductos);
        panel.add(btnVenta);
        panel.add(btnHistorial);

        add(panel, BorderLayout.CENTER);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new MainView().setVisible(true));
    }
}