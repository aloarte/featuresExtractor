package components;

import libs.CustomOperations;
import model.exceptions.AudioAnalysisException;
import org.nd4j.linalg.api.ndarray.INDArray;
import org.nd4j.linalg.indexing.NDArrayIndex;

import static constants.FeaturesNumbersConstants.TOTAL_FEATURES;


public class ShortTermExtractor {

    private SlicesProcessor slicesProcessor;
    private FeaturesProcessor featuresProcessor;

    public ShortTermExtractor() {
        this.slicesProcessor = new SlicesProcessor();
        this.featuresProcessor = new FeaturesProcessor();
    }

    /**
     * Extract the short term features from the audioSamples raw values.
     *
     * @param audioSamples  raw audio data
     * @param frequencyRate frequency rate of the audioSamples
     * @param sWindowSize   short term window size
     * @param sStepSize     short term step size
     * @return INDArray of size [34][audioSamples.size/sWindowSize] with the short term features
     */
    INDArray extractShortTermFeatures(double[] audioSamples, int frequencyRate,
                                      int sWindowSize, int sStepSize) throws AudioAnalysisException {


        INDArray normalizedSamples = slicesProcessor.calculateNormalizedSamples(audioSamples);

        int totalSamples = normalizedSamples.length();
        //Value of the position from the normalizedSamples INDArray which is being read at the moment
        int currentReadPosition = 0;
        int fftWindow = sWindowSize / 2;
        INDArray matrixStFeatures = null;
        INDArray fftPreviousAudioSlice = null;

        boolean firstSlice = true;


        while (currentReadPosition + sWindowSize - 1 < totalSamples) { //For each short-term window
            //Extract the audio slice from the whole audio source with Window as a size
            INDArray currentAudioSlice = normalizedSamples.get(NDArrayIndex.interval(currentReadPosition, currentReadPosition + sWindowSize)).dup();
            currentReadPosition += sStepSize;

            //Calculate the Fast Fourier Transform of the currentAudioSlice
            INDArray fftAudioSlice = slicesProcessor.calculateFftFromAudioSlice(currentAudioSlice, fftWindow);

            //The fftPreviousAudioSlice is a copy from a fftAudioSlice if its the first slice
            if (firstSlice) fftPreviousAudioSlice = fftAudioSlice.dup();

            //Extract the features from the different slices of audio from the same window
            INDArray extractedFeatures = featuresProcessor.extractFeaturesFromSlice(currentAudioSlice, fftAudioSlice, fftPreviousAudioSlice, frequencyRate, fftWindow);

            //
            fftPreviousAudioSlice = fftAudioSlice.dup();

            //If its the first slice
            if (firstSlice) {
                matrixStFeatures = extractedFeatures;
                firstSlice = false;
            }
            //If its not the first slice, append the extractedFeatures values to the matrix
            else matrixStFeatures = CustomOperations.append(matrixStFeatures, extractedFeatures);


        }

        matrixStFeatures = matrixStFeatures.reshape(matrixStFeatures.length() / TOTAL_FEATURES, TOTAL_FEATURES);
        return matrixStFeatures.transpose().dup();
    }

}
