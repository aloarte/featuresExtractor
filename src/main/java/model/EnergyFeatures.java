package model;

/**
 * Features related with the energy of the audio
 */
public class EnergyFeatures {

    /**
     * The sum of squares of the signal values, normalized by the respective frame length.
     */
    private double energy;

    /**
     * The entropy of sub-frames' normalized energies. It can be interpreted as a measure of abrupt changes.
     */
    private double entropyOfEnergy;

    public EnergyFeatures(double energy, double entropyOfEnergy) {
        this.energy = energy;
        this.entropyOfEnergy = entropyOfEnergy;
    }

    public EnergyFeatures() {

    }

    public double getEnergy() {
        return energy;
    }

    public void setEnergy(double energy) {
        this.energy = energy;
    }

    public double getEntropyOfEnergy() {
        return entropyOfEnergy;
    }

    public void setEntropyOfEnergy(double entropyOfEnergy) {
        this.entropyOfEnergy = entropyOfEnergy;
    }

    @Override
    public String toString() {
        return "----- Energy Features: ------\n " +
                "- Energy: " + getEnergy() + "\n" +
                "- Entropy: " + getEntropyOfEnergy() + "\n";
    }
}
