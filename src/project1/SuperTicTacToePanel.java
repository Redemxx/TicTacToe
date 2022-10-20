package project1;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.sql.Time;
import java.util.ArrayList;

public class SuperTicTacToePanel extends JPanel {

    private SuperTicTacToeGame game;
    private boolean current_player;
    private SuperTicTacToeGame.ai_type[] ai_states;
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
    private JMenuItem changeSizeItem;
    private JPanel game_panel;
    private JPanel button_panel;
    private JLabel playerTurn;
    private Boolean in_thread;
    private Boolean undoing;

    public SuperTicTacToeGame get_game() {
        return this.game;
    }
    public SuperTicTacToePanel() {
        int size = getSizeInput("Enter desired size of the TicTacToe board:");
        int winLength = getWinLength("How many in a row for a win?", size);
        String turn = getFirstTurn("Who starts first? X or O");
        game = new SuperTicTacToeGame(size, turn, winLength);
        instantiateInstance();
    }

    public SuperTicTacToePanel(SuperTicTacToeGame game) {
        this.game = game;
        instantiateInstance();
    }

    private void instantiateInstance() {
        undoing = false;
        in_thread = false;
        xIcon = new ImageIcon(getClass().getResource("x.png"));
        oIcon = new ImageIcon(getClass().getResource("o.png"));
        emptyIcon = new ImageIcon();
        buttonListener = new ButtonListener();

        moves_history = new ArrayList<SuperTicTacToeGame>();
        current_player = false;

        gui = new JFrame("Super Tic-Tac-Toe");
        gui.setLayout(new GridBagLayout());
        gui.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        undo = new JButton("Undo!");
        enable_ai = new JButton("Enable AI!");

        if(game.getTurn()){
            playerTurn = new JLabel("O's turn");
        } else{
            playerTurn = new JLabel("X's Turn");
        }

        fileMenu = new JMenu("File");
        quitItem = new JMenuItem("Quit!");
        undoItem = new JMenuItem("Undo!");
        changeSizeItem = new JMenuItem("Change Board Size");

        fileMenu.add(quitItem);
        fileMenu.add(undoItem);
        fileMenu.add(changeSizeItem);

        quitItem.addActionListener(buttonListener::actionPerformed_quit);
        undoItem.addActionListener(buttonListener::actionPerformed_undo);
        changeSizeItem.addActionListener(buttonListener::actionPerformed_size);

        undo.addActionListener(buttonListener::actionPerformed_undo);
        enable_ai.addActionListener(buttonListener::actionPerformed_enableai);

        JMenuBar menus = new JMenuBar();
        menus.add(fileMenu);

        game_panel = new JPanel();
        game_panel.setLayout(new GridLayout(game.getDimension(), game.getDimension()));
        game_panel.setPreferredSize(new Dimension(500, 500));

        button_panel = new JPanel();
        button_panel.setLayout(new GridLayout(3, 1));
        button_panel.add(undo);
        button_panel.add(enable_ai);
        button_panel.add(playerTurn);

        int s = game.getDimension();
        jButtonsBoard = new JButton[s][s];

        int cnt = 0;

        // Border size vars
        int top = 0;
        int left = 0;
        int bottom = 0;
        int right = 0;

        int border_width = (int) (6 - (game.getDimension() - 3 ) * 0.25);

        for (int a = 0; a < jButtonsBoard.length; a++) {
            for (int b = 0; b < jButtonsBoard[0].length; b++) {
                jButtonsBoard[a][b] = new JButton("", emptyIcon);
                jButtonsBoard[a][b].addActionListener(buttonListener);
                jButtonsBoard[a][b].setName(String.valueOf(cnt));
                game_panel.add(jButtonsBoard[a][b]);
                if (a == 0)
                    top = 0;
                else
                    top = border_width;

                if (b == 0)
                    left = 0;
                else
                    left = border_width;

                if (a == game.getDimension()-1)
                    bottom = 0;
                else
                    bottom = border_width;

                if (b == game.getDimension()-1)
                    right = 0;
                else
                    right = border_width;

                jButtonsBoard[a][b].setBorder(BorderFactory.createMatteBorder(top, left, bottom, right, Color.BLACK));
                ++cnt;
            }
        }

        cells = new Cell[s][s];
        for (int a = 0; a < cells.length; a++) {
            for (int b = 0; b < cells[0].length; b++) {
                cells[a][b] = Cell.EMPTY;
            }
        }
        GridBagConstraints c = new GridBagConstraints();
        c.fill = GridBagConstraints.VERTICAL;
        c.gridx = 0;
        c.gridy = 0;
        c.gridheight = 3;
        gui.getContentPane().add(button_panel, c);
        c.fill = GridBagConstraints.BOTH;
        c.gridx = 1;
        c.gridy = 0;
        c.gridheight = 3;
        c.gridwidth = 3;
        gui.getContentPane().add(game_panel, c);
        gui.setSize(610, 560);
        gui.setJMenuBar(menus);
        gui.setVisible(true);

        ai_states = new SuperTicTacToeGame.ai_type[] {SuperTicTacToeGame.ai_type.NONE, SuperTicTacToeGame.ai_type.NONE};
    }

