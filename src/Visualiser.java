import org.graphstream.graph.*;
import org.graphstream.graph.implementations.*;
import java.util.*;

public class Visualiser {
    public static int initial_node_capacity = 10;
    public static int initial_edge_capacity = 100;
    public Graph graph;
    public Game game;

    // public void main(String args[]) {
    // setup();
    // }

    /**
     * Sets up the visualiser.
     */
    public void setup() {
        // Visualising intial set up
        System.setProperty("org.graphstream.ui", "swing");
        graph = new MultiGraph("Game", false, true, game.board.greens.size(), initial_edge_capacity);
        graph.setAttribute("ui.quality");
        graph.setAttribute("ui.antialias");
        graph.setAttribute("ui.stylesheet", styleSheet);
        game.board.greens.forEach((green) -> {
            Node node = graph.addNode(String.valueOf(green.id));
            // node.setAttribute("ui.label", String.valueOf(green.id) + ":" +
            // String.format("%.2f", green.uncertainty));

            if (green.followsRed) {
                if (green.willVote) {
                    node.setAttribute("ui.class", "followsredwillvote");
                } else {
                    node.setAttribute("ui.class", "followsredwillnotvote");
                }
            } else {
                if (green.willVote) {
                    node.setAttribute("ui.class", "followsbluewillvote");
                } else {
                    node.setAttribute("ui.class", "followsbluewillnotvote");
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

    /**
     * Updates the visualiser, by updating the green agent properties
     */
    public void update_visualiser() {
        game.board.greens.forEach((green) -> {
            Node node = graph.getNode(String.valueOf(green.id));
            // node.setAttribute("ui.label", String.valueOf(green.id) + ":" +
            // String.format("%.2f", green.uncertainty));
            // node.setAttribute("ui.label", String.valueOf(green.id));
            if (green.followsRed) {
                if (green.willVote) {
                    node.setAttribute("ui.class", "followsredwillvote");
                } else {
                    node.setAttribute("ui.class", "followsredwillnotvote");
                }
            } else {
                if (green.willVote) {
                    node.setAttribute("ui.class", "followsbluewillvote");
                } else {
                    node.setAttribute("ui.class", "followsbluewillnotvote");
                }
            }

        });
    }

    /*
     * System sleep call, for a buffered visualisation
     */
    protected void sleep() {
        try {
            Thread.sleep(600);
        } catch (Exception e) {
        }
    }

    /**
     * The style sheet for the visualiser
     */
    protected String styleSheet = "node {fill-color: green;text-size: 20;text-alignment: center;z-index:2;size:20px;}" +
            "node.followsbluewillvote {fill-color: rgb(96,190,230);fill-mode:plain;shadow-color:rgb(61,66,107);shadow-width:5px;shadow-offset:0;shadow-mode:plain;}"
            +
            "node.followsredwillvote {fill-color: rgb(96,190,230);fill-mode:plain;}" +
            "node.followsbluewillnotvote {fill-color: rgb(255,105,97);fill-mode:plain;shadow-color:rgb(61,66,107);shadow-width:5px;shadow-offset:0;shadow-mode:plain;}"
            +
            "node.followsredwillnotvote {fill-color: rgb(255,105,97);fill-mode:plain;}" +
            "node.grey {fill-color: grey;}" +
            "node.marked {fill-color: purple;arrow-size: 3px, 2px;}";

    /**
     * Clearing the graph for the next visualisation
     */
    public void exit() {
        graph.clear();
        graph.display(false);
    }
}