import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.stream.Collectors;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.border.EmptyBorder;

public class Tarea15 extends JFrame {

    // --------- Modelo ---------
    public static class Persona implements Serializable {
        private int id;
        private String nombre;
        private int edad;
        private char sexo;    // 'H' o 'M'
        private String estado;

        public Persona(int id, String nombre, int edad, char sexo, String estado) {
            this.id = id;
            this.nombre = nombre;
            this.edad = edad;
            this.sexo = sexo;
            this.estado = estado;
        }

        public int getId() { return id; }
        public String getNombre() { return nombre; }
        public int getEdad() { return edad; }
        public char getSexo() { return sexo; }
        public String getEstado() { return estado; }

        public void setNombre(String nombre) { this.nombre = nombre; }
        public void setEdad(int edad) { this.edad = edad; }
        public void setSexo(char sexo) { this.sexo = sexo; }
        public void setEstado(String estado) { this.estado = estado; }

        public String toCSV() {
            return id + "," + nombre + "," + edad + "," + sexo + "," + estado;
        }

        @Override
        public String toString() {
            return "ID=" + id + " | " + nombre + " | " + edad + " | " + sexo + " | " + estado;
        }
    }

    // Colección principal en memoria
    private final Map<Integer, Persona> indicePorId = new HashMap<>();
    private final java.util.List<Persona> lista = new ArrayList<>();

    // --------- UI ---------
    private JTextField txtRuta;
    private JTextField txtId, txtNombre, txtEdad, txtSexo, txtEstado;
    private JTextArea areaLog;
    private JLabel lblContador;

    public Tarea15() {
        super("Colección ↔ Archivo (Leer / Borrar / Grabar) - Java Swing");

        // Panel principal
        JPanel root = new JPanel(new BorderLayout(10,10));
        root.setBorder(new EmptyBorder(10,10,10,10));
        setContentPane(root);

        // Top: Ruta de archivo + botones cargar/grabar/obj
        JPanel top = new JPanel(new BorderLayout(8,8));
        JPanel fileRow = new JPanel(new BorderLayout(5,5));
        txtRuta = new JTextField();
        JButton btnBuscarArchivo = new JButton("Elegir CSV...");
        btnBuscarArchivo.addActionListener(this::onElegirCSV);
        fileRow.add(new JLabel("Archivo CSV:"), BorderLayout.WEST);
        fileRow.add(txtRuta, BorderLayout.CENTER);
        fileRow.add(btnBuscarArchivo, BorderLayout.EAST);

        JPanel topBtns = new JPanel(new FlowLayout(FlowLayout.LEFT, 8, 0));
        JButton btnCargar = new JButton("Cargar CSV → Colección");
        btnCargar.addActionListener(this::onCargarCSV);
        JButton btnGrabar = new JButton("Grabar Colección → CSV");
        btnGrabar.addActionListener(this::onGrabarCSV);
        JButton btnExportObj = new JButton("Exportar .obj");
        btnExportObj.addActionListener(this::onExportarOBJ);
        JButton btnImportObj = new JButton("Importar .obj");
        btnImportObj.addActionListener(this::onImportarOBJ);

        topBtns.add(btnCargar);
        topBtns.add(btnGrabar);
        topBtns.add(btnExportObj);
        topBtns.add(btnImportObj);

        top.add(fileRow, BorderLayout.NORTH);
        top.add(topBtns, BorderLayout.SOUTH);

        // Center: Formulario y acciones Leer/Agregar/Actualizar/Borrar
        JPanel center = new JPanel(new BorderLayout(10,10));

        JPanel form = new JPanel(new GridLayout(5,2,6,6));
        txtId = new JTextField();
        txtNombre = new JTextField();
        txtEdad = new JTextField();
        txtSexo = new JTextField();
        txtEstado = new JTextField();

        form.add(new JLabel("ID (int):"));
        form.add(txtId);
        form.add(new JLabel("Nombre:"));
        form.add(txtNombre);
        form.add(new JLabel("Edad (int):"));
        form.add(txtEdad);
        form.add(new JLabel("Sexo (H/M):"));
        form.add(txtSexo);
        form.add(new JLabel("Estado (AGS/SLP/...):"));
        form.add(txtEstado);

        JPanel acciones = new JPanel(new FlowLayout(FlowLayout.LEFT, 8, 0));
        JButton btnLeer = new JButton("Leer (por ID)");
        btnLeer.addActionListener(this::onLeer);
        JButton btnAgregarAct = new JButton("Agregar/Actualizar");
        btnAgregarAct.addActionListener(this::onAgregarActualizar);
        JButton btnBorrar = new JButton("Borrar (por ID)");
        btnBorrar.addActionListener(this::onBorrar);
        JButton btnLimpiar = new JButton("Limpiar campos");
        btnLimpiar.addActionListener(e -> limpiarCampos());

        acciones.add(btnLeer);
        acciones.add(btnAgregarAct);
        acciones.add(btnBorrar);
        acciones.add(btnLimpiar);

        center.add(form, BorderLayout.NORTH);
        center.add(acciones, BorderLayout.CENTER);

        // Bottom: Log + contador
        JPanel bottom = new JPanel(new BorderLayout(8,8));
        areaLog = new JTextArea(12, 80);
        areaLog.setEditable(false);
        JScrollPane sp = new JScrollPane(areaLog);
        bottom.add(sp, BorderLayout.CENTER);

        lblContador = new JLabel("Registros en colección: 0");
        bottom.add(lblContador, BorderLayout.SOUTH);

        // Ensamble
        root.add(top, BorderLayout.NORTH);
        root.add(center, BorderLayout.CENTER);
        root.add(bottom, BorderLayout.SOUTH);

        // Tamaño y cierre
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(900, 600);
        setLocationRelativeTo(null);
    }

