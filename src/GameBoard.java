import java.util.Vector;

public class GameBoard {
    Vector<Green> greens;
    Vector<Grey> greys;
    double blue_uncertainty;
    double blue_energy;
    double red_uncertainty;

    public GameBoard(int n_green, double prob_edge, int n_grey, int n_spies, double uncertainty_lb,
            double uncertainty_ub, double percentage_vote) {

        greens = init_greens(n_green, prob_edge, uncertainty_lb, uncertainty_ub, percentage_vote);
        greys = init_greys(n_grey, n_spies);
        // set blue energy
        blue_energy = Config.BLUE_STARTING_ENERGY;
    }

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

    private Vector<Grey> init_greys(int n_grey, int n_spies) {
        // create n_grey greys with parameters
        Vector<Grey> greys = new Vector<Grey>();
        for (int i = 0; i < n_grey - n_spies; i++) {
            greys.add(new Grey(false));
        }
        for (int i = 0; i < n_spies; i++) {
            greys.add(new Grey(true));
        }
        return greys;
    }
}
