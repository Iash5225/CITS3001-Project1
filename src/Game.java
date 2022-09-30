import java.util.*;

public class Game {
    public Vector<Green> greens;
    public Red red;
    public Vector<Grey> greys;
    public Blue blue;

    public int n_rounds = 10;
    public int current_round;
    public boolean is_blues_turn;

    /**
     * Creates a new game with the given parameters.
     * 
     * @param n_green
     * @param prob_edge
     * @param n_grey
     * @param prob_spy
     * @param uncertainty_lb
     * @param uncertainty_ub
     * @param percentage_vote
     * 
     * @return Game object
     */
    public Game(int n_green, double prob_edge, int n_grey, double prob_spy, double uncertainty_lb,
            double uncertainty_ub, double percentage_vote) {

        // create n_green greens with parameters
        greens = new Vector<Green>();
        for (int i = 0; i < n_green; i++) {
            double uncertainty = uncertainty_lb + Math.random() * (uncertainty_ub - uncertainty_lb);
            boolean willVote = Math.random() < percentage_vote;
            greens.add(new Green(uncertainty, willVote, true));
        }

        // join greens with probability prob_edge
        for (Green g : greens) {
            for (Green g2 : greens) {
                if (g != g2 && Math.random() < prob_edge) {
                    g.friends.add(g2);
                }
            }
        }

        // create n_grey greys with parameters
        greys = new Vector<Grey>();
        for (int i = 0; i < n_grey; i++) {
            boolean isSpy = Math.random() < prob_spy;
            greys.add(new Grey(isSpy));
        }

        // create red and blue
        red = new Red();
        blue = new Blue(100);

        // set current round to 0
        current_round = 0;
        is_blues_turn = false;

        // set number of rounds to 10
        // n_rounds = 10;
    }

    /**
     * Prints the greens in the game.
     */
    public void printGreens() {
        // set color to green
        System.out.print("\033[0;32m");
        System.out.println("Greens:");
        // reset color
        System.out.print("\033[0m");
        for (Green g : greens) {
            g.print();
        }

    }

    /**
     * Next round of the game.
     */
    public void nextRound() {
        current_round++;
        System.out.println("=========================================");
        System.out.println("Round " + current_round);
        while (!is_blues_turn) {
            // red's turn
            System.out.println("===================================");
            // change print color to red
            System.out.print("\033[0;31m");

            System.out.println("Red's turn");

            // reset the print color
            System.out.print("\033[0m");

            System.out.println("Red's options:");
            System.out.println("(1) Send a message");
            System.out.println("(2) Do nothing");
            System.out.print("Enter your choice: ");
            try (Scanner sc = new Scanner(System.in)) {
                int choice = sc.nextInt();
                switch (choice) {
                    case 1:
                        System.out.print("Enter the message level from 1-5: ");

                        int level = sc.nextInt();
                        System.out.println("TODO: send message from red");
                        sendRedMessage(level);
                        is_blues_turn = true;
                        break;
                    case 2:
                        System.out.println("Red does nothing");
                        is_blues_turn = true;
                        break;
                    default:
                        System.out.println("Invalid choice, try again");
                        break;
                }
            }
        }

        while (is_blues_turn) {
            // blue's turn
            System.out.println("===================================");
            // change print color to blue
            System.out.print("\033[0;34m");
            System.out.println("Blue's turn");
            // reset the print color
            System.out.print("\033[0m");
            System.out.println("Blue's energy: " + blue.getEnergy());
            System.out.println("Blue's options:");
            System.out.println("(1) Send a message");
            System.out.println("(2) Add a grey");
            System.out.println("(3) Do nothing");
            System.out.print("Enter your choice: ");
            try (Scanner sc = new Scanner(System.in)) {
                int choice = sc.nextInt();
                switch (choice) {
                    case 1:
                        System.out.print("Enter the message level from 1-5: ");
                        int level = sc.nextInt();
                        System.out.println("TODO: send message from blue");
                        is_blues_turn = false;
                        break;
                    case 2:
                        System.out.println("TODO: add a grey");
                        is_blues_turn = false;
                        break;
                    case 3:
                        System.out.println("Blue does nothing");
                        is_blues_turn = false;
                        break;
                    default:
                        System.out.println("Invalid choice, try again");
                        break;
                }
            }
        }
    }

    private void sendRedMessage(int level) {
        for (Green g : greens) {
            // skip if green doesn't follow red
            if (!g.followsRed) {
                continue;
            }

            if (g.willVote) {
                //check if the addition of the uncertainty is greater than 1
                if (g.uncertainty + 0.1 * level < 1)
                    g.uncertainty = g.uncertainty + 0.1 * level;
            }
        }
    }

    private void sendBlueMessage(int level) {
        // TODO Auto-generated method stub

    }
}
