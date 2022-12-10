package nl.aniketic.pacman.entity;

import nl.aniketic.engine.gamestate.GameObject;

import java.awt.Rectangle;

public class Ghost implements GameObject {

    public static final int SIZE = 20;
    public static final int SPEED = 1;

    private final GhostPanelComponent panelComponent;
    private int screenX;
    private int screenY;
    private Direction direction;
    private final Rectangle collisionBody;

    public Ghost(int screenX, int screenY, GhostColor ghostColor) {
        this.screenX = screenX;
        this.screenY = screenY;
        this.direction = Direction.RIGHT;
        panelComponent = new GhostPanelComponent(screenX, screenY, ghostColor, direction);
        panelComponent.loadImages();
        collisionBody = new Rectangle(1, 1, SIZE - 2, SIZE - 2);
    }

    @Override
    public void update() {

    }

    @Override
    public void activatePanelComponent() {
        panelComponent.activate();
    }

    @Override
    public void deactivatePanelComponent() {
        panelComponent.deactivate();
    }

    public int getScreenX() {
        return screenX;
    }

    public void setScreenX(int screenX) {
        this.screenX = screenX;
        panelComponent.setScreenX(screenX);
    }

    public int getScreenY() {
        return screenY;
    }

    public void setScreenY(int screenY) {
        this.screenY = screenY;
        panelComponent.setScreenY(screenY);
    }

    public Direction getDirection() {
        return direction;
    }

    public void setDirection(Direction direction) {
        this.direction = direction;
        panelComponent.setDirection(direction);
    }

    public Rectangle getCollisionBody() {
        return collisionBody;
    }
}
