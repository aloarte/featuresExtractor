package components;

import model.ModuleParams;
import model.RawAudioFeatures;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.nd4j.linalg.api.ndarray.INDArray;
import testutils.INDArrayUtils;
import testutils.TestUtils;

import static testutils.TestingConstants.*;

public class AudioFeaturesExtractorTest {

    private TestUtils testUtils;

    private AudioFeaturesExtractor SUT;

    private double[] roundPrecisionComplete = new double[]{
            10000d,
            1000d,
            100d,
            100d,
            1d};
    private ModuleParams moduleParams;


    @Before
    public void startUp() {
        testUtils = new TestUtils();
        SUT = new AudioFeaturesExtractor();
        moduleParams = new ModuleParams(TEST_FREQUENCY_RATE, 0.01, 0.01, 1, 1);
        moduleParams.enableLogProcessesDuration();
    }

    @Ignore("The 301s sample of the song take too long.")
    @Test
    public void featureExtraction_knife301s() throws Exception {

        // Transform the input file into a float[] array
        double[] samples = testUtils.load_wav(TEST_KNIFE_301s_WAV);

        INDArray meanMidControlFeatures = INDArrayUtils.readMeanMidTermFeaturesFromFile(TEST_KNIFE_301s_CONTROL_VALUES_MEAN);

        RawAudioFeatures extractedFeatures = SUT.featureExtraction(samples, moduleParams);
        INDArrayUtils.assertFeatures(extractedFeatures.getMeanMidTermFeatures(), meanMidControlFeatures, roundPrecisionComplete);

    }

    @Test
    public void featureExtraction_knife30s() throws Exception {
        System.out.println("_____________ PATH: "+TEST_RESOURCES_PATH);


        // Transform the input file into a float[] array
        double[] samples = testUtils.load_wav(TEST_KNIFE_30s_WAV);

        INDArray meanMidControlFeatures = INDArrayUtils.readMeanMidTermFeaturesFromFile(TEST_KNIFE_30s_CONTROL_VALUES_MEAN);

        RawAudioFeatures extractedFeatures = SUT.featureExtraction(samples, moduleParams);
        INDArrayUtils.assertFeatures(extractedFeatures.getMeanMidTermFeatures(), meanMidControlFeatures, roundPrecisionComplete);

    }

    @Test
    public void featureExtraction_knife10s() throws Exception {

        // Transform the input file into a float[] array
        double[] samples = testUtils.load_wav(TEST_KNIFE_10s_WAV);
        INDArray meanMidControlFeatures = INDArrayUtils.readMeanMidTermFeaturesFromFile(TEST_KNIFE_10s_CONTROL_VALUES_MEAN);

        RawAudioFeatures extractedFeatures = SUT.featureExtraction(samples, moduleParams);
        INDArrayUtils.assertFeatures(extractedFeatures.getMeanMidTermFeatures(), meanMidControlFeatures, roundPrecisionComplete);
    }
}
