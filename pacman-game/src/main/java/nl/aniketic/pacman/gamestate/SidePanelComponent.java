package nl.aniketic.pacman.gamestate;

import nl.aniketic.engine.display.PanelComponent;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;

public class SidePanelComponent implements PanelComponent {

    private static final Font ARIAL_20 = new Font("Arial", Font.PLAIN, 20);

    private int screenX;
    private int screenY;
    private int score;

    public SidePanelComponent(int screenX, int screenY) {
        this.screenX = screenX;
        this.screenY = screenY;
        score = 0;
    }

    @Override
    public void paintComponent(Graphics2D g2) {
        g2.setColor(Color.WHITE);
        g2.setFont(ARIAL_20);
        g2.drawString("Score " + score, screenX, screenY + 20);
    }

    public void setScore(int score) {
        this.score = score;
    }
}
