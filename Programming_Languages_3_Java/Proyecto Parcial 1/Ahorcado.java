import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

public class Ahorcado extends JFrame {
    private static final int DEFAULT_MAX_INTENTOS = 6;
    private int maxIntentos = DEFAULT_MAX_INTENTOS;

    // Colores por defecto (puedes cambiarlos aquí si prefieres)
    private static final Color DEFAULT_BACKGROUND = Color.BLACK;
    private static final Color DEFAULT_TEXT = Color.WHITE;

    private Color backgroundColor = DEFAULT_BACKGROUND;
    private Color textColor = DEFAULT_TEXT;

    private String palabraSecreta;
    private char[] progreso;
    private Set<Character> letrasUsadas;
    private int intentos;

    private JLabel imagenLabel;
    private JLabel progresoLabel;
    private JLabel infoLabel;
    private JTextField letraField;
    private JButton adivinarButton;

    // Rutas detectadas
    private Path imagesDir = null;
    private Path diccionarioPath = null;

    public Ahorcado() {
        super("Ahorcado");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // Aplica color al content pane ya desde ahora
        getContentPane().setBackground(backgroundColor);

        // Debug working dir
        System.out.println("Working dir = " + Paths.get("").toAbsolutePath());

        // Detectar carpeta images recursivamente y ajustar maxIntentos si encuentra imágenes
        imagesDir = encontrarCarpetaImages();
        if (imagesDir != null) {
            System.out.println("images dir encontrado en: " + imagesDir.toAbsolutePath());
            maxIntentos = detectarMaxIntentos(imagesDir);
            System.out.println("maxIntentos detectado = " + maxIntentos);
            try (DirectoryStream<Path> ds = Files.newDirectoryStream(imagesDir, "*.{jpg,JPG,jpeg,JPEG}")) {
                for (Path p : ds) System.out.println("images: " + p.getFileName());
            } catch (IOException ignored) {}
        } else {
            System.err.println("No se encontró carpeta 'images'. Se usará DEFAULT_MAX_INTENTOS = " + DEFAULT_MAX_INTENTOS);
        }

        // Detectar diccionario (busca 'diccionario.txt' recursivamente)
        diccionarioPath = encontrarDiccionario();
        if (diccionarioPath != null) {
            System.out.println("diccionario.txt encontrado en: " + diccionarioPath.toAbsolutePath());
        } else {
            System.err.println("No se encontró diccionario.txt en el árbol del proyecto. Coloca el archivo en el working dir o en alguna subcarpeta.");
        }

        // Inicializar estado del juego (palabra cargada)
        palabraSecreta = cargarPalabra();
        progreso = new char[palabraSecreta.length()];
        for (int i = 0; i < progreso.length; i++) {
            int cp = palabraSecreta.codePointAt(i);
            progreso[i] = (Character.isLetter(cp) ? '_' : palabraSecreta.charAt(i));
        }
        letrasUsadas = new HashSet<>();
        intentos = 0;

        // UI: imagen arriba
        imagenLabel = new JLabel("", SwingConstants.CENTER);
        imagenLabel.setFont(new Font("SansSerif", Font.PLAIN, 12));
        imagenLabel.setOpaque(false);
        add(imagenLabel, BorderLayout.NORTH);
        actualizarImagen(); // intentos = 0

        // UI: progreso en el centro
        progresoLabel = new JLabel(getProgresoString(), SwingConstants.CENTER);
        progresoLabel.setFont(new Font("Monospaced", Font.BOLD, 28));
        progresoLabel.setOpaque(true);
        progresoLabel.setBackground(backgroundColor);
        progresoLabel.setForeground(textColor);
        add(progresoLabel, BorderLayout.CENTER);

        // Panel inferior: input + botón
        JPanel inputPanel = new JPanel(new FlowLayout());
        inputPanel.setBackground(backgroundColor);

        letraField = new JTextField(2);
        letraField.setFont(new Font("Monospaced", Font.PLAIN, 18));
        letraField.setForeground(textColor);
        // usar un fondo ligeramente distinto para el campo para distinguirlo
        letraField.setBackground(backgroundColor.brighter());
        letraField.setOpaque(true);

        adivinarButton = new JButton("Adivinar");
        adivinarButton.setEnabled(false);
        adivinarButton.setForeground(textColor);
        adivinarButton.setBackground(backgroundColor.darker());
        adivinarButton.setOpaque(true);

        // Asegurar que labels usan el color de texto
        JLabel lblLetra = new JLabel("Letra:");
        lblLetra.setForeground(textColor);
        lblLetra.setBackground(backgroundColor);
        lblLetra.setOpaque(true);

        // Habilitar el botón solo cuando hay exactamente un punto de código y es letra Unicode
        letraField.getDocument().addDocumentListener(new DocumentListener() {
            private void update() {
                String t = letraField.getText().trim();
                boolean ok = false;
                if (!t.isEmpty()) {
                    int codePoints = t.codePointCount(0, t.length());
                    if (codePoints == 1) {
                        int cp = t.codePointAt(0);
                        ok = Character.isLetter(cp);
                    }
                }
                adivinarButton.setEnabled(ok);
            }
            public void insertUpdate(DocumentEvent e) { update(); }
            public void removeUpdate(DocumentEvent e) { update(); }
            public void changedUpdate(DocumentEvent e) { update(); }
        });

        letraField.addActionListener(e -> { if (adivinarButton.isEnabled()) procesarLetra(); });
        adivinarButton.addActionListener(e -> procesarLetra());

        inputPanel.add(lblLetra);
        inputPanel.add(letraField);
        inputPanel.add(adivinarButton);

        add(inputPanel, BorderLayout.SOUTH);

        // Panel lateral / info con intentos restantes
        infoLabel = new JLabel("Intentos restantes: " + (maxIntentos - intentos), SwingConstants.CENTER);
        infoLabel.setFont(new Font("SansSerif", Font.PLAIN, 14));
        infoLabel.setForeground(textColor);
        infoLabel.setBackground(backgroundColor);
        infoLabel.setOpaque(true);
        add(infoLabel, BorderLayout.WEST);

        // Si la imagen label muestra texto (fallback), darle el color de texto
        imagenLabel.setForeground(textColor);

        pack();
        setSize(600, 500);
        setLocationRelativeTo(null);
        setVisible(true);
    }

