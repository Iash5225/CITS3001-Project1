package src;

public class GameObject {
    private static int count = 0;

    public int id;

    public GameObject() {
        id = count++;
    }
}
