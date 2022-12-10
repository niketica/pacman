package nl.aniketic.pacman.gamestate;

import nl.aniketic.engine.gamestate.GameObject;
import nl.aniketic.pacman.pathfinding.Node;

import java.util.ArrayList;
import java.util.List;

public class MainPanel implements GameObject {

    public static final int OFFSET_X = 10;
    public static final int OFFSET_Y = 10;
    private final MainPanelComponent mainPanelComponent;

    public MainPanel() {
        mainPanelComponent = new MainPanelComponent(OFFSET_X, OFFSET_Y);
    }

    @Override
    public void update() {

    }

    @Override
    public void activatePanelComponent() {
        mainPanelComponent.activate();
    }

    @Override
    public void deactivatePanelComponent() {
        mainPanelComponent.deactivate();
    }

    public void setMap(int[][] map) {
        mainPanelComponent.setMap(map);
    }

    public void cleanPathList() {
        mainPanelComponent.cleanPathList();
    }

    public void addPath(List<Node> path) {
        mainPanelComponent.addPath(path);
    }
}
