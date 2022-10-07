import org.graphstream.graph.*;
import org.graphstream.graph.implementations.*;

public class Visualiser {
    public static int Initial_Node_Capacity = 5;
    public static int Initial_Edge_Capacity = 100;

    public static void main(String args[]) {
        Tutor();
    }

    public static void Tutor() {
        System.setProperty("org.graphstream.ui", "swing");
        Graph graph = new MultiGraph("Game", false, true, Initial_Node_Capacity, Initial_Edge_Capacity);
        graph.setAttribute("ui.stylesheet", styleSheet);

        for (int i = 0; i < Initial_Node_Capacity; i++) {
            Node node = graph.addNode(Integer.toString(i));
            node.setAttribute("ui.label", node.getId());
        }
        graph.addEdge("AB", "A", "B");
        graph.addEdge("BC", "B", "C");
        graph.addEdge("CA", "C", "A");

        graph.display();
        for (Node node : graph) {
            node.setAttribute("ui.label", node.getId());
        }
    }

    protected static String styleSheet = 
            "node {" +
            "fill-color: black;" + 
            "text-size: 50;" +
            "}";
}