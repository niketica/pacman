package nl.aniketic.pacman.entity;

import nl.aniketic.engine.gamestate.GameObject;

public class Ghost implements GameObject {

    public static final int SIZE = 20;
    public static final int SPEED = 3;

    private GhostPanelComponent panelComponent;
    private int screenX;
    private int screenY;
    private Direction direction;

    public Ghost(int screenX, int screenY, GhostColor ghostColor) {
        this.screenX = screenX;
        this.screenY = screenY;
        this.direction = Direction.RIGHT;
        panelComponent = new GhostPanelComponent(screenX, screenY, ghostColor, direction);
        panelComponent.loadImages();
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
}
