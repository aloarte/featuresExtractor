import model.*;
import org.nd4j.linalg.api.ndarray.INDArray;

import java.util.ArrayList;
import java.util.List;

import static constants.FeaturesNumbersConstants.*;

public class DataParser {


    List<AudioFeatures> parseAudioFeatures(INDArray extractedFeatures, int numberOfStatistics) {
        if (extractedFeatures != null && extractedFeatures.length() == 68) {
            List<AudioFeatures> parsedFeatures = new ArrayList<>();
            for (int i = 0; i < numberOfStatistics; i++) {
                AudioFeatures audioFeatures = new AudioFeatures();

                audioFeatures.setZeroCrossingRate(extractedFeatures.getDouble(i));
                audioFeatures.setEnergyFeatures(parseEnergyFeatures(extractedFeatures, numberOfStatistics));
                audioFeatures.setSpectralFeatures(parseSpectralFeatures(extractedFeatures, numberOfStatistics));
                audioFeatures.setMfcCs(parseMFFCS(extractedFeatures, numberOfStatistics));
                audioFeatures.setChromaFeatures(parseChromaFeatures(extractedFeatures, numberOfStatistics));

                parsedFeatures.add(audioFeatures);
            }
            return parsedFeatures;
        } else return null;
    }

    EnergyFeatures parseEnergyFeatures(INDArray extractedFeatures, int currentStatisticPosition) {
        return new EnergyFeatures(
                extractedFeatures.getDouble(currentStatisticPosition + 1),
                extractedFeatures.getDouble(currentStatisticPosition + 2));
    }

    SpectralFeatures parseSpectralFeatures(INDArray extractedFeatures, int currentStatisticPosition) {
        return new SpectralFeatures(
                extractedFeatures.getDouble(currentStatisticPosition + 3),
                extractedFeatures.getDouble(currentStatisticPosition + 4),
                extractedFeatures.getDouble(currentStatisticPosition + 5),
                extractedFeatures.getDouble(currentStatisticPosition + 6),
                extractedFeatures.getDouble(currentStatisticPosition + 7));
    }

    MFCCs parseMFFCS(INDArray extractedFeatures, int currentStatisticPosition) {

//        extractedFeatures.getDouble(currentStatisticPosition + TIME_SPECTRAL_FEATURES);
//        extractedFeatures.getDouble(currentStatisticPosition + TIME_SPECTRAL_FEATURES +1);
//        extractedFeatures.getDouble(currentStatisticPosition + TIME_SPECTRAL_FEATURES+2);
//        extractedFeatures.getDouble(currentStatisticPosition + TIME_SPECTRAL_FEATURES+3);
//        extractedFeatures.getDouble(currentStatisticPosition + TIME_SPECTRAL_FEATURES+4);
//        extractedFeatures.getDouble(currentStatisticPosition + TIME_SPECTRAL_FEATURES+5);
//        extractedFeatures.getDouble(currentStatisticPosition + TIME_SPECTRAL_FEATURES+6);
//        extractedFeatures.getDouble(currentStatisticPosition + TIME_SPECTRAL_FEATURES+7);
//        extractedFeatures.getDouble(currentStatisticPosition + TIME_SPECTRAL_FEATURES+8);
//        extractedFeatures.getDouble(currentStatisticPosition + TIME_SPECTRAL_FEATURES+9);
//        extractedFeatures.getDouble(currentStatisticPosition + TIME_SPECTRAL_FEATURES+10);
//        extractedFeatures.getDouble(currentStatisticPosition + TIME_SPECTRAL_FEATURES+11);
//        extractedFeatures.getDouble(currentStatisticPosition + TIME_SPECTRAL_FEATURES+12);

        return new MFCCs();
    }

    ChromaFeatures parseChromaFeatures(INDArray extractedFeatures, int currentStatisticPosition) {

        double[] chromaFeatures = new double[]{
                extractedFeatures.getDouble(currentStatisticPosition + TIME_SPECTRAL_FEATURES + NCEPS_FEATURES),
                extractedFeatures.getDouble(currentStatisticPosition + TIME_SPECTRAL_FEATURES + NCEPS_FEATURES + 1),
                extractedFeatures.getDouble(currentStatisticPosition + TIME_SPECTRAL_FEATURES + NCEPS_FEATURES + 2),
                extractedFeatures.getDouble(currentStatisticPosition + TIME_SPECTRAL_FEATURES + NCEPS_FEATURES + 3),
                extractedFeatures.getDouble(currentStatisticPosition + TIME_SPECTRAL_FEATURES + NCEPS_FEATURES + 4),
                extractedFeatures.getDouble(currentStatisticPosition + TIME_SPECTRAL_FEATURES + NCEPS_FEATURES + 5),
                extractedFeatures.getDouble(currentStatisticPosition + TIME_SPECTRAL_FEATURES + NCEPS_FEATURES + 6),
                extractedFeatures.getDouble(currentStatisticPosition + TIME_SPECTRAL_FEATURES + NCEPS_FEATURES + 7),
                extractedFeatures.getDouble(currentStatisticPosition + TIME_SPECTRAL_FEATURES + NCEPS_FEATURES + 8),
                extractedFeatures.getDouble(currentStatisticPosition + TIME_SPECTRAL_FEATURES + NCEPS_FEATURES + 9),
                extractedFeatures.getDouble(currentStatisticPosition + TIME_SPECTRAL_FEATURES + NCEPS_FEATURES + 10),
                extractedFeatures.getDouble(currentStatisticPosition + TIME_SPECTRAL_FEATURES + NCEPS_FEATURES + 11)
        };

        return new ChromaFeatures(chromaFeatures, extractedFeatures.getDouble(currentStatisticPosition + TIME_SPECTRAL_FEATURES + NCEPS_FEATURES + CHROMA_FEATURES - 1));
    }


}
