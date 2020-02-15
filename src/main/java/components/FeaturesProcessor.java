package components;

import org.nd4j.linalg.api.ndarray.INDArray;
import org.nd4j.linalg.factory.Nd4j;
import org.nd4j.linalg.indexing.INDArrayIndex;
import org.nd4j.linalg.indexing.NDArrayIndex;

import static constants.FeaturesNumbersConstants.*;


public class FeaturesProcessor {

    public double EPS_CONSTANT = 0.00000001;


    INDArray extractFeaturesFromSlice(INDArray currentAudioSlice, INDArray fftAudioSlice, INDArray fftPreviousAudioSlice, int frequency_rate, int nFFT) {

        //Initialize the processors
        MfccsProcessor mfccsProcessor = new MfccsProcessor(frequency_rate, nFFT);
        ChromaProcessor chromaProcessor = new ChromaProcessor(nFFT, frequency_rate);
        EnergyProcessor energyProcessor = new EnergyProcessor(EPS_CONSTANT);
        SpectralProcessor spectralProcessor = new SpectralProcessor((EPS_CONSTANT));

        INDArray extractedFeatures = Nd4j.zeros(TOTAL_FEATURES, 1);

        // 1 : Extract Zero crossing rate
        extractedFeatures.putScalar(0, extractZeroCrossingRate(currentAudioSlice));

        // 2-3 : Audio energy features
        extractedFeatures.putScalar(1, energyProcessor.extractEnergy(currentAudioSlice));
        extractedFeatures.putScalar(2, energyProcessor.extractEnergyEntropy(currentAudioSlice, 10));

        //4-8: Audio spectral
        double[] spectralCentroidSpread = spectralProcessor.extractSpectralCentroidAndSpread(fftAudioSlice, frequency_rate);
        extractedFeatures.putScalar(3, spectralCentroidSpread[0]); // Spectral centroid
        extractedFeatures.putScalar(4, spectralCentroidSpread[1]); //Spectral spread
        extractedFeatures.putScalar(5, spectralProcessor.extractSpectralEntropy(fftAudioSlice, 10));
        extractedFeatures.putScalar(6, spectralProcessor.extractSpectralFlux(fftAudioSlice, fftPreviousAudioSlice));
        extractedFeatures.putScalar(7, spectralProcessor.extractSpectralRollOff(fftAudioSlice, 0.90, frequency_rate));

        // 9 -21: MFCCS
        double[] stMFCCs = mfccsProcessor.extractMFCC(fftAudioSlice, NCEPS_FEATURES);
        extractedFeatures.put(new INDArrayIndex[]{
                        NDArrayIndex.interval(TIME_SPECTRAL_FEATURES, TIME_SPECTRAL_FEATURES + NCEPS_FEATURES)},
                Nd4j.create(stMFCCs));

        //22-33, 34: chroma features
        INDArray stChromaFeaturesArr = chromaProcessor.extractChromaFeatures(fftAudioSlice);
        //The 12 chroma features
        extractedFeatures.put(new INDArrayIndex[]{
                NDArrayIndex.interval(TIME_SPECTRAL_FEATURES + NCEPS_FEATURES,
                        TIME_SPECTRAL_FEATURES + NCEPS_FEATURES + CHROMA_FEATURES - 1)
        }, stChromaFeaturesArr);
        //Chroma STD features
        extractedFeatures.put(TIME_SPECTRAL_FEATURES + NCEPS_FEATURES + CHROMA_FEATURES - 1,
                Nd4j.std(stChromaFeaturesArr));


        return extractedFeatures;

    }

    double extractZeroCrossingRate(INDArray x) {
        //Computes zero crossing rate of frame
        int count = x.length();
        int countz = 0;

        for (int i = 0; i < x.length() - 1; i++) {
            if ((x.getDouble(0) > 0 && x.getDouble(i + 1) <= 0)
                    ||
                    (x.getDouble(i) < 0 && x.getDouble(i + 1) >= 0)) {
                countz++;
            }
        }

        countz = countz / 2;

        return countz / (count - 1.);
    }


}
