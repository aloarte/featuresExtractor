package components;

import model.BpmFeatures;
import model.ModuleParams;
import model.RawAudioFeatures;
import model.exceptions.AudioAnalysisException;
import org.nd4j.linalg.api.ndarray.INDArray;

public class AudioFeaturesExtractor {

    private ShortTermExtractor shortTermExtractor;
    private MidTermExtractor midTermExtractor;
    private BpmExtractor bpmExtractor;

    public AudioFeaturesExtractor() {
        this.shortTermExtractor = new ShortTermExtractor();
        this.midTermExtractor = new MidTermExtractor();
        this.bpmExtractor = new BpmExtractor();
    }

    /**
     * Extract the features into a INDArray from the audioSamples input data. Uses the moduleParams to get the extraction
     * configuration.
     * <p>
     * 1) Extract short term features from the audioSamples double array
     * 2) Extract the mid term features from the short term features extracted previously
     *
     * @param audioSamples double[] with the raw data to analyze
     * @param moduleParams parameters for the extraction
     * @return INDArray of [34*numStatistics][1] with the mean of the mid term features
     * @throws AudioAnalysisException Exception raised in the process
     */
    public RawAudioFeatures featureExtraction(double[] audioSamples, final ModuleParams moduleParams) throws AudioAnalysisException {

        long timeBefore = System.currentTimeMillis();
        long timeAfter;

        //Extract the matrix with the [32 features] x [N window samples]
        INDArray shortTermFeatures = shortTermExtractor.extractShortTermFeatures(audioSamples, moduleParams.getFrequencyRate(), moduleParams.getShortTermWindowSize(), moduleParams.getShortTermStepSize());

        BpmFeatures bpmExtractedFeatures = bpmExtractor.extractBpm(shortTermFeatures, moduleParams);

        if (moduleParams.isLogProcessesDurationEnabled()) {
            timeAfter = System.currentTimeMillis();
            System.out.println("stFeatures extracted (" + (timeAfter - timeBefore) + ") : matrixExtractedFeatures [" + shortTermFeatures.shape()[0] + "][" + shortTermFeatures.shape()[1] + "]");
            timeBefore = timeAfter;
        }

        //Apply statistic operations to each N sample for each of the 32 features. Extract a matrix of [32 features] x [N statistic operations]
        INDArray midTermFeatures = midTermExtractor.obtainMidTermFeatures(shortTermFeatures, moduleParams);

        if (moduleParams.isLogProcessesDurationEnabled()) {
            timeAfter = System.currentTimeMillis();
            System.out.println("mtFeatures extracted (" + (timeAfter - timeBefore) + ") : mtFeatures [" + midTermFeatures.shape()[0] + "][" + midTermFeatures.shape()[1] + "]");
        }

        INDArray meanMidTermFeatures = midTermFeatures.mean(1).dup();

        return new RawAudioFeatures(meanMidTermFeatures, bpmExtractedFeatures);
    }

}
