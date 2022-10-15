public class GameController {
    CLI cli;

    public GameController(CLI cli) {
        this.cli = cli;
    }

    public void run() {

        int n_rounds = cli.get_int_from_user("How many rounds?", 5, 100);
        int n_greens = cli.get_int_from_user("How many greens?", 5, 100);
        double prob_edge = cli.get_double_from_user("What is the probability of an edge in the green network?", 0,
                1);
        int n_grey = cli.get_int_from_user("How many greys agents should there be?", 0, 5);
        int n_spies = cli.get_int_from_user("How many of them should be spies?", 0, n_grey);
        double u_lb = cli
                .get_double_from_user("What is the lower bound of the starting uncertainty?", -1, 1);
        double u_ub = cli.get_double_from_user(
                "What is the upper bound of the starting uncertainty?", u_lb, 1);
        double prob_vote = cli.get_double_from_user("What is the proportion of the population that votes?", 0, 1);
        double blue_starting_energy = cli.get_int_from_user("How much energy should blue start with?", 0, 100);
        int max_message_level = cli.get_int_from_user("What is the maximum message level?", 2, 10);

        boolean red_is_ai = cli.get_boolean_from_user("Would you like red to be an AI?");
        boolean blue_is_ai = cli.get_boolean_from_user("Would you like blue to be an AI?");
        int starting_player = cli.menu("Who should start?", new String[] { "Red", "Blue" }, null);
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
            GameBoard gb = new GameBoard(n_greens, prob_edge, n_grey, n_spies, u_lb,
                    u_ub, prob_vote, blue_starting_energy, max_message_level);

            Player red = new Player(red_is_ai);
            Player blue = new Player(red_is_ai);

            Game game = new Game(gb, n_rounds, blue_starts, red, blue, visualise);

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