    // ----------------- Métodos de detección -----------------

    private Path encontrarCarpetaImages() {
        Path inicio = Paths.get("").toAbsolutePath();
        final int MAX_DEPTH = 6;
        try (Stream<Path> stream = Files.find(inicio, MAX_DEPTH,
                (path, attr) -> attr.isDirectory() && path.getFileName().toString().equalsIgnoreCase("images"))) {
            return stream.findFirst().orElse(null);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    private int detectarMaxIntentos(Path dir) {
        Pattern p = Pattern.compile("^ahorcado_{1,2}(\\d{2})\\.(jpg|jpeg)$", Pattern.CASE_INSENSITIVE);
        int max = -1;
        try (DirectoryStream<Path> ds = Files.newDirectoryStream(dir, "*.{jpg,JPG,jpeg,JPEG}")) {
            for (Path entry : ds) {
                String name = entry.getFileName().toString();
                Matcher m = p.matcher(name);
                if (m.matches()) {
                    try {
                        int v = Integer.parseInt(m.group(1));
                        if (v > max) max = v;
                    } catch (NumberFormatException ignored) {}
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return max >= 0 ? max : DEFAULT_MAX_INTENTOS;
    }

    private Path encontrarDiccionario() {
        Path inicio = Paths.get("").toAbsolutePath();
        final int MAX_DEPTH = 6;
        try (Stream<Path> stream = Files.find(inicio, MAX_DEPTH,
                (path, attr) -> attr.isRegularFile() && path.getFileName().toString().equalsIgnoreCase("diccionario.txt"))) {
            return stream.findFirst().orElse(null);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    // ----------------- Carga de palabra -----------------

    private String cargarPalabra() {
        Path ruta = diccionarioPath != null ? diccionarioPath : Paths.get("diccionario.txt");
        if (!Files.exists(ruta)) {
            String msg = "No se encontró diccionario.txt.\nBuscado en: " + ruta.toAbsolutePath() +
                         "\nAsegúrate de que exista y tenga al menos una palabra por línea.";
            JOptionPane.showMessageDialog(this, msg, "diccionario.txt no encontrado", JOptionPane.ERROR_MESSAGE);
            System.err.println(msg);
            System.exit(1);
        }

        try {
            List<String> lineas = Files.readAllLines(ruta)
                    .stream()
                    .map(String::trim)
                    .filter(s -> !s.isEmpty())
                    .collect(Collectors.toList());

            if (lineas.isEmpty()) {
                JOptionPane.showMessageDialog(this, "El diccionario está vacío.", "Error", JOptionPane.ERROR_MESSAGE);
                System.exit(1);
            }

            Random r = new Random();
            return lineas.get(r.nextInt(lineas.size()));
        } catch (IOException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error leyendo diccionario.txt:\n" + e.getMessage(),
                    "Error", JOptionPane.ERROR_MESSAGE);
            System.exit(1);
            return "";
        }
    }

    // ----------------- Imagenes -----------------

    private void actualizarImagen() {
        if (imagesDir == null) {
            imagenLabel.setIcon(null);
            imagenLabel.setText("[images/ no encontrada]");
            imagenLabel.setForeground(textColor);
            return;
        }

        String nombre2 = String.format("ahorcado__%02d.jpg", intentos);
        String nombre1 = String.format("ahorcado_%02d.jpg", intentos);

        Path ruta2 = imagesDir.resolve(nombre2);
        Path ruta1 = imagesDir.resolve(nombre1);

        Path ruta = null;
        if (Files.exists(ruta2)) ruta = ruta2;
        else if (Files.exists(ruta1)) ruta = ruta1;
        else {
            try (DirectoryStream<Path> ds = Files.newDirectoryStream(imagesDir, "*"+String.format("%02d", intentos)+".*")) {
                for (Path e : ds) { ruta = e; break; }
            } catch (IOException ignored) {}
        }

        System.out.println("Intentando cargar imagen para intento=" + intentos + " -> " +
                (ruta == null ? ("no encontrada ("+nombre2+" / "+nombre1+")") : ruta.toAbsolutePath()));

        if (ruta == null || !Files.exists(ruta)) {
            imagenLabel.setIcon(null);
            imagenLabel.setText("[Falta: " + nombre2 + " ó " + nombre1 + "]");
            imagenLabel.setForeground(textColor);
            imagenLabel.revalidate();
            imagenLabel.repaint();
            return;
        }

        try {
            BufferedImage bimg = ImageIO.read(ruta.toFile());
            if (bimg == null) {
                imagenLabel.setIcon(null);
                imagenLabel.setText("[Imagen inválida]");
                imagenLabel.setForeground(textColor);
                System.err.println("ImageIO no pudo leer: " + ruta.toAbsolutePath());
                return;
            }
            Image scaled = bimg.getScaledInstance(300, 300, Image.SCALE_SMOOTH);
            imagenLabel.setIcon(new ImageIcon(scaled));
            imagenLabel.setText(null);
            imagenLabel.setForeground(textColor);
            imagenLabel.revalidate();
            imagenLabel.repaint();
        } catch (IOException ex) {
            ex.printStackTrace();
            imagenLabel.setIcon(null);
            imagenLabel.setText("[Error cargando imagen]");
            imagenLabel.setForeground(textColor);
        }
    }

    // ----------------- Lógica del juego -----------------

    private void procesarLetra() {
        String input = letraField.getText().trim();
        if (input.isEmpty()) return;
        int cp = input.codePointAt(0);
        String letraStr = new String(Character.toChars(cp));
        letraField.setText("");

        if (!Character.isLetter(cp)) {
            JOptionPane.showMessageDialog(this, "Introduce una letra válida (incluye acentos y ñ).", "Letra inválida", JOptionPane.WARNING_MESSAGE);
            return;
        }

        char keyForSet = Character.toUpperCase((char)cp);
        if (letrasUsadas.contains(keyForSet)) {
            JOptionPane.showMessageDialog(this, "Ya usaste esa letra: " + letraStr, "Aviso", JOptionPane.WARNING_MESSAGE);
            return;
        }
        letrasUsadas.add(keyForSet);

        boolean acierto = false;
        for (int i = 0; i < palabraSecreta.length(); i++) {
            String ch = palabraSecreta.substring(i, i+1);
            if (ch.equalsIgnoreCase(letraStr)) {
                progreso[i] = ch.toUpperCase().charAt(0);
                acierto = true;
            }
        }

        if (!acierto) {
            intentos++;
            if (intentos > maxIntentos) intentos = maxIntentos;
            actualizarImagen();
        }

        progresoLabel.setText(getProgresoString());
        infoLabel.setText("Intentos restantes: " + Math.max(0, (maxIntentos - intentos)));

        if (gano()) {
            JOptionPane.showMessageDialog(this, "¡Ganaste! La palabra era: " + palabraSecreta);
            reiniciarJuego();
        } else if (perdio()) {
            JOptionPane.showMessageDialog(this, "Perdiste. La palabra era: " + palabraSecreta);
            reiniciarJuego();
        }
    }

    private boolean gano() {
        for (char c : progreso) if (c == '_') return false;
        return true;
    }

    private boolean perdio() {
        return intentos >= maxIntentos;
    }

    private void reiniciarJuego() {
        palabraSecreta = cargarPalabra();
        progreso = new char[palabraSecreta.length()];
        for (int i = 0; i < progreso.length; i++) {
            int cp = palabraSecreta.codePointAt(i);
            progreso[i] = (Character.isLetter(cp) ? '_' : palabraSecreta.charAt(i));
        }
        letrasUsadas.clear();
        intentos = 0;
        actualizarImagen();
        progresoLabel.setText(getProgresoString());
        infoLabel.setText("Intentos restantes: " + (maxIntentos - intentos));
    }

    private String getProgresoString() {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < progreso.length; i++) {
            sb.append(progreso[i]);
            if (i < progreso.length - 1) sb.append(' ');
        }
        return sb.toString();
    }

    // ----------------- main -----------------
    public static void main(String[] args) {
        SwingUtilities.invokeLater(Ahorcado::new);
    }
}
