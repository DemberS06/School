import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;

public class Tarea18 extends JFrame {

    private static final String URL  = "jdbc:mysql://localhost:3306/base1?useSSL=false&serverTimezone=UTC";
    private static final String USER = "root";
    private static final String PASS = ""; // XAMPP por defecto

    private final JTextArea salida = new JTextArea(12, 40);

    public Tarea18() {
        super("SELECT * FROM tabla1 (MySQL/XAMPP)");
        salida.setEditable(false);

        JButton btnLeer = new JButton("Leer");
        btnLeer.addActionListener(this::onLeer);

        JPanel top = new JPanel(new FlowLayout(FlowLayout.LEFT));
        top.add(new JLabel("SELECT * FROM tabla1"));
        top.add(btnLeer);

        setLayout(new BorderLayout(10,10));
        add(top, BorderLayout.NORTH);
        add(new JScrollPane(salida), BorderLayout.CENTER);

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        pack();
        setLocationRelativeTo(null);
    }

    private void onLeer(ActionEvent e) {
        salida.setText("");
        String sql = "SELECT Id, nombre FROM tabla1";
        try (Connection cn = DriverManager.getConnection(URL, USER, PASS);
             PreparedStatement ps = cn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            salida.append("Resultados (Id, nombre):\n");
            boolean hay = false;
            while (rs.next()) {
                hay = true;
                salida.append(rs.getInt("Id") + ", " + rs.getString("nombre") + "\n");
            }
            if (!hay) salida.append("(sin filas)\n");

        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this,
                "Error de base de datos:\n" + ex.getMessage(),
                "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    public static void main(String[] args) {
        try { UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName()); } catch (Exception ignored) {}
        SwingUtilities.invokeLater(() -> new Tarea18().setVisible(true));
    }
}
