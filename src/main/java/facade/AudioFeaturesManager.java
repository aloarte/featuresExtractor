package facade;

import components.AudioFeaturesExtractor;
import components.MethodsEntryValidator;
import model.AudioFeatures;
import model.ModuleParams;
import model.exceptions.AudioExtractionException;
import org.nd4j.linalg.api.ndarray.INDArray;

import java.util.List;

public class AudioFeaturesManager {

    private DataParser dataParser;

    private AudioFeaturesExtractor audioFeaturesExtractor;

    private MethodsEntryValidator validator;

    public AudioFeaturesManager() {
        this.dataParser = new DataParser();
        this.audioFeaturesExtractor = new AudioFeaturesExtractor();
        this.validator = new MethodsEntryValidator();
    }

    /**
     * Extract all the audio features from a raw audio source
     *
     * @param rawAudioSource raw audio source in byte[] format
     * @return
     */
    public List<AudioFeatures> processAudioSource(final double[] rawAudioSource, final ModuleParams moduleParams) throws AudioExtractionException {

        try {
            validator.validateConfiguration(moduleParams);

            //Check if the audio source is well read from the raw source. If anything goes wrong, throws an AudioExtractionException
            validator.validateAudioSource(rawAudioSource, moduleParams.getFrequencyRate());

            //Extract the global features in an INDArray
            INDArray globalFeatures = audioFeaturesExtractor.globalFeatureExtraction(rawAudioSource, moduleParams);


            //Parse the audio features from the INDArray to the concrete AudioFeature object
            return dataParser.parseAudioFeatures(globalFeatures, moduleParams);
        } catch (AudioExtractionException audioExtractionException) {
            System.err.println(audioExtractionException.getMessage());
            throw audioExtractionException;
        }

    }


}
