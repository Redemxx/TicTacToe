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

    public SuperTicTacToe(SuperTicTacToeGame move){
        moves_history.add(move); // everytime a move is made call this function and add the board first than create the new board.
    }


}
