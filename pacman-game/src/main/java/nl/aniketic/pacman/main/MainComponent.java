package nl.aniketic.pacman.main;

import nl.aniketic.pacman.gamestate.PacmanGameStateManager;

public class MainComponent {

    public static void main(String[] args) {
        PacmanGameStateManager pacmanGameStateManager = new PacmanGameStateManager();
        pacmanGameStateManager.startGame();
    }
}
