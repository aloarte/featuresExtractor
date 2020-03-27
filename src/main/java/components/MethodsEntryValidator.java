package components;

import model.ModuleParams;
import model.enums.AudioExtractionExceptionType;
import model.enums.AudioReadExtractionExceptionType;
import model.exceptions.AudioExtractionException;
import model.exceptions.AudioReadExtractionException;
import org.nd4j.linalg.api.ndarray.INDArray;

import static constants.FeaturesNumbersConstants.TOTAL_FEATURES;

public class MethodsEntryValidator {

    /**
     * Verify the INDArray inputs to check that there is not any null or empty INDArray.
     *
     * @param currentAudioSlice     INDArray
     * @param fftAudioSlice         INDArray
     * @param fftPreviousAudioSlice INDArray
     * @throws AudioExtractionException Exception raised if the input is bad.
     */
    void verifySliceValues(INDArray currentAudioSlice, INDArray fftAudioSlice, INDArray fftPreviousAudioSlice) throws AudioExtractionException {
        if (currentAudioSlice == null || currentAudioSlice.length() == 0) {
            throw new AudioExtractionException(AudioExtractionExceptionType.BadCurrentAudioSlice, "Empty or null audio slice found.");
        }
        if (fftAudioSlice == null || fftAudioSlice.length() == 0) {
            throw new AudioExtractionException(AudioExtractionExceptionType.BadCurrentFftAudioSlice, "Empty or null audio slice FFT found.");
        }
        if (fftPreviousAudioSlice == null || fftPreviousAudioSlice.length() == 0) {
            throw new AudioExtractionException(AudioExtractionExceptionType.BadPreviousAudioSlice, "Empty or null previous audio slice found.");
        }

        if (fftAudioSlice.shape()[0] != fftPreviousAudioSlice.shape()[0] || fftAudioSlice.shape()[1] != fftPreviousAudioSlice.shape()[1]) {
            throw new AudioExtractionException(AudioExtractionExceptionType.fftShapesMismatch, "The sizes of the FFT slices must be the same. FFTAudio slice: [" + fftAudioSlice.shape()[0] + "," + fftAudioSlice.shape()[1] + "] FFTPreviousAudioSlice: [" + fftPreviousAudioSlice.shape()[0] + "," + fftPreviousAudioSlice.shape()[1] + "]");

        }
    }

    /**
     * Verify that any input rawAudioSource is not empty nor null and its a good audio source.
     *
     * @param rawAudioSource Double[] with the audio source
     * @throws AudioReadExtractionException Exception raised if the input is bad.
     */
    public void validateAudioSource(double[] rawAudioSource, int frequencyRate) throws AudioReadExtractionException {
        if (rawAudioSource != null) {
            //Check that the audio source is not an empty double array
            if (checkNotEmptyDataSource(rawAudioSource)) {
                if (rawAudioSource.length < frequencyRate) {
                    throw new AudioReadExtractionException(AudioReadExtractionExceptionType.TooLowSamples, "The input data contais too low samples. " + rawAudioSource.length + " samples with " + frequencyRate + " frequency rate is less than 1 second of audio.");
                }

            } else
                throw new AudioReadExtractionException(AudioReadExtractionExceptionType.BadAudioSourceRead, "The raw audio source seems empty.");
        } else
            throw new AudioReadExtractionException(AudioReadExtractionExceptionType.NullRawAudioSource, "The double[] raw audio source received was null.");
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
     * @throws AudioExtractionException Exception raised if the input is bad.
     */
    public void verifyExtractedMatrix(INDArray extractedFeaturesMatrix) throws AudioExtractionException {
        if (extractedFeaturesMatrix != null) {
            //Verify that the shortTermMatrix have 34 rows, one row per feature
            if (extractedFeaturesMatrix.rows() != TOTAL_FEATURES) {
                throw new AudioExtractionException(AudioExtractionExceptionType.WrongNumberOfFeaturesExtracted, "Bad number of features. " + extractedFeaturesMatrix.length() + " extracted of the " + TOTAL_FEATURES + " total features.");
            }

        } else {
            throw new AudioExtractionException(AudioExtractionExceptionType.BadExtractedFeaturesMatrix, "Empty or null extracted features matrix.");
        }
    }

    /**
     * Verify that the configuration contained in the moduleParams can be used to extract audio features
     *
     * @param moduleParams Configuration made by the user
     */
    public void validateConfiguration(ModuleParams moduleParams) {
        //TODO: Implement validations over the configuration
    }
}
