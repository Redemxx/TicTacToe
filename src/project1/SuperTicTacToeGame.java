package project1;

import javax.swing.*;
import java.util.ArrayList;

public class SuperTicTacToeGame {
    private final Cell[][] game_board;
    private final int dimension;
    private GameStatus game_status;

    // false for player 1, X
    // true for player 2, O
    private Boolean current_turn;
    private Boolean x_first;
    private int placed;
    private final int win_int;

    public int getDimension() {
        return dimension;
    }

    public boolean getTurn() {
        return current_turn;
    }


    /**
     * Creates a new game-board with default size of 3x3.
     */
    public SuperTicTacToeGame() {
        game_board = new Cell[3][3];
        x_first = false;
        dimension = 3;
        win_int = 3;
        instantiateBoard();
        game_status = GameStatus.IN_PROGRESS;
    }

    public SuperTicTacToeGame(SuperTicTacToeGame game) {
        dimension = game.dimension;
        game_board = new Cell[dimension][dimension];
        Cell[][] temp_cells = game.getboard();
        for (int a = 0; a < game_board.length; a++) {
            for (int b = 0; b < game_board[0].length; b++) {
                if (temp_cells[a][b] == Cell.EMPTY)
                    game_board[a][b] = Cell.EMPTY;
                else if (temp_cells[a][b] == Cell.X)
                    game_board[a][b] = Cell.X;
                else if (temp_cells[a][b] == Cell.O)
                    game_board[a][b] = Cell.O;
            }
        }

        win_int = game.win_int;
        game_status = game.game_status;
        placed = game.placed;
        current_turn = game.current_turn;
        x_first = game.x_first;
    }

    /**
     * Creates a new game-board with a given size which corresponds to length and height.
     *
     * @param size Int length and height of the board.
     */
    public SuperTicTacToeGame(int size, String turn, int winLength) {
        game_board = new Cell[size][size];
        dimension = size;
        win_int = winLength;

        game_status = GameStatus.IN_PROGRESS;

        if(turn.equalsIgnoreCase("x")){
            current_turn = false;
            x_first = true;
        } else if(turn.equalsIgnoreCase("o")){
            current_turn = true;
            x_first = false;
        } else{
            JOptionPane.showMessageDialog(null, "Turn defaulted to player X");
            x_first = true;
        }

        instantiateBoard();
    }

    /*
        Called from constructors; sets all cells in game_board to Cell.EMPTY
     */
    private void instantiateBoard() {
        placed = 0;
        current_turn = !x_first;
        for (int a = 0; a < game_board.length; a++) {
            for (int b = 0; b < game_board[0].length; b++) {
                game_board[a][b] = Cell.EMPTY;
            }
        }
    }

    public void select(int row, int col) {
        // Argument check for valid parameters
        if (row < 0 || row > dimension || col < 0 || col > dimension) {
            throw new IllegalArgumentException("Select location cannot be less than 0 or " +
                    "greater than current game-board size");
        }

        if (game_board[row][col] != Cell.EMPTY)
            throw new IllegalArgumentException("Cell is already played");
        // Sets the currently active player in the selected spot.
        game_board[row][col] = current_turn ? Cell.O : Cell.X;
        current_turn = !current_turn;
        ++placed;
    }

    public Cell[][] getboard() {
        return game_board;
    }

    public void reset() {
        game_status = GameStatus.IN_PROGRESS;
        instantiateBoard();
    }

    public void ai_choose() {
        // TODO: ai_choose()
        //  Method that analyzes board, picks best spot.
        //  Efficient.

        // Calculate position..

        // call select(row, col);
        boolean foundSpot = false; // created a boolean for while loop so ai can exit safely.

        while(!foundSpot) { // not true until it finds a spot that is empty.
            int randomRow = (int) (Math.random() * (dimension)); //only gets spots in the dimension of the board.
            int randomCol = (int) (Math.random() * (dimension)); //only get spots in the dimension of the board.

            if (game_board[randomRow][randomCol] == Cell.EMPTY) {
                select(randomRow, randomCol); //select spot!
                foundSpot = true; //found spot!
            }
        }

    }