    private int getSizeInput(String message) {
        int input_size = 0;

        // Attempt to get size of board from user
        try {
            while (input_size <= 2 || input_size >= 15) {
                String s = (String) JOptionPane.showInputDialog(null, message);
                if (s == null) {
                    System.exit(0);
                }
                input_size = Integer.parseInt(s);
            }
        }catch (Exception e) { // Default to 3 if user inputs invalid size or closes window
            input_size = 3;
        }
        return input_size;
    }

    private String getFirstTurn(String message) {
        String input_turn = "";

        // Gets first turn input from user
        try {
            String s = (String) JOptionPane.showInputDialog(null, message);
            input_turn = s;
        } catch (Exception e) { // Default turn is X if no input or window is closed
            input_turn = "x";
        }

        if (input_turn == null)
            input_turn = "x";
        return input_turn;
    }

    private int getWinLength(String message, int boardSize) {
        int input_winLength = 0;

        // if boardSize is 3, then win length is 3 by default
        if(boardSize == 3) return 3;

        // Gets win-length input from user
        try {
            while( input_winLength <= 3 || input_winLength > boardSize) {
                String s = (String) JOptionPane.showInputDialog(null, message);
                input_winLength = Integer.parseInt(s);
            }
        } catch (Exception e) { // Default win length is 4 if board size is greater than 3
            input_winLength = 4;
        }
        return input_winLength;
    }

    private SuperTicTacToeGame.ai_type getAiType(String message) {
        int type;
        // Gets win-length input from user
        try {
                type = (int)
                        JOptionPane.showOptionDialog(null, message, "Choose AI",
                                JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE, new ImageIcon(),
                                new String[]{"Easy (Random)", "Hard (Analyzes)"}, "Easy (Random)");
        } catch (Exception e) { // Default win length is 4 if board size is greater than 3
            type = 2;
        }
        SuperTicTacToeGame.ai_type return_type = SuperTicTacToeGame.ai_type.NONE;
        switch (type) {
            case 0 -> return_type = SuperTicTacToeGame.ai_type.EASY;
            case 1 -> return_type = SuperTicTacToeGame.ai_type.HARD;
            case 2 -> return_type = SuperTicTacToeGame.ai_type.NONE;
        }
        return return_type;
    }

