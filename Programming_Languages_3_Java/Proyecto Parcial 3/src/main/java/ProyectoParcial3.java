import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Types;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.swing.AbstractButton;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;
import javax.swing.filechooser.FileNameExtensionFilter;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.data.category.DefaultCategoryDataset;

public class ProyectoParcial3 extends JFrame {

    // ===== BD (XAMPP) =====
    private static final String URL  = "jdbc:mysql://localhost:3306/base1?useSSL=false&serverTimezone=UTC";
    private static final String USER = "root";
    private static final String PASS = "";

    // ===== UI =====
    private final JTextArea salida = new JTextArea(18, 72);
    private final JTextField txtId = new JTextField(6);
    private final JTextField txtId2 = new JTextField(6);
    private final JTextField txtNombre = new JTextField(18);
    private final JTextField txtEdad = new JTextField(4);
    private final JComboBox<String> cbSexo = new JComboBox<>(new String[]{"H","M"});
    private final JTextField txtEstado = new JTextField(12);
    private final JTextField txtRuta = new JTextField("C:\\Users\\Dembe\\Proyects\\School\\Java\\Proyecto Parcial 3\\milArchivo.txt", 44);

    private final ButtonGroup estadosGroup = new ButtonGroup();
    private final ButtonGroup sexoGroup = new ButtonGroup();
    private final JPanel estadosPanel = new JPanel(new GridLayout(0, 1, 6, 6));
    private final JPanel sexoPanel = new JPanel(new GridLayout(1, 0, 12, 12));
    private final Map<String, JRadioButton> estadoButtons = new LinkedHashMap<>();
    private final Map<String, JRadioButton> sexoButtons = new LinkedHashMap<>();

    private ChartPanel chartPanel;

