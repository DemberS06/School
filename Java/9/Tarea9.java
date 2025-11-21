import java.awt.*;
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.text.Normalizer;
import java.util.ArrayList;
import java.util.List;
import javax.swing.*;

public class Tarea9 extends JFrame {
    private static final String DATA_FILENAME = "data.txt";
    private JTextArea outputArea;
    private JButton leerButton;
    private JButton generarButton;
    private List<Person> people = new ArrayList<>();

    public Tarea9() {
        super("Generador de CURP - Dember");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(700, 500);
        setLocationRelativeTo(null);

        outputArea = new JTextArea();
        outputArea.setEditable(false);
        JScrollPane scroll = new JScrollPane(outputArea);

        leerButton = new JButton("Leer data");
        generarButton = new JButton("Generar CURP");

        JPanel top = new JPanel();
        top.add(leerButton);
        top.add(generarButton);

        add(top, BorderLayout.NORTH);
        add(scroll, BorderLayout.CENTER);

        leerButton.addActionListener(e -> leerData());
        generarButton.addActionListener(e -> generarCurps());
    }

    private void leerData() {
        people.clear();
        File f = new File(DATA_FILENAME);
        try {
            if (!f.exists()) {
                // crear el archivo vacío si no existe
                boolean created = f.createNewFile();
                outputArea.setText("Archivo '" + DATA_FILENAME + "' no encontrado. Se creó un archivo vacío en el directorio de trabajo.\n\n");
                outputArea.append("Coloca en cada línea: <nombre completo>,<día>,<mes (número)>,<año>\nEjemplo: Juan Pérez López,23,2,2006\n");
                return;
            }

            try (BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(f), StandardCharsets.UTF_8))) {
                String line;
                int lineNo = 0;
                StringBuilder sb = new StringBuilder();
                while ((line = br.readLine()) != null) {
                    lineNo++;
                    line = line.trim();
                    if (line.isEmpty()) continue;
                    String[] parts = line.split(",");
                    if (parts.length < 4) {
                        sb.append("Línea " + lineNo + " inválida (se esperan 4 campos separados por comas): " + line + "\n");
                        continue;
                    }
                    String nombre = parts[0].trim();
                    String dia = parts[1].trim();
                    String mes = parts[2].trim();
                    String año = parts[3].trim();
                    people.add(new Person(nombre, dia, mes, año));
                    sb.append("Cargada línea " + lineNo + ": " + nombre + ", " + dia + "/" + mes + "/" + año + "\n");
                }
                if (people.isEmpty()) sb.append("No se encontraron registros en '" + DATA_FILENAME + "'.\n");
                outputArea.setText(sb.toString());
            }
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(this, "Error leyendo/creando '" + DATA_FILENAME + "': " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void generarCurps() {
        if (people.isEmpty()) {
            outputArea.setText("No hay datos cargados. Presiona 'Leer data' primero para cargar '" + DATA_FILENAME + "'.\n");
            return;
        }
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < people.size(); i++) {
            Person p = people.get(i);
            try {
                String curp = buildCurp(p);
                sb.append(p.originalLine()).append(" -> ").append(curp).append("\n");
            } catch (IllegalArgumentException ex) {
                sb.append("Error en registro ").append(i + 1).append(": ").append(ex.getMessage()).append("\n");
            }
        }
        outputArea.setText(sb.toString());
    }

