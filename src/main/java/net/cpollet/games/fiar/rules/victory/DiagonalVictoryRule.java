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

package net.cpollet.games.fiar.rules.victory;

import net.cpollet.games.fiar.rules.Board;
import net.cpollet.games.fiar.rules.Player;

/**
 * @author Christophe Pollet
 */
public abstract class DiagonalVictoryRule implements VictoryRule {
    @Override
    public boolean hasWon(Player player, Player[][] matrix) {
        for (int column = 0; column < Board.WIDTH; column++) {
            for (int row = 0; row < Board.HEIGHT; row++) {
                if (checkDiagonal(row, column, player, matrix)) {
                    System.out.println("Diagonal verified from row:" + row + "; column:" + column);
                    return true;
                }
            }
        }

        return false;
    }

    abstract boolean checkDiagonal(int row, int column, Player player, Player[][] matrix);
}
