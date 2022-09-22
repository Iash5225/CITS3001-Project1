public class Grey extends GameObject {
    public boolean isSpy;

    public Grey(boolean isSpy){
        super();
        this.isSpy = isSpy;
    }

    public boolean SpyorNot() {
        return isSpy;
    }
}
