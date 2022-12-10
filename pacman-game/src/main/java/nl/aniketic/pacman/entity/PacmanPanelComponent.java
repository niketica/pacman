package nl.aniketic.pacman.entity;

import nl.aniketic.engine.display.PanelComponent;

import javax.imageio.ImageIO;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class PacmanPanelComponent implements PanelComponent {

    private final int size;

    private int screenX;
    private int screenY;

    private BufferedImage[] frames;
    private BufferedImage[] deathFrames;
    private int framesIndex;
    private int deathFramesIndex;
    private boolean dead;

    public PacmanPanelComponent(int col, int row, int size) {
        this.screenX = col;
        this.screenY = row;
        this.size = size;
        this.framesIndex = 0;
        this.deathFramesIndex = 0;
    }

    public void loadImages() {
        frames = new BufferedImage[6];
        BufferedImage image_1 = loadImage("/img/pacman_1.png");
        BufferedImage image_2 = loadImage("/img/pacman_2.png");
        BufferedImage image_3 = loadImage("/img/pacman_3.png");
        BufferedImage image_4 = loadImage("/img/pacman_4.png");

        frames[0] = image_1;
        frames[1] = image_2;
        frames[2] = image_3;
        frames[3] = image_4;
        frames[4] = image_3;
        frames[5] = image_2;

        deathFrames = new BufferedImage[4];
        BufferedImage image_5 = loadImage("/img/pacman_5.png");
        BufferedImage image_6 = loadImage("/img/pacman_6.png");
        BufferedImage image_7 = loadImage("/img/pacman_7.png");
        BufferedImage image_8 = loadImage("/img/pacman_8.png");

        deathFrames[0] = image_5;
        deathFrames[1] = image_6;
        deathFrames[2] = image_7;
        deathFrames[3] = image_8;
    }

    private BufferedImage loadImage(String path) {
        try {
            BufferedImage image = ImageIO.read(getClass().getResourceAsStream(path));
            return scaleImage(image, size, size);
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

    private BufferedImage rotate(BufferedImage original, int degrees) {
        int width = original.getWidth();
        int height = original.getHeight();
        BufferedImage rotatedImage = new BufferedImage(width, height, original.getType());
        Graphics2D g2 = rotatedImage.createGraphics();
        g2.rotate(Math.toRadians(degrees), width / 2, height / 2);
        g2.drawImage(original, null, 0, 0);
        g2.dispose();
        return rotatedImage;
    }

    @Override
    public void paintComponent(Graphics2D g2) {
        if (dead) {
            if (deathFramesIndex < deathFrames.length) {
                BufferedImage image = deathFrames[deathFramesIndex];
                g2.drawImage(image, screenX, screenY, null);
            }
        } else {
            BufferedImage image = frames[framesIndex];
            g2.drawImage(image, screenX, screenY, null);
        }
    }

    public void updateImageFrame() {
        framesIndex++;
        if (framesIndex > frames.length - 1) {
            framesIndex = 0;
        }

        if (dead && deathFramesIndex < deathFrames.length) {
            deathFramesIndex++;
        }
    }

    public void rotate(int degrees) {
        for (int i = 0; i < frames.length; i++) {
            BufferedImage image = frames[i];
            frames[i] = rotate(image, degrees);
        }
    }

    public void setScreenX(int screenX) {
        this.screenX = screenX;
    }

    public void setScreenY(int screenY) {
        this.screenY = screenY;
    }

    public void setDead(boolean dead) {
        this.dead = dead;
        framesIndex = 0;
    }
}
