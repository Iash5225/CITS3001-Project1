public class Red extends GameObject {
    public int levelofMessage;

    public Red() {
        super();
        levelofMessage = 0;
    }

    public void setLevelofMessage(int levelofMessage) throws Exception {

        this.levelofMessage = levelofMessage;
        switch (levelofMessage) {
            case 1:
                System.out.println("Red is sending level 1 message");
                break;
            case 2:
                System.out.println("Red is sending level 2 message");
                break;
            case 3:
                System.out.println("Red is sending level 3 message");
                break;
            case 4:
                System.out.println("Red is sending level 4 message");
                break;
            case 5:
                System.out.println("Red is sending level 5 message");
                break;
            default:
                throw new Exception("Invalid level of message");
        }
    }
}
