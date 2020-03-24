package components;

import model.ModuleParams;
import org.junit.Before;
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

    @Before
    public void startUp() {
        wavUtils = new WavUtils();
        SUT = new AudioFeaturesExtractor();
        currentSliceData = INDArrayUtils.readINDArrayFromFile(TEST_SAMPLE_INDARRAY_C_AUDIO_SLICE);
        currentFFTSliceData = INDArrayUtils.readINDArrayFromFile(TEST_SAMPLE_INDARRAY_FFT_C_AUDIO_SLICE);
    }

    @Test
    public void globalFeatureExtraction() throws Exception {

        // Transform the input file into a float[] array
        double[] samples = wavUtils.load_wav(TEST_SAMPLE);


        // Extract globalFeatures
        INDArray features = SUT.globalFeatureExtraction(samples, new ModuleParams(22050, 0.01, 0.01, 1, 1));

    }

    @Test
    public void extractAudioFeatures() throws Exception {

        // Transform the input file into a float[] array
        double[] samples = wavUtils.load_wav(TEST_SAMPLE_KNIFE);
        // Extract globalFeatures
        INDArray javaFeatures = SUT.extractAudioFeatures(samples, 22050, 220, 220);


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
