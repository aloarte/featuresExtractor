import org.junit.Assert;
import org.junit.Test;
import processors.RythmProcessor;
import utils.TestUtils;

import static org.junit.Assert.assertEquals;

public class RythmProcessorTest {


    @Test
    public void bpmTest(){

        double bpm = RythmProcessor.calculateBPM(TestUtils.loadAudioFromMP3File());

        assertEquals(0.03,bpm,0.1);

    }


    @Test
    public void bpmconfTest(){

        double bpmconf = RythmProcessor.calculateBPMConf(TestUtils.loadAudioFromMP3File());

        assertEquals(0.04,bpmconf,0.1);

    }
}
