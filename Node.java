import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class Node implements Comparable<Node> {
    private final String name;
    private Integer minDistance = Integer.MAX_VALUE; // MAX_VALUE simula infinito
    private Map<Node, Integer> adjacentNodes = new HashMap<>(); // guarda os nós vizinhos com seu peso/caminho
    private List<Node> shortestPath = new LinkedList<>(); // guarda os caminhos mais curto do source

    public Node(String name) {
        this.name = name;
    }

    public void setDistance(Integer distance) {
        this.minDistance = distance;
    }

    public Integer getDistance() {
        return this.minDistance;
    }

    public Map<Node, Integer> getAdjacentNodes() {
        return adjacentNodes;
    }

    public String getName() {
        return name;
    }

    // coloca no adjacente com seu peso
    public void addAdjacentNode(Node node, int weight) {
        adjacentNodes.put(node, weight);
    }

    // uso de PriorityQueue para comparar qual sera o menor caminho de no
    @Override
    public int compareTo(Node node) {
        return Integer.compare(this.minDistance, node.getDistance());
    }

    public List<Node> getShortestPath() {
        return this.shortestPath;
    }

    public void setShortestPath(List<Node> list) {
        this.shortestPath = list;
    }
}
