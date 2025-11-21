import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;

public class Tarea17 extends JFrame {

    private final JButton btnArchivo1 = new JButton("Seleccionar archivo 1");
    private final JButton btnArchivo2 = new JButton("Seleccionar archivo 2");
    private final JLabel lblResultado = new JLabel("Selecciona ambos archivos…", SwingConstants.CENTER);
    private final JLabel lblA1 = new JLabel("Archivo 1: (ninguno)");
    private final JLabel lblA2 = new JLabel("Archivo 2: (ninguno)");

    private Path path1 = null;
    private Path path2 = null;

    public Tarea17() {
        super("Comparador línea por línea");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(700, 220);
        setLocationRelativeTo(null);

        // Layout: botones a los lados y resultado al centro
        JPanel top = new JPanel(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();
        c.insets = new Insets(8, 8, 8, 8);
        c.fill = GridBagConstraints.HORIZONTAL;

        c.gridx = 0; c.gridy = 0; c.weightx = 0.25;
        top.add(btnArchivo1, c);

        c.gridx = 1; c.gridy = 0; c.weightx = 0.5;
        lblResultado.setFont(lblResultado.getFont().deriveFont(Font.BOLD, 18f));
        top.add(lblResultado, c);

        c.gridx = 2; c.gridy = 0; c.weightx = 0.25;
        top.add(btnArchivo2, c);

        JPanel bottom = new JPanel(new GridLayout(2, 1, 8, 4));
        bottom.setBorder(BorderFactory.createEmptyBorder(0, 16, 8, 16));
        bottom.add(lblA1);
        bottom.add(lblA2);

        getContentPane().setLayout(new BorderLayout(8, 8));
        getContentPane().add(top, BorderLayout.CENTER);
        getContentPane().add(bottom, BorderLayout.SOUTH);

        // Acciones
        btnArchivo1.addActionListener(e -> seleccionarArchivo(1));
        btnArchivo2.addActionListener(e -> seleccionarArchivo(2));
    }

    private void seleccionarArchivo(int cual) {
        JFileChooser fc = new JFileChooser();
        int r = fc.showOpenDialog(this);
        if (r == JFileChooser.APPROVE_OPTION) {
            if (cual == 1) {
                path1 = fc.getSelectedFile().toPath();
                lblA1.setText("Archivo 1: " + path1.toString());
            } else {
                path2 = fc.getSelectedFile().toPath();
                lblA2.setText("Archivo 2: " + path2.toString());
            }
            intentarComparar();
        }
    }

    private void intentarComparar() {
        if (path1 == null || path2 == null) return;
        try {
            ResultadoComparacion res = compararArchivosLineaPorLinea(path1, path2);
            if (res.identicos) {
                lblResultado.setText("100% iguales");
            } else {
                lblResultado.setText(String.format("%.2f %% iguales", res.porcentaje));
            }
        } catch (IOException ex) {
            lblResultado.setText("Error leyendo archivos");
            JOptionPane.showMessageDialog(this,
                    "No se pudieron leer los archivos:\n" + ex.getMessage(),
                    "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    /**
     * Compara línea por línea (misma posición). Si ambas listas tienen diferente tamaño,
     * se divide entre el máximo de líneas para el porcentaje.
     * Igualdad exacta de cadena (no se hace trim).
     */
    private ResultadoComparacion compararArchivosLineaPorLinea(Path a, Path b) throws IOException {
        // Cargar en ArrayList (cumple el requisito de ArrayList/Collection)
        List<String> l1 = new ArrayList<>(Files.readAllLines(a, StandardCharsets.UTF_8));
        List<String> l2 = new ArrayList<>(Files.readAllLines(b, StandardCharsets.UTF_8));

        int n1 = l1.size();
        int n2 = l2.size();
        int max = Math.max(n1, n2);

        // Caso especial: ambos vacíos
        if (max == 0) {
            return new ResultadoComparacion(true, 100.0);
        }

        int min = Math.min(n1, n2);
        int iguales = 0;

        for (int i = 0; i < min; i++) {
            if (l1.get(i).equals(l2.get(i))) {
                iguales++;
            }
        }
        // Líneas extra en el más largo cuentan como no iguales

        boolean identicos = (n1 == n2) && (iguales == n1);
        double porcentaje = (iguales * 100.0) / max;

        return new ResultadoComparacion(identicos, porcentaje);
    }

    private static class ResultadoComparacion {
        final boolean identicos;
        final double porcentaje;
        ResultadoComparacion(boolean identicos, double porcentaje) {
            this.identicos = identicos;
            this.porcentaje = porcentaje;
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new Tarea17().setVisible(true));
    }
}
