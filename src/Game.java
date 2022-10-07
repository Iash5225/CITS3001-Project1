import java.util.*;

public class Game {
    public Vector<Green> greens;
    public Red red;
    public Vector<Grey> greys;
    public Blue blue;
    public Scanner sc;

    public int n_rounds = 5;
    public double Min_Uncertainty = 0.75;
    public int current_round;
    public boolean is_blues_turn;
    public double energy_cost = 10;
    public int n_greys_released = -1;
    public int number_of_greys = 0;
    public double chance_of_switching = 0.2;

    /**
     * Creates a new game with the given parameters.
     * 
     * @param n_green         number of green agents
     * @param prob_edge       probability of an edge between 2 green agents
     * @param n_grey          number of grey agents
     * @param prob_spy        probability of a grey agent being a spy
     * @param uncertainty_lb  lower bound of uncertainty
     * @param uncertainty_ub  upper bound of uncertainty
     * @param percentage_vote percentage of green agents that will vote
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

        // set scanner
        sc = new Scanner(System.in);
    }

    // ================================ INFORMATION ================================
    /**
     * Prints the greens in the game.
     * for debugging purposes
     * should not be called by players
     */
    public void printGreens() {
        // set color to green
        System.out.print("\033[0;32m");
        System.out.println("Greens:");
        // reset color
        System.out.print("\033[0m");
        // column names
        System.out.println("id | uncertainty | willVote | followsRed | [friends]");
        System.out.println("----------------------------------------------------");
        for (Green g : greens) {
            g.print();
        }
        System.out.println();

    }

