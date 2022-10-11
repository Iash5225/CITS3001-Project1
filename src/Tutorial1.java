import org.graphstream.graph.*;
import org.graphstream.graph.implementations.*;

public class Tutorial1 {

    public static void main(String args[]) {
        Tutor();
    }
    public static void Tutor() {
        System.setProperty("org.graphstream.ui", "swing");
   
       // Graph graph = new SingleGraph("Tutorial 1");

        Graph graph = new MultiGraph("Tutorial 1", false, true,100,100);
        graph.setAttribute("ui.stylesheet", styleSheet);
        Node A = graph.addNode("A");
        A.setAttribute("ui.label", "A");
        Node B = graph.addNode("B");
        B.setAttribute("ui.label", "B");
        Node C = graph.addNode("C");
        C.setAttribute("ui.label", "C");
        graph.addEdge("AB", "A", "B");
        graph.addEdge("BC", "B", "C");
        graph.addEdge("CA", "C", "A");

        graph.display();
        for (Node node : graph) {
            node.setAttribute("ui.label", node.getId());
        }
    }

    protected static String styleSheet = "node {" +
            "	fill-color: black;text-size: 50; " +
            "}" +
            "node.marked {" +
            "	fill-color: red;text-size: 50;" +
            "}";
}