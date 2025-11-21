
import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTextArea;
import javax.swing.ScrollPaneConstants;
import javax.swing.SwingUtilities;
import javax.swing.WindowConstants;

public class Tarea16 extends JPanel {

    private static final Path BASE_DIR = Paths.get("C:\\Users\\Dembe\\Proyects\\School\\Java\\16");

    private static final Path PATH_TRABAJO = BASE_DIR.resolve("trabajo.txt");
    private static final Path PATH_CASA    = BASE_DIR.resolve("casa.txt");
    private static final Path PATH_ESCUELA = BASE_DIR.resolve("escuela.txt");

    public Tarea16() {
        setLayout(new BorderLayout());

        try { Files.createDirectories(BASE_DIR); } catch (Exception ignored) {}

        JTabbedPane tabs = new JTabbedPane();
        tabs.addTab("trabajo", new TabPanel(PATH_TRABAJO));
        tabs.addTab("casa",    new TabPanel(PATH_CASA));
        tabs.addTab("escuela", new TabPanel(PATH_ESCUELA));
        add(tabs, BorderLayout.CENTER);
    }

    /** Panel reutilizable: área de texto + botón Guardar, ligado a una ruta fija */
    private static class TabPanel extends JPanel {
        private final JTextArea area = new JTextArea(18, 40);
        private final JButton btnGuardar = new JButton("Guardar");
        private final JLabel lblRuta = new JLabel();

        private final Path ruta;

        TabPanel(Path ruta) {
            this.ruta = ruta;

            area.setLineWrap(true);
            area.setWrapStyleWord(true);

            setLayout(new BorderLayout(8, 8));
            setBorder(BorderFactory.createEmptyBorder(8, 8, 8, 8));

            // Barra superior con la ruta (solo informativa)
            JPanel barra = new JPanel(new FlowLayout(FlowLayout.LEFT, 8, 6));
            //lblRuta.setText("Archivo: " + ruta.toString());
            lblRuta.setToolTipText(ruta.toString());
            barra.add(lblRuta);
            barra.add(btnGuardar);

            add(barra, BorderLayout.NORTH);
            add(new JScrollPane(area,
                    ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED,
                    ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER),
                BorderLayout.CENTER);

            // Cargar contenido inicial si existe
            cargar();

            // Guardar
            btnGuardar.addActionListener(e -> guardar());
        }

        private void cargar() {
            try {
                if (Files.exists(ruta)) {
                    String contenido = Files.readString(ruta, StandardCharsets.UTF_8);
                    area.setText(contenido);
                } else {
                    area.setText("");
                }
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this,
                        "No se pudo leer:\n" + ruta + "\n\n" + ex.getMessage(),
                        "Lectura", JOptionPane.WARNING_MESSAGE);
            }
        }

        private void guardar() {
            try {
                Path parent = ruta.getParent();
                if (parent != null && !Files.exists(parent)) {
                    Files.createDirectories(parent);
                }
                Files.writeString(ruta, area.getText(), StandardCharsets.UTF_8);
                JOptionPane.showMessageDialog(this,
                        "Guardado en:\n" + ruta,
                        "Éxito", JOptionPane.INFORMATION_MESSAGE);
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this,
                        "Error al guardar:\n" + ex.getMessage(),
                        "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    // === MAIN para ejecutar directamente ===
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            JFrame.setDefaultLookAndFeelDecorated(true);
            JFrame f = new JFrame("Agenda: trabajo / casa / escuela");
            f.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
            f.setContentPane(new Tarea16());
            f.setBounds(100, 100, 640, 520);
            f.setVisible(true);
        });
    }
}
