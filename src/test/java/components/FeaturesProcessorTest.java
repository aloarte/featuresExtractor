package components;

import model.exceptions.AudioExtractionException;
import org.junit.Before;
import org.junit.Test;
import org.nd4j.linalg.api.ndarray.INDArray;
import testutils.INDArrayUtils;

import static constants.FeaturesNumbersConstants.TOTAL_FEATURES;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThat;
import static testutils.TestingConstants.*;

public class FeaturesProcessorTest {

    private INDArray currentSliceData;
    private INDArray fftCurrentSliceData;
    private INDArray fftPreviousSliceData;
    private FeaturesProcessor SUT;

    @Before
    public void startUp() {
        SUT = new FeaturesProcessor();
        currentSliceData = INDArrayUtils.readINDArrayFromFile(TEST_SAMPLE_DOUBLE_INDARRAY_C_AUDIO_SLICE);
        fftCurrentSliceData = INDArrayUtils.readINDArrayFromFile(TEST_SAMPLE_DOUBLE_INDARRAY_FFT_C_AUDIO_SLICE);
        fftPreviousSliceData = INDArrayUtils.readINDArrayFromFile(TEST_SAMPLE_DOUBLE_INDARRAY_FFT_P_AUDIO_SLICE);
    }


    @Test
    public void extractFeaturesFromSlice() throws AudioExtractionException {

        INDArray extractedFeatures = SUT.extractFeaturesFromSlice(currentSliceData, fftCurrentSliceData, fftPreviousSliceData, 22050, 1102);
        assertNotNull(extractedFeatures);
        assertThat(extractedFeatures.shape()[0], is(TOTAL_FEATURES));
        assertThat(extractedFeatures.shape()[1], is(1));
    }

    @Test
    public void extractZeroCrossingRate() {
        double zeroCrossingRate = SUT.extractZeroCrossingRate(currentSliceData);
        assertThat(zeroCrossingRate, is(TEST_AUDIO_ZCR_VALUE));

    }
}
