package project1;

import javax.swing.*;
import java.awt.*;
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

    public SuperTicTacToeGame get_game() {
        return this.game;
    }
    public SuperTicTacToePanel() {
        game = new SuperTicTacToeGame(getSizeInput("Enter desired size of the TicTacToe board:"));
        instantiateInstance();
    }

    public SuperTicTacToePanel(SuperTicTacToeGame game) {
        this.game = game;
        instantiateInstance();
    }

    private void instantiateInstance() {
        xIcon = new ImageIcon(getClass().getResource("x.png"));
        oIcon = new ImageIcon(getClass().getResource("o.png"));
        emptyIcon = new ImageIcon();

        gui = new JFrame("TicTacToe");
        gui.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

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
        JMenuBar menus = new JMenuBar();
        menus.add(fileMenu);

        gui.setLayout(new GridLayout(game.getDimension(), game.getDimension()));

        int s = game.getDimension();
        jButtonsBoard = new JButton[s][s];

        for (JButton[] a : jButtonsBoard) {
            for (JButton b : a) {
                b = new JButton("", emptyIcon);
                b.addActionListener(new ButtonListener());
//                gui.add(buttons[i][k]);
//                buttons[i][k].setBorder(BorderFactory.createLineBorder(Color.BLACK, 4));
                gui.add(b);
                b.setBorder(BorderFactory.createLineBorder(Color.BLACK, 4));
            }
        }

        gui.setSize(800,800);
        gui.setJMenuBar(menus);
        gui.setVisible(true);
    }

    private int getSizeInput(String message) {
        int input_size = 0;

        // Attempt to get size of board from user
        try {
            while (input_size <= 2 || input_size >= 15  ) {
                String s = (String) JOptionPane.showInputDialog(null, message);
                input_size = Integer.parseInt(s);
            }
        }catch (Exception e) { // Default to 3 if user inputs invalid size or closes window
            input_size = 3;
        }
        return input_size;
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
