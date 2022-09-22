import java.util.*;

public class Game {
    public Vector<Green> greens;

    public Game(int n_green, double prob_edge, int n_grey, double prob_spy, double uncertainty_lb, double uncertainty_ub, double percentage_vote) {
        greens = new Vector<Green>();
        for (int i = 0; i < n_green; i++) {
            double uncertainty = uncertainty_lb + Math.random() * (uncertainty_ub - uncertainty_lb);
            boolean willVote = Math.random() < percentage_vote;
            greens.add(new Green(uncertainty, willVote, true));
        }
        for (Green g : greens) {
            for (Green g2 : greens) {
                if (g != g2 && Math.random() < prob_edge) {
                    g.friends.add(g2);
                }
            }
        }
    }

    public void update() {
        for (Green g : greens) {
            g.calcNewUncertainty();
        }
        for (Green g : greens) {
            g.update();
        }
    }

    public int getVoteCount() {
        int count = 0;
        for (Green g : greens) {
            if (g.willVote) {
                count++;
            }
        }
        return count;
    }

    public static void main(String[] args) {
        Game game = new Game(100, 0.1, 0, 0, 0, 0.9, 0.2);
        System.out.println("Initial vote count: " + game.getVoteCount());
        System.out.println("Initial Green Graph:");
        for (Green g : game.greens) {
            System.out.print(g.id);
            System.out.print(": [");
            for (Green g2 : g.friends) {
                System.out.print(g2.id);
                System.out.print(" ");
            }
            System.out.println("]");
        }
        System.out.println("Running simulation...");
        for (int i = 0; i < 50; i++) {
            game.update();
            System.out.println(game.getVoteCount());
        }
    }

}
