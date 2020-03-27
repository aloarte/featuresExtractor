package components;

import libs.CustomOperations;
import model.ModuleParams;
import model.exceptions.AudioAnalysisException;
import org.jtransforms.fft.DoubleFFT_1D;
import org.nd4j.linalg.api.ndarray.INDArray;
import org.nd4j.linalg.factory.Nd4j;
import org.nd4j.linalg.indexing.NDArrayIndex;
import org.nd4j.linalg.ops.transforms.Transforms;
import utils.ComplexRealMatrixParser;

import java.util.Arrays;

import static constants.FeaturesNumbersConstants.TOTAL_FEATURES;


public class AudioFeaturesExtractor {

    private FeaturesProcessor featuresProcessor;

    private StatisticsExtractor statisticsExtractor;


    public AudioFeaturesExtractor() {
        this.featuresProcessor = new FeaturesProcessor();
        this.statisticsExtractor = new StatisticsExtractor();
    }


    /**
     * Iterate the samples audio source into several windows taken in steps to extract the 32 features in each window.
     *
     * @param samples
     * @param frequency_rate
     * @param sliceWindow
     * @param step
     * @return
     */
    INDArray extractAudioFeatures(double[] samples, int frequency_rate,
                                  int sliceWindow, int step) throws AudioAnalysisException {


        INDArray normalizedSamples = calculateNormalizedSamples(samples);


        int totalSamples = normalizedSamples.length();
        //Value of the position from the normalizedSamples INDArray which is being read at the moment
        int currentReadPosition = 0;
        int fftWindow = sliceWindow / 2;
        INDArray matrixStFeatures = null;
        INDArray fftPreviousAudioSlice = null;

        boolean firstSlice = true;


        while (currentReadPosition + sliceWindow - 1 < totalSamples) { //For each short-term window
            //Extract the audio slice from the whole audio source with Window as a size
            INDArray currentAudioSlice = normalizedSamples.get(NDArrayIndex.interval(currentReadPosition, currentReadPosition + sliceWindow)).dup();
            currentReadPosition += step;

            //Calculate the Fast Fourier Transform of the currentAudioSlice
            INDArray fftAudioSlice = calculateFftFromAudioSlice(currentAudioSlice, fftWindow);

            //The fftPreviousAudioSlice is a copy from a fftAudioSlice if its the first slice
            if (firstSlice) fftPreviousAudioSlice = fftAudioSlice.dup();

            //Extract the features from the different slices of audio from the same window
            INDArray extractedFeatures = featuresProcessor.extractFeaturesFromSlice(currentAudioSlice, fftAudioSlice, fftPreviousAudioSlice, frequency_rate, fftWindow);

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

        return matrixStFeatures.transpose();
    }

    /**
     * Get a INDArray normalized from the raw audioSamples array
     *
     * @param audioSamples Raw double array with the data read from the input audio
     * @return normalized INDArray containing the audioSamples
     */
    private INDArray calculateNormalizedSamples(double[] audioSamples) {
        INDArray normalizedSamples = Nd4j.create(audioSamples, new int[]{audioSamples.length});

        normalizedSamples = normalizedSamples.div(Math.pow(2, 15));

        double mean = (Nd4j.mean(normalizedSamples, 0)).getDouble(0);
        double maxValue = (Transforms.abs(normalizedSamples.dup()).maxNumber()).doubleValue();

        return normalizedSamples.sub(mean).div((maxValue) + 0.0000000001);
    }

    /**
     * Get a INDArray representing the FastFourierTransform of the passed currentAudioSlice
     *
     * @param currentAudioSlice INDArray with the audio slice to calculate its FFT
     * @param fftWindowSize     Size of the FFT window
     * @return INDArray with the FFT already calculated
     */
    INDArray calculateFftFromAudioSlice(INDArray currentAudioSlice, int fftWindowSize) {

        //Parse data to double array
        double[] audioSliceValues = currentAudioSlice.toDoubleVector();

        //Fake the imaginary component of each real value before the fft calculation
        double[] audioSliceComplexValues = ComplexRealMatrixParser.parseFromRealToComplex(audioSliceValues);

        //Get the FastFourierTransform of the audioSlice
        DoubleFFT_1D fastFourierTransform = new DoubleFFT_1D(audioSliceComplexValues.length);
        fastFourierTransform.realForward(audioSliceComplexValues);

        //The second value is zero
        audioSliceComplexValues[1] = 0;

        double[] audioSliceRealValues = ComplexRealMatrixParser.parseAbsValueFromComplexToReal(audioSliceComplexValues);

        //Replace the values
        audioSliceValues = Arrays.copyOfRange(audioSliceRealValues, 0, fftWindowSize);

        //Build a INDArray from the double[] with the FFT of slice
        return Nd4j.create(audioSliceValues).div(audioSliceValues.length);
    }


    public INDArray globalFeatureExtraction(double[] samples, final ModuleParams moduleParams) throws AudioAnalysisException {

        long timeBefore = System.currentTimeMillis();
        long timeAfter;

        //Extract the matrix with the [32 features] x [N window samples]
        INDArray matrixExtractedFeatures = extractAudioFeatures(samples, moduleParams.getFrequencyRate(), moduleParams.getShortTermWindowSize(), moduleParams.getShortTermStepSize());

        if (moduleParams.isLogProcessesDurationEnabled()) {
            timeAfter = System.currentTimeMillis();
            System.out.println("stFeatures extracted (" + (timeAfter - timeBefore) + ") : matrixExtractedFeatures [" + matrixExtractedFeatures.shape()[0] + "][" + matrixExtractedFeatures.shape()[1] + "]");
            timeBefore = timeAfter;
        }

        //Apply statistic operations to each N sample for each of the 32 features. Extract a matrix of [32 features] x [N statistic operations]
        INDArray mtFeatures = statisticsExtractor.obtainAudioFeaturesStatistics(matrixExtractedFeatures, moduleParams);


        if (moduleParams.isLogProcessesDurationEnabled()) {
            timeAfter = System.currentTimeMillis();
            System.out.println("mtFeatures extracted (" + (timeAfter - timeBefore) + ") : mtFeatures [" + mtFeatures.shape()[0] + "][" + mtFeatures.shape()[1] + "]");
        }

        return mtFeatures;

    }


}
