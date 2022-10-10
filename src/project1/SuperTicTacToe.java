package project1;

public class SuperTicTacToe {

    private SuperTicTacToeGame game;
    private SuperTicTacToePanel gameWindow;

    public SuperTicTacToe() {
        game = new SuperTicTacToeGame();
        gameWindow = new SuperTicTacToePanel(game);
    }
}
