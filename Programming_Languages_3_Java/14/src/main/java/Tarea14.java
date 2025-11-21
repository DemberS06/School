import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.SwingUtilities;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.data.category.DefaultCategoryDataset;

/**
 * Tarea14
 * - Usa JFreeChart para mostrar un histograma (barras) con 4 rangos de edad:
 *   <20, 20-40, 41-60, >60
 * - Panel con botones (radio) para cada estado + "Todos"
 * - Panel con botones (radio) para genero: "Todos", "H", "M"
 *
 * Requisitos: jfreechart en el classpath.
 *
 * Ejecutar:
 * javac -cp ".;path\to\jfreechart-x.y.z.jar;path\to\other-deps.jar" Tarea14.java
 * java -cp ".;path\to\jfreechart-x.y.z.jar;path\to\other-deps.jar" Tarea14 [ruta_al_archivo]
 */
public class Tarea14 {
    static class Record {
        final int age;
        final String sex; // "H" or "M"
        final String state; // e.g. "OAX"
        Record(int age, String sex, String state) {
            this.age = age;
            this.sex = sex;
            this.state = state;
        }
    }

    private final java.util.List<Record> records = new ArrayList<>();
    private final Set<String> estados = new TreeSet<>();
    private JFrame frame;
    private ChartPanel chartPanel;
    private ButtonGroup estadosGroup = new ButtonGroup();
    private ButtonGroup sexoGroup = new ButtonGroup();
    private Map<String, JRadioButton> estadoButtons = new HashMap<>();
    private Map<String, JRadioButton> sexoButtons = new HashMap<>();

    public static void main(String[] args) {
        String path = args.length > 0 ? args[0] : "C:\\Users\\Dembe\\Proyects\\School\\Java\\14\\archModificado.txt";
        Tarea14 app = new Tarea14();
        if (!app.loadFile(path)) {
            System.err.println("No se pudo leer archivo: " + path);
            System.exit(1);
        }
        SwingUtilities.invokeLater(() -> app.buildAndShowGui());
    }

