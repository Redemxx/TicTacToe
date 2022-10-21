package project1;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

/**
 * Class SuperTicTacToePanel extends JPanel, allowing it to create a window to display a TicTacToe
 * game using SuperTicTacToeGame.
 */
public class SuperTicTacToePanel extends JPanel {

    /**
     * SuperTicTacToeGame game.
     */
    private SuperTicTacToeGame game;
    /**
     * Represents the current player.
     */
    private boolean current_player;
    /**
     * SuperTicTacToeGame.ai_type[] of length 2 that represent the player type.
     */
    private SuperTicTacToeGame.ai_type[] ai_states;
    /**
     * ArrayList of SuperTicTacToeGame to hold all move history for the current game.
     */
    private ArrayList<SuperTicTacToeGame> moves_history;
    /**
     * Main JPanel object; gui
     */
    private JFrame gui;
    /**
     * Stores 2-dim array of JButtons to construct grid for the game.
     */
    private JButton[][] jButtonsBoard;
    /**
     * Button for handling undo inputs
     */
    private JButton undo;
    /**
     * Button for handling enabling AI
     */
    private JButton enable_ai;
    /**
     * 2-dim array of Cells to represent the current game board.
     */
    private Cell[][] cells;
    /**
     * Creates an instance of ButtonListener to assign as an actionListener to the JButtons.
     */
    private ButtonListener buttonListener;
    /**
     * Holds the X icon to assign to JButtons the X player plays.
     */
    private ImageIcon xIcon;
    /**
     * Holds the O icon to assign to JButtons the O player plays.
     */
    private ImageIcon oIcon;
    /**
     * Emtpy icon to display in empty cells
     */
    private ImageIcon emptyIcon;
    /**
     * JMenu to hold file menu options quit, undo, and resize board.
     */
    private JMenu fileMenu;
    /**
     * Quit button for JMenu
     */
    private JMenuItem quitItem;
    /**
     * Undo button for JMenu
     */
    private JMenuItem undoItem;
    /**
     * Change Size button for JMenu
     */
    private JMenuItem changeSizeItem;
    /**
     * JPanel that contains the JButtons that represent the GameBoard.
     */
    private JPanel game_panel;
    /**
     * JPanel that contains actions buttons undo, enable AI, and the JLabel playerTurn.
     */
    private JPanel button_panel;
    /**
     * JLabel that displays the current player.
     */
    private JLabel playerTurn;
    /**
     * Boolean that is true then the program is currently running in a spawned thread.
     */
    private Boolean in_thread;
    /**
     * Boolean to notify spawned threads to quit if possible because the user is undoing a move.
     */
    private Boolean undoing;

    /**
     * Returns the game object
     * @return SuperTicTacToeGame object
     */
    public SuperTicTacToeGame get_game() {
        return this.game;
    }

    /**
     * SuperTicTacToePanel constructor; collects the information needed
     * to instantiate all non-predetermined variables
     */
    public SuperTicTacToePanel() {
        // Before instantiating a new SuperTicTacToePanel and SuperTicTacGame, we first gather inputs from the user
        int size = getSizeInput("Enter desired size of the TicTacToe board:"); // collects size of board
        int winLength = getWinLength("How many in a row for a win?", size); // gets win-length
        String turn = getFirstTurn("Who starts first? X or O"); // determines who goes first
        game = new SuperTicTacToeGame(size, turn, winLength); // creates SuperTicTacToeGame
        instantiateInstance(); // creates SuperTicTacToePanel
    }

    /**
     * SuperTicTacToePanel constructor; creates a new panel using a TicTacToeGame object.
     * @param game SuperTicTacToeGame to use.
     */
    public SuperTicTacToePanel(SuperTicTacToeGame game) {
        this.game = game;
        instantiateInstance();
    }

