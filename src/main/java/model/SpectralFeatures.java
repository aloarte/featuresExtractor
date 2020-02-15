package model;

/**
 * Features related with the audio spectral
 */
public class SpectralFeatures {

    /**
     * The center of gravity of the spectrum.
     */
    private double spectralCentroid;

    /**
     * The second central moment of the spectrum.
     */
    private double spectralSpread;

    /**
     * Entropy of the normalized spectral energies for a set of sub-frames.
     */
    private double spectralEntropy;

    /**
     * The squared difference between the normalized magnitudes of the spectra of the two successive frames.
     */
    private double spectralFlux;

    /**
     * The frequency below which 90% of the magnitude distribution of the spectrum is concentrated
     */
    private double spectralRolloff;

    public SpectralFeatures(double spectralCentroid, double spectralSpread, double spectralEntropy, double spectralFlux, double spectralRolloff) {
        this.spectralCentroid = spectralCentroid;
        this.spectralSpread = spectralSpread;
        this.spectralEntropy = spectralEntropy;
        this.spectralFlux = spectralFlux;
        this.spectralRolloff = spectralRolloff;
    }

    public SpectralFeatures() {

    }

    public double getSpectralCentroid() {
        return spectralCentroid;
    }

    public void setSpectralCentroid(double spectralCentroid) {
        this.spectralCentroid = spectralCentroid;
    }

    public double getSpectralSpread() {
        return spectralSpread;
    }

    public void setSpectralSpread(double spectralSpread) {
        this.spectralSpread = spectralSpread;
    }

    public double getSpectralEntropy() {
        return spectralEntropy;
    }

    public void setSpectralEntropy(double spectralEntropy) {
        this.spectralEntropy = spectralEntropy;
    }

    public double getSpectralFlux() {
        return spectralFlux;
    }

    public void setSpectralFlux(double spectralFlux) {
        this.spectralFlux = spectralFlux;
    }

    public double getSpectralRolloff() {
        return spectralRolloff;
    }

    public void setSpectralRolloff(double spectralRolloff) {
        this.spectralRolloff = spectralRolloff;
    }

    @Override
    public String toString() {
        return "----- Spectral Features ----\n " +
                "- Spectral centroid: " + getSpectralCentroid() + "\n" +
                "- Spectral spread: " + getSpectralSpread() + "\n" +
                "- Spectral entropy: " + getSpectralEntropy() + "\n" +
                "- Spectral flux: " + getSpectralFlux() + "\n" +
                "- Spectral rolloff: " + getSpectralRolloff() + "\n";
    }
}
