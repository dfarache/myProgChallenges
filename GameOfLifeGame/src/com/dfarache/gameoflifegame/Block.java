
package com.dfarache.gameoflifegame;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import org.newdawn.slick.opengl.Texture;
import org.newdawn.slick.opengl.TextureLoader;
import static org.lwjgl.opengl.GL11.*;
import static com.dfarache.gameoflifegame.GameOfLife.BLOCK_SIZE;

public class Block {
    
    private BlockType type = BlockType.DEAD; //by default
    private final Texture texture;
    //location parameters
    private final float x;
    private final float y;
    
    public Block(BlockType type, float x, float y){
        this.type = type;
        this.x = x;
        this.y = y;
        this.texture = setTexture();
    }
    
    private Texture setTexture(){
        try (FileInputStream inStream = new FileInputStream(new File(type.location))){            
            return TextureLoader.getTexture("PNG", inStream);
        } catch (IOException ex) {
            System.err.println("Issues loading the texture " + ex);
            System.exit(0);
        }
        return null;
    }
    
    public void draw(){
        texture.bind();
        glLoadIdentity();
        glTranslatef(x,y,0);
        glBegin(GL_QUADS);
            glTexCoord2f(0,0);
            glVertex2f(0,0);
            glTexCoord2f(1,0);
            glVertex2f(BLOCK_SIZE,0);
            glTexCoord2f(1,1);
            glVertex2f(BLOCK_SIZE,BLOCK_SIZE);
            glTexCoord2f(0,1);
            glVertex2f(0,BLOCK_SIZE);
        glEnd();
        glLoadIdentity();
    }
    
    public BlockType getType(){
        return this.type;
    }
    
}