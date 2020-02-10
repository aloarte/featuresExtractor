package model;

/**
 * Features related with the energy of the audio
 */
public class EnergyFeatures {

    /**
     * The sum of squares of the signal values, normalized by the respective frame length.
     */
    private float energy;

    /**
     * The entropy of sub-frames' normalized energies. It can be interpreted as a measure of abrupt changes.
     */
    private float entropyOfEnergy;

    public EnergyFeatures() {
        //TODO: check if there is any default behaviour to build this class
    }

    public float getEnergy() {
        return energy;
    }

    public void setEnergy(float energy) {
        this.energy = energy;
    }

    public float getEntropyOfEnergy() {
        return entropyOfEnergy;
    }

    public void setEntropyOfEnergy(float entropyOfEnergy) {
        this.entropyOfEnergy = entropyOfEnergy;
    }
}
