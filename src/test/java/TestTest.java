import model.AudioFeatures;
import model.ModuleParams;
import model.exceptions.AudioExtractionException;
import org.junit.Before;
import org.junit.Test;
import org.nd4j.linalg.factory.Nd4j;
import testutils.SamplesReaderUtils;
import testutils.WavUtils;

import java.util.List;

import static org.junit.Assert.assertNotNull;
import static testutils.TestingConstants.*;

public class TestTest {

    WavUtils wavUtils;

    @Before
    public void startUp() {
        wavUtils = new WavUtils();
    }


    @Test
    public void testLane() throws AudioExtractionException {
        double[][] shortFeatureData = SamplesReaderUtils.readFeatureData(TEST_SAMPLE_SHORT_FEATURE);
        double[][] midFeatureData = SamplesReaderUtils.readFeatureData(TEST_SAMPLE_MID_FEATURE);

        double[] extractedFeatures = SamplesReaderUtils.readExtractedFeaturesData(TEST_SAMPLE_FEATURES);


        assert shortFeatureData != null;
        assert midFeatureData != null;
        assert extractedFeatures != null;
        System.out.println("ShortFeatureData[" + shortFeatureData.length + "][" + shortFeatureData[0].length + "]");
        System.out.println("MidFeatureData[" + midFeatureData.length + "][" + midFeatureData[0].length + "]");
        System.out.println("ExtractedFeatures[" + extractedFeatures.length + "]");

        DataParserPyAudioAnalisis dataParserPyAudioAnalisis = new DataParserPyAudioAnalisis();

        List<AudioFeatures> audioFeatures = dataParserPyAudioAnalisis.parseAudioFeaturesPython(Nd4j.create(extractedFeatures), new ModuleParams(22050, 1, 1, 1, 1));
        double[] samples = wavUtils.load_wav(TEST_SAMPLE_KNIFE);

        AudioFeaturesManager audioFeaturesManager = new AudioFeaturesManager();


        List<AudioFeatures> audioFeaturesJava = audioFeaturesManager.processAudioSource(samples, new ModuleParams(22050, 1, 1, 1, 1));

        assertNotNull(audioFeatures);


    }


}
