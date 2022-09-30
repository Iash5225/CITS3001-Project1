public class Grey extends GameObject {
    public boolean isSpy;
    public boolean isAlive;

    public Grey(boolean isSpy) {
        super();
        this.isSpy = isSpy;
        this.isAlive = true;
    }
}