    /**
     * instantiateInstance creates and displays the main JFrame holding the
     * game board JPanel, file menu, and other JPanel which holds the buttons.
     * Also sets the value of needed runtime variables used by the class.
     */
    private void instantiateInstance() {

        // Creates icons for each player
        xIcon = new ImageIcon(getClass().getResource("x.png"));
        oIcon = new ImageIcon(getClass().getResource("o.png"));
        emptyIcon = new ImageIcon();
        buttonListener = new ButtonListener();

        // Move tracking
        moves_history = new ArrayList<SuperTicTacToeGame>();
        current_player = false;
        undoing = false;
        in_thread = false;

        // Sets the default states of the players to be controlled by a user
        ai_states = new SuperTicTacToeGame.ai_type[] {SuperTicTacToeGame.ai_type.NONE, SuperTicTacToeGame.ai_type.NONE};

        // Creates GUI
        gui = new JFrame("Super Tic-Tac-Toe");
        gui.setLayout(new GridBagLayout());
        gui.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // creates buttons to be placed in buttonPanel
        undo = new JButton("Undo!");
        enable_ai = new JButton("Enable AI!");

        // creates JLabel playerTurn to display the current turn, also to be placed in buttonPanel
        if(game.getTurn()){
            playerTurn = new JLabel("O's turn"); // if O's turn, set label to "O"
        } else{
            playerTurn = new JLabel("X's Turn"); // if X's turn, set label to "X"
        }

        // instantiates all elements of the file menu (top left)
        fileMenu = new JMenu("File");
        quitItem = new JMenuItem("Quit!");
        undoItem = new JMenuItem("Undo!");
        changeSizeItem = new JMenuItem("Change Board Size");

        // adds the elements above^ to the file menu
        fileMenu.add(quitItem);
        fileMenu.add(undoItem);
        fileMenu.add(changeSizeItem);

        // adds an individual actionListener to each file menu element
        quitItem.addActionListener(buttonListener::actionPerformed_quit);
        undoItem.addActionListener(buttonListener::actionPerformed_undo);
        changeSizeItem.addActionListener(buttonListener::actionPerformed_size);

        // adds individual actionListeners to the buttons displayed in buttonPanel
        undo.addActionListener(buttonListener::actionPerformed_undo);
        enable_ai.addActionListener(buttonListener::actionPerformed_enableai);

        // instantiates menu to hold fileMenu
        JMenuBar menus = new JMenuBar();
        menus.add(fileMenu);

        // creates a game_panel JPanel to hold our Tic Tac Toe game within the gui
        game_panel = new JPanel();
        game_panel.setLayout(new GridLayout(game.getDimension(), game.getDimension()));
        game_panel.setPreferredSize(new Dimension(500, 500));

        // creates the button_panel to hold our undo and enable_ai buttons, then display playerTurn
        button_panel = new JPanel();
        button_panel.setLayout(new GridLayout(3, 1));
        button_panel.add(undo);
        button_panel.add(enable_ai);
        button_panel.add(playerTurn);

        // Creates the jButtonsBoard
        int s = game.getDimension();
        jButtonsBoard = new JButton[s][s];

        int cnt = 0;

        // Border size vars
        int top = 0;
        int left = 0;
        int bottom = 0;
        int right = 0;

        // Sets the size of the boarders of the JButtons to scale properly with grid size.
        int border_width = (int) (6 - (game.getDimension() - 3 ) * 0.25);

        // creates the array of JButtons to hold X's and O's and sets a button border depending
        // on where the button is located in the array so that the array of buttons looks like
        // a true TicTacToe board when formatted in the gui
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

        // sets all cells in the array to enum EMPTY
        cells = new Cell[s][s];
        for (int a = 0; a < cells.length; a++) {
            for (int b = 0; b < cells[0].length; b++) {
                cells[a][b] = Cell.EMPTY;
            }
        }

        // formats the buttonPanel elements
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
    }

    /**
     * getSizeInput creates a popup dialogue box when called allowing the user to input the size of the board.
     * Allowed inputs: [3-14].
     * @param message is the dialogue informing the user what they are inputting.
     * @return the size of the board
     */
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

    /**
     * getFirstTurn creates a popup dialogue box when called allowing the user to input who will go first.
     * Will default to player X if an invalid entry is given.
     * @param message is the dialogue informing the user what they are inputting.
     * @return The player that goes first.
     */
    private String getFirstTurn(String message) {
        String input_turn = "";

        // Gets first turn input from user
        try {
            String s = (String) JOptionPane.showInputDialog(null, message);
            input_turn = s;
        } catch (Exception e) { // Default turn is X if no input or window is closed
            input_turn = "x";
        }

        if (input_turn == null) // no input sets the first turn to player X
            input_turn = "x";
        return input_turn;
    }

    /**
     * getWinLength creates a popup dialogue box when called allowing the user to input how many
     * cells to chain in order to win. When the game is larger than 3x3, the user can only
     * choose between 4 and the board size. If a non-integer answer is given, the game will default to 4.
     * @param message is the dialogue informing the user what they are inputting.
     * @param boardSize takes in the size of the game board to determine if win-length
     *                 needs to be at least 4 and less than the board size.
     * @return The win-length to determine a win.
     */
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

    /**
     * Prompts the user to choose an AI to enable on the currently playing user. [Easy, Hard].
     * @param message Message to display to the user.
     * @return SuperTicTacToeGame.ai_type that describes the player type. Defaults to user input
     *          if closed or invalid entry given.
     */
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

