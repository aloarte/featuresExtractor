package components;

import model.enums.AudioExtractionExceptionType;
import model.enums.AudioReadExtractionExceptionType;
import model.exceptions.AudioExtractionException;
import model.exceptions.AudioReadExtractionException;
import org.junit.Before;
import org.junit.Test;
import org.nd4j.linalg.api.ndarray.INDArray;
import org.nd4j.linalg.factory.Nd4j;
import testutils.INDArrayUtils;
import testutils.WavUtils;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.*;
import static testutils.TestingConstants.*;

public class MethodsEntryValidatorTest {

    private INDArray currentSliceData;
    private INDArray fftCurrentSliceData;
    private INDArray fftPreviousSliceData;
    private INDArray shortTermFeatures;
    private MethodsEntryValidator SUT;
    private double[] audioDataSource;

    @Before
    public void startUp() {
        WavUtils wavUtils = new WavUtils();
        SUT = new MethodsEntryValidator();
        currentSliceData = INDArrayUtils.readAudioSliceFromFile(TEST_SAMPLE_INDARRAY_C_AUDIO_SLICE);
        fftCurrentSliceData = INDArrayUtils.readAudioSliceFromFile(TEST_SAMPLE_INDARRAY_FFT_C_AUDIO_SLICE);
        fftPreviousSliceData = INDArrayUtils.readAudioSliceFromFile(TEST_SAMPLE_INDARRAY_FFT_P_AUDIO_SLICE);
        shortTermFeatures = INDArrayUtils.readAudioFeaturesFromFile(TEST_SAMPLE_SHORT_FEATURE);

        audioDataSource = wavUtils.load_wav(TEST_SAMPLE);


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
            assertThat(exception.getExtractionExceptionType(), is(AudioExtractionExceptionType.fftShapesMismatch));
        }

        try {
            SUT.verifySliceValues(null, fftCurrentSliceData, fftPreviousSliceData);
            fail("This test case should raise an exception");
        } catch (AudioExtractionException exception) {
            assertNotNull(exception);
            assertThat(exception.getExtractionExceptionType(), is(AudioExtractionExceptionType.BadCurrentAudioSlice));
        }

        try {
            SUT.verifySliceValues(currentSliceData, null, currentSliceData);
            fail("This test case should raise an exception");
        } catch (AudioExtractionException exception) {
            assertNotNull(exception);
            assertThat(exception.getExtractionExceptionType(), is(AudioExtractionExceptionType.BadCurrentFftAudioSlice));
        }

        try {
            SUT.verifySliceValues(currentSliceData, fftCurrentSliceData, null);
            fail("This test case should raise an exception");
        } catch (AudioExtractionException exception) {
            assertNotNull(exception);
            assertThat(exception.getExtractionExceptionType(), is(AudioExtractionExceptionType.BadPreviousAudioSlice));

        }
    }

    @Test
    public void verifyExtractedMatrix_goodMatrix() {
        try {
            SUT.verifyExtractedMatrix(shortTermFeatures);
        } catch (AudioExtractionException exception) {
            fail("This test can't raise an exception");
        }
    }

    @Test
    public void verifyExtractedMatrix_badMatrix() {
        try {
            SUT.verifyExtractedMatrix(Nd4j.zeros(shortTermFeatures.rows() - 1, shortTermFeatures.columns()));
            fail("This test case should raise an exception");
        } catch (AudioExtractionException exception) {
            assertNotNull(exception);
            assertThat(exception.getExtractionExceptionType(), is(AudioExtractionExceptionType.WrongNumberOfFeaturesExtracted));
        }

        try {
            SUT.verifyExtractedMatrix(null);
            fail("This test case should raise an exception");
        } catch (AudioExtractionException exception) {
            assertNotNull(exception);
            assertThat(exception.getExtractionExceptionType(), is(AudioExtractionExceptionType.BadExtractedFeaturesMatrix));
        }
    }

    @Test
    public void validateAudioSource_goodAudioSource() {
        try {
            SUT.validateAudioSource(audioDataSource, TEST_FREQUENCY_RATE);
        } catch (AudioReadExtractionException exception) {
            fail("This test can't raise an exception");
        }
    }

    @Test
    public void validateAudioSource_badAudioSource() {
        try {
            SUT.validateAudioSource(null, TEST_FREQUENCY_RATE);
            fail("This test case should raise an exception");
        } catch (AudioReadExtractionException exception) {
            assertNotNull(exception);
            assertThat(exception.getReadExtractionExceptionType(), is(AudioReadExtractionExceptionType.NullRawAudioSource));
        }

        try {
            SUT.validateAudioSource(new double[2], TEST_FREQUENCY_RATE);
            fail("This test case should raise an exception");
        } catch (AudioReadExtractionException exception) {
            assertNotNull(exception);
            assertThat(exception.getReadExtractionExceptionType(), is(AudioReadExtractionExceptionType.BadAudioSourceRead));
        }

        double[] lowValuesDataSource = new double[TEST_FREQUENCY_RATE - 1];
        for (int i = 0; i < lowValuesDataSource.length; i++) {
            lowValuesDataSource[i] = Math.random() * 100;
        }

        try {
            SUT.validateAudioSource(lowValuesDataSource, TEST_FREQUENCY_RATE);
            fail("This test case should raise an exception");
        } catch (AudioReadExtractionException exception) {
            assertNotNull(exception);
            assertThat(exception.getReadExtractionExceptionType(), is(AudioReadExtractionExceptionType.TooLowSamples));
        }
    }


}
