package model;

import java.util.List;

public class AudioFeatures {

    private BpmFeatures bpmFeatures;

    private List<AudioShortFeatures> audioShortFeaturesList;

    public AudioFeatures() {
    }

    public AudioFeatures(BpmFeatures bpmFeatures, List<AudioShortFeatures> audioShortFeaturesList) {
        this.bpmFeatures = bpmFeatures;
        this.audioShortFeaturesList = audioShortFeaturesList;
    }

    public BpmFeatures getBpmFeatures() {
        return bpmFeatures;
    }

    public void setBpmFeatures(BpmFeatures bpmFeatures) {
        this.bpmFeatures = bpmFeatures;
    }

    public List<AudioShortFeatures> getAudioShortFeaturesList() {
        return audioShortFeaturesList;
    }

    public void setAudioShortFeaturesList(List<AudioShortFeatures> audioShortFeaturesList) {
        this.audioShortFeaturesList = audioShortFeaturesList;
    }


}
