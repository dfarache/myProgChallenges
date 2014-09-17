package com.dfarache.gameoflifegame;

import java.util.logging.Logger;
import static com.dfarache.gameoflifegame.Boot.grid;
import java.util.logging.Level;

public class GameOfLife {

    public static final int BLOCK_SIZE = 32;
    public static final int BLOCKS_WIDTH = 24;
    public static final int BLOCKS_HEIGT = 20;

    private static BlockGrid temp;

    protected static void startGameOfLife() {
        while (!checkIfAllDead()) {
            newRound();
            grid.draw();
            Boot.updateDisplay();
            Boot.input();
            waitForRound();
        }
    }

    private static void newRound() {
        temp = new BlockGrid();
        for (int i = 0; i < BLOCKS_WIDTH - 1; i++) {
            for (int j = 0; j < BLOCKS_HEIGT; j++) {
                int aliveNeighbours = countAliveNeighbours(i, j);
                applyGameRules(i, j, aliveNeighbours);
            }
        }
        copyTempToGrid();
    }

    private static boolean checkIfAllDead() {
        for (int i = 0; i < BLOCKS_WIDTH - 1; i++) {
            for (int j = 0; j < BLOCKS_HEIGT; j++) {
                if (grid.getAt(i, j).getType() == BlockType.ALIVE) {
                    return false;
                }
            }
        }
        System.out.println("They're all dead");
        return true;
    }

    private static void copyTempToGrid() {
        for (int i = 0; i < BLOCKS_WIDTH - 1; i++) {
            for (int j = 0; j < BLOCKS_HEIGT; j++) {
                grid.setAt(i, j, temp.getAt(i, j).getType());
            }
        }
    }

    private static void applyGameRules(int i, int j, int aliveNeighbours) {
        if (grid.getAt(i, j).getType() == BlockType.DEAD && aliveNeighbours == 3) {
            temp.setAt(i, j, BlockType.ALIVE);
        } else if (grid.getAt(i, j).getType() == BlockType.DEAD && aliveNeighbours != 3) {
            temp.setAt(i, j, BlockType.DEAD);
        } else if (grid.getAt(i, j).getType() == BlockType.ALIVE && (aliveNeighbours < 2 | aliveNeighbours > 3)) {
            temp.setAt(i, j, BlockType.DEAD);
        } else {
            temp.setAt(i, j, BlockType.ALIVE);
        }
    }

    private static int countAliveNeighbours(int i, int j) {
        int count = 0;
        if (j > 0) {
            count = grid.getAt(i, j - 1).getType() == BlockType.ALIVE ? ++count : count;
        }
        if (j < 14) {
            count = grid.getAt(i, j + 1).getType() == BlockType.ALIVE ? ++count : count;
        }
        if (i < 22) {
            count = grid.getAt(i + 1, j).getType() == BlockType.ALIVE ? ++count : count;
        }
        if (i > 0) {
            count = grid.getAt(i - 1, j).getType() == BlockType.ALIVE ? ++count : count;
        }
        if (j > 0 && i < 22) {
            count = grid.getAt(i + 1, j - 1).getType() == BlockType.ALIVE ? ++count : count;
        }
        if (j < 14 && i < 22) {
            count = grid.getAt(i + 1, j + 1).getType() == BlockType.ALIVE ? ++count : count;
        }
        if (j < 14 && i > 0) {
            count = grid.getAt(i - 1, j + 1).getType() == BlockType.ALIVE ? ++count : count;
        }
        if (i > 0 && j > 0) {
            count = grid.getAt(i - 1, j - 1).getType() == BlockType.ALIVE ? ++count : count;
        }

        return count;
    }

    private static void waitForRound() {
        try {
            Thread.sleep(1000);
        } catch (InterruptedException ex) {
            Logger.getLogger(GameOfLife.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
