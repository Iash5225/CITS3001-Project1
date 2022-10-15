import java.util.Vector;

public class Green extends GameObject {
    double uncertainty;
    double du;
    boolean willVote;
    boolean followsRed;
    Vector<Green> friends;

    public Green(double uncertainty, boolean willVote, boolean followsRed) {
        super();
        this.uncertainty = uncertainty;
        this.willVote = willVote;
        this.followsRed = followsRed;
        friends = new Vector<Green>();
    }

    public void print() {
        String u_rounded = String.format("%.2f", uncertainty);
        System.out.printf("%-2d | %-11s | %-8s | %-10s", id, u_rounded, willVote, followsRed);
        System.out.print(" | [ ");
        for (Green g : friends) {
            System.out.print(g.id + " ");
        }
        System.out.println("]");
    }

    /*
     * Influence green's uncertainty based on the uncertainty and opinion of the
     * message
     */
    public void influence(Double u, Boolean v) {
        // if they are more uncertain
        if (uncertainty > u) {
            // if they share the same opinion
            if (willVote == v) {
                du -= (uncertainty - u) / 5;
            } else {
                du += (uncertainty - u) / 5;
            }
        }
    }

    /**
     * Checks if a green will unfollow red based on red's uncertainty
     * 
     * @param uncertainty
     */
    public int unfollow(double u) {
        if (willVote) {
            // remap u to [0, 1]
            double u_0_1 = (u + 1) / 2;
            double gu_0_1 = (uncertainty + 1) / 2;

            // the lower the uncertainty, the more likely the green will unfollow
            // the lower the green's uncertainty, the more likely the green will unfollow
            double p = u_0_1 * gu_0_1;

            if (Math.random() > Math.pow(p, 2)) {
                followsRed = false;
                return 1;
            }
        }
        return 0;
    }

    /*
     * Updates the uncertainty of the green agent and resets the du value to 0
     * also triggers the green to change their vote status if they are uncertain
     * enough
     */
    public int update() {
        uncertainty += du;
        du = 0;
        uncertainty = Math.min(uncertainty, 0.999999);
        uncertainty = Math.max(uncertainty, -1);

        if (Math.random() < uncertainty) {
            willVote = !willVote;
            uncertainty = 0;
            return 1;
        }
        return 0;
    }
}
