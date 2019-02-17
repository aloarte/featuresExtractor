package processors;

/**
 * Perform all the rythm features process
 */
public class RythmProcessor {

    /**
     * Process the audio source to retrieve a float value that represents its bpm
     * @param audioSource      Raw audio source in byte[] format
     * @return                 Float with the BPM value
     */
    public static float calculateBPM(final byte[] audioSource){


        return 0.03F;
    }

    /**
     * Process the audio source to retrieve a float value that represents its bpm configuration
     * @param audioSource      Raw audio source in byte[] format
     * @return                 Float with the BPMConf value
     */
    public static float calculateBPMConf(final byte[] audioSource){
        return 0.04F;
    }

}
