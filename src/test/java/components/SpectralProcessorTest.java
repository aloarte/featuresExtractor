package components;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import static testutils.TestingConstants.EPS_CONSTANT;

public class SpectralProcessorTest {

    private SpectralProcessor SUT;

    @Before
    public void startUp() {
        SUT = new SpectralProcessor(EPS_CONSTANT);
    }

    @Ignore
    @Test
    public void stSpectralCentroidAndSpread() {
        //double[] stSpectralCentroidAndSpread = SUT.extractSpectralCentroidAndSpread(audioSource, frequencyRate);
        //  stSpectralCentroidAndSpread[0]  -> SpectralCentroid
        // stSpectralCentroidAndSpread[1]  -> SpectralSpread
        //TODO: Perform assertions against controlled values
    }

    @Ignore
    @Test
    public void stSpectralEntropy() {
        //double stSpectralEntropy = SUT.extractSpectralEntropy(audioSource, frequencyRate);
        //TODO: Perform assertions against controlled values
    }
}
