package nl.aniketic.pacman.gamestate;

import nl.aniketic.engine.gamestate.GameObject;

public class MainPanel extends GameObject {

    private final MainPanelComponent mainPanelComponent;

    public MainPanel() {
        mainPanelComponent = new MainPanelComponent(10, 10);
        this.panelComponent = mainPanelComponent;
    }

    @Override
    public void update() {

    }

    public void setMap(int[][] map) {
        mainPanelComponent.setMap(map);
    }
}
