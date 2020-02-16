package model;

import model.enums.StatisticalMeasureType;

import java.util.ArrayList;
import java.util.List;

public class ModuleParams {

    private List<StatisticalMeasureType> statisticalMeasures;

    public ModuleParams() {
        statisticalMeasures = new ArrayList<>();
        statisticalMeasures.add(StatisticalMeasureType.Mean);
        statisticalMeasures.add(StatisticalMeasureType.StandardDeviation);
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
}
