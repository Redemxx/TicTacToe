package project1;

import javax.swing.*;

public class SuperTicTacToePanel {

    private SuperTicTacToeGame game;
    private JButton cell_buttons;
    private JButton undo;
    private JButton enable_ai;
    public SuperTicTacToePanel() {
        game = new SuperTicTacToeGame();
    }

    public SuperTicTacToePanel(SuperTicTacToeGame game) {
        this.game = game;
    }
}
