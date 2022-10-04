import java.util.Vector;

public class Green extends GameObject {
    double uncertainty;
    double du;
    boolean willVote;
    boolean followsRed;
    Vector<Green> friends;

    public Green(double uncertainty, boolean willVote, boolean followsRed) {
        super();
        this.uncertainty = uncertainty;
        this.willVote = willVote;
        this.followsRed = followsRed;
        friends = new Vector<Green>();
    }

    public void print() {
        String u_rounded = String.format("%.2f", uncertainty);
        System.out.print(id + " " + u_rounded + " " + willVote + " " + followsRed);
        System.out.print(" [ ");
        for (Green g : friends) {
            System.out.print(g.id + " ");
        }
        System.out.println("]");
    }

    public void calcNewUncertainty() {
        double sum = 0;
        for (Green g : friends) {
            sum += g.uncertainty;
        }
        double avg = sum / friends.size();
        du = (avg - uncertainty) / 2;
    }

    public void green_interraction(){
        for (Green FRIEND : friends) {
            double leader_uncertainty = uncertainty;

            //if leader is more certain that they are going to vote, leader infuences friend
            if(leader_uncertainty< FRIEND.uncertainty){
                if(willVote == true){
                    FRIEND.willVote = true;
                }
                else{
                    FRIEND.willVote = false;
                }
            }
            
        }
    }

    public void update() {
        uncertainty += du;
        du = 0;
        if (Math.random() < uncertainty) {
            willVote = !willVote;
        }
    }
}
