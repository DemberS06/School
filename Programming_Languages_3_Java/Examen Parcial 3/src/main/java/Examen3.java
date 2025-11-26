import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
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
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;

public class Examen3 extends JFrame {

    // ==== DATOS DE CONEXIÓN A LA BASE DE DATOS (AJÚSTALOS A TU ENTORNO) ====
    // Cambia "mi_base" por el nombre real de tu BD
    private static final String DB_URL = "jdbc:mysql://localhost:3306/mi_base";
    private static final String DB_USER = "root";
    private static final String DB_PASSWORD = "";

    // Tabla esperada: personas(id INT, name VARCHAR, age INT, sex VARCHAR, address VARCHAR)

    // ==== ARCHIVOS SELECCIONADOS (PARA COMPARAR) ====
    private File archivo1;
    private File archivo2;

    private JTextArea txtResultados;

    public Examen3() {
        super("Examen 3 - Comparar archivos y subir a BD");

        // Tamaño de ventana un poco más grande
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1000, 600);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout(10, 10));

        // ==== FUENTES PERSONALIZADAS ====
        Font tituloFont = new Font("Arial", Font.BOLD, 20);
        Font botonFont  = new Font("Arial", Font.BOLD, 14);
        Font textFont   = new Font("Consolas", Font.PLAIN, 13);

        // Panel izquierdo: SOLO un botón -> Subir archivo 1
        JPanel panelIzquierda = new JPanel();
        panelIzquierda.setLayout(new BoxLayout(panelIzquierda, BoxLayout.Y_AXIS));
        panelIzquierda.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        JButton btnSubir1 = new JButton("Subir archivo 1 a BD");
        btnSubir1.setFont(botonFont);
        btnSubir1.setAlignmentX(Component.CENTER_ALIGNMENT);
        btnSubir1.setPreferredSize(new Dimension(200, 40));
        btnSubir1.setMaximumSize(new Dimension(220, 45));

        panelIzquierda.add(Box.createVerticalGlue());
        panelIzquierda.add(btnSubir1);
        panelIzquierda.add(Box.createVerticalGlue());

        // Panel derecho: SOLO un botón -> Subir archivo 2
        JPanel panelDerecha = new JPanel();
        panelDerecha.setLayout(new BoxLayout(panelDerecha, BoxLayout.Y_AXIS));
        panelDerecha.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        JButton btnSubir2 = new JButton("Subir archivo 2 a BD");
        btnSubir2.setFont(botonFont);
        btnSubir2.setAlignmentX(Component.CENTER_ALIGNMENT);
        btnSubir2.setPreferredSize(new Dimension(200, 40));
        btnSubir2.setMaximumSize(new Dimension(220, 45));

        panelDerecha.add(Box.createVerticalGlue());
        panelDerecha.add(btnSubir2);
        panelDerecha.add(Box.createVerticalGlue());

        // Panel central (ventana de resultados)
        JPanel panelCentro = new JPanel(new BorderLayout(10, 10));
        panelCentro.setBorder(BorderFactory.createEmptyBorder(15, 10, 15, 10));

        JLabel lblTituloResultados = new JLabel("Coincidencias entre los dos archivos", SwingConstants.CENTER);
        lblTituloResultados.setFont(tituloFont);

        txtResultados = new JTextArea();
        txtResultados.setEditable(false);
        txtResultados.setFont(textFont);
        txtResultados.setMargin(new Insets(10, 10, 10, 10));
        JScrollPane scroll = new JScrollPane(txtResultados);
        scroll.setPreferredSize(new Dimension(600, 400));

        panelCentro.add(lblTituloResultados, BorderLayout.NORTH);
        panelCentro.add(scroll, BorderLayout.CENTER);

        add(panelIzquierda, BorderLayout.WEST);
        add(panelDerecha, BorderLayout.EAST);
        add(panelCentro, BorderLayout.CENTER);

        // ==== ACCIONES ====

        // Botón: seleccionar archivo 1, subir a BD y comparar
        btnSubir1.addActionListener((ActionEvent e) -> {
            JFileChooser fc = new JFileChooser();
            int res = fc.showOpenDialog(Examen3.this);
            if (res == JFileChooser.APPROVE_OPTION) {
                archivo1 = fc.getSelectedFile();
                // archivo1 tiene encabezado
                subirArchivoABaseDeDatos(archivo1, false);
                actualizarComparacion();
            }
        });

        // Botón: seleccionar archivo 2, subir a BD y comparar
        btnSubir2.addActionListener((ActionEvent e) -> {
            JFileChooser fc = new JFileChooser();
            int res = fc.showOpenDialog(Examen3.this);
            if (res == JFileChooser.APPROVE_OPTION) {
                archivo2 = fc.getSelectedFile();
                // archivo2 NO tiene encabezado
                subirArchivoABaseDeDatos(archivo2, false);
                actualizarComparacion();
            }
        });
    }

    // ==== COMPARAR ARCHIVOS Y ACTUALIZAR VENTANA CENTRAL ====
    private void actualizarComparacion() {
        if (archivo1 == null || archivo2 == null) {
            txtResultados.setText("Sube ambos archivos para ver las coincidencias.");
            return;
        }

        try {
            // archivo1: tiene encabezado, archivo2: no tiene encabezado
            List<Registro> lista1 = leerRegistros(archivo1, false);
            List<Registro> lista2 = leerRegistros(archivo2, false);

            Set<Registro> conjunto1 = new HashSet<>(lista1);
            List<Registro> coincidencias = new ArrayList<>();

            for (Registro r : lista2) {
                if (conjunto1.contains(r)) {
                    coincidencias.add(r);
                }
            }

            StringBuilder sb = new StringBuilder();
            sb.append("Total de filas COMPLETAMENTE iguales en ambos archivos: ")
              .append(coincidencias.size())
              .append("\n\n");

            if (coincidencias.isEmpty()) {
                sb.append("No hay filas iguales (id, name, age, sex, address todos iguales).\n");
            } else {
                sb.append("Filas iguales:\n");
                sb.append("id;name;age;sex;address\n");
                sb.append("-----------------------------------------\n");
                for (Registro r : coincidencias) {
                    sb.append(r.toLinea()).append("\n");
                }
            }

            txtResultados.setText(sb.toString());
        } catch (IOException ex) {
            txtResultados.setText("Error al leer los archivos: " + ex.getMessage());
        }
    }

    // ==== LECTURA DE ARCHIVOS ====
    private List<Registro> leerRegistros(File file, boolean tieneEncabezado) throws IOException {
        List<Registro> lista = new ArrayList<>();

        try (BufferedReader br = new BufferedReader(
                new InputStreamReader(new FileInputStream(file), StandardCharsets.UTF_8))) {

            String linea;
            boolean esPrimera = true;

            while ((linea = br.readLine()) != null) {
                linea = linea.trim();
                if (linea.isEmpty()) continue;

                if (tieneEncabezado && esPrimera) {
                    // Saltar la línea de encabezado
                    esPrimera = false;
                    continue;
                }
                esPrimera = false;

                String[] partes = linea.split(";");
                if (partes.length < 5) {
                    // Línea inválida, la ignoramos
                    continue;
                }

                int id = Integer.parseInt(partes[0].trim());
                //int id = 0;
                String name = partes[1].trim();
                int age = Integer.parseInt(partes[2].trim());
                String sex = partes[3].trim();
                String address = partes[4].trim();

                lista.add(new Registro(id, name, age, sex, address));
            }
        }

        return lista;
    }

    // ==== SUBIR A BASE DE DATOS ====
    private void subirArchivoABaseDeDatos(File file, boolean tieneEncabezado) {
        try {
            List<Registro> registros = leerRegistros(file, tieneEncabezado);

            if (registros.isEmpty()) {
                JOptionPane.showMessageDialog(
                        this,
                        "El archivo " + file.getName() + " no tiene registros válidos.",
                        "Información",
                        JOptionPane.INFORMATION_MESSAGE
                );
                return;
            }

            try (Connection conn = obtenerConexion();
                 PreparedStatement ps = conn.prepareStatement(
                         // IGNORA los ids duplicados
                         "INSERT IGNORE INTO personas (id, name, age, sex, address) VALUES (?,?,?,?,?)")) {

                for (Registro r : registros) {
                    ps.setInt(1, r.id);
                    ps.setString(2, r.name);
                    ps.setInt(3, r.age);
                    ps.setString(4, r.sex);
                    ps.setString(5, r.address);
                    ps.addBatch();
                }

                ps.executeBatch();

                JOptionPane.showMessageDialog(
                        this,
                        "Se intentaron subir " + registros.size() + " registros desde " + file.getName(),
                        "Éxito",
                        JOptionPane.INFORMATION_MESSAGE
                );

            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(
                        this,
                        "Error al conectar o insertar en la BD desde " + file.getName() + ":\n" + ex.getMessage(),
                        "Error BD",
                        JOptionPane.ERROR_MESSAGE
                );
            }

        } catch (IOException ex) {
            JOptionPane.showMessageDialog(
                    this,
                    "Error al leer el archivo " + file.getName() + ":\n" + ex.getMessage(),
                    "Error archivo",
                    JOptionPane.ERROR_MESSAGE
            );
        }
    }

    private Connection obtenerConexion() throws SQLException {
        return DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
    }

    // ==== CLASE PARA REPRESENTAR UN REGISTRO ====
    private static class Registro {
        int id;
        String name;
        int age;
        String sex;
        String address;

        Registro(int id, String name, int age, String sex, String address) {
            this.id = id;
            this.name = name;
            this.age = age;
            this.sex = sex;
            this.address = address;
        }

        String toLinea() {
            return id + ";" + name + ";" + age + ";" + sex + ";" + address;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (!(o instanceof Registro)) return false;
            Registro registro = (Registro) o;
            return id == registro.id &&
                    age == registro.age &&
                    Objects.equals(name, registro.name) &&
                    Objects.equals(sex, registro.sex) &&
                    Objects.equals(address, registro.address);
        }

        @Override
        public int hashCode() {
            return Objects.hash(id, name, age, sex, address);
        }
    }

    // ==== MAIN ====
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            Examen3 ventana = new Examen3();
            ventana.setVisible(true);
        });
    }
}