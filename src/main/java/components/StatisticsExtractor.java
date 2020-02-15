package components;

import model.ModuleParams;
import model.enums.StatisticalMeasureType;
import org.nd4j.linalg.api.ndarray.INDArray;
import org.nd4j.linalg.factory.Nd4j;
import org.nd4j.linalg.indexing.INDArrayIndex;
import org.nd4j.linalg.indexing.NDArrayIndex;

public class StatisticsExtractor {

    /**
     * Read the info from the extractedFeaturedMatrix, calculate the statistics of each feature and put it on the mtFeatures output INDArray
     *
     * @param extractedFeaturesMatrix
     * @param audioFeaturesStatistics
     * @param featureIndex
     * @param featureDataIndex
     * @param N1
     * @param N2
     * @param moduleParams
     * @return
     */
    private INDArray calculateStatisticalInfo(INDArray extractedFeaturesMatrix, INDArray audioFeaturesStatistics, int featureIndex, int featureDataIndex, int N1, int N2, final ModuleParams moduleParams) {

        INDArray cur_stFeatures = extractedFeaturesMatrix.get(
                NDArrayIndex.point(featureIndex),
                NDArrayIndex.interval(N1, N2));

        for (StatisticalMeasureType measureType : moduleParams.getStatisticalMeasures()) {
            switch (measureType) {
                case MEAN:
                    calculateMean(cur_stFeatures, audioFeaturesStatistics, featureIndex, featureDataIndex);
                    break;
                case VARIANCE:
                    break;
                case STANDARD_DEVIATION:
                    calculateStandardDeviation(cur_stFeatures, audioFeaturesStatistics, featureIndex + extractedFeaturesMatrix.rows(), featureDataIndex);
                    break;
            }
        }

        return audioFeaturesStatistics;
    }

    void calculateMean(INDArray sourceData, INDArray outputData, int beginIndex, int endIndex) {
        outputData.put(new INDArrayIndex[]{
                NDArrayIndex.point(beginIndex),
                NDArrayIndex.point(endIndex)
        }, Nd4j.mean(sourceData));
    }

    void calculateStandardDeviation(INDArray sourceData, INDArray outputData, int beginIndex, int endIndex) {
        outputData.put(new INDArrayIndex[]{
                NDArrayIndex.point(beginIndex),
                NDArrayIndex.point(endIndex)
        }, Nd4j.std(sourceData));
    }

    /**
     * Normalize and appy statistic operation over the extractedFeaturesMatrix to return only the statistics numbers in each feature.
     *
     * @param extractedFeaturesMatrix Features extracted in a 32 x N matrix
     * @param mtWin
     * @param mtStep
     * @param stStep
     * @return
     */
    INDArray obtainAudioFeaturesStatistics(INDArray extractedFeaturesMatrix, int mtWin, int mtStep, int stStep, final ModuleParams moduleParams) {
        //Mid-Term feature extraction
        int mtWinRation = Math.round(mtWin / stStep);
        int mtStepRatio = Math.round(mtStep / stStep);


        int mtWindows = (int) Math.ceil(extractedFeaturesMatrix.columns() / mtStepRatio);

        //Create the base response INDArray initialized with zeros. Is size is [NumberOfFeatures * NumberOfStatisticalMeasures]
        INDArray audioFeaturesStatistics = Nd4j.zeros(extractedFeaturesMatrix.rows() * moduleParams.getStatisticalMeasuresNumber(), mtWindows);

        for (int featureIndex = 0; featureIndex < extractedFeaturesMatrix.rows(); featureIndex++) {
            int cur_p = 0;
            int featureDataIndex = 0;

            while (cur_p < extractedFeaturesMatrix.columns()) {
                int N1 = cur_p;
                int N2 = cur_p + mtWinRation;

                if (N2 > extractedFeaturesMatrix.columns()) {
                    N2 = extractedFeaturesMatrix.columns();
                }


                //Calculate all the statistical info based on the module params from the extractedFeaturesMatrix. Result returned on mtFeatures.
                calculateStatisticalInfo(extractedFeaturesMatrix, audioFeaturesStatistics, featureIndex, featureDataIndex, N1, N2, moduleParams);

                cur_p += mtStepRatio;
                featureDataIndex++;
            }


        }

        return audioFeaturesStatistics;
    }
}
