package project1;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class SuperTicTacToePanel extends JPanel {

    private SuperTicTacToeGame game;
    private JButton[][] jButtonsBoard;
    private JButton undo;
    private JButton enable_ai;
    public SuperTicTacToePanel() {
        game = new SuperTicTacToeGame();
    }

    public SuperTicTacToePanel(SuperTicTacToeGame game) {
        this.game = game;
    }

    private class ButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {

        }
    }
}
