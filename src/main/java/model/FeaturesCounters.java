package model;

public class FeaturesCounters {

    /**
     * Number of Time Spectral features
     */
    private int timeSpectralFeatures;

    /**
     * Number of Harmonic features
     */
    private int harmonicFeatures;

    /**
     * Number of
     */
    private int nceps;

    /**
     * Number of Chroma features
     */
    private int chromaFeatures;

    /**
     * Number of total features
     */
    private int totalFeatures;

    /**
     * Build the FeaturesCounters
     *
     * @param timeSpectralFeatures Number of time spectral features
     * @param harmonicFeatures     Number of harmonic features
     * @param nceps                Number of nceps
     * @param chromaFeatures       Number of chroma features
     */
    public FeaturesCounters(int timeSpectralFeatures, int harmonicFeatures, int nceps, int chromaFeatures) {
        this.timeSpectralFeatures = timeSpectralFeatures;
        this.harmonicFeatures = harmonicFeatures;
        this.nceps = nceps;
        this.chromaFeatures = chromaFeatures;
        //Calculate the number of total features
        this.totalFeatures = timeSpectralFeatures + harmonicFeatures + nceps + chromaFeatures;
    }

    public int getTimeSpectralFeatures() {
        return timeSpectralFeatures;
    }

    public int getHarmonicFeatures() {
        return harmonicFeatures;
    }

    public int getNceps() {
        return nceps;
    }

    public int getChromaFeatures() {
        return chromaFeatures;
    }

    public int getTotalFeatures() {
        return totalFeatures;
    }
}
