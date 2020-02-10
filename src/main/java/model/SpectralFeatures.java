package model;

/**
 * Features related with the audio spectral
 */
public class SpectralFeatures {

    /**
     * The center of gravity of the spectrum.
     */
    private float spectralCentroid;

    /**
     * The second central moment of the spectrum.
     */
    private float spectralSpread;

    /**
     * Entropy of the normalized spectral energies for a set of sub-frames.
     */
    private float spectralEntropy;

    /**
     * The squared difference between the normalized magnitudes of the spectra of the two successive frames.
     */
    private float spectralFlux;

    /**
     * The frequency below which 90% of the magnitude distribution of the spectrum is concentrated
     */
    private float spectralRolloff;

    public SpectralFeatures() {
        //TODO: check if there is any default behaviour to build this class
    }

    public float getSpectralCentroid() {
        return spectralCentroid;
    }

    public void setSpectralCentroid(float spectralCentroid) {
        this.spectralCentroid = spectralCentroid;
    }

    public float getSpectralSpread() {
        return spectralSpread;
    }

    public void setSpectralSpread(float spectralSpread) {
        this.spectralSpread = spectralSpread;
    }

    public float getSpectralEntropy() {
        return spectralEntropy;
    }

    public void setSpectralEntropy(float spectralEntropy) {
        this.spectralEntropy = spectralEntropy;
    }

    public float getSpectralFlux() {
        return spectralFlux;
    }

    public void setSpectralFlux(float spectralFlux) {
        this.spectralFlux = spectralFlux;
    }

    public float getSpectralRolloff() {
        return spectralRolloff;
    }

    public void setSpectralRolloff(float spectralRolloff) {
        this.spectralRolloff = spectralRolloff;
    }
}
