package components;

import model.ModuleParams;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.nd4j.linalg.api.iter.NdIndexIterator;
import org.nd4j.linalg.api.ndarray.INDArray;
import testutils.INDArrayUtils;
import testutils.WavUtils;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThat;
import static testutils.TestingConstants.*;

public class AudioFeaturesExtractorTest {

    private WavUtils wavUtils;

    private AudioFeaturesExtractor SUT;

    private INDArray currentSliceData;
    private INDArray currentFFTSliceData;
    private double[] roundPrecision = new double[]{
            10000d,
            1000d,
            100d,
            100d,
            10d};


    @Before
    public void startUp() {
        wavUtils = new WavUtils();
        SUT = new AudioFeaturesExtractor();
        currentSliceData = INDArrayUtils.readAudioSliceFromFile(TEST_SAMPLE_INDARRAY_C_AUDIO_SLICE);
        currentFFTSliceData = INDArrayUtils.readAudioSliceFromFile(TEST_SAMPLE_INDARRAY_FFT_C_AUDIO_SLICE);
    }

    @Ignore
    @Test
    public void featureExtraction() throws Exception {

        // Transform the input file into a float[] array
        double[] samples = wavUtils.load_wav(TEST_SAMPLE_KNIFE);

        INDArray controlFeatures = INDArrayUtils.readMidTermFeaturesFromFile(TEST_SAMPLE_FEATURES);

        // Extract globalFeatures
        INDArray extractedFeatures = SUT.featureExtraction(samples, new ModuleParams(TEST_FREQUENCY_RATE, 0.01, 0.01, 1, 1));
        INDArrayUtils.assertFeatures(extractedFeatures, controlFeatures, roundPrecision);

    }

    @Test
    public void extractShortTermFeatures() throws Exception {

        // Transform the input file into a float[] array
        double[] samples = wavUtils.load_wav(TEST_SAMPLE_KNIFE);

        //Extract the control short term features
        INDArray controlShortTermFeatures = INDArrayUtils.readAudioFeaturesFromFile(TEST_SAMPLE_SHORT_FEATURE);
        assertNotNull(controlShortTermFeatures);
        assertThat(controlShortTermFeatures.rows(), is(34));
        assertThat(controlShortTermFeatures.columns(), is(30196));

        // Extract short term features
        INDArray extractShortTermFeatures = SUT.extractShortTermFeatures(samples, TEST_FREQUENCY_RATE, 220, 220);

        assertNotNull(extractShortTermFeatures);
        assertThat(extractShortTermFeatures.rows(), is(34));
        assertThat(extractShortTermFeatures.columns(), is(30196));

        //Check that the values are the same
        INDArrayUtils.assertFeaturesData(extractShortTermFeatures, controlShortTermFeatures, roundPrecision);

    }


    @Test
    public void calculateFftFromAudioSlice() {
        INDArray fftAudioSlice = SUT.calculateFftFromAudioSlice(currentSliceData, TEST_NFFT);
        assertNotNull(fftAudioSlice);
        assertNotNull(currentFFTSliceData);
        assertThat(fftAudioSlice.size(1), is(TEST_NFFT));
        assertThat(currentFFTSliceData.size(1), is(TEST_NFFT));

        NdIndexIterator iterator = new NdIndexIterator(currentFFTSliceData.rows(), currentFFTSliceData.columns());
        while (iterator.hasNext()) {
            int[] nextIndex = iterator.next();
            assertThat(currentFFTSliceData.getDouble(nextIndex), is(fftAudioSlice.getDouble(nextIndex)));
        }
    }

}