    public ProyectoParcial3() {
        super("Proyecto parcial 3");

        // ===== raíz =====
        JPanel root = new JPanel(new BorderLayout(12,12));
        root.setBorder(new EmptyBorder(12,12,12,12));
        setContentPane(root);

        // ===== WEST: Estados + Sexo =====
        estadosPanel.setBorder(new EmptyBorder(8,8,8,8));
        JScrollPane estadosScroll = new JScrollPane(estadosPanel);
        estadosScroll.setPreferredSize(new Dimension(210, 380));
        estadosScroll.setBorder(new TitledBorder("Estados"));

        sexoPanel.setBorder(new TitledBorder("Sexo"));
        JRadioButton rSexoTodos = new JRadioButton("Todos", true);
        JRadioButton rSexoH = new JRadioButton("H");
        JRadioButton rSexoM = new JRadioButton("M");
        sexoGroup.add(rSexoTodos); sexoGroup.add(rSexoH); sexoGroup.add(rSexoM);
        sexoPanel.add(rSexoTodos); sexoPanel.add(rSexoH); sexoPanel.add(rSexoM);
        sexoButtons.put("TODOS", rSexoTodos);
        sexoButtons.put("H", rSexoH);
        sexoButtons.put("M", rSexoM);
        rSexoTodos.addActionListener(e -> refreshChart());
        rSexoH.addActionListener(e -> refreshChart());
        rSexoM.addActionListener(e -> refreshChart());

        JPanel west = new JPanel(new BorderLayout(10,10));
        west.add(estadosScroll, BorderLayout.CENTER);
        west.add(sexoPanel, BorderLayout.SOUTH);
        root.add(west, BorderLayout.WEST);

        // ===== CENTER: formulario + archivo + salida =====
        JPanel center = new JPanel(new BorderLayout(10,10));
        center.setBorder(new EmptyBorder(8,8,8,8));

        // — formulario (GridBag bien alineado) —
        JPanel form = new JPanel(new GridBagLayout());
        form.setBorder(new TitledBorder("Captura / Acciones"));
        GridBagConstraints c = new GridBagConstraints();
        c.insets = new Insets(6,8,6,8);
        c.fill = GridBagConstraints.HORIZONTAL;

        int row=0;
        c.gridy = row; c.gridx=0; form.add(new JLabel("Id:"), c);
        c.gridx=1; form.add(txtId, c);
        c.gridx=2; form.add(new JLabel("Id2:"), c);
        c.gridx=3; form.add(txtId2, c);
        c.gridx=4; form.add(new JLabel("Nombre:"), c);
        c.gridx=5; c.weightx=1; form.add(txtNombre, c);
        c.weightx=0;

        row++;
        c.gridy = row; c.gridx=0; form.add(new JLabel("Edad:"), c);
        c.gridx=1; form.add(txtEdad, c);
        c.gridx=2; form.add(new JLabel("Sexo:"), c);
        c.gridx=3; form.add(cbSexo, c);
        c.gridx=4; form.add(new JLabel("Estado:"), c);
        c.gridx=5; c.weightx=1; form.add(txtEstado, c);
        c.weightx=0;

        row++;
        JButton btnAgregar  = new JButton("Agregar / Actualizar");
        JButton btnEliminar = new JButton("Eliminar por ID");
        JButton btnVaciar   = new JButton("Vaciar BD");
        JButton btnLeer     = new JButton("Leer BD");
        c.gridy=row; c.gridx=0; c.gridwidth=2; form.add(btnAgregar, c);
        c.gridx=2; c.gridwidth=2; form.add(btnEliminar, c);
        c.gridx=4; c.gridwidth=1; form.add(btnVaciar, c);
        c.gridx=5; c.gridwidth=1; form.add(btnLeer, c);

        // — archivo —
        JPanel archivo = new JPanel(new GridBagLayout());
        archivo.setBorder(new TitledBorder("Archivo (id,id2,nombre,edad,sexo,estado)"));
        GridBagConstraints a = new GridBagConstraints();
        a.insets = new Insets(6,8,6,8);
        a.fill = GridBagConstraints.HORIZONTAL;
        JButton btnExaminar = new JButton("Examinar…");
        JButton btnCargar   = new JButton("Cargar archivo → BD");
        a.gridx=0; a.gridy=0; a.weightx=1; archivo.add(txtRuta, a);
        a.gridx=1; a.weightx=0; archivo.add(btnExaminar, a);
        a.gridx=2; archivo.add(btnCargar, a);

        // — salida —
        salida.setEditable(false);
        salida.setFont(new Font(Font.MONOSPACED, Font.PLAIN, 12));
        JScrollPane scroll = new JScrollPane(salida);
        scroll.setPreferredSize(new Dimension(760, 340));
        scroll.setBorder(new TitledBorder("Salida"));

        center.add(form, BorderLayout.NORTH);
        center.add(archivo, BorderLayout.CENTER);
        center.add(scroll, BorderLayout.SOUTH);
        root.add(center, BorderLayout.CENTER);

        // ===== EAST: gráfica =====
        DefaultCategoryDataset ds = buildDataset(null, null);
        JFreeChart chart = ChartFactory.createBarChart(
                "Distribución de edades - Todos los estados / Todos los géneros",
                "Rango de edad", "Frecuencia", ds
        );
        tuneChart(chart);
        chartPanel = new ChartPanel(chart);
        chartPanel.setPreferredSize(new Dimension(560, 440));
        JPanel east = new JPanel(new BorderLayout(8,8));
        east.setBorder(new EmptyBorder(8,8,8,8));
        east.add(chartPanel, BorderLayout.CENTER);
        east.add(new JLabel("Rangos: <20, 20–40, 41–60, >60", SwingConstants.CENTER), BorderLayout.SOUTH);
        root.add(east, BorderLayout.EAST);

        // ===== acciones =====
        btnExaminar.addActionListener(this::onExaminar);
        btnCargar.addActionListener(this::onCargarArchivo);
        btnAgregar.addActionListener(this::onAgregar);
        btnEliminar.addActionListener(this::onEliminar);
        btnVaciar.addActionListener(this::onVaciar);
        btnLeer.addActionListener(this::onLeer);

        ensureTabla();
        reloadEstadosPanel();   // llena radios con estados de BD
        refreshChart();
        onLeer(null);

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1320, 720);
        setLocationRelativeTo(null);
    }

    // ==================== Chart cosmetics ====================
    private void tuneChart(JFreeChart chart) {
        chart.setBackgroundPaint(Color.WHITE);
        CategoryPlot plot = (CategoryPlot) chart.getPlot();
        plot.setBackgroundPaint(Color.WHITE);
        plot.setOutlineVisible(false);
        NumberAxis range = (NumberAxis) plot.getRangeAxis();
        range.setStandardTickUnits(NumberAxis.createIntegerTickUnits()); // NO notación científica
        range.setAutoRangeIncludesZero(true);
        range.setLowerMargin(0.10);
        range.setUpperMargin(0.10);
        chart.getTitle().setFont(chart.getTitle().getFont().deriveFont(Font.BOLD, 18f));
    }

    // ==================== BD ====================
    private void ensureTabla() {
        String ddl =
            "CREATE TABLE IF NOT EXISTS personas (" +
            "  id INT PRIMARY KEY," +                    // usamos el id del archivo como PK
            "  id2 INT NULL," +
            "  nombre VARCHAR(80) NOT NULL," +
            "  edad INT NOT NULL," +
            "  sexo CHAR(1) NOT NULL," +
            "  estado VARCHAR(20) NOT NULL" +
            ")";
        try (Connection cn = DriverManager.getConnection(URL, USER, PASS);
             Statement st = cn.createStatement()) {
            st.execute(ddl);
        } catch (SQLException ex) {
            showErr("Error creando/verificando tabla personas", ex);
        }
    }

    private void onLeer(ActionEvent e) {
        salida.setText("personas(id,id2,nombre,edad,sexo,estado):\n");
        String sql = "SELECT id,id2,nombre,edad,sexo,estado FROM personas ORDER BY id";
        try (Connection cn = DriverManager.getConnection(URL, USER, PASS);
             PreparedStatement ps = cn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                salida.append(String.format("%d,%s,%s,%d,%s,%s%n",
                        rs.getInt(1),
                        rs.getObject(2)==null? "": String.valueOf(rs.getInt(2)),
                        rs.getString(3),
                        rs.getInt(4),
                        rs.getString(5),
                        rs.getString(6)));
            }
        } catch (SQLException ex) {
            showErr("Error leyendo personas", ex);
        }
    }

    private void onAgregar(ActionEvent e) {
        Integer id = parseInt(txtId.getText());
        Integer id2 = parseInt(txtId2.getText());
        Integer edad = parseInt(txtEdad.getText());
        String nombre = txtNombre.getText().trim();
        String sexo = String.valueOf(cbSexo.getSelectedItem());
        String estado = txtEstado.getText().trim();

        if (id == null || edad == null || nombre.isEmpty() || sexo.isEmpty() || estado.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Completa: id, nombre, edad, sexo, estado (id2 opcional).");
            return;
        }

        String sql =
            "INSERT INTO personas(id,id2,nombre,edad,sexo,estado) VALUES(?,?,?,?,?,?) " +
            "ON DUPLICATE KEY UPDATE id2=VALUES(id2), nombre=VALUES(nombre), edad=VALUES(edad), sexo=VALUES(sexo), estado=VALUES(estado)";
        try (Connection cn = DriverManager.getConnection(URL, USER, PASS);
             PreparedStatement ps = cn.prepareStatement(sql)) {
            ps.setInt(1, id);
            if (id2 != null) ps.setInt(2, id2); else ps.setNull(2, Types.INTEGER);
            ps.setString(3, nombre);
            ps.setInt(4, edad);
            ps.setString(5, sexo.toUpperCase(Locale.ROOT));
            ps.setString(6, estado.toUpperCase(Locale.ROOT));
            ps.executeUpdate();
            onLeer(null);
            reloadEstadosPanel();
            refreshChart();
        } catch (SQLException ex) {
            showErr("Error al agregar/actualizar", ex);
        }
    }

    private void onEliminar(ActionEvent e) {
        Integer id = parseInt(txtId.getText());
        if (id == null) { JOptionPane.showMessageDialog(this, "Captura un Id numérico para eliminar."); return; }
        try (Connection cn = DriverManager.getConnection(URL, USER, PASS);
             PreparedStatement ps = cn.prepareStatement("DELETE FROM personas WHERE id=?")) {
            ps.setInt(1, id);
            ps.executeUpdate();
            onLeer(null);
            reloadEstadosPanel();
            refreshChart();
        } catch (SQLException ex) {
            showErr("Error al eliminar", ex);
        }
    }

    private void onVaciar(ActionEvent e) {
        int r = JOptionPane.showConfirmDialog(this, "¿Vaciar completamente 'personas'?", "Confirmar", JOptionPane.YES_NO_OPTION);
        if (r != JOptionPane.YES_OPTION) return;
        try (Connection cn = DriverManager.getConnection(URL, USER, PASS);
             Statement st = cn.createStatement()) {
            st.execute("TRUNCATE TABLE personas");
            onLeer(null);
            reloadEstadosPanel();
            refreshChart();
        } catch (SQLException ex) {
            showErr("Error al vaciar BD", ex);
        }
    }

    // ==================== Archivo → BD ====================
    private void onExaminar(ActionEvent e) {
        JFileChooser fc = new JFileChooser();
        fc.setDialogTitle("Selecciona CSV/TXT con 6 columnas: id,id2,nombre,edad,sexo,estado");
        fc.setFileFilter(new FileNameExtensionFilter("Datos (*.csv, *.txt)", "csv", "txt"));
        if (fc.showOpenDialog(this) == JFileChooser.APPROVE_OPTION) {
            txtRuta.setText(fc.getSelectedFile().getAbsolutePath());
        }
    }

    private void onCargarArchivo(ActionEvent e) {
        File f = new File(txtRuta.getText().trim());
        if (!f.exists()) { JOptionPane.showMessageDialog(this, "No existe el archivo."); return; }

        List<String[]> filas = leerCSV6(f);
        if (filas.isEmpty()) { JOptionPane.showMessageDialog(this, "No hay filas válidas (se esperan 6 columnas)."); return; }

        String sql = "INSERT INTO personas(id,id2,nombre,edad,sexo,estado) VALUES(?,?,?,?,?,?) " +
                     "ON DUPLICATE KEY UPDATE id2=VALUES(id2), nombre=VALUES(nombre), edad=VALUES(edad), sexo=VALUES(sexo), estado=VALUES(estado)";

        int ok=0, bad=0, batch=0;
        try (Connection cn = DriverManager.getConnection(URL, USER, PASS)) {
            cn.setAutoCommit(false);
            try (PreparedStatement ps = cn.prepareStatement(sql)) {
                for (String[] p : filas) {
                    try {
                        int id    = Integer.parseInt(p[0]);
                        Integer id2 = tryParseInt(p[1]);
                        String nom= p[2];
                        int edad  = Integer.parseInt(p[3]);
                        String sx = p[4].toUpperCase(Locale.ROOT);
                        String edo= p[5].toUpperCase(Locale.ROOT);

                        ps.setInt(1, id);
                        if (id2 != null) ps.setInt(2, id2); else ps.setNull(2, Types.INTEGER);
                        ps.setString(3, nom);
                        ps.setInt(4, edad);
                        ps.setString(5, sx);
                        ps.setString(6, edo);
                        ps.addBatch();
                        ok++; batch++;
                        if (batch % 500 == 0) ps.executeBatch();
                    } catch (Exception exEach) {
                        bad++;
                    }
                }
                ps.executeBatch();
            }
            cn.commit();
            JOptionPane.showMessageDialog(this, "Carga terminada. OK=" + ok + " | Saltados=" + bad);
            onLeer(null);
            reloadEstadosPanel();
            refreshChart();
        } catch (SQLException ex) {
            showErr("Error cargando archivo", ex);
        }
    }

    /** Lee archivo con EXACTAMENTE 6 columnas separadas por coma: id,id2,nombre,edad,sexo,estado. Salta encabezado. */
    private static List<String[]> leerCSV6(File f) {
        List<String[]> out = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(f), StandardCharsets.UTF_8))) {
            String line; boolean first = true;
            while ((line = br.readLine()) != null) {
                line = line.trim();
                if (line.isEmpty()) continue;
                String[] raw = line.split(",", -1);
                if (raw.length != 6) continue;
                String[] p = new String[6];
                for (int i=0;i<6;i++) p[i] = raw[i].trim();
                // si hay encabezado, lo saltamos
                if (first && !isInt(p[0])) { first = false; continue; }
                first = false;
                if (!isInt(p[0]) || !isInt(p[3])) continue;
                if (p[2].isEmpty() || p[4].isEmpty() || p[5].isEmpty()) continue;
                out.add(p);
            }
        } catch (IOException ex) { ex.printStackTrace(); }
        return out;
    }

    // ==================== Estados (radios) + gráfica ====================
    private void reloadEstadosPanel() {
        estadosPanel.removeAll();
        estadoButtons.clear();
        // limpiar grupo
        for (Enumeration<AbstractButton> en = estadosGroup.getElements(); en.hasMoreElements();) {
            estadosGroup.remove(en.nextElement());
        }
        // “Todos”
        JRadioButton rAll = new JRadioButton("Todos", true);
        estadosGroup.add(rAll);
        estadosPanel.add(rAll);
        estadoButtons.put("TODOS", rAll);
        rAll.addActionListener(e -> refreshChart());

        String sql = "SELECT DISTINCT estado FROM personas ORDER BY estado";
        try (Connection cn = DriverManager.getConnection(URL, USER, PASS);
             Statement st = cn.createStatement();
             ResultSet rs = st.executeQuery(sql)) {
            while (rs.next()) {
                String edo = rs.getString(1);
                JRadioButton rb = new JRadioButton(edo);
                estadosGroup.add(rb);
                estadosPanel.add(rb);
                estadoButtons.put(edo, rb);
                rb.addActionListener(e -> refreshChart());
            }
        } catch (SQLException ex) {
            showErr("Error leyendo estados distintos", ex);
        }
        estadosPanel.revalidate();
        estadosPanel.repaint();
    }

    private void refreshChart() {
        String estado = getEstadoSeleccionado(); // null = todos
        String sexo = getSexoSeleccionado();     // null = todos
        DefaultCategoryDataset ds = buildDataset(estado, sexo);
        JFreeChart chart = ChartFactory.createBarChart(
                makeTitle(estado, sexo), "Rango de edad", "Frecuencia", ds
        );
        tuneChart(chart);
        chartPanel.setChart(chart);
    }

    private String getEstadoSeleccionado() {
        for (Map.Entry<String, JRadioButton> e : estadoButtons.entrySet()) {
            if (e.getValue().isSelected()) return e.getKey().equals("TODOS") ? null : e.getKey();
        }
        return null;
    }
    private String getSexoSeleccionado() {
        for (Map.Entry<String, JRadioButton> e : sexoButtons.entrySet()) {
            if (e.getValue().isSelected()) {
                String k = e.getKey();
                return k.equals("TODOS") ? null : k;
            }
        }
        return null;
    }

    /** Histograma de 4 rangos: <20, 20–40, 41–60, >60 */
    private DefaultCategoryDataset buildDataset(String estadoFilter, String sexoFilter) {
        String c1 = "<20", c2 = "20-40", c3 = "41-60", c4 = ">60";
        StringBuilder sb = new StringBuilder();
        sb.append("SELECT ");
        sb.append("SUM(CASE WHEN edad<=19 THEN 1 ELSE 0 END) c1, ");
        sb.append("SUM(CASE WHEN edad BETWEEN 20 AND 40 THEN 1 ELSE 0 END) c2, ");
        sb.append("SUM(CASE WHEN edad BETWEEN 41 AND 60 THEN 1 ELSE 0 END) c3, ");
        sb.append("SUM(CASE WHEN edad>=61 THEN 1 ELSE 0 END) c4 ");
        sb.append("FROM personas WHERE 1=1 ");
        List<Object> params = new ArrayList<>();
        if (estadoFilter != null) { sb.append("AND UPPER(estado)=? "); params.add(estadoFilter.toUpperCase(Locale.ROOT)); }
        if (sexoFilter != null)   { sb.append("AND UPPER(sexo)=? ");   params.add(sexoFilter.toUpperCase(Locale.ROOT)); }

        int v1=0,v2=0,v3=0,v4=0;
        try (Connection cn = DriverManager.getConnection(URL, USER, PASS);
             PreparedStatement ps = cn.prepareStatement(sb.toString())) {
            for (int i=0;i<params.size();i++) ps.setString(i+1, (String) params.get(i));
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    v1 = rs.getInt("c1");
                    v2 = rs.getInt("c2");
                    v3 = rs.getInt("c3");
                    v4 = rs.getInt("c4");
                }
            }
        } catch (SQLException ex) { showErr("Error leyendo datos para gráfica", ex); }

        DefaultCategoryDataset ds = new DefaultCategoryDataset();
        ds.addValue(v1, "Frecuencia", c1);
        ds.addValue(v2, "Frecuencia", c2);
        ds.addValue(v3, "Frecuencia", c3);
        ds.addValue(v4, "Frecuencia", c4);
        return ds;
    }

    private String makeTitle(String estado, String sexo) {
        StringBuilder sb = new StringBuilder("Distribución de edades - ");
        sb.append(estado == null ? "Todos los estados" : estado);
        sb.append(" / ");
        if (sexo == null) sb.append("Todos los géneros");
        else if ("H".equals(sexo)) sb.append("Hombres");
        else if ("M".equals(sexo)) sb.append("Mujeres");
        else sb.append(sexo);
        return sb.toString();
    }

    // ==================== util ====================
    private static Integer parseInt(String s) {
        try { return Integer.parseInt(s.trim()); } catch (Exception e) { return null; }
    }
    private static Integer tryParseInt(String s) {
        try { return Integer.parseInt(s.trim()); } catch (Exception e) { return null; }
    }
    private static boolean isInt(String s) {
        try { Integer.parseInt(s.trim()); return true; } catch (Exception e) { return false; }
    }
    private void showErr(String msg, Exception ex) {
        JOptionPane.showMessageDialog(this, msg + ":\n" + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
    }

    public static void main(String[] args) {
        try { UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName()); } catch (Exception ignored) {}
        SwingUtilities.invokeLater(() -> new ProyectoParcial3().setVisible(true));
    }
}
