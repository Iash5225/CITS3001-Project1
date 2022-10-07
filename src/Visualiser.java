import org.graphstream.graph.*;
import org.graphstream.graph.implementations.*;
import java.util.*;

public class Visualiser {
    public static int Initial_Node_Capacity = 10;
    public static int Initial_Edge_Capacity = 100;

    public static void main(String args[]) {
        Visualise(new Game(Initial_Node_Capacity, 0.3, 3, 0.33, 0, 0.5, 0.5));
    }

    public static void Visualise(Game game) {
        // Visualising intial set up
        System.setProperty("org.graphstream.ui", "swing");
        Graph graph = new MultiGraph("Game", false, true, Initial_Node_Capacity, Initial_Edge_Capacity);
        graph.setAttribute("ui.stylesheet", styleSheet);

        // Adding nodes
        game.greens.forEach((green) -> {
            Node node = graph.addNode(String.valueOf(green.id));
            node.setAttribute("ui.label", String.valueOf(green.id) + ":" + String.format("%.2f", green.uncertainty));
            if (green.followsRed) {
                node.setAttribute("ui.class", "red");
            } else {
                node.setAttribute("ui.class", "green");
            }

        });
        game.printGreens();

        // Game information
        ArrayList<String> green_interracArrayList = new ArrayList<String>();

        game.greens.forEach((green) -> {
            green.friends.forEach((friend) -> {
                if (!green_interracArrayList.contains(String.valueOf(green.id) + String.valueOf(friend.id))) {
                    graph.addEdge(String.valueOf(green.id) + String.valueOf(friend.id), String.valueOf(green.id),
                            String.valueOf(friend.id));
                    StringBuilder EDGE_ID = new StringBuilder();
                    EDGE_ID.append(String.valueOf(green.id) + String.valueOf(friend.id));
                    EDGE_ID.reverse();
                    green_interracArrayList.add(EDGE_ID.toString());
                }
            });
        });
        add_Grey_Nodes(graph, game);
        graph.display();

    }

    public static void add_Grey_Nodes(Graph graph, Game game) {
        Grey grey = game.greys.get(0);
        Node node = graph.addNode(String.valueOf(grey.id));
        node.setAttribute("ui.label", String.valueOf(grey.id) + ":" + grey.isSpy);
        node.setAttribute("ui.class", "grey");

    }

    protected static String styleSheet = "node {" +
            "fill-color: green;" +
            "text-size: 50;" +
            "text-alignment: justify;" +
            "}" +
            "node.red {" +
            "	fill-color: red;text-size: 50;" +
            "}" +
            "node.grey {" +
            "	fill-color: grey;text-size: 50;" +
            "}";
}