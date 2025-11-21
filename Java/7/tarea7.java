import java.awt.*;
import java.text.Normalizer;
import java.util.Random;
import javax.swing.*;

public class tarea7 {

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            JFrame f = new JFrame("Palíndromo & Adivina el número");
            f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            f.setSize(500, 300);
            f.setLocationRelativeTo(null);

            JTabbedPane tabs = new JTabbedPane();
            tabs.addTab("Palíndromo", new PanelPalindromo());
            tabs.addTab("Adivina (3 intentos)", new PanelAdivina());

            f.setContentPane(tabs);
            f.setVisible(true);
        });
    }

    /* ========== Pestaña 1: Palíndromo ========== */
    static class PanelPalindromo extends JPanel {
        private final JTextField t1 = new JTextField(25);
        private final JButton btn = new JButton("¿Es palíndromo?");
        private final JLabel e2 = new JLabel("Escribe una palabra o frase…");

        PanelPalindromo() {
            setLayout(new BorderLayout(10, 10));
            JPanel top = new JPanel(new FlowLayout(FlowLayout.LEFT));
            top.add(new JLabel("Texto:"));
            top.add(t1);
            top.add(btn);

            add(top, BorderLayout.NORTH);
            e2.setFont(e2.getFont().deriveFont(Font.BOLD));
            add(e2, BorderLayout.CENTER);

            // actionPerformed
            btn.addActionListener(e -> {
                String original = t1.getText();
                String normal = normalizar(original);
                String invertida = new StringBuilder(normal).reverse().toString();

                if (normal.equals(invertida)) {
                    e2.setText("✔ Es palíndromo");
                } else {
                    e2.setText("✘ No es palíndromo");
                }
            });
        }

        // Opcional: ignora mayúsculas, acentos y espacios/puntuación
        private String normalizar(String s) {
            if (s == null) return "";
            String noAcentos = Normalizer.normalize(s, Normalizer.Form.NFD)
                    .replaceAll("\\p{M}+", "");              // quita acentos
            String soloAlfanum = noAcentos
                    .replaceAll("[^A-Za-z0-9]", "")          // quita no alfanuméricos
                    .toLowerCase();
            return soloAlfanum;
        }
    }

    /* ========== Pestaña 2: Adivina el número (3 intentos) ========== */
    static class PanelAdivina extends JPanel {
        private final JTextField t1 = new JTextField(10);
        private final JButton btnProbar = new JButton("Probar");
        private final JButton btnReiniciar = new JButton("Reiniciar");
        private final JLabel estado = new JLabel("Adivina un número del 0 al 9. Tienes 3 intentos.");
        private int intentos;
        private int numSecreto;
        private final Random rnd = new Random();

        PanelAdivina() {
            setLayout(new BorderLayout(10, 10));

            JPanel fila = new JPanel(new FlowLayout(FlowLayout.LEFT));
            fila.add(new JLabel("Tu número:"));
            fila.add(t1);
            fila.add(btnProbar);
            fila.add(btnReiniciar);

            add(fila, BorderLayout.NORTH);
            estado.setFont(estado.getFont().deriveFont(Font.BOLD));
            add(estado, BorderLayout.CENTER);

            inicializar();

            // actionPerformed del botón Probar
            btnProbar.addActionListener(e -> {
                String texto = t1.getText().trim();
                int b;
                try {
                    b = Integer.parseInt(texto);
                } catch (NumberFormatException ex) {
                    estado.setText("Ingresa un número entero válido (0–9).");
                    return;
                }
                if (b < 0 || b > 9) {
                    estado.setText("Debe ser entre 0 y 9.");
                    return;
                }

                if (b == numSecreto) {
                    estado.setText("🎉 ¡Ganaste! El número era " + numSecreto + ".");
                    deshabilitarEntrada();
                    return;
                }

                intentos--;
                if (intentos > 0) {
                    String pista = (b < numSecreto) ? "Mayor" : "Menor";
                    estado.setText("Fallaste. Pista: " + pista + ". Te quedan " + intentos + " intentos.");
                } else {
                    estado.setText("❌ Sin intentos. El número era " + numSecreto + ".");
                    deshabilitarEntrada();
                }
            });

            // Reiniciar el juego
            btnReiniciar.addActionListener(e -> inicializar());
        }

        private void inicializar() {
            numSecreto = rnd.nextInt(10); // 0–9
            intentos = 3;
            estado.setText("Adivina un número del 0 al 9. Tienes 3 intentos.");
            t1.setText("");
            t1.setEnabled(true);           // habilitar campo
            btnProbar.setEnabled(true);    // habilitar botón
            t1.requestFocusInWindow();
        }

        private void deshabilitarEntrada() {
            // (en Swing se usa setEnabled, no setDisable)
            t1.setEnabled(false);
            btnProbar.setEnabled(false);
        }
    }
}