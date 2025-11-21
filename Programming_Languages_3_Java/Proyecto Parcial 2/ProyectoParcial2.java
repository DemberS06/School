import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.border.EmptyBorder;
import javax.swing.filechooser.FileNameExtensionFilter;

public class ProyectoParcial2 extends JFrame {
    private static final String FILE_PATH =
            "C:\\Users\\Dembe\\Proyects\\School\\Programming_Languages_3_Java\\Proyecto Parcial 2\\archModificado.txt";

    private final Map<Integer, Person> datos = new TreeMap<>();

    // Campos visibles (sin etiquetas)
    private final JTextField t1 = new JTextField(); // campo pequeño para id (ENTER busca)
    private final JTextField t2 = new JTextField(); // campo grande para mostrar info o "No existe"
    private final JButton borrarBtn = new JButton("Borrar");

    // --- Nuevos componentes ---
    private final JButton loteBtn = new JButton("Borrar x Lote"); // botón que pediste (texto ajustado)
    private final JLabel totalLabel = new JLabel(); // etiqueta que muestra total en archModificado

    public ProyectoParcial2() {
        super("ExamenParcial2");
        cargarDatos();

        // Panel principal con dibujo de "ventana" estilo pizarra
        WindowCanvas canvas = new WindowCanvas();
        canvas.setLayout(null);
        canvas.setBorder(new EmptyBorder(10,10,10,10));

        // Ajustes de apariencia de campos
        t1.setFont(new Font("SansSerif", Font.PLAIN, 20));
        t2.setFont(new Font("SansSerif", Font.PLAIN, 20));
        t2.setEditable(false);
        t2.setText("No existe");

        // ====== POSICIONES AJUSTADAS SEGÚN TU IMAGEN ======
        // Barra superior: t1 pequeño a la izquierda, t2 ancho a la derecha
        t1.setBounds(40, 18, 60, 36);        // pequeño cuadro arriba-izquierda ("4" en tu dibujo)
        t2.setBounds(120, 18, 560, 36);      // caja ancha arriba-derecha

        // Botones debajo: Borrar a la izquierda, Lote a la derecha (alineados horizontalmente)
        borrarBtn.setBounds(40, 90, 120, 40);     // botón debajo del t1
        loteBtn.setBounds(180, 90, 160, 40);      // botón "Borrar x Lote" a la derecha del borrar

        // Label total en la parte inferior izquierda (como tu boceto)
        totalLabel.setBounds(40, 330, 240, 30);
        totalLabel.setFont(new Font("SansSerif", Font.BOLD, 16));
        totalLabel.setForeground(Color.DARK_GRAY);
        totalLabel.setHorizontalAlignment(JLabel.LEFT);
        // =================================================

        // Estilos
        t1.setBorder(BorderFactory.createLineBorder(Color.DARK_GRAY, 2));
        t2.setBorder(BorderFactory.createLineBorder(Color.DARK_GRAY, 2));
        borrarBtn.setFocusable(false);
        borrarBtn.setFont(new Font("SansSerif", Font.BOLD, 14));
        loteBtn.setFocusable(false);
        loteBtn.setFont(new Font("SansSerif", Font.BOLD, 14));

        // Acciones
        t1.addActionListener(e -> buscarPorIdYActualizarT2());
        borrarBtn.addActionListener(e -> {
            borrarIdSilencioso();
            guardarDatos();
            actualizarContador();
        });

        // Acción del nuevo botón: abrir file chooser y procesar lote
        loteBtn.addActionListener(e -> {
            JFileChooser chooser = new JFileChooser();
            chooser.setDialogTitle("Selecciona archivo .txt con IDs/rangos");
            chooser.setFileFilter(new FileNameExtensionFilter("Archivos de texto (*.txt)", "txt"));
            int sel = chooser.showOpenDialog(this);
            if (sel == JFileChooser.APPROVE_OPTION) {
                File selFile = chooser.getSelectedFile();
                processBatchFile(selFile.toPath());
                // después de procesar, guardamos y actualizamos contador
                guardarDatos();
                actualizarContador();
                // actualizamos la vista
                t2.setText("No existe");
            }
        });

        // Agregar al canvas
        canvas.add(t1);
        canvas.add(t2);
        canvas.add(borrarBtn);
        canvas.add(loteBtn);
        canvas.add(totalLabel);

        // Contenedor principal
        this.getContentPane().setBackground(Color.WHITE);
        this.setContentPane(canvas);
        this.setSize(760, 420);
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);