    /**
     * Prints the status of the game.
     */
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
        }
        System.out.println("=========================================");
        System.out.println("Game status:");

        if (current_round > n_rounds) {
            if (number_of_red_votes > number_of_blue_votes) {
                System.out.print("\033[41m");
                System.out.println("Red wins!" + "\033[0m");
                System.out.print("\033[0m");
            } else if (number_of_red_votes < number_of_blue_votes) {
                System.out.print("\033[44m");
                System.out.println("Blue wins!" + "\033[0m");
                System.out.print("\033[0m");
            } else {
                System.out.println("Tie!");
            }
        }

        if (blue.LostALLEnergy()) {

            System.out.print("\033[41m");
            System.out.println("Red wins!" + "\033[0m");
            System.out.println("Blue lost all energy");
            System.exit(0);
        } else if (number_of_red_followers == 0) {
            System.out.print("\033[44m");
            System.out.println("Blue wins!" + "\033[0m");
            System.out.println("Red lost all followers");
            System.exit(0);
        }

        // add green interracting here
        // TODO add green interractng here, and the rest of the game status
        // calculate new uncertainty ,and deal with the votes

        System.out.println("Number of red followers: " + number_of_red_followers);
        System.out.println("Number of blue followers: " + number_of_blue_followers);

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

    private void print_bar(String label, String color, int n) {
        System.out.printf("%-20s:", label);
        System.out.print(color);
        for (int i = 0; i < n; i++) {
            System.out.print(" ");
        }
        System.out.println("\033[0m");
    }

    public void print_game_info_for_players() {
        // print a bar chart of the number of greens that will vote
        int n_voters = number_of_voting_greens();
        int n_non_voters = greens.size() - n_voters;
        System.out.println("=========================================");

        print_bar("Voters", "\033[44m", n_voters);
        print_bar("Non-voters", "\033[41m", n_non_voters);

        System.out.println("=========================================");

        int n_red_followers = 0;
        int n_non_followers = 0;
        for (Green green : greens) {
            if (green.followsRed) {
                n_red_followers++;
            } else {
                n_non_followers++;
            }
        }
        print_bar("Red followers", "\033[41m", n_red_followers);
        print_bar("Non followers", "\033[44m", n_non_followers);

    }

    // ================================ GAME LOGIC ================================
    /**
     * Checks the votes of the greens.
     */
    private void change_votes(double uncertainty) {
        int count = 0;
        if (current_round != 1) {
            for (Green g : greens) {
                // if green has an uncertainty above 0.75, and they will switch
                // their votes
                if (g.uncertainty > uncertainty) {
                    g.willVote = !g.willVote;
                    count++;
                }
            }
        }
        System.out.println("Number of agents who have changed their votes: " +
                count);
    }

    /**
     * Red's turn.
     */
    public void red_turn(PlayerRed p) {
        // set color to red
        System.out.print("\033[0;31m");
        System.out.println("Red's turn");
        // reset color
        System.out.print("\033[0m");

        // print game info for players
        print_game_info_for_players();

        // get red's action
        int action = p.get_next_move(this);
        switch (action) {
            case 1:
                for (Green g : greens) {
                    if (!g.followsRed)
                        continue;
                    influence_green(p.uncertainty, false, g);
                    unfollow(g, p.uncertainty);
                }
                break;
            case 2:
                break;
            default:
                System.out.println("Invalid action");
                System.exit(action);
        }
    }

    /**
     * Checks if a green will unfollow red.
     * 
     * @param g
     * @param uncertainty
     */
    private void unfollow(Green g, double uncertainty) {
        if (g.willVote) {
            if (g.uncertainty < -uncertainty) {
                if (2 * Math.random() < g.uncertainty + 1) {
                    g.willVote = false;
                }
            }
        }
    }

    /**
     * Blue's turn.
     */
    public void blue_turn(PlayerBlue p) {
        // set color to blue
        System.out.print("\033[0;34m");
        System.out.println("Blue's turn");
        // reset color
        System.out.print("\033[0m");

        // print game info for players
        print_game_info_for_players();

        // get blue's action
        int action = p.get_next_move(this);
        switch (action) {
            case 1:
                for (Green g : greens) {
                    influence_green(p.uncertainty, true, g);
                }
                break;
            case 2:
                break;
            case 3:
                release_grey();
                break;
            default:
                System.out.println("Invalid action");
                System.exit(action);
        }
    }

    private void influence_green(Double uncertainty, Boolean willVote, Green g) {
        // if they share the same opinion, g's uncertainty decreases
        if (g.willVote == willVote) {
            g.du -= 0.1;
        }
        // if they don't share the same opinion, then if g's uncertainty is above
        // uncertainty, g's uncertainty increases
        else if (g.uncertainty > uncertainty) {
            g.du += 0.1;
        }
    }

    /**
     * Green talking to eachother on voting day
     */

    private void release_grey() {
        Grey grey_agent = greys.remove(0);
        if (grey_agent.isSpy) {
            System.out.println("A RED spy has been released");
            for (Green g : greens) {
                influence_green(-1.0, false, g);
            }
            System.out.println("Blue sucks #5");
        } else {
            System.out.println("A non-spy has been released");
            for (Green g : greens) {
                influence_green(-1.0, true, g);
            }
            System.out.println("Red sucks #5");
        }
    }

    public void green_voting_day() {
        int[] interracted_greens = new int[greens.size()];
        for (int i = 0; i < greens.size(); i++) {
            interracted_greens[i] = 0;
        }
        for (int i = 0; i < interracted_greens.length; i++) {
            // Green Current = greens.get(i);
            for (int j = 0; j < greens.get(i).friends.size(); j++) {
                // Green Friend = Current.friends.get(j);
                if (interracted_greens[greens.get(i).friends.get(j).id] == 0) {
                    interracted_greens[greens.get(i).friends.get(j).id] = 1;
                    System.out.println(
                            "Green " + greens.get(i).id + " is talking to Green " + greens.get(i).friends.get(j).id);
                    double newUncertainty = (greens.get(i).uncertainty + greens.get(i).friends.get(j).uncertainty) / 2;

                    // if leader is more certain that they are going to vote, leader infuences
                    // friend
                    if (greens.get(i).uncertainty < greens.get(i).friends.get(j).uncertainty) {
                        greens.get(i).friends.get(j).willVote = greens.get(i).willVote;
                        greens.get(i).friends.get(j).uncertainty = newUncertainty;
                    } else if (greens.get(i).uncertainty == greens.get(i).friends.get(j).uncertainty) {
                        // do nothing
                        continue;
                    } else {
                        greens.get(i).willVote = greens.get(i).friends.get(j).willVote;
                        greens.get(i).uncertainty = newUncertainty;
                    }
                }
            }
        }
    }

    private void sendRedMessage(int level, boolean grey_turn) {
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
        if (grey_turn) {
            change_votes(Double.MAX_VALUE);
        } else {
            change_votes(Min_Uncertainty);
        }
    }

    private void sendBlueMessage(int level, boolean Grey_turn) {
        if (blue.getEnergy() < level * 8) {
            System.out.println("Not enough energy to send message");
            return;
        }
        blue.LoseEnergy(level * 8);

        for (Green g : greens) {
            if (g.willVote) {
                // check greens uncertainty

                // Check if the uncertainty is greater than the "high uncertainty" threshold
                // double EnergyLoss = Math.round((0.1 * level) * 100.0) / 100.0;

                double newUncertainty = Math.round((g.uncertainty + 0.1 * level) * 100.0) / 100.0;
                if (newUncertainty <= 1) {
                    g.uncertainty = newUncertainty;
                }
            }
        }
        if (Grey_turn) {
            change_votes(Double.MAX_VALUE);
        } else {
            change_votes(Min_Uncertainty);
        }
    }

}
