package nl.aniketic.engine.gamestate;

import nl.aniketic.engine.display.PanelComponent;

public abstract class GameObject {

    protected PanelComponent panelComponent;

    public void activatePanelComponent() {
        if (panelComponent != null) {
            panelComponent.activate();
        }
    }

    public void deactivatePanelComponent() {
        if (panelComponent != null) {
            panelComponent.deactivate();
        }
    }

    public abstract void update();
}
