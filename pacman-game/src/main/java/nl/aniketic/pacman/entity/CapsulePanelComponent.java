package nl.aniketic.pacman.entity;

import nl.aniketic.engine.display.PanelComponent;

import java.awt.Color;
import java.awt.Graphics2D;

public class CapsulePanelComponent implements PanelComponent {

    private int screenX;
    private int screenY;
    private int size;

    public CapsulePanelComponent(int screenX, int screenY, int size) {
        this.screenX = screenX;
        this.screenY = screenY;
        this.size = size;
    }

    @Override
    public void paintComponent(Graphics2D g2) {
        g2.setColor(Color.PINK);
        g2.fillOval(screenX, screenY, size, size);
    }
}
