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

package net.cpollet.games.fiar.rules;

import net.cpollet.games.fiar.rules.exception.IllegalChoiceException;
import net.cpollet.games.fiar.rules.victory.VictoryRule;

import java.util.List;

/**
 * @author Christophe Pollet
 */
public class Board {
    public static final int WIDTH = 8;
    public static final int HEIGHT = 8;

    private Player[][] matrix = new Player[WIDTH][HEIGHT];
    private List<VictoryRule> victoryRules;

    public int play(int column, Player player) throws IllegalChoiceException {
        int row = findLowestEmptyRowInColumn(column);

        updateCell(row, column, player);

        return row;
    }

    public boolean hasWon(Player player) {
        for (VictoryRule victoryRule : victoryRules) {
            if (victoryRule.hasWon(player, matrix)) {
                return true;
            }
        }

        return false;
    }

    private int findLowestEmptyRowInColumn(int column) throws IllegalChoiceException {
        for (int row = 0; row < HEIGHT; row++) {
            if (isEmpty(row, column)) {
                return row;
            }
        }

        throw new IllegalChoiceException(column);
    }

    private boolean isEmpty(int row, int column) {
        return matrix[row][column] == null;
    }

    private void updateCell(int row, int column, Player player) {
        matrix[row][column] = player;
    }

    public void setVictoryRules(List<VictoryRule> victoryRules) {
        this.victoryRules = victoryRules;
    }
}
