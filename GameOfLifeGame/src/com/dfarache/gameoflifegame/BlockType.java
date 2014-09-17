

package com.dfarache.gameoflifegame;


public enum BlockType {
    DEAD("./res/dead.png"), ALIVE("./res/alive.png");
    public final String location;
    
    BlockType(String location) {
        this.location = location;
    }
}
