import java.awt.*;
import javax.swing.*;

public class Gato {
    private JFrame frame;
    private JPanel boardPanel;
    private JButton[][] botones;
    private int n = 3; 
    private char player1Char = 'X';
    private char player2Char = 'O';
    private int turno = 1;
    private int movesMade = 0;

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new Gato().createAndShowGui());
    }

    private void createAndShowGui() {
        frame = new JFrame("Gato nxn - Jugador vs Jugador");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new BorderLayout());

        JMenuBar menuBar = new JMenuBar();
        JMenu menu = new JMenu("Juego");
        JMenuItem newGameItem = new JMenuItem("Nueva partida");
        JMenuItem settingsItem = new JMenuItem("Ajustes");

        newGameItem.addActionListener(e -> nuevaPartida());
        settingsItem.addActionListener(e -> abrirDialogoAjustes());

        menu.add(newGameItem);
        menu.add(settingsItem);
        menuBar.add(menu);
        frame.setJMenuBar(menuBar);

        boardPanel = new JPanel();
        frame.add(boardPanel, BorderLayout.CENTER);

        // Panel inferior con información de turno y reiniciar
        JPanel bottom = new JPanel(new FlowLayout(FlowLayout.CENTER));
        JLabel turnoLabel = new JLabel(getTurnText());
        JButton reiniciarBtn = new JButton("Reiniciar");
        reiniciarBtn.addActionListener(e -> nuevaPartida());
        bottom.add(turnoLabel);
        bottom.add(reiniciarBtn);
        frame.add(bottom, BorderLayout.SOUTH);

        // actualizamos el label cada vez que cambia el turno
        Timer t = new Timer(100, e -> turnoLabel.setText(getTurnText()));
        t.start();

        construirTablero();

        frame.setSize(600, 650);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    private String getTurnText() {
        return "Turno: " + (turno == 1 ? "Jugador 1 (" + player1Char + ")" : "Jugador 2 (" + player2Char + ")");
    }

    private void construirTablero() {
        boardPanel.removeAll();
        boardPanel.setLayout(new GridLayout(n, n));
        botones = new JButton[n][n];

        int fontSize = Math.max(12, 48 - n);

        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                JButton b = new JButton("");
                b.setFont(new Font(Font.SANS_SERIF, Font.BOLD, fontSize));
                final int r = i, c = j;
                b.addActionListener(ev -> onButtonClick(r, c));
                botones[i][j] = b;
                boardPanel.add(b);
            }
        }

        boardPanel.revalidate();
        boardPanel.repaint();
        movesMade = 0;
        turno = 1;
    }

    private void onButtonClick(int r, int c) {
        JButton b = botones[r][c];
        if (!b.getText().isEmpty()) return;

        char marca = (turno == 1) ? player1Char : player2Char;
        b.setText(String.valueOf(marca));
        b.setEnabled(false);
        movesMade++;

        if (hayGanador(r, c, marca)) {
            JOptionPane.showMessageDialog(frame, "Gana el " + (turno == 1 ? "Jugador 1" : "Jugador 2") + " con '" + marca + "'!", "Fin de la partida", JOptionPane.INFORMATION_MESSAGE);
            bloquearTablero();
            return;
        }

        if (movesMade == n * n) {
            JOptionPane.showMessageDialog(frame, "Empate!", "Fin de la partida", JOptionPane.INFORMATION_MESSAGE);
            return;
        }

        turno = 3 - turno;
    }

    private void bloquearTablero() {
        for (int i = 0; i < n; i++)
            for (int j = 0; j < n; j++)
                botones[i][j].setEnabled(false);
    }

    private boolean hayGanador(int r, int c, char marca) {
        boolean win = true;
        for (int j = 0; j < n; j++) {
            if (!marcaEquals(botones[r][j], marca)) { win = false; break; }
        }
        if (win) return true;

        win = true;
        for (int i = 0; i < n; i++) {
            if (!marcaEquals(botones[i][c], marca)) { win = false; break; }
        }
        if (win) return true;

        if (r == c) {
            win = true;
            for (int i = 0; i < n; i++) {
                if (!marcaEquals(botones[i][i], marca)) { win = false; break; }
            }
            if (win) return true;
        }

        if (r + c == n - 1) {
            win = true;
            for (int i = 0; i < n; i++) {
                if (!marcaEquals(botones[i][n - 1 - i], marca)) { win = false; break; }
            }
            if (win) return true;
        }

        return false;
    }

    private boolean marcaEquals(JButton b, char marca) {
        String t = b.getText();
        return t.length() == 1 && t.charAt(0) == marca;
    }

    private void nuevaPartida() {
        construirTablero();
    }

    private void abrirDialogoAjustes() {
        JPanel panel = new JPanel(new GridLayout(0, 2, 6, 6));
        JTextField nField = new JTextField(String.valueOf(n));
        JTextField p1Field = new JTextField(String.valueOf(player1Char));
        JTextField p2Field = new JTextField(String.valueOf(player2Char));

        panel.add(new JLabel("Tamaño n (filas/columnas):"));
        panel.add(nField);
        panel.add(new JLabel("Carácter Jugador 1:"));
        panel.add(p1Field);
        panel.add(new JLabel("Carácter Jugador 2:"));
        panel.add(p2Field);

        int res = JOptionPane.showConfirmDialog(frame, panel, "Ajustes", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
        if (res == JOptionPane.OK_OPTION) {
            String nText = nField.getText().trim();
            String p1Text = p1Field.getText().trim();
            String p2Text = p2Field.getText().trim();

            int newN;
            try {
                newN = Integer.parseInt(nText);
                if (newN < 1 || newN > 50) throw new NumberFormatException();
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(frame, "Introduce un número entero válido para n (1-50).", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            if (p1Text.isEmpty() || p1Text.length() > 1 || p2Text.isEmpty() || p2Text.length() > 1) {
                JOptionPane.showMessageDialog(frame, "Los caracteres de los jugadores deben ser exactamente 1 carácter cada uno.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            char newP1 = p1Text.charAt(0);
            char newP2 = p2Text.charAt(0);

            if (newP1 == newP2) {
                int opcion = JOptionPane.showConfirmDialog(frame, "Has elegido el mismo carácter para ambos jugadores. ¿Quieres continuar?", "Confirmar", JOptionPane.YES_NO_OPTION);
                if (opcion != JOptionPane.YES_OPTION) return;
            }

            this.n = newN;
            this.player1Char = newP1;
            this.player2Char = newP2;
            nuevaPartida();
        }
    }
}
