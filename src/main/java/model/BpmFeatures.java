package model;

/**
 * Features related with the bpm of the audio file
 */
public class BpmFeatures {


    private double bpmValue;


    private double bpmDeviation;

    public BpmFeatures(double bpmValue, double bpmDeviation) {
        this.bpmValue = bpmValue;
        this.bpmDeviation = bpmDeviation;
    }

    public BpmFeatures() {

    }

    public double getBpmValue() {
        return bpmValue;
    }

    public void setBpmValue(double bpmValue) {
        this.bpmValue = bpmValue;
    }

    public double getBpmDeviation() {
        return bpmDeviation;
    }

    public void setBpmDeviation(double bpmDeviation) {
        this.bpmDeviation = bpmDeviation;
    }

    @Override
    public String toString() {
        return " BPM Features: \n " +
                "- Value: " + getBpmValue() + "\n" +
                "- Deviation: " + getBpmDeviation() + "\n";
    }
}
