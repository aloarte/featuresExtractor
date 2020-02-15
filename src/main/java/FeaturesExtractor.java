import model.AudioFeatures;
import model.ModuleParams;
import model.enums.AudioReadExtractionExceptionType;
import model.exceptions.AudioExtractionException;
import model.exceptions.AudioReadExtractionException;
import org.nd4j.linalg.api.ndarray.INDArray;
import processors.GeneralRawProcessor;

import java.util.Arrays;
import java.util.List;

public class FeaturesExtractor {

    private DataParser dataParser;

    private GeneralRawProcessor generalRawProcessor;

    public FeaturesExtractor() {
        this.dataParser = new DataParser();
        this.generalRawProcessor = new GeneralRawProcessor();
    }

    /**
     * Extract all the audio features from a raw audio source
     *
     * @param rawAudioSource raw audio source in byte[] format
     * @return
     */
    public List<AudioFeatures> processAudioSource(final double[] rawAudioSource, final ModuleParams moduleParams) throws AudioExtractionException {

        try {
            //Check if the audio source is well read from the raw source. If anything goes wrong, throws an AudioExtractionException
            validateAudioSource(rawAudioSource);

            //Extract the global features in an INDArray
            INDArray globalFeatures = generalRawProcessor.globalFeatureExtraction(rawAudioSource, 22050, 2205, 2205, 2205, 2205, moduleParams);

            //Parse the audio features from the INDArray to the concrete AudioFeature object
            return dataParser.parseAudioFeatures(globalFeatures, moduleParams);
        } catch (AudioExtractionException audioExtractionException) {
            System.err.println(audioExtractionException.getMessage());
            throw audioExtractionException;
        }

    }

    void validateAudioSource(double[] rawAudioSource) throws AudioReadExtractionException {
        if (rawAudioSource != null) {
            //Check that the audio source is not an empty double array
            if (!Arrays.equals(rawAudioSource, new double[1])) {
                //TODO: Check additional known properties related with a "good" raw audio source
            } else
                throw new AudioReadExtractionException(AudioReadExtractionExceptionType.BAD_AUDIO_SOURCE_READ, "The raw audio source seems empty.");
        } else
            throw new AudioReadExtractionException(AudioReadExtractionExceptionType.NULL_RAW_AUDIO_SOURCE, "The double[] raw audio source received was null.");
    }


}
