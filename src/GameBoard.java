import java.util.Vector;

public class GameBoard {
    Vector<Green> greens;
    Vector<Grey> greys;
    double blue_uncertainty;
    double blue_energy;
    double red_uncertainty;

    int max_message_level;
    double blue_starting_energy;

    int n_unfollows;

    public GameBoard(int n_green, double prob_edge, int n_grey, int n_spies, double uncertainty_lb,
            double uncertainty_ub, double percentage_vote, double blue_starting_energy, int max_message_level) {

        greens = init_greens(n_green, prob_edge, uncertainty_lb, uncertainty_ub, percentage_vote);
        greys = init_greys(n_grey, n_spies);
        // set blue energy
        this.blue_starting_energy = blue_starting_energy;
        this.blue_energy = blue_starting_energy;
        this.blue_uncertainty = 0;
        this.red_uncertainty = 0;
        this.max_message_level = max_message_level;

        this.n_unfollows = 0;
    }

    /**
     * Initialise the greens
     * @param n_green number of greens
     * @param prob_edge probability of an edge between two greens 
     * @param uncertainty_lb lower bound of uncertainty
     * @param uncertainty_ub upper bound of uncertainty
     * @param percentage_vote percentage of greens that vote
     * @return vector of greens
     */
    private Vector<Green> init_greens(int n_green, double prob_edge, double uncertainty_lb,
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
        return greens;
    }

    /**
     * Initialise the greys
     * @param n_grey number of greys
     * @param n_spies number of spies
     * @return vector of greys
     */
    private Vector<Grey> init_greys(int n_grey, int n_spies) {
        // create n_grey greys with parameters and n_spies spies
        Vector<Grey> greys = new Vector<Grey>();
        for (int i = 0; i < n_grey - n_spies; i++) {
            greys.add(new Grey(false));
        }
        for (int i = 0; i < n_spies; i++) {
            greys.add(new Grey(true));
        }
        return greys;
    }

    /**
     * @return the number of greens that will vote
     */
    public int get_n_voters() {
        int count = 0;
        for (Green g : greens) {
            if (g.willVote) {
                count++;
            }
        }
        return count;
    }

    /**
     * Grey agent interracting with the game board
     * @return whether or not a grey is released
     */
    public boolean release_grey() {
        Grey grey_agent = greys.remove((int) Math.random() * greys.size());
        if (grey_agent.isSpy) {
            for (Green g : greens) {
                g.influence(-1.0, false);
            }
        } else {
            for (Green g : greens) {
                g.influence(-1.0, true);
            }
        }
        return grey_agent.isSpy;
    }

    /**
     * @return all the avaialble moves blue can make
     */
    public Boolean[] get_blue_options() {
        int max_level = Math.min((int) Math.floor(blue_energy), max_message_level);

        Boolean[] options = new Boolean[max_message_level + 2];
        options[0] = true;
        for (int i = 1; i < max_level + 1; i++) {
            options[i] = true;
        }
        for (int i = max_level + 1; i < max_message_level + 2; i++) {
            options[i] = false;
        }

        if (greys.size() > 0) {
            options[max_message_level + 1] = true;
        } else {
            options[max_message_level + 1] = false;
        }

        return options;
    }

    /**
     * @return all the avaialble moves red can make
     */
    public Boolean[] get_red_options() {
        Boolean[] options = new Boolean[max_message_level + 1];
        for (int i = 0; i < max_message_level + 1; i++) {
            options[i] = true;
        }
        return options;
    }

    /**
     * 
     * @return the game score
     */
    public int get_score() {
        int voters = 0;
        int non_voters = 0;
        for (Green g : greens) {
            if (g.willVote) {
                voters++;
            } else {
                non_voters++;
            }
        }
        return voters - non_voters;
    }

    /**
     * 
     * @param action the action blue takes
     */
    public void blue_turn(int action) {
        if (action == 0) {
            // do nothing
        } else if (action < max_message_level + 1) {
            // check if blue has enough energy
            if (get_blue_options()[action]) {
                // send message
                blue_energy -= action;
                // set blue uncertainty
                blue_uncertainty = 1.0 - (double) action * 2 / max_message_level;

                // update greens
                for (Green g : greens) {
                    g.influence(blue_uncertainty, true);
                }
            }

        } else if (action == max_message_level + 1) {
            // release grey
            release_grey();
        } else {
            System.out.println("Invalid action");
            System.exit(1);
        }
    }

    /**
     * 
     * @param action the action red takes
     */
    public void red_turn(int action) {
        if (action == 0) {
            // do nothing
        } else if (action < max_message_level + 1) {
            // check if red has enough energy
            if (get_red_options()[action]) {
                // send message
                red_uncertainty = 1.0 - (double) action * 2 / max_message_level;
                // update greens
                for (Green g : greens) {
                    if (!g.followsRed)
                        continue;
                    n_unfollows += g.unfollow(red_uncertainty);
                    g.influence(red_uncertainty, false);
                }
            }
        } else {
            System.out.println("Invalid action");
            System.exit(1);
        }
    }

    /**
     *  green interraction with the game board
     */
    public void green_turn() {
        for (Green g : greens) {
            g.update();
        }
        for (Green g1 : greens) {
            for (Green g2 : g1.friends) {
                g2.influence(g1.uncertainty, g1.willVote);
            }
        }
    }
}
