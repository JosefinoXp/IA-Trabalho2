package Dijkstra_Bonus;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class Node implements Comparable<Node> {
    private final String name;
    private Integer minDistance = Integer.MAX_VALUE; // MAX_VALUE simula infinito
    private Map<Node, Integer> adjacentNodes = new HashMap<>(); // guarda os nós vizinhos com seu peso/caminho
    private List<Node> shortestPath = new LinkedList<>(); // guarda os caminhos mais curto do source

    // Função: Node (construtor)
    // Descrição: Inicializa um novo nó com o nome fornecido.
    // Entrada: String name (nome do nó).
    // Saída: Um objeto Node inicializado.
    // Pré-Condicao: O nome deve ser uma string válida.
    // Pós-Condicao: O nó é criado com distância infinita e sem adjacentes.
    public Node(String name) {
        this.name = name;
    }

    // Função: setDistance
    // Descrição: Define a distância mínima do nó em relação ao nó inicial.
    // Entrada: Integer distance (nova distância).
    // Saída: Nenhuma.
    // Pré-Condicao: O valor deve ser um inteiro válido.
    // Pós-Condicao: A distância mínima do nó é atualizada.
    public void setDistance(Integer distance) {
        this.minDistance = distance;
    }

    // Função: getDistance
    // Descrição: Retorna a distância mínima registrada para o nó.
    // Entrada: Nenhuma.
    // Saída: Integer (distância mínima).
    // Pré-Condicao: Nenhuma.
    // Pós-Condicao: Retorna o valor atual da distância mínima.
    public Integer getDistance() {
        return this.minDistance;
    }

    // Função: getAdjacentNodes
    // Descrição: Retorna o mapa de nós adjacentes e seus respectivos pesos.
    // Entrada: Nenhuma.
    // Saída: Map<Node, Integer> (nós adjacentes e pesos).
    // Pré-Condicao: Nenhuma.
    // Pós-Condicao: Retorna o mapa atual de adjacentes.
    public Map<Node, Integer> getAdjacentNodes() {
        return adjacentNodes;
    }

    // Função: getName
    // Descrição: Retorna o nome do nó.
    // Entrada: Nenhuma.
    // Saída: String (nome do nó).
    // Pré-Condicao: Nenhuma.
    // Pós-Condicao: Retorna o nome do nó.
    public String getName() {
        return name;
    }

    // Função: addAdjacentNode
    // Descrição: Adiciona um nó adjacente com o peso da aresta.
    // Entrada: Node node (nó adjacente), int weight (peso da aresta).
    // Saída: Nenhuma.
    // Pré-Condicao: O nó adjacente deve ser válido.
    // Pós-Condicao: O nó adjacente é adicionado ao mapa de adjacentes.
    public void addAdjacentNode(Node node, int weight) {
        adjacentNodes.put(node, weight);
    }

    // Função: compareTo
    // Descrição: Compara este nó com outro nó baseado na distância mínima (usado em PriorityQueue).
    // Entrada: Node node (nó para comparar).
    // Saída: int (resultado da comparação).
    // Pré-Condicao: O nó deve ser válido.
    // Pós-Condicao: Retorna -1, 0 ou 1 conforme a comparação.
    @Override
    public int compareTo(Node node) {
        return Integer.compare(this.minDistance, node.getDistance());
    }

    // Função: getShortestPath
    // Descrição: Retorna a lista de nós que compõem o caminho mais curto até este nó.
    // Entrada: Nenhuma.
    // Saída: List<Node> (caminho mais curto).
    // Pré-Condicao: Nenhuma.
    // Pós-Condicao: Retorna o caminho mais curto atual.
    public List<Node> getShortestPath() {
        return this.shortestPath;
    }

    // Função: setShortestPath
    // Descrição: Define a lista de nós que compõem o caminho mais curto até este nó.
    // Entrada: List<Node> list (novo caminho).
    // Saída: Nenhuma.
    // Pré-Condicao: A lista deve ser válida.
    // Pós-Condicao: O caminho mais curto do nó é atualizado.
    public void setShortestPath(List<Node> list) {
        this.shortestPath = list;
    }
}
