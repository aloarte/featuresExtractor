package components;

import model.ModuleParams;
import model.enums.StatisticalMeasureType;
import model.exceptions.AudioAnalysisException;
import org.nd4j.linalg.api.ndarray.INDArray;
import org.nd4j.linalg.factory.Nd4j;
import org.nd4j.linalg.indexing.INDArrayIndex;
import org.nd4j.linalg.indexing.NDArrayIndex;

public class StatisticsExtractor {


    private MethodsEntryValidator validator;

    public StatisticsExtractor() {
        validator = new MethodsEntryValidator();
    }

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

        //System.out.println("calculateStatisticalInfo: extractedFeaturesMatrix ["+ extractedFeaturesMatrix.shape()[0] +"]["+ extractedFeaturesMatrix.shape()[1] +"] audioFeaturesStatistics [" + audioFeaturesStatistics.shape()[0] +"]["+ audioFeaturesStatistics.shape()[1] +"]");
        INDArray midTermSlice = extractedFeaturesMatrix.get(
                NDArrayIndex.point(featureIndex),
                NDArrayIndex.interval(N1, N2));

        for (StatisticalMeasureType measureType : moduleParams.getStatisticalMeasures()) {
            switch (measureType) {
                case Mean:
                    calculateMean(midTermSlice, audioFeaturesStatistics, featureIndex, featureDataIndex);
                    break;
                case Variance:
                    break;
                case StandardDeviation:
                    calculateStandardDeviation(midTermSlice, audioFeaturesStatistics, featureIndex + extractedFeaturesMatrix.rows(), featureDataIndex);
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
     * @return
     */
    INDArray obtainAudioFeaturesStatistics(INDArray extractedFeaturesMatrix, final ModuleParams moduleParams) throws AudioAnalysisException {

        validator.verifyExtractedMatrix(extractedFeaturesMatrix);

        //Mid-Term feature extraction
        int mtWinRation = Math.round((float) moduleParams.getMidTermWindowSize() / moduleParams.getShortTermStepSize());
        int mtStepRatio = Math.round((float) moduleParams.getMidTermStepSize() / moduleParams.getShortTermStepSize());


        int mtWindows = (int) Math.ceil((float) extractedFeaturesMatrix.columns() / mtStepRatio);

        //Create the base response INDArray initialized with zeros. Is size is [NumberOfFeatures * NumberOfStatisticalMeasures]
        INDArray audioFeaturesStatistics = Nd4j.zeros(extractedFeaturesMatrix.rows() * moduleParams.getStatisticalMeasuresNumber(), mtWindows);

        //68 veces
        for (int featureIndex = 0; featureIndex < extractedFeaturesMatrix.rows(); featureIndex++) {
            int cur_p = 0;
            int featureDataIndex = 0;

            //
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

        return audioFeaturesStatistics.mean(1);
    }
}
