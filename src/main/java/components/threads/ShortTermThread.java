package components.threads;

import components.FeaturesProcessor;
import model.ModuleParams;
import org.nd4j.linalg.api.ndarray.INDArray;

public class ShortTermThread implements Runnable {
    private FeaturesProcessor featuresProcessor;
    private INDArray normalizedSamples;
    private INDArray audioFeaturesStatistics;
    private ModuleParams moduleParams;
    private int featureIndex;
    private int featureValueDataIndex;
    private int mtWinRation;
    private int mtStepRatio;

    public ShortTermThread(INDArray inputNormalizedSamples, INDArray outputAudioFeaturesStatistics, ModuleParams moduleParams, int featureIndex, int mtWinRation, int mtStepRatio) {
        this.featuresProcessor = new FeaturesProcessor();
        this.normalizedSamples = inputNormalizedSamples;
        this.audioFeaturesStatistics = outputAudioFeaturesStatistics;
        this.moduleParams = moduleParams;
        this.featureIndex = featureIndex;
        this.mtWinRation = mtWinRation;
        this.mtStepRatio = mtStepRatio;

    }

    @Override
    public void run() {
//        //Extract the audio slice from the whole audio source with Window as a size
//        INDArray currentAudioSlice = normalizedSamples.get(NDArrayIndex.interval(currentReadPosition, currentReadPosition + sWindowSize)).dup();
//        currentReadPosition += sStepSize;
//
//        //Calculate the Fast Fourier Transform of the currentAudioSlice
//        INDArray fftAudioSlice = calculateFftFromAudioSlice(currentAudioSlice, fftWindow);
//
//        //The fftPreviousAudioSlice is a copy from a fftAudioSlice if its the first slice
//        if (firstSlice) fftPreviousAudioSlice = fftAudioSlice.dup();
//
//        //Extract the features from the different slices of audio from the same window
//        INDArray extractedFeatures = featuresProcessor.extractFeaturesFromSlice(currentAudioSlice, fftAudioSlice, fftPreviousAudioSlice, frequencyRate, fftWindow);
//
//        //
//        fftPreviousAudioSlice = fftAudioSlice.dup();
//
//        //If its the first slice
//        if (firstSlice) {
//            matrixStFeatures = extractedFeatures;
//            firstSlice = false;
//        }
//        //If its not the first slice, append the extractedFeatures values to the matrix
//        else matrixStFeatures = CustomOperations.append(matrixStFeatures, extractedFeatures);

    }
}
