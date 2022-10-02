import java.util.*;

public class Game {
    public Vector<Green> greens;
    public Red red;
    public Vector<Grey> greys;
    public Blue blue;

    public int n_rounds = 10;
    public double Min_Uncertainty = 0.75;
    public int current_round;
    public boolean is_blues_turn;
    public int n_greys_released=-1;
    public int number_of_greys = 0;

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
        number_of_greys = n_grey;

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
            Scanner sc = new Scanner(System.in);
            int choice = sc.nextInt();
            switch (choice) {
                case 1:
                    System.out.print("Enter the message level from 1-5: ");

                    int level = sc.nextInt();
                    System.out.print("\033[0;35m");

                    switch (level) {
                        case 1:
                            System.out.println("Blue sucks #1");
                            break;
                        case 2:
                            System.out.println("Blue sucks #2");
                            break;
                        case 3:
                            System.out.println("Blue sucks #3");
                            break;
                        case 4:
                            System.out.println("Blue sucks #4");
                            break;
                        case 5:
                            System.out.println("Blue sucks #5");
                            break;
                        default:
                            break;
                    }
                    // System.out.println("TODO: send message from red");
                    System.out.print("\033[0m");
                    sendRedMessage(level);
                    is_blues_turn = true;
                    break;
                case 2:
                    System.out.print("\033[0;35m");
                    System.out.println("Red does nothing");
                    System.out.print("\033[0m");
                    is_blues_turn = true;
                    break;
                default:
                    System.out.print("\033[0;35m");
                    System.out.println("Invalid choice, try again");
                    System.out.print("\033[0m");
                    break;
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
            Scanner sc = new Scanner(System.in);
            int choice = sc.nextInt();
            switch (choice) {
                case 1:
                    System.out.print("Enter the message level from 1-5: ");
                    int level = sc.nextInt();
                    System.out.print("\033[0;35m");
                    switch (level) {
                        case 1:
                            System.out.println("Red sucks #1");
                            break;
                        case 2:
                            System.out.println("Red sucks #2");
                            break;
                        case 3:
                            System.out.println("Red sucks #3");
                            break;
                        case 4:
                            System.out.println("Red sucks #4");
                            break;
                        case 5:
                            System.out.println("Red sucks #5");
                            break;
                        default:
                            break;
                    }
                    // System.out.println("TODO: send message from blue");
                    System.out.print("\033[0m");
                    sendBlueMessage(level);
                    is_blues_turn = false;
                    break;
                case 2:
                    System.out.println("TODO: add a grey");
                    System.out.print("\033[0;35m");
                    release_greys();
                    System.out.print("\033[0m");
                    is_blues_turn = false;
                    break;
                case 3:
                    System.out.print("\033[0;35m");
                    System.out.println("Blue does nothing");
                    System.out.print("\033[0m");
                    is_blues_turn = false;
                    break;
                default:
                    // change print color to purple
                    System.out.print("\033[0;35m");
                    System.out.println("Invalid choice, try again");
                    System.out.print("\033[0m");
                    break;
            }

        }
    }


    private void release_greys() {
        // TODO Auto-generated method stub
        n_greys_released++;
        if (n_greys_released <= number_of_greys) {
            Grey grey_agent = greys.get(n_greys_released);
            if (grey_agent.isSpy) {
                System.out.println("A spy has been released");
            } else {
                System.out.println("A non-spy has been released");
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
                // check if the addition of the uncertainty is greater than 1
                double newUncertainty = Math.round((g.uncertainty + 0.1 * level) * 100.0) / 100.0;
                if (newUncertainty <= 1) {
                    g.uncertainty = newUncertainty;
                }
            }
        }
    }

    private void sendBlueMessage(int level) {
        // TODO Auto-generated method stub
        for (Green g : greens) {
            // if green doesn't follow red, make sure they
            if (!g.followsRed) {
                continue;
            }

            if (g.willVote) {
                // check greens uncertainty

                // Check if the uncertainty is greater than the "high uncertainty" threshold
                // double EnergyLoss = Math.round((0.1 * level) * 100.0) / 100.0;


                double newUncertainty = Math.round((g.uncertainty + 0.1 * level) * 100.0) / 100.0;
                if (newUncertainty <= 1) {
                    g.uncertainty = newUncertainty;
                }

                if (Math.abs(g.uncertainty) > Min_Uncertainty) {
                    blue.LoseEnergy(10);
                }
            }

        }
    }


    public int number_of_voting_greens() {
        int count = 0;
        for (Green g : greens) {
            if (g.willVote) {
                count++;
            }
        }
        return count;
    }
}
