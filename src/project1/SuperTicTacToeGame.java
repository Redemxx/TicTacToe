package project1;

public class SuperTicTacToeGame {
    private Cell[][] game_board;
    private int dimension;
    private GameStatus game_status;

    // false for player 1, X
    // true for player 2, O
    private Boolean current_turn;

    public SuperTicTacToeGame() {
        game_board = new Cell[3][3];
        dimension = 3;
        instantiateBoard();
        game_status = GameStatus.IN_PROGRESS;
    }

    public SuperTicTacToeGame(int size) {
        game_board = new Cell[size][size];
        dimension = size;
        instantiateBoard();
        game_status = GameStatus.IN_PROGRESS;
    }

    private void instantiateBoard() {
        for (int a = 0; a < game_board.length; a++) {
            for (int b = 0; b < game_board[0].length; b++) {
                game_board[a][b] = Cell.EMPTY;
            }
        }
    }

    public void select(int row, int col) {
        // Argument check for valid parameters
        if (row < 0 || row > dimension || col < 0 || row > dimension) {
            throw new IllegalArgumentException("Select location cannot be less than 0 or " +
                    "greater than current game-board size");
        }

        // Sets the currently active player in the selected spot.
        game_board[row][col] = current_turn ? Cell.O : Cell.X;
    }

    public void reset() {
        // What is this for again?
    }

    public GameStatus getGame_status() {
        return GameStatus.IN_PROGRESS;
    }

    public void ai_choose() {

    }
}