        // Guardar al cerrar (sin preguntar)
        this.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                guardarDatos();
                dispose();
                System.exit(0);
            }
        });

        // Inicializar contador en UI
        actualizarContador();
    }

    // Busca por id en 'datos' y actualiza t2. Si no existe -> "No existe"
    private void buscarPorIdYActualizarT2() {
        String text = t1.getText().trim();
        if (text.isEmpty()) {
            t2.setText("No existe");
            return;
        }
        try {
            int id = Integer.parseInt(text);
            Person p = datos.get(id);
            if (p != null) t2.setText(p.toCsvLine());
            else t2.setText("No existe");
        } catch (NumberFormatException ex) {
            t2.setText("No existe");
        }
    }

    // Borra silenciosamente el registro cuyo id está en t1 (si existe).
    private void borrarIdSilencioso() {
        String text = t1.getText().trim();
        if (text.isEmpty()) return;
        try {
            int id = Integer.parseInt(text);
            datos.remove(id); // ya no guardamos en variable no usada
            t2.setText("No existe");
        } catch (NumberFormatException ignored) {
            // no hacer nada
        }
    }

    // Carga datos desde FILE_PATH a 'datos'
    private void cargarDatos() {
        datos.clear();
        Path p = Paths.get(FILE_PATH);
        if (!Files.exists(p)) return;
        try (BufferedReader br = Files.newBufferedReader(p, StandardCharsets.UTF_8)) {
            String line;
            while ((line = br.readLine()) != null) {
                line = line.trim();
                if (line.isEmpty()) continue;
                String[] partes = line.split(",", -1);
                if (partes.length < 5) continue;
                try {
                    int id = Integer.parseInt(partes[0].trim());
                    String nombre = partes[1].trim();
                    String edad = partes[2].trim();
                    String sexo = partes[3].trim();
                    String lugar = partes[4].trim();
                    datos.put(id, new Person(id, nombre, edad, sexo, lugar));
                } catch (NumberFormatException ex) {
                    // ignorar línea con id inválido
                }
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    // Guarda datos en FILE_PATH (sobrescribe)
    private void guardarDatos() {
        Path p = Paths.get(FILE_PATH);
        try {
            if (p.getParent() != null) Files.createDirectories(p.getParent());
            try (BufferedWriter bw = Files.newBufferedWriter(p, StandardCharsets.UTF_8,
                    StandardOpenOption.CREATE, StandardOpenOption.TRUNCATE_EXISTING)) {
                for (Person per : datos.values()) {
                    bw.write(per.toCsvLine());
                    bw.newLine();
                }
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    // Actualiza la etiqueta que muestra el total de elementos en archModificado
    private void actualizarContador() {
        totalLabel.setText("Total: " + datos.size());
    }

    // Procesa un archivo de lote con IDs y rangos separados por comas y saltos de línea.
    // Ejemplo de lineas:
    // 1276, 3456, 12034, 8765, 1-100, 15432, ...
    private void processBatchFile(Path batchPath) {
        if (batchPath == null || !Files.exists(batchPath)) return;
        String content;
        try {
            content = Files.readString(batchPath, StandardCharsets.UTF_8);
        } catch (IOException e) {
            e.printStackTrace();
            return;
        }
        // Normalizamos: separamos por comas (y consideramos fines de línea)
        String[] tokens = content.split(",");

        for (String tok : tokens) {
            if (tok == null) continue;
            String raw = tok.trim();
            if (raw.isEmpty()) continue;

            // Quitamos espacios internos para facilitar parseo de rangos como "14505 - 14509"
            String t = raw.replaceAll("\\s+", "");

            // Si tiene guion: rango
            if (t.contains("-")) {
                String[] ends = t.split("-", 2);
                if (ends.length != 2) continue;
                try {
                    int start = Integer.parseInt(ends[0]);
                    int end = Integer.parseInt(ends[1]);
                    // aseguramos orden y creamos variables finales para evitar capturas mutables
                    int a = Math.min(start, end);
                    int b = Math.max(start, end);
                    final int startVal = a;
                    final int endVal = b;

                    // Eliminamos todos los IDs en el rango [startVal, endVal] que existan en 'datos'.
                    if (datos instanceof TreeMap) {
                        TreeMap<Integer, Person> tm = (TreeMap<Integer, Person>) datos;
                        Set<Integer> ks = tm.subMap(startVal, true, endVal, true).keySet();
                        Integer[] keysToRemove = ks.toArray(new Integer[0]);
                        for (Integer k : keysToRemove) {
                            datos.remove(k);
                        }
                    } else {
                        // defensa: iterar y coleccionar
                        List<Integer> toRemove = new ArrayList<>();
                        for (Integer k : datos.keySet()) {
                            if (k >= startVal && k <= endVal) toRemove.add(k);
                        }
                        for (Integer k : toRemove) datos.remove(k);
                    }
                } catch (NumberFormatException ex) {
                    // token no parseable -> ignorar
                }
            } else {
                // número simple
                try {
                    int id = Integer.parseInt(t);
                    datos.remove(id);
                } catch (NumberFormatException ex) {
                    // ignorar token inválido
                }
            }
        }
    }

    // Clase simple para registros
    private static class Person {
        final int id;
        final String nombre;
        final String edad;
        final String sexo;
        final String lugar;

        Person(int id, String nombre, String edad, String sexo, String lugar) {
            this.id = id; this.nombre = nombre; this.edad = edad; this.sexo = sexo; this.lugar = lugar;
        }
        String toCsvLine() {
            return id + "," + nombre + "," + edad + "," + sexo + "," + lugar;
        }
    }

    // Panel que dibuja la "ventana" interior (barra superior con botones)
    private static class WindowCanvas extends JPanel {
        WindowCanvas() {
            setBackground(Color.WHITE);
        }
        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            Graphics2D g2 = (Graphics2D) g.create();
            // Si quieres un borde o barra superior dibujada, aquí va.
            g2.dispose();
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            ProyectoParcial2 app = new ProyectoParcial2();
            app.setVisible(true);
        });
    }
}
