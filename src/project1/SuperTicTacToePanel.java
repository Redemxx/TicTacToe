package project1;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class SuperTicTacToePanel extends JPanel {

    private SuperTicTacToeGame game;
    private JButton[][] jButtonsBoard;
    private JButton undo;
    private JButton enable_ai;
    private ImageIcon xIcon;
    private ImageIcon oIcon;
    private ImageIcon emptyIcon;
    public SuperTicTacToePanel() {
        game = new SuperTicTacToeGame();
    }

    public SuperTicTacToePanel(SuperTicTacToeGame game) {
        this.game = game;
    }

    private void instantiateInstance() {
        xIcon = new ImageIcon("x.png");
        oIcon = new ImageIcon("o.jpg");
        emptyIcon = new ImageIcon();

        int s = game.getDimension();
        jButtonsBoard = new JButton[s][s];

        for (JButton[] a : jButtonsBoard) {
            for (JButton b : a) {
                b = new JButton("", emptyIcon);
                b.addActionListener(new ButtonListener());
            }
        }

    }

    private class ButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {

        }
    }
}
