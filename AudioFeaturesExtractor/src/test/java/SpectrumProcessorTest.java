import org.junit.Assert;
import org.junit.Test;
import processors.SpectrumProcessor;
import utils.TestUtils;

import static org.junit.Assert.assertEquals;

public class SpectrumProcessorTest {

    @Test
    public void spectralCentroidTest(){

        double spectralCentroid = SpectrumProcessor.calculateSpectralCentroid(TestUtils.loadAudioFromMP3File());


        assertEquals(0.02,spectralCentroid,0.1);

    }


    @Test
    public void spectralFluxfTest(){

        double spectralFlux = SpectrumProcessor.calculateSpectralFlux(TestUtils.loadAudioFromMP3File());

        assertEquals(0.01,spectralFlux,0.1);

    }
}
