package facade;

import model.*;
import org.nd4j.linalg.api.ndarray.INDArray;

import java.util.ArrayList;
import java.util.List;

import static constants.FeaturesNumbersConstants.*;

public class DataParser {


    public DataParser() {

    }

    /**
     * Parse the INDArray of statistical measures of the extracted features into a list of AudioFeatures
     *
     * @param smExtractedFeatures INDArray containing the statistical measures of each extracted feature
     * @param moduleParams        ModuleParams with the configuration selected by the user
     * @return A list of AudioFeatures with the same number of AudioFeatures as the number of statistical measures.
     */
    List<AudioFeatures> parseAudioFeatures(INDArray smExtractedFeatures, final ModuleParams moduleParams) {
        if (smExtractedFeatures != null && smExtractedFeatures.length() == (TOTAL_FEATURES * moduleParams.getStatisticalMeasuresNumber())) {
            List<AudioFeatures> parsedFeatures = new ArrayList<>();
            for (int statisticalMeasureIndex = 0; statisticalMeasureIndex < moduleParams.getStatisticalMeasuresNumber(); statisticalMeasureIndex++) {
                //Create the AudioFeatures class and set the statistical measure type
                AudioFeatures audioFeatures = parseAudioFeature(smExtractedFeatures, statisticalMeasureIndex);
                audioFeatures.setStatisticalMeasureType(moduleParams.getStatisticalMeasures().get(statisticalMeasureIndex));

//                //Add each set of features to the final AudioFeatures class
//                audioFeatures.setZeroCrossingRate(smExtractedFeatures.getDouble(statisticalMeasureIndex));
//                audioFeatures.setEnergyFeatures(parseEnergyFeatures(smExtractedFeatures, statisticalMeasureIndex));
//                audioFeatures.setSpectralFeatures(parseSpectralFeatures(smExtractedFeatures, statisticalMeasureIndex));
//                audioFeatures.setMfcCs(parseMFFCS(smExtractedFeatures, statisticalMeasureIndex));
//                audioFeatures.setChromaFeatures(parseChromaFeatures(smExtractedFeatures, statisticalMeasureIndex));

                //Add the AudioFeatures calculated to the list of AudioFeatures
                parsedFeatures.add(audioFeatures);
            }
            //Return the list of AudioFeatures
            return parsedFeatures;
        } else return null;
    }

    public AudioFeatures parseAudioFeature(INDArray smExtractedFeatures, int statisticalMeasureIndex) {
        AudioFeatures audioFeatures = new AudioFeatures();
        //Add each set of features to the final AudioFeatures class
        audioFeatures.setZeroCrossingRate(smExtractedFeatures.getDouble(statisticalMeasureIndex));
        audioFeatures.setEnergyFeatures(parseEnergyFeatures(smExtractedFeatures, statisticalMeasureIndex));
        audioFeatures.setSpectralFeatures(parseSpectralFeatures(smExtractedFeatures, statisticalMeasureIndex));
        audioFeatures.setMfcCs(parseMFFCS(smExtractedFeatures, statisticalMeasureIndex));
        audioFeatures.setChromaFeatures(parseChromaFeatures(smExtractedFeatures, statisticalMeasureIndex));

        return audioFeatures;
    }

    /**
     * Obtain the EnergyFeatures from the INDArray of statistical measures of the extracted features
     *
     * @param smExtractedFeatures      INDArray containing the statistical measures of each extracted feature
     * @param currentStatisticPosition index of the position to retrieve the proper values
     * @return A class with the EnergyFeatures already parsed from the input
     */
    EnergyFeatures parseEnergyFeatures(INDArray smExtractedFeatures, int currentStatisticPosition) {
        return new EnergyFeatures(
                smExtractedFeatures.getDouble(currentStatisticPosition + 1),
                smExtractedFeatures.getDouble(currentStatisticPosition + 2));
    }

    /**
     * Obtain the SpectralFeatures from the INDArray of statistical measures of the extracted features
     *
     * @param smExtractedFeatures      INDArray containing the statistical measures of each extracted feature
     * @param currentStatisticPosition index of the position to retrieve the proper values
     * @return A class with the EnergyFeatures already parsed from the input
     */
    SpectralFeatures parseSpectralFeatures(INDArray smExtractedFeatures, int currentStatisticPosition) {
        return new SpectralFeatures(
                smExtractedFeatures.getDouble(currentStatisticPosition + 3),
                smExtractedFeatures.getDouble(currentStatisticPosition + 4),
                smExtractedFeatures.getDouble(currentStatisticPosition + 5),
                smExtractedFeatures.getDouble(currentStatisticPosition + 6),
                smExtractedFeatures.getDouble(currentStatisticPosition + 7));
    }

