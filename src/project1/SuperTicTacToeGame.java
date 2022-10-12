package project1;

public class SuperTicTacToeGame {
    private final Cell[][] game_board;
    private final int dimension;
    private GameStatus game_status;

    // false for player 1, X
    // true for player 2, O
    private Boolean current_turn;

    public int getDimension() {
        return dimension;
    }

    /**
     * Creates a new game-board with default size of 3x3.
     */
    public SuperTicTacToeGame() {
        game_board = new Cell[3][3];
        dimension = 3;
        instantiateBoard();
        game_status = GameStatus.IN_PROGRESS;
    }

    /**
     * Creates a new game-board with a given size which corresponds to length and height.
     *
     * @param size Int length and height of the board.
     */
    public SuperTicTacToeGame(int size) {
        game_board = new Cell[size][size];
        dimension = size;
        instantiateBoard();
        game_status = GameStatus.IN_PROGRESS;
    }

    /*
        Called from constructors; sets all cells in game_board to Cell.EMPTY
     */
    private void instantiateBoard() {
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
    }

    public Cell[][] getboard() {
        return game_board;
    }

    public void reset() {
        instantiateBoard();
        current_turn = false;
        game_status = GameStatus.IN_PROGRESS;
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
        int countO = 0;
        int countX = 0;

        for (int col = 0; col < game_board.length; col++) { // O horizontal win condition...

            if (game_board[0][col] == Cell.O) { //Maybe a nested for loop to check multiple rows?
                countO++;
            }
            if (game_board[0][col] == Cell.X) {
                System.out.println("Blocked!");
                countO = 0;
            }
            if (countO == 3) {
                countO = 0;
                return game_status = GameStatus.O_WON;
            }
        }

        for (int row = 0; row < game_board.length; row++) { // O vertical win condition...
            if(game_board[row][0] == Cell.O){ //Maybe a nested for loop to check multiple columns?
                countO++;
                if(game_board[row][0] == Cell.X){
                    System.out.println("Blocked!");
                    countO = 0;
                }
                if(countO == 3){
                    countO = 0;
                    return game_status = GameStatus.X_WON;
                }
                }
        }

        for (int col = 0; col < game_board.length; col++) { // O diagonal win condition...
            for(int row = 0; row < game_board.length; row++){
                if(game_board[row][col] == Cell.O) { //it checks downwards.
                    countO++;
                }
                if(game_board[row][col] == Cell.X){
                    System.out.println("Blocked!");
                    countO = 0;
                }
                if(countO == 3){
                    countO = 0;
                    return game_status = GameStatus.O_WON;
                }

            }

        }

        for (int col = 0; col < game_board.length; col++) { // X horizontal win condition...
            if(game_board[0][col] == Cell.X){
                countX++;
                if(game_board[0][col] == Cell.O){
                    System.out.println("Blocked!");
                    countX = 0;
                }
                if(countX == 3){
                    countX = 0;
                    return game_status = GameStatus.X_WON;
                }
            }
        }

        return game_status;
    }
}