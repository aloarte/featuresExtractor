package components;

import org.junit.Before;
import org.junit.Test;
import org.nd4j.linalg.api.ndarray.INDArray;
import testutils.INDArrayUtils;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;
import static testutils.TestingConstants.*;

public class ChromaProcessorTest {

    private ChromaProcessor SUT;

    private INDArray fftCurrentSliceData;


    @Before
    public void startUp() {
        SUT = ChromaProcessor.getInstance(TEST_FREQUENCY_RATE, TEST_NFFT);
        fftCurrentSliceData = INDArrayUtils.readAudioSliceFromFile(TEST_SAMPLE_INDARRAY_FFT_C_AUDIO_SLICE);

    }

    @Test
    public void extractChromaFeatures() {
        double delta = 1e-6f;
        double[] chromaFeatures = SUT.extractChromaFeatures(fftCurrentSliceData);
        assertEquals(TEST_AUDIO_CHROMA_VECTORS[0], chromaFeatures[0], delta);
        assertEquals(TEST_AUDIO_CHROMA_VECTORS[1], chromaFeatures[1], delta);
        assertEquals(TEST_AUDIO_CHROMA_VECTORS[2], chromaFeatures[2], delta);
        assertEquals(TEST_AUDIO_CHROMA_VECTORS[3], chromaFeatures[3], delta);
        assertEquals(TEST_AUDIO_CHROMA_VECTORS[4], chromaFeatures[4], delta);
        assertEquals(TEST_AUDIO_CHROMA_VECTORS[5], chromaFeatures[5], delta);
        assertEquals(TEST_AUDIO_CHROMA_VECTORS[6], chromaFeatures[6], delta);
        assertEquals(TEST_AUDIO_CHROMA_VECTORS[7], chromaFeatures[7], delta);
        assertEquals(TEST_AUDIO_CHROMA_VECTORS[8], chromaFeatures[8], delta);
        assertEquals(TEST_AUDIO_CHROMA_VECTORS[9], chromaFeatures[9], delta);
        assertEquals(TEST_AUDIO_CHROMA_VECTORS[10], chromaFeatures[10], delta);
        assertEquals(TEST_AUDIO_CHROMA_VECTORS[11], chromaFeatures[11], delta);


    }

}
