public class App {
    public static void main(String[] args) throws Exception {
        // create a new game
        Game game = new Game(50, 0.3, 3, 0.33, -1, 1, 0.5);

        // game.printGreens();

        RedPlayer red = new RedPlayer(false);
        BluePlayer blue = new BluePlayer(false);

        while (game.current_round < game.n_rounds) {
            game.red_turn(red);
            game.green_turn();
            game.blue_turn(blue);
            game.green_turn();
            game.current_round++;
        }

        game.game_status();
        game.green_voting_day();
        game.printGreens();

        System.out.print("\033[1;93m");
        System.out.println("Game over");
        System.out.print("\033[0m");
    }
}
