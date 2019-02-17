package data;

import java.io.Serializable;

/**
 * Data class that holds the 71 audio features extracted from one raw audio source
 */
public class AudioFeatures implements Serializable {

    /**
     * Beats per minute
     */
    public float bpm;

    /**
     * ...
     */
    public float bpmconf;

    /**
     * ...
     */
    public float mfccs;

    /**
     * ...
     */
    public float zrc;

    /**
     * ...
     */
    public float spectralFlux;

    /**
     * ...
     */
    public float spectralCentroid;

    /**
     * Full constructor
     * @param bpm       Beats per minute
     * @param bpmconf   ...
     * @param mfccs     ...
     * @param zrc       ...
     * @param spectralFlux  ...
     * @param spectralCentroid  ...
     */
    public AudioFeatures(float bpm, float bpmconf, float mfccs, float zrc, float spectralFlux, float spectralCentroid) {
        this.bpm = bpm;
        this.bpmconf = bpmconf;
        this.mfccs = mfccs;
        this.zrc = zrc;
        this.spectralFlux = spectralFlux;
        this.spectralCentroid = spectralCentroid;
    }

    /**
     * Default constructor
     */
    public AudioFeatures(){

    }

    public float getBpm() {
        return bpm;
    }

    public void setBpm(float bpm) {
        this.bpm = bpm;
    }

    public float getBpmconf() {
        return bpmconf;
    }

    public void setBpmconf(float bpmconf) {
        this.bpmconf = bpmconf;
    }

    public float getMfccs() {
        return mfccs;
    }

    public void setMfccs(float mfccs) {
        this.mfccs = mfccs;
    }

    public float getZrc() {
        return zrc;
    }

    public void setZrc(float zrc) {
        this.zrc = zrc;
    }

    public float getSpectralFlux() {
        return spectralFlux;
    }

    public void setSpectralFlux(float spectralFlux) {
        this.spectralFlux = spectralFlux;
    }

    public float getSpectralCentroid() {
        return spectralCentroid;
    }

    public void setSpectralCentroid(float spectralCentroid) {
        this.spectralCentroid = spectralCentroid;
    }
}