    private void displayBoard() {
        cells = game.getboard();

        if(game.getTurn()){
            playerTurn.setText("O's turn");
        } else{
            playerTurn.setText("X's turn");
        }

        int cnt = 0;
        int dim = game.getDimension();
        for (int a = 0; a < dim; a++) {
            for (int b = 0; b < dim; b++) {
                if (cells[a][b] == Cell.EMPTY) {
                    jButtonsBoard[cnt / dim][cnt % dim].setIcon(emptyIcon);
                    // Resets the board to be clickable again after a restart;
                    // where all the buttons would have the name -1 because they were
                    // used in the previous game.
                    if (jButtonsBoard[a][b].getName().equals("-1")) {
                        jButtonsBoard[a][b].setName(String.valueOf(cnt));
                    }
                }
                if (cells[a][b] == Cell.X) {
                    jButtonsBoard[cnt / dim][cnt % dim].setIcon(
                            new ImageIcon(xIcon.getImage().getScaledInstance(
                                    jButtonsBoard[a][b].getWidth(), -1, Image.SCALE_SMOOTH)));
                }
                if (cells[a][b] == Cell.O) {
                    jButtonsBoard[cnt / dim][cnt % dim].setIcon(
                            new ImageIcon(oIcon.getImage().getScaledInstance(
                            jButtonsBoard[a][b].getWidth(), -1, Image.SCALE_SMOOTH)));
                }
                ++cnt;
            }
        }

        GameStatus gameStatus = game.getGameStatus();
        if (gameStatus != GameStatus.IN_PROGRESS) {
            showStatus(gameStatus);
            game.reset();
            ai_states = new SuperTicTacToeGame.ai_type[] {SuperTicTacToeGame.ai_type.NONE,
                    SuperTicTacToeGame.ai_type.NONE};
            displayBoard();
            moves_history = new ArrayList<SuperTicTacToeGame>();
        }

        if (undoing)
            return;

        if (!in_thread)
            new Thread(this::checkAiPlay).start();
        else {
            in_thread = false;
            checkAiPlay();
        }
    }

    private void showStatus(GameStatus statuus) {
        if (statuus == GameStatus.O_WON) {
            JOptionPane.showMessageDialog(null, "Player O won!");
        } else if (statuus == GameStatus.X_WON) {
            JOptionPane.showMessageDialog(null, "Player X won!");
        }else if (statuus == GameStatus.CATS) {
            JOptionPane.showMessageDialog(null, "No winners, it's a Tie!");
        }
    }

    private void checkAiPlay() {
        int player = current_player ? 1 : 0;
        if (!ai_states[player].equals(SuperTicTacToeGame.ai_type.NONE)) {
            try{
                Thread.sleep(500);
            }catch (Exception ignored) {}

            player = current_player ? 1 : 0;
            if (ai_states[player].equals(SuperTicTacToeGame.ai_type.NONE))
                return;
            moves_history.add(new SuperTicTacToeGame(game));
            if (ai_states[player] == SuperTicTacToeGame.ai_type.EASY)
                game.ai_choose();
            else if (ai_states[player] == SuperTicTacToeGame.ai_type.HARD)
                game.justin_choose();
            current_player = !current_player;
            in_thread = true;
            displayBoard();
        }
    }

    private class ButtonListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            JButton source = (JButton)e.getSource();
            int cmd = Integer.parseInt(source.getName());
            if (cmd == -1){
                return;
            }
            int row = cmd / game.getDimension();
            int col = cmd % game.getDimension();

            int player = current_player ? 1 : 0;
            if (ai_states[player].equals(SuperTicTacToeGame.ai_type.NONE)) {
                moves_history.add(new SuperTicTacToeGame(game));
                try {
                    game.select(row, col);
                }catch (Exception E) {
                    return;
                }
                jButtonsBoard[row][col].setName("-1");
                current_player = !current_player;
                displayBoard();
            }
        }

        public void actionPerformed_undo(ActionEvent e) {
            if (moves_history.size() == 0)
                return;
            undoing = true;
            game = moves_history.get(moves_history.size()-1);
            moves_history.remove(moves_history.size()-1);
            current_player = !current_player;
            undoing = false;
            displayBoard();
        }

        public void actionPerformed_quit(ActionEvent e) {
            // ActionListener for QUIT button
            System.exit(0);
        }

        public void actionPerformed_enableai(ActionEvent e) {
            // ActionListener for ENABLE AI button
            int player = current_player ? 1 : 0;
            if (ai_states[player] != SuperTicTacToeGame.ai_type.NONE)
                return;
            SuperTicTacToeGame.ai_type type = getAiType("Choose an ai type");

            if (current_player) {
                ai_states[1] = type;
            }else{
                ai_states[0] = type;
            }

            displayBoard();
        }
        public void actionPerformed_size(ActionEvent e) {
            // ActionListener for changing board size
            gui.dispose();
            game = new SuperTicTacToePanel().get_game();
        }
    }
}
