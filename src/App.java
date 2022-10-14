
public class App {
    public static void main(String[] args) throws Exception {
        int n_games = Config.VISUALISE ? 1 : Config.N_GAMES;

        int red_wins = 0;
        int blue_wins = 0;
        int draws = 0;

        int n_unfollows = 0;
        for (int i = 0; i < n_games; i++) {
            // create a new game
            GameBoard gb = new GameBoard(Config.N_GREENS,
                    Config.PROB_EDGE,
                    Config.N_GREY,
                    Config.N_SPIES,
                    Config.UNCERTAINTY_LOWER_BOUND,
                    Config.UNCERTAINTY_UPPER_BOUND,
                    Config.PROB_VOTE);

            Player red = new Player(Config.RED_IS_AI);
            Player blue = new Player(Config.BLUE_IS_AI);

            Game game = new Game(gb, Config.N_ROUNDS, true, red, blue);

            int score = game.run();

            if (score < 0) {
                red_wins++;
            } else if (score > 0) {
                blue_wins++;
            } else {
                draws++;
            }
        }

        System.out.println("Red wins: " + red_wins);
        System.out.println("Blue wins: " + blue_wins);
        System.out.println("Draws: " + draws);

        System.out.println("Average number of unfollows: " + (double) n_unfollows / n_games);

    }

}
