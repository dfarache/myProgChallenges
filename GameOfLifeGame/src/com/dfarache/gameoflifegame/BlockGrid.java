package com.dfarache.gameoflifegame;

import static com.dfarache.gameoflifegame.GameOfLife.*;

public class BlockGrid {

    private final Block[][] blocks = new Block[BLOCKS_WIDTH][BLOCKS_HEIGT];

    public BlockGrid() {
        for (int i = 0; i < BLOCKS_WIDTH - 1; i++) {
            for (int j = 0; j < BLOCKS_HEIGT; j++) {
                blocks[i][j] = new Block(BlockType.DEAD, i * BLOCK_SIZE, j * BLOCK_SIZE);
            }
        }
    }

    public void setAt(int i, int j, BlockType b) {
        blocks[i][j] = new Block(b, i * BLOCK_SIZE, j * BLOCK_SIZE);
    }

    public Block getAt(int i, int j) {
        return blocks[i][j];
    }

    public void draw() {
        for (int i = 0; i < BLOCKS_WIDTH - 1; i++) {
            for (int j = 0; j < BLOCKS_HEIGT; j++) {
                blocks[i][j].draw();
            }
        }
    }
}
