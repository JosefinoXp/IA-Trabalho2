package DFS_Pior;

import java.util.ArrayList;
import java.util.List;

public class Vertex {
    private final String name;
    private boolean visited;
    private List<Edge> neighbors = new ArrayList<>();
    private int heuristic;

    // Função: Vertex (construtor)
    // Descrição: Cria um novo vértice com o nome especificado e valores padrão.
    // Entrada: String name (nome do vértice).
    // Saída: Instância de Vertex.
    // Pré-Condicao: O nome deve ser uma string válida.
    // Pós-Condicao: O vértice é inicializado com heurística 0 e sem vizinhos.
    public Vertex(String name) {
        this.name = name;
        this.heuristic = 0; // Default
    }

    // Função: getName
    // Descrição: Retorna o nome do vértice.
    // Entrada: Nenhuma.
    // Saída: String com o nome do vértice.
    // Pré-Condicao: O vértice deve estar inicializado.
    // Pós-Condicao: Nenhuma alteração no estado.
    public String getName() {
        return name;
    }

    // Função: isVisited
    // Descrição: Verifica se o vértice foi visitado.
    // Entrada: Nenhuma.
    // Saída: Booleano indicando se foi visitado.
    // Pré-Condicao: O vértice deve estar inicializado.
    // Pós-Condicao: Nenhuma alteração no estado.
    public boolean isVisited() {
        return visited;
    }

    // Função: setVisited
    // Descrição: Define o status de visitado do vértice.
    // Entrada: boolean visited (novo status).
    // Saída: Nenhuma.
    // Pré-Condicao: O vértice deve estar inicializado.
    // Pós-Condicao: O status de visitado é atualizado.
    public void setVisited(boolean visited) {
        this.visited = visited;
    }

    // Função: getNeighbors
    // Descrição: Retorna a lista de arestas vizinhas.
    // Entrada: Nenhuma.
    // Saída: Lista de Edge.
    // Pré-Condicao: O vértice deve estar inicializado.
    // Pós-Condicao: Nenhuma alteração no estado.
    public List<Edge> getNeighbors() {
        return neighbors;
    }

    // Função: addNeighbor
    // Descrição: Adiciona uma aresta vizinha ao vértice.
    // Entrada: Vertex to (vértice destino), int cost (custo da aresta).
    // Saída: Nenhuma.
    // Pré-Condicao: O vértice deve estar inicializado; to deve ser um Vertex válido.
    // Pós-Condicao: Uma nova aresta é adicionada à lista de vizinhos.
    public void addNeighbor(Vertex to, int cost) {
        neighbors.add(new Edge(to, cost));
    }

    // Função: getHeuristic
    // Descrição: Retorna o valor heurístico do vértice.
    // Entrada: Nenhuma.
    // Saída: Inteiro com o valor heurístico.
    // Pré-Condicao: O vértice deve estar inicializado.
    // Pós-Condicao: Nenhuma alteração no estado.
    public int getHeuristic() {
        return heuristic;
    }

    // Função: setHeuristic
    // Descrição: Define o valor heurístico do vértice.
    // Entrada: int heuristic (novo valor heurístico).
    // Saída: Nenhuma.
    // Pré-Condicao: O vértice deve estar inicializado.
    // Pós-Condicao: O valor heurístico é atualizado.
    public void setHeuristic(int heuristic) {
        this.heuristic = heuristic;
    }

    static class Edge {
        Vertex to;
        int cost;

        // Função: Edge (construtor)
        // Descrição: Cria uma nova aresta com destino e custo.
        // Entrada: Vertex to (destino), int cost (custo).
        // Saída: Instância de Edge.
        // Pré-Condicao: to deve ser um Vertex válido; cost deve ser um inteiro não negativo.
        // Pós-Condicao: A aresta é inicializada.
        Edge(Vertex to, int cost) {
            this.to = to;
            this.cost = cost;
        }
    }
}
