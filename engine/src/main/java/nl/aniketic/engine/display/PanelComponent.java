package nl.aniketic.engine.display;

import java.awt.Color;
import java.awt.Graphics2D;

public abstract class PanelComponent {

    protected Color color;
    protected int screenX;
    protected int screenY;
    protected int width;
    protected int height;

    public PanelComponent() {
        color = Color.WHITE;
        screenX = 0;
        screenY = 0;
        width = 100;
        height = 100;
    }

    public void activate() {
        GamePanel.addPanelComponent(this);
    }

    public void deactivate() {
        GamePanel.removePanelComponent(this);
    }

    public void paintComponent(Graphics2D g2) {
        g2.setColor(color);
        g2.fillRect(screenX, screenY, width, height);
    }

    public void setColor(Color color) {
        this.color = color;
    }

    public void setScreenX(int screenX) {
        this.screenX = screenX;
    }

    public void setScreenY(int screenY) {
        this.screenY = screenY;
    }

    public void addScreenX(int value) {
        this.screenX += value;
    }

    public void addScreenY(int value) {
        this.screenY += value;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public Color getColor() {
        return color;
    }

    public int getScreenX() {
        return screenX;
    }

    public int getScreenY() {
        return screenY;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }
}
