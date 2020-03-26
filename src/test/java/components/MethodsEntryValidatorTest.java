package components;

import model.exceptions.AudioExtractionException;
import org.junit.Before;
import org.junit.Test;
import org.nd4j.linalg.api.ndarray.INDArray;
import testutils.INDArrayUtils;

import static org.junit.Assert.*;
import static testutils.TestingConstants.*;

public class MethodsEntryValidatorTest {

    private INDArray currentSliceData;
    private INDArray fftCurrentSliceData;
    private INDArray fftPreviousSliceData;
    private MethodsEntryValidator SUT;

    @Before
    public void startUp() {
        SUT = new MethodsEntryValidator();
        currentSliceData = INDArrayUtils.readAudioSliceFromFile(TEST_SAMPLE_INDARRAY_C_AUDIO_SLICE);
        fftCurrentSliceData = INDArrayUtils.readAudioSliceFromFile(TEST_SAMPLE_INDARRAY_FFT_C_AUDIO_SLICE);
        fftPreviousSliceData = INDArrayUtils.readAudioSliceFromFile(TEST_SAMPLE_INDARRAY_FFT_P_AUDIO_SLICE);
    }


    @Test
    public void verifySliceValues_goodSlices() {
        try {
            SUT.verifySliceValues(currentSliceData, fftCurrentSliceData, fftPreviousSliceData);
        } catch (AudioExtractionException exception) {
            assertNull(exception);
        }
    }

    @Test
    public void verifySliceValues_badSlices() {
        try {
            SUT.verifySliceValues(currentSliceData, fftCurrentSliceData, currentSliceData);
            fail("This test case should raise an exception");
        } catch (AudioExtractionException exception) {
            assertNotNull(exception);
        }

        try {
            SUT.verifySliceValues(null, fftCurrentSliceData, fftPreviousSliceData);
            fail("This test case should raise an exception");
        } catch (AudioExtractionException exception) {
            assertNotNull(exception);
        }

        try {
            SUT.verifySliceValues(currentSliceData, null, currentSliceData);
            fail("This test case should raise an exception");
        } catch (AudioExtractionException exception) {
            assertNotNull(exception);
        }

        try {
            SUT.verifySliceValues(currentSliceData, fftCurrentSliceData, null);
            fail("This test case should raise an exception");
        } catch (AudioExtractionException exception) {
            assertNotNull(exception);
        }
    }

}
