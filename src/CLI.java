import java.util.Scanner;
import java.util.Vector;
import java.util.stream.Collectors;
import java.util.*;

public class CLI {
    Scanner scanner;

    // constructor
    public CLI() {
        // clear the screen
        // System.out.print("\033[H\033[2J");
        scanner = new Scanner(System.in);

    }

    public void print_info(String info) {
        System.out.print("\033[0m");
        System.out.println(info);
    }

    public void print_info(String info, String color) {
        System.out.print("[INFO] ");
        switch (color) {
            case "black":
                System.out.print("\033[0;30m");
                break;
            case "red":
                System.out.print("\033[0;31m");
                break;
            case "green":
                System.out.print("\033[0;32m");
                break;
            case "yellow":
                System.out.print("\033[0;33m");
                break;
            case "blue":
                System.out.print("\033[0;34m");
                break;
            case "purple":
                System.out.print("\033[0;35m");
                break;
            case "cyan":
                System.out.print("\033[0;36m");
                break;
            case "white":
                System.out.print("\033[0;37m");
                break;
            default:
                break;
        }
        System.out.println(info);
        System.out.print("\033[0m");
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

    public int menu(String header, String[] options, String prompt) {
        while (true) {
            System.out.println("=========================================");
            System.out.println(header);
            for (int i = 0; i < options.length; i++) {
                System.out.println(i + ": " + options[i]);
            }
            System.out.println("=========================================");
            System.out.print(prompt);
            try {
                int choice = Integer.parseInt(scanner.nextLine());
                if (choice >= 0 && choice < options.length) {
                    return choice;
                }
            } catch (NumberFormatException e) {
                // do nothing
            }
            System.out.println("Invalid choice. Try again.");
        }
    }

    public int get_int_from_user(String prompt, int min, int max) {
        while (true) {
            System.out.print(prompt + " (" + min + "-" + max + "): ");
            try {
                int choice = Integer.parseInt(scanner.nextLine());
                if (choice >= min && choice <= max) {
                    return choice;
                } else {
                    System.out.println("The number must be between " + min + " and " + max);
                }
            } catch (NumberFormatException e) {
                System.out.println("That's not a number. Try again.");
            }
        }
    }

    public double get_double_from_user(String prompt, double min, double max) {
        while (true) {
            System.out.print(prompt + " (" + min + "-" + max + "): ");
            try {
                double choice = Double.parseDouble(scanner.nextLine());
                if (choice >= min && choice <= max) {
                    return choice;
                } else {
                    System.out.println("The number must be between " + min + " and " + max);
                }
            } catch (NumberFormatException e) {
                System.out.println("That's not a number. Try again.");
            }
        }
    }

    public boolean get_boolean_from_user(String prompt) {
        while (true) {
            System.out.print(prompt + " (y/n): ");
            String choice = scanner.nextLine();
            if (choice.equals("y")) {
                return true;
            } else if (choice.equals("n")) {
                return false;
            } else {
                System.out.println("Invalid choice. Try again.");
            }
        }
    }

    /**
     * Prints the greens in the game.
     * for debugging purposes
     * should not be called by players
     */
    public void display_greens(Vector<Green> greens) {
        // set color to green
        System.out.print("\033[0;32m");
        System.out.println("Greens:");
        // reset color
        System.out.print("\033[0m");
        // column names
        System.out.println("id | uncertainty | willVote | followsRed | [friends]");
        System.out.println("----------------------------------------------------");
        for (Green g : greens) {
            g.print();
        }
        System.out.println();
    }

    public void debug_menu(GameBoard board) {
        String[] options = {
                "Print green info",
                "Plot green uncertainty distribution"
        };
        int choice = menu(
                "Debug menu:",
                options,
                "Enter your choice: ");
        switch (choice) {
            case 0:
                display_greens(board.greens);
                break;
            case 1:
                plot_green_uncertainty_distribution(board, 10);
                break;
            default:
                System.out.println("Very Invalid choice");
                break;
        }
    }

    public void print_game_info_for_players(GameBoard board) {
        // print a bar chart of the number of greens that will vote
        int n_voters = board.get_n_voters();
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

        System.out.println("=========================================");
        print_info("Number of Grey Agents: " + board.greys.size(), "white");
        print_info("Blue has " + board.blue_energy + " energy left", "blue");
        System.out.println("=========================================");

    }

    /**
     * Plots the green uncertainty distribution
     * 
     * @param n_intervals the number of intervals to use
     * @param board       the game board
     */
    public void plot_green_uncertainty_distribution(GameBoard board, int n_intervals) {
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

    /**
     * Prints a bar chart of the frequency of moves made by the agent
     * 
     * @param game
     */
    public void print_move_distribution(Game game) {
        int[] red_moves = game.red_moves_played;
        int[] red_frequency = new int[7];
        int[] blue_moves = game.blue_moves_played;
        int[] blue_frequency = new int[7];

        List<Integer> Red_asList = Arrays.stream(red_moves).boxed().collect(Collectors.toList());
        List<Integer> Blue_asList = Arrays.stream(blue_moves).boxed().collect(Collectors.toList());

        for (int i = 0; i < 7; i++) {
            int r_frequency = Collections.frequency(Red_asList, i);
            int b_frequency = Collections.frequency(Blue_asList, i);
            red_frequency[i] = r_frequency;
            blue_frequency[i] = b_frequency;
        }
        System.out.print("\033[0;31m");
        System.out.println("RED distribution:");
        System.out.print("\033[0m");
        for (int i = 0; i < red_frequency.length-1; i++) {
            String range = "Level " + String.format("%d", i) ;
            if(i==0){
                range = "Do nothing " + String.format("%d", i);
            }            
            //red histogram
            print_bar(range, "\033[31m", red_frequency[i]);
        }
        System.out.println("\n");
        System.out.print("\033[0;34m");
        System.out.println("BLUE distribution:");
        System.out.print("\033[0m");
        for (int i = 0; i < blue_frequency.length; i++) {
            String range = "Level " + String.format("%d", i);
            if (i == 0) {
                range = "Do nothing " + String.format("%d", i);
            }
            if (i == 6) {
                range = "Grey agents " + String.format("%d", i);
            }
            // blue histogram
            print_bar(range, "\033[34m", blue_frequency[i]);
        }
    }

    /**
     * Print round info
     * 
     * @param board
     * @param round
     */
    public void print_round_info(GameBoard board, int round) {
        System.out.println("=========================================");
        print_info("Round " + round, "green");
        print_game_info_for_players(board);
        System.out.println("=========================================");
    }

    /**
     * Prints game over
     * 
     * @param board
     */
    public void print_game_over(GameBoard board) {
        System.out.println("=========================================");
        print_info("Game Over", "green");
        print_game_info_for_players(board);
        System.out.println("=========================================");
    }

    /**
     * print game results
     * 
     * @param board
     */
    public void print_game_result(GameBoard board) {
        System.out.println("=========================================");
        print_info("Game Result", "green");
        int n_voters = board.get_n_voters();
        int n_non_voters = board.greens.size() - n_voters;
        if (n_voters > n_non_voters) {
            print_info("Blue wins!" + n_voters + " - " + n_non_voters, "blue");
        } else {
            print_info("Red wins!" + n_non_voters + " - " + n_voters, "red");
        }
        System.out.println("=========================================");
    }

    public int get_red_move(Boolean[] options) {
        Vector<Integer> valid_options = new Vector<Integer>();
        for (int i = 0; i < options.length; i++) {
            if (options[i]) {
                valid_options.add(i);
            }
        }
        String[] option_strings = new String[valid_options.size()];
        option_strings[0] = "Do nothing";
        for (int i = 1; i < options.length; i++) {
            option_strings[i] = "Send message " + i;
        }
        int choice = menu("Red's move:", option_strings, "Enter your choice: ");
        return valid_options.get(choice);
    }

    public int get_blue_move(Boolean[] options) {
        Vector<Integer> valid_options = new Vector<Integer>();
        for (int i = 0; i < options.length; i++) {
            if (options[i]) {
                valid_options.add(i);
            }
        }
        String[] option_strings = new String[valid_options.size()];
        option_strings[0] = "Do nothing";
        for (int i = 1; i < valid_options.size(); i++) {
            if (valid_options.get(i) < options.length - 1) {
                option_strings[i] = "Send message " + valid_options.get(i);
            } else {
                option_strings[i] = "Release Grey";
            }
        }

        int choice = menu("Blue's move:", option_strings, "Enter your choice: ");
        return valid_options.get(choice);
    }

    public void print_statistics(int red_wins, int blue_wins, int draws, double avg_unfollows) {
        System.out.println("=========================================");
        print_info("Statistics", "green");
        print_info("Red wins: " + red_wins, "red");
        print_info("Blue wins: " + blue_wins, "blue");
        print_info("Draws: " + draws, "white");
        print_info("Average number of unfollows: " + avg_unfollows, "white");
        System.out.println("=========================================");
    }

    public void print_welcome() {
        System.out.println("=========================================");
        System.out.println("Welcome to the Disinformation Game");
        System.out.println("By: Jean-Pierre le Breton & Iash Bashir");
        System.out.println("=========================================");
    }

    public Configuration get_config() {
        int mode = menu("Choose a game mode.", new String[] { "Small", "Medium", "Large", "Custom" },
                "Please choose a game mode.");
        if (mode == 3) {
            // Custom
            int n_rounds = get_int_from_user("How many rounds?", 5, 100);
            int n_greens = get_int_from_user("How many greens?", 5, 100);
            double prob_edge = get_double_from_user("What is the probability of an edge in the green network?", 0,
                    1);
            int n_grey = get_int_from_user("How many greys agents should there be?", 0, 5);
            int n_spies = get_int_from_user("How many of them should be spies?", 0, n_grey);
            double u_lb = get_double_from_user("What is the lower bound of the starting uncertainty?", -1, 1);
            double u_ub = get_double_from_user(
                    "What is the upper bound of the starting uncertainty?", u_lb, 1);
            double prob_vote = get_double_from_user("What is the proportion of the population that votes?", 0, 1);
            double blue_starting_energy = get_int_from_user("How much energy should blue start with?", 0, 100);
            int max_message_level = get_int_from_user("What is the maximum message level?", 2, 10);
            return new Configuration(n_greens, prob_edge, n_grey, n_spies, u_lb, u_ub, prob_vote, blue_starting_energy,
                    max_message_level, n_rounds);
        } else {
            return new Configuration(mode);
        }
    }
}
