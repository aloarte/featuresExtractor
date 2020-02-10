package processors;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.nd4j.linalg.api.ndarray.INDArray;
import testutils.WavUtils;

import static testutils.TestingConstants.TEST_AUDIO_SAMPLE_WAV_PATH;
import static testutils.TestingConstants.TEST_SAMPLE;

public class GeneralRawProcessorTest {

    private WavUtils wavUtils;

    private GeneralRawProcessor SUT;

    @Before
    public void startUp() {
        wavUtils = new WavUtils();
        SUT = new GeneralRawProcessor();
    }

    @Test
    public void globalFeatureExtraction() {

        // Transform the input file into a float[] array
        double[] samples = wavUtils.load_wav(TEST_SAMPLE);
        // Extract globalFeatures
        INDArray features = SUT.globalFeatureExtraction(samples, 22050, 2205, 2205, 2205, 2205);

        System.out.print(features);
    }

    @Ignore
    @Test
    public void mtFeatureExtraction() {

        // Transform the input file into a float[] array
        double[] samples = wavUtils.load_wav(TEST_AUDIO_SAMPLE_WAV_PATH);
        // Extract globalFeatures
        INDArray features = SUT.mtFeatureExtraction(samples, 22050, 2205, 2205, 2205, 2205);

        System.out.print(features);
    }

    @Ignore
    @Test
    public void stFeatureExtraction() {

        // Transform the input file into a float[] array
        double[] samples = wavUtils.load_wav(TEST_AUDIO_SAMPLE_WAV_PATH);
        // Extract globalFeatures
        INDArray features = SUT.stFeatureExtraction(samples, 22050, 2205, 2205);

        System.out.print(features);
    }

}
