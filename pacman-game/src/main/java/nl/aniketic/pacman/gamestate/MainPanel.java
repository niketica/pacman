package nl.aniketic.pacman.gamestate;

import nl.aniketic.engine.gamestate.GameObject;

public class MainPanel extends GameObject {

    public static final int OFFSET_X = 10;
    public static final int OFFSET_Y = 10;
    private final MainPanelComponent mainPanelComponent;

    public MainPanel() {
        mainPanelComponent = new MainPanelComponent(OFFSET_X, OFFSET_Y);
        this.panelComponent = mainPanelComponent;
    }

    @Override
    public void update() {

    }

    public void setMap(int[][] map) {
        mainPanelComponent.setMap(map);
    }
}
