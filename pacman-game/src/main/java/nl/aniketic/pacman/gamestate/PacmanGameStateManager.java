package nl.aniketic.pacman.gamestate;

import nl.aniketic.engine.display.DisplayManager;
import nl.aniketic.engine.gamestate.GameStateManager;
import nl.aniketic.engine.sound.Sound;
import nl.aniketic.pacman.controls.Key;
import nl.aniketic.pacman.controls.PacmanKeyHandler;
import nl.aniketic.pacman.entity.Capsule;
import nl.aniketic.pacman.entity.Direction;
import nl.aniketic.pacman.entity.Ghost;
import nl.aniketic.pacman.entity.GhostState;
import nl.aniketic.pacman.entity.GhostType;
import nl.aniketic.pacman.entity.Pacman;
import nl.aniketic.pacman.entity.Pellet;
import nl.aniketic.pacman.entity.Wall;

import java.awt.Rectangle;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

public class PacmanGameStateManager extends GameStateManager {

    private static final int PACMAN_START_COL = 1;
    private static final int PACMAN_START_ROW = 1;

    private PacmanKeyHandler keyListener;
    private int[][] map;
    private MainPanel mainPanel;
    private SidePanel sidePanel;
    private Pacman pacman;
    private Ghost[] ghosts;

    private List<Wall> walls;
    private List<Pellet> pellets;
    private List<Capsule> capsules;

    private Sound waka;

    private int score;
    private int lives;
    private boolean victory;
    private boolean gameOver;

    private final int ghostFrightenedCount = 300;
    private int currentGhostFrightenedCount = ghostFrightenedCount;

    @Override
    protected void startGameState() {
        DisplayManager.createDisplay("PAC-MAN");
        keyListener = new PacmanKeyHandler();
        DisplayManager.addKeyListener(keyListener);

        mainPanel = new MainPanel();
        mainPanel.activatePanelComponent();
        sidePanel = new SidePanel();
        sidePanel.activatePanelComponent();

        startNewGame();
    }

    @Override
    protected void updatePreGameState() {
        Key pressedKey = getPressedKey();

        if (victory || gameOver) {
            if (pressedKey == Key.SPACE) {
                resetGame();
            }
            return;
        }

        if (pressedKey == Key.UP && pacman.getDirection() != Direction.UP) {
            int screenX = pacman.getScreenX();
            int screenY = pacman.getScreenY() - Pacman.SPEED;
            Rectangle collisionBody = getCollisionBodyOnPosition(pacman.getCollisionBody(), screenX, screenY);
            if (!isCollisionWithWall(collisionBody)) {
                pacman.handleUp();
            }
        }
        if (pressedKey == Key.DOWN && pacman.getDirection() != Direction.DOWN) {
            int screenX = pacman.getScreenX();
            int screenY = pacman.getScreenY() + Pacman.SPEED;
            Rectangle collisionBody = getCollisionBodyOnPosition(pacman.getCollisionBody(), screenX, screenY);
            if (!isCollisionWithWall(collisionBody)) {
                pacman.handleDown();
            }
        }
        if (pressedKey == Key.LEFT && pacman.getDirection() != Direction.LEFT) {
            int screenX = pacman.getScreenX() - Pacman.SPEED;
            int screenY = pacman.getScreenY();
            Rectangle collisionBody = getCollisionBodyOnPosition(pacman.getCollisionBody(), screenX, screenY);
            if (!isCollisionWithWall(collisionBody)) {
                pacman.handleLeft();
            }
        }
        if (pressedKey == Key.RIGHT && pacman.getDirection() != Direction.RIGHT) {
            int screenX = pacman.getScreenX() + Pacman.SPEED;
            int screenY = pacman.getScreenY();
            Rectangle collisionBody = getCollisionBodyOnPosition(pacman.getCollisionBody(), screenX, screenY);
            if (!isCollisionWithWall(collisionBody)) {
                pacman.handleRight();
            }
        }

        movePacman();
        moveGhosts();

        Rectangle pacmanCollisionBody =
                getCollisionBodyOnPosition(pacman.getCollisionBody(), pacman.getScreenX(), pacman.getScreenY());
        Pellet pellet = getCollisionWithFood(pacmanCollisionBody);
        if (pellet != null) {
            pellet.deactivatePanelComponent();
            pellets.remove(pellet);
            score++;
            sidePanel.setScore(score);

            if (pellets.isEmpty()) {
                victory = true;
                sidePanel.setVictory(true);
            }

            if (!waka.isRunning()) {
                waka.loadClip();
                waka.play();
            }
        }

        boolean collisionWithGhost = isCollisionWithGhost(pacmanCollisionBody);
        if (collisionWithGhost) {
            lives--;
            sidePanel.setLives(lives);

            if (lives < 0) {
                gameOver = true;
                sidePanel.setGameOver(true);
            } else {
                resetPacman();
            }
        }

        Capsule capsule = getCollisionWithCapsule(pacmanCollisionBody);
        if (capsule != null) {
            capsule.deactivatePanelComponent();
            capsules.remove(capsule);
            for (Ghost ghost : ghosts) {
                ghost.setState(GhostState.FRIGHTENED);
            }
            currentGhostFrightenedCount = 0;
        }

        if (currentGhostFrightenedCount < ghostFrightenedCount) {
            currentGhostFrightenedCount++;
            if (currentGhostFrightenedCount >= ghostFrightenedCount) {
                for (Ghost ghost : ghosts) {
                    ghost.setState(GhostState.SCATTER);
                }
            }
        }
    }