    /**
     * Analyzes the board for the best spot to play and will call select() with the selected location
     */
    public void justin_choose() {
        int[][] board_weight = new int[dimension][dimension];
        int[][] board_wins = new int[dimension][dimension];
        boolean critical = false;
        int largest_spot = 0;

        for (int r = 0; r < dimension; r++) {
            for (int c = 0; c < dimension; c++) {
                if (!game_board[r][c].equals(Cell.EMPTY))
                    continue;
                int sum = 0;

                int comp = justin_choose_recursive(r,c,0) +  justin_choose_recursive(r,c,2);
                if (comp > sum)
                    sum = comp;
                comp = justin_choose_recursive(r,c,1) +  justin_choose_recursive(r,c,3);
                if (comp > sum)
                    sum = comp;
                comp = justin_choose_recursive(r,c,4) +  justin_choose_recursive(r,c,7);
                if (comp > sum)
                    sum = comp;
                comp = justin_choose_recursive(r,c,5) +  justin_choose_recursive(r,c,6);
                if (comp > sum)
                    sum = comp;

                if (sum >= win_int-1) {
                    critical = true;
                }

                if (sum > largest_spot)
                    largest_spot = sum;
                board_weight[r][c] = sum;

                // Winning moves check
                int win_sum = 0;
                comp = justin_choose_recursive_win(r,c,0) +  justin_choose_recursive_win(r,c,2);
                if (comp > win_sum)
                    win_sum = comp;
                comp = justin_choose_recursive_win(r,c,1) +  justin_choose_recursive_win(r,c,3);
                if (comp > win_sum)
                    win_sum = comp;
                comp = justin_choose_recursive_win(r,c,4) +  justin_choose_recursive_win(r,c,7);
                if (comp > win_sum)
                    win_sum = comp;
                comp = justin_choose_recursive_win(r,c,5) +  justin_choose_recursive_win(r,c,6);
                if (comp > win_sum)
                    win_sum = comp;

                board_wins[r][c] = win_sum;
            }
        }

        // Check for a winning move and play it
        for (int r = 0; r < dimension; r++) {
            for (int c = 0; c < dimension; c++) {
                if (board_wins[r][c] >= win_int-1) {
                    select(r,c);
                    return;
                }
            }
        }

        // Add some variation to behaviour
        if (!critical && Math.random() > 0.8) {
            ai_choose();
            return;
        }

        ArrayList<int[]> plays = new ArrayList<>();

        for (int r = 0; r < dimension; r++) {
            for (int c = 0; c < dimension; c++) {
                if (board_weight[r][c] == largest_spot && largest_spot != 0) {
                    plays.add(new int[]{r,c});
                }
            }
        }

        if (plays.size() == 0) {
            ai_choose();
            return;
        }

        int[] selection = plays.get((int)(Math.random() * (plays.size())));
        select(selection[0], selection[1]);
    }

    /**
     * Recursive helper method that sums up the number of the other player's Cells.
     * Checks in a direction specified by 'direction'.
     * @param r int Row to begin at
     * @param c int Col to begin at
     * @param direction int [0-7] specifying which direction to check.
     *                  {UP,LEFT,DOWN,RIGHT,DIAG-DOWN-RIGHT,DIAG-DOWN-LEFT,DIAG-UP-RIGHT,DIAG-UP-LEFT}
     * @return int sum of the other players Cells
     */
    private int justin_choose_recursive(int r, int c, int direction) {
        int row_delta = 0;
        int col_delta = 0;
        Cell enemy = current_turn ? Cell.X : Cell.O;

        switch (direction) {
            case 0 -> //Up
                    row_delta = -1;
            case 1 -> // Left
                    col_delta = -1;
            case 2 -> // Down
                    row_delta = 1;
            case 3 -> // Right
                    col_delta = 1;
            case 4 -> { // Down right
                row_delta = 1;
                col_delta = 1;
            }
            case 5 -> { // Down left
                row_delta = 1;
                col_delta = -1;
            }
            case 6 -> { // Up right
                row_delta = -1;
                col_delta = 1;
            }
            case 7 -> { // Up left
                row_delta = -1;
                col_delta = -1;
            }
        }

        r += row_delta;
        c += col_delta;

        if (r < 0 || r >= dimension || c < 0 || c >= dimension)
            return 0;

        if (game_board[r][c].equals(enemy))
            return 1 + justin_choose_recursive(r, c, direction);
        return 0;
    }

