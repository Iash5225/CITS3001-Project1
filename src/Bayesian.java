import java.util.*;

public class Bayesian {
    public GameBoard board;
    // TODO Auto-generated constructor stub

    /**
     * Evaluate the blue turns score for the game
     * Higher the score the agressive the agent is playing
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
     * @param red_move_score         the score evaluation for the red moves played
     *                               in game
     * @param n_rounds               the number of rounds played in the game
     * @param n_of_grey_agents       the number of grey agents in the game available
     * @param number_of_will_votes   the number of will votes in the game
     * @param total_number_of_greens the total number of greens in the game
     * @param blue_energy            the energy of the blue agent
     * @return the best move for the blue agent
     */
    public static int blue_move_agent(double red_move_score, int n_rounds, int n_of_grey_agents,
            int number_of_will_votes,
            int total_number_of_greens, double blue_energy) {
        double score = 0;
        // score = red_move_score;
        double proportion_of_voting_greens = (double) number_of_will_votes / (double) total_number_of_greens;

        // assess the proportion of voting greens
        // if low, then the blue agent should be more aggressive
        // if high, then the blue agent should be more passive
        if (proportion_of_voting_greens < 0.2 && proportion_of_voting_greens > 0.0) {
            score = score + 5;
        } else if (proportion_of_voting_greens < 0.4 && proportion_of_voting_greens > 0.2) {
            score = score + 4;
        } else if (proportion_of_voting_greens < 0.6 && proportion_of_voting_greens > 0.4) {
            score = score + 3;
        } else if (proportion_of_voting_greens < 0.8 && proportion_of_voting_greens > 0.6) {
            score = score + 2;
        } else if (proportion_of_voting_greens < 1.0 && proportion_of_voting_greens > 0.8) {
            score = score + 1;
        }

        double energy_increment = Config.BLUE_STARTING_ENERGY / 5;

        // assess the energy of the blue agent
        // if low, then the blue agent should be more passive
        // if high, then the blue agent should be more aggressive
        if (blue_energy <= energy_increment && blue_energy > 0.0) {
            score = score + 1;
        } else if (blue_energy <= energy_increment * 2 && blue_energy > energy_increment) {
            score = score + 2;
        } else if (blue_energy <= energy_increment * 3 && blue_energy > energy_increment * 2) {
            score = score + 3;
        } else if (blue_energy <= energy_increment * 4 && blue_energy > energy_increment * 3) {
            score = score + 4;
        } else if (blue_energy <= Config.BLUE_STARTING_ENERGY && blue_energy > energy_increment * 4) {
            score = score + 5;
        }

        if (score <= Config.max_score * 0.2) {
            if (n_of_grey_agents > 0) {
                return 6;
            } else {
                return 5;
            }
        }
        if (score <= Config.max_score * 0.4 && score > Config.max_score * 0.2) {
            return 4;
        }
        if (score <= Config.max_score * 0.6 && score > Config.max_score * 0.4) {
            return 3;
        }
        if (score <= Config.max_score * 0.8 && score > Config.max_score * 0.6) {
            return 2;
        }
        if (score <= Config.max_score && score > Config.max_score * 0.8) {
            return 1;
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

        int blue_move = blue_move_agent(red_move_score, Config.N_ROUNDS, Config.N_GREY,
                Config.N_GREENS / 2, Config.N_GREENS, 25.0);
        System.out.println(blue_move);

    }

}