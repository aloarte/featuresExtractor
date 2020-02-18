package components;

import model.exceptions.AudioExtractionException;
import org.apache.commons.lang3.ArrayUtils;
import org.nd4j.linalg.api.ndarray.INDArray;
import org.nd4j.linalg.factory.Nd4j;
import org.nd4j.linalg.indexing.INDArrayIndex;
import org.nd4j.linalg.indexing.NDArrayIndex;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

import static constants.FeaturesNumbersConstants.*;
import static constants.ModuleConstants.EPS_CONSTANT;


public class FeaturesProcessor {

    private MethodsEntryValidator validator;

    public FeaturesProcessor() {
        validator = new MethodsEntryValidator();
    }

    private static void writeINDArrayFromFile(INDArray indArrayData, String filename) {

        double[] data = indArrayData.data().asDouble();

        Double[] dataDouble = ArrayUtils.toObject(data);


        ArrayList<Double> yourArray = new ArrayList<>(Arrays.asList(dataDouble));
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(filename))) {
            bw.write(data.length + "\n");
            for (double line : yourArray) {
                bw.write(line + "");
                bw.newLine();
            }

            bw.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    INDArray extractFeaturesFromSlice(INDArray currentAudioSlice, INDArray fftAudioSlice, INDArray fftPreviousAudioSlice, int frequencyRate, int nFFT) throws AudioExtractionException {
        //verify the inputs
        validator.verifySliceValues(currentAudioSlice, fftAudioSlice, fftPreviousAudioSlice);

//        writeINDArrayFromFile(currentAudioSlice,"G:\\JavaProjects\\featuresExtractorGH\\src\\test\\resources\\CurrentAudioSliceSample.txt");
//        writeINDArrayFromFile(fftAudioSlice,"G:\\JavaProjects\\featuresExtractorGH\\src\\test\\resources\\FFTCurrentAudioSliceSample.txt");
//        writeINDArrayFromFile(fftPreviousAudioSlice,"G:\\JavaProjects\\featuresExtractorGH\\src\\test\\resources\\FFTPreviousAudioSliceSample.txt");


        //Initialize the processors
        MfccsProcessor mfccsProcessor = new MfccsProcessor(frequencyRate, nFFT);
        ChromaProcessor chromaProcessor = new ChromaProcessor(nFFT, frequencyRate);
        EnergyProcessor energyProcessor = new EnergyProcessor(EPS_CONSTANT);
        SpectralProcessor spectralProcessor = new SpectralProcessor((EPS_CONSTANT));

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

    double extractZeroCrossingRate(INDArray currentAudioSlice) {
        //Computes zero crossing rate of frame
        int count = currentAudioSlice.length();
        int countz = 0;

        for (int i = 0; i < currentAudioSlice.length() - 1; i++) {
            if ((currentAudioSlice.getDouble(0) > 0 && currentAudioSlice.getDouble(i + 1) <= 0)
                    ||
                    (currentAudioSlice.getDouble(i) < 0 && currentAudioSlice.getDouble(i + 1) >= 0)) {
                countz++;
            }
        }

        countz = countz / 2;

        return countz / (count - 1.);
    }


}
