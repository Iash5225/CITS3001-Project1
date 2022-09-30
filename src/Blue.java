//package src;

public class Blue extends GameObject {

    public double energy;

    public Blue (double energy){
        super();
        this.energy = energy;
    }

    public double getEnergy() {
        return energy;
    }

    public void LoseEnergy(double cost){
        this.energy = getEnergy()-cost;
    }
    public boolean LostALLEnergy(){
        return getEnergy() == 0;
    }
}