    @Override
    protected void updateEndGameState() {

    }

    private void resetGame() {
        pacman.deactivatePanelComponent();
        gameObjects.remove(pacman);

        for (Ghost ghost : ghosts) {
            ghost.deactivatePanelComponent();
            gameObjects.remove(ghost);
        }

        for (Pellet pellet : pellets) {
            pellet.deactivatePanelComponent();
        }

        for (Capsule capsule : capsules) {
            capsule.deactivatePanelComponent();
        }

        startNewGame();
    }

    private void startNewGame() {
        score = 0;
        lives = 3;
        victory = false;
        gameOver = false;
        sidePanel.setVictory(false);
        sidePanel.setGameOver(false);
        sidePanel.setScore(score);
        sidePanel.setLives(lives);

        waka = new Sound("/sound/waka.wav");
        map = loadMap("/map/map01.txt");

        walls = new ArrayList<>();
        pellets = new ArrayList<>();
        for (int row = 0; row < map.length; row++) {
            for (int col = 0; col < map[0].length; col++) {
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

        pacman = createNewPacman();
        pacman.activatePanelComponent();
        gameObjects.add(pacman);

        ghosts = new Ghost[4];
        ghosts[0] = createGhost(14, 11, GhostType.CLYDE);
        ghosts[1] = createGhost(18, 14, GhostType.INKY);
        ghosts[2] = createGhost(8, 14, GhostType.BLINKY);
        ghosts[3] = createGhost(16, 17, GhostType.PINKY);
        for (Ghost ghost : ghosts) {
            ghost.activatePanelComponent();
            gameObjects.add(ghost);
        }

        capsules = new ArrayList<>();
        capsules.add(createCapsule(1, 3));
        capsules.add(createCapsule(26, 3));
        capsules.add(createCapsule(1, 23));
        capsules.add(createCapsule(26, 23));
        for (Capsule capsule : capsules) {
            capsule.activatePanelComponent();
        }

        mainPanel.setMap(map);
    }

    private Pacman createNewPacman() {
        return new Pacman(PACMAN_START_COL * Pacman.SIZE + Pacman.SIZE / 2,
                PACMAN_START_ROW * Pacman.SIZE + Pacman.SIZE / 2);
    }

    private void resetPacman() {
        pacman.deactivatePanelComponent();
        gameObjects.remove(pacman);

        pacman = createNewPacman();
        pacman.activatePanelComponent();
        gameObjects.add(pacman);
    }

    private Ghost createGhost(int col, int row, GhostType ghostType) {
        int ghostX = MainPanel.OFFSET_X + Wall.WALL_SIZE * col;
        int ghostY = MainPanel.OFFSET_Y + Wall.WALL_SIZE * row;
        return new Ghost(ghostX, ghostY, ghostType);
    }

    private Capsule createCapsule(int col, int row) {
        int screenX = MainPanel.OFFSET_X + Wall.WALL_SIZE * col + 4;
        int screenY = MainPanel.OFFSET_Y + Wall.WALL_SIZE * row + 4;
        return new Capsule(screenX, screenY);
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
                if (potentialX + Pacman.SIZE < 0) {
                    potentialX = MainPanel.OFFSET_X + Pacman.SIZE + map.length * Wall.WALL_SIZE;
                }
                break;
            case RIGHT:
                potentialX += Pacman.SPEED;
                if (potentialX > MainPanel.OFFSET_X + map.length * Wall.WALL_SIZE) {
                    potentialX = -Pacman.SIZE;
                }
                break;
            default:
                System.out.println("Unhandled direction.");
        }

        Rectangle collisionBody = getCollisionBodyOnPosition(pacman.getCollisionBody(), potentialX, potentialY);
        boolean collision = isCollisionWithWall(collisionBody);
        if (!collision) {
            pacman.setScreenX(potentialX);
            pacman.setScreenY(potentialY);
        }
    }

    private void moveGhosts() {
        for (Ghost ghost : ghosts) {
            moveGhost(ghost);
        }
    }

    private void moveGhost(Ghost ghost) {
        int potentialX = ghost.getScreenX();
        int potentialY = ghost.getScreenY();
        switch (ghost.getDirection()) {
            case UP:
                potentialY -= Ghost.SPEED;
                break;
            case DOWN:
                potentialY += Ghost.SPEED;
                break;
            case LEFT:
                potentialX -= Ghost.SPEED;
                if (potentialX + Ghost.SIZE < 0) {
                    potentialX = MainPanel.OFFSET_X + Ghost.SIZE + map.length * Wall.WALL_SIZE;
                }
                break;
            case RIGHT:
                potentialX += Ghost.SPEED;
                if (potentialX > MainPanel.OFFSET_X + map.length * Wall.WALL_SIZE) {
                    potentialX = -Ghost.SIZE;
                }
                break;
            default:
                System.out.println("Unhandled direction.");
        }

        Rectangle collisionBody = getCollisionBodyOnPosition(ghost.getCollisionBody(), potentialX, potentialY);
        boolean collision = isCollisionWithWall(collisionBody);

        if (collision) {
            // TODO Add ghost AI - Just random movement for now
            Random random = new Random();
            Direction nextDirection = (Direction) Arrays.stream(Direction.values()).toArray()[random.nextInt(4)];
            ghost.setDirection(nextDirection);
        } else {
            ghost.setScreenX(potentialX);
            ghost.setScreenY(potentialY);
        }
    }

    private Rectangle getCollisionBodyOnPosition(Rectangle collisionBody, int screenX, int screenY) {
        return new Rectangle(
                screenX + collisionBody.x,
                screenY + collisionBody.y,
                collisionBody.width,
                collisionBody.height);
    }

    private boolean isCollisionWithWall(Rectangle collisionBody) {
        for (Wall wall : walls) {
            Rectangle wallCollisionBody = wall.getCollisionBody();
            if (collisionBody.intersects(wallCollisionBody)) {
                return true;
            }
        }
        return false;
    }

    private Pellet getCollisionWithFood(Rectangle collisionBody) {
        for (Pellet pellet : pellets) {
            if (collisionBody.intersects(pellet.getCollisionBody())) {
                return pellet;
            }
        }
        return null;
    }

    private boolean isCollisionWithGhost(Rectangle collisionBody) {
        for (Ghost ghost : ghosts) {
            Rectangle ghostCollisionBody =
                    getCollisionBodyOnPosition(ghost.getCollisionBody(), ghost.getScreenX(), ghost.getScreenY());
            if (collisionBody.intersects(ghostCollisionBody)) {
                return true;
            }
        }
        return false;
    }

    private Capsule getCollisionWithCapsule(Rectangle collisionBody) {
        for (Capsule capsule : capsules) {
            if (collisionBody.intersects(capsule.getCollisionBody())) {
                return capsule;
            }
        }
        return null;
    }

    private Key getPressedKey() {
        return Arrays.stream(Key.values())
                .filter(Key::isPressed)
                .findAny().orElse(null);
    }

}
