package components;

import model.ModuleParams;
import org.junit.Before;
import org.junit.Test;
import org.nd4j.linalg.api.ndarray.INDArray;
import testutils.INDArrayUtils;

import static testutils.TestingConstants.TEST_FREQUENCY_RATE;
import static testutils.TestingConstants.TEST_KNIFE_301s_CONTROL_VALUES_SHORTTERM;

public class BpmProcessorTest {

    BpmExtractor SUT;

    @Before
    public void setup() {
        SUT = new BpmExtractor();
    }


    @Test
    public void getBpm_knife301s() {
        ModuleParams moduleParams = new ModuleParams(TEST_FREQUENCY_RATE, 0.01, 0.01, 1, 1);
        INDArray controlShortTermFeatures = INDArrayUtils.readShortTermFeaturesFromFile(TEST_KNIFE_301s_CONTROL_VALUES_SHORTTERM);

        SUT.extractBpm(controlShortTermFeatures, moduleParams);
    }


}
