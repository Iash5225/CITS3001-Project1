import java.util.Scanner;

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
        System.out.println("Red's options:");
        System.out.println("(1) Send a message");
        System.out.println("(2) Do nothing");
        System.out.print("Enter your choice: ");

        while (move == -1) {
            int choice = game.sc.nextInt();
            switch (choice) {
                case 1:
                    System.out.print("Enter the message level from 1-5: ");
                    int level = game.sc.nextInt();
                    System.out.print("\033[0;35m"); // change color to purple
                    System.out.println("You sent a message of level " + level);
                    set_uncertainty(level);
                    move = 1;
                    break;
                case 2:
                    System.out.print("\033[0;35m"); // change color to purple
                    System.out.println("Red does nothing");
                    move = 2;
                    break;
                default:
                    System.out.print("\033[0;35m"); // change color to purple
                    System.out.println("Invalid choice, try again");
                    break;

            }
        }
        System.out.print("\033[0m"); // reset color
        return move;
    }

    private int get_next_move_agent(Game game) {
        return 0;
    }

    private void set_uncertainty(int msg_level) {
        uncertainty = 1.0 - (msg_level / 2.5);
    }
}
