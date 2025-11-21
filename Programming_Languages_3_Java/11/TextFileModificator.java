import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.*;
import java.util.ArrayList;
import java.util.List;
import javax.swing.*;

public class TextFileModificator extends JFrame {
    private JTextField campoTexto;
    private JButton botonLeer, botonGrabar;
    private JTextArea areaResultado;
    private String resultado = "";

    public TextFileModificator() {
        setTitle("Leer y Grabar");
        setSize(1000, 600);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new BorderLayout(8, 8));

        JPanel panelTop = new JPanel(new FlowLayout(FlowLayout.LEFT, 8, 8));
        panelTop.add(new JLabel("Ingresa texto con comas:"));
        campoTexto = new JTextField(25);
        panelTop.add(campoTexto);

        botonLeer = new JButton("Leer");
        botonGrabar = new JButton("Grabar");
        panelTop.add(botonLeer);
        panelTop.add(botonGrabar);

        add(panelTop, BorderLayout.NORTH);

        areaResultado = new JTextArea();
        areaResultado.setEditable(false);
        areaResultado.setLineWrap(true);
        areaResultado.setWrapStyleWord(true);
        JScrollPane scroll = new JScrollPane(areaResultado);
        add(scroll, BorderLayout.CENTER);

        // Acción del botón Leer
        botonLeer.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String input = campoTexto.getText();
                resultado = procesa(input);
                areaResultado.setText(resultado);
                if (resultado.isEmpty()) {
                    JOptionPane.showMessageDialog(TextFileModificator.this, "No se encontró contenido para procesar.");
                } else {
                    JOptionPane.showMessageDialog(TextFileModificator.this, "Texto procesado y mostrado en el área.");
                }
            }
        });

        // Acción del botón Grabar
        botonGrabar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (resultado == null || resultado.isEmpty()) {
                    JOptionPane.showMessageDialog(TextFileModificator.this, "Primero presiona Leer para generar el texto.");
                    return;
                }

                Path ruta = Paths.get("11\\salida.txt");
                try (BufferedWriter bw = Files.newBufferedWriter(ruta,
                        StandardCharsets.UTF_8,
                        StandardOpenOption.CREATE, StandardOpenOption.APPEND)) {
                    bw.write(resultado);
                    bw.newLine();
                    JOptionPane.showMessageDialog(TextFileModificator.this, "Texto grabado en salida.txt");
                } catch (IOException ex) {
                    JOptionPane.showMessageDialog(TextFileModificator.this, "Error al escribir archivo: " + ex.getMessage());
                }
            }
        });
    }

    private String procesa(String input) {
        if (input == null) return "";

        int n = input.length();
        List<String> partes = new ArrayList<>();

        int i = 0;
        while (i < n && Character.isWhitespace(input.charAt(i))) i++;
        if (i < n) {
            partes.add(String.valueOf(input.charAt(i)));
        }

        for (int j = 0; j < n; j++) {
            if (input.charAt(j) == ',') {
                int k = j + 1;
                while (k < n && Character.isWhitespace(input.charAt(k))) k++;
                if (k < n) {
                    partes.add(String.valueOf(input.charAt(k)));
                }
            }
        }

        // Unir sin separadores
        return String.join("", partes);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            TextFileModificator v = new TextFileModificator();
            v.setLocationRelativeTo(null);
            v.setVisible(true);
        });
    }
}