    // ---------- Acciones ----------
    private void onElegirCSV(ActionEvent e) {
        JFileChooser fc = new JFileChooser();
        int ans = fc.showOpenDialog(this);
        if (ans == JFileChooser.APPROVE_OPTION) {
            txtRuta.setText(fc.getSelectedFile().getAbsolutePath());
            log("Archivo seleccionado: " + txtRuta.getText());
        }
    }

    private void onCargarCSV(ActionEvent e) {
        String ruta = txtRuta.getText().trim();
        if (ruta.isEmpty()) {
            msg("Indica la ruta del CSV o usa 'Elegir CSV...'");
            return;
        }
        try {
            cargarDesdeCSV(Path.of(ruta));
            log("CSV cargado → colección (" + lista.size() + " registros).");
        } catch (Exception ex) {
            error("Error al cargar CSV: " + ex.getMessage());
        }
    }

    private void onGrabarCSV(ActionEvent e) {
        String ruta = txtRuta.getText().trim();
        if (ruta.isEmpty()) {
            msg("Indica la ruta del CSV para grabar.");
            return;
        }
        try {
            grabarACSV(Path.of(ruta));
            log("Colección grabada en CSV: " + ruta);
        } catch (Exception ex) {
            error("Error al grabar CSV: " + ex.getMessage());
        }
    }

    private void onExportarOBJ(ActionEvent e) {
        JFileChooser fc = new JFileChooser();
        fc.setDialogTitle("Exportar colección como .obj");
        int ans = fc.showSaveDialog(this);
        if (ans == JFileChooser.APPROVE_OPTION) {
            File f = fc.getSelectedFile();
            try (ObjectOutputStream oos = new ObjectOutputStream(new BufferedOutputStream(new FileOutputStream(f)))) {
                oos.writeObject(new ArrayList<>(lista));
                log("Colección exportada a: " + f.getAbsolutePath());
            } catch (Exception ex) {
                error("Error exportando .obj: " + ex.getMessage());
            }
        }
    }

    @SuppressWarnings("unchecked")
    private void onImportarOBJ(ActionEvent e) {
        JFileChooser fc = new JFileChooser();
        fc.setDialogTitle("Importar colección desde .obj");
        int ans = fc.showOpenDialog(this);
        if (ans == JFileChooser.APPROVE_OPTION) {
            File f = fc.getSelectedFile();
            try (ObjectInputStream ois = new ObjectInputStream(new BufferedInputStream(new FileInputStream(f)))) {
                java.util.List<Persona> cargada = (java.util.List<Persona>) ois.readObject();
                lista.clear();
                indicePorId.clear();
                for (Persona p : cargada) {
                    lista.add(p);
                    indicePorId.put(p.getId(), p);
                }
                actualizarContador();
                log("Colección importada desde: " + f.getAbsolutePath() + " (" + lista.size() + " registros).");
            } catch (Exception ex) {
                error("Error importando .obj: " + ex.getMessage());
            }
        }
    }

    private void onLeer(ActionEvent e) {
        Integer id = parseEntero(txtId.getText(), "ID");
        if (id == null) return;
        Persona p = indicePorId.get(id);
        if (p == null) {
            log("No existe registro con ID=" + id);
            return;
        }
        // Cargar datos al formulario
        txtNombre.setText(p.getNombre());
        txtEdad.setText(String.valueOf(p.getEdad()));
        txtSexo.setText(String.valueOf(p.getSexo()));
        txtEstado.setText(p.getEstado());
        log("LEER → " + p);
    }

