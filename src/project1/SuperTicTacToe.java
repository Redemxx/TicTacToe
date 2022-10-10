package project1;

public class SuperTicTacToe {

    private SuperTicTacToeGame game;
    private SuperTicTacToePanel gameWindow;

    public void Main(String[] args) {
        game = new SuperTicTacToeGame();
        gameWindow = new SuperTicTacToePanel(game);
    }
}
