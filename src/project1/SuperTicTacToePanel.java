package project1;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class SuperTicTacToePanel extends JPanel {

    private SuperTicTacToeGame game;
    private JButton[][] jButtonsBoard;
    private JButton undo;
    private JButton enable_ai;
    private ImageIcon xIcon; //Halloween theme?
    private ImageIcon oIcon; // Halloween theme?
    private ImageIcon emptyIcon;
    private JMenu fileMenu;
    private JMenuBar menuBar = new JMenuBar();
    private JMenuItem quitItem;
    private JMenuItem undoItem; //Create keyboard shortcut?
    private JMenuItem redoItem; //Create keyboard shortcut?

    public void main(String[] args) {

        fileMenu = new JMenu("File");
        quitItem = new JMenuItem("Quit!");
        undoItem = new JMenuItem("Undo!");
        redoItem = new JMenuItem("Redo!");

        fileMenu.add(quitItem);
        fileMenu.add(undoItem);
        fileMenu.add(redoItem);

        quitItem.addActionListener(new MenuHandler());
        undoItem.addActionListener(new MenuHandler()::actionPerformedB);
        redoItem.addActionListener(new MenuHandler()::actionPerformedC);

        menuBar.add(fileMenu);
        //OUR-GUI-NAME.add(menuBar);
    }

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
