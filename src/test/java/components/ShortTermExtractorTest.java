package components;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.nd4j.linalg.api.ndarray.INDArray;
import testutils.INDArrayUtils;
import testutils.TestUtils;

import static constants.FeaturesNumbersConstants.TOTAL_FEATURES;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThat;
import static testutils.TestingConstants.*;

public class ShortTermExtractorTest {

    private TestUtils testUtils;

    private ShortTermExtractor SUT;

    private double[] roundPrecision = new double[]{
            10000d,
            1000d,
            100d,
            100d,
            10d};


    private double[] roundPrecisionComplete = new double[]{
            10000d,
            1000d,
            100d,
            100d,
            1d};


    @Before
    public void startUp() {
        testUtils = new TestUtils();
        SUT = new ShortTermExtractor();
    }

    @Ignore("The 301s sample of the song take too long.")
    @Test
    public void extractShortTermFeatures_knife301s() throws Exception {

        // Transform the input file into a float[] array
        double[] samples = testUtils.load_wav(TEST_KNIFE_301s_WAV);

        //Extract the control short term features
        INDArray controlShortTermFeatures = INDArrayUtils.readShortTermFeaturesFromFile(TEST_KNIFE_301s_CONTROL_VALUES_SHORTTERM);
        assertNotNull(controlShortTermFeatures);
        assertThat(controlShortTermFeatures.rows(), is(TOTAL_FEATURES));

        // Extract short term features
        INDArray extractShortTermFeatures = SUT.extractShortTermFeatures(samples, TEST_FREQUENCY_RATE, 220, 220);

        assertNotNull(extractShortTermFeatures);
        assertThat(extractShortTermFeatures.rows(), is(TOTAL_FEATURES));
        assertThat(extractShortTermFeatures.columns(), is(controlShortTermFeatures.columns()));

        //Check that the values are the same
        INDArrayUtils.assertShortTermFeaturesData(extractShortTermFeatures, controlShortTermFeatures, roundPrecision);

    }

    @Test
    public void extractShortTermFeatures_knife30s() throws Exception {

        // Transform the input file into a float[] array
        double[] samples = testUtils.load_wav(TEST_KNIFE_30s_WAV);

        //Extract the control short term features
        INDArray controlShortTermFeatures = INDArrayUtils.readShortTermFeaturesFromFile(TEST_KNIFE_30s_CONTROL_VALUES_SHORTTERM);
        assertNotNull(controlShortTermFeatures);
        assertThat(controlShortTermFeatures.rows(), is(TOTAL_FEATURES));

        // Extract short term features
        INDArray extractShortTermFeatures = SUT.extractShortTermFeatures(samples, TEST_FREQUENCY_RATE, 220, 220);

        assertNotNull(extractShortTermFeatures);
        assertThat(extractShortTermFeatures.rows(), is(TOTAL_FEATURES));
        assertThat(extractShortTermFeatures.columns(), is(controlShortTermFeatures.columns()));

        //Check that the values are the same
        INDArrayUtils.assertShortTermFeaturesData(extractShortTermFeatures, controlShortTermFeatures, roundPrecision);

    }


    @Test
    public void extractShortTermFeatures_knife10s() throws Exception {

        // Transform the input file into a float[] array
        double[] samples = testUtils.load_wav(TEST_KNIFE_10s_WAV);

        //Extract the control short term features
        INDArray controlShortTermFeatures = INDArrayUtils.readShortTermFeaturesFromFile(TEST_KNIFE_10s_CONTROL_VALUES_SHORTTERM);
        assertNotNull(controlShortTermFeatures);
        assertThat(controlShortTermFeatures.rows(), is(TOTAL_FEATURES));

        // Extract short term features
        INDArray extractShortTermFeatures = SUT.extractShortTermFeatures(samples, TEST_FREQUENCY_RATE, 220, 220);

        assertNotNull(extractShortTermFeatures);
        assertThat(extractShortTermFeatures.rows(), is(TOTAL_FEATURES));
        assertThat(extractShortTermFeatures.columns(), is(controlShortTermFeatures.columns()));

        //Check that the values are the same
        INDArrayUtils.assertShortTermFeaturesData(extractShortTermFeatures, controlShortTermFeatures, roundPrecision);

    }

}
