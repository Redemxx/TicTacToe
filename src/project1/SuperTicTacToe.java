package project1;

import java.util.ArrayList;

public class SuperTicTacToe {

    private SuperTicTacToeGame game;
    private ArrayList<SuperTicTacToeGame> moves_history;
    private SuperTicTacToePanel game_panel;

    public SuperTicTacToe() {
        game = new SuperTicTacToeGame();
        game_panel = new SuperTicTacToePanel(game);
    }
}
