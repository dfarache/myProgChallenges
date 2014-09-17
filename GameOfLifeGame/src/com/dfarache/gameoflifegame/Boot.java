package com.dfarache.gameoflifegame;

import org.lwjgl.LWJGLException;
import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;
import static org.lwjgl.opengl.GL11.*;

public class Boot {

    protected static BlockGrid grid;
    private static boolean gameStarted = false;

    private static void boot() {
        initializeDisplay();
        initializeOpenGL();
        render();
    }

    private static void initializeDisplay() {
        try {
            Display.setDisplayMode(new DisplayMode(640, 480));
            Display.setTitle("Game of Life");
            Display.create();
        } catch (LWJGLException e) {
            Display.destroy();
            System.exit(0);
        }
    }

    private static void initializeOpenGL() {
        glMatrixMode(GL_PROJECTION);
        glLoadIdentity();
        glOrtho(0, 640, 480, 0, 1, -1);
        glMatrixMode(GL_MODELVIEW);
        glEnable(GL_TEXTURE_2D);
    }

    private static void render() {
        grid = new BlockGrid();
        while (!Display.isCloseRequested()) {
            grid.draw();
            input();
            updateDisplay();
        }
        Display.destroy();
    }

    protected static void updateDisplay() {
        Display.update();
        Display.sync(60);
    }

    protected static void input() {
        int mouseX = Mouse.getX();
        int mouseY = 480 - Mouse.getY() - 1;
        boolean isMouseClicked = Mouse.isButtonDown(0);
        if (isMouseClicked && !gameStarted) {
            int gridX = Math.round(mouseX / GameOfLife.BLOCK_SIZE);
            int gridY = Math.round(mouseY / GameOfLife.BLOCK_SIZE);
            System.out.println(gridX + "   " + gridY);
            setNewBlockAtSelectedPosition(gridX, gridY);
        }
        while (Keyboard.next()) {
            if (Keyboard.getEventKey() == Keyboard.KEY_SPACE) {
                gameStarted = !gameStarted;
                if (gameStarted) {
                    GameOfLife.startGameOfLife();
                }
            }
            if (Keyboard.getEventKey() == Keyboard.KEY_ESCAPE) {
                Display.destroy();
                System.exit(0);
            }
        }
    }

    private static void setNewBlockAtSelectedPosition(int x, int y) {
        BlockType type = grid.getAt(x, y).getType() == BlockType.DEAD ? BlockType.ALIVE : BlockType.DEAD;
        grid.setAt(x, y, type);
    }


    public static void main(String[] args) {
        Boot.boot();
    }
}
