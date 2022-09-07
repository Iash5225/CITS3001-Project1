public class Grey {
    public int id;
    public boolean isSpy;

    public Grey(int id, boolean isSpy){
        this.id = id;
        this.isSpy = isSpy;
    }
    public int getId() {
        return id;
    }

    public boolean SpyorNot() {
        return isSpy;
    }
}
