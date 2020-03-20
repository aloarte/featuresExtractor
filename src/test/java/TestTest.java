import model.AudioFeatures;
import model.ModuleParams;
import model.exceptions.AudioExtractionException;
import org.junit.Before;
import org.junit.Test;
import testutils.WavUtils;

import java.util.List;

import static testutils.TestingConstants.TEST_SAMPLE_KNIFE;

public class TestTest {

    WavUtils wavUtils;

    @Before
    public void startUp() {
        wavUtils = new WavUtils();
    }


    @Test
    public void testLane() throws AudioExtractionException {
//        double[][] shortFeatureData = SamplesReaderUtils.readFeatureData(TEST_SAMPLE_SHORT_FEATURE);
//        double[][] midFeatureData = SamplesReaderUtils.readFeatureData(TEST_SAMPLE_MID_FEATURE);
//
//        double[] extractedFeatures = SamplesReaderUtils.readExtractedFeaturesData(TEST_SAMPLE_FEATURES);
//
//
//        assert shortFeatureData != null;
//        assert midFeatureData != null;
//        assert extractedFeatures != null;
//        System.out.println("ShortFeatureData[" + shortFeatureData.length + "][" + shortFeatureData[0].length + "]");
//        System.out.println("MidFeatureData[" + midFeatureData.length + "][" + midFeatureData[0].length + "]");
//        System.out.println("ExtractedFeatures[" + extractedFeatures.length + "]");
//
//        DataParserPyAudioAnalisis dataParserPyAudioAnalisis = new DataParserPyAudioAnalisis();
//
//        List<AudioFeatures> audioFeatures = dataParserPyAudioAnalisis.parseAudioFeaturesPython(Nd4j.create(extractedFeatures), new ModuleParams(22050, 1, 1, 1, 1));


        double[] samples = wavUtils.load_wav(TEST_SAMPLE_KNIFE);
        AudioFeaturesManager audioFeaturesManager = new AudioFeaturesManager();
        ModuleParams moduleParams = new ModuleParams(22050, 0.01, 0.01, 1, 1);
        moduleParams.enableLogProcessesDuration();
        List<AudioFeatures> audioFeaturesJava = audioFeaturesManager.processAudioSource(samples, moduleParams);

        //assertNotNull(audioFeatures);


    }
}
