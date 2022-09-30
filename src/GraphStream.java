import org.graphstream.graph.*;
import org.graphstream.graph.implementations.*;

public class GraphStream {

    public static void main(String[] args) {
        System.setProperty("org.graphstream.ui", "swing");

        Graph graph = new SingleGraph("Tutorial 1");

        for (int i = 0; i < 100; i++) {
            graph.addNode(Integer.toString(i));
        }

        // Add some edges
        for (int i = 0; i < 100; i++) {
            for (int j = i + 1; j < 100; j++) {
                if (Math.random() < 0.05) {
                    graph.addEdge(Integer.toString(i) + "-" + Integer.toString(j), Integer.toString(i),
                            Integer.toString(j));
                }
            }
        }

        graph.display();

    }
}
