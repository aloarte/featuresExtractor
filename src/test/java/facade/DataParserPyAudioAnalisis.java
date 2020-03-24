package facade;

import model.AudioFeatures;
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
    List<AudioFeatures> parseAudioFeaturesPython(INDArray smExtractedFeatures, final ModuleParams moduleParams) {
        if (smExtractedFeatures != null && smExtractedFeatures.length() == (TOTAL_FEATURES * 2 * moduleParams.getStatisticalMeasuresNumber())) {
            List<AudioFeatures> parsedFeatures = new ArrayList<>();
            for (int statisticalMeasureIndex = 0; statisticalMeasureIndex < moduleParams.getStatisticalMeasuresNumber(); statisticalMeasureIndex++) {
                //Create the AudioFeatures class and set the statistical measure type
                AudioFeatures audioFeatures = new AudioFeatures();
                audioFeatures.setStatisticalMeasureType(moduleParams.getStatisticalMeasures().get(statisticalMeasureIndex));

                //Add each set of features to the final AudioFeatures class
                audioFeatures.setZeroCrossingRate(smExtractedFeatures.getDouble(statisticalMeasureIndex * 68));
                audioFeatures.setEnergyFeatures(parseEnergyFeatures(smExtractedFeatures, statisticalMeasureIndex * 68));
                audioFeatures.setSpectralFeatures(parseSpectralFeatures(smExtractedFeatures, statisticalMeasureIndex * 68));
                audioFeatures.setMfcCs(parseMFFCS(smExtractedFeatures, statisticalMeasureIndex * 68));
                audioFeatures.setChromaFeatures(parseChromaFeatures(smExtractedFeatures, statisticalMeasureIndex * 68));

                //Add the AudioFeatures calculated to the list of AudioFeatures
                parsedFeatures.add(audioFeatures);
            }
            //Return the list of AudioFeatures
            return parsedFeatures;
        } else return null;
    }
}
