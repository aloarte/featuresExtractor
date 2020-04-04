package components;

import model.ModuleParams;
import model.enums.ConfigurationExceptionType;
import model.enums.ExtractionExceptionType;
import model.enums.ProcessingExceptionType;
import model.exceptions.ConfigurationException;
import model.exceptions.ExtractionException;
import model.exceptions.ProcessingException;
import org.nd4j.linalg.api.ndarray.INDArray;

import static constants.FeaturesNumbersConstants.TOTAL_FEATURES;
import static constants.ModuleConstants.HIGH_FREQUENCY_RATE;
import static constants.ModuleConstants.RECOMMENDED_FREQUENCY_RATE;

public class MethodsEntryValidator {

    /**
     * Verify the INDArray inputs to check that there is not any null or empty INDArray.
     *
     * @param currentAudioSlice     INDArray
     * @param fftAudioSlice         INDArray
     * @param fftPreviousAudioSlice INDArray
     * @throws ProcessingException Exception raised if the input is bad.
     */
    void verifySliceValues(INDArray currentAudioSlice, INDArray fftAudioSlice, INDArray fftPreviousAudioSlice) throws ProcessingException {
        if (currentAudioSlice == null || currentAudioSlice.length() == 0) {
            throw new ProcessingException(ProcessingExceptionType.BadCurrentAudioSlice, "Empty or null audio slice found.");
        }
        if (fftAudioSlice == null || fftAudioSlice.length() == 0) {
            throw new ProcessingException(ProcessingExceptionType.BadCurrentFftAudioSlice, "Empty or null audio slice FFT found.");
        }
        if (fftPreviousAudioSlice == null || fftPreviousAudioSlice.length() == 0) {
            throw new ProcessingException(ProcessingExceptionType.BadPreviousAudioSlice, "Empty or null previous audio slice found.");
        }

        if (fftAudioSlice.shape()[0] != fftPreviousAudioSlice.shape()[0] || fftAudioSlice.shape()[1] != fftPreviousAudioSlice.shape()[1]) {
            throw new ProcessingException(ProcessingExceptionType.fftShapesMismatch, "The sizes of the FFT slices must be the same. FFTAudio slice: [" + fftAudioSlice.shape()[0] + "," + fftAudioSlice.shape()[1] + "] FFTPreviousAudioSlice: [" + fftPreviousAudioSlice.shape()[0] + "," + fftPreviousAudioSlice.shape()[1] + "]");

        }
    }

    /**
     * Verify that any input rawAudioSource is not empty nor null and its a good audio source.
     *
     * @param rawAudioSource Double[] with the audio source
     * @throws ExtractionException Exception raised if the input is bad.
     */
    public void validateAudioSource(double[] rawAudioSource, int frequencyRate) throws ExtractionException {
        if (rawAudioSource != null) {
            //Check that the audio source is not an empty double array
            if (checkNotEmptyDataSource(rawAudioSource)) {
                if (rawAudioSource.length < frequencyRate) {
                    throw new ExtractionException(ExtractionExceptionType.TooLowSamples, "The input data contais too low samples. " + rawAudioSource.length + " samples with " + frequencyRate + " frequency rate is less than 1 second of audio.");
                }

            } else
                throw new ExtractionException(ExtractionExceptionType.BadAudioSourceRead, "The raw audio source seems empty.");
        } else
            throw new ExtractionException(ExtractionExceptionType.NullRawAudioSource, "The double[] raw audio source received was null.");
    }

    public boolean checkNotEmptyDataSource(double[] rawAudioDataSource) {
        for (double v : rawAudioDataSource) {
            if (v != 0) return true;
        }
        return false;
    }

    /**
     * Verify that the extractedFeaturesMatrix is not empty or null and the number of features matches the value estimated.
     *
     * @param extractedFeaturesMatrix INDArray matrix with [NumberOfFeatures x ValuesOfSameFeature]
     * @throws ExtractionException Exception raised if the input is bad.
     */
    public void verifyExtractedMatrix(INDArray extractedFeaturesMatrix) throws ExtractionException {
        if (extractedFeaturesMatrix != null) {
            //Verify that the shortTermMatrix have 34 rows, one row per feature
            if (extractedFeaturesMatrix.rows() != TOTAL_FEATURES) {
                throw new ExtractionException(ExtractionExceptionType.WrongNumberOfFeaturesExtracted, "Bad number of features. " + extractedFeaturesMatrix.length() + " extracted of the " + TOTAL_FEATURES + " total features.");
            }

        } else {
            throw new ExtractionException(ExtractionExceptionType.BadExtractedFeaturesMatrix, "Empty or null extracted features matrix.");
        }
    }

