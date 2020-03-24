package components;

import org.junit.Before;
import org.junit.Test;
import org.nd4j.linalg.api.ndarray.INDArray;
import testutils.INDArrayUtils;

import static constants.FeaturesNumbersConstants.MFCCS_FEATURES;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThat;
import static testutils.TestingConstants.*;

public class MfccsProcessorTest {


    private INDArray fftCurrentSliceData;
    private MfccsProcessor SUT;

    @Before
    public void startUp() {
        fftCurrentSliceData = INDArrayUtils.readINDArrayFromFile(TEST_SAMPLE_INDARRAY_FFT_C_AUDIO_SLICE);
        SUT = MfccsProcessor.getInstance(TEST_FREQUENCY_RATE, TEST_NFFT);
    }


    @Test
    public void extractMFCC() {

        double[] MFCCs = SUT.extractMFCC(fftCurrentSliceData, MFCCS_FEATURES);
        assertNotNull(MFCCs);
        assertThat(MFCCs.length, is(MFCCS_FEATURES));
        assertThat(MFCCs[0], is(TEST_AUDIO_MFCCS[0]));
        assertThat(MFCCs[1], is(TEST_AUDIO_MFCCS[1]));
        assertThat(MFCCs[2], is(TEST_AUDIO_MFCCS[2]));
        assertThat(MFCCs[3], is(TEST_AUDIO_MFCCS[3]));
        assertThat(MFCCs[4], is(TEST_AUDIO_MFCCS[4]));
        assertThat(MFCCs[5], is(TEST_AUDIO_MFCCS[5]));
        assertThat(MFCCs[6], is(TEST_AUDIO_MFCCS[6]));
        assertThat(MFCCs[7], is(TEST_AUDIO_MFCCS[7]));
        assertThat(MFCCs[8], is(TEST_AUDIO_MFCCS[8]));
        assertThat(MFCCs[9], is(TEST_AUDIO_MFCCS[9]));
        assertThat(MFCCs[10], is(TEST_AUDIO_MFCCS[10]));
        assertThat(MFCCs[11], is(TEST_AUDIO_MFCCS[11]));
        assertThat(MFCCs[12], is(TEST_AUDIO_MFCCS[12]));
    }

}
