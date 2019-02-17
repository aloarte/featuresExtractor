import org.junit.Assert;
import org.junit.Test;
import processors.FrequencyProcessor;
import utils.TestUtils;

public class FrequencyProcessorTest {


    @Test
    public void mfccsTest(){

        double mfccs = FrequencyProcessor.calculateMFCCS(TestUtils.loadAudioFromMP3File());

        Assert.assertEquals(0.04,mfccs,0.1);

    }
}
