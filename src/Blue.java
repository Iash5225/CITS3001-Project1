public class Blue extends GameObject {

    public double energy;
    public double energy_cost;

    public Blue(double energy, double energy_cost) {
        super();
        this.energy = energy;
        this.energy_cost = 8.0;
    }

    public double getEnergy() {
        return energy;
    }

    public void LoseEnergy(double cost) {
        this.energy = getEnergy() - cost;
    }

    public boolean LostALLEnergy() {
        return getEnergy() <= 0;
    }
}