    /**
     * Recursive helper method that sums up the number of the current player's Cells.
     * Used for the AI to check for winning moves.
     * Checks in a direction specified by 'direction'.
     * @param r int Row to begin at
     * @param c int Col to begin at
     * @param direction int [0-7] specifying which direction to check.
     *                  {UP,LEFT,DOWN,RIGHT,DIAG-DOWN-RIGHT,DIAG-DOWN-LEFT,DIAG-UP-RIGHT,DIAG-UP-LEFT}
     * @return int sum of the players Cells
     */
    private int justin_choose_recursive_win(int r, int c, int direction) {
        int row_delta = 0;
        int col_delta = 0;
        Cell enemy = current_turn ? Cell.O : Cell.X;

        switch (direction) {
            case 0 -> //Up
                    row_delta = -1;
            case 1 -> // Left
                    col_delta = -1;
            case 2 -> // Down
                    row_delta = 1;
            case 3 -> // Right
                    col_delta = 1;
            case 4 -> { // Down right
                row_delta = 1;
                col_delta = 1;
            }
            case 5 -> { // Down left
                row_delta = 1;
                col_delta = -1;
            }
            case 6 -> { // Up right
                row_delta = -1;
                col_delta = 1;
            }
            case 7 -> { // Up left
                row_delta = -1;
                col_delta = -1;
            }
        }

        r += row_delta;
        c += col_delta;

        if (r < 0 || r >= dimension || c < 0 || c >= dimension)
            return 0;

        if (game_board[r][c].equals(enemy))
            return 1 + justin_choose_recursive_win(r, c, direction);
        return 0;
    }

    /**
     * Analyzes the board to check if a player has won, or if both players tied.
     * @return GameStatus representing the state of the game
     */
    public GameStatus getGameStatus() {
        int check_len = dimension - win_int + 1;

        // Checker for rows
        for (int r = 0; r < dimension; r++) {
            for (int c = 0; c < check_len; c++) {
                int sum = checkRowWin(r, c, Cell.EMPTY);
                if (sum >= win_int) {
                    if (current_turn) {
                        return GameStatus.X_WON;
                    } else {
                        return GameStatus.O_WON;
                    }
                }
            }
        }

        // Checker for cols
        for (int r = 0; r < check_len; r++) {
            for (int c = 0; c < dimension; c++) {
                int sum = checkColWin(r, c, Cell.EMPTY);
                if (sum >= win_int) {
                    if (current_turn) {
                        return GameStatus.X_WON;
                    } else {
                        return GameStatus.O_WON;
                    }
                }

            }
        }

        // Checker for right diags
        for (int r = 0; r < check_len; r++) {
            for (int c = 0; c < check_len; c++) {
                int sum = checkDiagWin(r, c, Cell.EMPTY);
                if (sum >= win_int) {
                    if (current_turn) {
                        return GameStatus.X_WON;
                    } else {
                        return GameStatus.O_WON;
                    }
                }
            }
        }

        // Checker for left diags
        // Checker for right diags
        for (int r = 0; r < check_len; r++) {
            for (int c = 0; c < check_len; c++) {
                int sum = checkLDiagWin(r, (dimension - 1) - c, Cell.EMPTY);
                if (sum >= win_int) {
                    if (current_turn) {
                        return GameStatus.X_WON;
                    } else {
                        return GameStatus.O_WON;
                    }
                }
            }
        }

        if (game_status == GameStatus.IN_PROGRESS && (placed >= dimension * dimension)) {
            game_status = GameStatus.CATS;
        }

        return game_status;
    }

