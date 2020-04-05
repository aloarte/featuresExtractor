package model;

import org.nd4j.linalg.api.ndarray.INDArray;

public class RawAudioFeatures {

    private INDArray meanMidTermFeatures;

    private BpmFeatures bpmFeatures;


    public RawAudioFeatures() {
    }

    public RawAudioFeatures(INDArray meanMidTermFeatures, BpmFeatures bpmFeatures) {
        this.meanMidTermFeatures = meanMidTermFeatures;
        this.bpmFeatures = bpmFeatures;
    }

    public INDArray getMeanMidTermFeatures() {
        return meanMidTermFeatures;
    }

    public void setMeanMidTermFeatures(INDArray meanMidTermFeatures) {
        this.meanMidTermFeatures = meanMidTermFeatures;
    }

    public BpmFeatures getBpmFeatures() {
        return bpmFeatures;
    }

    public void setBpmFeatures(BpmFeatures bpmFeatures) {
        this.bpmFeatures = bpmFeatures;
    }
}