    /**
     * Obtain the MFCCs from the INDArray of statistical measures of the extracted features
     *
     * @param smExtractedFeatures      INDArray containing the statistical measures of each extracted feature
     * @param currentStatisticPosition index of the position to retrieve the proper values
     * @return A class with the EnergyFeatures already parsed from the input
     */
    MFCCs parseMFFCS(INDArray smExtractedFeatures, int currentStatisticPosition) {

        double[] mfccsData = new double[13];

        mfccsData[0] = smExtractedFeatures.getDouble(currentStatisticPosition + TIME_SPECTRAL_FEATURES);
        mfccsData[1] = smExtractedFeatures.getDouble(currentStatisticPosition + TIME_SPECTRAL_FEATURES + 1);
        mfccsData[2] = smExtractedFeatures.getDouble(currentStatisticPosition + TIME_SPECTRAL_FEATURES + 2);
        mfccsData[3] = smExtractedFeatures.getDouble(currentStatisticPosition + TIME_SPECTRAL_FEATURES + 3);
        mfccsData[4] = smExtractedFeatures.getDouble(currentStatisticPosition + TIME_SPECTRAL_FEATURES + 4);
        mfccsData[5] = smExtractedFeatures.getDouble(currentStatisticPosition + TIME_SPECTRAL_FEATURES + 5);
        mfccsData[6] = smExtractedFeatures.getDouble(currentStatisticPosition + TIME_SPECTRAL_FEATURES + 6);
        mfccsData[7] = smExtractedFeatures.getDouble(currentStatisticPosition + TIME_SPECTRAL_FEATURES + 7);
        mfccsData[8] = smExtractedFeatures.getDouble(currentStatisticPosition + TIME_SPECTRAL_FEATURES + 8);
        mfccsData[9] = smExtractedFeatures.getDouble(currentStatisticPosition + TIME_SPECTRAL_FEATURES + 9);
        mfccsData[10] = smExtractedFeatures.getDouble(currentStatisticPosition + TIME_SPECTRAL_FEATURES + 10);
        mfccsData[11] = smExtractedFeatures.getDouble(currentStatisticPosition + TIME_SPECTRAL_FEATURES + 11);
        mfccsData[12] = smExtractedFeatures.getDouble(currentStatisticPosition + TIME_SPECTRAL_FEATURES + 12);

        return new MFCCs(mfccsData);
    }

    /**
     * Obtain the ChromaFeatures from the INDArray of statistical measures of the extracted features
     *
     * @param smExtractedFeatures      INDArray containing the statistical measures of each extracted feature
     * @param currentStatisticPosition index of the position to retrieve the proper values
     * @return A class with the EnergyFeatures already parsed from the input
     */
    ChromaFeatures parseChromaFeatures(INDArray smExtractedFeatures, int currentStatisticPosition) {

        double[] chromaFeatures = new double[]{
                smExtractedFeatures.getDouble(currentStatisticPosition + TIME_SPECTRAL_FEATURES + MFCCS_FEATURES),
                smExtractedFeatures.getDouble(currentStatisticPosition + TIME_SPECTRAL_FEATURES + MFCCS_FEATURES + 1),
                smExtractedFeatures.getDouble(currentStatisticPosition + TIME_SPECTRAL_FEATURES + MFCCS_FEATURES + 2),
                smExtractedFeatures.getDouble(currentStatisticPosition + TIME_SPECTRAL_FEATURES + MFCCS_FEATURES + 3),
                smExtractedFeatures.getDouble(currentStatisticPosition + TIME_SPECTRAL_FEATURES + MFCCS_FEATURES + 4),
                smExtractedFeatures.getDouble(currentStatisticPosition + TIME_SPECTRAL_FEATURES + MFCCS_FEATURES + 5),
                smExtractedFeatures.getDouble(currentStatisticPosition + TIME_SPECTRAL_FEATURES + MFCCS_FEATURES + 6),
                smExtractedFeatures.getDouble(currentStatisticPosition + TIME_SPECTRAL_FEATURES + MFCCS_FEATURES + 7),
                smExtractedFeatures.getDouble(currentStatisticPosition + TIME_SPECTRAL_FEATURES + MFCCS_FEATURES + 8),
                smExtractedFeatures.getDouble(currentStatisticPosition + TIME_SPECTRAL_FEATURES + MFCCS_FEATURES + 9),
                smExtractedFeatures.getDouble(currentStatisticPosition + TIME_SPECTRAL_FEATURES + MFCCS_FEATURES + 10),
                smExtractedFeatures.getDouble(currentStatisticPosition + TIME_SPECTRAL_FEATURES + MFCCS_FEATURES + 11)
        };

        return new ChromaFeatures(chromaFeatures, smExtractedFeatures.getDouble(currentStatisticPosition + TIME_SPECTRAL_FEATURES + MFCCS_FEATURES + CHROMA_FEATURES - 1));
    }


}
