package project1;

import java.util.ArrayList;

public class SuperTicTacToe {

    private SuperTicTacToeGame game;
    private SuperTicTacToePanel game_panel;

    public SuperTicTacToe() {
        game_panel = new SuperTicTacToePanel();
        game = game_panel.get_game();
    }

    public static void main(String[] args) {
        SuperTicTacToe main_thread = new SuperTicTacToe();
    }

}
