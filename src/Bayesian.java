import java.util.*;

public class Bayesian {
    public GameBoard board;
    // TODO Auto-generated constructor stub

    public double chance_of_winning;
    static int number_of_levels = 6;

    /**
     * Evaluate the blue turns score for the game
     * Higher the score
     * 
     * @param blue_turns
     * @return
     */
    public static double Red_score(int[] red_turns) {
        double score = 0;
        for (int i = 0; i < red_turns.length; i++) {
            int turn = red_turns[i];
            if (turn == 5) {
                score = score + 6;
            } else if (turn == 4) {
                score = score + 5;
            } else if (turn == 3) {
                score = score + 4;
            } else if (turn == 2) {
                score = score + 3;
            } else if (turn == 1) {
                score = score + 2;
            } else if (turn == 0) {
                score = score + 1;
            }
        }
        return score;
    }

    /**
     * 
     * @param red_move_score
     * @param n_rounds
     * @param n_of_grey_agents
     * @param number_of_will_votes
     * @param total_number_of_greens
     * @param blue_energy
     * @return
     */
    public static int blue_move_agent(double red_move_score, int n_rounds, int n_of_grey_agents,
            int number_of_will_votes,
            int total_number_of_greens, double blue_energy) {
        double score = 0;
        score = red_move_score / n_rounds;
        double proportion_of_voting_greens = (double) number_of_will_votes / (double) total_number_of_greens;

        if (proportion_of_voting_greens < 20.0 && proportion_of_voting_greens > 0.0) {
            score = score + 5;
        } else if (proportion_of_voting_greens < 40.0 && proportion_of_voting_greens > 20.0) {
            score = score + 4;
        } else if (proportion_of_voting_greens < 60.0 && proportion_of_voting_greens > 40.0) {
            score = score + 3;
        } else if (proportion_of_voting_greens < 80.0 && proportion_of_voting_greens > 60.0) {
            score = score + 2;
        } else if (proportion_of_voting_greens < 100.0 && proportion_of_voting_greens > 80.0) {
            score = score + 1;
        }

        if (blue_energy < 20.0 && blue_energy > 0.0) {
            score = score + 1;
        } else if (blue_energy < 40.0 && blue_energy > 20.0) {
            score = score + 2;
        } else if (blue_energy < 60.0 && blue_energy > 40.0) {
            score = score + 3;
        } else if (blue_energy < 80.0 && blue_energy > 60.0) {
            score = score + 4;
        } else if (blue_energy < 100.0 && blue_energy > 80.0) {
            score = score + 5;
        }

        if (score <= 20) {
            return 1;
        }
        if (score <= 40 && score > 20) {
            return 2;
        }
        if (score <= 60 && score > 40) {
            return 3;
        }
        if (score <= 80 && score > 60) {
            return 4;
        }
        if (score <= 100 && score > 80) {
            return 5;
        }
        return 0;

    }

    public static void main(String[] args) {
        // TODO Auto-generated method stub

        int[] red_turns = new int[5];
        red_turns[0] = 1;
        red_turns[1] = 1;
        red_turns[2] = 1;
        red_turns[3] = 1;
        red_turns[4] = 1;

        double red_move_score = Red_score(red_turns);

        int blue_move = blue_move_agent(red_move_score, Config.N_ROUNDS, Config.N_GREY, 5, Config.N_GREENS, 25.0);
        System.out.println(blue_move);

    }

}