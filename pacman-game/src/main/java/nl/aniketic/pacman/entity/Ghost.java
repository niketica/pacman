package nl.aniketic.pacman.entity;

import nl.aniketic.engine.gamestate.GameObject;

import java.awt.Rectangle;

public class Ghost implements GameObject {

    public static final int SIZE = 20;
    public static final int SPEED = 2;

    private final GhostPanelComponent panelComponent;
    private final Rectangle collisionBody;
    private final int updateEatenBobCount;

    private int screenX;
    private int screenY;
    private Direction direction;
    private GhostState state;
    private int currentEatenBobCount;

    private boolean moving;

    private int destinationX;
    private int destinationY;

    public Ghost(int screenX, int screenY, GhostType ghostType) {
        this.screenX = screenX;
        this.screenY = screenY;
        this.direction = Direction.RIGHT;
        state = GhostState.SCATTER;
        panelComponent = new GhostPanelComponent(screenX, screenY, ghostType, direction, state);
        panelComponent.loadImages();
        collisionBody = new Rectangle(1, 1, SIZE - 2, SIZE - 2);
        updateEatenBobCount = 2;
        currentEatenBobCount = 0;
    }

    @Override
    public void update() {
        if (state == GhostState.EATEN) {
            if (currentEatenBobCount >= updateEatenBobCount) {
                currentEatenBobCount = 0;
                panelComponent.updateEatenBobOffset();
            } else {
                currentEatenBobCount++;
            }
        }

        if (moving) {
            int deltaX = destinationX - screenX;
            int deltaY = destinationY - screenY;

            if (deltaX == 0 && deltaY == 0) {
                moving = false;
            } else {
                screenX += deltaX;
                screenY += deltaY;

                panelComponent.setScreenX(screenX);
                panelComponent.setScreenY(screenY);
            }
        }
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

    public void setState(GhostState state) {
        this.state = state;
        panelComponent.setState(state);
    }

    public GhostState getState() {
        return state;
    }

    public boolean isMoving() {
        return moving;
    }

    public void setDestinationX(int destinationX) {
        this.destinationX = destinationX;
        moving = true;
    }

    public void setDestinationY(int destinationY) {
        this.destinationY = destinationY;
        moving = true;
    }
}
