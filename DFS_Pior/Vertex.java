package DFS_Pior;

import java.util.ArrayList;
import java.util.List;

public class Vertex {
    private final String name;
    private boolean visited;
    private List<Edge> neighbors = new ArrayList<>();
    private int heuristic;

    public Vertex(String name) {
        this.name = name;
        this.heuristic = 0; // Default
    }

    public String getName() {
        return name;
    }

    public boolean isVisited() {
        return visited;
    }

    public void setVisited(boolean visited) {
        this.visited = visited;
    }

    public List<Edge> getNeighbors() {
        return neighbors;
    }

    public void addNeighbor(Vertex to, int cost) {
        neighbors.add(new Edge(to, cost));
    }

    public int getHeuristic() {
        return heuristic;
    }

    public void setHeuristic(int heuristic) {
        this.heuristic = heuristic;
    }

    static class Edge {
        Vertex to;
        int cost;

        Edge(Vertex to, int cost) {
            this.to = to;
            this.cost = cost;
        }
    }
}
