import java.lang.Thread;
public class Game {
    public GameBoard board;
    public CLI cli;
    public Visualiser visualiser;
    public Bayesian bayesian;

    public int n_rounds;
    public int current_round;
    public boolean blue_starts;
    public Player red_player;
    public Player blue_player;
    public int[] red_moves_played;

    public boolean is_human;

    // statistics
    public int n_unfollows = 0;

    /**
     * Creates a new game with the given parameters.
     * 
     * @param board         The game board.
     * @param n_rounds      The number of rounds.
     * @param is_blues_turn Whether it is blue's turn.
     * @param red           The red player.
     * @param blue          The blue player.
     *
     */
    public Game(GameBoard board, int n_rounds, boolean blue_starts, Player red_player, Player blue_player) {
        this.board = board;
        this.n_rounds = n_rounds;
        this.blue_starts = blue_starts;
        this.red_player = red_player;
        this.blue_player = blue_player;
        this.is_human = !red_player.is_agent || !blue_player.is_agent;

        this.cli = new CLI();
        if (Config.VISUALISE) {
            this.visualiser = new Visualiser();
            this.visualiser.game = this;
            visualiser.setup();
            visualiser.graph.display();
        }

        red_moves_played = new int[n_rounds];
        current_round = 0;
    }

    // ================================ GAME LOGIC ================================
    /**
     * Runs the game.
     * 
     * @return The score of the game.
     */
    public int run() {
        for (int i = 0; i < n_rounds; i++) {

            if (blue_starts) {
                blue_turn(get_blue_action());
                int red_MOVE = get_red_action();
                red_turn(red_MOVE);
                red_moves_played[i] = red_MOVE;
                green_turn();
            } else {
                red_turn(get_red_action());
                blue_turn(get_blue_action());
                green_turn();
            }
            if (Config.VISUALISE) {
                visualiser.update_visualiser();
                try {
                    Thread.sleep(5000);
                } catch (InterruptedException e) {
                    System.out.println("got interrupted!");
                }

            }
            current_round++;
        }
        if (is_human) {
            cli.print_game_result(board);
        }
        return board.get_score();
    }

    /**
     * Red's turn.
     */
    public int get_red_action() {
        if (is_human) {
            //cli.print_info("Red's turn", "red");
            //cli.print_game_info_for_players(board);
        }
        Boolean[] options = board.get_red_options();
        int action = -1;

        while (action < 0 || action >= options.length || !options[action]) {
            if (red_player.is_agent) {
                action = red_player.get_next_move(options);
                System.out.println("red Agent played:" + action);
            } else {
                action = cli.get_red_move(options);
            }
        }
        return action;
    }

    /**
     * Blue's turn.
     */
    public int get_blue_action() {
        if (!red_player.is_agent || !blue_player.is_agent) {
            //cli.print_info("Blue's Turn", "blue");
            cli.print_game_info_for_players(board);
        }
        Boolean[] options = board.get_blue_options();
        int action = -1;

        while (action < 0 || action >= options.length) {
            if (blue_player.is_agent) {
                //action = blue_player.get_next_move(options);
                double score = Bayesian.Red_score(red_moves_played);
                //System.out.println("Red's score is: " + score);
                int move = Bayesian.blue_move_agent(score,n_rounds,board.greys.size(),board.get_n_voters(),board.greens.size(),board.blue_energy);
                //  System.out.println("-----------------------");
                  System.out.println("Blue Agent played:" + move);
                  cli.print_game_info_for_players(board);
                //  System.out.println("-----------------------");
                action = move;
            } else {
                action = cli.get_blue_move(options);
            }
        }
        return action;
    }

    public void blue_turn(int action) {
        if (action == 0) {
            // do nothing
        } else if (action < Config.MAX_MESSAGE_LEVEL + 1) {
            // check if blue has enough energy
            if (board.get_blue_options()[action]) {
                // send message
                board.blue_energy -= action;
                // set blue uncertainty
                board.blue_uncertainty = 1.0 - (double) action * 2 / Config.MAX_MESSAGE_LEVEL;

                // update greens
                for (Green g : board.greens) {
                    g.influence(board.blue_uncertainty, true);
                }
            }

        } else if (action == Config.MAX_MESSAGE_LEVEL + 1) {
            // release grey
            board.release_grey();
        } else {
            System.out.println("Invalid action");
            System.exit(1);
        }
    }

    public void red_turn(int action) {
        if (action == 0) {
            // do nothing
        } else if (action < Config.MAX_MESSAGE_LEVEL + 1) {
            // check if red has enough energy
            if (board.get_red_options()[action]) {
                // send message
                board.red_uncertainty = 1.0 - (double) action * 2 / Config.MAX_MESSAGE_LEVEL;
                // update greens
                for (Green g : board.greens) {
                    if (!g.followsRed)
                        continue;
                    n_unfollows += g.unfollow(board.red_uncertainty);
                    g.influence(board.red_uncertainty, false);
                }
            }
        } else {
            System.out.println("Invalid action");
            System.exit(1);
        }
    }

    public void green_turn() {
        for (Green g : board.greens) {
            g.update();
        }
        for (Green g1 : board.greens) {
            for (Green g2 : g1.friends) {
                g2.influence(g1.uncertainty, g1.willVote);
            }
        }
        for (Green g : board.greens) {
            g.update();
        }
    }

}
