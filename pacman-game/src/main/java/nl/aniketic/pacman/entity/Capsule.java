package nl.aniketic.pacman.entity;

import nl.aniketic.engine.gamestate.GameObject;

import java.awt.Rectangle;

public class Capsule implements GameObject {

    public static final int CAPSULE_SIZE = 12;

    private final CapsulePanelComponent capsulePanelComponent;

    private final Rectangle collisionBody;

    public Capsule(int screenX, int screenY) {
        capsulePanelComponent = new CapsulePanelComponent(screenX, screenY, CAPSULE_SIZE);
        collisionBody = new Rectangle(screenX, screenY, CAPSULE_SIZE, CAPSULE_SIZE);
    }

    @Override
    public void update() {

    }

    @Override
    public void activatePanelComponent() {
        capsulePanelComponent.activate();
    }

    @Override
    public void deactivatePanelComponent() {
        capsulePanelComponent.deactivate();
    }

    public Rectangle getCollisionBody() {
        return collisionBody;
    }
}
