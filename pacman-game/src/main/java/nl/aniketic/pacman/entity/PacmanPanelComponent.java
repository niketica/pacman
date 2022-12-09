package nl.aniketic.pacman.entity;

import nl.aniketic.engine.display.PanelComponent;

import javax.imageio.ImageIO;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class PacmanPanelComponent extends PanelComponent {

    private final int size;

    private int screenX;
    private int screenY;

    private BufferedImage[] images;
    private int imageIndex;

    public PacmanPanelComponent(int col, int row, int size) {
        this.screenX = col;
        this.screenY = row;
        this.size = size;
        this.imageIndex = 0;
    }

    public void loadImages() {
        images = new BufferedImage[6];
        BufferedImage image_1 = loadImage("/img/pacman_1.png");
        BufferedImage image_2 = loadImage("/img/pacman_2.png");
        BufferedImage image_3 = loadImage("/img/pacman_3.png");
        BufferedImage image_4 = loadImage("/img/pacman_4.png");

        images[0] = image_1;
        images[1] = image_2;
        images[2] = image_3;
        images[3] = image_4;
        images[4] = image_3;
        images[5] = image_2;
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
        BufferedImage image = images[imageIndex];
        g2.drawImage(image, screenX, screenY, null);
    }

    public void updateImageFrame() {
        imageIndex++;
        if (imageIndex > images.length - 1) {
            imageIndex = 0;
        }
    }

    public void rotate(int degrees) {
        for (int i = 0; i < images.length; i++) {
            BufferedImage image = images[i];
            images[i] = rotate(image, degrees);
        }
    }

    @Override
    public int getScreenX() {
        return screenX;
    }

    @Override
    public void setScreenX(int screenX) {
        this.screenX = screenX;
    }

    @Override
    public int getScreenY() {
        return screenY;
    }

    @Override
    public void setScreenY(int screenY) {
        this.screenY = screenY;
    }
}
