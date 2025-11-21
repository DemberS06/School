import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;
import java.util.Locale;
import java.util.Map;
import java.util.TreeMap;

import javax.swing.JFrame;
import javax.swing.SwingUtilities;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.ChartUtils;
import org.jfree.chart.JFreeChart;
import org.jfree.data.general.DefaultPieDataset;

public class Tarea13 {
    public static void main(String[] args) {
        String path = args.length > 0 ? args[0] : "C:\\Users\\Dembe\\Proyects\\School\\Java\\13\\archModificado.txt";

        Map<String, int[]> stateCounts = new TreeMap<>();
        int totalH = 0, totalM = 0;

        try (BufferedReader br = new BufferedReader(
                new InputStreamReader(new FileInputStream(path), StandardCharsets.UTF_8))) {

            String line;
            while ((line = br.readLine()) != null) {
                line = line.trim();
                if (line.isEmpty()) continue;

                String[] parts = line.split(",");
                if (parts.length < 5) continue;

                String sexo = parts[3].trim().toUpperCase(Locale.ROOT);
                String estado = parts[4].trim().toUpperCase(Locale.ROOT);

                stateCounts.putIfAbsent(estado, new int[]{0, 0});

                if (sexo.equals("H")) {
                    stateCounts.get(estado)[0]++; 
                    totalH++;
                } else if (sexo.equals("M")) {
                    stateCounts.get(estado)[1]++; 
                    totalM++;
                }
            }

        } catch (IOException e) {
            System.err.println("Error leyendo el archivo: " + e.getMessage());
            return;
        }

        System.out.printf("%-28s %10s %10s %10s%n", "Estado", "Hombres", "Mujeres", "Total");
        System.out.println("--------------------------------------------------------------------");
        for (Map.Entry<String, int[]> e : stateCounts.entrySet()) {
            String edo = e.getKey();
            int h = e.getValue()[0];
            int m = e.getValue()[1];
            System.out.printf("%-28s %10d %10d %10d%n", edo, h, m, h + m);
        }
        System.out.println("--------------------------------------------------------------------");
        System.out.printf("%-28s %10d %10d %10d%n", "TOTALES", totalH, totalM, totalH + totalM);

        File csvOut = new File("conteo_por_estado_sexo_java.csv");
        try (PrintWriter pw = new PrintWriter(new OutputStreamWriter(
                new FileOutputStream(csvOut), StandardCharsets.UTF_8))) {
            pw.println("estado,hombres,mujeres,total");
            for (Map.Entry<String, int[]> e : stateCounts.entrySet()) {
                String edo = e.getKey();
                int h = e.getValue()[0];
                int m = e.getValue()[1];
                pw.printf("%s,%d,%d,%d%n", edo, h, m, h + m);
            }
        } catch (IOException ex) {
            System.err.println("No se pudo escribir el CSV: " + ex.getMessage());
        }
        System.out.println("CSV generado: " + csvOut.getAbsolutePath());

        DefaultPieDataset datasetGeneral = new DefaultPieDataset();
        datasetGeneral.setValue("Hombres", totalH);
        datasetGeneral.setValue("Mujeres", totalM);

        JFreeChart chartGeneral = ChartFactory.createPieChart(
                "Distribución general: Hombres vs Mujeres",
                datasetGeneral,
                true,
                true,  
                false  
        );

        File pngGeneral = new File("pastel_hombres_mujeres_java.png");
        try {
            ChartUtils.saveChartAsPNG(pngGeneral, chartGeneral, 700, 500);
            System.out.println("PNG general guardado: " + pngGeneral.getAbsolutePath());
        } catch (IOException ex) {
            System.err.println("No se pudo guardar el PNG general: " + ex.getMessage());
        }

        String edoObjetivo = "OAX";
        int hombresOax = 0, mujeresOax = 0;
        if (stateCounts.containsKey(edoObjetivo)) {
            hombresOax = stateCounts.get(edoObjetivo)[0];
            mujeresOax = stateCounts.get(edoObjetivo)[1];
        }

        if (hombresOax + mujeresOax == 0) {
            System.out.println("No hay registros para " + edoObjetivo + ". No se genera pastel de ese estado.");
        }

        DefaultPieDataset datasetOaxaca = new DefaultPieDataset();
        datasetOaxaca.setValue("Hombres (OAXACA)", hombresOax);
        datasetOaxaca.setValue("Mujeres (OAXACA)", mujeresOax);

        JFreeChart chartOaxaca = ChartFactory.createPieChart(
                "OAXACA: Hombres vs Mujeres",
                datasetOaxaca,
                true,  
                true,  
                false  
        );

        File pngOax = new File("pastel_oaxaca_h_m_java.png");
        try {
            ChartUtils.saveChartAsPNG(pngOax, chartOaxaca, 700, 500);
            System.out.println("PNG OAXACA guardado: " + pngOax.getAbsolutePath());
        } catch (IOException ex) {
            System.err.println("No se pudo guardar el PNG de OAXACA: " + ex.getMessage());
        }

        final JFreeChart chartGeneralFinal = chartGeneral;
        final JFreeChart chartOaxacaFinal = chartOaxaca;

        SwingUtilities.invokeLater(() -> {
            JFrame f1 = new JFrame("Gráfica General - H vs M");
            f1.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            f1.setContentPane(new ChartPanel(chartGeneralFinal));
            f1.pack();
            f1.setLocationRelativeTo(null);
            f1.setVisible(true);

            JFrame f2 = new JFrame("Gráfica OAXACA - H vs M");
            f2.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
            f2.setContentPane(new ChartPanel(chartOaxacaFinal));
            f2.pack();
            f2.setLocation(f1.getX() + 40, f1.getY() + 40); 
            f2.setVisible(true);
        });
    }
}
