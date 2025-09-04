package DFS_Pior;

import java.util.ArrayList;
import java.util.List;

public class Vertex {
    private final Integer data;
    private boolean visited;

    private List<Vertex> neighbors = new ArrayList<>();

    public Vertex(Integer data) {
        this.data = data;
    }

    public Integer getData() {
        return data;
    }

    public boolean isVisited() {
        return visited;
    }

    public void setVisited(boolean visited) {
        this.visited = visited;
    }

    public List<Vertex> getNeighbors() {
        return neighbors;
    }

    public void setNeighbors(List<Vertex> list) {
        neighbors = list;
    }
}
