import org.junit.Assert;
import org.junit.Test;
import processors.GenericAudioFeatures;
import utils.TestUtils;

public class GenericProcessorTest {



    @Test
    public void zrcTest(){

        double zrc = GenericAudioFeatures.calculateZRC(TestUtils.loadAudioFromMP3File());

        Assert.assertEquals(0.5,zrc,0.1);

    }
}
