package components;

import model.ModuleParams;
import model.enums.AudioAnalysisExceptionType;
import model.enums.ConfigurationExceptionType;
import model.enums.ExtractionExceptionType;
import model.enums.ProcessingExceptionType;
import model.exceptions.AudioAnalysisException;
import model.exceptions.ConfigurationException;
import model.exceptions.ExtractionException;
import model.exceptions.ProcessingException;
import org.junit.Before;
import org.junit.Test;
import org.nd4j.linalg.api.ndarray.INDArray;
import org.nd4j.linalg.factory.Nd4j;
import testutils.INDArrayUtils;
import testutils.TestUtils;

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
        TestUtils testUtils = new TestUtils();
        SUT = new MethodsEntryValidator();
        currentSliceData = INDArrayUtils.readAudioSliceFromFile(TEST_SAMPLE_INDARRAY_C_AUDIO_SLICE);
        fftCurrentSliceData = INDArrayUtils.readAudioSliceFromFile(TEST_SAMPLE_INDARRAY_FFT_C_AUDIO_SLICE);
        fftPreviousSliceData = INDArrayUtils.readAudioSliceFromFile(TEST_SAMPLE_INDARRAY_FFT_P_AUDIO_SLICE);
        shortTermFeatures = INDArrayUtils.readShortTermFeaturesFromFile(TEST_KNIFE_301s_CONTROL_VALUES_SHORTTERM);

        audioDataSource = testUtils.load_wav(TEST_KNIFE_30s_WAV);


    }


    @Test
    public void verifySliceValues_goodSlices() {
        try {
            SUT.verifySliceValues(currentSliceData, fftCurrentSliceData, fftPreviousSliceData);
        } catch (AudioAnalysisException exception) {
            assertNull(exception);
        }
    }

    @Test
    public void verifySliceValues_badSlices() {
        try {
            SUT.verifySliceValues(currentSliceData, fftCurrentSliceData, currentSliceData);
            fail("This test case should raise an exception");
        } catch (ProcessingException exception) {
            assertNotNull(exception);
            assertThat(exception.getAudioProcessingSubtype(), is(ProcessingExceptionType.fftShapesMismatch));
            assertThat(exception.getAudioAnalysisExceptionType(), is(AudioAnalysisExceptionType.AudioProcessing));
        } catch (Exception e) {
            fail("This test case should raise an ProcessingException");
        }

        try {
            SUT.verifySliceValues(null, fftCurrentSliceData, fftPreviousSliceData);
            fail("This test case should raise an exception");
        } catch (ProcessingException exception) {
            assertNotNull(exception);
            assertThat(exception.getAudioProcessingSubtype(), is(ProcessingExceptionType.BadCurrentAudioSlice));
            assertThat(exception.getAudioAnalysisExceptionType(), is(AudioAnalysisExceptionType.AudioProcessing));
        } catch (Exception e) {
            fail("This test case should raise an ProcessingException");
        }

        try {
            SUT.verifySliceValues(currentSliceData, null, currentSliceData);
            fail("This test case should raise an exception");
        } catch (ProcessingException exception) {
            assertNotNull(exception);
            assertThat(exception.getAudioProcessingSubtype(), is(ProcessingExceptionType.BadCurrentFftAudioSlice));
            assertThat(exception.getAudioAnalysisExceptionType(), is(AudioAnalysisExceptionType.AudioProcessing));
        } catch (Exception e) {
            fail("This test case should raise an ProcessingException");
        }

        try {
            SUT.verifySliceValues(currentSliceData, fftCurrentSliceData, null);
            fail("This test case should raise an exception");
        } catch (ProcessingException exception) {
            assertNotNull(exception);
            assertThat(exception.getAudioProcessingSubtype(), is(ProcessingExceptionType.BadPreviousAudioSlice));
            assertThat(exception.getAudioAnalysisExceptionType(), is(AudioAnalysisExceptionType.AudioProcessing));

        } catch (Exception e) {
            fail("This test case should raise an ProcessingException");
        }
    }

    @Test
    public void verifyExtractedMatrix_goodMatrix() {
        try {
            SUT.verifyExtractedMatrix(shortTermFeatures);
        } catch (ExtractionException exception) {
            fail("This test can't raise an exception");
        } catch (Exception e) {
            fail("This test case should raise an ExtractionException");
        }
    }

    @Test
    public void verifyExtractedMatrix_badMatrix() {
        try {
            SUT.verifyExtractedMatrix(Nd4j.zeros(shortTermFeatures.rows() - 1, shortTermFeatures.columns()));
            fail("This test case should raise an exception");
        } catch (ExtractionException exception) {
            assertNotNull(exception);
            assertThat(exception.getAudioAnalysisExceptionType(), is(AudioAnalysisExceptionType.AudioExtraction));
            assertThat(exception.getExtractionExceptionSubtype(), is(ExtractionExceptionType.WrongNumberOfFeaturesExtracted));
        } catch (Exception e) {
            fail("This test case should raise an ExtractionException");
        }

        try {
            SUT.verifyExtractedMatrix(null);
            fail("This test case should raise an exception");
        } catch (ExtractionException exception) {
            assertNotNull(exception);
            assertThat(exception.getAudioAnalysisExceptionType(), is(AudioAnalysisExceptionType.AudioExtraction));
            assertThat(exception.getExtractionExceptionSubtype(), is(ExtractionExceptionType.BadExtractedFeaturesMatrix));

        } catch (Exception e) {
            fail("This test case should raise an ExtractionException");
        }
    }

    @Test
    public void validateAudioSource_goodAudioSource() {
        try {
            SUT.validateAudioSource(audioDataSource, TEST_FREQUENCY_RATE);
        } catch (ExtractionException exception) {
            fail("This test can't raise an exception");
        }
    }

    @Test
    public void validateAudioSource_badAudioSource() {
        try {
            SUT.validateAudioSource(null, TEST_FREQUENCY_RATE);
            fail("This test case should raise an exception");
        } catch (ExtractionException exception) {
            assertNotNull(exception);
            assertThat(exception.getExtractionExceptionSubtype(), is(ExtractionExceptionType.NullRawAudioSource));
        } catch (Exception e) {
            fail("This test case should raise an ExtractionException");
        }

        try {
            SUT.validateAudioSource(new double[2], TEST_FREQUENCY_RATE);
            fail("This test case should raise an exception");
        } catch (ExtractionException exception) {
            assertNotNull(exception);
            assertThat(exception.getExtractionExceptionSubtype(), is(ExtractionExceptionType.BadAudioSourceRead));
        } catch (Exception e) {
            fail("This test case should raise an ExtractionException");
        }

        double[] lowValuesDataSource = new double[TEST_FREQUENCY_RATE - 1];
        for (int i = 0; i < lowValuesDataSource.length; i++) {
            lowValuesDataSource[i] = Math.random() * 100;
        }
        try {
            SUT.validateAudioSource(lowValuesDataSource, TEST_FREQUENCY_RATE);
            fail("This test case should raise an exception");
        } catch (ExtractionException exception) {
            assertNotNull(exception);
            assertThat(exception.getExtractionExceptionSubtype(), is(ExtractionExceptionType.TooLowSamples));
        } catch (Exception e) {
            fail("This test case should raise an ExtractionException");
        }
    }

    @Test
    public void validateConfiguration_goodConfiguration() {
        try {
            ModuleParams moduleParams = new ModuleParams(TEST_FREQUENCY_RATE, 0.01, 0.01, 1, 1);
            SUT.validateConfiguration(moduleParams);
        } catch (ConfigurationException e) {
            fail("This test can't raise an exception");
        }

        try {
            ModuleParams moduleParams = new ModuleParams(TEST_FREQUENCY_RATE, 1, 1, 1, 1);
            SUT.validateConfiguration(moduleParams);
        } catch (ConfigurationException e) {
            fail("This test can't raise an exception");
        }

        try {
            ModuleParams moduleParams = new ModuleParams(TEST_LOW_FREQUENCY_RATE, 1, 1, 1, 1);
            moduleParams.forceLowFrequencyRate();
            SUT.validateConfiguration(moduleParams);
        } catch (ConfigurationException e) {
            fail("This test can't raise an exception");
        }

        try {
            ModuleParams moduleParams = new ModuleParams(TEST_HIGH_FREQUENCY_RATE, 1, 1, 1, 1);
            moduleParams.forceHighFrequencyRate();
            SUT.validateConfiguration(moduleParams);
        } catch (ConfigurationException e) {
            fail("This test can't raise an exception");
        }

        try {
            ModuleParams moduleParams = new ModuleParams(TEST_FREQUENCY_RATE, 0.01, 0.1, 1, 1);
            moduleParams.forceHighStepSize();
            SUT.validateConfiguration(moduleParams);
        } catch (ConfigurationException e) {
            fail("This test can't raise an exception");
        }

        try {
            ModuleParams moduleParams = new ModuleParams(TEST_FREQUENCY_RATE, 0.001, 0.001, 0.01, 0.1);
            moduleParams.forceHighStepSize();
            SUT.validateConfiguration(moduleParams);
        } catch (ConfigurationException e) {
            fail("This test can't raise an exception");
        }
    }


    @Test
    public void validateConfiguration_badConfig_warningExceptions() {

        try {
            ModuleParams moduleParams = new ModuleParams(TEST_FREQUENCY_RATE, 0.01, 0.1, 1, 1);
            SUT.validateConfiguration(moduleParams);
            fail("This test should raise an exception");
        } catch (ConfigurationException e) {
            assertThat(e.getAudioAnalysisExceptionType(), is(AudioAnalysisExceptionType.ExtractionConfiguration));
            assertThat(e.getConfigurationExceptionSubtype(), is(ConfigurationExceptionType.WarningHighStepSize));
        }

        try {
            ModuleParams moduleParams = new ModuleParams(TEST_FREQUENCY_RATE, 0.001, 0.001, 0.01, 0.1);
            SUT.validateConfiguration(moduleParams);
            fail("This test should raise an exception");
        } catch (ConfigurationException e) {
            assertThat(e.getAudioAnalysisExceptionType(), is(AudioAnalysisExceptionType.ExtractionConfiguration));
            assertThat(e.getConfigurationExceptionSubtype(), is(ConfigurationExceptionType.WarningHighStepSize));
        }

        try {
            ModuleParams moduleParams = new ModuleParams(TEST_LOW_FREQUENCY_RATE, 1, 1, 1, 1);
            SUT.validateConfiguration(moduleParams);
            fail("This test should raise an exception");
        } catch (ConfigurationException e) {
            assertThat(e.getAudioAnalysisExceptionType(), is(AudioAnalysisExceptionType.ExtractionConfiguration));
            assertThat(e.getConfigurationExceptionSubtype(), is(ConfigurationExceptionType.WarningLowFrequencyRate));
        }

        try {
            ModuleParams moduleParams = new ModuleParams(TEST_HIGH_FREQUENCY_RATE, 1, 1, 1, 1);
            SUT.validateConfiguration(moduleParams);
            fail("This test should raise an exception");
        } catch (ConfigurationException e) {
            assertThat(e.getAudioAnalysisExceptionType(), is(AudioAnalysisExceptionType.ExtractionConfiguration));
            assertThat(e.getConfigurationExceptionSubtype(), is(ConfigurationExceptionType.WarningHighFrequencyRate));
        }
    }

    @Test
    public void validateConfiguration_badConfig_badInputs() {

        try {
            SUT.validateConfiguration(null);
            fail("This test should raise an exception");
        } catch (ConfigurationException e) {
            assertThat(e.getAudioAnalysisExceptionType(), is(AudioAnalysisExceptionType.ExtractionConfiguration));
            assertThat(e.getConfigurationExceptionSubtype(), is(ConfigurationExceptionType.NullConfiguration));
        }

        try {
            ModuleParams moduleParams = new ModuleParams(TEST_FREQUENCY_RATE, 1, 1, 0.01, 0.01);
            SUT.validateConfiguration(moduleParams);
            fail("This test should raise an exception");
        } catch (ConfigurationException e) {
            assertThat(e.getAudioAnalysisExceptionType(), is(AudioAnalysisExceptionType.ExtractionConfiguration));
            assertThat(e.getConfigurationExceptionSubtype(), is(ConfigurationExceptionType.IncompatibleWindowSizes));
        }

        try {
            ModuleParams moduleParams = new ModuleParams(TEST_FREQUENCY_RATE, 0, 1, 1, 1);
            SUT.validateConfiguration(moduleParams);
            fail("This test should raise an exception");
        } catch (ConfigurationException e) {
            assertThat(e.getAudioAnalysisExceptionType(), is(AudioAnalysisExceptionType.ExtractionConfiguration));
            assertThat(e.getConfigurationExceptionSubtype(), is(ConfigurationExceptionType.InvalidParameter));
        }
        try {
            ModuleParams moduleParams = new ModuleParams(TEST_FREQUENCY_RATE, 1, -1, 1, 1);
            SUT.validateConfiguration(moduleParams);
            fail("This test should raise an exception");
        } catch (ConfigurationException e) {
            assertThat(e.getAudioAnalysisExceptionType(), is(AudioAnalysisExceptionType.ExtractionConfiguration));
            assertThat(e.getConfigurationExceptionSubtype(), is(ConfigurationExceptionType.InvalidParameter));
        }

        try {
            ModuleParams moduleParams = new ModuleParams(TEST_FREQUENCY_RATE, 1, 1, 0, 1);
            SUT.validateConfiguration(moduleParams);
            fail("This test should raise an exception");
        } catch (ConfigurationException e) {
            assertThat(e.getAudioAnalysisExceptionType(), is(AudioAnalysisExceptionType.ExtractionConfiguration));
            assertThat(e.getConfigurationExceptionSubtype(), is(ConfigurationExceptionType.InvalidParameter));
        }

        try {
            ModuleParams moduleParams = new ModuleParams(TEST_FREQUENCY_RATE, 1, 1, 1, -1);
            SUT.validateConfiguration(moduleParams);
            fail("This test should raise an exception");
        } catch (ConfigurationException e) {
            assertThat(e.getAudioAnalysisExceptionType(), is(AudioAnalysisExceptionType.ExtractionConfiguration));
            assertThat(e.getConfigurationExceptionSubtype(), is(ConfigurationExceptionType.InvalidParameter));
        }

        try {
            ModuleParams moduleParams = new ModuleParams(0, 1, 1, 1, 1);
            SUT.validateConfiguration(moduleParams);
            fail("This test should raise an exception");
        } catch (ConfigurationException e) {
            assertThat(e.getAudioAnalysisExceptionType(), is(AudioAnalysisExceptionType.ExtractionConfiguration));
            assertThat(e.getConfigurationExceptionSubtype(), is(ConfigurationExceptionType.InvalidParameter));
        }
    }

}
