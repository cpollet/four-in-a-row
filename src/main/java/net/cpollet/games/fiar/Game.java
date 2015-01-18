/*
 * Copyright 2015 Christophe Pollet
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package net.cpollet.games.fiar;

import net.cpollet.games.fiar.gui.BoardGUI;
import net.cpollet.games.fiar.rules.Board;
import net.cpollet.games.fiar.rules.Player;
import net.cpollet.games.fiar.rules.exception.IllegalChoiceException;
import net.cpollet.games.fiar.rules.victory.DownLeftVictoryRule;
import net.cpollet.games.fiar.rules.victory.HorizontalVictoryRule;
import net.cpollet.games.fiar.rules.victory.UpRightVictoryRule;
import net.cpollet.games.fiar.rules.victory.VerticalVictoryRule;

import javax.swing.*;
import java.util.Arrays;

/**
 * @author Christophe Pollet
 */
public class Game {
    private GameHumanInterface gameHumanInterface;

    public static void main(String[] args) {
        new Game().start();
    }

    public Game() {
        BoardGUI boardGUI = new BoardGUI(Board.HEIGHT, Board.WIDTH);
        JFrame frame = new JFrame("Game");

        frame.setContentPane(boardGUI.panel1);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setAlwaysOnTop(true);
        frame.setVisible(true);
        frame.setAlwaysOnTop(false);

        gameHumanInterface = boardGUI;
    }

    public void start() {
        Board board = new Board();
        board.setVictoryRules(Arrays.asList(
                new HorizontalVictoryRule(),
                new VerticalVictoryRule(),
                new DownLeftVictoryRule(),
                new UpRightVictoryRule()
        ));

        for (int turn = 0; turn < Board.WIDTH * Board.HEIGHT; turn++) {
            Player player = getPlayer(turn);

            boolean played = false;
            while (!played) {
                try {
                    int playedColumn = gameHumanInterface.getColumnChoice(player);
                    int playedRow = board.play(playedColumn, player);

                    gameHumanInterface.updateCell(playedRow, playedColumn, player);

                    if (board.hasWon(player)) {
                        gameHumanInterface.displayWinner(player);
                        return;
                    }

                    played = true;
                } catch (IllegalChoiceException e) {
                    System.out.println("Column full");
                }
            }
        }
    }

    private Player getPlayer(int turn) {
        if (turn % 2 == 0) {
            return Player.ONE;
        }

        return Player.TWO;
    }


}
