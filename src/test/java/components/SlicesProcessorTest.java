package components;

import org.junit.Before;
import org.junit.Test;
import org.nd4j.linalg.api.iter.NdIndexIterator;
import org.nd4j.linalg.api.ndarray.INDArray;
import testutils.INDArrayUtils;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThat;
import static testutils.TestingConstants.*;

public class SlicesProcessorTest {
    private SlicesProcessor SUT;

    private INDArray currentSliceData;
    private INDArray currentFFTSliceData;

    @Before
    public void startUp() {
        SUT = new SlicesProcessor();
        currentSliceData = INDArrayUtils.readAudioSliceFromFile(TEST_SAMPLE_INDARRAY_C_AUDIO_SLICE);
        currentFFTSliceData = INDArrayUtils.readAudioSliceFromFile(TEST_SAMPLE_INDARRAY_FFT_C_AUDIO_SLICE);
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
