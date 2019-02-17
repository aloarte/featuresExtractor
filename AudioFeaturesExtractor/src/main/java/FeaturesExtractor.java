import data.AudioFeatures;
import processors.FrequencyProcessor;
import processors.GenericAudioFeatures;
import processors.RythmProcessor;
import processors.SpectrumProcessor;

public class FeaturesExtractor {

    /**
     * Extract all the audio features from a raw audio source
     * @param rawAudioSource raw audio source in byte[] format
     * @return
     */
    public static AudioFeatures processAudioSource(final byte [] rawAudioSource){

        AudioFeatures extractedFeatures = new AudioFeatures();

        byte [] processedAudioSource = transformAudioInput(rawAudioSource);

        //Features from the Rythm family
        extractedFeatures.setBpm(RythmProcessor.calculateBPM(processedAudioSource));
        extractedFeatures.setBpmconf(RythmProcessor.calculateBPMConf(processedAudioSource));

        //Features from the audio spectrum family
        extractedFeatures.setSpectralCentroid(SpectrumProcessor.calculateSpectralCentroid(processedAudioSource));
        extractedFeatures.setSpectralFlux(SpectrumProcessor.calculateSpectralFlux((processedAudioSource)));

        //Features from the frequency family
        extractedFeatures.setMfccs(FrequencyProcessor.calculateMFCCS(processedAudioSource));

        //Othe features
        extractedFeatures.setZrc(GenericAudioFeatures.calculateZRC(processedAudioSource));

        return extractedFeatures;


    }

    /**
     * Performs *** audio transformation
     * @param rawAudioSource    Raw audio input
     * @return                  Processed audio output in byte[] format
     */
    private static byte[] transformAudioInput(byte[] rawAudioSource) {
        return rawAudioSource;
    }
}
