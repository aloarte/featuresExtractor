import model.AudioFeatures;
import org.junit.Before;
import org.junit.Test;
import testutils.WavUtils;

import java.util.List;

import static testutils.TestingConstants.TEST_SAMPLE;

public class FeaturesExtractorTest {


    String pathFile;
    private WavUtils wavUtils;

    private FeaturesExtractor SUT;

    @Before
    public void startUp() {
        wavUtils = new WavUtils();
        SUT = new FeaturesExtractor();
    }

    @Test
    public void processAudioSource() throws Exception {

        // Transform the input file into a float[] array
        double[] samples = wavUtils.load_wav(TEST_SAMPLE);

        List<AudioFeatures> extractedFeatures = SUT.processAudioSource(samples);

    }


}
