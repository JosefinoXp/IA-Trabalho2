package DFS_Pior;

import java.io.File;
import java.util.*;

import DFS_Pior.Vertex.Edge;

public class DFS {

    private static class PathState {
        Vertex vertex;
        int g;
        PathState parentState;

        PathState(Vertex vertex, int g, PathState parentState) {
            this.vertex = vertex;
            this.g = g;
            this.parentState = parentState;
        }
    }

    // Função: traverse
    // Descrição: Realiza a busca em profundidade (DFS) para encontrar o caminho do vértice inicial ao objetivo, imprimindo iterações e resultados conforme especificações.
    // Entrada: Vertex start (vértice inicial), Vertex goal (vértice objetivo).
    // Saída: Imprime o processo de execução e o resumo final no console.
    // Pré-Condicao: O grafo deve estar carregado corretamente com vértices e arestas; start e goal devem existir.
    // Pós-Condicao: O caminho é encontrado e impresso se existir; caso contrário, informa que não foi encontrado.
    public void traverse(Vertex start, Vertex goal) {
        System.out.println("Início da execução");

        Set<String> visited = new HashSet<>();
        PriorityQueue<PathState> stack = new PriorityQueue<>(Comparator.comparingInt(s -> s.g + s.vertex.getHeuristic()));
        PathState init = new PathState(start, 0, null);
        stack.add(init);
        visited.add(start.getName());

        int expandedNodes = 0; // nova variável para contar nós expandidos
        boolean found = false;
        int distance = -1;
        List<String> path = null;
        int finalPerformance = 0;

        while (!stack.isEmpty() && !found) {
            PathState current = stack.poll();
            Vertex v = current.vertex;

            expandedNodes++; // conta nó expandido

            if (v.getName().equals(goal.getName())) {
                found = true;
                distance = current.g;
                path = new ArrayList<>();
                PathState temp = current;
                while (temp != null) {
                    path.add(temp.vertex.getName());
                    temp = temp.parentState;
                }
                Collections.reverse(path);
                finalPerformance = expandedNodes;
                continue;
            }

            // Expand
            List<Edge> neigh = new ArrayList<>(v.getNeighbors());
            Collections.reverse(neigh);
            for (Edge edge : neigh) {
                Vertex child = edge.to;
                if (!visited.contains(child.getName())) {
                    visited.add(child.getName());
                    stack.add(new PathState(child, current.g + edge.cost, current));
                }
            }

            // Print after expand if stack not empty
            if (!stack.isEmpty()) {
                System.out.println("Iteração " + expandedNodes + ":");

                System.out.print("Lista: ");
                // Para exibir a lista ordenada como está na fila de prioridade
                List<PathState> currentList = new ArrayList<>(stack);
                currentList.sort(Comparator.comparingInt(s -> s.g + s.vertex.getHeuristic()));
                for (PathState state : currentList) {
                    int h = state.vertex.getHeuristic();
                    int soma = state.g + h;
                    System.out.print("(" + state.vertex.getName() + ": " + state.g + " + " + h + " = " + soma + ") ");
                }
                System.out.println();

                // Medida de desempenho: quantidade de nós expandidos
                System.out.println("Medida de desempenho (nós expandidos): " + expandedNodes);
                finalPerformance = expandedNodes;
            }
        }

        System.out.println("Fim da execução");

        if (found) {
            System.out.println("Distância: " + distance);
            System.out.println("Caminho: " + String.join(" – ", path));
            System.out.println("Medida de desempenho (nós expandidos): " + finalPerformance);
        } else {
            System.out.println("Caminho não encontrado.");
        }
    }

    // Função: main
    // Descrição: Ponto de entrada do programa; lê o arquivo de entrada, constrói o grafo e executa a busca DFS.
    // Entrada: Argumentos da linha de comando (não utilizados).
    // Saída: Executa a busca e imprime resultados no console.
    // Pré-Condicao: O arquivo "arquivoEntrada.txt" deve existir e estar no formato correto.
    // Pós-Condicao: O grafo é construído e a busca é realizada.
    public static void main(String[] args) {
        try {
            Scanner scanner = new Scanner(new File("arquivoEntrada.txt"));
            Map<String, Vertex> nodes = new HashMap<>();
            String startName = null;
            String goalName = null;
            boolean directed = true;

            while (scanner.hasNextLine()) {
                String line = scanner.nextLine().trim();
                if (line.isEmpty() || line.startsWith("%")) continue;
                if (line.endsWith(".")) line = line.substring(0, line.length() - 1);

                if (line.startsWith("ponto_inicial(")) {
                    startName = line.substring(14, line.length() - 1).trim();
                } else if (line.startsWith("ponto_final(")) {
                    goalName = line.substring(12, line.length() - 1).trim();
                } else if (line.startsWith("orientado(")) {
                    String val = line.substring(10, line.length() - 1).trim();
                    directed = val.equals("s");
                } else if (line.startsWith("pode_ir(")) {
                    String[] parts = line.substring(8, line.length() - 1).split(",");
                    String from = parts[0].trim();
                    String to = parts[1].trim();
                    int cost = Integer.parseInt(parts[2].trim());
                    nodes.computeIfAbsent(from, Vertex::new);
                    nodes.computeIfAbsent(to, Vertex::new);
                    nodes.get(from).addNeighbor(nodes.get(to), cost);
                    if (!directed) {
                        nodes.get(to).addNeighbor(nodes.get(from), cost);
                    }
                } else if (line.startsWith("h(")) {
                    String[] parts = line.substring(2, line.length() - 1).split(",");
                    String from = parts[0].trim();
                    int val = Integer.parseInt(parts[2].trim());
                    nodes.computeIfAbsent(from, Vertex::new);
                    nodes.get(from).setHeuristic(val);
                }
            }
            scanner.close();

            Vertex start = nodes.get(startName);
            Vertex goal = nodes.get(goalName);

            DFS dfs = new DFS();
            dfs.traverse(start, goal);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
