public class Game {
    public int n_green;
    public double prob_edge;
    public int n_grey;
    public double prob_spy;
    public double uncertainty_lb;
    public double uncertainty_ub;
    public double percentage_vote;

    public Game(int n_green, double prob_edge, int n_grey, double prob_spy, double uncertainty_lb, double uncertainty_ub, double percentage_vote) {
        this.n_green = n_green;
        this.prob_edge = prob_edge;
        this.n_grey = n_grey;
        this.prob_spy = prob_spy;
        this.uncertainty_lb = uncertainty_lb;
        this.uncertainty_ub = uncertainty_ub;
        this.percentage_vote = percentage_vote;
    }

    public int getN_green() {
        return n_green;
    }

    public double getProb_edge() {
        return prob_edge;
    }

    public int getN_grey() {
        return n_grey;
    }

    public double getProb_spy() {
        return prob_spy;
    }

    public double getUncertainty_lb() {
        return uncertainty_lb;
    }

    public double getUncertainty_ub() {
        return uncertainty_ub;
    }

    public double getPercentage_vote() {
        return percentage_vote;
    }
}
