package components;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.nd4j.linalg.api.ndarray.INDArray;
import testutils.INDArrayUtils;

import static testutils.TestingConstants.*;

public class ChromaProcessorTest {

    private ChromaProcessor SUT;

    private INDArray fftCurrentSliceData;


    @Before
    public void startUp() {
        SUT = ChromaProcessor.getInstance(TEST_FREQUENCY_RATE, TEST_NFFT);
        fftCurrentSliceData = INDArrayUtils.readINDArrayFromFile(TEST_SAMPLE_DOUBLE_INDARRAY_FFT_C_AUDIO_SLICE);

    }


    @Ignore
    @Test
    public void stSpectralCentroidAndSpread() {
        double[] stSpectralCentroidAndSpread = SUT.extractChromaFeatures(fftCurrentSliceData);
        //  stSpectralCentroidAndSpread[0]  -> SpectralCentroid
        // stSpectralCentroidAndSpread[1]  -> SpectralSpread
        //TODO: Perform assertions against controlled values
    }

    @Ignore
    @Test
    public void stSpectralEntropy() {
        //double stSpectralEntropy = SUT.stSpectralEntropy(audioSource, frequencyRate);
        //TODO: Perform assertions against controlled values
    }
}
