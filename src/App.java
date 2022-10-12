
public class App {
    public static void main(String[] args) throws Exception {
        boolean visual = false;

        int red_wins = 0;
        int blue_wins = 0;
        int draws = 0;

        int n_unfollows = 0;
        for (int i = 0; i < (visual ? 1 : 1000); i++) {
            // create a new game
            Game game = new Game(100, 0.6, 3, 1, -1, 1, 0.5);

            if (visual) {
                Visualiser visualiser = new Visualiser();
                visualiser.game = game;
                game.printGreens();

                visualiser.setup();
                visualiser.graph.display();

                RedPlayer red = new RedPlayer(false);
                BluePlayer blue = new BluePlayer(false);

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

                RedPlayer red = new RedPlayer(true);
                BluePlayer blue = new BluePlayer(true);

                while (game.current_round < game.n_rounds) {
                    int n_followers_a = 0;
                    int n_followers_b = 0;
                    for (Green g : game.greens) {
                        if (g.followsRed) {
                            n_followers_a++;
                        }
                    }
                    game.red_turn(red);
                    for (Green g : game.greens) {
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
