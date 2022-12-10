package nl.aniketic.pacman.entity;

public enum GhostType {
    CLYDE(0),
    BLINKY(1),
    INKY(2),
    PINKY(3);

    private final int imageIndex;

    GhostType(int imageIndex) {
        this.imageIndex = imageIndex;
    }

    public int getImageIndex() {
        return imageIndex;
    }
}
