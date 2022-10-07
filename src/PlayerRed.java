public class PlayerRed implements Player {
    public boolean isAgent = false;
    public double uncertainty = 0.0;

    public PlayerRed(boolean isAgent) {
        this.isAgent = isAgent;
    }

    public int get_next_move(Game game) {
        if (isAgent) {
            return get_next_move_agent(game);
        } else {
            return get_next_move_human(game);
        }
    }

    private int get_next_move_human(Game game) {
        int move = -1;

        System.out.println("=========================================");
        System.out.print("\033[47m"); // set background to white
        System.out.println("Round " + game.current_round + "\033[0m");
        System.out.println("Number of Grey Agents Active: " + game.greys.size());

        System.out.println("===================================");
        System.out.print("\033[0;31m"); // change color to red
        System.out.println("Red's turn");
        System.out.print("\033[0m"); // reset color

        while (move == -1) {
            System.out.println("Red's options:");
            System.out.println("(1) Send a message");
            System.out.println("(2) Do nothing");
            System.out.print("Enter your choice: ");

            // get user input
            System.out.print("\033[0;32m"); // change color to green
            int choice = game.sc.nextInt();
            System.out.print("\033[0m"); // reset color

            switch (choice) {
                case 1:
                    System.out.print("Enter the message level from 1-5: ");

                    // get user input
                    System.out.print("\033[0;32m"); // change color to green
                    int level = game.sc.nextInt();
                    System.out.print("\033[0m"); // reset color
                    if (level >= 1 && level <= 5) {
                        set_uncertainty(level);
                        move = 1;
                        System.out.print("\033[0;35m"); // change color to purple
                        System.out.println("Red sends a message of level " + level);
                    } else {
                        System.out.print("\033[0;31m"); // change text color to red
                        System.out.println("Invalid message level");
                    }
                    break;
                case 2:
                    System.out.print("\033[0;35m"); // change color to purple
                    System.out.println("Red does nothing");
                    move = 2;
                    break;
                case 9:
                    game.print_debug_menu();
                    break;
                default:
                    System.out.print("\033[0;31m"); // change text color to red
                    System.out.println("Invalid choice, try again");
                    break;

            }
            System.out.print("\033[0m"); // reset color
        }
        return move;
    }

    private int get_next_move_agent(Game game) {
        return 0;
    }

    private void set_uncertainty(int msg_level) {
        uncertainty = 1.0 - (msg_level / 2.5);
    }
}
