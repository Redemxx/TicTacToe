package project1;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class GUIGameHolder extends JPanel implements ActionListener {

    public static void main(String[] args){

        JTextField inputField;

        JFrame gameHolder = new JFrame("TicTacToe Game");
        gameHolder.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        gameHolder.setSize(1100, 400);

        // adds the SuperTicTacToeGame after game mode has been selected and board size inputted
        gameHolder.add(new SuperTicTacToePanel(SuperTicTacToeGame(Integer.parseInt(((inputField.getText()))))));
    }

    @Override
    public void actionPerformed(ActionEvent e) {

    }
}
