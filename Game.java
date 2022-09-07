import java.util.*;

public class Game {
    public Vector<Green> greens;

    public Game(int n_green, double prob_edge, int n_grey, double prob_spy, double uncertainty_lb, double uncertainty_ub, double percentage_vote) {
        greens = new Vector<Green>();
        for (int i = 0; i < n_green; i++) {
            double uncertainty = uncertainty_lb + Math.random() * (uncertainty_ub - uncertainty_lb);
            boolean willVote = Math.random() < percentage_vote;
            greens.add(new Green(uncertainty, willVote, true));
        }
        for (Green g : greens) {
            for (Green g2 : greens) {
                if (g != g2 && Math.random() < prob_edge) {
                    g.friends.add(g2);
                }
            }
        }
    }

}
