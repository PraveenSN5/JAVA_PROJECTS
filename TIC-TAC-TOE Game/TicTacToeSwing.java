import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class TicTacToeSwing {

    private JFrame frame;
    private JButton[] buttons = new JButton[9];
    private boolean xTurn = true; // X goes first
    private int moveCount = 0;

    public TicTacToeSwing() {
        createGUI();
    }

    private void createGUI() {
        frame = new JFrame("Tic-Tac-Toe");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(300, 300);
        frame.setLayout(new GridLayout(3, 3));

        // Create buttons
        for (int i = 0; i < 9; i++) {
            buttons[i] = new JButton();
            buttons[i].setFont(new Font("Arial", Font.BOLD, 40));
            buttons[i].addActionListener(new ButtonClickListener(i));
            frame.add(buttons[i]);
        }

        frame.setVisible(true);
    }

    private class ButtonClickListener implements ActionListener {
        private int index;

        public ButtonClickListener(int index) {
            this.index = index;
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            if (buttons[index].getText().equals("")) {
                if (xTurn) {
                    buttons[index].setText("X");
                } else {
                    buttons[index].setText("O");
                }
                moveCount++;
                xTurn = !xTurn;

                if (checkForWin()) {
                    String winner = xTurn ? "O" : "X";
                    JOptionPane.showMessageDialog(frame, winner + " wins!");
                    resetGame();
                } else if (moveCount == 9) {
                    JOptionPane.showMessageDialog(frame, "It's a draw!");
                    resetGame();
                }
            }
        }
    }

    private boolean checkForWin() {
        // Check rows
        for (int i = 0; i < 9; i += 3) {
            if (!buttons[i].getText().equals("") &&
                buttons[i].getText().equals(buttons[i+1].getText()) &&
                buttons[i].getText().equals(buttons[i+2].getText())) {
                return true;
            }
        }

        // Check columns
        for (int i = 0; i < 3; i++) {
            if (!buttons[i].getText().equals("") &&
                buttons[i].getText().equals(buttons[i+3].getText()) &&
                buttons[i].getText().equals(buttons[i+6].getText())) {
                return true;
            }
        }

        // Check diagonals
        if (!buttons[0].getText().equals("") &&
            buttons[0].getText().equals(buttons[4].getText()) &&
            buttons[0].getText().equals(buttons[8].getText())) {
            return true;
        }

        if (!buttons[2].getText().equals("") &&
            buttons[2].getText().equals(buttons[4].getText()) &&
            buttons[2].getText().equals(buttons[6].getText())) {
            return true;
        }

        return false;
    }

    private void resetGame() {
        for (int i = 0; i < 9; i++) {
            buttons[i].setText("");
        }
        xTurn = true;
        moveCount = 0;
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new TicTacToeSwing());
    }
}