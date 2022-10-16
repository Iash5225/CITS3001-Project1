import java.lang.Thread;

public class Game {
    public GameBoard board;
    public CLI cli;
    public Visualiser visualiser;
    public Agent bayesian;

    public int n_rounds;
    public int current_round;
    public boolean blue_starts;
    public Player red_player;
    public Player blue_player;
    public int[] red_moves_played;

    public boolean is_human;

    public boolean visualise;

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
    public Game(GameBoard board, int n_rounds, boolean blue_starts, Player red_player, Player blue_player,
            boolean visualise, CLI cli) {
        this.board = board;
        this.n_rounds = n_rounds;
        this.blue_starts = blue_starts;
        this.red_player = red_player;
        this.blue_player = blue_player;
        this.is_human = !red_player.is_agent || !blue_player.is_agent;
        this.visualise = visualise;

        this.cli = cli;
        if (visualise) {
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
                board.blue_turn(get_blue_action());
                board.red_turn(get_red_action());
                board.green_turn();
            } else {
                board.red_turn(get_red_action());
                board.blue_turn(get_blue_action());
                board.green_turn();
            }
            if (visualise) {
                visualiser.update_visualiser();
                try {
                    Thread.sleep(1500);
                } catch (InterruptedException e) {
                    System.out.println("got interrupted!");
                }

            }
            current_round++;
        }
        if (is_human) {
            cli.print_game_result(board);
            if (visualise) {
                visualiser.update_visualiser();
            }
        }
        return board.get_score();
    }

    /**
     * Red's turn.
     */
    public int get_red_action() {
        if (is_human) {
            cli.print_info("Red's turn", "red");
            cli.print_game_info_for_players(board);
        }
        Boolean[] options = board.get_red_options();
        int action = -1;

        while (action < 0 || action >= options.length || !options[action]) {
            if (red_player.is_agent) {
                action = red_player.get_next_move(options);
                //System.out.println("red Agent played:" + action);
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
            cli.print_info("Blue's Turn", "blue");
            cli.print_game_info_for_players(board);
        }
        Boolean[] options = board.get_blue_options();
        int action = -1;

        while (action < 0 || action >= options.length) {
            if (blue_player.is_agent) {
                // action = blue_player.get_next_move(options);
                double score = Agent.Red_score(red_moves_played);
                // System.out.println("Red's score is: " + score);
                int move = Agent.blue_move_agent(score, n_rounds,board);
                // System.out.println("-----------------------");
                //System.out.println("Blue Agent played:" + move);
                // cli.print_game_info_for_players(board);
                // System.out.println("-----------------------");
                action = move;
            } else {
                action = cli.get_blue_move(options);
            }
        }
        return action;
    }
}
