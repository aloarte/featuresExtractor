package components;

import org.junit.Before;
import org.junit.Test;
import org.nd4j.linalg.api.ndarray.INDArray;
import testutils.INDArrayUtils;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThat;
import static testutils.TestingConstants.*;

public class SpectralProcessorTest {

    private SpectralProcessor SUT;

    private INDArray fftCurrentSliceData;
    private INDArray fftPreviousSliceData;


    @Before
    public void startUp() {
        SUT = SpectralProcessor.getInstance(EPS_CONSTANT);
        fftCurrentSliceData = INDArrayUtils.readINDArrayFromFile(TEST_SAMPLE_INDARRAY_FFT_C_AUDIO_SLICE);
        fftPreviousSliceData = INDArrayUtils.readINDArrayFromFile(TEST_SAMPLE_INDARRAY_FFT_P_AUDIO_SLICE);

    }


    @Test
    public void extractSpectralCentroidAndSpread() {
        double[] stSpectralCentroidAndSpread = SUT.extractSpectralCentroidAndSpread(fftCurrentSliceData, 22050);
        assertNotNull(stSpectralCentroidAndSpread);
        assertThat(stSpectralCentroidAndSpread.length, is(2));
        assertThat(stSpectralCentroidAndSpread[0], is(TEST_AUDIO_SPECTRAL_CENTROID_VALUE));
        assertThat(stSpectralCentroidAndSpread[1], is(TEST_AUDIO_SPECTRAL_SPREAD_VALUE));
    }

    @Test
    public void extractSpectralEntropy() {
        double spectralEntropy = SUT.extractSpectralEntropy(fftCurrentSliceData, 10);
        assertThat(spectralEntropy, is(TEST_AUDIO_SPECTRAL_ENTROPY_VALUE));
    }

    @Test
    public void extractSpectralFlux() {
        double spectralFlux = SUT.extractSpectralFlux(fftCurrentSliceData, fftPreviousSliceData);
        assertThat(spectralFlux, is(TEST_AUDIO_SPECTRAL_FLUX_VALUE));
    }

    @Test
    public void extractSpectralRollOff() {
        double spectralFlux = SUT.extractSpectralRollOff(fftCurrentSliceData, 0.90, 22050);
        assertThat(spectralFlux, is(TEST_AUDIO_SPECTRAL_ROLLOFF_VALUE));
    }
}
