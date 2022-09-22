public class Red extends GameObject {
    public int levelofMessage;

    public Red (){
        super();
        levelofMessage=0;
    }

    public void setLevelofMessage(int levelofMessage) throws Exception {

        this.levelofMessage = levelofMessage;
        switch(levelofMessage){
            case 1:

                break;
            case 2:
                break;
            case 3:
                break;
            case 4:
                break;
            case 5:
                break;
            default:
                throw new Exception("level of messaging is outside the bounds of 0->5");
        }
    }
}
