import java.util.*;

public class Game {
    public GameBoard board;
    public Scanner sc;

    public int n_rounds = Config.N_ROUNDS;
    public int current_round;
    public boolean is_blues_turn;

    /**
     * Creates a new game with the given parameters.
     * 
     * @param board         The game board.
     * @param n_rounds      The number of rounds.
     * @param is_blues_turn Whether it is blue's turn.
     * @param red           The red player.
     * @param blue          The blue player.
     *
     */
    public Game(GameBoard board, int n_rounds, boolean is_blues_turn, RedPlayer red_player, BluePlayer blue_player) {
        this.board = board;

        // set current round to 0
        current_round = 0;
        is_blues_turn = false;

        // set scanner
        sc = new Scanner(System.in);
    }

    // ================================ INFORMATION ================================
    /**
     * Prints the greens in the game.
     * for debugging purposes
     * should not be called by players
     */
    public void printGreens() {
        // set color to green
        System.out.print("\033[0;32m");
        System.out.println("Greens:");
        // reset color
        System.out.print("\033[0m");
        // column names
        System.out.println("id | uncertainty | willVote | followsRed | [friends]");
        System.out.println("----------------------------------------------------");
        for (Green g : board.greens) {
            g.print();
        }
        System.out.println();

    }

    /**
     * Returns the number of green that will vote
     */
    public int number_of_voting_greens() {
        int count = 0;
        for (Green g : board.greens) {
            if (g.willVote) {
                count++;
            }
        }
        return count;
    }

    /**
     * Print the bar chart of the uncertainties
     */
    private void print_bar(String label, String color, int n) {
        // bar unicode
        // https://www.compart.com/en/unicode/block/U+2580
        System.out.printf("%-20s:", label);
        System.out.print(color);
        for (int i = 0; i < n; i++) {
            System.out.print("\u2584");
        }
        System.out.println("\033[0m");
    }

    public void print_game_info_for_players() {
        // print a bar chart of the number of greens that will vote
        int n_voters = number_of_voting_greens();
        int n_non_voters = board.greens.size() - n_voters;
        System.out.println("=========================================");

        print_bar("Voters", "\033[34m", n_voters);
        print_bar("Non-voters", "\033[31m", n_non_voters);

        System.out.println("=========================================");

        int n_red_followers = 0;
        int n_non_followers = 0;
        for (Green green : board.greens) {
            if (green.followsRed) {
                n_red_followers++;
            } else {
                n_non_followers++;
            }
        }
        print_bar("Red followers", "\033[31m", n_red_followers);
        print_bar("Non followers", "\033[34m", n_non_followers);

    }

    public void print_debug_menu() {
        System.out.println("=========================================");
        System.out.println("Debug menu:");
        System.out.println("1. Print green info");
        System.out.println("2. Plot green uncertainty distribution");
        System.out.println("=========================================");

        while (true) {
            System.out.print("Enter your choice: ");
            int choice = sc.nextInt();
            boolean valid_choice = true;
            switch (choice) {
                case 1:
                    printGreens();
                    break;
                case 2:
                    plot_green_uncertainty_distribution(10);
                    break;
                default:
                    System.out.println("Invalid choice");
                    valid_choice = false;
                    break;
            }
            if (valid_choice) {
                break;
            }
        }

    }

    /**
     * Plots the green uncertainty distribution
     * 
     * @param n_intervals
     */
    public void plot_green_uncertainty_distribution(int n_intervals) {
        System.out.println("Green uncertainty distribution:");
        int[] bins = new int[n_intervals];
        for (Green green : board.greens) {
            int bin = (int) ((green.uncertainty + 1) * (double) n_intervals / 2);
            bins[bin]++;
        }
        for (int i = 0; i < bins.length; i++) {
            Double lb = -1.0 + i * 2.0 / (double) n_intervals;
            String range = String.format("(%.1f)", lb) + " - "
                    + String.format("(%.1f)", lb + 2.0 / (double) n_intervals);
            print_bar(range, "\033[34m", bins[i]);
        }

    }

    // ================================ GAME LOGIC ================================
    /**
     * Red's turn.
     */
    public void red_turn(RedPlayer p) {
        // set color to red
        if (!p.isAgent) {
            System.out.print("\033[0;31m");
            System.out.println("Red's turn");
            // reset color
            System.out.print("\033[0m");

            // print game info for players
            print_game_info_for_players();
        }

        // get red's action
        int action = p.get_next_move(this);
        switch (action) {
            case 1:
                for (Green g : board.greens) {
                    if (!g.followsRed)
                        continue;
                    g.influence(p.uncertainty, false);
                    g.unfollow(p.uncertainty);
                }
                break;
            case 2:
                break;
            default:
                System.out.println("Invalid action");
                System.exit(action);
        }
    }

    /**
     * Blue's turn.
     */
    public void blue_turn(BluePlayer p) {

        if (!p.isAgent) {
            // set color to blue
            System.out.print("\033[0;34m");
            System.out.println("Blue's turn");
            // reset color
            System.out.print("\033[0m");

            // print game info for players
            print_game_info_for_players();
        }

        // get blue's action
        int action = p.get_next_move(this);
        switch (action) {
            case 1:
                for (Green g : board.greens) {
                    g.influence(p.uncertainty, true);
                }
                Double cost = (1.0 - p.uncertainty) * 5 / 2;
                board.blue_energy -= cost;
                break;
            case 2:
                break;
            case 3:
                if (board.greys.size() > 0) {
                    release_grey();
                } else {
                    System.out.println("No grey to release");
                }
                break;
            default:
                System.out.println("Invalid action");
                System.exit(action);
        }
    }

    public void green_turn() {
        for (Green g : board.greens) {
            g.update();
        }
        for (Green g1 : board.greens) {
            for (Green g2 : g1.friends) {
                g2.influence(g1.uncertainty, g1.willVote);
            }
        }
    }

    private void release_grey() {
        Grey grey_agent = board.greys.remove((int) Math.random() * board.greys.size());
        if (grey_agent.isSpy) {
            System.out.println("A RED spy has been released");
            for (Green g : board.greens) {
                g.influence(-1.0, false);
            }
            System.out.println("Blue sucks #5");
        } else {
            System.out.println("A non-spy has been released");
            for (Green g : board.greens) {
                g.influence(-1.0, true);
            }
            System.out.println("Red sucks #5");
        }
    }

    public int[] get_valid_moves(String player) {
        switch (player) {
            case "red":
                return new int[] { 1, 2, 3, 4, 5 };
            case "blue":
                int max_level = (int) Math.floor(board.blue_energy / 5);
                int[] valid_moves = new int[max_level + 1];
                for (int i = 0; i < max_level + 1; i++) {
                    valid_moves[i] = i;
                }
                return valid_moves;
            default:
                System.out.println("Invalid player");
                System.exit(1);
        }
        return null;
    }

    public int who_won() {
        int red_votes = 0;
        int blue_votes = 0;
        for (Green g : board.greens) {
            if (g.willVote) {
                blue_votes++;
            } else {
                red_votes++;
            }
        }
        return blue_votes - red_votes;
    }

}