    private boolean loadFile(String path) {
        File f = new File(path);
        if (!f.exists()) {
            System.err.println("Archivo no encontrado: " + path);
            return false;
        }
        try (BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(f), StandardCharsets.UTF_8))) {
            String line;
            while ((line = br.readLine()) != null) {
                line = line.trim();
                if (line.isEmpty()) continue;
                // formato esperado: id,nombre,edad,sexo,estado
                String[] parts = line.split(",");
                if (parts.length < 5) continue;
                String edadS = parts[2].trim();
                String sexo = parts[3].trim().toUpperCase(Locale.ROOT);
                String estado = parts[4].trim().toUpperCase(Locale.ROOT);
                int edad;
                try {
                    edad = Integer.parseInt(edadS);
                } catch (NumberFormatException ex) {
                    continue; // ignorar linea con edad inválida
                }
                if (!sexo.equals("H") && !sexo.equals("M")) {
                    // Si hay otros códigos, los tratamos como "Otros" pero aquí ignoramos
                    // (podrías extenderlo si hace falta).
                    // Para que el filtro "Todos" funcione, guardamos igual.
                }
                records.add(new Record(edad, sexo, estado));
                estados.add(estado);
            }
            return true;
        } catch (Exception ex) {
            ex.printStackTrace();
            return false;
        }
    }

    private void buildAndShowGui() {
        frame = new JFrame("Tarea14 - Frecuencias por edad");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(1000, 650);
        frame.setLayout(new BorderLayout(8, 8));

        // Panel izquierdo: estados (radio buttons) dentro de scroll
        JPanel estadosPanel = new JPanel();
        estadosPanel.setLayout(new GridLayout(0, 1, 4, 4));
        JRadioButton allEstados = new JRadioButton("Todos");
        allEstados.setSelected(true);
        estadosGroup.add(allEstados);
        estadoButtons.put("TODOS", allEstados);
        estadosPanel.add(allEstados);

        for (String edo : estados) {
            JRadioButton rb = new JRadioButton(edo);
            estadosGroup.add(rb);
            estadosPanel.add(rb);
            estadoButtons.put(edo, rb);
            rb.addActionListener(e -> updateChart());
        }
        allEstados.addActionListener(e -> updateChart());

        JScrollPane estadosScroll = new JScrollPane(estadosPanel);
        estadosScroll.setPreferredSize(new Dimension(140, 400));
        estadosScroll.setBorder(BorderFactory.createTitledBorder("Estados"));

        // Panel superior: géneros
        JPanel sexoPanel = new JPanel();
        sexoPanel.setLayout(new GridLayout(1, 0, 8, 8));
        JRadioButton sexoTodos = new JRadioButton("Todos");
        JRadioButton sexoH = new JRadioButton("H");
        JRadioButton sexoM = new JRadioButton("M");
        sexoTodos.setSelected(true);

        sexoGroup.add(sexoTodos);
        sexoGroup.add(sexoH);
        sexoGroup.add(sexoM);
        sexoButtons.put("TODOS", sexoTodos);
        sexoButtons.put("H", sexoH);
        sexoButtons.put("M", sexoM);

        sexoPanel.add(sexoTodos);
        sexoPanel.add(sexoH);
        sexoPanel.add(sexoM);

        sexoTodos.addActionListener(e -> updateChart());
        sexoH.addActionListener(e -> updateChart());
        sexoM.addActionListener(e -> updateChart());

        JPanel leftContainer = new JPanel(new BorderLayout(4,4));
        leftContainer.add(estadosScroll, BorderLayout.CENTER);
        leftContainer.add(sexoPanel, BorderLayout.SOUTH);

        frame.add(leftContainer, BorderLayout.WEST);

        // Chart inicial (todos)
        DefaultCategoryDataset dataset = buildDataset(null, null); // null = todos
        JFreeChart chart = createChart(dataset, "Distribución de edades");
        chartPanel = new ChartPanel(chart);
        frame.add(chartPanel, BorderLayout.CENTER);

        // Pie de información / instrucciones abajo
        JLabel info = new JLabel("Selecciona un estado y/o género para filtrar. Rango de edades: <20, 20-40, 41-60, >60");
        frame.add(info, BorderLayout.SOUTH);

        // Añadir listeners a todos (estados ya añadidos)
        // (los ya añadidos por creación de botones manejan la actualización)

        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    /** Construye dataset filtrando por estado (null==todos) y sexo (null==todos) */
    private DefaultCategoryDataset buildDataset(String estadoFilter, String sexoFilter) {
        // categorías: "<20", "20-40", "41-60", ">60"
        String c1 = "<20";
        String c2 = "20-40";
        String c3 = "41-60";
        String c4 = ">60";

        int[] counts = new int[4];

        for (Record r : records) {
            if (estadoFilter != null && !estadoFilter.equalsIgnoreCase(r.state)) continue;
            if (sexoFilter != null && !sexoFilter.equalsIgnoreCase(r.sex)) continue;

            int a = r.age;
            if (a <= 19) counts[0]++;
            else if (a >= 20 && a <= 40) counts[1]++;
            else if (a >= 41 && a <= 60) counts[2]++;
            else if (a >= 61) counts[3]++;
        }

        DefaultCategoryDataset ds = new DefaultCategoryDataset();
        ds.addValue(counts[0], "Frecuencia", c1);
        ds.addValue(counts[1], "Frecuencia", c2);
        ds.addValue(counts[2], "Frecuencia", c3);
        ds.addValue(counts[3], "Frecuencia", c4);
        return ds;
    }

    private JFreeChart createChart(DefaultCategoryDataset ds, String title) {
        JFreeChart chart = ChartFactory.createBarChart(
                title,
                "Rango de edad",
                "Frecuencia",
                ds
        );
        return chart;
    }

    private void updateChart() {
        // averiguar selección de estado
        String selEstado = null;
        for (Map.Entry<String, JRadioButton> e : estadoButtons.entrySet()) {
            String key = e.getKey();
            JRadioButton rb = e.getValue();
            if (rb.isSelected()) {
                if (key.equals("TODOS")) selEstado = null;
                else selEstado = key;
                break;
            }
        }
        // sexo
        String selSexo = null;
        for (Map.Entry<String, JRadioButton> e : sexoButtons.entrySet()) {
            String key = e.getKey();
            JRadioButton rb = e.getValue();
            if (rb.isSelected()) {
                if (key.equals("TODOS")) selSexo = null;
                else selSexo = key;
                break;
            }
        }

        DefaultCategoryDataset ds = buildDataset(selEstado, selSexo);
        JFreeChart newChart = createChart(ds, makeTitle(selEstado, selSexo));
        chartPanel.setChart(newChart);
    }

    private String makeTitle(String estado, String sexo) {
        StringBuilder sb = new StringBuilder("Distribución de edades - ");
        if (estado == null) sb.append("Todos los estados");
        else sb.append(estado);
        sb.append(" / ");
        if (sexo == null) sb.append("Todos los géneros");
        else if (sexo.equals("H")) sb.append("Hombres");
        else if (sexo.equals("M")) sb.append("Mujeres");
        else sb.append(sexo);
        return sb.toString();
    }
}
