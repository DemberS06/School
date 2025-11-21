import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.nio.charset.StandardCharsets;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;

public class Tarea19 extends JFrame {

    private static final String URL  = "jdbc:mysql://localhost:3306/base1?useSSL=false&serverTimezone=UTC";
    private static final String USER = "root";
    private static final String PASS = ""; 

    private final JTextArea salida = new JTextArea(16, 60);
    private final JTextField txtId = new JTextField(8);
    private final JTextField txtNombre = new JTextField(18);

    public Tarea19() {
        super("CRUD tabla1 (MySQL/XAMPP) - Leer / Insertar / Borrar / Cargar CSV");

        salida.setEditable(false);
        JScrollPane scroll = new JScrollPane(salida);

        JButton btnLeer     = new JButton("Leer todo");
        JButton btnInsertar = new JButton("Insertar");
        JButton btnBorrar   = new JButton("Borrar por ID");
        JButton btnCargar   = new JButton("Cargar CSV (batch)");
        JButton btnImprimir = new JButton("Imprimir");
        JButton btnGuardar  = new JButton("Guardar .txt");

        btnLeer.addActionListener(this::onLeer);
        btnInsertar.addActionListener(this::onInsertar);
        btnBorrar.addActionListener(this::onBorrar);
        btnCargar.addActionListener(this::onCargarCSV);
        btnImprimir.addActionListener(e -> {
            try { salida.print(); } catch (Exception ex) { JOptionPane.showMessageDialog(this, "No se pudo imprimir:\n"+ex.getMessage()); }
        });
        btnGuardar.addActionListener(e -> {
            try {
                File f = new File("resultado.txt");
                try (Writer w = new OutputStreamWriter(new FileOutputStream(f), StandardCharsets.UTF_8)) {
                    w.write(salida.getText());
                }
                JOptionPane.showMessageDialog(this, "Guardado: " + f.getAbsolutePath());
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Error al guardar:\n"+ex.getMessage());
            }
        });

        JPanel fila1 = new JPanel(new FlowLayout(FlowLayout.LEFT));
        fila1.add(new JLabel("Id:"));
        fila1.add(txtId);
        fila1.add(new JLabel("Nombre:"));
        fila1.add(txtNombre);
        fila1.add(btnInsertar);
        fila1.add(btnBorrar);

        JPanel fila2 = new JPanel(new FlowLayout(FlowLayout.LEFT));
        fila2.add(btnLeer);
        fila2.add(btnCargar);
        fila2.add(btnImprimir);
        fila2.add(btnGuardar);

        setLayout(new BorderLayout(10,10));
        add(fila1, BorderLayout.NORTH);
        add(scroll, BorderLayout.CENTER);
        add(fila2, BorderLayout.SOUTH);

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        pack();
        setLocationRelativeTo(null);

        ensureTable(); // crea la tabla si no existe
    }

    private void ensureTable() {
        String ddl = "CREATE TABLE IF NOT EXISTS tabla1 (" +
                     "  Id INT," +
                     "  nombre VARCHAR(100)" +
                     ")";
        try (Connection cn = DriverManager.getConnection(URL, USER, PASS);
             Statement st = cn.createStatement()) {
            st.execute(ddl);
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, "Error creando/verificando tabla:\n" + ex.getMessage(),
                    "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void onLeer(ActionEvent e) {
        salida.setText("");
        String sql = "SELECT Id, nombre FROM tabla1 ORDER BY Id";
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
            JOptionPane.showMessageDialog(this, "Error al leer:\n" + ex.getMessage(),
                    "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void onInsertar(ActionEvent e) {
        String sId = txtId.getText().trim();
        String nombre = txtNombre.getText().trim();
        if (sId.isEmpty() || nombre.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Captura Id y Nombre.");
            return;
        }
        int id;
        try { id = Integer.parseInt(sId); }
        catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Id debe ser numérico.");
            return;
        }

        String sql = "INSERT INTO tabla1 (Id, nombre) VALUES (?, ?)";
        try (Connection cn = DriverManager.getConnection(URL, USER, PASS);
             PreparedStatement ps = cn.prepareStatement(sql)) {
            ps.setInt(1, id);
            ps.setString(2, nombre);
            int n = ps.executeUpdate();
            salida.append("Insertados: " + n + " fila(s)\n");
            onLeer(null);
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, "Error al insertar:\n" + ex.getMessage(),
                    "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void onBorrar(ActionEvent e) {
        String sId = txtId.getText().trim();
        if (sId.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Captura el Id a borrar.");
            return;
        }
        int id;
        try { id = Integer.parseInt(sId); }
        catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Id debe ser numérico.");
            return;
        }

        String sql = "DELETE FROM tabla1 WHERE Id = ?";
        try (Connection cn = DriverManager.getConnection(URL, USER, PASS);
             PreparedStatement ps = cn.prepareStatement(sql)) {
            ps.setInt(1, id);
            int n = ps.executeUpdate();
            salida.append("Borrados: " + n + " fila(s) con Id=" + id + "\n");
            onLeer(null);
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, "Error al borrar:\n" + ex.getMessage(),
                    "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void onCargarCSV(ActionEvent e) {
        // Abre archivo (ej. el del aula con 1000 registros)
        JFileChooser fc = new JFileChooser();
        fc.setDialogTitle("Selecciona CSV (formato: Id,nombre)");
        int r = fc.showOpenDialog(this);
        if (r != JFileChooser.APPROVE_OPTION) return;

        File f = fc.getSelectedFile();
        List<String[]> filas = new ArrayList<>();

        // Lee CSV simple: Id,nombre (con o sin encabezado). Ajusta si tu archivo trae otro separador.
        try (BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(f), StandardCharsets.UTF_8))) {
            String line;
            while ((line = br.readLine()) != null) {
                line = line.trim();
                if (line.isEmpty()) continue;
                String[] parts = line.split(",", -1); // separador coma
                if (parts.length < 2) continue;
                // Detecta encabezado si la primera columna no es número
                if (filas.isEmpty()) {
                    try { Integer.parseInt(parts[0].trim()); }
                    catch (NumberFormatException ex2) {
                        // es encabezado: saltar y sigue
                        continue;
                    }
                }
                filas.add(new String[]{parts[0].trim(), parts[2].trim()});
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Error leyendo archivo:\n" + ex.getMessage(),
                    "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        if (filas.isEmpty()) {
            JOptionPane.showMessageDialog(this, "No se encontraron filas válidas (Id,nombre).");
            return;
        }

        // Inserción en batch (rápida)
        String sql = "INSERT INTO tabla1 (Id, nombre) VALUES (?, ?)";
        int ok = 0, bad = 0;
        try (Connection cn = DriverManager.getConnection(URL, USER, PASS)) {
            cn.setAutoCommit(false);
            try (PreparedStatement ps = cn.prepareStatement(sql)) {
                for (String[] p : filas) {
                    try {
                        int id = Integer.parseInt(p[0]);
                        String nombre = p[1];
                        ps.setInt(1, id);
                        ps.setString(2, nombre);
                        ps.addBatch();
                        ok++;
                    } catch (Exception exEach) {
                        bad++;
                    }
                }
                ps.executeBatch();
            }
            cn.commit();
            salida.append("Cargado CSV: " + ok + " insertados");
            if (bad > 0) salida.append(" | " + bad + " saltados\n");
            else salida.append("\n");
            onLeer(null);
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, "Error insertando en batch:\n" + ex.getMessage(),
                    "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    public static void main(String[] args) {
        try { UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName()); } catch (Exception ignored) {}
        SwingUtilities.invokeLater(() -> new Tarea19().setVisible(true));
    }
}
