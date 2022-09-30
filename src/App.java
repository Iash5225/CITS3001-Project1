public class App {
    public static void main(String[] args) throws Exception {
        // create a new game
        Game game = new Game(10, 0.3, 3, 0.33, 0, 0.5, 0.5);

        game.printGreens();

        while (game.current_round < game.n_rounds) {
            game.nextRound();
        }

        game.printGreens();

        System.out.println("Game over");

    }
}