    private void onAgregarActualizar(ActionEvent e) {
        Integer id = parseEntero(txtId.getText(), "ID");
        Integer edad = parseEntero(txtEdad.getText(), "Edad");
        if (id == null || edad == null) return;

        String nombre = txtNombre.getText().trim();
        String sx = txtSexo.getText().trim().toUpperCase(Locale.ROOT);
        String estado = txtEstado.getText().trim().toUpperCase(Locale.ROOT);

        if (nombre.isEmpty() || sx.isEmpty() || estado.isEmpty()) {
            msg("Completa Nombre, Sexo y Estado.");
            return;
        }
        if (!(sx.equals("H") || sx.equals("M"))) {
            msg("Sexo debe ser H o M.");
            return;
        }

        Persona existente = indicePorId.get(id);
        if (existente == null) {
            Persona p = new Persona(id, nombre, edad, sx.charAt(0), estado);
            lista.add(p);
            indicePorId.put(id, p);
            log("AGREGADO → " + p);
        } else {
            existente.setNombre(nombre);
            existente.setEdad(edad);
            existente.setSexo(sx.charAt(0));
            existente.setEstado(estado);
            log("ACTUALIZADO → " + existente);
        }
        actualizarContador();
    }

    private void onBorrar(ActionEvent e) {
        Integer id = parseEntero(txtId.getText(), "ID");
        if (id == null) return;
        Persona p = indicePorId.remove(id);
        if (p == null) {
            log("No existe registro con ID=" + id);
            return;
        }
        // remover también de la lista
        lista.removeIf(per -> per.getId() == id);
        actualizarContador();
        log("BORRADO → " + p);
        limpiarCampos();
    }

    // ---------- Persistencia CSV ----------
    private void cargarDesdeCSV(Path path) throws IOException {
        lista.clear();
        indicePorId.clear();

        try (BufferedReader br = Files.newBufferedReader(path, StandardCharsets.UTF_8)) {
            String linea;
            int lineaNum = 0;
            while ((linea = br.readLine()) != null) {
                lineaNum++;
                linea = linea.trim();
                if (linea.isEmpty()) continue;

                String[] parts = linea.split(",");
                if (parts.length < 5) {
                    // tolera CSV con espacios: intenta reagrupar
                    error("Línea " + lineaNum + " inválida: " + linea);
                    continue;
                }
                try {
                    int id = Integer.parseInt(parts[0].trim());
                    String nombre = parts[1].trim();
                    int edad = Integer.parseInt(parts[2].trim());
                    char sexo = parts[3].trim().isEmpty() ? 'H' : parts[3].trim().toUpperCase(Locale.ROOT).charAt(0);
                    String estado = parts[4].trim().toUpperCase(Locale.ROOT);

                    Persona p = new Persona(id, nombre, edad, sexo, estado);
                    lista.add(p);
                    indicePorId.put(id, p);
                } catch (Exception ex) {
                    error("Error parseando línea " + lineaNum + ": " + linea + " → " + ex.getMessage());
                }
            }
        }
        // Mantener orden por ID al mostrar/guardar
        ordenarListaPorId();
        actualizarContador();
    }

    private void ordenarListaPorId() {
        Collections.sort(lista, Comparator.comparingInt(Persona::getId));
    }

    private void grabarACSV(Path path) throws IOException {
        // Guardar ordenado por id
        ordenarListaPorId();
        List<String> lineas = lista.stream().map(Persona::toCSV).collect(Collectors.toList());
        Files.write(path, lineas, StandardCharsets.UTF_8);
    }

    // ---------- Utilidades ----------
    private Integer parseEntero(String s, String campo) {
        try {
            return Integer.parseInt(s.trim());
        } catch (Exception ex) {
            msg("Valor inválido para " + campo + ": " + s);
            return null;
        }
    }

    private void actualizarContador() {
        lblContador.setText("Registros en colección: " + lista.size());
    }

    private void limpiarCampos() {
        txtId.setText("");
        txtNombre.setText("");
        txtEdad.setText("");
        txtSexo.setText("");
        txtEstado.setText("");
    }

    private void log(String s) { areaLog.append(s + "\n"); }
    private void msg(String s) { JOptionPane.showMessageDialog(this, s); }
    private void error(String s) { areaLog.append("[ERROR] " + s + "\n"); }

    // ---------- Main ----------
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            Tarea15 gui = new Tarea15();
            // (Opcional) Pre-cargar una ruta por defecto:
            gui.txtRuta.setText("C:\\Users\\Dembe\\Proyects\\School\\Java\\15\\archModificado.txt");
            // O si estás en un entorno tipo Linux/Colab, podrías usar:
            // gui.txtRuta.setText("/mnt/data/archModificado.txt");
            gui.setVisible(true);
        });
    }
}
