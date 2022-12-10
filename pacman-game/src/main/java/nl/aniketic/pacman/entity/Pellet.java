package nl.aniketic.pacman.entity;

import nl.aniketic.engine.gamestate.GameObject;

import java.awt.Rectangle;

public class Pellet implements GameObject {

    public static final int BLOCK_SIZE = 20;
    public static final int FOOD_SIZE = 6;

    private final int screenX;
    private final int screenY;
    private final Rectangle collisionBody;

    private final PelletPanelComponent pelletPanelComponent;

    public Pellet(int col, int row, int offsetX, int offsetY) {
        this.screenX = offsetX + col * BLOCK_SIZE;
        this.screenY = offsetY + row * BLOCK_SIZE;
        pelletPanelComponent = new PelletPanelComponent(screenX, screenY);
        collisionBody = new Rectangle(
                screenX + BLOCK_SIZE / 2 - FOOD_SIZE / 2,
                screenY + BLOCK_SIZE / 2 - FOOD_SIZE / 2,
                FOOD_SIZE,
                FOOD_SIZE);
    }

    @Override
    public void update() {

    }

    @Override
    public void activatePanelComponent() {
        pelletPanelComponent.activate();
    }

    @Override
    public void deactivatePanelComponent() {
        pelletPanelComponent.deactivate();
    }

    public Rectangle getCollisionBody() {
        return collisionBody;
    }
}