    /**
     * Recursive helper method that checks how long a chain of plays is on the board horizontally.
     * @param row int row to check
     * @param col int col to start at
     * @param cell Always pass Cell.EMPTY, the point of this is the recursive functionality that checks for
     *             identical Cells chained together.
     * @return int sum of identical cells horizontally from the starting point of row, col
     */
    private int checkRowWin(int row, int col, Cell cell) {
        if (row >= dimension || col >= dimension) {
            return 0;
        }

        if (cell == Cell.EMPTY) {
            cell = game_board[row][col];
            if (cell == Cell.EMPTY)
                return 0;
        }

        Cell current_spot = game_board[row][col];
        if (current_spot == cell) {
            return 1 + checkRowWin(row, col + 1, cell);
        } else {
            return 0;
        }
    }

    /**
     * Recursive helper method that checks how long a chain of plays is on the board vertically.
     * @param row int row to check
     * @param col int col to start at
     * @param cell Always pass Cell.EMPTY, the point of this is the recursive functionality that checks for
     *             identical Cells chained together.
     * @return int sum of identical cells vertically from the starting point of row, col
     */
    private int checkColWin(int row, int col, Cell cell) {
        if (row >= dimension || col >= dimension) {
            return 0;
        }
        if (cell == Cell.EMPTY) {
            cell = game_board[row][col];
            if (cell == Cell.EMPTY)
                return 0;
        }

        Cell current_spot = game_board[row][col];
        if (current_spot == cell) {
            return 1 + checkColWin(row + 1, col, cell);
        } else {
            return 0;
        }
    }

    /**
     * Recursive helper method that checks how long a chain of plays is on the board in right diagonal.
     * @param row int row to check
     * @param col int col to start at
     * @param cell Always pass Cell.EMPTY, the point of this is the recursive functionality that checks for
     *             identical Cells chained together.
     * @return int sum of identical cells in a right diagonal from the starting point of row, col
     */
    private int checkDiagWin(int row, int col, Cell cell) {
        if (row >= dimension || col >= dimension) {
            return 0;
        }
        if (cell == Cell.EMPTY) {
            cell = game_board[row][col];
            if (cell == Cell.EMPTY)
                return 0;
        }

        Cell current_spot = game_board[row][col];
        if (current_spot == cell) {
            return 1 + checkDiagWin(row + 1, col + 1, cell);
        } else {
            return 0;
        }
    }

    /**
     * Recursive helper method that checks how long a chain of plays is on the board in left diagonal.
     * @param row int row to check
     * @param col int col to start at
     * @param cell Always pass Cell.EMPTY, the point of this is the recursive functionality that checks for
     *             identical Cells chained together.
     * @return int sum of identical cells in a left diagonal from the starting point of row, col
     */
    private int checkLDiagWin(int row, int col, Cell cell) {
        if (row >= dimension || col < 0) {
            return 0;
        }
        if (cell == Cell.EMPTY) {
            cell = game_board[row][col];
            if (cell == Cell.EMPTY)
                return 0;
        }

        Cell current_spot = game_board[row][col];
        if (current_spot == cell) {
            return 1 + checkLDiagWin(row + 1, col - 1, cell);
        } else {
            return 0;
        }
    }

    /**
     * enum ai_type holds the possible states of a player; being NONE for the user, EASY for the random AI,
     * and HARD for the AI that analyzes the board.
     */
    public enum ai_type {
        EASY,
        HARD,
        NONE
    }
}