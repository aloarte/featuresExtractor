package processors;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

public class ChromaProcessorTest {

    private ChromaProcessor SUT;


    @Before
    public void startUp() {
        //SUT = new ChromaProcessor(EPS_CONSTANT);
    }


    @Ignore
    @Test
    public void stSpectralCentroidAndSpread() {
        //double[] stSpectralCentroidAndSpread = SUT.stSpectralCentroidAndSpread(audioSource, frequencyRate);
        //  stSpectralCentroidAndSpread[0]  -> SpectralCentroid
        // stSpectralCentroidAndSpread[1]  -> SpectralSpread
        //TODO: Perform assertions against controlled values
    }

    @Ignore
    @Test
    public void stSpectralEntropy() {
        //double stSpectralEntropy = SUT.stSpectralEntropy(audioSource, frequencyRate);
        //TODO: Perform assertions against controlled values
    }
}
