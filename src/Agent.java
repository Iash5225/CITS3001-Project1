
public class Agent {
    public GameBoard board;

    /**
     * Evaluate the agents moves for the game
     * Higher the score the more agressive the agent is playing
     * 
     * @param moves the moves played by the opponent
     * @return the score
     */
    public static double Moves_evaluator(int[] moves) {
        double score = 0;
        for (int i = 0; i < moves.length; i++) {
            int turn = moves[i];
            if (turn == 5) {
                score = score + 5;
            } else if (turn == 4) {
                score = score + 4;
            } else if (turn == 3) {
                score = score + 3;
            } else if (turn == 2) {
                score = score + 2;
            } else if (turn == 1) {
                score = score + 1;
            } 
        }
        return score;
    }

    /**
     * 
     * @param red_move_score - score of the red moves played
     * @param n_rounds       - number of rounds played
     * @param gb             - gameboard
     * @return
     */
    public static int blue_move_agent(double red_move_score, int n_rounds, GameBoard gb) {

        final double max_score = 3;
        double score = 0;
        score = red_move_score/n_rounds;
        double blue_energy = gb.blue_energy;
        int total_number_of_greens = gb.greens.size();
        Boolean[] available_moves = gb.get_blue_options();
        double blue_starting_energy = gb.blue_starting_energy;
        double proportion_of_voting_greens = (double) (gb.get_n_voters() / total_number_of_greens);

        // assess the proportion of voting greens
        // if low, then the blue agent should be more aggressive
        // if high, then the blue agent should be more passive
        if (proportion_of_voting_greens < 0.2 && proportion_of_voting_greens > 0.0) {
            score = score + 0.5;
        } else if (proportion_of_voting_greens < 0.4 && proportion_of_voting_greens > 0.2) {
            score = score + 0.4;
        } else if (proportion_of_voting_greens < 0.6 && proportion_of_voting_greens > 0.4) {
            score = score + 0.3;
        } else if (proportion_of_voting_greens < 0.8 && proportion_of_voting_greens > 0.6) {
            score = score + 0.2;
        } else if (proportion_of_voting_greens < 1.0 && proportion_of_voting_greens > 0.8) {
            score = score + 0.1;
        }

        double energy_increment = blue_starting_energy / 5;

        // assess the energy of the blue agent
        // if low, then the blue agent should be more passive
        // if high, then the blue agent should be more aggressive
        if (blue_energy <= energy_increment && blue_energy > 0.0) {
            score = score + 0.1;
        } else if (blue_energy <= energy_increment * 2 && blue_energy > energy_increment) {
            score = score + 0.2;
        } else if (blue_energy <= energy_increment * 3 && blue_energy > energy_increment * 2) {
            score = score + 0.3;
        } else if (blue_energy <= energy_increment * 4 && blue_energy > energy_increment * 3) {
            score = score + 0.4;
        } else if (blue_energy <= blue_starting_energy && blue_energy > energy_increment * 4) {
            score = score + 0.5;
        }
        if (blue_energy <= 6 && score > 0.0 && available_moves[6]) {
            return 6;
        }
        if (score <= max_score * 0.2 && score > 0.0 && available_moves[5]) {
            return 5;
        }
        if (score <= max_score * 0.4 && score > max_score * 0.2 && available_moves[4]) {
            return 4;
        }
        if (score <= max_score * 0.6 && score > max_score * 0.4 && available_moves[3]) {
            return 3;
        }
        if (score <= max_score * 0.8 && score > max_score * 0.6 && available_moves[2]) {
            return 2;
        }
        if (score <= max_score && score > max_score * 0.8 && available_moves[1]) {
            return 1;
        }
        return 0;
    }

    /**
     * 
     * @param blue_move_score - score of the blue moves played
     * @param n_rounds        - number of rounds played in total
     * @param gb              - gameboard
     * @return
     */
    public static int red_move_agent(double blue_move_score, int n_rounds, GameBoard gb) {

        final double max_score = 6 * n_rounds + 15;
        double score = 0;
        score = blue_move_score;
        int total_number_of_greens = gb.greens.size();
        Boolean[] available_moves = gb.get_red_options();
        
        double proportion_of_following_red = (double) 1- (gb.n_unfollows / total_number_of_greens);
        double proportion_of_voting_greens = (double) (gb.get_n_voters() / total_number_of_greens);

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

        // assess the proportion of following red
        // if low, then the red agent should be more agressive
        // if high, then the red agent should be more passive
        if (proportion_of_following_red < 0.2 && proportion_of_following_red > 0.0) {
            score = score + 5;
        } else if (proportion_of_following_red < 0.4 && proportion_of_following_red > 0.2) {
            score = score + 4;
        } else if (proportion_of_following_red < 0.6 && proportion_of_following_red > 0.4) {
            score = score + 3;
        } else if (proportion_of_following_red < 0.8 && proportion_of_following_red > 0.6) {
            score = score + 2;
        } else if (proportion_of_following_red < 1.0 && proportion_of_following_red > 0.8) {
            score = score + 1;
        }

        if (score <= max_score * 0.2 && score > 0.0 && available_moves[5]) {
            return 5;
        }
        if (score <= max_score * 0.4 && score > max_score * 0.2 && available_moves[4]) {
            return 4;
        }
        if (score <= max_score * 0.6 && score > max_score * 0.4 && available_moves[3]) {
            return 3;
        }
        if (score <= max_score * 0.8 && score > max_score * 0.6 && available_moves[2]) {
            return 2;
        }
        if (score <= max_score && score > max_score * 0.8 && available_moves[1]) {
            return 1;
        }
        return 0;
    }

    // public static void main(String[] args) {

    // int[] red_turns = new int[5];
    // red_turns[0] = 1;
    // red_turns[1] = 1;
    // red_turns[2] = 1;
    // red_turns[3] = 1;
    // red_turns[4] = 1;

    // double red_move_score = Red_score(red_turns);

    // int blue_move = blue_move_agent(red_move_score, Config.N_ROUNDS,
    // Config.N_GREY,
    // Config.N_GREENS / 2, Config.N_GREENS, 25.0);
    // System.out.println(blue_move);

    // }

}