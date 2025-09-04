package DFS_Pior;

import java.util.Arrays;
import java.util.Collections;
import java.util.Deque;
import java.util.LinkedList;

public class DFS {
    
    public void traverse(Vertex startVertex) {
        Deque<Vertex> stack = new LinkedList();

        stack.push(startVertex);

        while (!stack.isEmpty()) {
            Vertex current = stack.pop();

            if (!current.isVisited()) {
                current.setVisited(true);
                System.out.println("Node(data=" + current.getData() + ", visited=" + current.isVisited() + ")");

                Collections.reverse(current.getNeighbors());

                current.getNeighbors().forEach(stack::push);
            }

        }
    }

    public static void main(String[] args) {
        Vertex v0 = new Vertex(0);
        Vertex v1 = new Vertex(1);
        Vertex v2 = new Vertex(2);
        Vertex v3 = new Vertex(3);
        Vertex v4 = new Vertex(4);
        Vertex v5 = new Vertex(5);
        Vertex v6 = new Vertex(6);

        v0.setNeighbors(Arrays.asList(v1, v5, v6));
        v1.setNeighbors(Arrays.asList(v3, v4, v5));
        v4.setNeighbors(Arrays.asList(v2, v6));
        v6.setNeighbors(Arrays.asList(v0));

        DFS codigo = new DFS();
        codigo.traverse(v0);
        
    }
}
