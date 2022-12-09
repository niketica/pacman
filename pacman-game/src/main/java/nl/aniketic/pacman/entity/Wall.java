package nl.aniketic.pacman.entity;

import java.awt.Rectangle;

public class Wall {

    private static final int WALL_SIZE = 20;

    private final int col;
    private final int row;
    private final int screenX;
    private final int screenY;
    private final Rectangle collisionBody;

    public Wall(int col, int row, int offsetX, int offsetY) {
        this.col = col;
        this.row = row;
        this.screenX = offsetX + col * WALL_SIZE;
        this.screenY = offsetY + row * WALL_SIZE;
        collisionBody = new Rectangle(screenX, screenY, WALL_SIZE, WALL_SIZE);
    }

    public int getCol() {
        return col;
    }

    public int getRow() {
        return row;
    }

    public int getScreenX() {
        return screenX;
    }

    public int getScreenY() {
        return screenY;
    }

    public Rectangle getCollisionBody() {
        return collisionBody;
    }
}
