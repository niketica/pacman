package nl.aniketic.engine.display;

import javax.swing.JPanel;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.util.ArrayList;
import java.util.List;

public class GamePanel extends JPanel {

    // TODO split into different lists for layers
    private static List<PanelComponent> PANEL_COMPONENTS = new ArrayList<>();

    public GamePanel(int screenWidth, int screenHeight) {
        this.setPreferredSize(new Dimension(screenWidth, screenHeight));
        this.setBackground(Color.BLACK);
        this.setDoubleBuffered(true);
        this.setFocusable(true);
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        Graphics2D g2 = (Graphics2D) g;
        PANEL_COMPONENTS.forEach(panelComponent -> panelComponent.paintComponent(g2));

        g.dispose();
    }

    public static void addPanelComponent(PanelComponent panelComponent) {
        PANEL_COMPONENTS.add(panelComponent);
    }

    public static void removePanelComponent(PanelComponent panelComponent) {
        PANEL_COMPONENTS.remove(panelComponent);
    }

    public static void cleanGamePanel() {
        PANEL_COMPONENTS = new ArrayList<>();
    }
}
