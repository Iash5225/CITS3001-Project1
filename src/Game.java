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
    public double energy_cost = 20;
    public int n_greys_released = -1;
    public int number_of_greys = 0;
    public double chance_of_switching = 0.2;

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
        System.out.print("\033[4;37m");
        System.out.println("Round " + current_round);
        System.out.print("\033[0m");
        change_following();
        change_votes();
        game_status();
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
                    sendBlueMessage(level, false);
                    is_blues_turn = false;
                    break;
                case 2:
                    // System.out.println("TODO: add a grey");
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

    public void game_status() {
        int number_of_red_followers = 0;
        int number_of_blue_followers = 0;
        int number_of_red_votes = 0;
        int number_of_blue_votes = 0;
        for (Green g : greens) {
            if (g.followsRed) {
                number_of_red_followers++;
            } else {
                number_of_blue_followers++;
            }
            if (g.willVote) {
                if (g.followsRed) {
                    number_of_red_votes++;
                } else {
                    number_of_blue_votes++;
                }
            }
        }
        System.out.println("=========================================");
        System.out.println("Game status:");

        if (current_round == n_rounds) {
            if (number_of_red_votes > number_of_blue_votes) {
                System.out.print("\033[41m");
                System.out.println("Red wins!");
                System.out.print("\033[0m");
            } else if (number_of_red_votes < number_of_blue_votes) {
                System.out.print("\033[44m");
                System.out.println("Blue wins!");
                System.out.print("\033[0m");
            } else {
                System.out.println("Tie!");
            }
        }

        if (blue.LostALLEnergy()) {
            System.out.print("\033[47m");
            System.out.println("Blue lost all energy");
            System.out.println("Red wins");
            System.out.print("\033[0m");
            System.exit(0);
        } else if (number_of_red_followers == 0) {
            System.out.print("\033[47m");
            System.out.println("Red lost all followers");
            System.out.println("Blue wins");
            System.out.print("\033[0m");
            System.exit(0);
        }

        // add green interracting here
        // TODO add green interractng here, and the rest of the game status
        // calculate new uncertainty ,and deal with the votes

        System.out.println("Number of red followers: " + number_of_red_followers);
        System.out.println("Number of blue followers: " + number_of_blue_followers);

    }

    /**
     * Changes the votes of the greens.
     */

    private void change_votes() {
        int count = 0;
        if (current_round != 1) {
            for (Green g : greens) {
                // if green has an uncertainty above 0.75 or below -0.75, and they will switch
                // their votes
                if (Math.abs(g.uncertainty) > Min_Uncertainty) {
                    g.willVote = !g.willVote;
                    count++;
                }
            }
        }
        System.out.println("Number of agents who have changed their votes: " + count);
    }

    private void change_following() {
        int count = 0;
        if (current_round != 1) {
            for (Green g : greens) {
                // if green has an uncertainty above 0.75 or below -0.75, and they will switch
                // their votes
                boolean Team_red = Math.random() < chance_of_switching;
                if (Team_red) {
                    g.followsRed = !g.followsRed;
                    count++;
                }
            }
        }
        System.out.println("Number of agents who have changed their teams: " + count);
    }

    private void release_greys() {
        // TODO Auto-generated method stub
        Grey grey_agent = greys.get(0);
        if (grey_agent.isSpy) {
            System.out.println("A RED spy has been released");
            // Potent message = 5
            sendRedMessage(5);
            System.out.println("Blue sucks #5");
        } else {
            System.out.println("A non-spy has been released");
            // Potent message = 5
            sendBlueMessage(5, true);
            System.out.println("Red sucks #5");
        }
        greys.remove(0);
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

    private void sendBlueMessage(int level, boolean Grey_turn) {

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

                if (Math.abs(g.uncertainty) > Min_Uncertainty && !Grey_turn) {
                    blue.LoseEnergy(energy_cost);
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
