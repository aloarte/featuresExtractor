package facade;

import model.AudioShortFeatures;
import model.ModuleParams;
import org.nd4j.linalg.api.ndarray.INDArray;

import java.util.ArrayList;
import java.util.List;

import static constants.FeaturesNumbersConstants.TOTAL_FEATURES;

public class DataParserPyAudioAnalisis extends DataParser {

    /**
     * Parse the INDArray of statistical measures of the extracted features into a list of AudioFeatures
     *
     * @param smExtractedFeatures INDArray containing the statistical measures of each extracted feature
     * @param moduleParams        ModuleParams with the configuration selected by the user
     * @return A list of AudioFeatures with the same number of AudioFeatures as the number of statistical measures.
     */
    List<AudioShortFeatures> parseAudioFeaturesPython(INDArray smExtractedFeatures, final ModuleParams moduleParams) {
        if (smExtractedFeatures != null && smExtractedFeatures.length() == (TOTAL_FEATURES * 2 * moduleParams.getStatisticalMeasuresNumber())) {
            List<AudioShortFeatures> parsedFeatures = new ArrayList<>();
            for (int statisticalMeasureIndex = 0; statisticalMeasureIndex < moduleParams.getStatisticalMeasuresNumber(); statisticalMeasureIndex++) {
                //Create the AudioFeatures class and set the statistical measure type
                AudioShortFeatures audioShortFeatures = new AudioShortFeatures();
                audioShortFeatures.setStatisticalMeasureType(moduleParams.getStatisticalMeasures().get(statisticalMeasureIndex));

                //Add each set of features to the final AudioFeatures class
                audioShortFeatures.setZeroCrossingRate(smExtractedFeatures.getDouble(statisticalMeasureIndex * 68));
                audioShortFeatures.setEnergyFeatures(parseEnergyFeatures(smExtractedFeatures, statisticalMeasureIndex * 68));
                audioShortFeatures.setSpectralFeatures(parseSpectralFeatures(smExtractedFeatures, statisticalMeasureIndex * 68));
                audioShortFeatures.setMfcCs(parseMFFCS(smExtractedFeatures, statisticalMeasureIndex * 68));
                audioShortFeatures.setChromaFeatures(parseChromaFeatures(smExtractedFeatures, statisticalMeasureIndex * 68));

                //Add the AudioFeatures calculated to the list of AudioFeatures
                parsedFeatures.add(audioShortFeatures);
            }
            //Return the list of AudioFeatures
            return parsedFeatures;
        } else return null;
    }
}
