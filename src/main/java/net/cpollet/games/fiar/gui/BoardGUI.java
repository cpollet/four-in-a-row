package net.cpollet.games.fiar.gui;

import net.cpollet.games.fiar.Game;
import net.cpollet.games.fiar.GameHumanInterface;
import net.cpollet.games.fiar.rules.Player;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;

public class BoardGUI extends JFrame implements GameHumanInterface {
    public JPanel panel1;
    private JPanel board;
    private JLabel lbl;
    private JButton newGameButton;

    private JLabel[][] label;

    private boolean choiceEnabled = false;
    private boolean choiceMade = false;
    private int choice;

    public BoardGUI(final int height, final int width) throws HeadlessException {
        board.setSize(400, 400);
        board.setLayout(new GridLayout(height, width, 5, 5));

        board.setComponentOrientation(ComponentOrientation.LEFT_TO_RIGHT);

        Border border = BorderFactory.createLineBorder(Color.BLACK, 1);
        label = new JLabel[width][height];
        for (int row = 0; row < height; row++) {
            for (int column = 0; column < width; column++) {
                label[row][column] = new JLabel();
                label[row][column].setHorizontalTextPosition(JLabel.CENTER);
                label[row][column].setVerticalTextPosition(JLabel.CENTER);
                label[row][column].setBorder(border);
                label[row][column].addMouseListener(new CellChoiceListener(row, column));
                board.add(label[row][column], row, column);
            }
        }

//        newGameButton.addActionListener(new ActionListener() {
//            @Override
//            public void actionPerformed(ActionEvent e) {
//                for (int row = 0; row < height; row++) {
//                    for (int column = 0; column < width; column++) {
//                        label[row][column].setBackground(Color.GRAY);
//                    }
//                }
//            }
//        });
    }

    private boolean isChoiceEnabled() {
        return choiceEnabled;
    }

    private boolean isChoiceMade() {
        return choiceMade;
    }

    private int getChoice() {
        return choice;
    }

    @Override
    public int getColumnChoice(Player player) {
        lbl.setText("Player " + player);

        choiceMade = false;
        choiceEnabled = true;

        FutureTask<Integer> columnChoice = new FutureTask<>(new Callable<Integer>() {
            @Override
            public Integer call() throws Exception {
                while (!isChoiceMade()) {
                    Thread.sleep(10l);
                }

                return getChoice();
            }
        });

        new Thread(columnChoice).start();

        try {
            return columnChoice.get();
        } catch (InterruptedException | ExecutionException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void updateCell(int row, int column, Player player) {
        if (player == Player.ONE) {
            label[row][column].setBackground(Color.ORANGE);
        } else {
            label[row][column].setBackground(Color.RED);
        }

        label[row][column].setOpaque(true);
    }

    @Override
    public void displayWinner(Player player) {
        JOptionPane.showMessageDialog(null, "Player " + player + " won the game", "WINNNER", JOptionPane.WARNING_MESSAGE);
        lbl.setText("Player " + player + " won the game");
        // newGameButton.setVisible(true);
    }

    @Override
    public void displayNoWinner() {
        JOptionPane.showMessageDialog(null, "No winner", "TIE", JOptionPane.WARNING_MESSAGE);
        lbl.setText("No winner");
        // newGameButton.setVisible(true);
    }

    private class CellChoiceListener extends MouseAdapter {
        private final int row;
        private final int column;

        public CellChoiceListener(int row, int column) {
            this.row = row;
            this.column = column;
        }

        @Override
        public void mouseClicked(MouseEvent e) {
            super.mouseClicked(e);

            if (isChoiceEnabled()) {
                System.out.println("Row: " + row + "; Column:" + column);

                choice = column;
                choiceEnabled = false;
                choiceMade = true;
            }
        }
    }
}