    // Implementación simplificada/generadora de CURP. NOTA: no pretende reemplazar la generación oficial.
    private String buildCurp(Person p) {
        // Normalizar (quitar acentos y mayúsculas)
        String nombre = normalize(p.nombre);
        String[] nameParts = nombre.split("\\s+");
        if (nameParts.length == 0) throw new IllegalArgumentException("Nombre vacío");

        // Separar apellidos y nombres asumiendo: primer token = apellido paterno, segundo token = apellido materno, resto = nombres
        String paterno = nameParts.length >= 1 ? nameParts[0] : "";
        String materno = nameParts.length >= 2 ? nameParts[1] : "";
        String nombres = "";
        if (nameParts.length > 2) {
            StringBuilder tmp = new StringBuilder();
            for (int i = 2; i < nameParts.length; i++) {
                if (i > 2) tmp.append(' ');
                tmp.append(nameParts[i]);
            }
            nombres = tmp.toString();
        }

        // Manejar casos de nombres compuestos comunes (José, María) - se usa el segundo nombre si existe
        String firstNameForCurp = nombres;
        if (!nombres.isEmpty()) {
            String[] nTokens = nombres.split("\\s+");
            if (nTokens.length > 1 && (nTokens[0].equalsIgnoreCase("JOSE") || nTokens[0].equalsIgnoreCase("JOSÉ") || nTokens[0].equalsIgnoreCase("MA") || nTokens[0].equalsIgnoreCase("MARIA") || nTokens[0].equalsIgnoreCase("MARÍA") )) {
                firstNameForCurp = nTokens[1];
            } else firstNameForCurp = nTokens[0];
        } else {
            // si no hay nombres (caso extraño), usar X
            firstNameForCurp = "X";
        }

        // Partes iniciales
        char a1 = firstCharOrX(paterno);
        char a2 = firstVowelAfterFirstOrX(paterno);
        char a3 = firstCharOrX(materno);
        char a4 = firstCharOrX(firstNameForCurp);

        // Fecha: año(2) mes(2) día(2)
        String year = twoRightDigits(p.año);
        String month = twoDigits(p.mes);
        String day = twoDigits(p.dia);

        // Lugar de nacimiento: no proporcionado en data.txt => usamos XX como placeholder
        String state = "XX";

        // Consonantes intermedias
        char c1 = firstInternalConsonantOrX(paterno);
        char c2 = firstInternalConsonantOrX(materno);
        char c3 = firstInternalConsonantOrX(firstNameForCurp);

        // Homoclave y dígito verificador simplificados
        String homoclave = "00"; // en la CURP real se calcula con algoritmos oficiales

        String curp = new StringBuilder()
                .append(a1).append(a2).append(a3).append(a4)
                .append(year).append(month).append(day)
                .append(state)
                .append(c1).append(c2).append(c3)
                .append(homoclave)
                .toString();

        return curp.toUpperCase();
    }

    // Helpers
    private static String normalize(String s) {
        String tmp = s.trim().toUpperCase();
        tmp = Normalizer.normalize(tmp, Normalizer.Form.NFD).replaceAll("\\p{InCombiningDiacriticalMarks}+", "");
        // eliminar caracteres no alfabéticos excepto espacios
        tmp = tmp.replaceAll("[^A-Z\\s]", "");
        return tmp;
    }

    private static char firstCharOrX(String s) {
        if (s == null || s.isEmpty()) return 'X';
        return s.charAt(0);
    }

    private static char firstVowelAfterFirstOrX(String s) {
        if (s == null || s.length() <= 1) return 'X';
        for (int i = 1; i < s.length(); i++) {
            char c = s.charAt(i);
            return c;
        }
        return 'X';
    }

    private static char firstInternalConsonantOrX(String s) {
        if (s == null || s.length() <= 1) return 'X';
        for (int i = 1; i < s.length(); i++) {
            char c = s.charAt(i);
            if ("BCDFGHJKLMNPQRSTVWXYZ".indexOf(c) >= 0) return c;
        }
        return 'X';
    }

    private static String twoDigits(String n) {
        try {
            int v = Integer.parseInt(n.trim());
            if (v < 0) v = 0;
            if (v > 99) v = v % 100;
            return String.format("%02d", v);
        } catch (NumberFormatException ex) {
            return "00";
        }
    }

    private static String twoRightDigits(String n) {
        try {
            int v = Integer.parseInt(n.trim());
            int r = v % 100;
            return String.format("%02d", r);
        } catch (NumberFormatException ex) {
            return "00";
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            Tarea9 app = new Tarea9();
            app.setVisible(true);
        });
    }

    // Clase auxiliar para guardar los datos leídos
    private static class Person {
        String nombre;
        String dia;
        String mes;
        String año;

        Person(String nombre, String dia, String mes, String año) {
            this.nombre = nombre;
            this.dia = dia;
            this.mes = mes;
            this.año = año;
        }

        String originalLine() {
            return nombre + "," + dia + "," + mes + "," + año;
        }
    }
}
