import java.util.*;

public class Bayesian extends GameObject {
    // TODO Auto-generated constructor stub

    public Game game;
    public double chance_of_winning;
    double[][] state_matrix;
    static int number_of_levels = 6;
    static int number_of_rounds = 4;
    double total_number_of_combinations = Math.pow(6.0, (double) number_of_rounds);

    public Bayesian() {
        super();
    }

    public void initialise() {
        state_matrix = new double[number_of_levels][number_of_rounds];
        for (int i = 0; i < number_of_levels; i++) {
            for (int j = 0; j < number_of_rounds; j++) {
                state_matrix[i][j] = 0.5;
            }
        }
    }

    public void print() {
        for (int i = 0; i < number_of_levels; i++) {
            for (int j = 0; j < number_of_rounds; j++) {
                System.out.print(state_matrix[i][j] + " ");
            }
            System.out.println();
        }
    }

    /**
     * 
     */
    public String generate_red_turns(HashMap<String, String> ht) {
        String red_turns = "";
        for (int i = 0; i < number_of_rounds; i++) {
            ht.put(String.valueOf(i), "0");
            for (int j = 0; j < number_of_levels; j++) {
                red_turns = red_turns + String.valueOf(i);
            }
        }
        return red_turns;
    }

    public void update_hashtable(HashMap<String, String> ht) {
        for (int i = 0; i < total_number_of_combinations; i++) {
            ht.put(String.valueOf(i), "0");
        }
    }

    public void generate_string(HashMap<String, String> ht, String buffer, int hastable_index) {
        for (int i = 0; i < 6; i++) {
            String index = String.valueOf(i);
            ht.put(index, "0");
        }
        hastable_index++;
    }

    /**
     * Evaluate the blue turns score for the game
     * Higher the score
     * 
     * @param blue_turns
     * @return
     */
    public static double score(int[] blue_turns) {
        double score = 0;
        for (int i = 0; i < blue_turns.length; i++) {
            int turn = blue_turns[i];
            if (turn == 5) {
                score = score + 1;
            } else if (turn == 4) {
                score = score + 0.75;
            } else if (turn == 3) {
                score = score + 0.5;
            } else if (turn == 2) {
                score = score + 0.25;
            } else if (turn == 1) {
                score = score + 0.125;
            } else if (turn == 0) {
                score = score + 0.0625;
            }
        }
        return score;
    }

    public static int blue_move_agent(double score, int n_rounds) {
        if (score <= 0.0625) {
            return 5;
        }
        if (score <= 0.0625 + 1) {
            return 4;
        }
        if (score <= 0.0625 + 2) {
            return 3;
        }
        if (score <= 0.0625 + 3) {
            return 2;
        }
        if (score <= 0.0625 + 4) {
            return 1;
        }
        return 0;

    }

    public static void main(String[] args) {
        // TODO Auto-generated method stub
        //Bayesian bayesian = new Bayesian();
        HashMap<String, String> Red_Turns_List = new HashMap<String, String>();
        // bayesian.generate_red_turns();
        // bayesian.update_hashtable(Red_Turns_List);

        Red_Turns_List.put("0", "0");
        Red_Turns_List.put("1", "1");
        Red_Turns_List.put("2", "2");
        Red_Turns_List.put("3", "3");
        Red_Turns_List.put("4", "4");
        Red_Turns_List.put("5", "5");

        int[] blue_moves = new int[5];
        int[] red_moves = new int[5];
        boolean visual = false;

        int red_wins = 0;
        int blue_wins = 0;
        int draws = 0;

       // int n_unfollows = 0;
        for (int i = 0; i < (visual ? 1 : 1000); i++) {
            // create a new game
            Game game = new Game(100, 0.6, 3, 1, -1, 1, 0.5);
            game.blue_turns = blue_moves;
            game.red_turns = red_moves;
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
                  //  n_unfollows += n_followers_a - n_followers_b;
                    // game.green_turn();
                    game.blue_turn(blue);
                    game.green_turn();
                    game.current_round++;

                }
            }

            int score = game.who_won();
            String red_moves_after_game = Arrays.toString(game.red_turns).replaceAll("\\[|\\]|,|\\s", "");
            // String blue_moves_after_game = Arrays.toString(game.blue_turns);
            String blue_moves_after_game = blue_move_agent(score(game.blue_turns), game.n_rounds) + "";
            System.out.println(blue_moves_after_game);
            //Syst(blue_moves_after_game);
            if (score < 0) {
                red_wins++;
                //Red_Turns_List.put(red_moves_after_game, String.valueOf(red_wins));
                Red_Turns_List.put(red_moves_after_game, blue_moves_after_game);
            } else if (score > 0) {
                blue_wins++;
            } else {
                draws++;
            }
        }

        System.out.println("Red wins: " + red_wins);
        System.out.println("Blue wins: " + blue_wins);
        System.out.println("Draws: " + draws);

        // System.out.println("Average number of unfollows: " + (double) n_unfollows /
        // 1000);
    }

}