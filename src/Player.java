import java.util.Vector;

public class Player {
    public boolean is_agent = false;

    public Player(boolean is_agent) {
        this.is_agent = is_agent;
    }

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
