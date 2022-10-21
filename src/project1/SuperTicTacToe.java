package project1;

/**
 * Class SuperTicTacToe creates a SuperTicTacToePanel object.
 */
public class SuperTicTacToe {

    /**
     * Holds the current game board.
     */
    private SuperTicTacToeGame game;
    /**
     * SuperTicTacToePanel to spawn and run the game.
     */
    private SuperTicTacToePanel game_panel;

    /**
     * SuperTicTacToe constructor; creates a new SuperTicTacToePanel and gets the game from the panel.
     */
    public SuperTicTacToe() {
        game_panel = new SuperTicTacToePanel();
        game = game_panel.get_game();
    }

    /**
     * Ran first at runtime, creates a new SuperTicTacToe object
     * @param args String[] passed from the OS.
     */
    public static void main(String[] args) {
        SuperTicTacToe main_thread = new SuperTicTacToe();
    }

}
