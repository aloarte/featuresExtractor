package components;

import model.ModuleParams;
import model.exceptions.AudioAnalysisException;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.nd4j.linalg.api.ndarray.INDArray;
import testutils.TestUtils;

import static testutils.TestingConstants.TEST_FREQUENCY_RATE;
import static testutils.TestingConstants.TEST_KNIFE_301s_WAV;

@Ignore
public class BpmProcessorTest {

    BpmExtractor SUT;

    @Before
    public void setup() {
        SUT = new BpmExtractor();
    }


    @Test
    public void getBpm_knife301s() throws AudioAnalysisException {
        ModuleParams moduleParams = new ModuleParams(TEST_FREQUENCY_RATE, 0.04, 0.04, 1, 1);

        double[] samples = new TestUtils().load_wav(TEST_KNIFE_301s_WAV);

        //Extract the matrix with the [32 features] x [N window samples]
        INDArray shortTermFeatures = new AudioFeaturesExtractor().extractShortTermFeatures(samples, moduleParams.getFrequencyRate(), moduleParams.getShortTermWindowSize(), moduleParams.getShortTermStepSize());


        //INDArray controlShortTermFeatures = INDArrayUtils.readShortTermFeaturesFromFile(TEST_KNIFE_301s_CONTROL_VALUES_SHORTTERM);

        SUT.extractBpm(shortTermFeatures, moduleParams);


    }


}
