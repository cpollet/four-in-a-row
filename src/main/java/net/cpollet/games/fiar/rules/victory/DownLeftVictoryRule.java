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

import net.cpollet.games.fiar.rules.Player;

/**
 * @author Christophe Pollet
 */
public class DownLeftVictoryRule extends DiagonalVictoryRule {
    protected boolean checkDiagonal(int startingRow, int startingColumn, Player player, Player[][] matrix) {
        for (int i = 0; i < 4; i++) {
            int row = startingRow - i;
            int column = startingColumn + i;

            if (row >= 0 && column >= 0) {
                if (matrix[row][column] != player) {
                    return false;
                }
            } else {
                return false;
            }
        }

        return true;
    }
}
