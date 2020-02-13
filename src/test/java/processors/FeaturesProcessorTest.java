package processors;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.nd4j.linalg.api.ndarray.INDArray;

public class FeaturesProcessorTest {

    private static int frequencyRate = 1;
    private static INDArray audioSource;  //TODO: Mock a good value of any audio source already processed
    private FeaturesProcessor SUT;

    @Before
    public void startUp() {
        SUT = new FeaturesProcessor();
    }


    @Ignore
    @Test
    public void extractFeaturesFromSlice() {
        INDArray audioSourcePrev;  //TODO: Mock a good value of any audio source already processed

        //double stSpectralFlux = SUT.extractFeaturesFromSlice(audioSource,audioSourcePrev);
        //TODO: Perform assertions against controlled values
    }

    @Ignore
    @Test
    public void stSpectralRollOff() {
        //double stSpectralRollOff = SUT.extractZeroCrossingRate(audioSource,);
        //TODO: Perform assertions against controlled values
    }

    //TODO: perform the rest of the test over the StCalculator class
}
