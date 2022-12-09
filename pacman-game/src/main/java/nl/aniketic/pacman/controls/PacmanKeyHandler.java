package nl.aniketic.pacman.controls;

import nl.aniketic.engine.controls.KeyHandler;

import java.awt.event.KeyEvent;

public class PacmanKeyHandler extends KeyHandler {

    public PacmanKeyHandler() {
        putKey(KeyEvent.VK_W, Key.UP.getKeyObserver());
        putKey(KeyEvent.VK_A, Key.LEFT.getKeyObserver());
        putKey(KeyEvent.VK_D, Key.RIGHT.getKeyObserver());
        putKey(KeyEvent.VK_S, Key.DOWN.getKeyObserver());
    }
}
