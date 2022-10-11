import org.graphstream.graph.*;
import org.graphstream.graph.implementations.*;
import java.util.*;

public class Visualiser {
    public static int Initial_Node_Capacity = 10;
    public static int Initial_Edge_Capacity = 100;
    public Graph graph;
    public Game game;

    public void main(String args[]) {
       // Game game = new Game(Initial_Node_Capacity, 0.3, 3, 0.33, 0, 0.5, 0.5);
        setup();
    }

    public void setup() {
        // Visualising intial set up
        System.setProperty("org.graphstream.ui", "swing");
        graph = new MultiGraph("Game", false, true, game.greens.size(), Initial_Edge_Capacity);
        graph.setAttribute("ui.quality");
        graph.setAttribute("ui.antialias");
        graph.setAttribute("ui.stylesheet", styleSheet);
        game.greens.forEach((green) -> {
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

        game.greens.forEach((green) -> {
            green.friends.forEach((friend) -> {
                if (!green_interracArrayList.contains(String.valueOf(green.id) + String.valueOf(friend.id))) {
                    graph.addEdge(String.valueOf(green.id) + String.valueOf(friend.id), String.valueOf(green.id),
                            String.valueOf(friend.id));
                    StringBuilder EDGE_ID = new StringBuilder();
                    EDGE_ID.append(String.valueOf(friend.id)+ String.valueOf(green.id));
                    green_interracArrayList.add(EDGE_ID.toString());
                }
            });
        });
        update_visualiser();
    }

    public void update_visualiser(){
        game.greens.forEach((green) -> {
            Node node = graph.getNode(String.valueOf(green.id));
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
    }

    public void add_Grey_Nodes() {
        Grey grey = game.greys.get(0);
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

    protected String styleSheet = "node {fill-color: green;text-size: 30;text-alignment: justify;z-index:0;size:20px;}"+
            "node.greenwillVote {fill-color: green;stroke-width:5;stroke-color:blue;stroke-mode:plain;}" +
            "node.greenwillNOTVote {fill-color: green;}"+
            "node.redwillVote {fill-color: red;stroke-width:5;stroke-color:blue;stroke-mode:plain;}"+
            "node.redwillNOTVote {fill-color: red;}"+
            "node.grey {fill-color: grey;}" +
            "node.marked {fill-color: purple;arrow-size: 3px, 2px;}"
            ;
}