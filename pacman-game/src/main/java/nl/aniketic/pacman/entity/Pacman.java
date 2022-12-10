package nl.aniketic.pacman.entity;

import nl.aniketic.engine.gamestate.GameObject;

import java.awt.Rectangle;

public class Pacman implements GameObject {

    public static final int SIZE = 20;
    public static final int SPEED = 3;

    private final PacmanPanelComponent pacmanPanelComponent;
    private final Rectangle collisionBody;

    private final int frameUpdateCount = 6;
    private int currentFrameUpdateCount = frameUpdateCount;

    private Direction direction;
    private int screenX;
    private int screenY;

    public Pacman(int screenX, int screenY) {
        this.screenX = screenX;
        this.screenY = screenY;

        pacmanPanelComponent = new PacmanPanelComponent(screenX, screenY, SIZE);
        pacmanPanelComponent.loadImages();
        direction = Direction.RIGHT;
        collisionBody = new Rectangle(1, 1, SIZE-2, SIZE-2);
    }

    @Override
    public void update() {
        if (currentFrameUpdateCount >= frameUpdateCount) {
            pacmanPanelComponent.updateImageFrame();
            currentFrameUpdateCount = 0;
        } else {
            currentFrameUpdateCount++;
        }
    }

    @Override
    public void activatePanelComponent() {
        pacmanPanelComponent.activate();
    }

    @Override
    public void deactivatePanelComponent() {
        pacmanPanelComponent.deactivate();
    }

    public void handleUp() {
        switch (direction) {
            case DOWN:
                pacmanPanelComponent.rotate(180);
                break;
            case LEFT:
                pacmanPanelComponent.rotate(90);
                break;
            case RIGHT:
                pacmanPanelComponent.rotate(270);
                break;
            default:
                throw new IllegalStateException("Unknown direction: " + direction);
        }
        direction = Direction.UP;
    }

    public void handleDown() {
        switch (direction) {
            case UP:
                pacmanPanelComponent.rotate(180);
                break;
            case LEFT:
                pacmanPanelComponent.rotate(270);
                break;
            case RIGHT:
                pacmanPanelComponent.rotate(90);
                break;
            default:
                throw new IllegalStateException("Unknown direction: " + direction);
        }
        direction = Direction.DOWN;
    }

    public void handleLeft() {
        switch (direction) {
            case UP:
                pacmanPanelComponent.rotate(270);
                break;
            case DOWN:
                pacmanPanelComponent.rotate(90);
                break;
            case RIGHT:
                pacmanPanelComponent.rotate(180);
                break;
            default:
                throw new IllegalStateException("Unknown direction: " + direction);
        }
        direction = Direction.LEFT;
    }

    public void handleRight() {
        switch (direction) {
            case UP:
                pacmanPanelComponent.rotate(90);
                break;
            case DOWN:
                pacmanPanelComponent.rotate(270);
                break;
            case LEFT:
                pacmanPanelComponent.rotate(180);
                break;
            default:
                throw new IllegalStateException("Unknown direction: " + direction);
        }
        direction = Direction.RIGHT;
    }

    public Rectangle getCollisionBody() {
        return collisionBody;
    }

    public int getScreenX() {
        return screenX;
    }

    public void setScreenX(int screenX) {
        this.screenX = screenX;
        pacmanPanelComponent.setScreenX(screenX);
    }

    public int getScreenY() {
        return screenY;
    }

    public void setScreenY(int screenY) {
        this.screenY = screenY;
        pacmanPanelComponent.setScreenY(screenY);
    }

    public Direction getDirection() {
        return direction;
    }
}
