
public class App {
    public static void main(String[] args) throws Exception {
        CLI cli = new CLI();
        cli.print_welcome();

        while (true) {
            int choice = cli.menu("What would you like to do?", new String[] { "Play a game", "Quit" },
                    "Please choose an option.");
            if (choice == 0) {
                GameController gc = new GameController(cli);
                gc.run();
            } else {
                break;
            }
        }
    }

}
