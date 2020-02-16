package components;

import model.ModuleParams;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.nd4j.linalg.api.ndarray.INDArray;
import testutils.WavUtils;

import static testutils.TestingConstants.TEST_SAMPLE;

public class AudioFeaturesExtractorTest {

    private WavUtils wavUtils;

    private AudioFeaturesExtractor SUT;

    @Before
    public void startUp() {
        wavUtils = new WavUtils();
        SUT = new AudioFeaturesExtractor();
    }

    @Test
    public void globalFeatureExtraction() throws Exception {

        // Transform the input file into a float[] array
        double[] samples = wavUtils.load_wav(TEST_SAMPLE);
        // Extract globalFeatures
        INDArray features = SUT.globalFeatureExtraction(samples, 22050, 2205, 2205, 2205, 2205, new ModuleParams());

        System.out.print(features);
    }

    @Ignore
    @Test
    public void mtFeatureExtraction() {
//
//        // Transform the input file into a float[] array
//        double[] samples = wavUtils.load_wav(TEST_SAMPLE);
//        // Extract globalFeatures
//        INDArray features = SUT.mtFeatureExtraction(samples, 22050, 2205, 2205, 2205, 2205);
//
//        System.out.print(features);
    }


    @Test
    public void stFeatureExtraction() throws Exception {

        // Transform the input file into a float[] array
        double[] samples = wavUtils.load_wav(TEST_SAMPLE);
        // Extract globalFeatures
        INDArray features = SUT.extractAudioFeatures(samples, 22050, 2205, 2205);

        System.out.print(features);
    }

}
