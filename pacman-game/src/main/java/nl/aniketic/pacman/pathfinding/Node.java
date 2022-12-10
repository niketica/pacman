package nl.aniketic.pacman.pathfinding;

import java.util.ArrayList;
import java.util.List;

public class Node {

    private int col;
    private int row;

    private int gValue;
    private int hValue;

    private boolean walkable;

    private Node connection;

    private List<Node> neighbours;

    public Node(int col, int row) {
        this.col = col;
        this.row = row;
        this.gValue = 99999999;
        this.hValue = 99999999;
    }

    public int getCol() {
        return col;
    }

    public int getRow() {
        return row;
    }

    public int getgValue() {
        return gValue;
    }

    public void setgValue(int gValue) {
        this.gValue = gValue;
    }

    public int getfValue() {
        return gValue + hValue;
    }

    public boolean isWalkable() {
        return walkable;
    }

    public void setWalkable(boolean walkable) {
        this.walkable = walkable;
    }

    public int gethValue() {
        return hValue;
    }

    public void sethValue(int hValue) {
        this.hValue = hValue;
    }

    public Node getConnection() {
        return connection;
    }

    public void setConnection(Node connection) {
        this.connection = connection;
    }

    public void addNeighbour(Node node) {
        if (neighbours == null) {
            neighbours = new ArrayList<>();
        }
        neighbours.add(node);
    }

    public List<Node> getNeighbours() {
        return neighbours;
    }

    public int getDistance(Node node) {
        int colDistance = col - node.getCol();
        if (colDistance < 0) {
            colDistance *= -1;
        }

        int rowDistance = col - node.getRow();
        if (rowDistance <0) {
            rowDistance *= -1;
        }

        return colDistance + rowDistance;
    }
}
