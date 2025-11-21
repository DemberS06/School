import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class Tarea8 extends JPanel implements ActionListener {

    TextField entrada;
    int oportunidades = 0;

    public Tarea8() {
        setLayout(new FlowLayout());
        entrada = new TextField(18);
        entrada.addActionListener(this);
        add(new JLabel("Escribe y presiona Enter para avanzar:"));
        add(entrada);
        setPreferredSize(new Dimension(780, 460));
        setBackground(new Color(180, 220, 255)); 
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        oportunidades++;
        repaint();
        if (oportunidades >= 9) System.exit(0);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g.create();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        int W = getWidth(), H = getHeight();
        int sueloY = (int)(H*0.78);

        Color cielo   = new Color(180, 220, 255);
        Color nieve   = new Color(235, 245, 255);
        Color sombraNieve = new Color(210, 230, 245);
        Color hielo   = new Color(220, 240, 255);
        Color lineaHielo = new Color(140, 170, 190);
        Color entradaOscura = new Color(90, 120, 150);
        Color naranjaPico = new Color(250, 140, 40);
        Color negro = new Color(20, 20, 30);

        // Paso 1:
        if (oportunidades >= 1) {
            g2.setColor(nieve);
            g2.fillRect(0, sueloY, W, H - sueloY);
            g2.setColor(sombraNieve);
            g2.fillOval(-60, sueloY-14, 220, 28);
            g2.fillOval(160, sueloY-10, 260, 22);
            g2.fillOval(460, sueloY-16, 260, 30);
        }

        int igluCX = (int)(W*0.48);  
        int igluBaseY = sueloY;     
        int radio = 150;            

        // Paso 2:
        if (oportunidades >= 2) {
            g2.setColor(hielo);
            g2.fillArc(igluCX - radio, igluBaseY - 2*radio, 2*radio, 2*radio, 0, 180);
            g2.fillRect(igluCX - radio, igluBaseY - radio, 2*radio, radio);

            g2.setColor(lineaHielo);
            g2.setStroke(new BasicStroke(3));
            g2.drawArc(igluCX - radio, igluBaseY - 2*radio, 2*radio, 2*radio, 0, 180);
            g2.drawLine(igluCX - radio, igluBaseY, igluCX + radio, igluBaseY);
        }

        // Paso 3:
        if (oportunidades >= 3) {
            g2.setColor(lineaHielo);
            g2.setStroke(new BasicStroke(2));
            for (int y = igluBaseY - (int)(1.6*radio); y < igluBaseY; y += 26) {
                int dy = igluBaseY - y;
                double semiAncho = Math.sqrt(Math.max(0, radio*radio - Math.pow(dy,2)));
                int x1 = igluCX - (int)semiAncho;
                int x2 = igluCX + (int)semiAncho;
                g2.drawLine(x1, y, x2, y);
            }
        }

        // Paso 4:
        if (oportunidades >= 4) {
            g2.setColor(lineaHielo);
            int[][] columnas = {
                {-90, -50, -15, 20, 55, 90},
            };
            for (int dx : columnas[0]) {
                for (int k = 0; k < 5; k++) {
                    int y = igluBaseY - 30 - k*30;
                    int x = igluCX + dx;
                    int alto = 16 + (k%2==0?8:0);
                    int top = Math.max(igluBaseY - (int)(1.6*radio), y - alto/2);
                    int bottom = Math.min(igluBaseY-4, y + alto/2);
                    if (top < bottom) g2.drawLine(x, top, x, bottom);
                }
            }
        }

        // Paso 5:
        int entradaW = 90, entradaH = 70;
        int entradaX = igluCX + (int)(0.55*radio);
        int entradaY = igluBaseY - entradaH + 6;
        if (oportunidades >= 5) {
            g2.setColor(hielo);
            g2.fillRoundRect(entradaX, entradaY, entradaW, entradaH, 30, 30);
            g2.setColor(entradaOscura);
            g2.fillRoundRect(entradaX + 10, entradaY + 12, entradaW - 20, entradaH - 18, 24, 24);
            g2.setColor(lineaHielo);
            g2.setStroke(new BasicStroke(2.5f));
            g2.drawRoundRect(entradaX, entradaY, entradaW, entradaH, 30, 30);
        }

        // Paso 6:
        if (oportunidades >= 6) {
            g2.setColor(new Color(0, 20, 40, 25));
            g2.fillOval(igluCX - (int)(1.1*radio), igluBaseY - 10, (int)(2.2*radio), 22);
            g2.fillOval(entradaX - 10, igluBaseY - 8, entradaW + 20, 18);
        }

        // Paso 7: 
        int pBaseX = igluCX - (int)(0.55*radio); 
        int pBaseY = igluBaseY;                  
        int pAlto = 130;                          
        int pAncho = 86;

        if (oportunidades >= 7) {
            g2.setColor(negro);
            g2.fillOval(pBaseX - pAncho/2, pBaseY - pAlto, pAncho, pAlto);

            g2.setColor(nieve);
            g2.fillOval(pBaseX - (int)(pAncho*0.32), pBaseY - (int)(pAlto*0.80), (int)(pAncho*0.64), (int)(pAlto*0.62));

            g2.setColor(negro);
            g2.fillOval(pBaseX - (int)(pAncho*0.70), pBaseY - (int)(pAlto*0.68), (int)(pAncho*0.55), (int)(pAlto*0.48));
            g2.fillOval(pBaseX + (int)(pAncho*0.15), pBaseY - (int)(pAlto*0.68), (int)(pAncho*0.55), (int)(pAlto*0.48));

            g2.setColor(naranjaPico);
            Polygon pico = new Polygon();
            int picoY = pBaseY - (int)(pAlto*0.78);
            pico.addPoint(pBaseX - 4, picoY);
            pico.addPoint(pBaseX + 18, picoY + 8);
            pico.addPoint(pBaseX - 4, picoY + 16);
            g2.fillPolygon(pico);

            g2.setColor(Color.WHITE);
            g2.fillOval(pBaseX - 18, picoY - 16, 16, 16);
            g2.fillOval(pBaseX + 2,  picoY - 16, 16, 16);
            g2.setColor(Color.BLACK);
            g2.fillOval(pBaseX - 13, picoY - 10, 6, 6);
            g2.fillOval(pBaseX + 7,  picoY - 10, 6, 6);

            g2.setColor(new Color(255, 160, 60));
            g2.fillOval(pBaseX - 28, pBaseY - 12, 30, 14);
            g2.fillOval(pBaseX + 2,  pBaseY - 12, 30, 14);

            g2.setColor(new Color(200, 30, 60));
            g2.fillRoundRect(pBaseX - 30, pBaseY - (int)(pAlto*0.62), 60, 14, 10, 10);
            g2.fillRoundRect(pBaseX + 20,  pBaseY - (int)(pAlto*0.62) + 10, 12, 28, 8, 8);
        }

        // Paso 8: 
        if (oportunidades >= 8) {
            g2.setColor(new Color(30, 50, 80));
            g2.setFont(getFont().deriveFont(Font.BOLD, 18f));
            g2.drawString("Pingüino Rodríguez", pBaseX - 90, pBaseY + 28);
        }

        // HUD
        g2.setColor(Color.BLACK);
        g2.setFont(getFont().deriveFont(Font.BOLD, 14f));
        g2.drawString("Paso: " + Math.min(oportunidades, 9) + "/9 — Enter para avanzar", 16, 28);

        g2.dispose();
    }

    public static void main(String[] args) {
        JFrame.setDefaultLookAndFeelDecorated(true);
        JFrame f = new JFrame("Iglú con el pingüino Rodríguez");
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        f.getContentPane().add(new Tarea8(), BorderLayout.CENTER);
        f.pack();
        f.setLocationRelativeTo(null);
        f.setVisible(true);
    }
}