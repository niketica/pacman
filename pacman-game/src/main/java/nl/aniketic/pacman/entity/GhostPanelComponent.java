package nl.aniketic.pacman.entity;

import nl.aniketic.engine.display.PanelComponent;

import javax.imageio.ImageIO;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class GhostPanelComponent implements PanelComponent {

    private int screenX;
    private int screenY;
    private Direction direction;

    private BufferedImage[] images;
    private GhostColor ghostColor;

    public GhostPanelComponent(int screenX, int screenY, GhostColor ghostColor, Direction direction) {
        this.screenX = screenX;
        this.screenY = screenY;
        this.ghostColor = ghostColor;
        this.direction = direction;
    }

    public void loadImages() {
        images = new BufferedImage[5];
        BufferedImage image_1 = loadImage("/img/ghost_1.png");
        BufferedImage image_2 = loadImage("/img/ghost_2.png");
        BufferedImage image_3 = loadImage("/img/ghost_3.png");
        BufferedImage image_4 = loadImage("/img/ghost_4.png");
        BufferedImage image_5 = loadImage("/img/ghost_5.png");

        images[0] = image_1;
        images[1] = image_2;
        images[2] = image_3;
        images[3] = image_4;
        images[4] = image_5;
    }

    private BufferedImage loadImage(String path) {
        try {
            BufferedImage image = ImageIO.read(getClass().getResourceAsStream(path));
            return scaleImage(image, Ghost.SIZE, Ghost.SIZE);
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

    @Override
    public void paintComponent(Graphics2D g2) {
        BufferedImage image = images[ghostColor.getImageIndex()];
        g2.drawImage(image, screenX, screenY, null);
    }

    public void setScreenX(int screenX) {
        this.screenX = screenX;
    }

    public void setScreenY(int screenY) {
        this.screenY = screenY;
    }

    public void setDirection(Direction direction) {
        this.direction = direction;
    }
}
