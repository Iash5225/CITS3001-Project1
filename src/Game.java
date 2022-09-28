package src;

import java.util.*;

public class Game {
    public Vector<Green> greens;
    public Red rednode;
    public Grey greynode;
    public Blue bluenode;

    public Game(int n_green, double prob_edge, int n_grey, double prob_spy, double uncertainty_lb,
                double uncertainty_ub, double percentage_vote) {
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
     * @param rednode the red node
     *                1: send level 1 message - 10% of the greens will vote
     *                2: send level 2 message - 20% of the greens will vote
     *                3: send level 3 message - 30% of the greens will vote
     *                4: send level 4 message - 40% of the greens will vote
     *                5: send level 5 message - 50% of the greens will vote
     */
    public void InfluenceGreen(Red rednode) {
        double percentage_vote = 0.1 * rednode.levelofMessage;
        for (Green g : greens) {
            if ( g.uncertainty + percentage_vote < 1 ){
                g.uncertainty = g.uncertainty + percentage_vote;
            }
        }
    }


    /**
     * if grey is a spy, it can act like a red agent without losing followers
     * if it is an ally of blue, blue can take its turn, without losing a lifeline.
     *
     * @param greynode the grey node
     */
    public void grey_turn(Grey greynode){
        boolean isSpy = greynode.SpyorNot();

        //if grey is a spy, it can act like a red agent without losing followers
        if (isSpy){
            //send a message to the greens
            InfluenceGreen(rednode);
        }

        //if it is an ally of blue, blue can take its turn, without losing a lifeline.
        else{
            //blue can take its turn, without losing a lifeline.
            //add blue turn here

            //added this line -- just so I can remember that blues looses 0 energy
            bluenode.LoseEnergy(0);

        }
    }
}
