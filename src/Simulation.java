public class Simulation {
    CLI cli;
    static int n_games = 1;
    static int n_rounds = 10;
    static int n_greens = 50;
    static double prob_edge = 0.3;
    static int n_greys = 3;
    static int n_spies = 1;
    static int u_lb = -1;
    static int u_ub = 1;
    static double prob_vote = 0.5;
    static int blue_starting_energy = 25;
    static int max_message_level = 5;

    static boolean red_is_ai = true;
    static boolean blue_is_ai = true;
    static boolean blue_starts = true;
    static boolean visualise = false;

    public static void main(String[] args) throws Exception {
        CLI cli = new CLI();

        int red_wins = 0;
        int blue_wins = 0;
        int draws = 0;

        int n_unfollows = 0;

        for (int i = 0; i < n_games; i++) {
            // create a new game
            GameBoard gb = new GameBoard(n_greens, prob_edge, n_greys, n_spies, u_lb, u_ub, prob_vote,
                    blue_starting_energy, max_message_level);

            Player red = new Player(red_is_ai);
            Player blue = new Player(blue_is_ai);

            Game game = new Game(gb, n_rounds, blue_starts, red, blue, visualise, cli);

            int score = game.run();

            if (score < 0) {
                red_wins++;
            } else if (score > 0) {
                blue_wins++;
            } else {
                draws++;
            }

            n_unfollows += game.board.n_unfollows;
            cli.print_move_distribution(game);
            // cli.plot_green_uncertainty_distribution(gb, 10);
        }
        cli.print_statistics(red_wins, blue_wins, draws, n_unfollows / n_games);
    }

    public int[] round_simulation(int number_of_rounds) {
        int[] win_summary = new int[2];
        int red_wins = 0;
        int blue_wins = 0;
        for (int i = 0; i < n_games; i++) {
            // create a new game
            GameBoard gb = new GameBoard(n_greens, prob_edge, n_greys, n_spies, u_lb, u_ub, prob_vote,
                    blue_starting_energy, max_message_level);

            Player red = new Player(red_is_ai);
            Player blue = new Player(blue_is_ai);

            Game game = new Game(gb, number_of_rounds, blue_starts, red, blue, visualise, cli);

            int score = game.run();

            if (score < 0) {
                red_wins++;
            } else if (score > 0) {
                blue_wins++;
            }
        }
        win_summary[0] = blue_wins;
        win_summary[1] = red_wins;
        return win_summary;
    }

    public int[] energy_simulation(int starting_energy) {
        int[] win_summary = new int[2];
        int red_wins = 0;
        int blue_wins = 0;
        for (int i = 0; i < n_games; i++) {
            // create a new game
            GameBoard gb = new GameBoard(n_greens, prob_edge, n_greys, n_spies, u_lb, u_ub, prob_vote,
                    starting_energy, max_message_level);

            Player red = new Player(red_is_ai);
            Player blue = new Player(blue_is_ai);

            Game game = new Game(gb, n_rounds, blue_starts, red, blue, visualise, cli);

            int score = game.run();

            if (score < 0) {
                red_wins++;
            } else if (score > 0) {
                blue_wins++;
            }
        }
        win_summary[0] = blue_wins;
        win_summary[1] = red_wins;
        return win_summary;
    }

    public int[] uncertainty_simulation(double u_lb, double u_ub) {
        CLI cli = new CLI();
        int[] win_summary = new int[2];
        int red_wins = 0;
        int blue_wins = 0;
        for (int i = 0; i < n_games; i++) {
            // create a new game
            GameBoard gb = new GameBoard(n_greens, prob_edge, n_greys, n_spies, u_lb, u_ub, prob_vote,
                    blue_starting_energy, max_message_level);

            Player red = new Player(red_is_ai);
            Player blue = new Player(blue_is_ai);

            Game game = new Game(gb, n_rounds, blue_starts, red, blue, visualise, cli);

            int score = game.run();

            if (score < 0) {
                red_wins++;
            } else if (score > 0) {
                blue_wins++;
            }
            cli.plot_green_uncertainty_distribution(gb, 10);
        }
        win_summary[0] = blue_wins;
        win_summary[1] = red_wins;

        return win_summary;
    }
}
