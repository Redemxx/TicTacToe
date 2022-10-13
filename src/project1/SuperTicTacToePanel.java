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
    private Cell[][] cells;
    private ButtonListener buttonListener;
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
        buttonListener = new ButtonListener();

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

        int cnt = 0;
        for (int a = 0; a < jButtonsBoard.length; a++) {
            for (int b = 0; b < jButtonsBoard[0].length; b++) {
                jButtonsBoard[a][b] = new JButton("", emptyIcon);
                jButtonsBoard[a][b].addActionListener(buttonListener);
                jButtonsBoard[a][b].setName(String.valueOf(cnt));
//                gui.add(buttons[i][k]);
//                buttons[i][k].setBorder(BorderFactory.createLineBorder(Color.BLACK, 4));
                gui.add(jButtonsBoard[a][b]);
                jButtonsBoard[a][b].setBorder(BorderFactory.createLineBorder(Color.BLACK, 4));
                ++cnt;
            }
        }

        cells = new Cell[s][s];
        for (int a = 0; a < cells.length; a++) {
            for (int b = 0; b < cells[0].length; b++) {
                cells[a][b] = Cell.EMPTY;
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

    private void updateBoard() {
        cells = game.getboard();

        int cnt = 0;
        int dim = game.getDimension();
        for (int a = 0; a < dim; a++) {
            for (int b = 0; b < dim; b++) {
//                if (cells[a][b] == Cell.EMPTY)
//                    continue;
                if (cells[a][b] == Cell.X) {
                    jButtonsBoard[cnt / dim][cnt % dim].setIcon(xIcon);
                }
                if (cells[a][b] == Cell.O) {
                    jButtonsBoard[cnt / dim][cnt % dim].setIcon(oIcon);
                }
                ++cnt;
            }
        }
    }

    private class ButtonListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            JButton source = (JButton)e.getSource();
            int cmd = Integer.parseInt(source.getName());
            int row = cmd / game.getDimension();
            int col = cmd % game.getDimension();

            game.select(row, col);
            updateBoard();
        }
    }
}
