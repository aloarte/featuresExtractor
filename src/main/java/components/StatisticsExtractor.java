package components;

import model.ModuleParams;
import model.enums.StatisticalMeasureType;
import model.exceptions.AudioAnalysisException;
import org.nd4j.linalg.api.ndarray.INDArray;
import org.nd4j.linalg.factory.Nd4j;
import org.nd4j.linalg.indexing.INDArrayIndex;
import org.nd4j.linalg.indexing.NDArrayIndex;

import java.util.ArrayList;
import java.util.List;

import static constants.FeaturesNumbersConstants.TOTAL_FEATURES;

public class StatisticsExtractor {


    private MethodsEntryValidator validator;

    public StatisticsExtractor() {
        validator = new MethodsEntryValidator();
    }

    /**
     * Read the info from the extractedFeaturedMatrix, calculate the statistics of each feature and put it on the mtFeatures output INDArray
     *
     * @param midTermSlice
     * @param moduleParams
     * @return
     */
    List<Double> calculateStatisticalInfo(INDArray midTermSlice, final ModuleParams moduleParams) {
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

    /**
     * Normalize and appy statistic operation over the extractedFeaturesMatrix to return only the statistics numbers in each feature.
     *
     * @param extractedShortTermFeatures Features extracted in a 32 x N matrix
     * @return
     */
    INDArray obtainMidTermFeatures(INDArray extractedShortTermFeatures, final ModuleParams moduleParams) throws AudioAnalysisException {

        validator.verifyExtractedMatrix(extractedShortTermFeatures);

        //Mid-Term feature extraction
        int mtWinRation = Math.round((float) moduleParams.getMidTermWindowSize() / moduleParams.getShortTermStepSize());
        int mtStepRatio = Math.round((float) moduleParams.getMidTermStepSize() / moduleParams.getShortTermStepSize());

        //Get the number of MtWindows that will be analyzed
        int mtWindowsNumber = (int) Math.ceil((float) extractedShortTermFeatures.columns() / mtStepRatio);

        //Create the base response INDArray initialized with zeros. Is size is [NumberOfFeatures * NumberOfStatisticalMeasures]
        INDArray audioFeaturesStatistics = Nd4j.zeros(extractedShortTermFeatures.rows() * moduleParams.getStatisticalMeasuresNumber(), mtWindowsNumber);

        //For each feature (34 times) extractedShortTermFeatures rows
        for (int featureIndex = 0; featureIndex < extractedShortTermFeatures.rows(); featureIndex++) {
            int cur_p = 0;
            int featureValueDataIndex = 0;

            //For each value of each feature extractedShortTermFeatures cols
            while (cur_p < extractedShortTermFeatures.columns()) {
                int N1 = cur_p;
                int N2 = cur_p + mtWinRation;

                //For the last window
                if (N2 > extractedShortTermFeatures.columns()) {
                    N2 = extractedShortTermFeatures.columns();
                }

                //Extract the mid term slice for the feature [featureIndex]
                INDArray midTermSlice = extractedShortTermFeatures.get(
                        NDArrayIndex.point(featureIndex),
                        NDArrayIndex.interval(N1, N2));

                //Calculate all the statistical info based on the module params from the extractedFeaturesMatrix. Result returned on mtFeatures.
                List<Double> statisticValuesOfSlice = calculateStatisticalInfo(midTermSlice, moduleParams);
                setInfoIntoMidTerm(audioFeaturesStatistics, statisticValuesOfSlice, featureIndex, featureValueDataIndex);

                cur_p += mtStepRatio;
                featureValueDataIndex++;
            }


        }
//        return audioFeaturesStatistics.mean(1);
        return audioFeaturesStatistics;

    }

    /**
     * Insert the calculated statistic from the midTermSlice into the audioFeaturesStatistics
     *
     * @param audioFeaturesStatistics INDArray where the output statistic values are saved
     * @param statisticValuesOfSlice  statistics values extracted from the midTermSlice
     * @param beginIndex
     * @param endIndex
     */
    private void setInfoIntoMidTerm(INDArray audioFeaturesStatistics, List<Double> statisticValuesOfSlice, int beginIndex, int endIndex) {
        int i = 0;
        for (double statValue : statisticValuesOfSlice) {
            audioFeaturesStatistics.put(new INDArrayIndex[]{
                    NDArrayIndex.point(beginIndex + TOTAL_FEATURES * i),
                    NDArrayIndex.point(endIndex)
            }, statValue);
            i++;
        }

    }
}
