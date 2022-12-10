package nl.aniketic.pacman.gamestate;

import nl.aniketic.engine.gamestate.GameObject;

public class SidePanel implements GameObject {

    public static final int SCREEN_X = 10;
    public static final int SCREEN_Y = 640;

    private final SidePanelComponent sidePanelComponent;

    public SidePanel() {
        sidePanelComponent = new SidePanelComponent(SCREEN_X, SCREEN_Y);
    }

    @Override
    public void update() {
    }

    @Override
    public void activatePanelComponent() {
        sidePanelComponent.activate();
    }

    @Override
    public void deactivatePanelComponent() {
        sidePanelComponent.deactivate();
    }

    public void setScore(int score) {
        sidePanelComponent.setScore(score);
    }
}
