public class Simulation {
    CLI cli;
    int n_games;

    public static void main(String[] args) throws Exception {
        CLI cli = new CLI();

        int n_games = 10;
        int n_rounds = 50;
        int n_greens = 25;
        double prob_edge = 0.3;
        int n_greys = 10;
        int n_spies = 1;
        int u_lb = -1;
        int u_ub = 1;
        double prob_vote = 0.5;
        int blue_starting_energy = 100;
        int max_message_level = 5;

        boolean red_is_ai = true;
        boolean blue_is_ai = true;
        boolean blue_starts = true;
        boolean visualise = false;

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
            cli.plot_green_uncertainty_distribution(gb, 10);
        }
        cli.print_statistics(red_wins, blue_wins, draws, n_unfollows / n_games);
    }
}
