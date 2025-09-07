package Dijkstra_Bonus;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Dijkstra {

    // Função: calculateShortestPath
    // Descrição: Calcula o caminho mais curto entre o nó inicial (source) e o nó final (target) usando o algoritmo de Dijkstra com limite de fio.
    // Entrada: Node source (nó inicial), Node target (nó final), Map heuristics (mapa de heurísticas, não usado no Dijkstra), int limiteFio (comprimento máximo do fio).
    // Saída: Nenhuma (imprime o resultado no console).
    // Pré-Condicao: O grafo deve estar corretamente inicializado, com nós e arestas.
    // Pós-Condicao: O caminho mais curto e a distância são exibidos no console, considerando o limite de fio.
    public static void calculateShortestPath(Node source, Node target, Map heuristics, int limiteFio) {
        source.setDistance(0);
        Set settledNodes = new HashSet<>();
        Queue unsettledNodes = new PriorityQueue<>(Collections.singleton(source));
        int iteration = 1;
        int nodesExpanded = 0; // Medida de desempenho: nós expandidos acumulados

        System.out.println("Início da execução");

        while (!unsettledNodes.isEmpty()) {
            Node currentNode = (Node) unsettledNodes.poll();

            int fioRestante = limiteFio - currentNode.getDistance();
            boolean descartarCaminho = fioRestante <= 0;

            System.out.println("\nIteração " + iteration + ":");
            System.out.print("Fila: ");
            unsettledNodes.add(currentNode); // Temporariamente adicionar de volta para printar a fila completa
            for (Node node : (Queue<Node>) unsettledNodes) {
                int h = (Integer) heuristics.getOrDefault(node.getName(), 0);
                System.out.print("(" + node.getName() + ": " + node.getDistance() + " + " + h + " = " + (node.getDistance() + h) + ") ");
            }
            System.out.println();
            unsettledNodes.remove(currentNode); // Remover após print

            System.out.print("Fio restante: " + fioRestante);
            if (descartarCaminho) {
                System.out.println(" – Caminho descartado");
                System.out.println("Medida de desempenho (nós expandidos): " + nodesExpanded);
                iteration++;
                continue;
            } else {
                System.out.println();
            }

            nodesExpanded++; // Incrementa medida após expandir

            // Se alcançou o target, pode parar
            if (currentNode == target) {
                System.out.println("Medida de desempenho (nós expandidos): " + nodesExpanded);
                break;
            }

            currentNode.getAdjacentNodes()
                    .entrySet().stream().filter(entry -> !settledNodes.contains(entry.getKey()))
                    .forEach(entry -> {
                        int newDistance = currentNode.getDistance() + (Integer) entry.getValue();
                        // Só atualizar se newDistance <= limiteFio
                        if (newDistance <= limiteFio && newDistance < ((Node) entry.getKey()).getDistance()) {
                            ((Node) entry.getKey()).setDistance(newDistance);
                            ((Node) entry.getKey()).setShortestPath(
                                    Stream.concat(currentNode.getShortestPath().stream(), Stream.of(currentNode)).collect(Collectors.toList())
                            );
                            unsettledNodes.add((Node) entry.getKey());
                        }
                    });
            settledNodes.add(currentNode);

            System.out.println("Medida de desempenho (nós expandidos): " + nodesExpanded);
            iteration++;
        }

        System.out.println("\nFim da execução");
        // Resumo
        System.out.println("Distância: " + target.getDistance());
        String path = target.getShortestPath().stream().map(Node::getName).collect(Collectors.joining(" – "));
        System.out.println("Caminho: " + path + " – " + target.getName());
        System.out.println("Medida de desempenho (nós expandidos): " + nodesExpanded);
    }

    // Função: lerArquivo
    // Descrição: Lê um arquivo de texto contendo a definição do grafo, inicial, final, orientação e heurísticas.
    // Entrada: String filename (nome do arquivo).
    // Saída: Map contendo os nós inicial, final e heurísticas.
    // Pré-Condicao: O arquivo deve estar no formato esperado.
    // Pós-Condicao: Retorna um mapa com os dados do grafo prontos para uso.
    public static Map lerArquivo(String filename) throws FileNotFoundException {
        Scanner scanner = new Scanner(new File(filename));
        Map nodes = new HashMap<>();
        Map heuristics = new HashMap<>();
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

                ((Node) nodes.get(from)).addAdjacentNode((Node) nodes.get(to), cost);
                if (!directed) {
                    ((Node) nodes.get(to)).addAdjacentNode((Node) nodes.get(from), cost);
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

        Map result = new HashMap<>();
        result.put("initial", nodes.get(initial));
        result.put("target", nodes.get(target));
        result.put("heuristics", heuristics);
        return result;
    }

    // Função: main
    // Descrição: Ponto de entrada do programa, faz a leitura do arquivo, pergunta pelo comprimento do fio e executa o algoritmo de Dijkstra com limite.
    // Entrada: Nenhum.
    // Saída: Nenhuma (imprime resultados no console).
    // Pré-Condicao: O arquivo de entrada deve existir e estar no formato correto.
    // Pós-Condicao: O resultado do algoritmo é exibido no console.
    public static void main(String[] args) {
        try {
            // Leitura do arquivo
            Map graphData = lerArquivo("arquivoEntrada.txt");
            Node source = (Node) graphData.get("initial");
            Node target = (Node) graphData.get("target");
            Map heuristics = (Map) graphData.get("heuristics");

            try (Scanner input = new Scanner(System.in)) {
                // Pergunta pelo comprimento do fio
                System.out.println("Qual o comprimento do fio?");
                int limiteFio = input.nextInt();

                calculateShortestPath(source, target, heuristics, limiteFio);
            }
            
        } catch (FileNotFoundException e) {
            System.out.println("Arquivo não encontrado: " + e.getMessage());
        }
    }
}
