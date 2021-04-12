package components.threads;

import components.StatisticsExtractor;
import model.ModuleParams;
import org.nd4j.linalg.api.ndarray.INDArray;
import org.nd4j.linalg.indexing.NDArrayIndex;

import java.util.List;

public class MidTermThread implements Runnable {
    private StatisticsExtractor extractor;
    private INDArray extractedShortTermFeatures;
    private INDArray audioFeaturesStatistics;
    private ModuleParams moduleParams;
    private int featureIndex;
    private int featureValueDataIndex;
    private int mtWinRation;
    private int mtStepRatio;

    public MidTermThread(INDArray inputExtractedShortTermFeatures, INDArray outputAudioFeaturesStatistics, ModuleParams moduleParams, int featureIndex, int mtWinRation, int mtStepRatio) {
        this.extractor = new StatisticsExtractor();
        this.extractedShortTermFeatures = inputExtractedShortTermFeatures;
        this.audioFeaturesStatistics = outputAudioFeaturesStatistics;
        this.moduleParams = moduleParams;
        this.featureIndex = featureIndex;
        this.mtWinRation = mtWinRation;
        this.mtStepRatio = mtStepRatio;

    }

    @Override
    public void run() {
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
            List<Double> statisticValuesOfSlice = extractor.calculateStatisticalInfo(midTermSlice, moduleParams);
            extractor.setInfoIntoMidTerm(audioFeaturesStatistics, statisticValuesOfSlice, featureIndex, featureValueDataIndex);

            cur_p += mtStepRatio;
            featureValueDataIndex++;
        }
    }
}
