public interface Player {
    public boolean isAgent = false;
    public double uncertainty = 0.0;

    public int get_next_move(Game game);
}
