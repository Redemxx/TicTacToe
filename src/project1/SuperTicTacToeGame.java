package project1;

public class SuperTicTacToeGame {
    private Cell[][] game_board;
    private GameStatus game_status;
    private Boolean ai_enabled;

    public SuperTicTacToeGame() {
        game_board = new Cell[3][3];
        instantiateBoard();
        game_status = GameStatus.IN_PROGRESS;
    }

    public SuperTicTacToeGame(int size) {
        game_board = new Cell[size][size];
        instantiateBoard();
    }

    private void instantiateBoard() {
        for (int a = 0; a < game_board.length; a++) {
            for (int b = 0; b < game_board[0].length; b++) {
                game_board[a][b] = Cell.EMPTY;
            }
        }
    }
}