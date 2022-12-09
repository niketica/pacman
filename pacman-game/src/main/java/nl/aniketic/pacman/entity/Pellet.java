package nl.aniketic.pacman.entity;

import nl.aniketic.engine.gamestate.GameObject;

import java.awt.Rectangle;

public class Pellet extends GameObject {

    public static final int BLOCK_SIZE = 20;
    public static final int FOOD_SIZE = 6;

    private final int screenX;
    private final int screenY;
    private final Rectangle collisionBody;

    public Pellet(int col, int row, int offsetX, int offsetY) {
        this.screenX = offsetX + col * BLOCK_SIZE;
        this.screenY = offsetY + row * BLOCK_SIZE;
        this.panelComponent = new PelletPanelComponent(screenX, screenY);
        collisionBody = new Rectangle(
                screenX + BLOCK_SIZE / 2 - FOOD_SIZE / 2,
                screenY + BLOCK_SIZE / 2 - FOOD_SIZE / 2,
                FOOD_SIZE,
                FOOD_SIZE);
    }

    @Override
    public void update() {

    }

    public Rectangle getCollisionBody() {
        return collisionBody;
    }
}
