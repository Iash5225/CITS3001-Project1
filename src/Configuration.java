
public class Configuration {
    int n_greens;
    double prob_edge;
    int n_greys;
    int n_spies;
    double u_lb;
    double u_ub;
    double prob_vote;
    double blue_starting_energy;
    int max_message_level;
    int n_rounds;

    public Configuration(int n_greens, double prob_edge, int n_greys, int n_spies, double u_lb, double u_ub,
            double prob_vote, double blue_starting_energy, int max_message_level, int n_rounds) {
        this.n_greens = n_greens;
        this.prob_edge = prob_edge;
        this.n_greys = n_greys;
        this.n_spies = n_spies;
        this.u_lb = u_lb;
        this.u_ub = u_ub;
        this.prob_vote = prob_vote;
        this.blue_starting_energy = blue_starting_energy;
        this.max_message_level = max_message_level;
        this.n_rounds = n_rounds;
    }

    public Configuration(int mode) {
        switch (mode) {
            case 0:
                this.n_greens = 10;
                this.prob_edge = 0.3;
                this.n_greys = 3;
                this.n_spies = 1;
                this.u_lb = -0.5;
                this.u_ub = 0.5;
                this.prob_vote = 0.5;
                this.blue_starting_energy = 25;
                this.max_message_level = 5;
                this.n_rounds = 5;
                break;
            case 1:
                this.n_greens = 25;
                this.prob_edge = 0.2;
                this.n_greys = 3;
                this.n_spies = 1;
                this.u_lb = -0.5;
                this.u_ub = 0.5;
                this.prob_vote = 0.5;
                this.blue_starting_energy = 25;
                this.max_message_level = 5;
                this.n_rounds = 10;
                break;
            case 2:
                this.n_greens = 50;
                this.prob_edge = 0.1;
                this.n_greys = 3;
                this.n_spies = 1;
                this.u_lb = -0.5;
                this.u_ub = 0.5;
                this.prob_vote = 0.5;
                this.blue_starting_energy = 25;
                this.max_message_level = 5;
                this.n_rounds = 20;
                break;
            default:
                this.n_greens = 10;
                this.prob_edge = 0.3;
                this.n_greys = 3;
                this.n_spies = 1;
                this.u_lb = -0.5;
                this.u_ub = 0.5;
                this.prob_vote = 0.5;
                this.blue_starting_energy = 25;
                this.max_message_level = 5;
                this.n_rounds = 10;
                break;
        }
    }
}
