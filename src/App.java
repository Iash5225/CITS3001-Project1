public class App {
    public static void main(String[] args) throws Exception {
        Visualiser visualiser = new Visualiser();
        for (int i = 0; i < 1; i++) {
            // create a new game
            Game game = new Game(10, 0.3, 3, 0.33, -1, 0, 0.8);
            visualiser.game = game;
            game.printGreens();

            visualiser.setup();
            visualiser.graph.display();

            RedPlayer red = new RedPlayer(true);
            BluePlayer blue = new BluePlayer(true);

            while (game.current_round < game.n_rounds) {
                game.red_turn(red);
                game.green_turn();
                game.blue_turn(blue);
                game.green_turn();
                game.current_round++;
                visualiser.game = game;
                visualiser.update_visualiser();
            }
            game.plot_green_uncertainty_distribution(10);
            game.who_won();
        }

        System.out.print("\033[1;93m");
        System.out.println("Game over");
        System.out.print("\033[0m");
    }
}
