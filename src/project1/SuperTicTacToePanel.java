package project1;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class SuperTicTacToePanel extends JPanel {

    private SuperTicTacToeGame game;
    private ArrayList<SuperTicTacToeGame> moves_history;
    private JFrame gui;
    private JButton[][] jButtonsBoard;
    private JButton undo;
    private JButton enable_ai;
    private ImageIcon xIcon;
    private ImageIcon oIcon;
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
        String s = getInput("Enter desired size of the TicTacToe board:");
        game = new SuperTicTacToeGame(Integer.getInteger(s));
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

        gui = new JFrame("TicTacToe");
        gui.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    private String getInput(String message) {
        return (String)JOptionPane.showInputDialog(gui, message);
    }

    private class ButtonListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            String cmd = e.getActionCommand();
            switch (cmd) {
//                case condition -> code;
            }
        }
    }
}
