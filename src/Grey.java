public class Grey extends GameObject {
    public boolean isSpy;

    public Grey(boolean isSpy) {
        super();
        this.isSpy = isSpy;
    }

    public void print() {
        System.out.printf("%-2d | %-5s", id, isSpy);
    }
}
