package nl.aniketic.pacman.gamestate;

import nl.aniketic.engine.display.PanelComponent;

import javax.imageio.ImageIO;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class SidePanelComponent implements PanelComponent {

    private static final Font ARIAL_20 = new Font("Arial", Font.PLAIN, 20);

    private final int screenX;
    private final int screenY;
    private final int pacmanSize;

    private BufferedImage pacman;
    private int score;
    private int lives;
    private boolean victory;
    private boolean gameOver;

    public SidePanelComponent(int screenX, int screenY) {
        this.screenX = screenX;
        this.screenY = screenY;
        score = 0;
        pacmanSize = 20;
    }

    @Override
    public void paintComponent(Graphics2D g2) {
        g2.setColor(Color.WHITE);
        g2.setFont(ARIAL_20);
        g2.drawString("Score " + score, screenX, screenY + 20);
        g2.drawString("Lives ", screenX + 100, screenY + 20);
        for (int i=0; i<lives; i++) {
            int xPos = screenX + 160 + i * pacmanSize + 10 * i;
            int yPos = screenY;
            g2.drawImage(pacman, xPos, yPos, null);
        }

        if (victory) {
            g2.drawString("You won! Press [space] to restart.", screenX, screenY + 40);
        } else if (gameOver) {
            g2.drawString("Game over! Press [space] to restart.", screenX, screenY + 40);
        }
    }

    public void setScore(int score) {
        this.score = score;
    }

    public void setLives(int lives) {
        this.lives = lives;
    }

    public void setVictory(boolean victory) {
        this.victory = victory;
    }

    public void setGameOver(boolean gameOver) {
        this.gameOver = gameOver;
    }

    public void loadImages() {
        pacman = loadImage("/img/pacman_4.png");
    }

    private BufferedImage loadImage(String path) {
        try {
            BufferedImage image = ImageIO.read(getClass().getResourceAsStream(path));
            return scaleImage(image, pacmanSize, pacmanSize);
        } catch (IOException e) {
            throw new IllegalStateException("Could not load image.", e);
        }
    }

    private BufferedImage scaleImage(BufferedImage original, int width, int height) {
        BufferedImage scaledImage = new BufferedImage(width, height, original.getType());
        Graphics2D g2 = scaledImage.createGraphics();
        g2.drawImage(original, 0, 0, width, height, null);
        g2.dispose();
        return scaledImage;
    }
}
