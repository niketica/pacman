package nl.aniketic.pacman.entity;

import java.awt.Rectangle;

public class Wall {

    public static final int WALL_SIZE = 20;

    private final int screenX;
    private final int screenY;
    private final Rectangle collisionBody;

    public Wall(int col, int row, int offsetX, int offsetY) {
        this.screenX = offsetX + col * WALL_SIZE;
        this.screenY = offsetY + row * WALL_SIZE;
        collisionBody = new Rectangle(screenX, screenY, WALL_SIZE, WALL_SIZE);
    }

    public Rectangle getCollisionBody() {
        return collisionBody;
    }
}
