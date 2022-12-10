package nl.aniketic.pacman.entity;

import nl.aniketic.engine.display.PanelComponent;

import java.awt.Color;
import java.awt.Graphics2D;

public class PelletPanelComponent implements PanelComponent {

    private final int screenX;
    private final int screenY;

    public PelletPanelComponent(int screenX, int screenY) {
        this.screenX = screenX;
        this.screenY = screenY;
    }

    @Override
    public void paintComponent(Graphics2D g2) {
        g2.setColor(Color.WHITE);
        g2.fillOval(
                screenX + Pellet.BLOCK_SIZE / 2 - Pellet.FOOD_SIZE / 2,
                screenY + Pellet.BLOCK_SIZE / 2 - Pellet.FOOD_SIZE / 2,
                Pellet.FOOD_SIZE,
                Pellet.FOOD_SIZE);
    }
}
