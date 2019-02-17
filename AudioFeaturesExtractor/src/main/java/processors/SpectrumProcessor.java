package processors;

/**
 * Class that performs all the spectrum features process.
 */
public class SpectrumProcessor {

    /**
     * Process the audio source to retrieve a float value that represents its spectral flux
     * @param audioSource      Raw audio source in byte[] format
     * @return                 Float with the SpectralFlux value
     */
    public static float calculateSpectralFlux(final byte[] audioSource){


        return 0.01F;
    }

    /**
     * Process the audio source to retrieve a float value that represents its spectral centroid
     * @param audioSource      Raw audio source in byte[] format
     * @return                 Float with the SpectralCentroid value
     */
    public static float calculateSpectralCentroid(final byte[] audioSource){
        return 0.02F;
    }
}
