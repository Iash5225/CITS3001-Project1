import java.util.*;

public class Game {
    public Vector<Green> greens;
    public Red rednode;

    public Game(int n_green, double prob_edge, int n_grey, double prob_spy, double uncertainty_lb, double uncertainty_ub, double percentage_vote) {
        greens = new Vector<Green>();
        for (int i = 0; i < n_green; i++) {
            double uncertainty = uncertainty_lb + Math.random() * (uncertainty_ub - uncertainty_lb);
            boolean willVote = Math.random() < percentage_vote;
            greens.add(new Green(uncertainty, willVote, true));
        }
        for (Green g : greens) {
            for (Green g2 : greens) {
                if ( g != g2 && Math.random() < prob_edge ){
                    g.friends.add(g2);
                }
            }
        }
    }

    /**
     *
     * @param rednode the red node
     *                1: send level 1 message - 10% of the greens will vote
     *                2: send level 2 message - 20% of the greens will vote
     *                3: send level 3 message - 30% of the greens will vote
     *                4: send level 4 message - 40% of the greens will vote
     *                5: send level 5 message - 50% of the greens will vote
     */
    public void InfluenceGreen(Red rednode) {
        switch (rednode.levelofMessage) {
            case 1:
                for (Green g : greens) {
                    g.uncertainty = g.uncertainty + 0.1;
                }

                //Increase green uncertainty by an increment of 0.1
                //check if bounds are still valid
                //check if green will vote is false

            case 2:
//Decrease green uncertainty by an increment of 0.2
                for (Green g : greens) {
                    if ( g.uncertainty + 0.2 < 1 ){
                        g.uncertainty = g.uncertainty + 0.2;
                    }
                }
            case 3:
//Decrease green uncertainty by an increment of 0.3
                for (Green g : greens) {
                    if ( g.uncertainty + 0.2 < 1 ){
                        g.uncertainty = g.uncertainty + 0.3;
                    }
                }
            case 4:
//Decrease green uncertainty by an increment of 0.4
                for (Green g : greens) {
                    if ( g.uncertainty + 0.2 < 1 ){
                        g.uncertainty = g.uncertainty + 0.4;
                    }
                }
            case 5:
//Decrease green uncertainty by an increment of 0.5
                for (Green g : greens) {
                    if ( g.uncertainty + 0.2 < 1 ){
                        g.uncertainty = g.uncertainty + 0.5;
                    }
                }

        }
    }
}
