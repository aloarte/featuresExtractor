package components;

import org.junit.Before;
import org.junit.Test;
import org.nd4j.linalg.api.ndarray.INDArray;
import testutils.INDArrayUtils;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static testutils.TestingConstants.*;

public class ChromaProcessorTest {

    private ChromaProcessor SUT;

    private INDArray fftCurrentSliceData;


    @Before
    public void startUp() {
        SUT = ChromaProcessor.getInstance(TEST_FREQUENCY_RATE, TEST_NFFT);
        fftCurrentSliceData = INDArrayUtils.readINDArrayFromFile(TEST_SAMPLE_INDARRAY_FFT_C_AUDIO_SLICE);

    }

    @Test
    public void extractChromaFeatures() {
        double[] chromaFeatures = SUT.extractChromaFeatures(fftCurrentSliceData);
        assertThat(chromaFeatures[0], is(TEST_AUDIO_CHROMA_VECTORS[0]));
        assertThat(chromaFeatures[1], is(TEST_AUDIO_CHROMA_VECTORS[1]));
        assertThat(chromaFeatures[2], is(TEST_AUDIO_CHROMA_VECTORS[2]));
        assertThat(chromaFeatures[3], is(TEST_AUDIO_CHROMA_VECTORS[3]));
        assertThat(chromaFeatures[4], is(TEST_AUDIO_CHROMA_VECTORS[4]));
        assertThat(chromaFeatures[5], is(TEST_AUDIO_CHROMA_VECTORS[5]));
        assertThat(chromaFeatures[6], is(TEST_AUDIO_CHROMA_VECTORS[6]));
        assertThat(chromaFeatures[7], is(TEST_AUDIO_CHROMA_VECTORS[7]));
        assertThat(chromaFeatures[8], is(TEST_AUDIO_CHROMA_VECTORS[8]));
        assertThat(chromaFeatures[9], is(TEST_AUDIO_CHROMA_VECTORS[9]));
        assertThat(chromaFeatures[10], is(TEST_AUDIO_CHROMA_VECTORS[10]));
        assertThat(chromaFeatures[11], is(TEST_AUDIO_CHROMA_VECTORS[11]));


    }

}
