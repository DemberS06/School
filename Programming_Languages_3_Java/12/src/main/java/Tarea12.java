import java.awt.BorderLayout;
import java.text.DecimalFormat;

import javax.swing.JFrame;
import javax.swing.JTabbedPane;
import javax.swing.SwingUtilities;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.labels.PieSectionLabelGenerator;
import org.jfree.chart.labels.StandardPieSectionLabelGenerator;
import org.jfree.chart.plot.PiePlot3D;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.util.Rotation;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.data.general.DefaultPieDataset;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;

public class Tarea12 extends JFrame {

    public Tarea12() {
        super("Tarea12 - 5 Gráficas (JFreeChart)");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(900, 600);
        setLocationRelativeTo(null);

        JTabbedPane tabs = new JTabbedPane();

        tabs.add("Pie 3D", createPie3DPanel());
        tabs.add("Line Chart", createLineChartPanel());
        tabs.add("Scatter", createScatterChartPanel());
        tabs.add("Bar Chart", createBarChartPanel());
        tabs.add("Pie (labels)", createPieWithLabelsPanel());

        add(tabs, BorderLayout.CENTER);
    }

    private ChartPanel createPie3DPanel() {
        DefaultPieDataset dataset = new DefaultPieDataset();
        dataset.setValue("Linux", 29);
        dataset.setValue("Mac", 20);
        dataset.setValue("Windows", 51);

        JFreeChart chart = ChartFactory.createPieChart3D(
                "Which operating system are you using?",
                dataset,
                true, true, false
        );
        PiePlot3D plot = (PiePlot3D) chart.getPlot();
        plot.setStartAngle(290);
        plot.setDirection(Rotation.CLOCKWISE);
        plot.setForegroundAlpha(0.5f);

        return new ChartPanel(chart);
    }

    private ChartPanel createLineChartPanel() {
        DefaultCategoryDataset dataset = new DefaultCategoryDataset();
        String s1 = "Visitor";
        String s2 = "Unique Visitor";

        dataset.addValue(200, s1, "2016-12-19");
        dataset.addValue(150, s1, "2016-12-20");
        dataset.addValue(100, s1, "2016-12-21");
        dataset.addValue(210, s1, "2016-12-22");
        dataset.addValue(240, s1, "2016-12-23");
        dataset.addValue(195, s1, "2016-12-24");
        dataset.addValue(245, s1, "2016-12-25");

        dataset.addValue(150, s2, "2016-12-19");
        dataset.addValue(130, s2, "2016-12-20");
        dataset.addValue(95, s2, "2016-12-21");
        dataset.addValue(195, s2, "2016-12-22");
        dataset.addValue(200, s2, "2016-12-23");
        dataset.addValue(180, s2, "2016-12-24");
        dataset.addValue(230, s2, "2016-12-25");

        JFreeChart chart = ChartFactory.createLineChart("Site Traffic", "Date", "Number of Visitor", dataset);
        return new ChartPanel(chart);
    }

    private ChartPanel createScatterChartPanel() {
        XYSeriesCollection dataset = new XYSeriesCollection();

        XYSeries boys = new XYSeries("Boys");
        boys.add(1, 72.9); boys.add(2,81.6); boys.add(3,88.9); boys.add(4,96);
        boys.add(5,102.1); boys.add(6,108.5); boys.add(7,113.9); boys.add(8,119.3);
        boys.add(9,123.8); boys.add(10,124.4);

        XYSeries girls = new XYSeries("Girls");
        girls.add(1,72.5); girls.add(2,80.1); girls.add(3,87.2); girls.add(4,94.5);
        girls.add(5,101.4); girls.add(6,107.4); girls.add(7,112.8); girls.add(8,118.2);
        girls.add(9,122.9); girls.add(10,123.4);

        dataset.addSeries(boys);
        dataset.addSeries(girls);

        JFreeChart chart = ChartFactory.createScatterPlot("Boys VS Girls weight comparison chart", "X-Axis", "Y-Axis", dataset);
        return new ChartPanel(chart);
    }

    private ChartPanel createBarChartPanel() {
        DefaultCategoryDataset dataset = new DefaultCategoryDataset();

        dataset.addValue(10, "USA", "2005");
        dataset.addValue(15, "India", "2005");
        dataset.addValue(20, "China", "2005");

        dataset.addValue(15, "USA", "2010");
        dataset.addValue(20, "India", "2010");
        dataset.addValue(25, "China", "2010");

        dataset.addValue(20, "USA", "2015");
        dataset.addValue(25, "India", "2015");
        dataset.addValue(30, "China", "2015");

        JFreeChart chart = ChartFactory.createBarChart("Bar Chart Example", "Year", "Population in Million", dataset, PlotOrientation.VERTICAL, true, true, false);
        return new ChartPanel(chart);
    }

    private ChartPanel createPieWithLabelsPanel() {
        DefaultPieDataset dataset = new DefaultPieDataset();
        dataset.setValue("80-100", 120);
        dataset.setValue("60-79", 80);
        dataset.setValue("40-59", 20);
        dataset.setValue("20-39", 7);
        dataset.setValue("0-19", 3);

        JFreeChart chart = ChartFactory.createPieChart("Pie Chart Example", dataset, true, true, false);
        PieSectionLabelGenerator labelGenerator = new StandardPieSectionLabelGenerator("Marks {0} : ({2})", new DecimalFormat("0"), new DecimalFormat("0%"));
        ((org.jfree.chart.plot.PiePlot) chart.getPlot()).setLabelGenerator(labelGenerator);

        return new ChartPanel(chart);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            Tarea12 t = new Tarea12();
            t.setVisible(true);
        });
    }
}
