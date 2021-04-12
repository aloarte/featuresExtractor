package components;

import model.ModuleParams;
import model.enums.StatisticalMeasureType;
import org.nd4j.linalg.api.ndarray.INDArray;
import org.nd4j.linalg.factory.Nd4j;
import org.nd4j.linalg.indexing.INDArrayIndex;
import org.nd4j.linalg.indexing.NDArrayIndex;

import java.util.ArrayList;
import java.util.List;

import static constants.FeaturesNumbersConstants.TOTAL_FEATURES;

public class StatisticsExtractor {


    /**
     * Insert the calculated statistic from the midTermSlice into the audioFeaturesStatistics
     *
     * @param audioFeaturesStatistics INDArray where the output statistic values are saved
     * @param statisticValuesOfSlice  statistics values extracted from the midTermSlice
     * @param beginIndex
     * @param endIndex
     */
    public void setInfoIntoMidTerm(INDArray audioFeaturesStatistics, List<Double> statisticValuesOfSlice, int beginIndex, int endIndex) {
        int i = 0;
        for (double statValue : statisticValuesOfSlice) {
            audioFeaturesStatistics.put(new INDArrayIndex[]{
                    NDArrayIndex.point(beginIndex + TOTAL_FEATURES * i),
                    NDArrayIndex.point(endIndex)
            }, statValue);
            i++;
        }

    }

    /**
     * Read the info from the extractedFeaturedMatrix, calculate the statistics of each feature and put it on the mtFeatures output INDArray
     *
     * @param midTermSlice
     * @param moduleParams
     * @return
     */
    public List<Double> calculateStatisticalInfo(INDArray midTermSlice, final ModuleParams moduleParams) {
        List<Double> statisticalValuesPerSlice = new ArrayList<>();
        for (StatisticalMeasureType measureType : moduleParams.getStatisticalMeasures()) {
            switch (measureType) {
                case Mean:
                    statisticalValuesPerSlice.add(Nd4j.mean(midTermSlice).sumNumber().doubleValue());
                    break;
                case Variance:
                    break;
                case StandardDeviation:
                    statisticalValuesPerSlice.add(Nd4j.std(midTermSlice).sumNumber().doubleValue());
                    break;
            }
        }
        return statisticalValuesPerSlice;
    }
}
