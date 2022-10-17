import java.util.Vector;

public class Player {
    public boolean is_agent = false;

    public Player(boolean is_agent) {
        this.is_agent = is_agent;
    }

    /**
     * Returns the action the player wants to take
     * @param options the moves available to the player
     * @return random move selected from the available options
     */
    public int get_next_move(Boolean[] options) {
        Vector<Integer> valid_options = new Vector<Integer>();
        for (int i = 0; i < options.length; i++) {
            if (options[i]) {
                valid_options.add(i);
            }
        }
        return valid_options.get((int) (Math.random() * valid_options.size()));
    }
}
