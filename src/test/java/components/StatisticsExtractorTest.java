package components;

import model.ModuleParams;
import model.exceptions.AudioAnalysisException;
import org.junit.Before;
import org.junit.Test;
import org.nd4j.linalg.api.ndarray.INDArray;
import org.nd4j.linalg.factory.Nd4j;
import org.nd4j.linalg.indexing.NDArrayIndex;
import testutils.INDArrayUtils;

import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThat;
import static testutils.TestingConstants.*;

public class StatisticsExtractorTest {

    StatisticsExtractor SUT;

    INDArray controlShortTermFeatures;
    INDArray controlMidMeanTermFeatures;

    ModuleParams moduleParams;

    private double[] roundPrecision = new double[]{
            10000d,
            1000d,
            100d,
            10d,
            1d};

    @Before
    public void startUp() {
        SUT = new StatisticsExtractor();
        controlShortTermFeatures = INDArrayUtils.readShortTermFeaturesFromFile(TEST_KNIFE_301s_CONTROL_VALUES_SHORTTERM);
        moduleParams = new ModuleParams(TEST_FREQUENCY_RATE, 0.01, 0.01, 1, 1);
    }

    @Test
    public void obtainAudioFeaturesStatistics_knife10s() throws AudioAnalysisException {
        INDArray controlMidMeanTermFeatures = INDArrayUtils.readMidTermFeaturesFromFile(TEST_KNIFE_10s_CONTROL_VALUES_MIDTERM);
        INDArray controlShortTermFeatures = INDArrayUtils.readShortTermFeaturesFromFile(TEST_KNIFE_10s_CONTROL_VALUES_SHORTTERM);

        INDArray extractedMidTermFeatures = SUT.obtainMidTermFeatures(controlShortTermFeatures, moduleParams);
        INDArrayUtils.assertMidTermFeaturesData(extractedMidTermFeatures, controlMidMeanTermFeatures, roundPrecision);
    }

    @Test
    public void obtainAudioFeaturesStatistics_knife30s() throws AudioAnalysisException {
        INDArray controlMidMeanTermFeatures = INDArrayUtils.readMidTermFeaturesFromFile(TEST_KNIFE_30s_CONTROL_VALUES_MIDTERM);
        INDArray controlShortTermFeatures = INDArrayUtils.readShortTermFeaturesFromFile(TEST_KNIFE_30s_CONTROL_VALUES_SHORTTERM);

        INDArray extractedMidTermFeatures = SUT.obtainMidTermFeatures(controlShortTermFeatures, moduleParams);
        INDArrayUtils.assertMidTermFeaturesData(extractedMidTermFeatures, controlMidMeanTermFeatures, roundPrecision);
    }


    @Test
    public void obtainAudioFeaturesStatistics_knife301s() throws AudioAnalysisException {
        INDArray controlMidMeanTermFeatures = INDArrayUtils.readMidTermFeaturesFromFile(TEST_KNIFE_301s_CONTROL_VALUES_MIDTERM);
        INDArray controlShortTermFeatures = INDArrayUtils.readShortTermFeaturesFromFile(TEST_KNIFE_301s_CONTROL_VALUES_SHORTTERM);

        INDArray extractedMidTermFeatures = SUT.obtainMidTermFeatures(controlShortTermFeatures, moduleParams);
        INDArrayUtils.assertMidTermFeaturesData(extractedMidTermFeatures, controlMidMeanTermFeatures, roundPrecision);
    }


    @Test
    public void calculateStatisticalInfo() {

        INDArray midTermSlice = controlShortTermFeatures.get(
                NDArrayIndex.point(0),
                NDArrayIndex.interval(0, 100));

        INDArray outputSlice = Nd4j.zeros(midTermSlice.rows() * moduleParams.getStatisticalMeasuresNumber(), 100);
        List<Double> sliceStatistics = SUT.calculateStatisticalInfo(midTermSlice, moduleParams);
        assertNotNull(sliceStatistics);

        assertThat(sliceStatistics.size(), is(moduleParams.getStatisticalMeasuresNumber()));
        assertThat(sliceStatistics.get(0), is(TEST_SLICE_STATS_MEAN));
        assertThat(sliceStatistics.get(1), is(TEST_SLICE_STATS_STD));

    }



}
