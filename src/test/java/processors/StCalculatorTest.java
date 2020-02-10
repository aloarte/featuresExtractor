package processors;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.nd4j.linalg.api.ndarray.INDArray;

public class StCalculatorTest {

    private static int frequencyRate = 1;
    private static INDArray audioSource;  //TODO: Mock a good value of any audio source already processed
    private StCalculator SUT;

    @Before
    public void startUp() {
        SUT = new StCalculator();
    }

    @Ignore
    @Test
    public void stSpectralCentroidAndSpread() {
        double[] stSpectralCentroidAndSpread = SUT.stSpectralCentroidAndSpread(audioSource, frequencyRate);
        //  stSpectralCentroidAndSpread[0]  -> SpectralCentroid
        // stSpectralCentroidAndSpread[1]  -> SpectralSpread
        //TODO: Perform assertions against controlled values
    }

    @Ignore
    @Test
    public void stSpectralEntropy() {
        double stSpectralEntropy = SUT.stSpectralEntropy(audioSource, frequencyRate);
        //TODO: Perform assertions against controlled values
    }

    @Ignore
    @Test
    public void stSpectralFlux() {
        INDArray audioSourcePrev;  //TODO: Mock a good value of any audio source already processed

        //double stSpectralFlux = SUT.stSpectralFlux(audioSource,audioSourcePrev);
        //TODO: Perform assertions against controlled values
    }

    @Ignore
    @Test
    public void stSpectralRollOff() {
        //double stSpectralRollOff = SUT.stSpectralRollOff(audioSource,);
        //TODO: Perform assertions against controlled values
    }

    //TODO: perform the rest of the test over the StCalculator class
}
