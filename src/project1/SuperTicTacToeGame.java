package project1;

import java.lang.reflect.Array;

public class SuperTicTacToeGame {
    private Cell[][] game_board;
    private int dimension;
    private GameStatus game_status;

    // false for player 1, X
    // true for player 2, O
    private Boolean current_turn;
    private int placed;
    private int win_int;

    public int getDimension() {
        return dimension;
    }

    public boolean getTurn(){
        return current_turn;
    }

    /**
     * Creates a new game-board with default size of 3x3.
     */
    public SuperTicTacToeGame() {
        game_board = new Cell[3][3];
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
        instantiateBoard();
        game_status = GameStatus.IN_PROGRESS;

        if(turn.toLowerCase().equals("x")){
            current_turn = false;
        } else{
            current_turn = true;
        }
    }

    /*
        Called from constructors; sets all cells in game_board to Cell.EMPTY
     */
    private void instantiateBoard() {
        placed = 0;
        current_turn = false;
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

        // Sets the currently active player in the selected spot.
        game_board[row][col] = current_turn ? Cell.O : Cell.X;
        current_turn = !current_turn;
        ++placed;
    }

    public Cell[][] getboard() {
        return game_board;
    }

    public void reset() {
        current_turn = false;
        game_status = GameStatus.IN_PROGRESS;
        instantiateBoard();
    }

    public void ai_choose() {
        // TODO: ai_choose()
        //  Method that analyzes board, picks best spot.
        //  Efficient.

        // Calculate position..

        // call select(row, col);
        // reuse code; don't re-write
    }

    public GameStatus getGameStatus() {
        // TODO: getGameStatus()
        //  Analyze board for a winner or a draw
        //  Set game_status to status
        //  Return game_status

        int check_len = dimension - win_int + 1;

        // Checker for rows
        for (int r = 0; r < dimension; r++) {
            for (int c = 0; c < check_len; c++) {
                int sum = checkRowWin(r, c, Cell.EMPTY);
                if (sum >= win_int) {
                    if (current_turn) {
                        return GameStatus.X_WON;
                    }else {
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
                    }else {
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
                    }else {
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
                    }else {
                        return GameStatus.O_WON;
                    }
                }
            }
        }

        if (game_status == GameStatus.IN_PROGRESS && (placed == dimension*dimension)) {
            game_status = GameStatus.CATS;
        }

        //        int countO = 0;
        //        int countX = 0;
        //
        //        for (int col = 0; col < game_board.length; col++) { // O horizontal win condition...
        //
        //            if (game_board[0][col] == Cell.O) { //Maybe a nested for loop to check multiple rows?
        //                countO++;
        //            }
        //            if (game_board[0][col] == Cell.X) {
        //                System.out.println("Blocked!");
        //                countO = 0;
        //            }
        //            if (countO == 3) {
        //                countO = 0;
        //                return game_status = GameStatus.O_WON;
        //            }
        //        }
        //
        //        for (int row = 0; row < game_board.length; row++) { // O vertical win condition...
        //            if(game_board[row][0] == Cell.O){ //Maybe a nested for loop to check multiple columns?
        //                countO++;
        //                if(game_board[row][0] == Cell.X){
        //                    System.out.println("Blocked!");
        //                    countO = 0;
        //                }
        //                if(countO == 3){
        //                    countO = 0;
        //                    return game_status = GameStatus.X_WON;
        //                }
        //                }
        //        }
        //
        //        for (int col = 0; col < game_board.length; col++) { // O diagonal win condition...
        //            for(int row = 0; row < game_board.length; row++){
        //                if(game_board[row][col] == Cell.O) { //it checks downwards.
        //                    countO++;
        //                }
        //                if(game_board[row][col] == Cell.X){
        //                    System.out.println("Blocked!");
        //                    countO = 0;
        //                }
        //                if(countO == 3){
        //                    countO = 0;
        //                    return game_status = GameStatus.O_WON;
        //                }
        //
        //            }
        //
        //        }
        //
        //        for (int col = 0; col < game_board.length; col++) { // X horizontal win condition...
        //            if(game_board[0][col] == Cell.X){
        //                countX++;
        //                if(game_board[0][col] == Cell.O){
        //                    System.out.println("Blocked!");
        //                    countX = 0;
        //                }
        //                if(countX == 3){
        //                    countX = 0;
        //                    return game_status = GameStatus.X_WON;
        //                }
        //            }
        //        }
        //
        //        return game_status;

        return game_status;
    }

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
            return 1 + checkRowWin(row, col+1, cell);
        }else {
            return 0;
        }
    }

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
            return 1 + checkColWin(row+1, col, cell);
        }else {
            return 0;
        }
    }

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
            return 1 + checkDiagWin(row+1, col+1, cell);
        }else {
            return 0;
        }
    }

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
            return 1 + checkLDiagWin(row+1, col-1, cell);
        }else {
            return 0;
        }
    }
}