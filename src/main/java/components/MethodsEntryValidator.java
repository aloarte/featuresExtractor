package components;

import model.enums.AudioExtractionExceptionType;
import model.enums.AudioReadExtractionExceptionType;
import model.exceptions.AudioExtractionException;
import model.exceptions.AudioReadExtractionException;
import org.nd4j.linalg.api.ndarray.INDArray;

import java.util.Arrays;

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
    }

    /**
     * Verify that any input rawAudioSource is not empty nor null and its a good audio source.
     *
     * @param rawAudioSource Double[] with the audio source
     * @throws AudioReadExtractionException Exception raised if the input is bad.
     */
    public void validateAudioSource(double[] rawAudioSource) throws AudioReadExtractionException {
        if (rawAudioSource != null) {
            //Check that the audio source is not an empty double array
            if (!Arrays.equals(rawAudioSource, new double[1])) {
                //TODO: Check additional known properties related with a "good" raw audio source
            } else
                throw new AudioReadExtractionException(AudioReadExtractionExceptionType.BadAudioSourceRead, "The raw audio source seems empty.");
        } else
            throw new AudioReadExtractionException(AudioReadExtractionExceptionType.NullRawAudioSource, "The double[] raw audio source received was null.");
    }

    /**
     * Verify that the extractedFeaturesMatrix is not empty or null and the number of features matches the value estimated.
     *
     * @param extractedFeaturesMatrix INDArray matrix with [NumberOfFeatures x ValuesOfSameFeature]
     * @throws AudioExtractionException Exception raised if the input is bad.
     */
    public void verifyExtractedMatrix(INDArray extractedFeaturesMatrix) throws AudioExtractionException {
        if (extractedFeaturesMatrix != null) {
            if (extractedFeaturesMatrix.rows() != TOTAL_FEATURES) {
                throw new AudioExtractionException(AudioExtractionExceptionType.BadExtractedFeaturesMatrix, "Bad number of features. " + extractedFeaturesMatrix.length() + " extracted of the " + TOTAL_FEATURES + " total features.");
            }
        } else {
            throw new AudioExtractionException(AudioExtractionExceptionType.WrongNumberOfFeaturesExtracted, "Empty or null extracted features matrix.");
        }
    }
}
