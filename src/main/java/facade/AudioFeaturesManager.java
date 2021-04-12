package facade;

import components.AudioFeaturesExtractor;
import components.MethodsEntryValidator;
import model.AudioFeatures;
import model.AudioShortFeatures;
import model.ModuleParams;
import model.RawAudioFeatures;
import model.exceptions.AudioAnalysisException;
import utils.WavUtils;

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


    public AudioFeatures processAudioSource(String audioSamplesPath, final ModuleParams moduleParams) throws AudioAnalysisException {
        double[] audioSamples = WavUtils.load_wav(audioSamplesPath);
        return processAudioSource(audioSamples, moduleParams);
    }

    /**
     * Extract all the audio features from a raw audio source
     *
     * @param rawAudioSource raw audio source in byte[] format
     * @return
     */
    public AudioFeatures processAudioSource(final double[] rawAudioSource, final ModuleParams moduleParams) throws AudioAnalysisException {

        //Check the module configuration values
        validator.validateConfiguration(moduleParams);
        //Check if the audio source is well read from the raw source. If anything goes wrong, throws an AudioExtractionException
        validator.validateAudioSource(rawAudioSource, moduleParams.getFrequencyRate());

        //Extract the global features in an INDArray

        RawAudioFeatures rawAudioFeatures = audioFeaturesExtractor.featureExtraction(rawAudioSource, moduleParams);

        //Parse the audio features from the INDArray to the concrete AudioFeature object
        List<AudioShortFeatures> audioShortFeatures = dataParser.parseAudioFeatures(rawAudioFeatures.getMeanMidTermFeatures(), moduleParams);

        return new AudioFeatures(rawAudioFeatures.getBpmFeatures(), audioShortFeatures);
    }
}
