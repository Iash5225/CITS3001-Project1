import org.graphstream.graph.*;
import org.graphstream.graph.implementations.*;
import java.util.*;

public class Visualiser {
    public static int initial_node_capacity = 10;
    public static int initial_edge_capacity = 100;
    public Graph graph;
    public Game game;

    public void main(String args[]) {
        setup();
    }

    public void setup() {
        // Visualising intial set up
        System.setProperty("org.graphstream.ui", "swing");
        graph = new MultiGraph("Game", false, true, game.board.greens.size(), initial_edge_capacity);
        graph.setAttribute("ui.quality");
        graph.setAttribute("ui.antialias");
        graph.setAttribute("ui.stylesheet", styleSheet);
        game.board.greens.forEach((green) -> {
            Node node = graph.addNode(String.valueOf(green.id));
            node.setAttribute("ui.label", String.valueOf(green.id) + ":" + String.format("%.2f", green.uncertainty));

            if (green.followsRed) {
                if (green.willVote) {
                    node.setAttribute("ui.class", "redwillVote");
                } else {
                    node.setAttribute("ui.class", "redwillNOTVote");
                }
            } else {
                if (green.willVote) {
                    node.setAttribute("ui.class", "greenwillVote");
                } else {
                    node.setAttribute("ui.class", "greenwillNOTVote");
                }
            }
        });

        // Game information
        ArrayList<String> green_interracArrayList = new ArrayList<String>();

        game.board.greens.forEach((green) -> {
            green.friends.forEach((friend) -> {
                if (!green_interracArrayList.contains(String.valueOf(green.id) + String.valueOf(friend.id))) {
                    graph.addEdge(String.valueOf(green.id) + String.valueOf(friend.id), String.valueOf(green.id),
                            String.valueOf(friend.id));
                    StringBuilder EDGE_ID = new StringBuilder();
                    EDGE_ID.append(String.valueOf(friend.id) + String.valueOf(green.id));
                    green_interracArrayList.add(EDGE_ID.toString());
                }
            });
        });
        update_visualiser();
    }

    public void update_visualiser() {
        game.board.greens.forEach((green) -> {
            Node node = graph.getNode(String.valueOf(green.id));
            // node.setAttribute("ui.label", String.valueOf(green.id) + ":" +
            // String.format("%.2f", green.uncertainty));
            node.setAttribute("ui.label", String.valueOf(green.id));
            if (green.followsRed) {
                if (green.willVote) {
                    node.setAttribute("ui.class", "redwillVote");
                } else {
                    node.setAttribute("ui.class", "redwillNOTVote");
                }
            } else {
                if (green.willVote) {
                    node.setAttribute("ui.class", "greenwillVote");
                } else {
                    node.setAttribute("ui.class", "greenwillNOTVote");
                }
            }

        });
    }

    public void add_Grey_Nodes() {
        Grey grey = game.board.greys.get(0);
        Node node = graph.addNode(String.valueOf(grey.id));
        node.setAttribute("ui.label", String.valueOf(grey.id) + ":" + grey.isSpy);
        node.setAttribute("ui.class", "grey");
        explore(graph.getNode("0"));
        graph.removeNode(node);
    }

    public void explore(Node source) {
        Iterator<? extends Node> k = source.getBreadthFirstIterator();
        while (k.hasNext()) {
            Node next = k.next();
            String initial_class = String.valueOf(next.getLabel("ui.class"));
            next.setAttribute("ui.class", "marked");
            sleep();
            next.setAttribute("ui.class", initial_class);
        }
    }

    protected void sleep() {
        try {
            Thread.sleep(600);
        } catch (Exception e) {
        }
    }

    protected String styleSheet = "node {fill-color: green;text-size: 20;text-alignment: center;z-index:2;size:20px;}" +
            "node.greenwillVote {fill-color: rgb(186,255,201);fill-mode:plain;shadow-color:rgb(61,66,107);shadow-width:5px;shadow-offset:0;shadow-mode:plain;}"
            +
            "node.greenwillNOTVote {fill-color: rgb(186,255,201);fill-mode:plain;}" +
            "node.redwillVote {fill-color: rgb(255,105,97);fill-mode:plain;shadow-color:rgb(61,66,107);shadow-width:5px;shadow-offset:0;shadow-mode:plain;}"
            +
            "node.redwillNOTVote {fill-color: rgb(255,105,97);fill-mode:plain;}" +
            "node.grey {fill-color: grey;}" +
            "node.marked {fill-color: purple;arrow-size: 3px, 2px;}";
}