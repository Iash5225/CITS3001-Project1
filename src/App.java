
public class App {
    public static void main(String[] args) throws Exception {
        boolean visual = false;

        int red_wins = 0;
        int blue_wins = 0;
        int draws = 0;

        int n_unfollows = 0;
        for (int i = 0; i < (visual ? 1 : Config.N_GAMES); i++) {
            // create a new game
            GameBoard gb = new GameBoard(Config.N_GREENS,
                    Config.PROB_EDGE,
                    Config.N_GREY,
                    Config.N_SPIES,
                    Config.UNCERTAINTY_LOWER_BOUND,
                    Config.UNCERTAINTY_UPPER_BOUND,
                    Config.PROB_VOTE);

            RedPlayer red = new RedPlayer(!visual);
            BluePlayer blue = new BluePlayer(!visual);

            Game game = new Game(gb, Config.N_ROUNDS, true, red, blue);

            if (visual) {
                Visualiser visualiser = new Visualiser();
                visualiser.game = game;
                game.printGreens();

                visualiser.setup();
                visualiser.graph.display();

                while (game.current_round < game.n_rounds) {
                    game.red_turn(red);
                    game.green_turn();
                    game.blue_turn(blue);
                    game.green_turn();
                    game.current_round++;
                    if (visual) {
                        visualiser.game = game;
                        visualiser.update_visualiser();
                    }
                }

                int score = game.who_won();
                game.plot_green_uncertainty_distribution(10);
                game.print_game_info_for_players();
                System.out.print("\033[1;93m");
                System.out.println("Game over");
                System.out.print("\033[0m");
                if (score < 0) {
                    System.out.print("\033[0;31m"); // Red
                    System.out.println("Red wins!");
                    System.out.print("\033[0m"); // Reset
                } else if (score > 0) {
                    System.out.print("\033[0;34m"); // Blue
                    System.out.println("Blue wins!");
                    System.out.print("\033[0m"); // Reset
                } else {
                    System.out.println("Tie");
                }
            } else {
                while (game.current_round < game.n_rounds) {
                    int n_followers_a = 0;
                    int n_followers_b = 0;
                    for (Green g : game.board.greens) {
                        if (g.followsRed) {
                            n_followers_a++;
                        }
                    }
                    game.red_turn(red);
                    for (Green g : game.board.greens) {
                        if (g.followsRed) {
                            n_followers_b++;
                        }
                    }
                    n_unfollows += n_followers_a - n_followers_b;
                    // game.green_turn();
                    game.blue_turn(blue);
                    game.green_turn();
                    game.current_round++;

                }
            }

            int score = game.who_won();
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

        System.out.println("Average number of unfollows: " + (double) n_unfollows / 1000);

    }

}
