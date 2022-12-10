package nl.aniketic.pacman.pathfinding;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class PathfindingController {

    private final int[][] map;

    private List<Node> nodes;

    public PathfindingController(int[][] map) {
        this.map = map;
        resetNodes();
    }

    public void resetNodes() {
        nodes = new ArrayList<>();
        for (int row = 0; row < map.length; row++) {
            for (int col = 0; col < map[0].length; col++) {
                int mapValue = map[row][col];

                Node node = new Node(col, row);
                node.setWalkable(mapValue != 1);

                nodes.add(node);
            }
        }

        for (Node node : nodes) {
            int col = node.getCol();
            int row = node.getRow();
            Node nodeUp = getNode(col, row - 1);
            Node nodeDow = getNode(col, row + 1);
            Node nodeLeft = getNode(col - 1, row);
            Node nodeRight = getNode(col + 1, row);

            if (nodeUp != null) {
                node.addNeighbour(nodeUp);
            }
            if (nodeDow != null) {
                node.addNeighbour(nodeDow);
            }
            if (nodeLeft != null) {
                node.addNeighbour(nodeLeft);
            }
            if (nodeRight != null) {
                node.addNeighbour(nodeRight);
            }
        }
    }

    public List<Node> getPath(Node startNode, Node targetNode) {
        List<Node> nodesToSearch = new ArrayList<>();
        nodesToSearch.add(startNode);
        List<Node> nodesProcessed = new ArrayList<>();

        while (!nodesToSearch.isEmpty()) {
            Node current = nodesToSearch.get(0);
            for (Node n : nodesToSearch) {
                if (n.getfValue() < current.getfValue()
                        || n.getfValue() == current.getfValue() && n.gethValue() < current.gethValue()) {
                    current = n;
                }
            }

            nodesProcessed.add(current);
            nodesToSearch.remove(current);


            if (current.equals(targetNode)) {
                Node currentPathTile = targetNode;
                List<Node> path = new ArrayList<>();
                while (currentPathTile != startNode) {
                    path.add(currentPathTile);
                    currentPathTile = currentPathTile.getConnection();
                }
                return path;
            }


            List<Node> neighbours = current.getNeighbours().stream()
                    .filter(n -> n.isWalkable() && !nodesProcessed.contains(n))
                    .collect(Collectors.toList());

            for (Node neighbour : neighbours) {
                boolean inSearch = nodesToSearch.contains(neighbour);
                int costToNeighbour = current.getgValue() + current.getDistance(neighbour);

                if (!inSearch || costToNeighbour < neighbour.getgValue()) {
                    neighbour.setgValue(costToNeighbour);
                    neighbour.setConnection(current);

                    if (!inSearch) {
                        neighbour.sethValue(neighbour.getDistance(targetNode));
                        nodesToSearch.add(neighbour);
                    }
                }
            }
        }

        return null;
    }

    public Node getNode(int col, int row) {
        for (Node node : nodes) {
            if (node.getCol() == col && node.getRow() == row) {
                return node;
            }
        }
        return null;
    }
}
