package components;

import components.threads.MidTermThread;
import model.ModuleParams;
import model.exceptions.AudioAnalysisException;
import org.nd4j.linalg.api.ndarray.INDArray;
import org.nd4j.linalg.factory.Nd4j;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class MidTermExtractor {


    private MethodsEntryValidator validator;

    public MidTermExtractor() {
        validator = new MethodsEntryValidator();
    }

    /**
     * Normalize and appy statistic operation over the extractedFeaturesMatrix to return only the statistics numbers in each feature.
     *
     * @param extractedShortTermFeatures Features extracted in a 32 x N matrix
     * @return
     */
    INDArray obtainMidTermFeatures(INDArray extractedShortTermFeatures, final ModuleParams moduleParams) throws AudioAnalysisException {

        validator.verifyExtractedMatrix(extractedShortTermFeatures);

//        //Mid-Term feature extraction
        int mtWinRation = Math.round((float) moduleParams.getMidTermWindowSize() / moduleParams.getShortTermStepSize());
        int mtStepRatio = Math.round((float) moduleParams.getMidTermStepSize() / moduleParams.getShortTermStepSize());

        //Get the number of MtWindows that will be analyzed
        int mtWindowsNumber = (int) Math.ceil((float) extractedShortTermFeatures.columns() / mtStepRatio);

        //Create the base response INDArray initialized with zeros. Is size is [NumberOfFeatures * NumberOfStatisticalMeasures]
        INDArray audioFeaturesStatistics = Nd4j.zeros(extractedShortTermFeatures.rows() * moduleParams.getStatisticalMeasuresNumber(), mtWindowsNumber);

        ExecutorService executor = Executors.newFixedThreadPool(34);
        StatisticsExtractor extractor = new StatisticsExtractor();
        //For each feature (34 times) extractedShortTermFeatures rows
        for (int featureIndex = 0; featureIndex < extractedShortTermFeatures.rows(); featureIndex++) {
            executor.submit(new MidTermThread(extractedShortTermFeatures, audioFeaturesStatistics, moduleParams, featureIndex, mtWinRation, mtStepRatio));
        }
        executor.shutdown();
        try {
            while (executor.awaitTermination(1, TimeUnit.HOURS)) {
                break;
            }
        } catch (Exception ignored) {

        }

//        return audioFeaturesStatistics.mean(1);
        return audioFeaturesStatistics;

    }

}
