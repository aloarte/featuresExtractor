package model;

import java.io.Serializable;

/**
 * Data class that holds the 71 audio features extracted from one raw audio source
 */
public class AudioFeatures implements Serializable {

    //1
    /**
     * The rate of sign-changes of the signal during the duration of a particular frame.
     */
    public double zeroCrossingRate;

    //2-3
    /**
     * Features related with the energy of the audio
     */
    private EnergyFeatures energyFeatures;

    //4-8
    /**
     * Features related with the audio spectral
     */
    private SpectralFeatures spectralFeatures;

    // 9 -21
    /**
     * Mel Frequency Cepstral Coefficients form a cepstral representation where the frequency bands are not linear but distributed according to the mel-scale.
     */
    private MFCCs mfcCs;

    //22-33, 34
    /**
     * Features related with the chroma values of the audio
     */
    private ChromaFeatures chromaFeatures;


    /**
     * Default constructor
     */
    public AudioFeatures() {
        this.zeroCrossingRate = 0;
        this.energyFeatures = new EnergyFeatures();
        this.spectralFeatures = new SpectralFeatures();
        this.mfcCs = new MFCCs();
        this.chromaFeatures = new ChromaFeatures();
    }

    public double getZeroCrossingRate() {
        return zeroCrossingRate;
    }

    public void setZeroCrossingRate(double zeroCrossingRate) {
        this.zeroCrossingRate = zeroCrossingRate;
    }

    public EnergyFeatures getEnergyFeatures() {
        return energyFeatures;
    }

    public void setEnergyFeatures(EnergyFeatures energyFeatures) {
        this.energyFeatures = energyFeatures;
    }

    public SpectralFeatures getSpectralFeatures() {
        return spectralFeatures;
    }

    public void setSpectralFeatures(SpectralFeatures spectralFeatures) {
        this.spectralFeatures = spectralFeatures;
    }

    public MFCCs getMfcCs() {
        return mfcCs;
    }

    public void setMfcCs(MFCCs mfcCs) {
        this.mfcCs = mfcCs;
    }

    public ChromaFeatures getChromaFeatures() {
        return chromaFeatures;
    }

    public void setChromaFeatures(ChromaFeatures chromaFeatures) {
        this.chromaFeatures = chromaFeatures;
    }
}
