public class App {
    public static void main(String[] args) throws Exception {
        // create a new game
        Game game = new Game(100, 0.3, 3, 0.33, 0, 0.5, 0.5);

        game.printGreens();

        PlayerRed red = new PlayerRed(false);
        PlayerBlue blue = new PlayerBlue(false);

        while (game.current_round < game.n_rounds) {
            game.red_turn(red);
            game.blue_turn(blue);
            // game.green_turn();
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
