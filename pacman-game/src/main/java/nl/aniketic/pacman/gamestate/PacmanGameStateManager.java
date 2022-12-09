package nl.aniketic.pacman.gamestate;

import nl.aniketic.engine.display.DisplayManager;
import nl.aniketic.engine.gamestate.GameStateManager;
import nl.aniketic.pacman.controls.Key;
import nl.aniketic.pacman.controls.PacmanKeyHandler;
import nl.aniketic.pacman.entity.Direction;
import nl.aniketic.pacman.entity.Pellet;
import nl.aniketic.pacman.entity.Pacman;
import nl.aniketic.pacman.entity.Wall;

import java.awt.Rectangle;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class PacmanGameStateManager extends GameStateManager {

    private PacmanKeyHandler keyListener;
    private int[][] map;
    private MainPanel mainPanel;
    private Pacman pacman;

    private List<Wall> walls;
    private List<Pellet> pellets;

    @Override
    protected void startGameState() {
        DisplayManager.createDisplay("PAC-MAN");
        keyListener = new PacmanKeyHandler();
        DisplayManager.addKeyListener(keyListener);
        startNewGame();
    }

    @Override
    protected void updatePreGameState() {
        Key pressedKey = getPressedKey();
        if (pressedKey != null) {
            if (pressedKey == Key.UP && pacman.getDirection() != Direction.UP) {
                int screenX = pacman.getScreenX();
                int screenY = pacman.getScreenY() - Pacman.SPEED;
                if (!isCollisionWithWall(screenX, screenY)) {
                    pacman.handleUp();
                }
            }
            if (pressedKey == Key.DOWN && pacman.getDirection() != Direction.DOWN) {
                int screenX = pacman.getScreenX();
                int screenY = pacman.getScreenY() + Pacman.SPEED;
                if (!isCollisionWithWall(screenX, screenY)) {
                    pacman.handleDown();
                }
            }
            if (pressedKey == Key.LEFT && pacman.getDirection() != Direction.LEFT) {
                int screenX = pacman.getScreenX() - Pacman.SPEED;
                int screenY = pacman.getScreenY();
                if (!isCollisionWithWall(screenX, screenY)) {
                    pacman.handleLeft();
                }
            }
            if (pressedKey == Key.RIGHT && pacman.getDirection() != Direction.RIGHT) {
                int screenX = pacman.getScreenX() + Pacman.SPEED;
                int screenY = pacman.getScreenY();
                if (!isCollisionWithWall(screenX, screenY)) {
                    pacman.handleRight();
                }
            }
        }

        movePacman();

        Pellet pellet = getCollisionWithFood(pacman.getScreenX(), pacman.getScreenY());
        if (pellet != null) {
            pellet.deactivatePanelComponent();
        }
    }

    private void movePacman() {
        int potentialX = pacman.getScreenX();
        int potentialY = pacman.getScreenY();
        switch (pacman.getDirection()) {
            case UP:
                potentialY -= Pacman.SPEED;
                break;
            case DOWN:
                potentialY += Pacman.SPEED;
                break;
            case LEFT:
                potentialX -= Pacman.SPEED;
                break;
            case RIGHT:
                potentialX += Pacman.SPEED;
                break;
            default:
                System.out.println("Unhandled direction.");
        }
        boolean collision = isCollisionWithWall(potentialX, potentialY);
        if (!collision) {
            pacman.setScreenX(potentialX);
            pacman.setScreenY(potentialY);
        }
    }

    private boolean isCollisionWithWall(int screenX, int screenY) {
        Rectangle pacmanCollisionBody = pacman.getCollisionBody();
        pacmanCollisionBody = new Rectangle(
                screenX + pacmanCollisionBody.x,
                screenY + pacmanCollisionBody.y,
                pacmanCollisionBody.width,
                pacmanCollisionBody.height);

        for (Wall wall : walls) {
            Rectangle wallCollisionBody = wall.getCollisionBody();
            if (pacmanCollisionBody.intersects(wallCollisionBody)) {
                return true;
            }
        }
        return false;
    }

    private Pellet getCollisionWithFood(int screenX, int screenY) {
        Rectangle pacmanCollisionBody = pacman.getCollisionBody();
        pacmanCollisionBody = new Rectangle(
                screenX + pacmanCollisionBody.x,
                screenY + pacmanCollisionBody.y,
                pacmanCollisionBody.width,
                pacmanCollisionBody.height);

        for (Pellet pellet : pellets) {
            if (pacmanCollisionBody.intersects(pellet.getCollisionBody())) {
                return pellet;
            }
        }

        return null;
    }

    @Override
    protected void updateEndGameState() {

    }

    private void startNewGame() {
        map = loadMap("/map/map01.txt");

        walls = new ArrayList<>();
        pellets = new ArrayList<>();
        for (int row=0; row<map.length; row++) {
            for (int col=0; col<map[0].length; col++) {
                int value = map[row][col];
                if (value == 1) {
                    walls.add(new Wall(col, row, MainPanel.OFFSET_X, MainPanel.OFFSET_Y));
                } else if (value == 2) {
                    Pellet pellet = new Pellet(col, row, MainPanel.OFFSET_X, MainPanel.OFFSET_Y);
                    pellet.activatePanelComponent();
                    pellets.add(pellet);
                }
            }
        }

        mainPanel = new MainPanel();
        mainPanel.setMap(map);
        mainPanel.activatePanelComponent();

        int col = 1;
        int row = 1;
        pacman = new Pacman(col * Pacman.SIZE + Pacman.SIZE / 2, row * Pacman.SIZE + Pacman.SIZE / 2);
        pacman.activatePanelComponent();
        gameObjects.add(pacman);
    }

    private int[][] loadMap(String filePath) {
        List<String> mapLines = getMapLines(filePath);
        int[][] newMap = mapLines.stream()
                .map(s -> s.split(" "))
                .map(s -> Arrays.stream(s).mapToInt(Integer::parseInt).toArray())
                .toArray(int[][]::new);
        return newMap;
    }

    private List<String> getMapLines(String filePath) {
        List<String> mapLines;
        try {
            InputStream inputStream = getClass().getResourceAsStream(filePath);
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
            mapLines = bufferedReader.lines().collect(Collectors.toList());
            bufferedReader.close();
        } catch (IOException e) {
            throw new IllegalStateException("Unable to load map.", e);
        }
        return mapLines;
    }

    private Key getPressedKey() {
        return Arrays.stream(Key.values())
                .filter(Key::isPressed)
                .findAny().orElse(null);
    }

}
