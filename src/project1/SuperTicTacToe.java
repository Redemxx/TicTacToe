package project1;

public class SuperTicTacToe {

    private SuperTicTacToeGame game;
    private SuperTicTacToePanel game_panel;

    public SuperTicTacToe() {
        game = new SuperTicTacToeGame();
        game_panel = new SuperTicTacToePanel(game);
    }
}
