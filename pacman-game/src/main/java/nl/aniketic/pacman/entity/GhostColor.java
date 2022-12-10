package nl.aniketic.pacman.entity;

public enum GhostColor {
    ORANGE(0),
    RED(1),
    CYAN(2),
    PINK(3);

    private final int imageIndex;

    GhostColor(int imageIndex) {
        this.imageIndex = imageIndex;
    }

    public int getImageIndex() {
        return imageIndex;
    }
}
