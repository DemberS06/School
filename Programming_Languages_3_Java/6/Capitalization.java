import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.util.StringTokenizer;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;

public class Capitalization {
        static class MiPanel extends JPanel {
        private final JTextField txtEntrada;
        private final JButton btnCapitalizar;
        private final JTextField txtPrimeraLetra;   
        private final JTextField txtCadaPalabra;    

        MiPanel() {
            setLayout(new BorderLayout(12, 12));
            setBackground(new Color(245, 245, 245));

            var arriba = new JPanel(new BorderLayout(8, 8));
            arriba.setOpaque(false);
            arriba.add(new JLabel("Texto de entrada:"), BorderLayout.WEST);

            txtEntrada = new JTextField();
            arriba.add(txtEntrada, BorderLayout.CENTER);

            btnCapitalizar = new JButton("Capitalizar");
            btnCapitalizar.addActionListener(this::onCapitalizar);

            var abajo = new JPanel(new GridLayout(2, 2, 8, 8));
            abajo.setOpaque(false);
            abajo.add(new JLabel("Primera letra en mayúscula:"));
            txtPrimeraLetra = new JTextField();
            txtPrimeraLetra.setEditable(false);
            abajo.add(txtPrimeraLetra);

            abajo.add(new JLabel("Cada palabra en mayúscula:"));
            txtCadaPalabra = new JTextField();
            txtCadaPalabra.setEditable(false);
            abajo.add(txtCadaPalabra);

            add(arriba, BorderLayout.NORTH);
            add(btnCapitalizar, BorderLayout.CENTER);
            add(abajo, BorderLayout.SOUTH);

            setBorder(BorderFactory.createEmptyBorder(12, 12, 12, 12));
        }

        private void onCapitalizar(ActionEvent e) {
            String a = txtEntrada.getText();
            if (a == null) a = "";
            a = a.strip();

            String primera = capitalizarPrimeraLetra(a);
            txtPrimeraLetra.setText(primera);

            String porPalabra = capitalizarCadaPalabra(a);
            txtCadaPalabra.setText(porPalabra);
        }

        private String capitalizarPrimeraLetra(String a) {
            if (a.isEmpty()) return "";
            StringBuilder sb = new StringBuilder(a.length());
            sb.append(Character.toUpperCase(a.charAt(0)));
            if (a.length() > 1) sb.append(a.substring(1));
            return sb.toString();
        }

        private String capitalizarCadaPalabra(String a) {
            if (a.isEmpty()) return "";
            StringTokenizer st = new StringTokenizer(a, " ");
            StringBuilder sb = new StringBuilder(a.length() + 8);
            while (st.hasMoreElements()) { // (también sirve hasMoreTokens)
                String token = (String) st.nextElement();
                if (!token.isEmpty()) {
                    // Capitaliza primera letra de cada token
                    StringBuilder tmp = new StringBuilder(token);
                    tmp.setCharAt(0, Character.toUpperCase(tmp.charAt(0)));
                    sb.append(tmp).append(' ');
                }
            }
            if (sb.length() > 0 && sb.charAt(sb.length() - 1) == ' ')
                sb.deleteCharAt(sb.length() - 1);
            return sb.toString();
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            try {
                UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
            } catch (Exception ignored) {}

            JFrame frame = new JFrame("Dos TextField");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

            MiPanel miClase = new MiPanel();
            frame.getContentPane().add(miClase, BorderLayout.CENTER);

            frame.setSize(560, 240);
            frame.setLocationRelativeTo(null); 
            frame.getContentPane().setBackground(new Color(230, 230, 230));

            frame.setVisible(true);
        });
    }
}