    /**
     * Updates all graphics on the JPanel gui. Checks if an AI needs to play for the next turn.
     * Calls getGameStatus of game when ran. If the game is finished, it will notify the user and reset the game.
     */
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
                                    jButtonsBoard[a][b].getWidth(), -1, Image.SCALE_SMOOTH))); // takes game images and makes them scalable
                }
                if (cells[a][b] == Cell.O) {
                    jButtonsBoard[cnt / dim][cnt % dim].setIcon(
                            new ImageIcon(oIcon.getImage().getScaledInstance(
                            jButtonsBoard[a][b].getWidth(), -1, Image.SCALE_SMOOTH))); // takes game images and makes them scalable
                }
                ++cnt;
            }
        }

        // Checks the status of the game and resets if finished.
        GameStatus gameStatus = game.getGameStatus();
        if (gameStatus != GameStatus.IN_PROGRESS) {
            showStatus(gameStatus);
            game.reset();
            ai_states = new SuperTicTacToeGame.ai_type[] {SuperTicTacToeGame.ai_type.NONE,
                    SuperTicTacToeGame.ai_type.NONE};
            displayBoard();
            moves_history = new ArrayList<SuperTicTacToeGame>();
        }

        // Safety check to prevent AI from running when undoing moves.
        if (undoing)
            return;

        /* Checks if the call is currently in a thread or not. Checks if an AI needs to play the next turn.
        *  I needed to use threads because the board would not update graphically until after the AI plays causing
        *  an unsatisfying instant placing effect during gameplay. The thread is only necessary once after
        *  the actionPerformed call, therefor in implemented a simple check as I imagine spawning hundreds
        *  of threads is not very good for performance.
        */
        if (!in_thread)
            new Thread(this::checkAiPlay).start();
        else {
            in_thread = false;
            checkAiPlay();
        }
    }

    /**
     * Helper method that displays a JOptionPane notifying the user of the end of a game and the winner.
     * @param status GameStatus to report to the user.
     */
    private void showStatus(GameStatus status) {
        if (status == GameStatus.O_WON) {
            JOptionPane.showMessageDialog(null, "Player O won!");
        } else if (status == GameStatus.X_WON) {
            JOptionPane.showMessageDialog(null, "Player X won!");
        }else if (status == GameStatus.CATS) {
            JOptionPane.showMessageDialog(null, "No winners, it's a Tie!");
        }
    }

    /**
     * Checks if the next player is an AI, calls the appropriate AI method if so.
     */
    private void checkAiPlay() {
        int player = current_player ? 1 : 0;
        if (!ai_states[player].equals(SuperTicTacToeGame.ai_type.NONE)) {
            // Adds delay to AI to feel more like a player.
            try{
                Thread.sleep(500);
            }catch (Exception ignored) {} // Ignores exception as this is only to cause a delay for the user.

            player = current_player ? 1 : 0;

            // If the player is a user, exit
            if (ai_states[player].equals(SuperTicTacToeGame.ai_type.NONE))
                return;

            // Player is not user, so add current game to the moves history and call appropriate
            // AI method.
            moves_history.add(new SuperTicTacToeGame(game));
            if (ai_states[player] == SuperTicTacToeGame.ai_type.EASY)
                game.ai_choose();
            else if (ai_states[player] == SuperTicTacToeGame.ai_type.HARD)
                game.justin_choose();

            // Re-display board
            current_player = !current_player;
            in_thread = true;
            displayBoard();
        }
    }

    /**
     * Acts as the router for the GUI's buttons. Has 5 'actionPerformed' functions
     * to handle player moves, undos, quit, AI, and changing the board size.
     */
    private class ButtonListener implements ActionListener {

        // Used for processing player input to place a Tac (X) or Toe (O).
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

        // Processes user request to undo a move
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

        // Quits the program
        public void actionPerformed_quit(ActionEvent e) {
            // ActionListener for QUIT button
            System.exit(0);
        }

        // ActionListener for ENABLE AI button
        public void actionPerformed_enableai(ActionEvent e) {
            int player = current_player ? 1 : 0;

            // Checks if the current player is already an AI, returns if so.
            if (ai_states[player] != SuperTicTacToeGame.ai_type.NONE)
                return;
            SuperTicTacToeGame.ai_type type = getAiType("Choose an ai type");

            // Sets the current player to the returned ai_type
            if (current_player) {
                ai_states[1] = type;
            }else{
                ai_states[0] = type;
            }

            // Calls displayBoard to prompt set AI to play
            displayBoard();
        }

        // Prompts the user to configure a new game, allowing them to change the size
        // and winning length.
        public void actionPerformed_size(ActionEvent e) {
            // ActionListener for changing board size
            gui.dispose(); // closes last game
            game = new SuperTicTacToePanel().get_game(); // creates new game
        }
    }
}
