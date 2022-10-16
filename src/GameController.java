public class GameController {
    CLI cli;
    Configuration c;

    public GameController(CLI cli, Configuration config) {
        this.cli = cli;
        this.c = config;
    }

    public void run() {

        boolean red_is_ai = cli.get_boolean_from_user("Would you like red to be an AI?");
        boolean blue_is_ai = cli.get_boolean_from_user("Would you like blue to be an AI?");
        int starting_player = cli.menu("Who should start?", new String[] { "Red", "Blue" }, "");
        boolean blue_starts = (starting_player == 1 ? true : false);
        int n_games = 1;
        boolean visualise = false;
        if (red_is_ai && blue_is_ai) {
            n_games = cli.get_int_from_user("How many games would you like to run?", 1, 1000);
        } else {
            visualise = cli.get_boolean_from_user("Would you like to visualise the game?");
        }

        int red_wins = 0;
        int blue_wins = 0;
        int draws = 0;

        int n_unfollows = 0;

        for (int i = 0; i < n_games; i++) {
            // create a new game
            GameBoard gb = new GameBoard(c.n_greens, c.prob_edge, c.n_greys, c.n_spies, c.u_lb,
                    c.u_ub, c.prob_vote, c.blue_starting_energy, c.max_message_level);

            Player red = new Player(red_is_ai);
            Player blue = new Player(red_is_ai);

            Game game = new Game(gb, c.n_rounds, blue_starts, red, blue, visualise, cli);

            int score = game.run();

            if (score < 0) {
                red_wins++;
            } else if (score > 0) {
                blue_wins++;
            } else {
                draws++;
            }

            n_unfollows += game.board.n_unfollows;
        }

        cli.print_statistics(red_wins, blue_wins, draws, n_unfollows / n_games);
    }
}
