package model;

import model.enums.StatisticalMeasureType;

import java.util.ArrayList;
import java.util.List;

public class ModuleParams {

    /**
     * List of the statistical measures that will be calculated for each audio feature
     */
    private List<StatisticalMeasureType> statisticalMeasures;

    /**
     * Number of frequency data per second
     */
    private int frequencyRate;

    /**
     * Size of the mid term window (number of data per window)
     */
    private int midTermWindowSize;

    private int midTermStepSize;

    /**
     * Size of the short term window (number of data per window)
     */
    private int shortTermWindowSize;

    /**
     * Size of the short term step (number of data
     */
    private int shortTermStepSize;

    private boolean logProcessesDurationEnabled = false;

    private boolean forceUseLowFrequencyRate = false;

    private boolean forceUseHighFrequencyRate = false;

    private boolean forceHighStepSize = false;


    public ModuleParams(int frequencyRate, double shortTermWindow, double shortTermStep, double midTermWindow, double midTermStep) {
        statisticalMeasures = new ArrayList<>();
        statisticalMeasures.add(StatisticalMeasureType.Mean);
        statisticalMeasures.add(StatisticalMeasureType.StandardDeviation);

        this.frequencyRate = frequencyRate;
        this.shortTermWindowSize = (int) (frequencyRate * shortTermWindow);
        this.shortTermStepSize = (int) (frequencyRate * shortTermStep);
        this.midTermWindowSize = (int) (frequencyRate * midTermWindow);
        this.midTermStepSize = (int) (frequencyRate * midTermStep);

    }

    public List<StatisticalMeasureType> getStatisticalMeasures() {
        return statisticalMeasures;
    }

    public void setStatisticalMeasures(List<StatisticalMeasureType> statisticalMeasures) {
        this.statisticalMeasures = statisticalMeasures;
    }

    public int getStatisticalMeasuresNumber() {
        if (statisticalMeasures != null) return this.statisticalMeasures.size();
        else return 0;
    }

    public int getFrequencyRate() {
        return frequencyRate;
    }

    public int getMidTermWindowSize() {
        return midTermWindowSize;
    }

    public int getMidTermStepSize() {
        return midTermStepSize;
    }

    public int getShortTermWindowSize() {
        return shortTermWindowSize;
    }

    public int getShortTermStepSize() {
        return shortTermStepSize;
    }

    public boolean isLogProcessesDurationEnabled() {
        return logProcessesDurationEnabled;
    }

    public boolean isForceUseLowFrequencyRateEnabled() {
        return forceUseLowFrequencyRate;
    }

    public boolean isForceUseHighFrequencyRateEnabled() {
        return forceUseHighFrequencyRate;
    }

    public boolean isForceHighStepSize() {
        return forceHighStepSize;
    }

    public void enableLogProcessesDuration() {
        this.logProcessesDurationEnabled = true;
    }

    public void forceLowFrequencyRate() {
        forceUseLowFrequencyRate = true;
    }

    public void forceHighFrequencyRate() {
        forceUseHighFrequencyRate = true;
    }

    public void forceHighStepSize() {
        forceHighStepSize = true;
    }
}
