package components;

import model.exceptions.AudioExtractionException;
import org.nd4j.linalg.api.ndarray.INDArray;
import org.nd4j.linalg.factory.Nd4j;
import org.nd4j.linalg.indexing.INDArrayIndex;
import org.nd4j.linalg.indexing.NDArrayIndex;

import static constants.FeaturesNumbersConstants.*;
import static constants.ModuleConstants.EPS_CONSTANT;


public class FeaturesProcessor {

    private MethodsEntryValidator validator;

    public FeaturesProcessor() {
        validator = new MethodsEntryValidator();
    }

    INDArray extractFeaturesFromSlice(INDArray currentAudioSlice, INDArray fftAudioSlice, INDArray fftPreviousAudioSlice, int frequencyRate, int nFFT) throws AudioExtractionException {
        //System.out.println("extractFeaturesFromSlice: currentAudioSlice ["+ currentAudioSlice.shape()[0] +"]["+ currentAudioSlice.shape()[1] +"] fftAudioSlice [" + fftAudioSlice.shape()[0] +"]["+ fftAudioSlice.shape()[1] +"]");


        //verify the inputs
        validator.verifySliceValues(currentAudioSlice, fftAudioSlice, fftPreviousAudioSlice);

        //Initialize the processors
        MfccsProcessor mfccsProcessor = MfccsProcessor.getInstance(frequencyRate, nFFT);
        ChromaProcessor chromaProcessor = ChromaProcessor.getInstance(frequencyRate, nFFT);
        EnergyProcessor energyProcessor = EnergyProcessor.getInstance(EPS_CONSTANT);
        SpectralProcessor spectralProcessor = SpectralProcessor.getInstance(EPS_CONSTANT);

        INDArray extractedFeatures = Nd4j.zeros(TOTAL_FEATURES, 1);

        // 1 : Extract Zero crossing rate
        extractedFeatures.putScalar(0, extractZeroCrossingRate(currentAudioSlice));

        // 2-3 : Audio energy features
        extractedFeatures.putScalar(1, energyProcessor.extractEnergy(currentAudioSlice));
        extractedFeatures.putScalar(2, energyProcessor.extractEnergyEntropy(currentAudioSlice, 10));

        //4-8: Audio spectral
        double[] spectralCentroidSpread = spectralProcessor.extractSpectralCentroidAndSpread(fftAudioSlice, frequencyRate);
        extractedFeatures.putScalar(3, spectralCentroidSpread[0]); // Spectral centroid
        extractedFeatures.putScalar(4, spectralCentroidSpread[1]); //Spectral spread
        extractedFeatures.putScalar(5, spectralProcessor.extractSpectralEntropy(fftAudioSlice, 10));
        extractedFeatures.putScalar(6, spectralProcessor.extractSpectralFlux(fftAudioSlice, fftPreviousAudioSlice));
        extractedFeatures.putScalar(7, spectralProcessor.extractSpectralRollOff(fftAudioSlice, 0.90, frequencyRate));

        // 9 -21: MFCCS
        double[] stMFCCs = mfccsProcessor.extractMFCC(fftAudioSlice, MFCCS_FEATURES);
        extractedFeatures.put(new INDArrayIndex[]{
                        NDArrayIndex.interval(TIME_SPECTRAL_FEATURES, TIME_SPECTRAL_FEATURES + MFCCS_FEATURES)},
                Nd4j.create(stMFCCs));

        //22-33, 34: chroma features
        double[] stChromaFeaturesArr = chromaProcessor.extractChromaFeatures(fftAudioSlice);
        //The 12 chroma features
        extractedFeatures.put(new INDArrayIndex[]{
                NDArrayIndex.interval(TIME_SPECTRAL_FEATURES + MFCCS_FEATURES,
                        TIME_SPECTRAL_FEATURES + MFCCS_FEATURES + CHROMA_FEATURES - 1)
        }, Nd4j.create(stChromaFeaturesArr));
        //Chroma STD features
        extractedFeatures.put(TIME_SPECTRAL_FEATURES + MFCCS_FEATURES + CHROMA_FEATURES - 1,
                Nd4j.std(Nd4j.create(stChromaFeaturesArr)));


        return extractedFeatures;

    }

    double extractZeroCrossingRate(INDArray currentAudioSlice) {
        //Computes zero crossing rate of frame
        int count = currentAudioSlice.length();
        int countz = 0;

        for (int i = 0; i < currentAudioSlice.length() - 1; i++) {
            if ((currentAudioSlice.getDouble(i) > 0 && currentAudioSlice.getDouble(i + 1) <= 0)
                    ||
                    (currentAudioSlice.getDouble(i) < 0 && currentAudioSlice.getDouble(i + 1) >= 0)) {
                countz++;
            }
        }

        //countz = countz / 2;

        return countz / (count - 1.);
    }


}
