package components;

import model.ModuleParams;
import model.exceptions.AudioAnalysisException;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.nd4j.linalg.api.ndarray.INDArray;
import testutils.INDArrayUtils;

import static testutils.TestingConstants.*;

public class MidTermExtractorTest {

    MidTermExtractor SUT;

    INDArray controlShortTermFeatures;
    INDArray controlMidMeanTermFeatures;

    ModuleParams moduleParams;

    private double[] roundPrecision = new double[]{
            10000d,
            1000d,
            100d,
            10d,
            1d};

    @Before
    public void startUp() {
        SUT = new MidTermExtractor();
        controlShortTermFeatures = INDArrayUtils.readShortTermFeaturesFromFile(TEST_KNIFE_301s_CONTROL_VALUES_SHORTTERM);
        moduleParams = new ModuleParams(TEST_FREQUENCY_RATE, 0.01, 0.01, 1, 1);
    }

    @Test
    public void obtainAudioFeaturesStatistics_knife10s() throws AudioAnalysisException {
        INDArray controlMidMeanTermFeatures = INDArrayUtils.readMidTermFeaturesFromFile(TEST_KNIFE_10s_CONTROL_VALUES_MIDTERM);
        INDArray controlShortTermFeatures = INDArrayUtils.readShortTermFeaturesFromFile(TEST_KNIFE_10s_CONTROL_VALUES_SHORTTERM);

        INDArray extractedMidTermFeatures = SUT.obtainMidTermFeatures(controlShortTermFeatures, moduleParams);
        INDArrayUtils.assertMidTermFeaturesData(extractedMidTermFeatures, controlMidMeanTermFeatures, roundPrecision);
    }

    @Test
    public void obtainAudioFeaturesStatistics_knife30s() throws AudioAnalysisException {
        INDArray controlMidMeanTermFeatures = INDArrayUtils.readMidTermFeaturesFromFile(TEST_KNIFE_30s_CONTROL_VALUES_MIDTERM);
        INDArray controlShortTermFeatures = INDArrayUtils.readShortTermFeaturesFromFile(TEST_KNIFE_30s_CONTROL_VALUES_SHORTTERM);

        INDArray extractedMidTermFeatures = SUT.obtainMidTermFeatures(controlShortTermFeatures, moduleParams);
        INDArrayUtils.assertMidTermFeaturesData(extractedMidTermFeatures, controlMidMeanTermFeatures, roundPrecision);
    }

    @Ignore("The 301s sample of the song take too long.")
    @Test
    public void obtainAudioFeaturesStatistics_knife301s() throws AudioAnalysisException {
        INDArray controlMidMeanTermFeatures = INDArrayUtils.readMidTermFeaturesFromFile(TEST_KNIFE_301s_CONTROL_VALUES_MIDTERM);
        INDArray controlShortTermFeatures = INDArrayUtils.readShortTermFeaturesFromFile(TEST_KNIFE_301s_CONTROL_VALUES_SHORTTERM);

        INDArray extractedMidTermFeatures = SUT.obtainMidTermFeatures(controlShortTermFeatures, moduleParams);
        INDArrayUtils.assertMidTermFeaturesData(extractedMidTermFeatures, controlMidMeanTermFeatures, roundPrecision);
    }

}
