import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Dijkstra {

    // Função: calculateShortestPath
    // Descrição: Calcula o caminho mais curto entre o nó inicial (source) e o nó final (target) usando o algoritmo de Dijkstra.
    // Entrada: Node source (nó inicial), Node target (nó final), Map<String, Integer> heuristics (mapa de heurísticas, não usado no Dijkstra).
    // Saída: Nenhuma (imprime o resultado no console).
    // Pré-Condicao: O grafo deve estar corretamente inicializado, com nós e arestas.
    // Pós-Condicao: O caminho mais curto e a distância são exibidos no console.
    public static void calculateShortestPath(Node source, Node target, Map<String, Integer> heuristics) {
        source.setDistance(0);
        Set<Node> settledNodes = new HashSet<>();
        Queue<Node> unsettledNodes = new PriorityQueue<>(Collections.singleton(source));
        int iteration = 1;
        int nodesExpanded = 0; // Medida de desempenho: nós expandidos acumulados

        System.out.println("Início da execução");

        while (!unsettledNodes.isEmpty()) {
            // Exibição iterativa
            System.out.println("\nIteração " + iteration + ":");
            System.out.print("Lista: ");
            for (Node node : unsettledNodes) {
                int h = heuristics.getOrDefault(node.getName(), 0); // Heurística (0 para Dijkstra)
                System.out.print("(" + node.getName() + ": " + node.getDistance() + " + " + h + " = " + (node.getDistance() + h) + ") ");
            }
            System.out.println();
            System.out.println("Medida de desempenho: " + nodesExpanded); // Exemplo: nós expandidos

            Node currentNode = unsettledNodes.poll();
            nodesExpanded++; // Incrementa medida

            // Se alcançou o target, pode parar (otimização opcional)
            if (currentNode == target) {
                break;
            }

            currentNode.getAdjacentNodes()
                    .entrySet().stream().filter(entry -> !settledNodes.contains(entry.getKey()))
                    .forEach(entry -> {
                        evaluateDistanceAndPath(entry.getKey(), entry.getValue(), currentNode);
                        unsettledNodes.add(entry.getKey());
                    });
            settledNodes.add(currentNode);
            iteration++;
        }

        System.out.println("\nFim da execução");
        // Resumo
        System.out.println("Distância: " + target.getDistance());
        String path = target.getShortestPath().stream().map(Node::getName).collect(Collectors.joining(" – "));
        System.out.println("Caminho: " + path + " – " + target.getName());
        System.out.println("Medida de desempenho: " + nodesExpanded);
    }

    // Função: evaluateDistanceAndPath
    // Descrição: Avalia se o caminho passando pelo nó atual é menor que o já registrado no nó adjacente e atualiza se necessário.
    // Entrada: Node adjacentNode (nó adjacente), Integer edgeWeight (peso da aresta), Node sourceNode (nó de origem).
    // Saída: Nenhuma (atualiza o nó adjacente se encontrar caminho menor).
    // Pré-Condicao: Os nós devem estar inicializados e conectados.
    // Pós-Condicao: O nó adjacente pode ter sua distância e caminho mais curto atualizados.
    private static void evaluateDistanceAndPath(Node adjacentNode, Integer edgeWeight, Node sourceNode) {
        Integer newDistance = sourceNode.getDistance() + edgeWeight;
        if (newDistance < adjacentNode.getDistance()) {
            adjacentNode.setDistance(newDistance);
            adjacentNode.setShortestPath(
                    Stream.concat(sourceNode.getShortestPath().stream(), Stream.of(sourceNode)).collect(Collectors.toList())
            );
        }
    }

    // Função: lerArquivo
    // Descrição: Lê um arquivo de texto contendo a definição do grafo, inicial, final, orientação e heurísticas.
    // Entrada: String filename (nome do arquivo).
    // Saída: Map<String, Object> contendo os nós inicial, final e heurísticas.
    // Pré-Condicao: O arquivo deve estar no formato esperado.
    // Pós-Condicao: Retorna um mapa com os dados do grafo prontos para uso.
    public static Map<String, Object> lerArquivo(String filename) throws FileNotFoundException {
        Scanner scanner = new Scanner(new File(filename));
        Map<String, Node> nodes = new HashMap<>();
        Map<String, Integer> heuristics = new HashMap<>();
        String initial = null;
        String target = null;
        boolean directed = true; // Default s (orientado)

        while (scanner.hasNextLine()) {
            String line = scanner.nextLine().trim();
            if (line.isEmpty() || line.startsWith("%")) continue; // Ignora comentários e vazios

            if (line.startsWith("ponto_inicial(")) {
                initial = line.substring(14, line.indexOf(")"));
            } else if (line.startsWith("ponto_final(")) {
                target = line.substring(12, line.indexOf(")"));
            } else if (line.startsWith("orientado(")) {
                directed = line.charAt(10) == 's';
            } else if (line.startsWith("pode_ir(")) {
                String[] parts = line.substring(8, line.indexOf(")")).split(",");
                String from = parts[0].trim();
                String to = parts[1].trim();
                int cost = Integer.parseInt(parts[2].trim());

                nodes.putIfAbsent(from, new Node(from));
                nodes.putIfAbsent(to, new Node(to));

                nodes.get(from).addAdjacentNode(nodes.get(to), cost);
                if (!directed) {
                    nodes.get(to).addAdjacentNode(nodes.get(from), cost);
                }
            } else if (line.startsWith("h(")) {
                String[] parts = line.substring(2, line.indexOf(")")).split(",");
                String node = parts[0].trim();
                // parts[1] é o final, ignorado pois é sempre f0 no exemplo
                int hValue = Integer.parseInt(parts[2].trim());
                heuristics.put(node, hValue);
            }
        }
        scanner.close();

        // Adicione o nó final se não estiver presente (ex.: f0 pode não ter 'pode_ir')
        nodes.putIfAbsent(target, new Node(target));

        Map<String, Object> result = new HashMap<>();
        result.put("initial", nodes.get(initial));
        result.put("target", nodes.get(target));
        result.put("heuristics", heuristics);
        return result;
    }

    // Função: main
    // Descrição: Ponto de entrada do programa, faz a leitura do arquivo e executa o algoritmo de Dijkstra.
    // Entrada: Nenhum.
    // Saída: Nenhuma (imprime resultados no console).
    // Pré-Condicao: O arquivo de entrada deve existir e estar no formato correto.
    // Pós-Condicao: O resultado do algoritmo é exibido no console.
    public static void main() {
        try {
            // Leitura do arquivo
            Map<String, Object> graphData = lerArquivo("arquivoEntrada.txt");
            Node source = (Node) graphData.get("initial");
            Node target = (Node) graphData.get("target");
            Map<String, Integer> heuristics = (Map<String, Integer>) graphData.get("heuristics");

            calculateShortestPath(source, target, heuristics);

        } catch (FileNotFoundException e) {
            System.out.println("Arquivo não encontrado: " + e.getMessage());
        }
    }
}
