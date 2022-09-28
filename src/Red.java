package src;

public class Red extends GameObject {
    public int levelofMessage;

    public Red() {
        super();
        levelofMessage = 0;
    }

    public void setLevelofMessage(int levelofMessage) throws Exception {
        this.levelofMessage = levelofMessage; //work on that
        System.out.println("Red is sending a level " + levelofMessage + "message");

    }
}