    /**
     * Verify that the configuration contained in the moduleParams can be used to extract audio features
     *
     * @param moduleParams Configuration made by the user
     */
    public void validateConfiguration(ModuleParams moduleParams) throws ConfigurationException {

        if (moduleParams != null) {
            validateConfigurationFrequency(moduleParams);
            validateConfigurationStMtWindowStepSize(moduleParams);
        } else
            throw new ConfigurationException(ConfigurationExceptionType.NullConfiguration, "The module configuration is null.");
    }

    private void validateConfigurationStMtWindowStepSize(ModuleParams moduleParams) throws ConfigurationException {
        if (moduleParams.getShortTermWindowSize() > 0 &&
                moduleParams.getShortTermStepSize() > 0 &&
                moduleParams.getMidTermWindowSize() > 0 &&
                moduleParams.getMidTermStepSize() > 0) {
            if (moduleParams.getShortTermWindowSize() <= moduleParams.getMidTermWindowSize()) {
                //If the step is bigger than the window size, but the force flag isn't activate,  throw an error
                if ((moduleParams.getShortTermStepSize() > moduleParams.getShortTermWindowSize()) && !moduleParams.isForceHighStepSizeEnabled()) {
                    throw new ConfigurationException(ConfigurationExceptionType.WarningHighStepSize, "The step size is larger than the window size. This will cause regions with info ignored. You can force this configuration by using forceHighStepSize()" +
                            " ShortStepSize: " + moduleParams.getShortTermStepSize() +
                            ", ShortWindowSize: " + moduleParams.getShortTermWindowSize());
                }

                if ((moduleParams.getMidTermStepSize() > moduleParams.getMidTermWindowSize()) && !moduleParams.isForceHighStepSizeEnabled()) {
                    throw new ConfigurationException(ConfigurationExceptionType.WarningHighStepSize, "The step size is larger than the window size. This will cause regions with info ignored. You can force this configuration by using forceHighStepSize()" +
                            " MidStepSize: " + moduleParams.getMidTermStepSize() +
                            ", MidWindowSize: " + moduleParams.getMidTermWindowSize());
                }

            } else
                throw new ConfigurationException(ConfigurationExceptionType.IncompatibleWindowSizes, "The value of the short window size must be less or equals than the mid window size." +
                        " ShortWindowSize: " + moduleParams.getShortTermWindowSize() +
                        ", MidWindowSize: " + moduleParams.getMidTermWindowSize());
        } else throw new ConfigurationException(ConfigurationExceptionType.InvalidParameter,
                "The input parameters had invalid values. Values must be be > 0 " +
                        " ShortWindowSize: " + moduleParams.getShortTermWindowSize() +
                        ", ShortStepSize: " + moduleParams.getShortTermStepSize() +
                        ", MidWindowSize: " + moduleParams.getMidTermWindowSize() +
                        ", MidStepSize: " + moduleParams.getMidTermStepSize());
    }

    private void validateConfigurationFrequency(ModuleParams moduleParams) throws ConfigurationException {
        if (moduleParams.getFrequencyRate() > 0) {
            if (moduleParams.getFrequencyRate() < RECOMMENDED_FREQUENCY_RATE && !moduleParams.isForceUseLowFrequencyRateEnabled()) {
                throw new ConfigurationException(ConfigurationExceptionType.WarningLowFrequencyRate, "The frequency rate is too low. We recommend you to use 22050 as your frequency rate. Otherwise you can force this configuration by using forceLowFrequencyRate()");
            }

            if ((moduleParams.getFrequencyRate() > RECOMMENDED_FREQUENCY_RATE && moduleParams.getFrequencyRate() > HIGH_FREQUENCY_RATE) && !moduleParams.isForceUseHighFrequencyRateEnabled()) {
                throw new ConfigurationException(ConfigurationExceptionType.WarningHighFrequencyRate, "The frequency rate is too high and may cause slow audio processing. We recommend you to use 22050 as your frequency rate. Otherwise you can force this configuration by using forceHighFrequencyRate()");
            }

        } else throw new ConfigurationException(ConfigurationExceptionType.InvalidParameter,
                "The input parameters had invalid values. Values must be be > 0  FrequencyRate: " + moduleParams.getFrequencyRate());
    }
}
