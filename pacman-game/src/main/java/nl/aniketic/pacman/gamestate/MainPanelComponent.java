package nl.aniketic.pacman.gamestate;

import nl.aniketic.engine.display.PanelComponent;
import nl.aniketic.pacman.pathfinding.Node;

import java.awt.Color;
import java.awt.Graphics2D;
import java.util.ArrayList;
import java.util.List;

public class MainPanelComponent implements PanelComponent {

    public static final int BLOCK_SIZE = 20;
    public static final int WALL_WIDTH = 2;

    private final int screenX;
    private final int screenY;

    private int[][] map;

    private List<List<Node>> pathList;

    public MainPanelComponent(int screenX, int screenY) {
        this.screenX = screenX;
        this.screenY = screenY;
    }

    @Override
    public void paintComponent(Graphics2D g2) {
        if (map == null) {
            return;
        }

        for (int row=0; row<map.length; row++) {
            for (int col = 0; col < map[0].length; col++) {
                int value = map[row][col];
                if (value == 1) {
                    drawWall(g2, row, col);
                }
            }
        }

//        drawPaths(g2);
    }

    private void drawPaths(Graphics2D g2) {
        if (pathList != null) {
            for (List<Node> path : pathList) {
                for (Node node : path) {
                    g2.setColor(Color.RED);
                    int nodeX = screenX + node.getCol() * BLOCK_SIZE;
                    int nodeY = screenY + node.getRow() * BLOCK_SIZE;
                    g2.fillRect(nodeX, nodeY, BLOCK_SIZE, BLOCK_SIZE);
                }
            }
        }
    }

    private void drawWall(Graphics2D g2, int row, int col) {
        int x1 = screenX + col * BLOCK_SIZE;
        int y1 = screenY + row * BLOCK_SIZE;

        g2.setColor(Color.BLUE);
        g2.fillRect(x1, y1, BLOCK_SIZE, BLOCK_SIZE);

        g2.setColor(Color.BLACK);
        g2.fillRect(x1+WALL_WIDTH, y1+WALL_WIDTH, BLOCK_SIZE-WALL_WIDTH*2, BLOCK_SIZE-WALL_WIDTH*2);

        if (row > 0) {
            int valueTop = map[row - 1][col];
            if (valueTop == 1) {
                g2.fillRect(x1+WALL_WIDTH, y1, BLOCK_SIZE-WALL_WIDTH*2, WALL_WIDTH);
            }
        }

        if (row < map.length - 1) {
            int valueBottom = map[row + 1][col];
            if (valueBottom == 1) {
                g2.fillRect(x1+WALL_WIDTH, y1 + BLOCK_SIZE - WALL_WIDTH, BLOCK_SIZE-WALL_WIDTH*2, WALL_WIDTH);
            }
        }

        if (col > 0) {
            int valueLeft = map[row][col - 1];
            if (valueLeft == 1) {
                g2.fillRect(x1, y1 + WALL_WIDTH, WALL_WIDTH, BLOCK_SIZE-WALL_WIDTH*2);
            }
        }

        if (col < map[0].length - 1) {
            int valueRight = map[row][col + 1];
            if (valueRight == 1) {
                g2.fillRect(x1 + BLOCK_SIZE - WALL_WIDTH, y1 + WALL_WIDTH, WALL_WIDTH, BLOCK_SIZE-WALL_WIDTH*2);
            }
        }
    }

    public void setMap(int[][] map) {
        this.map = map;
    }

    public void cleanPathList() {
        pathList = new ArrayList<>();
    }

    public void addPath(List<Node> path) {
        if (pathList == null) {
            pathList = new ArrayList<>();
        }
        pathList.add(path);
    }
}
