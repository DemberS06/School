import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.Map;
import java.util.TreeMap;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.border.EmptyBorder;

/**
 * ExamenParcial2 - Versión con apariencia personalizada similar a la imagen.
 *
 * - Lee/escribe el archivo en FILE_PATH.
 * - No muestra labels.
 * - T2 inicia con "No existe".
 * - Al presionar ENTER en el campo superior izquierdo (T1) busca por id.
 * - Botón "Borrar" elimina silenciosamente el registro correspondiente al id en T1
 *   y actualiza T2 a "No existe" (sin mensajes emergentes).
 */
public class ExamenParcial2 extends JFrame {
    private static final String FILE_PATH =
            "C:\\Users\\Dembe\\Proyects\\School\\Java\\Examen Parcial 2\\archModificado.txt";

    private final Map<Integer, Person> datos = new TreeMap<>();

    // Campos visibles (sin etiquetas)
    private final JTextField t1 = new JTextField(); // campo pequeño para id (ENTER busca)
    private final JTextField t2 = new JTextField(); // campo grande para mostrar info o "No existe"
    private final JButton borrarBtn = new JButton("Borrar");

    public ExamenParcial2() {
        super("ExamenParcial2");
        cargarDatos();

        // Panel principal con dibujo de "ventana" estilo pizarra
        WindowCanvas canvas = new WindowCanvas();
        canvas.setLayout(null);
        canvas.setBorder(new EmptyBorder(10,10,10,10));

        // Ajustes de apariencia de campos
        t1.setFont(new Font("SansSerif", Font.PLAIN, 18));
        t2.setFont(new Font("SansSerif", Font.PLAIN, 18));
        t2.setEditable(false);
        t2.setText("No existe");

        // Establecemos tamaños y posición (aprox. como la imagen)
        t1.setBounds(40, 60, 110, 36);       // pequeño cuadro a la izquierda
        t2.setBounds(220, 50, 420, 40);      // caja ancha arriba-derecha
        borrarBtn.setBounds(40, 130, 110, 36); // botón debajo del T1

        // Opcional: estilo simple para los campos (borde parecido a cajas)
        t1.setBorder(BorderFactory.createLineBorder(Color.DARK_GRAY, 2));
        t2.setBorder(BorderFactory.createLineBorder(Color.DARK_GRAY, 2));
        borrarBtn.setFocusable(false);
        borrarBtn.setFont(new Font("SansSerif", Font.BOLD, 14));

        // Acciones
        t1.addActionListener(e -> buscarPorIdYActualizarT2());
        borrarBtn.addActionListener(e -> borrarIdSilencioso());

        // Agregar al canvas
        canvas.add(t1);
        canvas.add(t2);
        canvas.add(borrarBtn);

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
            Person removed = datos.remove(id);
            // No mostrar mensajes; sólo actualizar interfaz
            t2.setText("No existe");
        } catch (NumberFormatException ignored) {
            // no hacer nada
        }
    }

    // Carga datos desde FILE_PATH a 'datos'
    private void cargarDatos() {
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
            // no mostramos cuadros; solo imprimimos en consola (silencioso)
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
            /*
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

            // Dibujar un rectángulo grande (simula la ventana dibujada en la pizarra)
            int margin = 18;
            int x = margin, y = margin, w = getWidth() - margin * 2, h = getHeight() - margin * 2;
            // sombra sutil
            g2.setColor(new Color(0,0,0,20));
            g2.fillRoundRect(x+6, y+6, w, h, 14, 14);

            // cuerpo
            g2.setColor(new Color(245, 245, 245));
            g2.fillRoundRect(x, y, w, h, 14, 14);

            // borde
            g2.setColor(new Color(150,150,150));
            g2.setStroke(new BasicStroke(2f));
            g2.drawRoundRect(x, y, w, h, 14, 14);

            // barra superior (como la cabecera de una ventana)
            int barH = 34;
            g2.setColor(new Color(230,230,230));
            g2.fillRoundRect(x, y, w, barH, 14, 14);
            // línea divisoria de la barra
            g2.setColor(new Color(200,200,200));
            g2.drawLine(x, y + barH, x + w, y + barH);

            // "botoncitos" en la esquina superior derecha (minimizar / cerrar estilo dibujo)
            int bx = x + w - 12 - 24;
            int by = y + 7;
            g2.setColor(new Color(220,80,80));
            g2.fillOval(bx, by, 14, 14); // rojo
            g2.setColor(new Color(245,200,50));
            g2.fillOval(bx + 20, by, 14, 14); // amarillo
            g2.setColor(new Color(100,200,100));
            g2.fillOval(bx + 40, by, 14, 14); // verde

            g2.dispose();*/
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            ExamenParcial2 app = new ExamenParcial2();
            app.setVisible(true);
        });
    }
}
